package Compiller.Syntactic;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Compiller.Lexic.Token;

public class ParserCpp {
    
    private StringBuilder code = new StringBuilder();

    
    private List<Token> tokens;
    private Token currentToken;
    private List<Token> tokenErrorList;
    private String newCode;
    
    public StringBuilder getCode() {
        return code;
    }

    public void setCode(StringBuilder code) {
        this.code = code;
    }

    public ParserCpp(List<Token> tokens) {
        this.tokens = tokens;
        this.tokenErrorList = new ArrayList<>();
    }

    public void translate(String newCode) {
        code.append(" "+ newCode + " ");
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
        if(matchLexeme("saida" , "cout << ")){
            if(matchLexeme("(" , "")){
                if(matchType("TXT" , currentToken.getLexeme())){
                    if(matchLexeme(")", "<< endl")){
                        if(matchLexeme(";" , ";\n")){
                            return true;
                        }
                        error(";", currentToken);
                        return false;
                    }
                    error(")", currentToken);
                    return false;
                }

                else if (EXPI()){
                    if(matchLexeme(")", "<< endl")){
                        if(matchLexeme(";" , ";\n")){
                            return true;
                        }
                        error(";", currentToken);
                        return false;
                    }
                    error(")", currentToken);
                    return false;
                }

                else if (EXPF()){
                    if(matchLexeme(")", "<< endl")){
                        if(matchLexeme(";", ";\n")){
                            return true;
                        }
                        error(";", currentToken);
                        return false;
                    }
                    error(")", currentToken);
                    return false;
                }

                else if(matchType("ID" , currentToken.getLexeme())){
                    if(matchLexeme(")" , "<< endl")){
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
                                    if (matchLexeme("senao", "    else")) {
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
                                    error("senao", currentToken);
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
                    if (matchLexeme(";" , ";")) {
                        if (COND()) {
                            if (matchLexeme(";", ";")) {
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
            String tokenId = currentToken.getLexeme();
            if (matchType("ID" , currentToken.getLexeme())) {
                if (matchLexeme(";" , ";\n")) {
                    return true;
                }
                else if (matchLexeme("=" , "=")) {
                    if(matchLexeme("entrada", "0;\ncin >> ")){
                        if(matchLexeme("(" , tokenId)){
                            if(matchLexeme(")" , "")){
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
                    else if (EXPI()) {
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
        if (matchLexeme("decimal" , "float")) {
            String tokenId = currentToken.getLexeme();
            if (matchType("ID" , currentToken.getLexeme())) {
                if (matchLexeme(";" , ";\n")) {
                    return true;
                }
                else if (matchLexeme("=" , "=")) {
                    if(matchLexeme("entrada", "0;\ncin >> ")){
                        if(matchLexeme("(" , tokenId)){
                            if(matchLexeme(")" , "")){
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
                    else if (EXPF()) {
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
        if (matchLexeme("texto" , "string")) {
            String tokenId = currentToken.getLexeme();
            if (matchType("ID" , currentToken.getLexeme())) {

                if (STRING_(tokenId)) {
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

    public boolean STRING_(String tokenId) {
        if (matchLexeme(";" , ";\n")) {
            return true;
        }
        else if (matchLexeme("=" , "=")) {
            if(matchLexeme("entrada", "\"\";\ncin >> ")){
                if(matchLexeme("(" , tokenId)){
                    if(matchLexeme(")" , "")){
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
        if (matchLexeme("estado" , "bool")) {
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
            error("BOOLEAN", currentToken);
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
                        if (NUMI()) {
                            return true;
                        }
                        error("inteiro", currentToken);
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
        else if (EXPI()) {
            if (matchLexeme(";")) {
                return true;
            }
            error(";", currentToken);
            return false;
        }
        else if (EXPF()) {
            if (matchLexeme(";")) {
                return true;
            }
            error(";", currentToken);
            return false;
        }
        return false;
    }

    public boolean EXPI() {
        if (TI()) {
            if (RI()) {
                return true;
            }
        }
        return false;
    }

    public boolean EXPF() {
        if (TF()) {
            if (RF()) {
                return true;
            }
        }
        return false;
    }

    public boolean RI() {
        if (matchLexeme("+" , "+")){
            if (TI()) {
                if (RI()) {
                    return true;
                }
            }
        }
        else if (matchLexeme("-" , "-")){
            if (TI()) {
                if (RI()) {
                    return true;
                }
            }
        }
        return true;
    }

    public boolean RF() {
        if (matchLexeme("+" , "+")){
            if (TF()) {
                if (RF()) {
                    return true;
                }
            }
        }
        else if (matchLexeme("-" , "-")){
            if (TF()) {
                if (RF()) {
                    return true;
                }
            }
        }
        return true;
    }

    public boolean TI() {
        if (FI()){
            if (SI()) {
                return true;
            }
        }
        return false;
    }

    public boolean TF() {
        if (FF()){
            if (SF()) {
                return true;
            }
        }
        return false;
    }

    public boolean SI() {
        if (matchLexeme("*" , "*")) {
            if (FI()) {
                if (SI()) {
                    return true;
                }
            }
        }
        else if (matchLexeme("/" , "/")) {
            if (FI()) {
                if (SI()) {
                    return true;
                }
            }
        }
        return true;
    }
    
    public boolean SF() {
        if (matchLexeme("*" , "*")) {
            if (FF()) {
                if (SF()) {
                    return true;
                }
            }
        }
        else if (matchLexeme("/" , "/")) {
            if (FF()) {
                if (SF()) {
                    return true;
                }
            }
        }
        return true;
    }

    public boolean FI() {
        if (matchLexeme("(" , "(")) {
            if (EXPI()) {
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
        error("ID or inteiro", currentToken);
        return false;
    }

    public boolean FF() {
        if (matchLexeme("(" , "(")) {
            if (EXPF()) {
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
        error("ID or EXP", currentToken);
        return false;
    }

    public boolean NUMI() {
        if (matchType("INT", currentToken.getLexeme())) {
            return true;
        }
        return false;
    }

    public boolean NUM() {
        if (matchType("INT", currentToken.getLexeme())) {
            return true;
        }
        else if (matchType("FLOAT", currentToken.getLexeme())){
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

    public boolean analyze() {
        
        currentToken = getNextToken();
        translate("#include <iostream>\n");
        translate("#include <string>\n");
        translate("using namespace std;\n");
        translate("int main() {\n");
        if(BLOCO()) {
            if (currentToken.getType().equals("EOF") && tokenErrorList.size() == 0) {
                translate("return 0;\n");
                translate("} \n");
                return true;
            } 
            else {
                error("EOF", currentToken);
                return false;
            }
        }
        else {
            error("BLOCO", currentToken);
            return false;
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