package Compiller.Lexical;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Code {

    private String path;
    private String codeString;
    
    public Code(String path) {
        this.path = path;
        this.codeString = getCodeString();
    }
    
    public String getCodeString() {
        try (Scanner scanner = new Scanner(new File(path))) {
            String codeAux = "";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                codeAux += codeAux == "" ? line : '\n' + line;
            }
            // return codeAux.substring(0, codeAux.length() - 1)
            return codeAux;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countLines(){
        int contador = 1;
        
        for (int i = 0; i < codeString.length(); i++) {
            if (codeString.charAt(i) == '\n') {
                contador++;
            }
        }
        return contador;
    }
            
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
   
}

