package vehicles;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a jet ski available for rental, with attributes and methods to facilitate the 
 * rental process. Extends AbstractVehicle class to inherit basic vehicle attributes.
 *
 * @author Ricardo Salas
 *
 */
public class JetSki extends AbstractVehicle {

  /** Field variable to represent the water-based vehicle fee. */
  private static final BigDecimal MARINE_CHARGE = SPECIAL_FARE;
  
  /** Field variable to represent the towing fee. */
  private static final BigDecimal TOWING_CHARGE =
                  SPECIAL_FARE.multiply(new BigDecimal("0.01"));

  /** Field variable to represent the luxury charge. */
  private static final BigDecimal LUXURY_CHARGE =
                  SPECIAL_FARE.multiply(new BigDecimal("0.02"));

  /** Field variable to represent the sport fee. */
  private static final BigDecimal SPORT_CHARGE = SPECIAL_FARE.multiply(new BigDecimal("0.04"));

  /** Field variable represents the type of jet ski. */
  private JetskiType myJetskiType;

  /**
   * Parameterized constructor calls parent class constructor to initialize JetSki() field
   * variables.
   *
   * @param theName the name of the vehicle
   * @param theVin string VIN that every production vehicle should have.
   * @param theJetSkiType The type of jet ski.
   */
  public JetSki(final String theName, final String theVin,
                final String theDescription, final JetskiType theJetSkiType) {
    super(theName, theVin, theDescription);
    this.myJetskiType = theJetSkiType;
  }

  /**
   * Retrieves the type of jet ski.
   *
   *  @return my jet ski type
   */
  public JetskiType getMyJetSkiType() {
    return myJetskiType;
  }

  /**
   * Puts together details about the jet ski.
   *
   * @return an string representation of the jet ski class.
   */
  @Override
  public String toString() {
    return "Jet Ski (Name:" + getMyName() + ", VIN:" + getMyVin()
           + ", available?:" + isRentable() + ", skiType:" + myJetskiType + ")";
  }

  /**
   * Definition of the equals method from the one in the parent class.
   *
   * @param theOtherVehicle the comparator
   * @return true if jet ski matches other vehicle.
   */
  public boolean equals(final Object theOtherVehicle) {
  
    if (!(theOtherVehicle instanceof JetSki)) {
      return false;
    } 
    final JetSki otherJetSki = (JetSki) theOtherVehicle;
    return super.equals(otherJetSki) && myJetskiType == otherJetSki.myJetskiType;
  }

  /**
   * A calculation based on the hash codes of its superclass attributes
   * as well as the hash codes of the specific attributes of the jet ski object.
   *
   * @return a hash code value for the jet ski object.
   */
  public int hashCode() {
    return super.hashCode() + Objects.hash(myJetskiType);
  }

  /**
   * Adds to the amount charged, based on what kind of jet ski.
   *
   * @SuppressWarnings
   * 
   * @return rentalAmount Set to price.
   */
  @Override
  public BigDecimal calculateRentalAmount() {

    BigDecimal rentalAmount = MARINE_CHARGE;

    switch (myJetskiType) {
      case CRUISER:
        rentalAmount = rentalAmount.add(SPECIAL_FARE);
        break;
      case TOWING:
        rentalAmount = rentalAmount.add(TOWING_CHARGE);
        break;
      case LUXURY:
        rentalAmount = rentalAmount.add(LUXURY_CHARGE);
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
   * Represents the type of jet ski rental.
   */
  public enum JetskiType {
    /** Cruiser jet ski. */
    CRUISER,
    
    /** Towing jet ski. */
    TOWING,
  
    /** Luxury jet ski. */
    LUXURY,
    
    /** Sport jet ski. */
    SPORT
  }

}
