package Compiller.Semantic;

import Compiller.Lexic.Token;

import java.util.ArrayList;
import java.util.List;


public class Semantic {

    private List<Token> tokens;
    private String message;
    private Token currentToken;
    private Token prevToken;
    private List<Token> declaredIds;

    public Semantic(List<Token> tokens) {
        this.tokens = tokens;
        this.message = "";
        this.currentToken = null;
        this.prevToken = null;
        this.declaredIds = new ArrayList<>();
    }

    public void goNextToken() {
        
        if (tokens.size() > 0) {
            prevToken = currentToken;
            currentToken = tokens.remove(0);
        }
        else {
            currentToken = null;
        }
    }

    public boolean hasErrorTokens(){
        if (!message.isEmpty()) {
            return true;
        }
        return false;
    }

    public String getTokensErrorMessages(){
        message = "";
        for (Token token : tokens) {
            if ("ERR".equals(token.getType())) {
                String msgErrToken = String.format("\n\nSemantic Error: Token used not declared: '%s'\n\t at line %s%d\u001B[0m",
                "\u001B[31m" + token.getLexeme() + "\u001B[0m", "\u001B[33m", token.getLine());

                message += msgErrToken + "\n";
            }
        }
        return message;
    }

    public boolean analyze() {
        
        goNextToken();
        while (currentToken != null) {
            if (currentToken.getType().equals("ID") && prevToken != null && (prevToken.getType().equals("INT") || prevToken.getType().equals("FLOAT") || prevToken.getType().equals("TXT") || prevToken.getType().equals("BOOL"))) {
            }
        }       
    }

}
