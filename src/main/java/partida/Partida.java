
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
    public static int apuesta;
    public static boolean xtreme;
    
    public static void nuevaPartidaUnJugador(Idiomas i, int n_filas, int n_columnas, int bet, boolean xtreme_mode){
        encontradas.clear();
        idioma = i;
        jugadorUno = new Jugador();
        jugadorDos = null;
        sopa = new Sopa(n_filas, n_columnas);
        apuesta = bet;
        xtreme = xtreme_mode;
    }
    
    public static void nuevaPartidaDosJugadores(Idiomas i, int n_filas, int n_columnas, int bet, boolean xtreme_mode){
        nuevaPartidaUnJugador(i, n_filas, n_columnas, bet, xtreme_mode);
        jugadorDos = new Jugador();
    }
    
    public static boolean yaEncontrada(Palabra palabra){
        return encontradas.contains(palabra);
    }
    
    public static void agregarPalabra(Palabra palabra) {
        encontradas.addLast(palabra);
    }
    
}
