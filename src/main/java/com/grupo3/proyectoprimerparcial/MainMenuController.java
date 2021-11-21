package com.grupo3.proyectoprimerparcial;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import partida.Idiomas;
import partida.Partida;

public class MainMenuController implements Initializable{
    
    @FXML
    private Label columnsLabel;
    @FXML
    private Label rowsLabel;
    @FXML
    private Pane minusRow;
    @FXML
    private Pane plusRow;
    @FXML
    private Pane minusColumn;
    @FXML
    private Pane plusColumn;
    @FXML
    private Pane previousLanguage;
    @FXML
    private Pane nextLanguage;
    @FXML
    private Pane goButton;
    @FXML
    private Pane language;
    @FXML
    private Pane singleplayer;
    @FXML
    private Pane multiplayer;
    
    private int jugadores = 1;
    private boolean xtreme = false;
    @FXML
    private Pane exit;
    @FXML
    private Pane minusBet;
    @FXML
    private Label betLabel;
    @FXML
    private Pane plusBet;
    @FXML
    private Pane timer;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Pane[] arr_panes = {minusRow, plusRow, minusColumn, plusColumn, previousLanguage, 
            nextLanguage, goButton, singleplayer, multiplayer, exit, minusBet, plusBet, timer};
        for(Pane pane: arr_panes){
            pane.setOnMouseEntered(mouseEnteredButton);
            pane.setOnMouseExited(mouseExitedButton);
            pane.setOnMousePressed(mouseExitedButton);
            pane.setOnMouseReleased(mouseEnteredButton);
        }
        cambiarIdioma();
    }

    EventHandler<MouseEvent> mouseEnteredButton = (event) -> {
        Pane p = (Pane) event.getTarget();
        p.setOpacity((p.getOpacity() == 0.25)?0:0.25);
        event.consume();
    };
    
    EventHandler<MouseEvent> mouseExitedButton = (event) -> {
        Pane p = (Pane) event.getTarget();
        if(p == timer) p.setOpacity((p.getOpacity() == 0)?0.25:0.85);
        else p.setOpacity((p.getOpacity() == 0.25)?0.85:0.25);
        
        event.consume();
    };

    @FXML
    private void minusRow(MouseEvent event) {
        int rows = Integer.parseInt(rowsLabel.getText());
        if(rows > 5) rowsLabel.setText(String.valueOf(--rows));
    }

    @FXML
    private void plusRow(MouseEvent event) {
        int rows = Integer.parseInt(rowsLabel.getText());
        if(rows < 20) rowsLabel.setText(String.valueOf(++rows));
    }

    @FXML
    private void minusColumn(MouseEvent event) {
        int columns = Integer.parseInt(columnsLabel.getText());
        if(columns > 5) columnsLabel.setText(String.valueOf(--columns));
    }

    @FXML
    private void plusColumn(MouseEvent event) {
        int columns = Integer.parseInt(columnsLabel.getText());
        if(columns < 20) columnsLabel.setText(String.valueOf(++columns));
    }

    @FXML
    private void previousLanguage(MouseEvent event) {
        cambiarIdioma();
    }

    @FXML
    private void nextLanguage(MouseEvent event) {
        cambiarIdioma();
    }
    
    private void cambiarIdioma(){
        language.getChildren().clear();
        if(language.getUserData() == Idiomas.SPANISH){
            ImageView imgview;
            try{
                InputStream input = new FileInputStream(new File("resources/images/english.png"));
                Image img = new Image(input);
                imgview = new ImageView(img);
                language.setUserData(Idiomas.ENGLISH);
                language.getChildren().add(imgview);
            }catch(Exception e){
                
            }
            
        }else{
            ImageView imgview;
            try{
                InputStream input = new FileInputStream(new File("resources/images/spanish.png"));
                Image img = new Image(input);
                imgview = new ImageView(img);
                language.setUserData(Idiomas.SPANISH);
                language.getChildren().add(imgview);
            }catch(Exception e){
                
            }
        }
    }

    @FXML
    private void singleplayer(MouseEvent event) {
        singleplayer.setOpacity(0);
        multiplayer.setOpacity(0.85);
        jugadores = 1;
    }

    @FXML
    private void multiplayer(MouseEvent event) {
        multiplayer.setOpacity(0);
        singleplayer.setOpacity(0.85);
        jugadores = 2;
    }
    
    @FXML
    private void empezarPartida(MouseEvent event) throws IOException {
        
        Idiomas idioma = (Idiomas) language.getUserData();
        int n_filas = Integer.parseInt(rowsLabel.getText());
        int n_columnas = Integer.parseInt(columnsLabel.getText());
        int apuesta = Integer.parseInt(betLabel.getText());
        
        if(jugadores == 1) Partida.nuevaPartidaUnJugador(idioma, n_filas, n_columnas, apuesta, xtreme);
        else Partida.nuevaPartidaDosJugadores(idioma, n_filas, n_columnas, apuesta, xtreme);
        
        App.setRoot("Sopa");
        
    }

    @FXML
    private void salir(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void minusBet(MouseEvent event) {
        int apuesta = Integer.parseInt(betLabel.getText());
        if(apuesta > 100) betLabel.setText(String.valueOf(apuesta-100));
        if(apuesta == 999) betLabel.setText(String.valueOf(apuesta-99));
    }

    @FXML
    private void plusBet(MouseEvent event) {
        int apuesta = Integer.parseInt(betLabel.getText());
        if(apuesta < 900) betLabel.setText(String.valueOf(apuesta+100));
        if(apuesta == 900) betLabel.setText(String.valueOf(apuesta+99));
    }

    @FXML
    private void clickTimer(MouseEvent event) {
        Pane p = (Pane) event.getTarget();
        p.setOpacity((p.getOpacity() == 0.25)?0.0:0.85);
        xtreme = !xtreme;
        event.consume();
    }

   
}
