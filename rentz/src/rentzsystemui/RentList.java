package rentzsystemui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This RentList class manages user rentals stored in the userandcurrentrentals.txt file,
 * facilitating account creation for new users and login for existing users.
 * Allows adding, removing, and displaying user rentals.
 *
 *<p>The class provides methods to interact with user rentals data,
 * including adding new users and their rentals, removing rentals,
 * confirming user drop-offs, retrieving all rentals, and updating user rentals.</p>
 *
 *<p>The 'userandcurrentrentals.txt' file structure should follow a pattern of
 * 'username:rental1,rental2,rental3,...'.
 *
 * @author Ricardo Salas
 *
 */
public class RentList {

  /** Text file that holds users and their current rentals.*/
  private static final String USERS_AND_CURRENT_RENTALS = "../rentz/src/"
      + "RegistrationAndCurrentRentalList/usersandcurrentrentals.txt";

  /**
   * Finds and adds a rental if user is found otherwise adds user
   * to the usersandcurrentrentals.txt to later be displayed.
   *
   * @param theUserName the string representation of the user.
   *
   * @param myCurrentRental the currently rented.
   */
  public static void findOrAddUser(String theUserName, String myCurrentRental) {
    String filePath = USERS_AND_CURRENT_RENTALS;
        
    Map<String, String> userRentals = readUserRentalsFromFile(filePath);
        
    if (userRentals.containsKey(theUserName)) {
      String rentals = userRentals.get(theUserName);
      rentals += "," + myCurrentRental;
      userRentals.put(theUserName, rentals);
    } else {
      userRentals.put(theUserName, myCurrentRental);
    }
        
    writeUserRentalsToFile(filePath, userRentals);
  }
  
  /**
   * Removes the chosen rental from the file usersandcurrentrental.txt
   *
   * @param theUserName The string representation of the user.
   *
   * @param theRentalToRemove The chosen rental.
   */
  public static void removeRental(String theUserName, String theRentalToRemove) {

    String filePath = USERS_AND_CURRENT_RENTALS;

    Map<String, String> userRentals = readUserRentalsFromFile(filePath);

    if (userRentals.containsKey(theUserName)) {
      String rentals = userRentals.get(theUserName);

      if (rentals.equals(theRentalToRemove) || rentals.equals(theRentalToRemove + ",")) {
        userRentals.remove(theUserName);
        
      } else {

        // User exists, remove the specified rental
        boolean theIsItLast = isItLast(theUserName, theRentalToRemove);
        rentals = removeSubstring(rentals, theRentalToRemove, theIsItLast);
        userRentals.put(theUserName, rentals);
      }
    }
    // Write the updated map to the file
    writeUserRentalsToFile(filePath, userRentals);
  }

  /**
   * Determines if its the last current Rental off the
   * usersandcurrentrentals.txt, this is for the purpose of
   * taking off the key (being the user name) to be able to
   * start fresh again once renting begins.
   *
   * @param theUserName The String identification of the user
   *
   * @param theRentalToRemove The chosen rental.
   * 
   * @return boolean
   */
  public static boolean isItLast(String theUserName, String theRentalToRemove) {
    String filePath = USERS_AND_CURRENT_RENTALS;

    Map<String, String> userRentals = readUserRentalsFromFile(filePath);
        
    String rentals = userRentals.get(theUserName);
    if (rentals.endsWith(theRentalToRemove)) {
      return true;
    }
    return false;
  }
    
  private static String removeSubstring(String str, String substringToRemove, boolean isItLast) {
    if (isItLast == false) {
      return str.replace(substringToRemove + ",", "").replaceAll(",\\s*,", ",");
    }
    return str.replace(substringToRemove, "").replaceAll(",\\s*,", ",");
  }
    
  /**
   * Method to read user rentals from a file.
   *
   * @param filePath the path for usersandcurrentrentals.txt
   * @return userRentals a map to use for adding, removing, and displaying users and their rentals.
   */
  private static Map<String, String> readUserRentalsFromFile(String filePath) {
    Map<String, String> userRentals = new HashMap<>();
        
    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(":");
        userRentals.put(parts[0], parts[1]);
      }
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + filePath);
    }      
    return userRentals;
  }
    

  /**
   *  Writes user rentals to a file.
   *
   * @param filePath    The path for usersandcurrentrentals.txt
   * @param userRentals Map where key is user and value are their rentals.
   */
  private static void writeUserRentalsToFile(String filePath, Map<String, String> userRentals) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
      for (Map.Entry<String, String> entry : userRentals.entrySet()) {
        writer.println(entry.getKey() + ":" + entry.getValue());
      }
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }
  
  /**
   * verifies whether the rental is under the current users name for dropping off rental.
   *
   * @param theCurrentUserName the current users identity
   *
   * @param theChoosenDropVehicle the name identification of the chosen rental.
   * @return boolean 
   */
  public static boolean confirmDropUser(String theCurrentUserName, String theChoosenDropVehicle) {
    String filePath = USERS_AND_CURRENT_RENTALS;
        
    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(":");
        if (parts.length >= 2 && parts[0].equals(theCurrentUserName) 
            && parts[1].contains(theChoosenDropVehicle)) {
          return true;
        }
      }
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + filePath);
    }
    return false;
  }
  
  /**
   * parses text accordingly to retrieve all rentals from all users usersandcurrentrentals.txt
   *
   * @return allRentals a string array of rentals that are currently being used.
   */
  public static String[] getAllRentals() {
    String filePath = USERS_AND_CURRENT_RENTALS;

    List<String> allRentals = new ArrayList<>();

    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] parts = line.split(":");
                
        String[] rentals = parts[1].split(",");
        allRentals.addAll(Arrays.asList(rentals));
                
      }
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + filePath);
    }

    return allRentals.toArray(new String[0]);
  }
    
  /**
   *  updates and creates a string of the 
   *  current users rentals for display.
   *
   * @param theUserName the name of the current user.
   * @return rentals a string that contains a user and its rentals.
   */
  public static String updateUserCurrRentals(String theUserName) {
    String filePath = USERS_AND_CURRENT_RENTALS;

    String rentals = "";
    Map<String, String> userRentals = readUserRentalsFromFile(filePath);

    try (Scanner scanner = new Scanner(new File(filePath))) {
      if (userRentals.containsKey(theUserName)) {
        rentals = userRentals.get(theUserName);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rentals;
  }
}
