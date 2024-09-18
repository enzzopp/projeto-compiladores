package Compiller.Lexical;
import java.text.CharacterIterator;
import java.util.HashSet;
import java.util.Set;

public class Reserved extends AFD{

    private static final Set<String> RESERVED_WORDS = new HashSet<>();

    static {
        RESERVED_WORDS.add("cheio");
        RESERVED_WORDS.add("quebrado");
        RESERVED_WORDS.add("escrita");
        RESERVED_WORDS.add("boi");
        RESERVED_WORDS.add("real");
        RESERVED_WORDS.add("barça");
        RESERVED_WORDS.add("roda");
        RESERVED_WORDS.add("vaiqueva");
        RESERVED_WORDS.add("cpa");
        RESERVED_WORDS.add("cnao");
        RESERVED_WORDS.add("ecpa"); // else if
        RESERVED_WORDS.add("oia"); //output
        RESERVED_WORDS.add("bota"); //input
    }

    @Override
    public Token evaluate(CharacterIterator code){

        boolean isFirstLetter = Character.isLetter(code.current());

        if(isFirstLetter){
            String word = readLetter(code);

            if(endletter(code) && RESERVED_WORDS.contains(word)){
                return new Token("RESERVED", word);
            }
        }
        return null;
    }

    private String readLetter(CharacterIterator code){

        String id = "";
        
        while(Character.isLetter(code.current()) || Character.isDigit(code.current()) || code.current() == '_'){

            id += code.current();
            code.next();
        }
        return id;
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
