package rentzsystemui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * Manages user authentication, login, and new account creation.
 *
 *<p>The UserAuthenticationLogin class provides functionality for user login and
 * new account creation. It handles activation and login areas for both new and 
 * existing members.
 *
 * @author Ricardo Salas
 *
 */
public class UserLoginGui extends JFrame {

  private static final long serialVersionUID = 3266503789591713957L;

  public JFrame myFrame;

  private static String myCurrentUser;

  public UserLoginGui(JFrame theFrame) {
    this.myFrame = theFrame;
  }

  /**
   * Initializes and starts the login panel for user authentication and creation.
   *
   * @param theFrame This JFrame is used throughout each stage of Rentz. 
   * @return panel panel that contains content to log into Rentz.
   */
  public JPanel loginPanelStart(JFrame theFrame) {
    JPanel panel = new JPanel(new GridLayout(3, 2));

    int red = 50;
    int green = 204;
    int blue = 202;

    this.setBackground(new Color(red, green, blue));
    panel.setBackground(Color.WHITE);
    panel.setPreferredSize(new Dimension(300, 100));
    JLabel usernameLabel = new JLabel("Username:");
    usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton newAccountButton = new JButton("new account?");
    JButton loginButton = new JButton("Login");

    panel.add(usernameLabel);
    panel.add(usernameField);
    panel.add(passwordLabel);
    panel.add(passwordField);
    panel.add(newAccountButton);
    panel.add(loginButton);

    newAccountButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JPanel newMemPanel = newMemPanel();

        theFrame.getContentPane().removeAll();
        theFrame.add(newMemPanel);
        theFrame.revalidate();
        theFrame.repaint();
      }
    });

    loginButton.addActionListener(new ActionListener() {


      @Override
      public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        myCurrentUser = username;
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);
        final Registration reg = new Registration();
        if (reg.existingMemLogin(username, password)) {
          VehicleInfoGui itemInfoGui = new VehicleInfoGui(theFrame);

          //remove the content from the frame
          theFrame.getContentPane().removeAll();
          itemInfoGui.start(theFrame);

        } else {
          usernameField.setText("");
          passwordField.setText("");
          JOptionPane.showMessageDialog(null, "Username or password is incorrect."
                                              + " If you are a new user please create an account.");
          }
        }
      });

    panel.setBorder(new LineBorder(Color.BLACK, 2));

    return panel;

  }
  
  /**
   * When the  new account button is pressed a new panel begins
   * to get a new user registered with a new account.
   *
   * @return panel The panel where you enter a username and password
   *               to be able to access Rentz.
   */
  private JPanel newMemPanel() {
    JPanel panel = new JPanel(new GridLayout(3, 2));
    panel.setPreferredSize(new Dimension(300, 100));

    panel.setBackground(Color.WHITE);
    JLabel newUsernameLabel = new JLabel("Your Username:");
    newUsernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    JLabel newPasswordLabel = new JLabel("Your Password:");
    newPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    JTextField newUsernameField = new JTextField();
    JPasswordField newPasswordField = new JPasswordField();
    JButton submitButton = new JButton("submit");

    panel.add(newUsernameLabel);
    panel.add(newUsernameField);
    panel.add(newPasswordLabel);
    panel.add(newPasswordField);
    panel.add(submitButton);

    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String theUsername = newUsernameField.getText();
        char[] passwordChars = newPasswordField.getPassword();
        String password = new String(passwordChars);

        final Registration reg = new Registration();
        if (reg.newMemSignUp(theUsername, password)) {
          
          JPanel loginPanel = loginPanelStart(myFrame);
          
          myFrame.getContentPane().removeAll();
          myFrame.add(loginPanel);
          myFrame.revalidate();
          myFrame.repaint();
        }

      }

    });

    panel.setBorder(new LineBorder(Color.BLACK, 2));

    return panel;
  }

  /** 
   * Retrieves the name of the currently logged-in user.
   *
   * @return myCurrentUser The name of the currently  
   *         logged-in user, or null if no user is logged-in.
   */
  public static String getMyCurrentUser() {
    return myCurrentUser;
  }
}