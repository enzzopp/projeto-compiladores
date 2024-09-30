package Compiller.Lexic;
import java.text.CharacterIterator;

public class Integer extends AFD {

    @Override
    public Token evaluate(CharacterIterator code, int line) {

        if (Character.isDigit(code.current())){
            String lexeme = readNumber(code);

            if (endLexeme(code)){
                return new Token("INT", lexeme, line);
            }
        }
        return null;
    }

    private String readNumber(CharacterIterator code){
        String lexeme = "";

        while (Character.isDigit(code.current()) && code.current() != '.'){
            lexeme += code.current();
            code.next();
        }
        return lexeme;
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
        code.current() == '}' || 
        code.current() == '{' ||  
        code.current() == ';' ||
        code.current() == '\n' ||
        code.current() == '"' ||
        code.current() == CharacterIterator.DONE;
    }
}