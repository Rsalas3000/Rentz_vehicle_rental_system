package vehicles;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Abstract representation of vehicles for rental purposes.
 *
 * <p>This class serves as a blueprint for various types of vehicles that can be rented.
 * It includes common properties and behaviors that all vehicle types share.
 *
 * @author Ricardo Salas
 *
 */
public abstract class AbstractVehicle {
  /** Represents the base fare for renting a vehicle.*/
  private static final BigDecimal BASE_FARE = new BigDecimal(10);

  /** Represents a non-basic vehicle fare for renting a vehicle. */
  public static final BigDecimal SPECIAL_FARE = BASE_FARE;

  /** Represents a premium for motorcycles. */
  public static final BigDecimal MOTORCYCLE_PREMIUM = BASE_FARE.multiply(new BigDecimal(2));

  /** Represents the base fare for renting out cars. */
  public static final BigDecimal CAR_FARE = BASE_FARE.multiply(new BigDecimal(3));

  /** Represents the unique VIN for the vehicle. */
  private final String myVin;

  /** Represents the name of the vehicle. */
  private String myName;

  /** 
   * Represents the rental status of the vehicle availability.
   * 
   * <p>This is set independently as well, when instantiating a subclass of this class for the
   *     purposes of possible unavailability of the vehicle.
   */
  private boolean myAvailabilityStatus = true;
  
  /** Represents a description of the rental. */
  private String myDescription;

  /**
   * Creates a new vehicle.
   *
   * @param theName String representation of vehicle.
   * @param theVin  String VIN representation of vehicle.
   * @param theDescription Information about the vehicle.
   */
  public AbstractVehicle(final String theName, final String theVin,
                           final String theDescription) {
    this.myName = theName;
    this.myVin = theVin;
    this.myDescription = theDescription;
  }

  /**
   * Calculates the rental total.
   *
   *  @return Calculate rental amount.
   */
  public abstract BigDecimal calculateRentalAmount();

  /**
   *  Retrieves the string representation of the vehicle.
   *
   *  @return String representation of the vehicle.
   */
  public abstract String toString();

  /**
   * Redefinition of the hash code method from the one in the parent class.
   *
   * @return hash code
   */
  public int hashCode() {
    return Objects.hash(myVin, myName, myAvailabilityStatus);
  }

  /**
   * Retrieves an arranged value for the rental.
   *
   *  @return BASE_FARE */
  public static BigDecimal getBaseFare() {
    return BASE_FARE;
  }

  /**
   * Retrieves the VIN number of the vehicle.
   *
   *  @return Vehicle VIN.
   */
  public String getMyVin() {
    return myVin;
  }
  
  /** Sets the name of the vehicle. */
  public void setMyName(final String theName) {
    this.myName = theName;
  }

  /**
   * Retrieves the type of vehicle.
   *
   * @return the type of vehicle.
   */
  public String getMyType() {
    return this.getClass().getSimpleName();
  }

  /**
   * Retrieves the rental status of the vehicle.
   *
   *  @return true if the vehicle can be rented, false otherwise.
   */
  public boolean isRentable() {
    return myAvailabilityStatus;
  }

  /**
   * Sets the rental status to true if no user has rented it, false otherwise.
   *
   *  @param theRentalStatus to set.
   */
  public void setMyRentalStatus(final boolean theRentalStatus) {
    this.myAvailabilityStatus = theRentalStatus;
  }

  /**
   * Displays the name of the vehicle.
   *
   * @return string name of vehicle with spaces for display.
   */
  public String getDisplayName() {
    return "        " + myName + "          ";
  }
  
  /**
   * Retrieves the name of the vehicle.
   *
   * @return myName String representation of the vehicle */
  public String getMyName() {
    return myName;
  }

  /**
   * Retrieves the information pertaining to the vehicle.
   *
   * @return myDescription Information on the vehicle.
   */
  public String getMyDiscription() {
    return myDescription;
  }

  /**
   * Sets the description of the vehicle.
   *
   * @param myDescription a string of information about the vehicle.
   */
  public void setMyDiscription(String myDescription) {
    this.myDescription = myDescription;
  }
  
}