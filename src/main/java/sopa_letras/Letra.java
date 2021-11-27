
package sopa_letras;

public class Letra implements Comparable<Letra>{
    
    private char letra;
    private int columna;
    private int fila;
    private int usos;

    public Letra(char letra, int fila, int columna) {
        this.letra = letra;
        this.columna = columna;
        this.fila = fila;
        this.usos = 0;
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
    
    public int getUsos(){
        return usos;
    }
    
    public void usar(){
        usos++;
    }
    
    @Override
    public String toString(){
        return Character.toString(letra);
    }

    @Override
    public int compareTo(Letra letra) {
        if(fila != letra.getFila()) return fila-letra.getFila();
        else return columna-letra.getColumna();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.letra;
        hash = 79 * hash + this.columna;
        hash = 79 * hash + this.fila;
        return hash;
    }
    
}
