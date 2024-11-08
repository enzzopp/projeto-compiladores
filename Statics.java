import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import Compiller.Lexic.Token;

public class Statics {

    private List<Token> listTokens;

    public Statics(List <Token> listTokens){
        this.listTokens = listTokens;
    }

    public void countCaracters(String path){
        File file = new File(path);
        StringBuilder contents = new StringBuilder();
        int countLines = 0;
        try(Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                countLines++;
                contents.append(scanner.nextLine());
                contents.append("\n");
            }
            System.out.print(contents.toString() +"O código apresenta : " + contents.length() + " caracteres e " + countLines + " linhas.");
            //amountTypeTokens(listTokens);
        }catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado " + e.getMessage());
        }
    }

    public List<Token> getListTokens() {
        return listTokens;
    }

    public void setListTokens(List<Token> listTokens) {
        this.listTokens = listTokens;
    }

}
