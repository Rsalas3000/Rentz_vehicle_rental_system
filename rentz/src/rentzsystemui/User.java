package rentzsystemui;

import java.util.Objects;

/**
 * This class embodies a singular user for registration or sign-in purposes.
 * The user object is immutable. Should the parameters be null, both constructor
 * methods within this class will throw a NullPointerException.
 *
 * @author Ricardo Salas
 */
public final class User {

  /** Declaration the user name. */
  private String myName;

  /** Declaration of the user's password. */
  private String myPassword;

  /** Declaration of the user's VIP status. */
  private boolean myVipStatus;
    
  /**
   * This parameterized constructor is called when an User object is created only with user
   * name and password. VIPStatus default value is false
   *
   * @param theUserName String value of the identity of the user.
   * @param thePassword A unique string to securely give access to the user.
   */
  public User(final String theUserName, final String thePassword) {
    this(theUserName, thePassword, false);
  }

  /**
   * Parameterized constructor with user name, password and VIPStatus. It throws exception is
   * name or password is empty
   *
   * @param theName  The entered user name
   * @param thePassword The entered password
   * @param theVipStatus The entered VIP status
   * @throws NullPointerException For null parameters
   * @throws IllegalArgumentException if the name or password is empty.
   */
  public User(final String theName, final String thePassword, final Boolean theVipStatus) {

    myName = Objects.requireNonNull(theName);
    myPassword = Objects.requireNonNull(thePassword);
    myVipStatus = Objects.requireNonNull(theVipStatus);

    if (theName.isEmpty() || thePassword.isEmpty()) { 
                                  
      throw new IllegalArgumentException();
    }
  }
  
  /**
   * Retrieves the name of the user.
   *
   *  @return myName
   */
  public String getMyName() {
    return myName;
  }

  /**
   * Retrieves the password of the user.
   *
   *  @return myPassword
   */
  public String getMyPassword() {
    return myPassword;
  }

  /**
   * Retrieves boolean value on whether
   * the user signed up as a RENTZ VIP member.
   *
   *  @return myVipStatus
   */
  public Boolean getMyVipStatus() {
    return myVipStatus;
  }


  /**
   *  returns a string representation of user.
   *
   * @return sb.toString()
   */
  public String toString() {

    final StringBuilder sb = new StringBuilder();

    final String comma = ", ";

    sb.append(getClass().getSimpleName());
    sb.append("(");
    sb.append(this.getMyName());
    sb.append(comma);
    sb.append(this.getMyPassword());
    sb.append(comma);
    sb.append(this.getMyVipStatus());
    sb.append(")");

    return sb.toString();
  }

  /**
   * check the state of the two objects to see if they're equal return true if they match,
   * false if they don't.
   *
   * @param theOtherObject The comparator
   * @return result
   */
  public boolean equals(final Object theOtherObject) {

    boolean result = false;
    if (this == theOtherObject) {
      result = true;
    } else if (theOtherObject == null) {
      result = false;
    } else if (this.getClass() != theOtherObject.getClass()) {
      result = false;
    } else {
      final User other = (User) theOtherObject;
      result = Objects.equals(myName, other.myName)
                && Objects.equals(myPassword, other.myPassword)
               && Objects.equals(myVipStatus, other.myVipStatus);
    }
    return result;
  }

  /**
   * Create the hash code to get the user name, password and VIPStatus.
   *
   * @return hash code the hashed sequence of values
   */
  @Override
  public int hashCode() {
    return Objects.hash(myName, myPassword, myVipStatus);

  }
}
