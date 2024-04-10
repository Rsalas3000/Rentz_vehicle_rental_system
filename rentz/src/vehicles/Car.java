package vehicles;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a car available for rental, with attributes and methods to facilitate the 
 * rental process. Extends AbstractVehicle class to inherit basic vehicle attributes.
 *
 * @author Ricardo Salas
 *
 */
public class Car extends AbstractVehicle {

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
   * Parameterized constructor calls parent class constructor to initialize Car() field
   * variables.
   *
   * @param theName of the vehicle.
   * @param theVin Vehicle Identification number.
   * @param theAvailabilityStatus availability of the vehicle.
   * @param theLuxury features of the vehicle.
   * @param theNavigation features of the vehicle.
   * @param theDrivingAssistance features of the vehicle.
   */
  public Car(final String theName, final String theVin,
             final String theDiscription, final boolean theAvailabilityStatus, 
             final boolean theLuxury, final boolean theNavigation,
             final boolean theDrivingAssistance) {

    super(theName, theVin, theDiscription);

    this.myLuxury = theLuxury;
    this.myNavigation = theNavigation;
    this.myDrivingAssistance = theDrivingAssistance;
  }

  /**
   * Verifies whether the rental is considered a luxury item.
   *
   * @return true if vehicle is of status, false otherwise.
   */
  public boolean getIsMyLuxury() {
    return myLuxury;
  }

  /**
   * Retrieves if the vehicle has navigation or not.
   *
   * @return true if vehicle has navigation, false otherwise.
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
   * Adds all extra feature fees of rental.
   *
   * @return RentalAmount
   */
  public BigDecimal calculateRentalAmount() {
    BigDecimal theRentalAmount = CAR_FARE;
    if (myLuxury) {
      theRentalAmount = theRentalAmount.add(LUXURY_CHARGE);
    }
    if (myNavigation) {
      theRentalAmount = theRentalAmount.add(NAVIGATION_CHARGE);
    }
    if (myDrivingAssistance) {
      theRentalAmount = theRentalAmount.add(DRIVING_ASSISTANCE_CHARGE);
    }
    return theRentalAmount;
  }

  /**
   * Puts together details about the car.
   *
   * @return an string representation of the car class.
   */
  @Override
  public String toString() {
    
    return "Car (Name:" + getMyName() + ", VIN:" + getMyVin()
           + ", available?:" + isRentable() + ", IsLuxury?:" + myLuxury
           + ", HasNavigation?:" + myNavigation + ", HasAssistance?:" + myDrivingAssistance
           + ")";
  }

  /**
   * Redefinition of the equals method from the one of the parent class.
   *
   * @return true if vehicle is equal to the other vehicle, false otherwise.
   */
  @Override
  public boolean equals(final Object theOtherVehicle) {
    if (!(theOtherVehicle instanceof Car)) {
      return false;
    }
    final Car otherCar = (Car) theOtherVehicle;
    return super.equals(otherCar)
           && myLuxury == otherCar.myLuxury
           && myNavigation == otherCar.myNavigation
           && myDrivingAssistance == otherCar.myDrivingAssistance;
  }

  /**
   * A calculation based on the hash codes of its superclass attributes
   * as well as the hash codes of the specific attributes of the car object.
   *
   * @return a hash code value for the car object.
   */
  @Override
  public int hashCode() {
    return super.hashCode() + Objects.hash(myLuxury, myNavigation, myDrivingAssistance);
  }

}
