import java.text.CharacterIterator;

public class Number extends AFD {

    @Override
    public Token evaluate(CharacterIterator code) {

        if (Character.isDigit(code.current())){
            String number = readNumber(code);

            if (endNumber(code)){
                return new Token("NUM", number);
            }
        }
        return null;
    }

    private String readNumber(CharacterIterator code){
        String number = "";
        while (Character.isDigit(code.current()) || code.current() == '.'){

            if (code.current() == '.'){ //encontrou o numero
                if (number.contains(".")){ //se a string ja tem ponto anteriormente
                    throw new RuntimeException("Error: Invalid number: " + number + code.current());
                }
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
        code.current() == '\n' ||
        code.current() == CharacterIterator.DONE;
    }
}