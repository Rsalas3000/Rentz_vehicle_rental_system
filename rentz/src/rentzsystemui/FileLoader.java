package rentzsystemui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The FileLoader class provides utility methods for reading and writing
 * user information to and from files.
 * 
 * <p>This class includes methods to read user information from a file and
 * store it in a map. as well as methods to write user information and rental
 * details to a file. </p>
 *
 * @author Ricardo Salas
 */
public final class FileLoader {

  /**
   * spit token.
   */
  public static final String SPLIT_TOKEN = ",";

  /**
   * A private constructor, to prevent external instantiation.
   */
  private FileLoader() {

  }

  /**
   * Reads item information from a file and returns a List of Item objects.
   *
   * @param theFile the name of the file to load into a List of Items
   * @return a List of Item objects created from data in an input file
   */
  public static Map<String, User> readItemsFromFile(final String theFile) {

    final Map<String, User> userList = new HashMap<String, User>();

    final File filePath = Paths.get(theFile).toAbsolutePath().toFile();
    try (BufferedReader in = new BufferedReader(new FileReader(filePath))) { // Java 7!
      String line;
      while ((line = in.readLine()) != null) {
        final String[] tokens = line.split(SPLIT_TOKEN);
        final String uName = tokens[0];
        final String uPwd = tokens[1];
        final boolean isVip = Boolean.parseBoolean(tokens[2]);
        final User u = new User(uName, uPwd, isVip);
        userList.put(uName, u);
      }
      in.close();
    } catch (final IOException e) {
      e.printStackTrace();
    } // no finally block needed to close 'input' with the Java 7 try with resource block

    return userList;
  }

  /**
   * Writes information of the user to the file.
   *
   * @param theFile the name of the file to load into a List of Items
   * @param theUser the user details to write to file
   */
  public static void writeUserToFile(final String theFile, final User theUser) {

    try (BufferedWriter out = new BufferedWriter(new FileWriter(theFile, true))) {
      final StringBuffer line = new StringBuffer();
      line.append(theUser.getMyName() + SPLIT_TOKEN);
      line.append(theUser.getMyPassword() + SPLIT_TOKEN);
      line.append(theUser.getMyVipStatus());
      line.append("\n");
      out.write(line.toString());
    } catch (final IOException e) {
      e.printStackTrace();
    } 
  }
  
  /**
   * Writes both the User with their proper rental.
   *
   * @param theFile the name of the file to load into a List of Items
   * @param theUser the user details to write to file.
   * @param theRentalName the name of the rented vehicle.
   */
  public static void writeRentalToFile(final String theFile,
                                       final User theUser, String theRentalName) {

    try (BufferedWriter out = new BufferedWriter(new FileWriter(theFile, true))) {

      final StringBuffer line = new StringBuffer();
      line.append(theUser.getMyName() + SPLIT_TOKEN);
      line.append(theRentalName);
      out.write(line.toString());
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

}

