import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Token t1 = new Token("ID","Resultado");
        // Token t2 = new Token("NUM","234");

        List<Token> tokens;
        String code = "2 + 2 + 2";
        Lexer lexer = new Lexer(code);
        tokens = lexer.getTokens();

        for (Token t: tokens){
            System.out.println(t);
        }
    }
}
