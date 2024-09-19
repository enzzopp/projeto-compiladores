package Compiller.Lexical;
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
        afds.add(new Delimiter());
        afds.add(new Integer());
        afds.add(new Float());
        afds.add(new Reserved());
        afds.add(new Identifier());
        afds.add(new LexicalError());

    }

    public boolean hasErrorTokens(){
        for (Token token : tokens) {
            if ("ERR".equals(token.getType())) {
                return true;
            }
        }
        return false;
    }

    public String getTokensErrorMessage(){
        String message = "";
        for (Token token : tokens) {
            if ("ERR".equals(token.getType())) {
                String msgErrToken = String.format("\n\nLexical Error: Token not recognized: '%s'\n\t at line %s%d\u001B[0m",
                "\u001B[31m" + token.getLexeme() + "\u001B[0m", "\u001B[33m", token.getLine());

                message += msgErrToken + "\n";
            }
        }
        return message;
    }

    public void skipWhitespaces() {
        while(code.current() == ' ' || code.current() == '\n' || code.current() == '\t') {
            code.next();
        }
    }

    private int getTokenLineFromPos(int pos) {

        int line = 1;
        code.setIndex(0);

        for (int i = 0; i < pos; i++) {
            
            if (code.current() == '\n'){
                line++;
            }
            code.next();
        }
        code.setIndex(pos);
        return line;
    }

    public List<Token> getTokens(){
        boolean isCriticalError;
        int pos = 0;
        int line = 0;

        while (code.current() != CharacterIterator.DONE) {
            isCriticalError = true;
            skipWhitespaces();
            
            if (code.current() == CharacterIterator.DONE) break;

            for (AFD afd : afds) {
                pos = code.getIndex();
                line = getTokenLineFromPos(pos);
                Token t = afd.evaluate(code, line);
                if (t != null) {
                    isCriticalError = false;
                    tokens.add(t);
                    break;
                } 
                else {
                    code.setIndex(pos);
                }
            }  
            if (!isCriticalError) continue;

            throw new RuntimeException("Critical error: Token not recognized: " + code.current() + " at line " + line);
            
        }

        if (hasErrorTokens()){
            throw new RuntimeException(getTokensErrorMessage());
        }
        System.out.println(getTokenLineFromPos(pos));
        tokens.add(new Token("EOF", "$", getTokenLineFromPos(pos)));
        return tokens;
    }
}