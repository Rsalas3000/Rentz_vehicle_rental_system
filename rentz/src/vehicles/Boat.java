package vehicles;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a boat available for rental, with attributes and methods to facilitate the 
 * rental process. Extends AbstractVehicle class to inherit basic vehicle rental attributes.
 *
 * @author Ricardo Salas
 *
 */
public class Boat extends AbstractVehicle {

  /** Field variable to represent the water-based vehicle fee. */
  public static final BigDecimal MARINE_CHARGE = SPECIAL_FARE;

  /** field variable to represent the surf boat charge. */
  public static final BigDecimal SURF_CHARGE = SPECIAL_FARE.multiply(new BigDecimal("0.01"));

  /** Field variable to represent the cruiser fee. */
  public static final BigDecimal CRUISER_CHARGE = SPECIAL_FARE.multiply(new BigDecimal("0.02"));

  /** Field variable to represent the sport fee. */
  public static final BigDecimal SPORT_CHARGE = SPECIAL_FARE.multiply(new BigDecimal("0.04"));

  /** Field variable represents the type of boat. */
  private BoatType myBoatType;

  /**
   * Parameterized constructor calls parent class constructor to initialize Boat() field
   * variables.
   *
   * @param theName of the vehicle.
   * @param theVin Vehicle Identification number.
   * @param theDescription Description of the vehicle.
   * @param theBoatType Type of boat.
   */
  public Boat(final String theName, final String theVin,
              final String theDescription, final BoatType theBoatType) {
    super(theName, theVin, theDescription);
    this.myBoatType = theBoatType;
  }

  /**
   * Retrieves the type of boat.
   *
   *  @return myBoatType
   */
  public BoatType getMyBicycleType() {
    return myBoatType;
  }

  /**
   * Puts together details about the boat.
   *
   *  @return an string representation of the boat class.
   */
  @Override
  public String toString() {
    return "Boat (Name:" + getMyName() + ", VIN:" + getMyVin() + ", available?:"
           + isRentable() + ", boatType:" + myBoatType + ")";
  }

  /**
   * Definition of the equals method from the one in the parent class.
   *
   * @param theOtherVehicle the comparator
   * @return boolean
   */
  public boolean equals(final Object theOtherVehicle) {
    if (!(theOtherVehicle instanceof Boat)) {
      return false;
    }
    final Boat otherBoat = (Boat) theOtherVehicle;
    return super.equals(otherBoat) && myBoatType == otherBoat.myBoatType;
  }

  /**
   * A calculation based on the hash codes of its superclass attributes
   * as well as the hash codes of the specific attributes of the boat object.
   *
   * @return a hash code value for the boat object.
   */
  @Override
  public int hashCode() {
    return super.hashCode() + Objects.hash(myBoatType);
  }

  /**
   * Adds to the amount to be billed based on type.
   *
   * @SuppressWarnings
   * @return rentalAmount set to price.
   */
  @Override
  public BigDecimal calculateRentalAmount() {

    BigDecimal rentalAmount = MARINE_CHARGE;

    switch (myBoatType) {
      case MOTORBOAT:
        rentalAmount = rentalAmount.add(SPECIAL_FARE);
        break;
      case SURF:
        rentalAmount = rentalAmount.add(SURF_CHARGE);
        break;
      case CRUISER:
        rentalAmount = rentalAmount.add(CRUISER_CHARGE);
        break;
      case SPORT:
        rentalAmount = rentalAmount.add(SPORT_CHARGE);
        break;
      default:
        break;
    }
    return rentalAmount;
  }

  /**
   * Represents the type of boat rental.
   *
   */
  public enum BoatType {
        /** Motor boat. */
        MOTORBOAT,       

        /** surf Boat. */
        SURF,
        
        /** Cruiser boat. */
        CRUISER,
        
        /** Sport Boat. */
        SPORT
  }

}
