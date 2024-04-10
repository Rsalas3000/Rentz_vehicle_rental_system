package rentzsystemui;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import vehicles.AbstractVehicle;


/**
 * Bill object to return to the current user after
 * completing renting process.
 *
 * @author Ricardo Salas
 *
 */
public class Bill {

  /** * Currency format for US currency system. */
  public static final NumberFormat NF = NumberFormat.getCurrencyInstance(Locale.US);

  /** * A unique integer value. */
  private final int myBillId;

  /** * A User object. */
  private final User myPrimaryUser;

  /** * A Vehicle object. */
  private final AbstractVehicle myVehicle;

  /** * An integer representing the number of days Vehicle is rented. */
  private final int myNumDays;

  /** * Total bill amount. */
  private BigDecimal myBillAmount;

  /**
   * Constructs a new bill class with the provided parameters.
   * This constructor initializes the relevant instance fields of the Bill class.   *
   *
   * @param theBillId A unique integer value assigned to the bill.
   * @param thePrimaryUser A User object.
   * @param theVehicle A Vehicle object.
   * @param theNumDays An integer representing the number of days Vehicle is rented.
   */
  public Bill(final int theBillId, final User thePrimaryUser,
                final AbstractVehicle theVehicle, final int theNumDays) {

    this.myBillId = theBillId;
    this.myPrimaryUser = thePrimaryUser;
    this.myVehicle = theVehicle;
    this.myNumDays = theNumDays;
    this.myBillAmount = new BigDecimal("0.0");
  }

  /**
   * Computes and prints the total bill amount.
   *
   * @param theOutput The print stream to allow multiple outputs of the bill.
   */
  public void computeAndPrintAmount(final PrintStream theOutput) {

    theOutput.println("----Cost Information----");
    theOutput.println("RentalPerDay:");
        
    final BigDecimal calculateRent = myVehicle.calculateRentalAmount();
    theOutput.println("Cost per Day: " + NF.format(calculateRent));
    theOutput.println("No.of Rental days: " + myNumDays);

    final BigDecimal numOfDays = new BigDecimal(myNumDays);

    final BigDecimal totalAmountBefore = calculateRent.multiply(numOfDays);
    theOutput.println("Total Amount: " + NF.format(totalAmountBefore));

    final BigDecimal onePercentRate = new BigDecimal("0.01");

    final BigDecimal insurance = totalAmountBefore.multiply(onePercentRate);
    theOutput.println("Insurance: " + NF.format(insurance));

    BigDecimal vipStatus = new BigDecimal("0.00");
    String vipDiscount = "VIPDiscount: ";

    if (myPrimaryUser.getMyVipStatus()) {
      vipDiscount = "VIPDiscount: -";
      vipStatus = totalAmountBefore.multiply(onePercentRate);
      theOutput.println(vipDiscount + NF.format(vipStatus));

    } else {
      theOutput.println("VIPDiscount: " + NF.format(vipStatus));

    }

    final BigDecimal tax = totalAmountBefore.multiply(new BigDecimal("0.1"));
    theOutput.println("Tax: " + NF.format(tax));

    myBillAmount = totalAmountBefore.add(insurance).subtract(vipStatus).add(tax);

    theOutput.println("Total Rent: " + NF.format(myBillAmount));
        
        
    JOptionPane.showMessageDialog(null,
        "----Cost Information---- \n"
        + " Rental per day: "
        + "\n Cost per day: " + NF.format(calculateRent)
        + "\n No. of Rental days: " + numOfDays
        + "\n Total Amount: " + NF.format(totalAmountBefore)
        + "\n Insurance: " + NF.format(insurance)
        + "\n" + vipDiscount + NF.format(vipStatus)
        + "\n Tax: " + NF.format(tax)
        + "\n Total Rent: " + NF.format(myBillAmount));
  }

  /**
   * retrieves the bill Id.
   *
   *@return myBillId
   */
  public int getBillId() {
    return myBillId;
  }
}
