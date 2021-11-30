
package partida;

import TDAs.ArrayList;

public class Jugador {
    
    private int puntos;
    private int vidas;
    private final ArrayList<String> palabras_encontradas;
    private int modificaciones;
    
    public Jugador() {
        palabras_encontradas = new ArrayList();
        vidas = 3;
        modificaciones = 2;
    }

    public int getPuntos() {
        return puntos;
    }

    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
        if(this.puntos < 0) this.puntos = 0;
    }

    public int getNumeroPalabrasEncontradas() {
        return palabras_encontradas.size();
    }

    public void agregarPalabra(String palabra) {
        if (!palabras_encontradas.contains(palabra)) 
            palabras_encontradas.addLast(palabra);
    }
    
    public void quitarVida(){
        vidas--;
    }
    
    public int getVidas(){
        return vidas;
    }
    
    public ArrayList<String> getPalabras(){
        return palabras_encontradas;
    }
    
    public int getModificaciones(){
        return modificaciones;
    }
    
    public void modifica(){
        modificaciones--;
    }
    
}
