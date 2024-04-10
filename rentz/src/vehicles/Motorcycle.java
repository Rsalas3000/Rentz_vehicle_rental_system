package vehicles;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a motorcycle available for rental, with attributes and methods to facilitate the 
 * rental process. Extends AbstractVehicle class to inherit basic vehicle attributes.
 *
 * @author Ricardo Salas
 *
 */
public class Motorcycle extends AbstractVehicle {

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
   * Parameterized constructor calls parent class constructor to initialize Motorcycle() field
   * variables.
   *
   * @param theName of the vehicle.
   * @param theVin Vehicle Identification number.
   * @param availablityStatus availability of the vehicle.
   * @param theLuxury features of the vehicle.
   */
  public Motorcycle(final String theName, final String theVin,
                    final String theDescription, final boolean availablityStatus, 
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
   *  @return myLuxury
   */
  public boolean getIsMyLuxury() {
    return myLuxury;
  }

  /**
   * Retrieves a boolean on whether vehicle has navigation.
   *
   *  @return myNavigation
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
   * Adds to the amount charged on what type of motorcycle.
   *
   *  @return RentalAmount to price.
   *  
   */
  public BigDecimal calculateRentalAmount() {
    BigDecimal rentalAmount = MOTORCYCLE_PREMIUM;
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
   * A string representation of the Motorcycle class.
   *
   * @return String The relevant information of the motorcycle.
   */
  @Override
  public String toString() {
    return "Motorcycle (Name:" + getMyName() + ", VIN:" + getMyVin()
           + ", available?:" + isRentable() + ", IsLuxury?:" + myLuxury
           + ", HasNavigation?:" + myNavigation + ", HasAssistance?:" + myDrivingAssistance + ")";
  }
  
  /**
   * Redefinition of the equals method from the one of the parent class.
   *
   * @return true if vehicle is equal to the other vehicle, false otherwise.
   */
  @Override
  public boolean equals(final Object theOtherVehicle) {
    if (!(theOtherVehicle instanceof Motorcycle)) {
      return false;
    } 
    final Motorcycle otherMotorcyle = (Motorcycle) theOtherVehicle;
    return super.equals(otherMotorcyle)
           && myLuxury == otherMotorcyle.myLuxury
           && myNavigation == otherMotorcyle.myNavigation
           && myDrivingAssistance == otherMotorcyle.myDrivingAssistance;
  }

  /**
   * A calculation based on the hash codes of its superclass attributes
   * as well as the hash codes of the specific attributes of the motorcycle object.
   *
   * @return a hash code value for the Motorcycle object.
   */
  @Override
  public int hashCode() {
    return super.hashCode() + Objects.hash(myLuxury, myNavigation, myDrivingAssistance);
  }
}
