package Compiller.Lexical;
import java.text.CharacterIterator;

public class Delimiter extends AFD {
    
    @Override
    public Token evaluate(CharacterIterator code, int line){
        switch(code.current()){
            case ';' :
                code.next();
                return new Token("ENDL", ";", line);
            case '{' :
                code.next();
                return new Token("OB", "{", line);
            case '}' :
                code.next();
                return new Token("CB", "}", line);
            default:
                return null;
        }
    }
}
