
package partida;

import TDAs.ArrayList;
import TDAs.CircularDoublyLinkedList;
import TDAs.DoublyLinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import sopa_letras.Letra;
import sopa_letras.Palabra;
import sopa_letras.Sopa;

public abstract class Partida {
    
    public static Jugador jugadorUno;
    public static Jugador jugadorDos;
    public static Idiomas idioma = Idiomas.SPANISH;
    public static Sopa sopa;
    public static DoublyLinkedList<Palabra> encontradas = new DoublyLinkedList();
    public static DoublyLinkedList<String> encontradas_string = new DoublyLinkedList();
    public static ArrayList<String> validas;
    public static int apuesta;
    public static boolean xtreme;
    
    private static final Comparator<Palabra> cmp = (p1, p2) -> {
    
        if(p1.getLetras().size() > p2.getLetras().size()) return 1;
        if(p1.getLetras().size() < p2.getLetras().size()) return -1;
        
        for(int i = 0; i < p1.getLetras().size(); i++){
                if(!p1.getLetras().contains(p2.getLetras().get(i))) return -2;
            }
            return 0;
    
    };
    
    public static void nuevaPartidaUnJugador(int n_filas, int n_columnas, int bet, boolean xtreme_mode){
        encontradas.clear();
        encontradas_string.clear();
        jugadorUno = new Jugador();
        jugadorDos = null;
        sopa = new Sopa(n_filas, n_columnas);
        apuesta = bet;
        xtreme = xtreme_mode;
        validas = new ArrayList((idioma == Idiomas.SPANISH)?50000:2000);
        actualizarPalabrasValidas();
    }
    
    public static void nuevaPartidaDosJugadores(int n_filas, int n_columnas, int bet, boolean xtreme_mode){
        nuevaPartidaUnJugador(n_filas, n_columnas, bet, xtreme_mode);
        jugadorDos = new Jugador();
    }
    
    public static boolean yaEncontrada(Palabra palabra){
        return encontradas.contains(palabra, cmp);
    }
    
    public static void agregarPalabra(Palabra palabra, String s_palabra) {
        encontradas.addLast(palabra);
        encontradas_string.addLast(s_palabra);
    }
    
    /**
     * Este método actualiza la lista de palabras que es posible encontrar 
     * dentro la sopa, tomando en cuenta los movimientos que puede hacer
     * el jugador.
     */
    public static void actualizarPalabrasValidas(){
        
        validas.clear();
        Map<Character, Integer> usos = new HashMap();
        
        for(CircularDoublyLinkedList<Letra> fila: sopa.getSopa()){
            for(Letra letra : fila){
                Character c = letra.getLetra();
                if(!usos.containsKey(c)){
                    usos.put(c, 5-letra.getUsos());
                }else{
                    usos.put(c, usos.get(c)+(5-letra.getUsos()));
                }
            }
        }
        Charset inputCharset = Charset.forName("UTF-8");
        try(BufferedReader bf = new BufferedReader(new FileReader(idioma.ruta, inputCharset))){
             
            String palabra;
            while((palabra = bf.readLine()) != null){
                
                palabra = palabra.strip();
                boolean valida = true;
                
                if(palabra.length() == 1) continue;
                
                if(idioma == Idiomas.SPANISH){
                    palabra = palabra.replace("á","a");
                    palabra = palabra.replace("é","e");
                    palabra = palabra.replace("í","i");
                    palabra = palabra.replace("ó","o");
                    palabra = palabra.replace("ú","u");
                }
                
                palabra = palabra.toUpperCase();
                
                Map<Character, Integer> letras = new HashMap();
                for(int i = 0; i < palabra.length() && valida; i++){
                    
                    Character c = palabra.charAt(i);
                    
                    if(!usos.containsKey(c)) valida = false;
                    
                    if(!letras.containsKey(c)) letras.put(c, 1);
                    else{
                        letras.put(c, letras.get(c)+1);
                        if(letras.get(c) > usos.get(c)) valida = false;
                    }
                }
                
                if(valida && !encontradas_string.contains(palabra)){
                    validas.addLast(palabra);
                }
            }
            
        }catch(Exception e){}
        System.out.println(validas.size());
    }
}
