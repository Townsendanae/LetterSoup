package com.grupo3.proyectoprimerparcial;

import TDAs.ArrayList;
import TDAs.CircularDoublyLinkedList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import partida.Idiomas;
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
    private Timeline timer;

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
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnShuffle;
    @FXML
    private Label Lifes1;
    @FXML
    private Label LabelPalabras1;
    @FXML
    private Label Score1;
    @FXML
    private Label Lifes2;
    @FXML
    private Label LabelPalabras2;
    @FXML
    private Label Score2;
    @FXML
    private Button btnAddRow;
    @FXML
    private Button btnDelRow;
    @FXML
    private Button btnAddCol;
    @FXML
    private Button btnDelCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (Partida.idioma == Idiomas.ENGLISH) {
            btnMenu.setText("Menu");
            btnShuffle.setText("Shuffle");
            Lifes1.setText("LIFES : ");
            Lifes2.setText("LIFES : ");
            Score1.setText("SCORE : ");
            Score2.setText("SCORE : ");
            LabelPalabras1.setText("WORDS : ");
            LabelPalabras2.setText("WORDS : ");
            btnAddRow.setText("Add Row");
            btnAddCol.setText("Add Column");
            btnDelRow.setText("Delete Row");
            btnDelCol.setText("Delete Column");
            btnEnviar.setText("Send");
        }

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
            segundosJugadorUno = 90;
            if (Partida.jugadorDos != null) {
                segundosJugadorDos = 90;
            }

            timerPlayerOne.setText("01:30");
            if (Partida.jugadorDos != null) {
                timerPlayerTwo.setText("01:30");
            }

            timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> actualizarTimers()));
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

            int n_fila = y + 1;

            //creación flechas para mover filas
            StackPane leftArrow = crearFlechaFila(height, tamaño_letra, n_fila, "⮜", true);
            leftVBox.getChildren().add(leftArrow);

            StackPane rightArrow = crearFlechaFila(height, tamaño_letra, n_fila, "⮞", false);
            rightVBox.getChildren().add(rightArrow);

            // creación casillas con letras
            for (int x = 0; x < fila.size(); x++) {

                Letra l = fila.get(x); //Obtengo mi elemento por columna

                StackPane pane = crearCasilla(width, height, tamaño_letra, l);

                gridpane.add(pane, x, y); //Agrego al gridpane el contener en la posicion X,Y

            }
        }

        // Creación flechas para mover Columnas
        for (int i = 0; i < sopaDeLetras.getN_columnas(); i++) {

            int n_columna = i + 1;

            StackPane down_arrow = crearFlechaColumna(width, tamaño_letra, n_columna, "⮟", false);
            bottomHBox.getChildren().add(down_arrow);

            StackPane up_arrow = crearFlechaColumna(width, tamaño_letra, n_columna, "⮝", true);
            topHBox.getChildren().add(up_arrow);
        }

        panelCentral.getChildren().add(gridpane);

    }

    private StackPane crearCasilla(double width, double height, double tamaño_letra, Letra l) {

        Character c = l.getLetra(); //Extraigo el valor del objeto letra
        //creación letras
        StackPane pane = new StackPane();
        pane.setPrefSize(width, height);
        pane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
        pane.setCursor(Cursor.HAND);

        Label letra = new Label(String.valueOf(c)); //Lo contengo en un label para mostrar
        letra.setMouseTransparent(true);

        letra.setStyle("-fx-font-family: 'Tahoma'; -fx-font-size: " + tamaño_letra + "px;");

        Pane fondo = new Pane();
        fondo.setStyle("-fx-background-color: #5169FF;");
        fondo.setMouseTransparent(true);
        fondo.setOpacity(0.20 * l.getUsos());
        if (l.getUsos() == 5) {
            pane.setDisable(true);
            letra.setVisible(false);
        }

        pane.getChildren().add(fondo);
        pane.getChildren().add(letra);
        StackPane.setAlignment(letra, Pos.CENTER);

        pane.setOnMouseClicked(t -> seleccionarLetra(fondo, l));

        pane.setOnMouseEntered(e -> mouseEnteredLetter(pane, l));
        pane.setOnMouseExited(e -> mouseExitedLetter(pane, l));
        pane.setOnMousePressed(e -> mouseExitedLetter(pane, l));
        pane.setOnMouseReleased(e -> mouseEnteredLetter(pane, l));

        return pane;

    }

    private StackPane crearFlechaColumna(double width, double tamaño_letra, int n_columna, String flecha, boolean up) {

        StackPane pane = new StackPane();
        pane.setPrefSize(width, 30);
        pane.setCursor(Cursor.HAND);
        pane.setOpacity(0);

        Label arrow = new Label(flecha);
        arrow.setStyle("-fx-font-size: " + tamaño_letra * 0.8 + "px;");
        arrow.setMouseTransparent(true);

        pane.getChildren().add(arrow);
        StackPane.setAlignment(arrow, Pos.CENTER);

        pane.setOnMouseEntered(e -> mouseEnteredArrow(pane));
        pane.setOnMouseExited(e -> mouseExitedArrow(pane));

        if (up) {
            pane.setOnMouseClicked(e -> moveColumnBackwards(n_columna));
        } else {
            pane.setOnMouseClicked(e -> moveColumnForward(n_columna));
        }

        return pane;
    }

    private StackPane crearFlechaFila(double height, double tamaño_letra, int n_fila, String flecha, boolean left) {
        StackPane pane = new StackPane();
        pane.setPrefHeight(height);
        pane.setCursor(Cursor.HAND);
        pane.setOpacity(0);

        Label arrow = new Label(flecha);
        arrow.setStyle("-fx-font-size: " + tamaño_letra + "px;");
        arrow.setMouseTransparent(true);

        pane.getChildren().add(arrow);
        StackPane.setAlignment(arrow, Pos.CENTER_RIGHT);

        pane.setOnMouseEntered(e -> mouseEnteredArrow(pane));
        pane.setOnMouseExited(e -> mouseExitedArrow(pane));

        if (left) {
            pane.setOnMouseClicked(e -> moveRowBackwards(n_fila));
        } else {
            pane.setOnMouseClicked(e -> moveRowForward(n_fila));
        }

        return pane;

    }

    private void mouseEnteredLetter(StackPane p, Letra l) {

        int n_fila = l.getFila() - 1;
        int n_columna = l.getColumna() - 1;

        if (modoEliminarColumna || modoAgregarColumna) {
            gridpane.getChildren().stream()
                    .filter((Node n) -> (GridPane.getColumnIndex(n) == n_columna))
                    .forEach((Node n) -> n.setStyle(n.getStyle() + " -fx-background-color: #BFE1FF;"));
        } else if (modoEliminarFila || modoAgregarFila) {
            gridpane.getChildren().stream()
                    .filter((Node n) -> (GridPane.getRowIndex(n) == n_fila))
                    .forEach((Node n) -> n.setStyle(n.getStyle() + " -fx-background-color: #BFE1FF;"));
        } else {
            p.setStyle(p.getStyle() + " -fx-background-color: #BFE1FF;");
        }
    }

    private void mouseExitedLetter(StackPane p, Letra l) {

        int n_fila = l.getFila() - 1;
        int n_columna = l.getColumna() - 1;

        if (modoEliminarColumna || modoAgregarColumna) {
            gridpane.getChildren().stream()
                    .filter((Node n) -> (GridPane.getColumnIndex(n) == n_columna))
                    .forEach((Node n) -> n.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;"));
        } else if (modoEliminarFila || modoAgregarFila) {
            gridpane.getChildren().stream()
                    .filter((Node n) -> (GridPane.getRowIndex(n) == n_fila))
                    .forEach((Node n) -> n.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;"));
        } else {
            p.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
        }
    }

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

    public void seleccionarLetra(Pane fondo, Letra l) {
        //Extensión para Modificaciones

        if (modoAgregarFila || modoAgregarColumna || modoEliminarFila || modoEliminarColumna) {
            modificacion(l);

        } else {

            // Implementacion del click
            if (letras.isEmpty()) { // No se ha seleccionado ninguna palabra, es la primera letra
                letras.addLast(l);
                fondo.setOpacity(fondo.getOpacity() + 0.20);
                fondos.addLast(fondo);

            } else if (!letras.contains(l)) { // comprobar que no haya sido agregada en el mismo intento

                // comprobar si esta en los costados
                int size = letras.size();
                Letra previous = letras.get(size - 1);
                int actualX = l.getColumna();
                int actualY = l.getFila();

                // comprobar si estan juntas
                if ((actualX + 1 == previous.getColumna() || previous.getColumna() == actualX - 1 || previous.getColumna() == actualX)
                        && (previous.getFila() == actualY + 1 || previous.getFila() == actualY - 1 || previous.getFila() == actualY)
                        && (actualX != previous.getColumna() || actualY != previous.getFila())) {
                    letras.addLast(l);

                    // Oscureciendo la casilla
                    fondo.setOpacity(fondo.getOpacity() + 0.20);
                    fondos.addLast(fondo);
                    }
            }

        }

    }

    private void modificacion(Letra l) {
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
        if (Partida.validas.size() == 0) {
            GameOver();
        }
    }

    private void refrescarSopa() {
        generarSopa();
        gridpane.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 1px;");
        letras = new ArrayList();
    }

    private void cambiarTurno() {
        //Solo realiza cambio de turno cuando a quien voy a cambiar aun posee vidas
        if (jugadorActual == Partida.jugadorUno && Partida.jugadorDos.getVidas() != 0) {
            jugadorActual = Partida.jugadorDos;
        } else if (jugadorActual == Partida.jugadorDos && Partida.jugadorUno.getVidas() != 0) {
            jugadorActual = Partida.jugadorUno;
        }

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

    private void actualizarTimers() {
        if (getTurno() == 1) {
            if (segundosJugadorUno > 60) {
                segundosJugadorUno--;
                timerPlayerOne.setText(String.format("0%d:%02d", segundosJugadorUno / 60, segundosJugadorUno - 60));
            } else if (segundosJugadorUno > 0) {
                segundosJugadorUno--;
                timerPlayerOne.setText(String.format("00:%02d", segundosJugadorUno));
            } else if (Partida.jugadorDos != null) {
                cambiarTurno();
            }
        } else {
            if (segundosJugadorDos > 60) {
                segundosJugadorDos--;
                timerPlayerTwo.setText(String.format("0%d:%02d", segundosJugadorDos / 60, segundosJugadorDos - 60));
            } else if (segundosJugadorDos > 0) {
                segundosJugadorDos--;
                timerPlayerTwo.setText(String.format("00:%02d", segundosJugadorDos));
            } else {
                cambiarTurno();
            }
        }

        if (segundosJugadorUno == 0 && segundosJugadorDos == 0) {
            GameOver();

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
            lblpalabras.setText(cantidadPalabras);
        }
    }

    private void casoPorTurno(int turno, Boolean bool) {
        switch (turno) {
            case 1:
                modificarLabel(jugadorActual, lblPuntosJ1, vidasJ1, lblPalabrasJ1, bool);
                break;
            case 2:
                modificarLabel(jugadorActual, lblPuntosJ2, vidasJ2, lblPalabrasJ2, bool);
                break;
        }
    }

    void AbrirVentana(Intento intento) {
        VBox rootNuevaVentana = new VBox();
        Label label = new Label();

        switch (intento) {
            case ERROR:
                if (Partida.idioma == Idiomas.SPANISH) label.setText("MmmMmmM ¿Ha escuchado sobre la RAE? porque esa palabra no existe");
                else label.setText("Have you heard about The Cambridge Dictionary? That word doesn't exist");
                break;
            case YA_ENCONTRADA:
                if (Partida.idioma == Idiomas.SPANISH) label.setText("Oiga, ya encontró esa, busque otra");
                else label.setText("Hey, you already found that one, look for another :)");
                break;
        }
        Button b = new Button("Ok");

        rootNuevaVentana.getChildren().addAll(label, b);
        rootNuevaVentana.setAlignment(Pos.CENTER);
        rootNuevaVentana.setSpacing(20);
        rootNuevaVentana.setPadding(new Insets(10, 15, 10, 15));
        Stage s = new Stage();
        Scene sce = new Scene(rootNuevaVentana);
        b.setOnAction(t -> s.close());
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
        if (letras.isEmpty()) {
            return;
        }
        Palabra p = new Palabra(letras, jugadorActual);       
        Intento intento = p.comprobar(); // comprobar inserta la palabra en las encontradas

        switch (intento) {
            case ERROR:               
                if (jugadorActual.getVidas() != 0) {
                    AbrirVentana(Intento.ERROR);
                }
                casoPorTurno(getTurno(), true);
                refrescarSopa();
                if (Partida.jugadorDos == null) {
                    if (jugadorActual.getVidas() == 0) {
                        GameOver();
                    }
                } else {
                    if (Partida.jugadorUno.getVidas() == 0 && Partida.jugadorDos.getVidas() == 0) {
                        GameOver();
                    }
                }

                break;
            case YA_ENCONTRADA:              
                AbrirVentana(Intento.YA_ENCONTRADA);
                refrescarSopa();
                break;
            case ACIERTO:               
                casoPorTurno(getTurno(), false);
                refrescarSopa();
                break;
        }
        if (Partida.jugadorDos != null) {
            cambiarTurno();
        }
    }

    private void GameOver() {
        if (Partida.xtreme) {
            timer.stop();
        }
        if (Partida.jugadorDos == null) {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("GAME OVER");
            a.setHeaderText(null);
            String mensaje = "Tu puntaje es: " + jugadorActual.getPuntos();
            if (Partida.apuesta > 0) {
                mensaje = (jugadorActual.getPuntos() < Partida.apuesta)
                        ? "PIERDES\n" + mensaje : "GANAS\n" + mensaje;
            }
            a.setContentText(mensaje);
            a.show();

            try {
                App.setRoot("MainMenu");
            } catch (IOException ex) {}
            
        } else {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("GAME OVER");
            a.setHeaderText(null);
            if (Partida.jugadorUno.getPuntos() > Partida.jugadorDos.getPuntos()) {
                a.setContentText("Gana el jugador 1\nPuntaje Jugador 1: " + Partida.jugadorUno.getPuntos()
                        + "\nPuntaje Jugador 2: " + Partida.jugadorDos.getPuntos());
                a.show();
            } else if (Partida.jugadorUno.getPuntos() < Partida.jugadorDos.getPuntos()) {
                a.setContentText("Gana el jugador 2\nPuntaje Jugador 1: " + Partida.jugadorUno.getPuntos()
                        + "\nPuntaje Jugador 2: " + Partida.jugadorDos.getPuntos());
                a.show();
            } else {
                a.setContentText("EMPATE TECNICO\nPuntaje Jugador 1: " + Partida.jugadorUno.getPuntos()
                        + "\nPuntaje Jugador 2: " + Partida.jugadorDos.getPuntos());
                a.show();
            }

            try {
                App.setRoot("MainMenu");
            } catch (IOException ex) {}

        }
    }

}
