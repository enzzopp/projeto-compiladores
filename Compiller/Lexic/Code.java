package Compiller.Lexic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Code {

    private String path;
    private String codeString;
    
    public Code(String path) {
        this.path = path;
        this.codeString = getCodeString();
    }
    
    public String getCodeString() {
        try (Scanner scanner = new Scanner(new InputStreamReader(new FileInputStream(new File(path)), StandardCharsets.UTF_8))) {
            StringBuilder codeAux = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (codeAux.length() > 0) {
                    codeAux.append('\n');
                }
                codeAux.append(line);
            }
            return codeAux.toString();
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

