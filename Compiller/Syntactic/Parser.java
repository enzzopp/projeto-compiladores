package Compiller.Syntactic;
import java.util.ArrayList;
import java.util.List;
import Compiller.Lexic.Token;

public class Parser {
    
    private List<Token> tokens;
    private Token currentToken;
    private Token previousToken;
    private List<Token> tokenErrorList;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.tokenErrorList = new ArrayList<>();
    }

    public Token getNextToken() {
        
        if (tokens.size() > 0) {
            return tokens.remove(0);
        }
        return null;
    }

    public void error(String rule, Token currentToken) {

        if (currentToken.getType() == "EOF") {
            return;
        }
        
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
        else if (currentToken.getLexeme().equals("enquanto")){
            if (WHILE()){
                if(BLOCO()) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public boolean IFELSE() {
        if (matchLexeme("se")) {
            if (matchLexeme("(")) {
                if (COND()) {
                    if (matchLexeme(")")) {
                        if (matchLexeme("{")) {
                            if (BLOCO()) {
                                if (matchLexeme("}")) {
                                    if (matchLexeme("cnao")) {
                                        if (matchLexeme("{")) {
                                            if (BLOCO()) {
                                                if (matchLexeme("}")) {
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
        if(matchLexeme("enquanto")){
            if(matchLexeme("(")){
                if(COND()){
                    if (matchLexeme(")")) {
                        if(matchLexeme("{")){
                            if(BLOCO()){
                                if(matchLexeme("}")){
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

    public boolean COND() {
        if (matchType("ID")) {
            if (OP()) {
                if (NUM()) {
                    return true;
                }
                else if (matchType("ID")) {
                    return true;
                }
                error("INT or FLOAT", currentToken);
                return false;
            }
            return false;
        }
        error("ID", currentToken);
        return false;
    }

    public boolean OP() {
        if (matchLexeme("<")) {
            return true;
        }
        else if (matchLexeme(">")) {
            return true;
        }
        else if (matchLexeme("==")) {
            return true;
        }
        else if (matchLexeme("<=")) {
            return true;
        }
        else if (matchLexeme(">=")) {
            return true;
        }
        else if (matchLexeme("!=")) {
            return true;
        }
        error("OP", currentToken);
        return false;
    }

    public boolean ATR() {
        if (matchType("ID")) {
            if (matchLexeme("=")){
                if (EXP()) {
                    if (matchLexeme(";")) {
                        if (X()) {
                            return true;
                        }
                    }
                    error(";", currentToken);
                    return false;
                }
                return false;
            }
            error("=", currentToken);
            return false;
        }
        error("ID", currentToken);
        return false;
    }

    public boolean X() {
        if (currentToken.getType().equals("ID")) {   
            if (ATR()) {
                return true;
            }
        }
        return true;
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
        if (matchLexeme("+")){
            if (T()) {
                if (R()) {
                    return true;
                }
            }
        }
        else if (matchLexeme("-")){
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
        if (matchLexeme("*")) {
            if (F()) {
                if (S()) {
                    return true;
                }
            }
        }
        else if (matchLexeme("/")) {
            if (F()) {
                if (S()) {
                    return true;
                }
            }
        }
        return true;
    }

    public boolean F() {
        if (matchLexeme("(")) {
            if (EXP()) {
                if (matchLexeme(")")) {
                    return true;
                }
                error(")", currentToken);
                return false;
            }
        }
        else if (matchType("ID")) {
            return true;
        }
        else if (NUM()) {
            return true;
        }
        error("ID or NUM or EXP", currentToken);
        return false;
    }

    public boolean NUM() {
        if (matchType("INT")) {
            return true;
        }
        else if (matchType("FLOAT")) {
            return true;
        }
        return false;
    }
    

    public boolean matchLexeme(String lexeme) {
        if (currentToken.getLexeme().equals(lexeme)) {
            previousToken = currentToken;
            currentToken = getNextToken();
            return true;
        }
        return false;
    }

    public boolean matchType(String type) {
        if (currentToken.getType().equals(type)) {
            previousToken = currentToken;
            currentToken = getNextToken();
            return true;
        }
        return false;
    }

    public void analyze() {
        currentToken = getNextToken();
        if(BLOCO()) {
            if (currentToken.getType().equals("EOF") && tokenErrorList.size() == 0) {
                System.out.println("Syntax is correct!");
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
}