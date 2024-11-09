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

    public void amountTypeTokens(List <Token> listTokens){
        int amountINT = 0;
        int amountFLOAT = 0;
        int amountSTRING = 0;
        int amountPRINTS = 0;
        int amountINPUTS = 0;
        int amountFOR = 0;
        int amountWHILE = 0;
        int amountOP = 0;
        int amountCP = 0;
        int amountOB = 0;
        int amountCB = 0;
        
        for (Token token : listTokens) {
            if (token.getType().equals("INT")) {
                amountINT ++;
            }
            else if(token.getType().equals("FLOAT")){
                amountFLOAT++;
            }
            else if(token.getType().equals("TXT")){
                amountSTRING++;
            }
            else if(token.getLexeme().equals("saida")){
                amountPRINTS++;
            }
            else if(token.getLexeme().equals("entrada")){
                amountINPUTS++;
            }
            else if(token.getLexeme().equals("para")){
                amountFOR++;
            }
            else if(token.getLexeme().equals("enquanto")){
                amountWHILE++;
            }
            else if(token.getType().equals("OP")){
                amountOP++;
            }
            else if(token.getType().equals("CP")){
                amountCP++;
            }
            else if(token.getType().equals("OB")){
                amountOB++;
            }
            else if(token.getType().equals("CB")){
                amountCB++;
            }
        }

        System.out.println("----- ESTATÍSTICAS DO CÓDIGO -----");

        System.out.println("Quantidade de inteiros: " + amountINT);
        System.out.println("Quantidade de floats: " + amountFLOAT);
        System.out.println("Quantidade de strings: " + amountSTRING);
        System.out.println("Quantidade de prints: " + amountPRINTS);
        System.out.println("Quantidade de inputs: " + amountINPUTS);
        System.out.println("Quantidade de for: " + amountFOR);
        System.out.println("Quantidade de while: " + amountWHILE);
        System.out.println("Quantidade de '(': " + amountOP);
        System.out.println("Quantidade de ')': " + amountCP);
        System.out.println("Quantidade de '{': " + amountOB);
        System.out.println("Quantidade de '}': " + amountCB);
        System.out.println("---------------------------------");
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
