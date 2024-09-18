package Compiller.Lexical;
import java.text.CharacterIterator;
import java.util.HashSet;
import java.util.Set;

public class Reserved extends AFD{

    private static final Set<String> RESERVED_WORDS = new HashSet<>();

    static {
        RESERVED_WORDS.add("int");
        RESERVED_WORDS.add("float");
        RESERVED_WORDS.add("double");
        RESERVED_WORDS.add("char");
        RESERVED_WORDS.add("void");
        RESERVED_WORDS.add("if");
        RESERVED_WORDS.add("else");
        RESERVED_WORDS.add("for");
        RESERVED_WORDS.add("while");
        RESERVED_WORDS.add("return");
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
        code.current() == '\n'||
        code.current() == CharacterIterator.DONE;
    }
}
