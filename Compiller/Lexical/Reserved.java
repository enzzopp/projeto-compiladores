package Compiller.Lexical;
import java.text.CharacterIterator;
import java.util.HashSet;
import java.util.Set;

public class Reserved extends AFD{

    private static final Set<String> RESERVED_WORDS = new HashSet<>();

    static {
        RESERVED_WORDS.add("cheio"); // int
        RESERVED_WORDS.add("quebrado"); //float
        RESERVED_WORDS.add("escrita"); //String
        RESERVED_WORDS.add("boi"); //boolean
        RESERVED_WORDS.add("real"); //true
        RESERVED_WORDS.add("barça"); //false
        RESERVED_WORDS.add("roda"); //for
        RESERVED_WORDS.add("vaiqueva"); // while
        RESERVED_WORDS.add("cpa"); //if
        RESERVED_WORDS.add("cnao"); //else
        RESERVED_WORDS.add("ecpa"); //else if
        RESERVED_WORDS.add("oia"); //output
        RESERVED_WORDS.add("bota"); //input
    }

    @Override
    public Token evaluate(CharacterIterator code, int line){

        boolean isFirstLetter = Character.isLetter(code.current());

        if(isFirstLetter){
            String word = readLetter(code);

            if(endletter(code) && RESERVED_WORDS.contains(word)){
                return new Token("RESERVED", word, line);
            }
        }
        return null;
    }

    private String readLetter(CharacterIterator code){

        String word = "";
        
        while(Character.isLetter(code.current()) || Character.isDigit(code.current()) || code.current() == '_'){

            word += code.current();
            code.next();
        }
        return word;
    }

    private boolean endletter(CharacterIterator code){
        return 
        code.current() == ' ' || 
        code.current() == '+' || 
        code.current() == '-' || 
        code.current() == '*' || 
        code.current() == '/' || 
        code.current() == '(' || 
        code.current() == ')' || 
        code.current() == ';' ||
        code.current() == '\n'||
        code.current() == CharacterIterator.DONE;
    }
}
