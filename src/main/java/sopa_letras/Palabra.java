package sopa_letras;

import TDAs.ArrayList;
import java.util.Objects;
import partida.Jugador;
import partida.Partida;

public class Palabra {

    private final ArrayList<Letra> letras;
    private final Jugador jugador;

    public Palabra(ArrayList<Letra> letras, Jugador jugador) {
        this.letras = letras;
        this.jugador = jugador;
    }

    public Intento comprobar() {
        
        StringBuilder palabra = new StringBuilder();
        for (Letra letra : letras) {
            palabra.append(letra.toString());
        }
        
        Intento intento = comprobarPalabra(palabra.toString());
        if (intento == Intento.ERROR) {
            intento = comprobarPalabra(palabra.reverse().toString());
        }
        
        int puntos = getPuntos(intento);
        
        jugador.sumarPuntos(puntos);
        if (puntos > 0) {
            return Intento.ACIERTO;
        }
        if (puntos < 0) {
            jugador.quitarVida();
            return Intento.ERROR;
        }
        return Intento.YA_ENCONTRADA;
    }

    private int getPuntos(Intento intento) {
        int puntos = letras.size();
        
        switch (intento) {
            case ACIERTO:
                return puntos;
            case ERROR:
                return -puntos;
            default:
                return 0;
        }
    }

    private Intento comprobarPalabra(String palabra) {
        if (Partida.yaEncontrada(this) && Partida.encontradas_string.contains(palabra)) {
            return Intento.YA_ENCONTRADA;
        } else if (Partida.validas.contains(palabra) || Partida.encontradas_string.contains(palabra)) {

            jugador.agregarPalabra(palabra);
            Partida.agregarPalabra(this, palabra);
            Partida.actualizarPalabrasValidas();
            
            for(Letra letra: letras){
                letra.usar();
            }
            
            return Intento.ACIERTO;
        }

        return Intento.ERROR;
    }

    public ArrayList<Letra> getLetras() {
        return letras;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.letras);
        hash = 97 * hash + Objects.hashCode(this.jugador);
        return hash;
    }
}
