package Compiller.Lexical;
import java.text.CharacterIterator;

public class Float extends AFD {

    @Override
    public Token evaluate(CharacterIterator code, int line) {

        if (Character.isDigit(code.current()) || code.current() == '.'){
            String number = readNumber(code);

            if (endNumber(code) && !number.equals(".")){
                return new Token("FLOAT", number, line);
            }
        }
        return null;
    }
    private String readNumber(CharacterIterator code){
        String number = "";

        while (Character.isDigit(code.current()) || code.current() == '.'){

            if (code.current() == '.' && number.contains(".")){
                return null;
            }

            number += code.current();
            code.next();
        }
        return number;
    }

    private boolean endNumber(CharacterIterator code){
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