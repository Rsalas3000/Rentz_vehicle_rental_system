package rentzsystemui;

import javax.swing.SwingUtilities;

/** 
 * The entry point class for the Rentz program.
 *
 * <p>This class contains the main method, which serves as the entry point for starting the
 *    Rentz application. When executed, it initializes the IntroAnimationController class
 *    for a proper introduction of the brand.
 *
 * @author Ricardo Salas
 *
 */
public class RentzStart {
  
  /**
   * The start method that creates an instance of the IntroAnimationController then
   * calling its own start method using the SwingUtilities.invokeLater(), which allows 
   * the instance to be queued on the Event Dispatch Thread (EDT).
   */
  private void start() {
    SwingUtilities.invokeLater(() -> {
      IntroAnimationController intro = new IntroAnimationController();
      intro.start();
    });
  }
  
  /** 
   * Initialization of the Rentz program. This calls the RentzStart object and invokes
   * its start method which in turn initializes the IntroAnimationController object.
   *
   */
  public static void main(String[] args) {
    RentzStart rentz =  new RentzStart();
    rentz.start();
  }

}
