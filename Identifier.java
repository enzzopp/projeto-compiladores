import java.text.CharacterIterator;

public class Identifier extends AFD{

    @Override
    public Token evaluate(CharacterIterator code){

        boolean isFirstLetter = Character.isLetter(code.current());

        if(isFirstLetter){
            String letter = readLetter(code);

            if(endletter(code)){
                return new Token("ID", letter);
            }
        }
        return null;
    }

    private String readLetter(CharacterIterator code){

        String id = "";
        
        while(Character.isLetter(code.current()) || Character.isDigit(code.current()) || code.current() == '_'){

            id += code.current();
            code.next();
        }
        return id;
    }

    private boolean endletter(CharacterIterator code){
        return 
        code.current() == ' ' || 
        code.current() == '+' || 
        code.current() == '-' || 
        code.current() == '*' || 
        code.current() == '/' || 
        code.current() == '(' || 
        code.current() == ')' || 
        code.current() == '\n'||
        code.current() == CharacterIterator.DONE;
    }
    
}
