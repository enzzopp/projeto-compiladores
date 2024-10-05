package Compiller.Lexic;
import java.text.CharacterIterator;
import java.util.HashSet;
import java.util.Set;

public class Reserved extends AFD{

    private static final Set<String> RESERVED_WORDS = new HashSet<>();

    static {
        RESERVED_WORDS.add("inteiro"); // int
        RESERVED_WORDS.add("decimal"); //float
        RESERVED_WORDS.add("texto"); //String
        RESERVED_WORDS.add("estado"); //boolean
        RESERVED_WORDS.add("real"); //true
        RESERVED_WORDS.add("barça"); //false
        RESERVED_WORDS.add("para"); //for
        RESERVED_WORDS.add("enquanto"); // while
        RESERVED_WORDS.add("se"); //if
        RESERVED_WORDS.add("senao"); //else
        RESERVED_WORDS.add("ou"); //else if
        RESERVED_WORDS.add("saida"); //output
        RESERVED_WORDS.add("entrada"); //input
    }

    @Override
    public Token evaluate(CharacterIterator code, int line){

        boolean isFirstLetter = Character.isLetter(code.current());

        if(isFirstLetter){
            String lexeme = readLexeme(code);

            if(endLexeme(code) && RESERVED_WORDS.contains(lexeme)){
                return new Token("RESERVED", lexeme, line);
            }
        }
        return null;
    }

    private String readLexeme(CharacterIterator code){

        String lexeme = "";
        
        while(Character.isLetter(code.current()) || Character.isDigit(code.current()) || code.current() == '_'){

            lexeme += code.current();
            code.next();
        }
        return lexeme;
    }

    private boolean endLexeme(CharacterIterator code){
        return 
        code.current() == ' ' || 
        code.current() == '+' || 
        code.current() == '-' || 
        code.current() == '*' || 
        code.current() == '/' || 
        code.current() == '(' || 
        code.current() == ')' || 
        code.current() == '}' || 
        code.current() == '{' || 
        code.current() == ';' ||
        code.current() == '\n'||
        code.current() == '"' ||
        code.current() == CharacterIterator.DONE;
    }
}