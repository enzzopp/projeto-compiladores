package Compiller.Lexical;
import java.text.CharacterIterator;

public class Integer extends AFD {

    @Override
    public Token evaluate(CharacterIterator code) {

        if (Character.isDigit(code.current())){
            String number = readNumber(code);

            if (endNumber(code)){
                return new Token("INT", number);
            }
        }
        return null;
    }

    private String readNumber(CharacterIterator code){
        String number = "";

        while (Character.isDigit(code.current()) && code.current() != '.'){
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