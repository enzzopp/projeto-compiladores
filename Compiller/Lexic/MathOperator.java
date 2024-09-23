package Compiller.Lexic;
import java.text.CharacterIterator;

public class MathOperator extends AFD{
    @Override
    public Token evaluate(CharacterIterator code, int line){
        switch(code.current()){
            case '+' :
                code.next();
                return new Token("PLUS", "+", line);

            case '-' :
                code.next();
                return new Token("SUB", "-", line);

            case '*' :
                code.next();
                return new Token("MUL", "*", line);
            case '%' :
                code.next();
                return new Token("MOD", "%", line);

            case '/' :
                code.next();
                return new Token("DIV", "/", line);
                
            case '(' :
                code.next();
                return new Token("OP", "(", line);

            case ')' :
                code.next();
                return new Token("CP", ")", line);

            default:
                return null;
        }
    }
    
}
