package partida;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public abstract class Sonidos {

    public static void playBGMusic() {

        Media sound = new Media(new File("resources/sounds/bg_music.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.0);

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });

        mediaPlayer.play();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3),
                        new KeyValue(mediaPlayer.volumeProperty(), 0.15)));
        timeline.play();
    }
    
    public static void goButton(){
        
        Media sound = new Media(new File("resources/sounds/go_button.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.4);
        mediaPlayer.play();
    }
    
    public static void clickOne(){
        
        Media sound = new Media(new File("resources/sounds/click_01.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.15);
        mediaPlayer.play();
    }
    
    public static void clickTwo(){
        
        Media sound = new Media(new File("resources/sounds/click_02.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.4);
        mediaPlayer.play();
    }
    
    public static void clickThree(){
        
        Media sound = new Media(new File("resources/sounds/click_03.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.6);
        mediaPlayer.play();
    }
    
    public static void success(){
        
        Media sound = new Media(new File("resources/sounds/success.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
    }
    
    public static void mistake(){
        
        Media sound = new Media(new File("resources/sounds/mistake.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
    }
    
    public static void letter(){
        
        Media sound = new Media(new File("resources/sounds/letter.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(1.0);
        mediaPlayer.play();
    }

}
