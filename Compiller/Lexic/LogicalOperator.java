package Compiller.Lexic;
import java.text.CharacterIterator;

public class LogicalOperator extends AFD {
    
    @Override
    public Token evaluate(CharacterIterator code, int line){
        switch(code.current()){
            case '&' :
                code.next();
                if (code.current() == '&'){
                    code.next();
                    return new Token("AND", "&&", line);
                }
                return null;
            case '|' :
                code.next();
                if (code.current() == '|'){
                    code.next();
                    return new Token("OR", "||", line);
                }
                return null;
            case '!' :
                code.next();
                if (code.current() == '='){
                    code.next();
                    return new Token("NE", "!=", line);
                }
                return new Token("NOT", "!", line);
            case '<' :
                code.next();
                if(code.current() == '='){
                    code.next();
                    return new Token("LE", "<=", line);
                }
                return new Token("LT", "<", line);
            case '>' :
                code.next();
                if(code.current() == '='){
                    code.next();
                    return new Token("GE", ">=", line);
                }
                return new Token("GT", ">", line);
            case '=' :
                code.next();
                if(code.current() == '='){
                    code.next();
                    return new Token("EQUAL", "==", line);
                }
                return new Token("ASSIGN", "=", line);
            default:
                return null;
        }
    }
}
