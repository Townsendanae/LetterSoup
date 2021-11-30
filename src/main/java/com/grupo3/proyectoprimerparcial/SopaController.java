package com.grupo3.proyectoprimerparcial;

import TDAs.ArrayList;
import TDAs.CircularDoublyLinkedList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import partida.Jugador;
import partida.Partida;
import sopa_letras.Intento;
import sopa_letras.Letra;
import sopa_letras.Palabra;
import sopa_letras.Sopa;

public class SopaController implements Initializable {

    @FXML
    private AnchorPane panelCentral;

    private GridPane gridpane;
    private Jugador jugadorActual;
    private int segundosJugadorUno;
    private int segundosJugadorDos;

    private boolean modoAgregarFila;
    private boolean modoEliminarFila;
    private boolean modoAgregarColumna;
    private boolean modoEliminarColumna;
    ArrayList<Letra> letras = new ArrayList<>();
    ArrayList<Pane> fondos = new ArrayList<>();

    @FXML
    private VBox leftVBox;
    @FXML
    private VBox rightVBox;
    @FXML
    private VBox playerTwoPane;
    @FXML
    private HBox vidasJ1;
    @FXML
    private HBox vidasJ2;
    @FXML
    private HBox topHBox;
    @FXML
    private HBox bottomHBox;
    @FXML
    private HBox buttons;
    @FXML
    private VBox playerOnePane;
    @FXML
    private Label timerPlayerOne;
    @FXML
    private Label timerPlayerTwo;
    @FXML
    private Button btnEnviar;
    @FXML
    private Label lblPalabrasJ1;
    @FXML
    private Label lblPalabrasJ2;
    @FXML
    private Label lblPuntosJ1;
    @FXML
    private Label lblPuntosJ2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        generarSopa();
        gridpane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
        if (Partida.jugadorDos == null) {
            playerTwoPane.setVisible(false);
        } else {
            playerOnePane.setStyle("-fx-background-color: linear-gradient(from 0% 50% to 100% 50%, #65C0FF, white);");
            playerTwoPane.setOpacity(0.5);
        }
        jugadorActual = Partida.jugadorUno;

        vidasJ1.getChildren().forEach((n) -> n.setVisible(true));
        vidasJ2.getChildren().forEach((n) -> n.setVisible(true));

        timerPlayerOne.setVisible(Partida.xtreme);
        timerPlayerTwo.setVisible(Partida.xtreme);

        if (Partida.xtreme) {
            // Iniciando Timers
            segundosJugadorUno = segundosJugadorDos = 90;

            timerPlayerOne.setText("01:30");
            timerPlayerTwo.setText("01:30");

            Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> actualizarTimers()));
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();
        }

    }

    public void generarSopa() {
        //Limpio la Sopa Anterior
        panelCentral.getChildren().clear();
        leftVBox.getChildren().clear();
        rightVBox.getChildren().clear();
        topHBox.getChildren().clear();
        bottomHBox.getChildren().clear();

        //Creo mi gridpane que muestra mi sopa de letras
        gridpane = new GridPane();

        //Obtengo mi sopa de letras que lleva el control por detras del javafx
        Sopa sopaDeLetras = Partida.sopa;

        //tamaños dinámicos
        double width = 500 / sopaDeLetras.getN_columnas();
        double height = 500 / sopaDeLetras.getN_filas();
        double tamaño_letra = (width >= height) ? height / 2 : width / 2;

        //Recorro mi sopa por fila y columna, uso for porque es esencial guardar las coordenadas (x,y) para el gridpana
        for (int y = 0; y < sopaDeLetras.getSopa().size(); y++) {

            CircularDoublyLinkedList<Letra> fila = sopaDeLetras.getSopa().get(y); //Obtengo mi lista que hace de fila

            //creación flechas para mover filas
            StackPane pane_2 = new StackPane();
            pane_2.setPrefHeight(height);
            pane_2.setCursor(Cursor.HAND);
            pane_2.setOpacity(0);
            pane_2.setUserData(y + 1);

            Label flecha_izq = new Label("⮜");
            flecha_izq.setStyle("-fx-font-size: " + tamaño_letra + "px;");
            flecha_izq.setMouseTransparent(true);

            pane_2.getChildren().add(flecha_izq);
            StackPane.setAlignment(flecha_izq, Pos.CENTER_RIGHT);

            leftVBox.getChildren().add(pane_2);

            int n_fila = y + 1;

            pane_2.setOnMouseEntered(e -> mouseEnteredArrow(pane_2));
            pane_2.setOnMouseExited(e -> mouseExitedArrow(pane_2));
            pane_2.setOnMouseClicked(e -> moveRowBackwards(n_fila));

            StackPane pane_3 = new StackPane();
            pane_3.setPrefHeight(height);
            pane_3.setCursor(Cursor.HAND);
            pane_3.setOpacity(0);
            pane_3.setUserData(y + 1);

            Label flecha_der = new Label("⮞");
            flecha_der.setStyle("-fx-font-size: " + tamaño_letra + "px;");
            flecha_der.setMouseTransparent(true);

            pane_3.getChildren().add(flecha_der);
            StackPane.setAlignment(flecha_der, Pos.CENTER_RIGHT);

            rightVBox.getChildren().add(pane_3);

            pane_3.setOnMouseEntered(e -> mouseEnteredArrow(pane_3));
            pane_3.setOnMouseExited(e -> mouseExitedArrow(pane_3));
            pane_3.setOnMouseClicked(e -> moveRowForward(n_fila));

            for (int x = 0; x < fila.size(); x++) {
                int coordX = x;
                int coordY = y;
                Letra l = fila.get(x); //Obtengo mi elemento por columna
                Character c = l.getLetra(); //Extraigo el valor del objeto letra

                //creación letras
                StackPane pane = new StackPane();
                pane.setPrefSize(width, height);
                pane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
                pane.setCursor(Cursor.HAND);
                pane.setUserData(l);

                Label contenedor = new Label(String.valueOf(c)); //Lo contengo en un label para mostrar
                contenedor.setMouseTransparent(true);

                contenedor.setStyle("-fx-font-family: 'Tahoma'; -fx-font-size: " + tamaño_letra + "px;");

                Pane fondo = new Pane();
                fondo.setStyle("-fx-background-color: #3999FF;");
                fondo.setMouseTransparent(true);
                fondo.setOpacity(0.25 * l.getUsos());

                pane.getChildren().add(fondo);
                pane.getChildren().add(contenedor);
                StackPane.setAlignment(contenedor, Pos.CENTER);

                pane.setOnMouseClicked(t -> seleccionarLetra(fondo, l, coordX, coordY));

                pane.setOnMouseEntered(mouseEnteredLetter);
                pane.setOnMouseExited(mouseExitedLetter);
                pane.setOnMousePressed(mouseExitedLetter);
                pane.setOnMouseReleased(mouseEnteredLetter);

                gridpane.add(pane, x, y); //Agrego al gridpane el contener en la posicion X,Y

            }

        }

        // Creación flechas para mover Columnas
        for (int i = 0; i < sopaDeLetras.getN_columnas(); i++) {

            StackPane pane_4 = new StackPane();
            pane_4.setPrefSize(width, 30);
            pane_4.setCursor(Cursor.HAND);
            pane_4.setOpacity(0);
            pane_4.setUserData(i + 1);

            Label flecha_aba = new Label("⮟");
            flecha_aba.setStyle("-fx-font-size: " + tamaño_letra * 0.8 + "px;");
            flecha_aba.setMouseTransparent(true);

            pane_4.getChildren().add(flecha_aba);
            StackPane.setAlignment(flecha_aba, Pos.CENTER);

            bottomHBox.getChildren().add(pane_4);

            int n_columna = i + 1;

            pane_4.setOnMouseEntered(e -> mouseEnteredArrow(pane_4));
            pane_4.setOnMouseExited(e -> mouseExitedArrow(pane_4));
            pane_4.setOnMouseClicked(e -> moveColumnForward(n_columna));

            StackPane pane_5 = new StackPane();
            pane_5.setPrefSize(width, 30);
            pane_5.setCursor(Cursor.HAND);
            pane_5.setOpacity(0);
            pane_5.setUserData(i + 1);

            Label flecha_arr = new Label("⮝");
            flecha_arr.setStyle("-fx-font-size: " + tamaño_letra * 0.8 + "px;");
            flecha_arr.setMouseTransparent(true);

            pane_5.getChildren().add(flecha_arr);
            StackPane.setAlignment(flecha_arr, Pos.CENTER);

            topHBox.getChildren().add(pane_5);

            pane_5.setOnMouseEntered(e -> mouseEnteredArrow(pane_5));
            pane_5.setOnMouseExited(e -> mouseExitedArrow(pane_5));
            pane_5.setOnMouseClicked(e -> moveColumnBackwards(n_columna));
        }

        panelCentral.getChildren().add(gridpane);

    }

    // EVENT HANDLERS
    EventHandler<MouseEvent> mouseEnteredLetter = (event) -> {

        StackPane p = (StackPane) event.getTarget();
        Letra l = (Letra) p.getUserData();
        int n_fila = l.getFila();
        int n_columna = l.getColumna();

        if (modoEliminarColumna || modoAgregarColumna) {
            gridpane.getChildren().stream()
                    .filter((Node n) -> ((Letra) n.getUserData()).getColumna() == n_columna)
                    .forEach((Node n) -> n.setStyle(n.getStyle() + " -fx-background-color: #BFE1FF;"));
        } else if (modoEliminarFila || modoAgregarFila) {
            gridpane.getChildren().stream()
                    .filter((Node n) -> ((Letra) n.getUserData()).getFila() == n_fila)
                    .forEach((Node n) -> n.setStyle(n.getStyle() + " -fx-background-color: #BFE1FF;"));
        } else {
            p.setStyle(p.getStyle() + " -fx-background-color: #BFE1FF;");
        }
        event.consume();
    };

    EventHandler<MouseEvent> mouseExitedLetter = (event) -> {
        StackPane p = (StackPane) event.getTarget();
        Letra l = (Letra) p.getUserData();
        int n_fila = l.getFila();
        int n_columna = l.getColumna();

        if (modoEliminarColumna || modoAgregarColumna) {
            gridpane.getChildren().stream()
                    .filter((Node n) -> ((Letra) n.getUserData()).getColumna() == n_columna)
                    .forEach((Node n) -> n.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;"));
        } else if (modoEliminarFila || modoAgregarFila) {
            gridpane.getChildren().stream()
                    .filter((Node n) -> ((Letra) n.getUserData()).getFila() == n_fila)
                    .forEach((Node n) -> n.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;"));
        } else {
            p.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
        }

        event.consume();
    };

    private void mouseEnteredArrow(StackPane p) {
        FadeTransition f = new FadeTransition(Duration.millis(250), p);
        f.setFromValue(p.getOpacity());
        f.setToValue(1);
        f.play();
    }

    private void mouseExitedArrow(StackPane p) {
        FadeTransition f = new FadeTransition(Duration.millis(250), p);
        f.setFromValue(p.getOpacity());
        f.setToValue(0);
        f.play();
    }

    private void moveRowForward(int n_fila) {
        Partida.sopa.avanzarFila(n_fila);
        refrescarSopa();
    }

    private void moveRowBackwards(int n_fila) {
        Partida.sopa.retrocederFila(n_fila);
        refrescarSopa();
    }

    private void moveColumnForward(int n_columna) {
        Partida.sopa.avanzarColumna(n_columna);
        refrescarSopa();
    }

    private void moveColumnBackwards(int n_columna) {
        Partida.sopa.retrocederColumna(n_columna);
        refrescarSopa();
    }

    public void seleccionarLetra(Pane fondo, Letra l, int posicionX, int posicionY) {
        //Extensión para Modificaciones

        if (modoAgregarFila || modoAgregarColumna || modoEliminarFila || modoEliminarColumna) {
            if (modoAgregarFila) {
                Partida.sopa.insertarFila(l.getFila());
                modoAgregarFila = false;
            } else if (modoAgregarColumna) {
                Partida.sopa.insertarColumna(l.getColumna());
                modoAgregarColumna = false;
            } else if (modoEliminarFila) {
                Partida.sopa.eliminarFila(l.getFila());
                modoEliminarFila = false;
            } else {
                Partida.sopa.eliminarColumna(l.getColumna());
                modoEliminarColumna = false;
            }

            jugadorActual.modifica();
            comprobarModificaciones();

            refrescarSopa();
        } else {

            //TODO Implementacion del click (Donaí)
            if (letras.isEmpty()) { // No se ha seleccionado ninguna palabra, es la primera letra
                letras.addLast(l);
                fondo.setOpacity(fondo.getOpacity() + 0.20);
                fondos.addLast(fondo);
                System.out.println(letras);
            } else { // comprobar si esta en los costados. 
                int size = letras.size();
                Letra previous = letras.get(size - 1);
                int actualX = l.getColumna();
                int actualY = l.getFila();

                //comprobar si estan juntas.^w^
                if ((actualX + 1 == previous.getColumna() || previous.getColumna() == actualX - 1 || previous.getColumna() == actualX)
                        && (previous.getFila() == actualY + 1 || previous.getFila() == actualY - 1 || previous.getFila() == actualY)
                        && (actualX != previous.getColumna() || actualY != previous.getFila())) {
                    letras.addLast(l);
                    // Oscureciendo la casilla
                    fondo.setOpacity(fondo.getOpacity() + 0.20);
                    fondos.addLast(fondo);
                    System.out.println(letras);
                } else {
                    System.out.println("La letra " + l + "Debe estar junto a su palabra");
                }
            }

        }

    }

    private void refrescarSopa() {
        generarSopa();
        gridpane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
    }

    private void cambiarTurno() {
        jugadorActual = ((jugadorActual == Partida.jugadorUno) ? Partida.jugadorDos : Partida.jugadorUno);

        if (jugadorActual == Partida.jugadorUno) {
            playerOnePane.setStyle("-fx-background-color: linear-gradient(from 0% 50% to 100% 50%, #65C0FF, white);");
            playerTwoPane.setStyle("");

            playerTwoPane.setOpacity(0.5);
            playerOnePane.setOpacity(1);
        } else {
            playerTwoPane.setStyle("-fx-background-color: linear-gradient(from 0% 50% to 100% 50%, white, #FF6D6D);");
            playerOnePane.setStyle("");

            playerOnePane.setOpacity(0.5);
            playerTwoPane.setOpacity(1);
        }

        comprobarModificaciones();
    }

    private void comprobarModificaciones() {
        if (jugadorActual.getModificaciones() == 0) {
            buttons.setDisable(true);
        } else {
            buttons.setDisable(false);
        }
    }

    private void quitarVidaJugadorActual() {
        if (jugadorActual == Partida.jugadorUno) {
            vidasJ1.getChildren().remove(vidasJ1.getChildren().size() - 1);
        } else {
            vidasJ2.getChildren().remove(vidasJ2.getChildren().size() - 1);
        }
    }

    private void actualizarTimers() {
        if (jugadorActual == Partida.jugadorUno) {
            if (segundosJugadorUno > 60) {
                segundosJugadorUno--;
                timerPlayerOne.setText(String.format("0%d:%02d", segundosJugadorUno / 60, segundosJugadorUno - 60));
            } else {
                segundosJugadorUno--;
                timerPlayerOne.setText(String.format("00:%d", segundosJugadorUno));
            }
        } else {
            if (segundosJugadorDos > 60) {
                segundosJugadorDos--;
                timerPlayerTwo.setText(String.format("0%d:%02d", segundosJugadorDos / 60, segundosJugadorDos - 60));
            } else {
                segundosJugadorDos--;
                timerPlayerTwo.setText(String.format("00:%d", segundosJugadorDos));
            }
        }

        if (segundosJugadorUno == 0 || segundosJugadorDos == 0) {
            terminarJuego();
        }
    }

    private int getTurno() {

        if (jugadorActual == Partida.jugadorUno) {
            return 1;
        } else {
            return 2;
        }

    }

    private void modificarLabel(Jugador jugador, Label lblpuntos, HBox HBoxVidas, Label lblpalabras, Boolean quitarVida) {
        String puntos = "" + jugador.getPuntos();
        lblpuntos.setText(puntos);

        if (quitarVida && (HBoxVidas.getChildren() != null)) {
            HBoxVidas.getChildren().remove(0);
        } else {
            String cantidadPalabras = "" + jugador.getNumeroPalabrasEncontradas();
            Platform.runLater(() -> lblpalabras.setText(cantidadPalabras));
        }
    }

    private void casoPorTurno(int turno, Boolean bool) {
        switch (turno) {
            case 1:
                System.out.println("turno 1 ");
                modificarLabel(jugadorActual, lblPuntosJ1, vidasJ1, lblPalabrasJ1, bool);
                break;
            case 2:
                modificarLabel(jugadorActual, lblPuntosJ2, vidasJ2, lblPalabrasJ2, bool);
                break;
        }
    }
    
    void AbrirVentana(Intento intento){
        VBox rootNuevaVentana = new VBox();
        Label label = new Label();
        
        switch(intento){
            case ERROR:
                label.setText("MmmMmmM ¿Ha escuchado sobre la RAE? porque esa palabra no existe");
                break;
            case YA_ENCONTRADA:
                label.setText("Oiga, ya encontró esa, busque otra");
                break;           
        }
        Button b = new Button("Ok");
                              
        rootNuevaVentana.getChildren().addAll(label,b);
        rootNuevaVentana.setAlignment(Pos.CENTER);
        rootNuevaVentana.setSpacing(20);
        rootNuevaVentana.setPadding(new Insets(10,15,10,15));        
        Stage s = new Stage();  
        Scene sce = new Scene(rootNuevaVentana);
        b.setOnAction( t -> s.close() ); 
        s.setScene(sce);
        s.setTitle("Mensaje");
        s.show();
                
        
    }

    private void terminarJuego() {
        //TODO
    }

    @FXML
    private void exitToMenu(ActionEvent event) throws IOException {
        App.setRoot("MainMenu");
    }

    @FXML
    private void agregarFila(ActionEvent event) {
        modoAgregarFila = !modoAgregarFila;
        modoEliminarFila = modoAgregarColumna = modoEliminarColumna = false;
    }

    @FXML
    private void eliminarFila(ActionEvent event) {
        modoEliminarFila = !modoEliminarFila;
        modoAgregarFila = modoAgregarColumna = modoEliminarColumna = false;
    }

    @FXML
    private void agregarColumna(ActionEvent event) {
        modoAgregarColumna = !modoAgregarColumna;
        modoAgregarFila = modoEliminarFila = modoEliminarColumna = false;
    }

    @FXML
    private void eliminarColumna(ActionEvent event) {
        modoEliminarColumna = !modoEliminarColumna;
        modoEliminarFila = modoAgregarFila = modoAgregarColumna = false;
    }

    @FXML
    private void shuffle(ActionEvent event) {
        Partida.sopa.reorganizarLetras();
        refrescarSopa();
        event.consume();
    }

    @FXML
    private void enviarPalabra() {
        if(letras.isEmpty()) return;
        Palabra p = new Palabra(letras, jugadorActual);
        System.out.println(p);
        Intento intento = p.comprobar(); // comprobar inserta la palabra en las encontradas

        switch (intento) {
            case ERROR:
                System.out.println("MmmMmmM ¿Ha escuchado sobre la RAE? porque esa palabra no existe");
                AbrirVentana(Intento.ERROR);
                jugadorActual.quitarVida();
                casoPorTurno(getTurno(), true);
                for(Pane fondo: fondos){
                    fondo.setOpacity(0);
                }
                break;
            case YA_ENCONTRADA:
                System.out.println("Oiga, ya encontró esa, busque otra");
                AbrirVentana(Intento.YA_ENCONTRADA);
                break;
            case ACIERTO:
                System.out.println("¡Bien, ahora recopilemos los puntos!");
                casoPorTurno(getTurno(), false);
                break;
        }
        if (Partida.jugadorDos != null) {
            cambiarTurno();
        }
        letras.clear();
    }
}
