package vehicles;


import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a truck available for rental, with attributes and methods to facilitate the 
 * rental process. Extends AbstractVehicle class to inherit basic vehicle attributes.
 *
 * @author Ricardo Salas
 *
 */
public class Truck extends AbstractVehicle {

  /** Field variable to represent the charge fee for the luxury feature. */
  private static final BigDecimal LUXURY_CHARGE = new BigDecimal(10);

  /** field variable to represent the charge fee for the navigation feature. */
  private static final BigDecimal NAVIGATION_CHARGE = new BigDecimal(1);

  /** Field variable to represent the charge fee for driving assistance feature. */
  private static final BigDecimal DRIVING_ASSISTANCE_CHARGE = new BigDecimal(2);

  /** Field variable to represent if the vehicle has luxury features. */
  private final boolean myLuxury;

  /** Field variable to represent if the vehicle has navigation features. */
  private final boolean myNavigation;

  /** Field variable to represent if the vehicle has driving assistance features. */
  private final boolean myDrivingAssistance;

  /**
   * Parameterized constructor calls parent class constructor to initialize Truck() field
   * variables.
   *
   * @param theName of the vehicle.
   * @param theVin Vehicle Identification number.
   * @param theRent availability of the vehicle.
   * @param theLuxury features of the vehicle.
   * @param theNavigation features of the vehicle.
   * @param theDrivingAssistance features of the vehicle.
   */
  public Truck(final String theName, final String theVin,
               final String theDescription, final boolean theRent,
               final boolean theLuxury, final boolean theNavigation,
               final boolean theDrivingAssistance) {

    super(theName, theVin, theDescription);

    this.myLuxury = theLuxury;
    this.myNavigation = theNavigation;
    this.myDrivingAssistance = theDrivingAssistance;

  }

  /**
   * Retrieves a boolean on whether vehicle is considered a luxury item.
   *
   *  @return true if vehicle is of status, false otherwise.
   */
  public boolean getIsMyLuxury() {
    return myLuxury;

  }

  /**
   * Retrieves a boolean on whether vehicle has navigation.
   *
   *  @return true if vehicle has navigation, false otherwise.
   */
  public boolean getIsMyNavigation() {
    return myNavigation;

  }

  /**
   * Determines whether there is driving assistance.
   *
   *  @return true if vehicle has driving assistance, false otherwise.
   */
  public boolean getIsMyDrivingAssistance() {
    return myDrivingAssistance;
  }

  /** 
   * A string representation of the truck class.
   *
   * @return String
   */
  public BigDecimal calculateRentalAmount() {
    BigDecimal rentalAmount = CAR_FARE;
    if (myLuxury) {
      rentalAmount = rentalAmount.add(LUXURY_CHARGE);
    }
    if (myNavigation) {
      rentalAmount = rentalAmount.add(NAVIGATION_CHARGE);
    }
    if (myDrivingAssistance) {
      rentalAmount = rentalAmount.add(DRIVING_ASSISTANCE_CHARGE);
    }
    return rentalAmount;
  }

  /**
   *  Puts together details about the truck.
   *
   *  @return String The relevant information of the truck.
   */
  @Override
  public String toString() {
    return "Truck (Name:" + getMyName() + ", VIN:" + getMyVin()
            + ", available?:" + isRentable() + ", IsLuxury?:" + myLuxury
            + ", HasNavigation?:" + myNavigation + ", HasAssistance?:" + myDrivingAssistance + ")";
  }

 
  /**
   * Compares this truck object with the specified object for equality.
   *
   * @return true if the specified object is also a truck and all corresponding
   *         attributes including its superclass attributes, match; otherwise returns false.
   */
  @Override
  public boolean equals(final Object theOtherVehicle) {
    if (!(theOtherVehicle instanceof Truck)) {
      return false;
    }
    final Truck otherTruck = (Truck) theOtherVehicle;
    return super.equals(otherTruck)
           && myLuxury == otherTruck.myLuxury
           && myNavigation == otherTruck.myNavigation
           && myDrivingAssistance == otherTruck.myDrivingAssistance;
  }

  /**
   * A calculation based on the hash codes of its superclass attributes
   * as well as the hash codes of the specific attributes of the truck object.
   *
   * @return a hash code value for the truck object.
   */
  @Override
  public int hashCode() {
    return super.hashCode() + Objects.hash(myLuxury, myNavigation, myDrivingAssistance);
  }

}
