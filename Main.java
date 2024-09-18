
import java.util.List;

import Compiller.Lexical.Code;
import Compiller.Lexical.Lexer;
import Compiller.Lexical.Token;

public class Main {
    public static void main(String[] args) {

        // Token t1 = new Token("ID","Resultado");
        // Token t2 = new Token("NUM","234");

        List<Token> tokens;
        String path = "resources/code.txt";

        Code f1 = new Code(path);

        System.out.println(f1.countLines());

        Lexer lexer = new Lexer(f1.getCodeString());

        tokens = lexer.getTokens();

        for (Token t: tokens){
            System.out.println(t);
        }
    }
}