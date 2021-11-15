
package sopa_letras;

public class Letra implements Comparable<Letra>{
    
    private char letra;
    private int columna;
    private int fila;

    public Letra(char letra, int fila, int columna) {
        this.letra = letra;
        this.columna = columna;
        this.fila = fila;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    public int getColumna() {
        return columna;
    }

    public int getFila() {
        return fila;
    }

    public void actualizarPosicion(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
    }
    
    @Override
    public String toString(){
        return Character.toString(letra)+" "+fila+" "+columna;
    }

    @Override
    public int compareTo(Letra letra) {
        if(fila != letra.getFila()) return fila-letra.getFila();
        else return columna-letra.getColumna();
    }
    
}
