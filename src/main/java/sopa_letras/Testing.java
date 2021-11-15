
package sopa_letras;

import TDAs.ArrayList;
import TDAs.CircularDoublyLinkedList;
import java.util.Iterator;
import partida.Jugador;

public class Testing {
    
    public static void main(String[] args){
        
        Sopa sopa = new Sopa(8,10);
        
        Iterator<CircularDoublyLinkedList> it = sopa.getSopa().iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
        
        sopa.avanzarFila(1);
        sopa.retrocederFila(8);
        System.out.println("");
        sopa.insertarFila(5);
        sopa.insertarColumna(5);
        sopa.insertarFila(5);
        sopa.eliminarColumna(2);
        
        Iterator<CircularDoublyLinkedList> it2 = sopa.getSopa().iterator();
        while(it2.hasNext()){
            System.out.println(it2.next());
        }
        
        Jugador jugador = new Jugador();
        Letra l1 = new Letra('A', 6, 1);
        Letra l2 = new Letra('L', 5, 2);
        Letra l3 = new Letra('O', 4, 3);
        Letra l4 = new Letra('B', 3, 4);
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
