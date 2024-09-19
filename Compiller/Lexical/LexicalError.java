package Compiller.Lexical;
import java.text.CharacterIterator;

public class LexicalError extends AFD {

    @Override
    public Token evaluate(CharacterIterator code, int line) {

        String word = readError(code);

        if (endError(code)){
            return new Token("ERR", word, line);
        }
        return null;
    }
    private String readError(CharacterIterator code){
        String word = "";

        while (!endError(code)){
            word += code.current();
            code.next();
        }
        return word;
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
        code.current() == ';' ||
        code.current() == '\n' ||
        code.current() == CharacterIterator.DONE;
    }
}