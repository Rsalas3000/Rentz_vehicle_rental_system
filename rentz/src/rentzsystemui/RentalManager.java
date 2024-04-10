package rentzsystemui;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.JOptionPane;
import vehicles.AbstractVehicle;

/**
 * The RentalManager class manages the connectivity between registration and rental actions.
 * 
 * <p>This class provides methods for renting vehicles, printing rental bills, dropping off
 * vehicles, and managing transactions.</p>
 * 
 * <p>It includes methods to check if a rental is available, rent a vehicle to a user, 
 * print rental bills to files, and handle the dropping off of vehicles.</p>
 * 
 * <p>This class also provides functionality to interact with users through GUI dialogs,
 * such as prompting for rental information and providing rental confirmation messages.</p>
 *
 *@author Ricardo Salas
 *
 */
public class RentalManager {

  /** Reference to the bill list. */
  private Map<Integer, Bill> myBills;

  /** Reference to registration Object. */
  private Registration myRegistration;

  /** Reference for the stars. */
  private final String myStars = "***********************";

  /** Reference for the Bill number. */
  public int myNextBillId = 1;

  /**
   * Constructor that initializes the Registration object.
   *
   * @param theRegistration object
   * @throws NullException for null registration objects
   */
  public RentalManager(final Registration theRegistration) {
    
    this.myRegistration = Objects.requireNonNull(theRegistration);

    myBills = new HashMap<>();
  }

  /** 
   * Retrieves the current itemInfoMap.
   *
   * @return itemInfoMap
   * 
   */
  public Map<String, AbstractVehicle> getMyItemInfoMap() {
    return VehicleInfoGui.myVehInfoMap;
  }

  /**
   * Retrieves the Registration of users.
   *
   *  @return myRegistration
   */
  public Registration getMyRegistration() {
    return myRegistration;
  }

  /**
   * Returns a boolean on whether the rental is available for the current user.
   *
   * @param theVehicleName the specified name of the rental
   * @return boolean
   * 
   */
  public boolean isRentable(final String theVehicleName) {
    final AbstractVehicle vehicle = VehicleInfoGui.getMap().get(theVehicleName);
    return vehicle != null && vehicle.isRentable();
  }

  /**
   * Brings together all information needed to give out a proper Rental.
   *
   * @param theRentalId A rental integer for verification.
   * @param theUserName The current username.
   * @param theNumDays  The amount of days that should be charged
   * @param theBillId   A identification integer for the bill created
   * @return Boolean
   */
  public boolean rent(final int theBillId, final String theUserName,
                       final int theRentalId, final int theNumDays, final String theVehicleName) {

    Objects.requireNonNull(theRentalId);
    Objects.requireNonNull(theUserName);
    Objects.requireNonNull(theNumDays);
    Objects.requireNonNull(theBillId);

    if (theNumDays <= 0) {
      throw new IllegalArgumentException("Invalid number of days");
    }

    if (isRentable(theVehicleName) && myRegistration.getMyUserList().containsKey(theUserName)) {
      VehicleInfoGui.getMap().get(theVehicleName).setMyRentalStatus(false);

      final Bill bill = new Bill(theBillId, myRegistration.getMyUserList().get(theUserName),
          VehicleInfoGui.getMap().get(theVehicleName), theNumDays);

      myBills.put(bill.getBillId(), bill);

      final DateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");

      String path = "src/ClientBills/" + UserLoginGui.getMyCurrentUser() + "-";
      try (PrintStream fileOut = new PrintStream(path
           + df.format(new Date()) + "-" + theBillId + ".txt")) {
        printBill(fileOut, theUserName, theRentalId, theVehicleName, bill);
        
      } catch (final FileNotFoundException e) {

        e.printStackTrace();
      }
      return true;
    }
    return false;
  }

  /**
   * Prints the bill in the file.
   *
   * @param theOutput a printStream for outputting to necessary components and files.
   *
   *@param theUserName the name of the renter
   *
   * @param theVehicleId the integer id of the rental
   *
   * @param theBill The bill object to give to the user and maintain for record.
   */
  private void printBill(final PrintStream theOutput, String theUserName,
                          final int theVehicleId, final String theVehicleName, final Bill theBill) {

    theOutput.println(myStars);
    theOutput.println(" Rental Bill Summary");
    theOutput.println(myStars);
    theOutput.println("User Name: " + theUserName);
    theOutput.println("----Vehicle Information----");
    theOutput.println("VehicleName " + VehicleInfoGui.getMap().get(theVehicleName).getMyName());
    theOutput.println("VehicleID " + theVehicleId);
    theOutput.println("VehicleType " + VehicleInfoGui.getMap().get(theVehicleName)
                        .getClass().getSimpleName());
    theOutput.println("VIN " + VehicleInfoGui.getMap().get(theVehicleName).getMyVin());
    theBill.computeAndPrintAmount(theOutput);
    theOutput.close();
    RentList.findOrAddUser(theUserName, theVehicleName);

  }

  /**
   * Retrieves input and then provides output to the user
   * for confirmation of the rented item.
   *
   * @param theVehicleName The name representation of the rental.
   * @param theRentalId the integer id for the rental.
   */
  public void printRent(String theVehicleName, int theRentalId) {
    String myCurrentUser = UserLoginGui.getMyCurrentUser();
    int numDays = -1;
    boolean foundVehicle = false;
    String inputNumDays;
    
    if (!isRentable(theVehicleName)) {
      JOptionPane.showMessageDialog(null, "This vehicle is currently being Used."
                                        + " Give another one a try");
    }
    while (numDays <= 0 && isRentable(theVehicleName)) {
      inputNumDays = JOptionPane.showInputDialog(null, "Provide the number of days"
                                                     + " you would you like to rent?");
      numDays = Integer.parseInt(inputNumDays);

      if (isRentable(theVehicleName)) {
        foundVehicle = true;
      }
    }
   
    if (foundVehicle) {
      rent(myNextBillId++, myCurrentUser, theRentalId, numDays, theVehicleName);
    }
  }

  /**
   *  Assures the vehicle was dropped properly and
   *  is ready to be rented out again.
   *
   * @param theUserName The name of the user.
   * @param theVehicleName The name representation of the rental.
   */
  public void printDrop(String theUserName, String theVehicleName) {
    boolean droppedVehicle = VehicleInfoGui.getMap().get(theVehicleName).isRentable();
    boolean userMatch = RentList.confirmDropUser(theUserName, theVehicleName);

    while (!droppedVehicle && userMatch) {
      if (drop(theVehicleName)) {
        droppedVehicle = true;
        JOptionPane.showMessageDialog(null,
            "RENTZ thanks you for trusting us to be apart of your experience, the " + theVehicleName
            + " was successfully dropped off");
        RentList.removeRental(theUserName, theVehicleName);
      }
      if (userMatch == false) {
        JOptionPane.showMessageDialog(null, "This vehicle is rented by someone else");
      }
    }
  }

  /**
   * Checks theVehicleID is valid and if able to rent by calling isRentable.
   *
   * @return if the user is allowed to return the vehicle
   */
  public boolean drop(final String theVehicleName) {
    final AbstractVehicle vehicle = VehicleInfoGui.getMap().get(theVehicleName);
    if (vehicle == null) {
      return false;
    }
    if (vehicle.isRentable()) {
      JOptionPane.showMessageDialog(null, "Vehicle is not rented, this is out on the floor");
    }
    vehicle.setMyRentalStatus(true);
    return true;
  }

  /**
   * Clears the map containing all abstract vehicles.
   */
  public void clearList() {
    VehicleInfoGui.getMap().clear();
    myBills.clear();
  }

}
