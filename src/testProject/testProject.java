package testProject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;


public class testProject {
	
	JFrame mainwindow;
	JFXPanel mediapanel;
	JFileChooser fc;
	Dimension dim;
	MediaPlayer player;
	
	public testProject() {
		mainwindow = new JFrame();		
		MediaView mediaview = new MediaView();
		DropShadow dropshadow = new DropShadow();
		dropshadow.setOffsetY(5.0);
		dropshadow.setOffsetX(5.0);
		dropshadow.setColor(Color.BLACK);
		mediaview.setEffect(dropshadow);
		
		mediapanel = new JFXPanel();
		Group g = new Group(mediaview);
		Scene s = new Scene(g);
		s.setFill(Paint.valueOf("TRANSPARENT"));
		mediapanel.setScene(s);
		
		JPanel controlpanel = new JPanel();
        JButton playButton = new JButton("Play");
        JButton stopButton = new JButton("Stop");
        JButton pauseButton = new JButton("Pause");
        JButton volumeUpButton = new JButton("<+");
        JButton volumeDownButton = new JButton("<-");
        JButton seekBackwardButton = new JButton("-10s");
        JButton seekForwardButton = new JButton("+10s");
        
        controlpanel.add(playButton);
        controlpanel.add(stopButton);
        controlpanel.add(pauseButton);
        controlpanel.add(volumeUpButton);
        controlpanel.add(volumeDownButton);
        controlpanel.add(seekBackwardButton);
        controlpanel.add(seekForwardButton);
        
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem openvideo = new JMenuItem("Open Video");
        
        menu.add(openvideo);
        menubar.add(menu);
        
        mainwindow.add(mediapanel);
        mainwindow.add(controlpanel, BorderLayout.SOUTH);
        mainwindow.setJMenuBar(menubar);
        
        mainwindow.setSize(500,400);
        
		int w = mainwindow.getWidth(), h = mainwindow.getHeight();
		dim = new Dimension(w, h);
		mediapanel.setPreferredSize(dim);
        mainwindow.setVisible(true);
        
        openvideo.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(java.awt.event.ActionEvent arg0) {
				fc = new JFileChooser();
        		fc.showOpenDialog(null);
        		URL mediaURL = null;
        		
        		try {
        			mediaURL = fc.getSelectedFile().toURI().toURL();
        		}
        		catch(MalformedURLException e1) {
        			
        		}
				
        		if(mediaURL != null) {
        			String mediaStringURL = mediaURL.toExternalForm();
        			Media media = new Media(mediaStringURL);
        			player = new MediaPlayer(media);
        			mediaview.setMediaPlayer(player);
        			DoubleProperty mvw = mediaview.fitWidthProperty();
        			DoubleProperty mvh = mediaview.fitHeightProperty();
        			mvw.bind(Bindings.selectDouble(mediaview.sceneProperty(), "width"));
        			mvh.bind(Bindings.selectDouble(mediaview.sceneProperty(), "height"));
        			mediaview.setPreserveRatio(true);
        		}
			}
        });
        
        playButton.addActionListener(new ActionListener() {
            
			@Override
			public void actionPerformed(ActionEvent e) {
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
 
        stopButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) { 
                player.stop();
            }
        }); 
        
        pauseButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		player.pause();
        	}
        });
        
        volumeUpButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		player.setVolume(player.getVolume()+(player.getVolume()*0.1));
        	}
        });
        
        volumeDownButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		player.setVolume(player.getVolume()-(player.getVolume()*0.1));
        	}
        });
        
        seekBackwardButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		Duration newTime = player.getCurrentTime();
        		player.seek(newTime.add(Duration.millis(10000).negate()));
        	}
        });
        
        seekForwardButton.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		Duration newTime = player.getCurrentTime();
        		player.seek(newTime.add(Duration.millis(10000)));
        	}
        });
	}

	public static void main(String[] args) {
		new testProject();
	}
	
}
