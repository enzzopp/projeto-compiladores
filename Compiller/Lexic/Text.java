package Compiller.Lexic;
import java.text.CharacterIterator;

public class Text extends AFD {

    @Override
    public Token evaluate(CharacterIterator code, int line) {
        
        if (code.current() == '"') {
            String lexeme = readLexeme(code);
            
            if (lexeme != null && endLexeme(code)) {
                return new Token("TXT", lexeme, line);
            }
        }
        return null;
    }

    private String readLexeme(CharacterIterator code) {
        String lexeme = "";
        
        // Adiciona a primeira aspa dupla
        lexeme += code.current();
        code.next();

        while (code.current() != '"' && code.current() != CharacterIterator.DONE) {
            lexeme += code.current();
            code.next();
        }

        // Adiciona a aspa dupla de fechamento se encontrada
        if (code.current() == '"') {
            lexeme += code.current();
            code.next();
            return lexeme;
        }
        return null;
    }

    private boolean endLexeme(CharacterIterator code){
        return 
        code.current() == ' ' || 
        code.current() == '+' || 
        code.current() == '-' || 
        code.current() == '*' || 
        code.current() == '/' || 
        code.current() == '(' || 
        code.current() == ')' || 
        code.current() == ';' ||
        code.current() == '\n'||
        code.current() == '"' ||
        code.current() == CharacterIterator.DONE;
    }
}
