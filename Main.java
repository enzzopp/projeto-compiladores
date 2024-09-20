import java.util.List;
import Compiller.Lexic.Code;
import Compiller.Lexic.Lexer;
import Compiller.Lexic.Token;

public class Main {
    public static void main(String[] args) {

        // Token t1 = new Token("ID","Resultado");
        // Token t2 = new Token("NUM","234");

        List<Token> tokens;
        String path = "resources/code.du";

        Code f1 = new Code(path);

        Lexer lexer = new Lexer(f1.getCodeString());

        tokens = lexer.getTokens();

        for (Token t: tokens){
            System.out.println(t);
        }
    }
}