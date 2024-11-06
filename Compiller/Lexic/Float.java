package Compiller.Lexic;
import java.text.CharacterIterator;

public class Float extends AFD {

    @Override
    public Token evaluate(CharacterIterator code, int line) {

        if (Character.isDigit(code.current())){

            String lexeme = readNumber(code);

            if (lexeme.charAt(lexeme.length()-1) == '.'){
                return null;
            }


            if (endNumber(code) && !lexeme.equals(".")){
                return new Token("FLOAT", lexeme, line);
            }
        }
        return null;
    }
    private String readNumber(CharacterIterator code){
        String lexeme = "";

        while (Character.isDigit(code.current()) || code.current() == '.'){

            if (code.current() == '.' && lexeme.contains(".")){
                return null;
            }

            lexeme += code.current();
            code.next();
        }
        return lexeme;
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
        code.current() == '}' || 
        code.current() == '{' ||  
        code.current() == ';' ||
        code.current() == '\n' ||
        code.current() == '"' ||
        code.current() == CharacterIterator.DONE;
    }
}