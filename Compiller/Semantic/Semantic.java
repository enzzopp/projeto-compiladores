package Compiller.Semantic;

import Compiller.Lexic.Token;

import java.util.ArrayList;
import java.util.List;


public class Semantic {

    private List<Token> tokens;
    private String message;
    private Token currentToken;
    private Token prevToken;
    private List<String> declaredIds;

    public Semantic(List<Token> tokens) {
        this.tokens = tokens;
        this.message = "";
        this.currentToken = null;
        this.prevToken = null;
        this.declaredIds = new ArrayList<>();
    }

    public void goNextToken(boolean isFirst) {

        if (tokens.size() > 0) {
            
            if (isFirst) {
                prevToken = tokens.remove(0);
                currentToken = prevToken;
                isFirst = false;
            }
            else {
                prevToken = currentToken;
                currentToken = tokens.remove(0);
            }
        }
        else {
            currentToken = null;
        }
    }

    public boolean hasErrorTokens(){
        if (message.isEmpty()) {
            return false;
        }
        return true;
    }

    public void ThrowErrorMessages(Token token) {
        String msgErrToken = String.format("\n\nSemantic Error: Token used not declared: '%s'\n\t at line %s%d\u001B[0m",
        "\u001B[31m" + token.getLexeme() + "\u001B[0m", "\u001B[33m", token.getLine());

        message += msgErrToken + "\n";
    }

    public boolean analyze() {
        boolean isFirst = true;
        goNextToken(isFirst);

        while (currentToken != null) {
            if (currentToken.getType().equals("ID") && prevToken.getType().equals("RESERVED")) {
                declaredIds.add(currentToken.getLexeme());
            }
            else if (currentToken.getType().equals("ID")) {
                if (!declaredIds.contains(currentToken.getLexeme())) 
                {   
                    ThrowErrorMessages(currentToken);
                }
            }
            goNextToken(false);
        }
        if (!hasErrorTokens()) {
            return true;
        }
        System.out.println(message);
        return false;
    }
}
