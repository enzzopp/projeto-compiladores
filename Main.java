import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Compiller.Lexic.Code;
import Compiller.Lexic.Lexer;
import Compiller.Lexic.Token;
import Compiller.Syntactic.ParserJava;
import Compiller.Syntactic.ParserCpp;
import Compiller.Semantic.Semantic;

public class Main {

    public static void createJavaTranslateFile(String path, StringBuilder contents){
    
        try (FileWriter writer = new FileWriter(path)){
            writer.write(contents.toString());
        } catch (IOException e) {
            System.err.println("ERRO ao escrever no arquivo: " + e.getMessage());
        }
    }
    public static void createCppTranslateFile(String path, StringBuilder contents){
    
        try (FileWriter writer = new FileWriter(path)){
            writer.write(contents.toString());
        } catch (IOException e) {
            System.err.println("ERRO ao escrever no arquivo: " + e.getMessage());
        }
    }
    public static void main(String[] args) {

        List<Token> tokens;
        List<Token> tokensSemantic;
        
        String path = "resources/code";

        Code f1 = new Code(path);
        
        Lexer lexer = new Lexer(f1.getCodeString());

        Statics s = new Statics(lexer.getTokens());
        
        tokens = lexer.getTokens();
        tokensSemantic = new ArrayList<>();

        for (Token token : tokens) {
            Token tokenSemantic = new Token(token.getType(),token.getLexeme(), token.getLine());
            tokensSemantic.add(tokenSemantic);
        }

        
        System.out.println(s.amountTypeTokens(tokensSemantic));
        
        s.createStaticFile("resources/Statics");
        System.out.println("---------------------------------");

        boolean isCorrect = false;
        
        if (args.length > 0 && args[0].equals("-j")) {
            ParserJava parser = new ParserJava(tokens);
            isCorrect = parser.analyze();

            Semantic semantic = new Semantic(tokensSemantic);
            isCorrect = semantic.analyze() && isCorrect;

            if (isCorrect) {
                createJavaTranslateFile("resources/JavaTranslate.java", parser.getCode());
                s.countCaracters("resources/code");
                
            }
        }
        else if (args.length > 0 && args[0].equals("-c")) {
            ParserCpp parser = new ParserCpp(tokens);
            isCorrect = parser.analyze();

            Semantic semantic = new Semantic(tokensSemantic);
            isCorrect = semantic.analyze() && isCorrect;
            
            if (isCorrect) {
                createCppTranslateFile("resources/CppTranslate.cpp", parser.getCode());
            }
        }
    }

}
