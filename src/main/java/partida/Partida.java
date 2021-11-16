
package partida;

import TDAs.ArrayList;
import sopa_letras.Palabra;
import sopa_letras.Sopa;

public abstract class Partida {
    
    public static Jugador jugadorUno;
    public static Jugador jugadorDos;
    public static Idiomas idioma;
    public static Sopa sopa;
    public static ArrayList<Palabra> encontradas = new ArrayList();
    
    public static void nuevaPartidaUnJugador(Idiomas i, int n_filas, int n_columnas){
        idioma = i;
        jugadorUno = new Jugador();
        sopa = new Sopa(n_filas, n_columnas);
        
    }
    
    public static void nuevaPartidaDosJugadores(Idiomas i, int n_filas, int n_columnas){
        idioma = i;
        jugadorUno = new Jugador();
        jugadorDos = new Jugador();
        sopa = new Sopa(n_filas, n_columnas);
        
    }
    
    public static boolean yaEncontrada(Palabra palabra){
        return encontradas.contains(palabra);
    }
    
    public static void agregarPalabra(Palabra palabra) {
        encontradas.addLast(palabra);
    }
    
}
