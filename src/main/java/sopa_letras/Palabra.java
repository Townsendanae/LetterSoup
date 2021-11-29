
package sopa_letras;

import java.util.Stack;
import TDAs.ArrayList;
import java.util.Objects;
import partida.Jugador;
import partida.Partida;

public class Palabra {
    
    private final ArrayList<Letra> letras;
    private final Jugador jugador;
    
    public Palabra(ArrayList<Letra> letras, Jugador jugador){
        this.letras = letras;
        this.jugador = jugador;
    }
    
    public Intento comprobar(){
        int puntos = getPuntos();
        jugador.sumarPuntos(puntos);
        if(puntos > 0) return Intento.ACIERTO;
        if(puntos < 0){
            jugador.quitarVida();
            return Intento.ERROR;
        }
        return Intento.YA_ENCONTRADA;
    }
    
    private int getPuntos(){
        int puntos = letras.size();
        Intento intento = esValida(getOrientacion());
        switch(intento){
            case ACIERTO: return puntos;
            case ERROR: return -puntos;
            default:  return 0;
        }
    }
    
    private void ordenarLetras(){
        Stack<Letra> pila = new Stack();
        for(Letra letra: letras){
            if(pila.isEmpty()) pila.push(letra);
            else{
                Stack<Letra> pila_aux = new Stack();
                while(!pila.isEmpty() && pila.peek().compareTo(letra) < 0){
                    pila_aux.push(pila.pop());
                }
                pila.push(letra);
                while(!pila_aux.isEmpty()){
                    pila.push(pila_aux.pop());
                }
            }
        }
        
        letras.clear();
        while(!pila.isEmpty()){
            letras.addLast(pila.pop());
        }
    }
    
    private Orientacion getOrientacion(){
        ordenarLetras();
        
        int fila = letras.get(0).getFila();
        int columna = letras.get(0).getColumna();
        
        for(int i = 1; i < letras.size() && (fila > 0 || columna > 0); i++){
            if(fila != letras.get(i).getFila()) fila = -1;
            if(columna != letras.get(i).getColumna()) columna = -1;
        }
        
        if(fila > 0) return Orientacion.HORIZONTAL;
        if(columna > 0) return Orientacion.VERTICAL;
        
        fila = letras.get(0).getFila();
        
        for(int i = 1; i < letras.size() && fila > 0; i++){
            if(fila != letras.get(i).getFila()-1) fila = -100;
            fila++;
        }
        
        if(fila < 0) return Orientacion.OTRA;
        
        columna = letras.get(0).getColumna();
        
        for(int i = 1; i < letras.size(); i++){
            if(columna != letras.get(i).getColumna()+1) columna = -100;
            columna--;
        }
        
        if(columna > 0) return Orientacion.DIAGONAL;
        
        columna = letras.get(0).getColumna();
        
        for(int i = 1; i < letras.size(); i++){
            if(columna != letras.get(i).getColumna()-1) return Orientacion.OTRA;
            columna++;
        }
        
        return Orientacion.DIAGONAL;
    }
    
    
    private Intento esValida(Orientacion o){
        if(o == Orientacion.OTRA) return Intento.ERROR;
        else{
            StringBuilder palabra = new StringBuilder();
            for(Letra letra: letras){
                palabra.append(letra.toString());
            }
            Intento intento = comprobarPalabra(palabra.toString());
            if (intento == Intento.ERROR) return comprobarPalabra(palabra.reverse().toString());
            return intento;
        }
    }
        
    
    private Intento comprobarPalabra(String palabra){
        if(Partida.yaEncontrada(this) && Partida.encontradas_string.contains(palabra)) 
            return Intento.YA_ENCONTRADA;
        else if(Partida.validas.contains(palabra) || Partida.encontradas_string.contains(palabra)){
            
            jugador.agregarPalabra(this);
            Partida.agregarPalabra(this, palabra);
            Partida.actualizarPalabrasValidas();
            return Intento.ACIERTO;
        }
         
        return Intento.ERROR;
    }
    
    public ArrayList<Letra> getLetras(){
        return letras;
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(o == this) return true;
        if(o.getClass() == this.getClass()){
            Palabra p = (Palabra) o;
            if(p.getLetras().size() != letras.size()) return false;
            for(int i = 0; i < p.getLetras().size(); i++){
                if(!p.getLetras().contains(letras.get(i))) return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.letras);
        hash = 97 * hash + Objects.hashCode(this.jugador);
        return hash;
    }
}
