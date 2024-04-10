package rentzsystemui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * This introduction class displays an introduction animation with sound.
 * It initializes a JFrame with a GIF image and plays an intro sound.
 * Once it is done, it transitions to the login screen.
 *
 * @author Ricardo Salas
 *
 */
public class IntroAnimationController { 
  /** The base frame in which other components will be placed. */
  private JFrame myFrame;
  
  /** The panel in where GIF image will be displayed. */
  private static JPanel myIntroPanel;
  
  /** Synchronizes the intro image and the sound. */
  private Timer myTimer;
  
  /** The label that will hold the icons. */
  private JLabel myGifLabel;
  
  /** The authentication process for members and account creation for new members. */
  private UserLoginGui myLoginStart;

  private String wavPath = "../rentz/src/AudioIntro/Michael John Wookey"
                         + " - Wanderlust Motifs - Far East Intro .wav";

  /**
   * Initialization of the declared variables that consist of the introduction of Brand.
   */
  public IntroAnimationController() {
    myFrame = new JFrame();
    myIntroPanel = new JPanel();
    myGifLabel = new JLabel(new ImageIcon("../rentz/src/movingImages/RENTZLOGO.gif"));
    myLoginStart = new UserLoginGui(myFrame);

  }
 
  /**
   * Starts the display of the introduction animation and sound.
   */
  public void start() {
    myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    myIntroPanel.setLayout(new BorderLayout());

    int red = 251;
    int green = 251;
    int blue = 251;
    Color bgColor = new Color(red, green, blue);
    myIntroPanel.setBackground(bgColor);

    try {
      AudioInputStream introSound = AudioSystem.getAudioInputStream(new File(wavPath));

      Clip clip = AudioSystem.getClip();

      clip.open(introSound);

      clip.addLineListener(new LineListener() {
        public void update(LineEvent event) {
          if (event.getType() == LineEvent.Type.STOP) {

            clip.close();
              }
            }
        });

      clip.start();

    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
      e1.printStackTrace();
    }

    myGifLabel.setHorizontalAlignment(JLabel.CENTER);
    myGifLabel.setVerticalAlignment(JLabel.CENTER);

    myIntroPanel.add(myGifLabel, BorderLayout.CENTER);
    myFrame.setContentPane(myIntroPanel);

    myTimer = new Timer(8500, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        myIntroPanel.remove(myGifLabel);
        
        myFrame.remove(myIntroPanel);
        myFrame.getContentPane().setBackground(new Color(50, 204, 202));
        myFrame.setLayout(new GridBagLayout()); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        myFrame.getContentPane().add(myLoginStart.loginPanelStart(myFrame), gbc);
        myFrame.setVisible(true);

        myTimer.stop();

      }
    });

    myTimer.start();
    myFrame.setVisible(true);

  }

  /**
   * Initializes and starts the IntroAnimationController class.
   *
   * @param args starts the program RENTZ
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      IntroAnimationController intro = new IntroAnimationController();
      intro.start();
    });
  }
}
