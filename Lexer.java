import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private List<Token> tokens;
    private List<AFD> afds;
    private CharacterIterator code;

    public Lexer(String code) {
        tokens = new ArrayList<>();
        afds = new ArrayList<>();
        this.code = new StringCharacterIterator(code);
        
        afds.add(new MathOperator());
        afds.add(new Number());
        afds.add(new Identifier());

    }

    public void skipWhitespaces() {
        while(code.current() == ' ' || code.current() == '\n' || code.current() == '\t') {
            code.next();
        }
    }

    public List<Token> getTokens(){
        boolean accept;

        while (code.current() != CharacterIterator.DONE) {
            accept = false;
            skipWhitespaces();
            
            if (code.current() == CharacterIterator.DONE) break;

            for (AFD afd : afds) {
                int pos = code.getIndex();
                Token t = afd.evaluate(code);
                if (t != null) {
                    accept = true;
                    tokens.add(t);
                    break;
                } 
                else {
                    code.setIndex(pos);
                }
            }  
            if (accept) continue;
            throw new RuntimeException("Error: Token not recognized: " + code.current());
        }
        tokens.add(new Token("EOF", "$"));
        return tokens;
    }
}
