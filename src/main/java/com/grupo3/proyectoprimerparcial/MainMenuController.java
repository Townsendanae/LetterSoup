package com.grupo3.proyectoprimerparcial;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import partida.Idiomas;
import partida.Partida;
import partida.Sonidos;

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
            pane.setOnMouseEntered(e -> mouseEnteredButton(pane));
            pane.setOnMouseExited(e -> mouseExitedButton(pane));
            pane.setOnMousePressed(e -> mouseExitedButton(pane));
            pane.setOnMouseReleased(me -> mouseEnteredButton(pane));
        }
        cambiarIdioma();
        timer.setOnMouseClicked(e -> clickTimer(timer));
    }

    private void mouseEnteredButton(Pane p){
        p.setOpacity((p.getOpacity() == 0.25)?0:0.25);
    };
    
    private void mouseExitedButton(Pane p){
        if(p == timer) p.setOpacity((p.getOpacity() == 0)?0.25:0.85);
        else p.setOpacity((p.getOpacity() == 0.25)?0.85:0.25);
    };

    @FXML
    private void minusRow(MouseEvent event) {
        Sonidos.clickOne();
        int rows = Integer.parseInt(rowsLabel.getText());
        if(rows > 5) rowsLabel.setText(String.valueOf(--rows));
    }

    @FXML
    private void plusRow(MouseEvent event) {
        Sonidos.clickOne();
        int rows = Integer.parseInt(rowsLabel.getText());
        if(rows < 20) rowsLabel.setText(String.valueOf(++rows));
    }

    @FXML
    private void minusColumn(MouseEvent event) {
        Sonidos.clickOne();
        int columns = Integer.parseInt(columnsLabel.getText());
        if(columns > 5) columnsLabel.setText(String.valueOf(--columns));
    }

    @FXML
    private void plusColumn(MouseEvent event) {
        Sonidos.clickOne();
        int columns = Integer.parseInt(columnsLabel.getText());
        if(columns < 20) columnsLabel.setText(String.valueOf(++columns));
    }

    @FXML
    private void previousLanguage(MouseEvent event) {
        Sonidos.clickThree();
        cambiarIdioma();
    }

    @FXML
    private void nextLanguage(MouseEvent event) {
        Sonidos.clickThree();
        cambiarIdioma();
    }
    
    private void cambiarIdioma(){
        language.getChildren().clear();
        if(Partida.idioma == Idiomas.SPANISH){
            ImageView imgview;
            try{
                InputStream input = new FileInputStream(new File("resources/images/english.png"));
                Image img = new Image(input);
                imgview = new ImageView(img);
                Partida.idioma = Idiomas.ENGLISH;
                language.getChildren().add(imgview);
            }catch(Exception e){
                
            }
            
        }else{
            ImageView imgview;
            try{
                InputStream input = new FileInputStream(new File("resources/images/spanish.png"));
                Image img = new Image(input);
                imgview = new ImageView(img);
                Partida.idioma = Idiomas.SPANISH;
                language.getChildren().add(imgview);
            }catch(Exception e){}
        }
    }

    @FXML
    private void singleplayer(MouseEvent event) {
        Sonidos.clickTwo();
        singleplayer.setOpacity(0);
        multiplayer.setOpacity(0.85);
        jugadores = 1;
    }

    @FXML
    private void multiplayer(MouseEvent event) {
        Sonidos.clickTwo();
        multiplayer.setOpacity(0);
        singleplayer.setOpacity(0.85);
        jugadores = 2;
        betLabel.setText("0");
    }
    
    @FXML
    private void empezarPartida(MouseEvent event) throws IOException {
        
        Sonidos.goButton();
        
        int n_filas = Integer.parseInt(rowsLabel.getText());
        int n_columnas = Integer.parseInt(columnsLabel.getText());
        int apuesta = Integer.parseInt(betLabel.getText());
        
        if(jugadores == 1) Partida.nuevaPartidaUnJugador( n_filas, n_columnas, apuesta, xtreme);
        else Partida.nuevaPartidaDosJugadores(n_filas, n_columnas, apuesta, xtreme);
        
        App.setRoot("Sopa");
        
    }

    @FXML
    private void salir(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void minusBet(MouseEvent event) {
        Sonidos.clickThree();
        int apuesta = Integer.parseInt(betLabel.getText());
        if(apuesta > 0) betLabel.setText(String.valueOf(apuesta-10));
        if(apuesta == 99) betLabel.setText(String.valueOf(apuesta-9));
    }

    @FXML
    private void plusBet(MouseEvent event) {
        Sonidos.clickThree();
        singleplayer.setOpacity(0.25);
        multiplayer.setOpacity(0.85);
        jugadores = 1;
        
        int apuesta = Integer.parseInt(betLabel.getText());
        if(apuesta < 90) betLabel.setText(String.valueOf(apuesta+10));
        if(apuesta == 90) betLabel.setText(String.valueOf(apuesta+9));
    }

    private void clickTimer(Pane p){
        Sonidos.clickOne();
        p.setOpacity((p.getOpacity() == 0.25)?0.0:0.85);
        xtreme = !xtreme;
    }

   
}
