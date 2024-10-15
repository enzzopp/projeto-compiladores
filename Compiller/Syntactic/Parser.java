package Compiller.Syntactic;
import java.util.List;
import Compiller.Lexic.Token;

public class Parser {
    
    private List<Token> tokens;
    private Token currentToken;
    private Token previousToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Token getNextToken() {
        return tokens.size() > 0 ? tokens.remove(0) : null;
    }

    public void error(String rule, Token currentToken) {
        System.out.println("Error in rule " + rule + " at line " + currentToken.getLine() +
        " by lexeme " + currentToken.getLexeme() + " and type " + currentToken.getType());
    }

    // ifElse -> se '(' condition ')' '{' expression '}' cnao '{' expression '}'
    // condition -> 'id' operator (num | 'id')
    // num -> FLOAT | INT
    // operator -> '<' | '>' | '==' | '<=' | '>=' | '!='
    // expression -> 'id' '=' E
    // E -> E + T | E - T | T
    // T -> T * F | T / F | F
    // F -> '(' E ')' | 'id' | num

    // eliminando recursão a esquerda e fatorando
    // BLOCO -> IFELSE BLOCO | FOR BLOCO | WHILE BLOCO | atr BLOCO | ε
    // IFELSE -> se '(' condition ')' '{' BLOCO '}' cnao '{' BLOCO '}'
    // atr -> 'id' '=' E ; X
    // X -> atr | ε
    // condition -> 'id' operator (num | 'id')
    // num -> FLOAT | INT
    // operator -> '<' | '>' | '==' | '<=' | '>=' | '!='
    // atribuicao -> Q
    // E -> T R
    // R -> '+' T R | '-' T R | ε
    // T -> F S
    // S -> '*' F S | '/' F S | ε
    // F -> '(' E ')' | 'id' | num

    public boolean BLOCO() {
        if (currentToken.getLexeme().equals("se")) {
            if (IFELSE() && BLOCO()) {
                return true;
            }
            return false;
        }
        else if (currentToken.getType().equals("ID")) { // atribuição
            if (ATR() && BLOCO()) {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean IFELSE() {
        if (currentToken.getLexeme().equals("se")) {
            if 
            (
                matchLexeme("se") && matchLexeme("(") && COND() &&
                matchLexeme(")") && matchLexeme("{") && BLOCO() &&
                matchLexeme("}") && matchLexeme("cnao") && matchLexeme("{") &&
                BLOCO() && matchLexeme("}")
            )
            {
                return true;
            }
            error("IFELSE", previousToken);
            return false;
        }
        return false;
    }

    public boolean COND() {
        if 
        (
            matchType("ID") && OP() && (matchType("FLOAT") || matchType("INT"))
        )
        {
            return true;
        }
        error("COND", currentToken);
        return false;
    }

    public boolean OP() {
        if 
        (
            matchLexeme("<") || matchLexeme(">") || matchLexeme("==") ||
            matchLexeme("<=") || matchLexeme(">=") || matchLexeme("!=")
        )
        {
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
                }
            }
        }
        error("ATR", previousToken);
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
        if (T() && R()) {
            return true;
        }
        error("EXP", currentToken);
        return false;
    }

    public boolean R() {
        if (matchLexeme("+") && T() && R() || matchLexeme("-") && T() && R()) {
            return true;
        }
        return true;
    }

    public boolean T() {
        if (F() && S()) {
            return true;
        }
        error("T", currentToken);
        return false;
    }

    public boolean S() {
        if (matchLexeme("*") && F() && S() || matchLexeme("/") && F() && S()) {
            return true;
        }
        return true;
    }

    public boolean F() {
        if 
        (
            matchLexeme("(") && EXP() && matchLexeme(")") || matchType("ID") || matchType("FLOAT") || matchType("INT")
        )
        {
            return true;
        }
        error("F", currentToken);
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
            if (currentToken.getType().equals("EOF")) {
                System.out.println("Syntax is correct!");
            } else {
                error("EOF", currentToken);
            }
        }
        else {
            error("BLOCO", previousToken);
        }
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public Token getCurrentToken() {
        return currentToken;
    }
}