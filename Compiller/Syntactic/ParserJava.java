package Compiller.Syntactic;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Compiller.Lexic.Token;

public class ParserJava {
    
    StringBuilder code = new StringBuilder();
    private List<Token> tokens;
    private Token currentToken;
    private List<Token> tokenErrorList;
    private String newCode;


    public ParserJava(List<Token> tokens) {
        this.tokens = tokens;
        this.tokenErrorList = new ArrayList<>();
    }

    public void translate(String newCode) {
        code.append(" "+ newCode + " ");
        //System.out.print(" "+ newCode + " ");
    }

    public Token getNextToken() {
        
        if (tokens.size() > 0) {
            return tokens.remove(0);
        }
        return null;
    }

    public void error(String rule, Token currentToken) {


        // if (currentToken.getType() == "EOF") {
        //     return;
        // }
        
        if (!tokenErrorList.contains(currentToken)) {
            tokenErrorList.add(currentToken);

            String errorMessage = String.format(
            "\n\nSyntax Error:\n\t unexpected token at line %s got: %s expected: %s\u001B[0m",
            currentToken.getLine(),                                  // Regra esperada em vermelho
            "\u001B[31m" + currentToken.getLexeme() + "\u001B[0m",              // Token atual em amarelo
            "\u001B[32m" + rule + "\u001B[0m"                 // Número da linha em amarelo
            );

            System.out.println(errorMessage);
        }
    }

    public boolean BLOCO() {
        if (currentToken.getLexeme().equals("se")) { // IFELSE
            if (IFELSE()) {
                if (BLOCO()) {
                    return true;
                }
                return false;
            }
            return false;
        }
        else if (currentToken.getType().equals("ID")) { // ATR
            if (ATR()) {
                if (BLOCO()) {
                    return true;
                }
                return false; 
            }
            return false;
        }
        else if (currentToken.getLexeme().equals("enquanto")){ // WHILE
            if (WHILE()){
                if(BLOCO()) {
                    return true;
                }
                return false;
            }
            return false;
        }
        else if (currentToken.getLexeme().equals("para")) { // FOR
            if (FOR()) {
                if(BLOCO()) {
                    return true;
                }
                return false;
            }
            return false;
        }
        else if (currentToken.getLexeme().equals("inteiro") || currentToken.getLexeme().equals("decimal") || currentToken.getLexeme().equals("texto") || currentToken.getLexeme().equals("estado")) { // DECL
            if (DECL()) {
                if (BLOCO()) {
                    return true;
                }
                return false;
            }
            return false;
        }

        else if (currentToken.getLexeme().equals("saida")){ // PRINT
            if(PRINT()){
                if(BLOCO()){
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public boolean PRINT(){
        if(matchLexeme("saida" , "System.out.println")){
            if(matchLexeme("(" , "(")){
                if(matchType("TXT" , currentToken.getLexeme())){
                    if(matchLexeme(")", ")")){
                        if(matchLexeme(";" , ";\n")){
                            return true;
                        }
                        error(";", currentToken);
                        return false;
                    }
                    error(")", currentToken);
                    return false;
                }

                else if (EXP()){
                    if(matchLexeme(")" , ")")){
                        if(matchLexeme(";" , ";\n")){
                            return true;
                        }
                        error(";", currentToken);
                        return false;
                    }
                    error(")", currentToken);
                    return false;
                }

                else if(matchType("ID" , currentToken.getLexeme())){
                    if(matchLexeme(")" , ")")){
                        if(matchLexeme(";" , ";\n")){
                            return true;
                        }
                        error(";", currentToken);
                        return false;
                    }
                    error(")", currentToken);
                    return false;
                }
                error("ID", currentToken);
                return false;
            }
            error("(", currentToken);
            return false;
        }
        return false;
    }

    public boolean IFELSE() {
        if (matchLexeme("se", "if ")) {
            if (matchLexeme("(", "(")) {
                if (COND()) {
                    if (matchLexeme(")", ")")) {
                        if (matchLexeme("{", "{\n")) {
                            if (BLOCO()) {
                                if (matchLexeme("}", "}\n")) {
                                    if (matchLexeme("cnao", "    else")) {
                                        if (matchLexeme("{", "{\n")) {
                                            if (BLOCO()) {
                                                if (matchLexeme("}", "}\n")) {
                                                    return true;
                                                }
                                                error("}", currentToken);
                                                return false;
                                            }
                                            return false;
                                        }
                                        error("{", currentToken);
                                        return false;
                                    }
                                    error("cnao", currentToken);
                                    return false;
                                }
                                error("}", currentToken);
                                return false;
                            }
                            error("BLOCO", currentToken);
                            return false;
                        }
                        error("{", currentToken);
                        return false;
                    }
                    error(")", currentToken);
                    return false;
                }
                error("CONDICAO", currentToken);
                return false;
            }
            error("(", currentToken);
            return false;
        }
        return false;
    }
    
    public boolean WHILE() {
        if(matchLexeme("enquanto", "while")){
            if(matchLexeme("(", "(")){
                if(COND()){
                    if (matchLexeme(")", ")")) {
                        if(matchLexeme("{", "{\n")){
                            if(BLOCO()){
                                if(matchLexeme("}", "}\n")){
                                    return true;
                                }
                                error("}", currentToken);
                                return false;
                            }
                            error("BLOCO", currentToken);
                            return false;
                        }
                        error("{", currentToken);
                        return false;
                    }
                    error(")", currentToken);
                    return false;
                }
                error("CONDICAO", currentToken);
                return false;
            }
            error("(", currentToken);
            return false;
        }
        return false;
    }
    
    public boolean FOR() {
        if (matchLexeme("para" , "for")) {
            if (matchLexeme("(" , "(")) {
                if (ATR_FOR()) {
                    if (matchLexeme(";" , ";\n")) {
                        if (COND()) {
                            if (matchLexeme(";", ";\n")) {
                                if (INC()) {
                                    if (matchLexeme(")",")")) {
                                        if (matchLexeme("{","{")) {
                                            if (BLOCO()) {
                                                if (matchLexeme("}","}")) {
                                                    return true;
                                                }
                                                error("}", currentToken);
                                                return false;
                                            }
                                            error("BLOCO", currentToken);
                                            return false;
                                        }
                                        error("{", currentToken);
                                        return false;
                                    }
                                    error(")", currentToken);
                                    return false;
                                }
                                error("INC", currentToken);
                                return false;
                            }
                            error(";", currentToken);
                            return false;
                        }
                        error("CONDICAO", currentToken);
                        return false;
                    }
                    error(";", currentToken);
                    return false;
                }
                error("ATR_FOR", currentToken);
                return false;
            }
            error("(", currentToken);
            return false;
        }
        return false;
    }

    public boolean DECL() {
        if (INT()){
            return true;
        }
        else if (FLOAT()){
            return true;
        }
        else if (STRING()){
            return true;
        }
        else if (BOOLEAN()){
            return true;
        }
        return false;
    }

    public boolean INT() {
        if (matchLexeme("inteiro" , "int")) {
            if (matchType("ID" , currentToken.getLexeme())) {
                if (matchLexeme(";" , ";\n")) {
                    return true;
                }
                else if (matchLexeme("=" , "=")) {
                    if(matchLexeme("entrada", "scanner.nextInt")){
                        if(matchLexeme("(" , "(")){
                            if(matchLexeme(")" , ")")){
                                if(matchLexeme(";", ";\n")){
                                    return true;
                                }
                                error(";", currentToken);
                                return false;
                            }
                            error(")", currentToken);
                            return false;
                        }
                        error("(", currentToken);
                        return false;
                    }
                    else if (EXP()) {
                        if (matchLexeme(";" , ";\n")) {
                            return true;
                        }
                        error(";", currentToken);
                        return false;
                    }
                    error("EXP or entrada", currentToken);
                    return false;
                }
                error("; or =", currentToken);
                return false;
            }
            error("ID", currentToken);
            return false;   
        }
        return false;
    }

    public boolean FLOAT() {
        if (matchLexeme("decimal" , "double")) {
            if (matchType("ID" , currentToken.getLexeme())) {
                if (matchLexeme(";" , ";\n")) {
                    return true;
                }
                else if (matchLexeme("=" , "=")) {
                    if(matchLexeme("entrada", "scanner.nextFloat")){
                        if(matchLexeme("(" , "(")){
                            if(matchLexeme(")" , ")")){
                                if(matchLexeme(";", ";\n")){
                                    return true;
                                }
                                error(";", currentToken);
                                return false;
                            }
                            error(")", currentToken);
                            return false;
                        }
                        error("(", currentToken);
                        return false;
                    }
                    else if (EXP()) {
                        if (matchLexeme(";" , ";\n")) {
                            return true;
                        }
                        error(";", currentToken);
                        return false;
                    }
                    error("EXP or entrada", currentToken);
                    return false;
                }
                error("; or =", currentToken);
                return false;
            }
            error("ID", currentToken);
            return false;
        }
        return false;
    }

    public boolean STRING() {
        if (matchLexeme("texto" , "String")) {
            if (matchType("ID" , currentToken.getLexeme())) {
                if (STRING_()) {
                    return true;
                }
                error("; or =", currentToken);
                return false;
            }
            error("ID", currentToken);
            return false;
        }
        return false;
    }

    public boolean STRING_() {
        if (matchLexeme(";" , ";\n")) {
            return true;
        }
        else if (matchLexeme("=" , "=")) {
            if(matchLexeme("entrada", "scanner.nextLine")){
                if(matchLexeme("(" , "(")){
                    if(matchLexeme(")" , ")")){
                        if(matchLexeme(";", ";\n")){
                            return true;
                        }
                        error(";", currentToken);
                        return false;
                    }
                    error(")", currentToken);
                    return false;
                }
                error("(", currentToken);
                return false;
            }
            else if (matchType("TXT" , currentToken.getLexeme())) {
                if (matchLexeme(";", ";\n")) {
                    return true;
                }
                error(";", currentToken);
                return false;
            }
            error("TXT or entrada", currentToken);
            return false;
        }
        error("; or =", currentToken);
        return false;
    }

    public boolean BOOLEAN() {
        if (matchLexeme("estado" , "boolean")) {
            if (matchType("ID" , currentToken.getLexeme())) {
                if (BOOLEAN_()) {
                    return true;
                }
                error("ID", currentToken);
                return false;
            }
            error("estado", currentToken);
            return false;
        }
        return false;
    }

    public boolean BOOLEAN_() {
        if (matchLexeme(";", ";\n")) {
            return true;
        }
        else if (matchLexeme("=" ,"=")) {
            if (BOOLEAN__()) {
                return true;
            }
            error("BOOLEAN__", currentToken);
            return false;
        }
        error("; or =", currentToken);
        return false;
    }

    public boolean BOOLEAN__() {
        if (matchLexeme("real" , "true")) {
            if (matchLexeme(";" , ";\n")) {
                return true;
            }
            error(";", currentToken);
            return false;
        }
        else if (matchLexeme("barça" , "false")) {
            if (matchLexeme(";" , ";\n")) {
                return true;
            }
            error(";", currentToken);
            return false;
        }
        error("real or barça", currentToken);
        return false;
    }
        
    public boolean ATR_FOR() {
        if (matchLexeme("inteiro", "int")) {
            if (matchType("ID" , currentToken.getLexeme())) {
                if (matchLexeme("=" , "=")) {
                    if (NUM()) {
                        return true;
                    }
                    else if (matchType("ID" , currentToken.getLexeme())) {
                        return true;
                    }
                    error("ID or NUM", currentToken);
                    return false;
                }
                error("=", currentToken);
                return false;
            }
            error("ID", currentToken);
            return false;
        }
        error("inteiro", currentToken);
        return false;
    }

    public boolean INC() {
        if (matchType("ID" , currentToken.getLexeme())) {
            if (matchLexeme("=" , "=")) {
                if (matchType("ID" , currentToken.getLexeme())) {
                    if (OP_MAT()) {
                        if (NUM()) {
                            return true;
                        }
                        error("NUM", currentToken);
                        return false;
                    }
                    error("OP_MAT", currentToken);
                    return false;
                }
                error("ID", currentToken);
                return false;
            }
            error("=", currentToken);
            return false;
        }
        error("ID", currentToken);
        return false;
    }

    public boolean OP_MAT() {
        if (matchLexeme("+" , "+")) {
            return true;
        }
        else if (matchLexeme("-" , "-")) {
            return true;
        }
        else if (matchLexeme("/" , "/")) {
            return true;
        }
        else if (matchLexeme("*" , "*")) {
            return true;
        }
        error("+ | - | / | *", currentToken);
        return false;
    }


    public boolean COND() {
        if (matchType("ID", currentToken.getLexeme())) {
            if (OP()) {
                if (OP_()) {
                    return true;
                }
                return false;
            }
            error("OP", currentToken);
            return false;
        }
        error("ID", currentToken);
        return false;
    }

    public boolean OP() {
        if (matchLexeme("<", " < ")) {
            return true;
        }
        else if (matchLexeme(">", " > ")) {
            return true;
        }
        else if (matchLexeme("==", " == ")) {
            return true;
        }
        else if (matchLexeme("<=", " <= ")) {
            return true;
        }
        else if (matchLexeme(">=", " >= ")) {
            return true;
        }
        else if (matchLexeme("!=", " != ")) {
            return true;
        }
        error("OP", currentToken);
        return false;
    }

    public boolean OP_() {
        if (matchType("ID", currentToken.getLexeme())) {
            return true;
        }
        else if (NUM()) {
            return true;
        }
        error("ID or NUM", currentToken);
        return false;
    }

    public boolean ATR() {
        if (matchType("ID",currentToken.getLexeme())) {
            if(X()) {
                translate(";\n");
                return true;
            }
            error("=", currentToken);
            return false;
        }
        error("ID", currentToken);
        return false;
    }

    public boolean X() {
        if (matchLexeme("=", "=")) {
            if (Y()) {
                return true;
            }
            return false;
        }
        error("=", currentToken);
        return false;
    }

    public boolean Y() {
        if (matchType("TXT", currentToken.getLexeme())) {
            if (matchLexeme(";")) {
                return true;
            }
            error(";", currentToken);
            return false;
        }
        else if ((matchLexeme("real")) || (matchLexeme("barça"))){
            if (matchLexeme(";")) {
                return true;
            }
            error(";", currentToken);
            return false;
        }
        else if (EXP()) {
            if (matchLexeme(";")) {
                return true;
            }
            error(";", currentToken);
            return false;
        }
        return false;
    }

    public boolean EXP() {
        if (T()) {
            if (R()) {
                return true;
            }
        }
        return false;
    }

    public boolean R() {
        if (matchLexeme("+" , "+")){
            if (T()) {
                if (R()) {
                    return true;
                }
            }
        }
        else if (matchLexeme("-" , "-")){
            if (T()) {
                if (R()) {
                    return true;
                }
            }
        }
        return true;
    }

    public boolean T() {
        if (F()){
            if (S()) {
                return true;
            }
        }
        return false;
    }

    public boolean S() {
        if (matchLexeme("*" , "*")) {
            if (F()) {
                if (S()) {
                    return true;
                }
            }
        }
        else if (matchLexeme("/" , "/")) {
            if (F()) {
                if (S()) {
                    return true;
                }
            }
        }
        return true;
    }

    public boolean F() {
        if (matchLexeme("(" , "(")) {
            if (EXP()) {
                if (matchLexeme(")" , ")")) {
                    return true;
                }
                error(")", currentToken);
                return false;
            }
        }
        else if (matchType("ID" , currentToken.getLexeme())) {
            return true;
        }
        else if (NUM()) {
            return true;
        }
        error("ID or inteiro or decimal", currentToken);
        return false;
    }

    public boolean NUM() {
        if (matchType("INT", currentToken.getLexeme())) {
            //translate(";");
            return true;
        }
        else if (matchType("FLOAT", currentToken.getLexeme())) {
            //translate(";");
            return true;
        }
        return false;
    }
    

    public boolean matchLexeme(String lexeme) {
        if (currentToken.getLexeme().equals(lexeme)) {
            currentToken = getNextToken();
            return true;
        }
        return false;
    }

    public boolean matchLexeme(String lexeme, String newCode) {
        if (currentToken.getLexeme().equals(lexeme)) {
            translate(newCode);
            currentToken = getNextToken();
            return true;
        }
        return false;
    }

    public boolean matchType(String type) {
        if (currentToken.getType().equals(type)) {
            currentToken = getNextToken();
            return true;
        }
        return false;
    }

    public boolean matchType(String type, String newCode) {
        if (currentToken.getType().equals(type)) {
            translate(newCode);
            currentToken = getNextToken();
            return true;
        }
        return false;
    }

    public void createJavaTranslateFile(String path , StringBuilder contents){

        try (FileWriter writer = new FileWriter(path)){
            writer.write(contents.toString());
        } catch (IOException e) {
            System.err.println("ERRO ao escrever no arquivo: " + e.getMessage());
        }
    }

    public void analyze() {
        
        boolean hasImport = false;
        
        for (Token token : tokens) {
            if (token.getLexeme().equals("entrada") && !hasImport){
                translate("import java.util.Scanner;\n");
                hasImport = true;
            }
        }
        
        currentToken = getNextToken();
        translate("public class JavaTranslate { \n");
        translate("public static void main (String[] args) {\n");
        if(hasImport){
            translate("Scanner scanner = new Scanner (System.in);\n");
        }
        if(BLOCO()) {
            if (currentToken.getType().equals("EOF") && tokenErrorList.size() == 0) {
                if(hasImport){
                    translate("  scanner.close();\n");
                }
                translate("} \n");
                translate("} \n");
                // System.out.println("Syntax is correct!");
                createJavaTranslateFile("resources/JavaTranslate.java", code);
            } 
            else {
                error("EOF", currentToken);
            }
        }
        else {
            error("BLOCO", currentToken);
        }
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    
    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }
}