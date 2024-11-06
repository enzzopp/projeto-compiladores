import java.util.List;
import Compiller.Lexic.Code;
import Compiller.Lexic.Lexer;
import Compiller.Lexic.Token;
import Compiller.Syntactic.ParserJava;
import Compiller.Syntactic.ParserC;

public class Main {
    public static void main(String[] args) {

        List<Token> tokens;
        String path = "resources/code";

        Code f1 = new Code(path);

        Lexer lexer = new Lexer(f1.getCodeString());

        tokens = lexer.getTokens();

        for (Token token : tokens) {
            // System.out.println(token);
        }

        if (args.length > 0 && args[0].equals("-j")) {
            ParserJava parser = new ParserJava(tokens);
            parser.analyze();
        }
        else if (args.length > 0 && args[0].equals("-c")) {
            ParserC parser = new ParserC(tokens);
            parser.analyze();
        }
    }
}