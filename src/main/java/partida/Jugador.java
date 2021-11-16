
package partida;

import TDAs.ArrayList;
import sopa_letras.Palabra;

public class Jugador {
    
    private int puntos;
    private int vidas;
    private final ArrayList<Palabra> palabras_encontradas;
    
    public Jugador() {
        palabras_encontradas = new ArrayList();
        vidas = 3;
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

    public void agregarPalabra(Palabra palabra) {
        palabras_encontradas.addLast(palabra);
    }
    
    public void quitarVida(){
        vidas--;
    }
    
    public int getVidas(){
        return vidas;
    }
    
}
