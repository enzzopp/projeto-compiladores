import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Token t1 = new Token("ID","Resultado");
        // Token t2 = new Token("NUM","234");

        List<Token> tokens;
        FileSettings f1 = new FileSettings("ggdr");
        f1.read();
        Lexer lexer = new Lexer(f1.getCode());
        System.out.println(f1.countLines());
        tokens = lexer.getTokens();

        for (Token t: tokens){
            System.out.println(t);
        }
    }
}
