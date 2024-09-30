package Compiller.Lexic;
import java.text.CharacterIterator;

public class Identifier extends AFD{

    @Override
    public Token evaluate(CharacterIterator code, int line){

        if(Character.isLetter(code.current())){
            String lexeme = readLexeme(code);

            if(endLexeme(code)){
                return new Token("ID", lexeme, line);
            }
        }
        return null;
    }

    private String readLexeme(CharacterIterator code){

        String lexeme = "";
        
        while(Character.isLetter(code.current()) || Character.isDigit(code.current()) || code.current() == '_'){

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
        code.current() == '\n'||
        code.current() == '"' ||
        code.current() == CharacterIterator.DONE;
    }
    
}
