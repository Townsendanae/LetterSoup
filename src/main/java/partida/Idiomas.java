
package partida;

public enum Idiomas {
    SPANISH("AABCDEEFGHIIJKLMNÑOOPQRSTUUVWXYZ", "resources/palabras.txt"), ENGLISH("AABCDEEFGHIIJKLMNOOPQRSTUUVWXYZ", "resources/words.txt");
    
    public final String chars;
    public final String ruta;
    
    private Idiomas(String chars, String ruta){
        this.chars = chars;
        this.ruta = ruta;
    }
    
}
