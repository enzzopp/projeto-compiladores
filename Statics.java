import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import Compiller.Lexic.Token;

public class Statics {

    private List<Token> listTokens;
    private StringBuilder statics = new StringBuilder();

    public Statics(List <Token> listTokens){
        this.listTokens = listTokens;
    }

    public String amountTypeTokens(List <Token> listTokens){
        int amountID = 0;
        int amountINT = 0;
        int amountFLOAT = 0;
        int amountSTRING = 0;
        int amountPRINTS = 0;
        int amountINPUTS = 0;
        int amountFOR = 0;
        int amountWHILE = 0;
        int amountPLUS = 0;
        int amountSUB = 0;
        int amountMUL = 0;
        int amountDIV = 0;
        int amountTRUE = 0;
        int amountFALSE = 0;
        int amountOP = 0;
        int amountCP = 0;
        int amountOB = 0;
        int amountCB = 0;
        int smallIds = 0;

        for (Token token : listTokens) {
            if(token.getType().equals("ID")){
                //System.out.println(token.getLexeme() + token.getLexeme().length());

                if(token.getLexeme().length() <= 4) smallIds++;

                amountID++;
            }
            else if (token.getType().equals("INT")) {
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
            else if(token.getType().equals("PLUS")){
                amountPLUS++;
            }
            else if(token.getType().equals("SUB")){
                amountSUB++;
            }
            else if(token.getType().equals("MUL")){
                amountMUL++;
            }
            else if(token.getType().equals("DIV")){
                amountDIV++;
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
            else if(token.getLexeme().equals("real")){
                amountTRUE++;
            }
            else if(token.getLexeme().equals("barça")){
                amountFALSE++;
            }
        }

        // System.out.println("----- ESTATÍSTICAS DO CÓDIGO -----");
        // System.out.println("Quantidade de ID: " + amountID);
        // System.out.println("Quantidade de inteiros: " + amountINT);
        // System.out.println("Quantidade de decimal: " + amountFLOAT);
        // System.out.println("Quantidade de texto: " + amountSTRING);
        // System.out.println("Quantidade de saida: " + amountPRINTS);
        // System.out.println("Quantidade de entrada: " + amountINPUTS);
        // System.out.println("Quantidade de para: " + amountFOR);
        // System.out.println("Quantidade de enquanto: " + amountWHILE);
        // System.out.println("Quantidade de '+': " + amountPLUS);
        // System.out.println("Quantidade de '-': " + amountSUB);
        // System.out.println("Quantidade de '*': " + amountMUL);
        // System.out.println("Quantidade de '/': " + amountDIV);
        // System.out.println("Quantidade de '(': " + amountOP);
        // System.out.println("Quantidade de ')': " + amountCP);
        // System.out.println("Quantidade de '{': " + amountOB);
        // System.out.println("Quantidade de '}': " + amountCB);
        // System.out.println("Quantidade de 'real': " + amountTRUE);
        // System.out.println("Quantidade de 'barça': " + amountFALSE);
        // System.out.println("Quantidade de '}': " + amountCB);
        // System.out.println("Quantidade de palavaras com tamanho menor ou igual a 4: " + smallIds);
        // System.out.println();


        return(
            ("----- ESTATÍSTICAS DO CÓDIGO -----\n") +
            ("Quantidade de ID: " + amountID + "\n") +
            ("Quantidade de inteiros: " + amountINT + "\n") +
            ("Quantidade de decimal: " + amountFLOAT + "\n") +
            ("Quantidade de texto: " + amountSTRING + "\n") +
            ("Quantidade de saida: " + amountPRINTS + "\n") +
            ("Quantidade de entrada: " + amountINPUTS + "\n") +
            ("Quantidade de para: " + amountFOR + "\n") +
            ("Quantidade de enquanto: " + amountWHILE + "\n") +
            ("Quantidade de '+': " + amountPLUS + "\n") +
            ("Quantidade de '-': " + amountSUB + "\n") +
            ("Quantidade de '*': " + amountMUL + "\n") +
            ("Quantidade de '/': " + amountDIV + "\n") +
            ("Quantidade de '(': " + amountOP + "\n") +
            ("Quantidade de ')': " + amountCP + "\n") +
            ("Quantidade de '{': " + amountOB + "\n") +
            ("Quantidade de '}': " + amountCB + "\n") +
            ("Quantidade de 'real': " + amountTRUE + "\n") +
            ("Quantidade de 'barça': " + amountFALSE + "\n") +
            ("Quantidade de '}': " + amountCB + "\n") +
            ("Quantidade de palavaras com tamanho menor ou igual a 4: " + smallIds + "\n")
        );
        
    }

    public void smallWords(Token words , int len){
        if(words.getType().equals("ID") && words.getLexeme().length() <= len){
            statics.append("AVISO: " + words.getLexeme() +" ESCREVA UM IDENTIFICADOR COM UM NÚMERO MAIOR DE CARACTERES\n");
            System.out.println("\033[0;33m AVISO: " + words.getLexeme() +"\033[0m ESCREVA UM IDENTIFICADOR COM UM NÚMERO MAIOR DE CARACTERES");
        }
    }

    public void createStaticFile(String path){
        String qtds = amountTypeTokens(listTokens);
        statics.append(qtds + "\n");

        for (Token token : listTokens){
            smallWords(token, 4);
        }
        statics.append("---------------------------------");

        try (FileWriter writer = new FileWriter(path)){
            writer.write(statics.toString());
        } catch (IOException e) {
            System.err.println("ERRO ao escrever no arquivo: " + e.getMessage());
        }
    }

    public void countCaracters(String path){
        System.out.println();
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

    public StringBuilder getStatics() {
        return statics;
    }

    public void setStatics(StringBuilder statics) {
        this.statics = statics;
    }
}
