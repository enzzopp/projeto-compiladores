package Compiller.Lexic;
import java.text.CharacterIterator;

public class LexicalError extends AFD {

    @Override
    public Token evaluate(CharacterIterator code, int line) {

        String lexeme = readError(code);

        if (endError(code)){
            return new Token("ERR", lexeme, line);
        }
        return null;
    }
    private String readError(CharacterIterator code){
        String lexeme = "";

        while (!endError(code)){
            lexeme += code.current();
            code.next();
        }

        if (lexeme.length() == 0){
            code.next();
            return null;
        }

        return lexeme;
    }

    private boolean endError(CharacterIterator code){
        return 
        code.current() == ' ' ||
        code.current() == '+' ||
        code.current() == '-' || 
        code.current() == '*' || 
        code.current() == '/' || 
        code.current() == '(' || 
        code.current() == ')' ||
        code.current() == '}' || 
        code.current() == '{' ||  
        code.current() == ';' ||
        code.current() == '\n' ||
        code.current() == '"' ||
        code.current() == CharacterIterator.DONE;
    }
}