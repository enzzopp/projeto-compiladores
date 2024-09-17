import java.io.File;
import java.io.FileNotFoundException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Scanner;

public class FileSettings {

    private String code = "";
    private CharacterIterator word;

    public FileSettings(String word) {
        this.word = new StringCharacterIterator(code);
    }

    public String getCode() {
        return code;
    }

    public void read(){
        
        String path = "code.txt";
        
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                code += line + '\n';
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int countLines(){
        int aux = 0;
        System.out.println();
        // while (word.current() != CharacterIterator.DONE){
        //     if (word.current() == '\n'){
        //         aux ++;
        //         word.next();
        //     }
        // }
        return aux;
    }
}

