
package sopa_letras;

import TDAs.ArrayList;
import TDAs.CircularDoublyLinkedList;
import java.util.Iterator;
import partida.Idiomas;
import partida.Jugador;
import partida.Partida;

public class Testing {
    
    public static void main(String[] args){
        
        Partida.nuevaPartidaUnJugador(8, 8, 100, false);
        Partida.idioma = Idiomas.SPANISH;
        Partida.actualizarPalabrasValidas();
        
        Jugador jugador = Partida.jugadorUno;
        Letra l1 = new Letra('B', 1, 1);
        Letra l2 = new Letra('O', 2, 1);
        Letra l3 = new Letra('L', 3, 1);
        Letra l4 = new Letra('A', 5, 1);
        ArrayList<Letra> letras = new ArrayList();
        letras.addLast(l1);
        letras.addLast(l2);
        letras.addLast(l3);
        letras.addLast(l4);
        Palabra palabra = new Palabra(letras, jugador);
        palabra.comprobar();
        Palabra palabra2 = new Palabra(letras, jugador);
        palabra2.comprobar();
        
        System.out.println(jugador.getPuntos()+" "+
                jugador.getNumeroPalabrasEncontradas()+" "+
                jugador.getVidas());
        
        
    }
    
}
