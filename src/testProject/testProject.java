package testProject;

import java.awt.event.WindowListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFileChooser;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class testProject extends Application
{
    public static void main(String[] args) 
    {
        Application.launch(args);
    }
     
    @Override
    public void start(Stage stage) 
    {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
    	
        // Locate the media content in the CLASSPATH
        URL mediaUrl = null;
		try {
			mediaUrl = fileChooser.getSelectedFile().toURI().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String mediaStringUrl = mediaUrl.toExternalForm();
         
        // Create a Media
        Media media = new Media(mediaStringUrl);
         
        // Create a Media Player
        final MediaPlayer player = new MediaPlayer(media);
        // Automatically begin the playback
        player.setAutoPlay(true);
         
        // Create a 400X300 MediaView
        MediaView mediaView = new MediaView(player);
        mediaView.setFitWidth(400);
        mediaView.setFitHeight(300);        
        mediaView.setSmooth(true);
         
        // Create the DropShadow effect
        DropShadow dropshadow = new DropShadow();
        dropshadow.setOffsetY(5.0);
        dropshadow.setOffsetX(5.0);
        dropshadow.setColor(Color.WHITE);
 
        mediaView.setEffect(dropshadow);        
         
        // Create the Buttons
        Button playButton = new Button("Play");
        Button stopButton = new Button("Stop");
        Button pauseButton = new Button("Pause");
        Button volumeUpButton = new Button("<+");
        Button volumeDownButton = new Button("<-");
        Button seekBackwardButton = new Button("-10s");
        Button seekForwardButton = new Button("+10s");
         
        // Create the Event Handlers for the Button
        playButton.setOnAction(new EventHandler <ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
                if (player.getStatus() == Status.PLAYING) 
                {
                    player.stop();
                    player.play();
                } 
                else
                {
                    player.play();
                }
            }
        });     
 
        stopButton.setOnAction(new EventHandler <ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
                player.stop();
            }
        }); 
        
        pauseButton.setOnAction(new EventHandler <ActionEvent>() {
        	public void handle(ActionEvent event) {
        		player.pause();
        	}
        });
        
        volumeUpButton.setOnAction(new EventHandler <ActionEvent>() {
        	public void handle(ActionEvent event) {
        		player.setVolume(player.getVolume()+(player.getVolume()*0.1));
        	}
        });
        
        volumeDownButton.setOnAction(new EventHandler <ActionEvent>() {
        	public void handle(ActionEvent event) {
        		player.setVolume(player.getVolume()-(player.getVolume()*0.1));
        	}
        });
        
        seekBackwardButton.setOnAction(new EventHandler <ActionEvent>() {
        	public void handle(ActionEvent event) {
        		Duration newTime = player.getCurrentTime();
        		player.seek(newTime.add(Duration.millis(10000).negate()));
        	}
        });
        
        seekForwardButton.setOnAction(new EventHandler <ActionEvent>() {
        	public void handle(ActionEvent event) {
        		Duration newTime = player.getCurrentTime();
        		player.seek(newTime.add(Duration.millis(10000)));
        	}
        });
        // Create the HBox
        HBox controlBox = new HBox(5, playButton, stopButton, pauseButton, volumeUpButton, volumeDownButton,seekBackwardButton,seekForwardButton);
         
        // Create the VBox
        VBox root = new VBox(5,mediaView,controlBox);
         
        // Set the Style-properties of the HBox
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
 
        // Create the Scene
        Scene scene = new Scene(root);
        
        stage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {
        	public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
        		player.pause();
        	}
        });
        
        stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
        	public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
        		player.play();
        	}
        });
        
        // Add the scene to the Stage
        stage.setScene(scene);
        // Set the title of the Stage
        stage.setTitle("Video Player");
        // Display the Stage
        stage.show();       
    }   
}