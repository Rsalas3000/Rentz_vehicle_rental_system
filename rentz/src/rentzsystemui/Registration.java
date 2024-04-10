package rentzsystemui;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * The Registration object to keep track and manage
 * the active users.
 *
 * @author Ricardo Salas
 *
 */
public class Registration {

  /**
   * Scanner for the application.
   */
  public static final Scanner SCANNER = new Scanner(System.in);

  /**
   * User Storage File.
   */
  private static final String DEFAULT_USERFILE_NAME = "../rentz/src/"
                                     + "registrationandcurrentrentalList/registeredusers.txt";

  /**
   * The registered user list for sign in.
   */
  private final Map<String, User> myUserList;

  /**
   *  The file of user names.
   */
  private final String myUserfileName;

  /**
   * Constructs a sign in/registration system.
   */
  public Registration() {

    this(DEFAULT_USERFILE_NAME);
  }

  /**
   * Constructor for registering users.
   *
   * @param theUserfileName the name of the file of users.
   */
  public Registration(final String theUserfileName) {

    this.myUserfileName = theUserfileName;
    myUserList = FileLoader.readItemsFromFile(theUserfileName);

  }

  /**
   * getter for myUserList.
   *
   * @return myUserList
   */
  public Map<String, User> getMyUserList() {
    return myUserList;
  }

  /**
   * new member sign-in.
   *
   * @return boolean true
   */
  public boolean newMemSignUp(String theUserName, String thePassword) {

    final boolean result;

    while (myUserList.containsKey(theUserName)) {

      theUserName = JOptionPane.showInputDialog("User already exists, enter different user name:");
      continue;
    }

    boolean validPassword = validatingPassword(theUserName, thePassword);

    while (!validPassword) {
      thePassword = JOptionPane.showInputDialog("create a new password \n"
                                           + "Password must contain: \n"
                                           + "at least 6 characters \n"
                                           + "at least one digit \n" 
                                           + "at least one upper case character \n"
                                           + "at least one lower case character \n" 
                                           + "at least one special character \n");

      validPassword = validatingPassword(thePassword, theUserName);
    }
    int buttonPressed = JOptionPane.showConfirmDialog(null, "Join our Premium Membership?");

    boolean vipStatus = false;
    if (buttonPressed == JOptionPane.YES_OPTION) {
      vipStatus = true;

    } else if (buttonPressed == JOptionPane.NO_OPTION) {
      vipStatus = false;
    }

    final User newUser = new User(theUserName, thePassword, vipStatus);

    if (register(newUser)) {
      JOptionPane.showMessageDialog(null, "Welcome to RENTZ, your account is now active.");
    }

    result = true;

    return result;
  }

  /**
   * Verifies that the member is on the list.
   *
   * @param theUserName the name of the user.
   * @param password the password created by the user.
   * @return boolean
   */
  public boolean existingMemLogin(String theUserName, String password) {
    boolean result = false;

    if (login(theUserName, password)) {
      result = true;
    }

    return result;
  }

  /**
   * Verifies if the created password meets the requirements.
   *
   * @param theUserName the name of the user.
   * @param thePassword the created string by the user.
   * @return validPassword
   */
  private boolean validatingPassword(final String theUserName, final String thePassword) {

    boolean validPassword = false;
    final int minLength = 6;
    final String symbols = ".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*";

    if (thePassword.length() >= minLength
        && Pattern.compile(".*[a-z].*").matcher(thePassword).find()
        && Pattern.compile(".*[0-9].*").matcher(thePassword).find()
        && Pattern.compile(".*[A-Z].*").matcher(thePassword).find()
        && Pattern.compile(symbols).matcher(thePassword).find()
        && !(thePassword.contains(theUserName))) {
      validPassword = true;
    }
    return validPassword;
  }

  /**
   * Verify Sign-in procedure.
   *
   * @param theUsername the users' name for sign-in
   * @param thePassword password for sign in
   * @return sign-in success
   */
  public boolean login(final String theUsername, final String thePassword) {

    Objects.requireNonNull(theUsername);
    Objects.requireNonNull(thePassword);

    if (theUsername.isEmpty() || thePassword.isEmpty()) {
      JOptionPane.showMessageDialog(null, "To login a username and password is required");
      throw new IllegalArgumentException();
    }

    User userInFile = myUserList.get(theUsername);

    return userInFile != null && userInFile.getMyPassword().equals(thePassword);
  }

  /**
   * Adds a user to the registered user list.
   *
   * @param theUser the user who is signing up or signing in.
   * @return true/false returns if registration is successful
   */
  public boolean register(final User theUser) {
    myUserList.put(theUser.getMyName(), theUser);
    FileLoader.writeUserToFile(myUserfileName, theUser);

    return true;
  }

  /**
   * Empties the user list.
   */
  public void clear() {
    myUserList.clear();
  }

  public String getUserfileName() {
    return myUserfileName;
  }

  /**
   * Converts the Registered users as a list.
   *
   * @return the string representation of registration
   */
  public String toString() {
    return "Registered UserList" + myUserList.toString();
  }

}
