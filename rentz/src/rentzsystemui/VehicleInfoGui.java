package rentzsystemui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import vehicles.AbstractVehicle;
import vehicles.Boat;
import vehicles.Car;
import vehicles.EcoCar;
import vehicles.JetSki;
import vehicles.Motorcycle;
import vehicles.Quad;
import vehicles.Truck;
        
/**
 * The Vehicleinfogui class makes possible for the selection of rentals and dropping them off
 * by user. This GUI provides functionalities for choosing rentals, viewing
 * rental information and gives actions for users. The class contains components
 * for displaying rentals by categorizing the type of vehicle in turn
 * giving an organized and user-friendly experience.
 *
 * @author Ricardo Salas
 *
 */
public class VehicleInfoGui extends JFrame {
  private static final long serialVersionUID = 6108083172156753877L;
  /** Represents a list of vehicle names for selection and display. */
  private JList<String> myVehList;
  
  /** Represents a text area to display information upon vehicle selection. */
  private JTextArea myInfoTextArea;
  
  /** Represents a text area to display current rentals of the current user. */
  private JTextArea myInfoCurrTextArea;
  
  /** A map that stores information on vehicles. The names of the vehicles are keys, and the
   * values are the corresponding Abstract Vehicle object.  
   */
  public static Map<String, AbstractVehicle> myVehInfoMap = new HashMap<String, AbstractVehicle>();

  /** Vehicle name selected when choosing from itemList. */
  public String chosenVehName = null;
    
  /** vehicle id picked from itemList. */
  public int chosenVehId = -1;
    
  /** Chosen vehicle rental status instantiated. */
  public boolean chosenVehStatus;
  
  /** still images that help categorize by representing each type of vehicle. */
  private String[] myStillImages = { "car.jpeg", "eco-car.jpeg",
                                     "truck.jpeg", "motorcycle.jpeg",
                                     "quad.jpeg", "boat.jpeg", "jetski.jpeg" };

  /** Implementing image transitions triggered by mouse roll over to animate button interactions. */
  private String[] myMovingImages = { "car.gif", "eco-car.gif",
                                      "truck.gif", "motorcycle.gif",
                                      "quad.gif", "boat.gif", "jetski.gif" };
  
  /** The quantity of buttons needed for each type of vehicle. */
  private static final int NUM_OF_BUTTONS = 7;
  
  /** The size for user friendly navigation for vehicle category choosing. */
  private static final Dimension BUTTON_SIZE = new Dimension(200, 200); // big and bright

  /** The size for user friendly navigation for user actions. */
  private static final Dimension LOWER_BUTTON_SIZE  = new Dimension(100, 100);
  
  /** Registration class instantiated. */
  private final Registration myReg = new Registration();
  
  /** A panel for when the user wants to go back to login. */
  private JPanel myLoginPanel;
  
  /** The frame that is used as a coupling as a base for UI. */
  private JFrame myFrame;
  
  /** The GUI login component. */
  private UserLoginGui myLoginGui;
  
  /** The constructor that uses the myFrame as coupling. */
  public VehicleInfoGui(JFrame theFrame) {
    this.myFrame = theFrame;
  }
  
  /** 
   *Initiates the Vehiclelinfogui component.
   *
   * @param theFrame JFrame where all activity flows 
   *        and gets its components instantiated.
   */
  public void start(JFrame theFrame) {

    createVehicles();
    setTheRented(myVehInfoMap, theCurrentlyRented);

    myVehList = new JList<>();
    myVehList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    myVehList.addListSelectionListener(this::itemListSelectionChanged);

    myInfoTextArea = new JTextArea();
    myInfoTextArea.setEditable(false);
    myInfoTextArea.setLineWrap(true);
    myInfoTextArea.setWrapStyleWord(true);
    myInfoTextArea.setBackground(UIManager.getColor("TextArea.background"));

    JScrollPane myinfoSpane = new JScrollPane(myInfoTextArea);
    myinfoSpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    JButton[] myVehButtons = createButtonsFromPath(myStillImages, myMovingImages);
    addActionListenerToButtons(myVehButtons);
    JPanel myBsPane = createButtonPanel(myVehButtons);

    final JTabbedPane myTabbedPane = createTabbedPane();

    addCompsToContPane(theFrame, myBsPane, myinfoSpane, myTabbedPane);

    theFrame.setLocationRelativeTo(null);
    theFrame.setVisible(true);
  }
  
  /** 
   * Creates a map for vehicle objects.
   *
   * <p>This method instantiates various types of vehicles, such as cars, economical cars, trucks,
   *  motorcycles, quads, boats, and jet skis then populates them into a map. Each Vehicle object
   *  has its unique name as the key in the map. Each are initialized with relevant details, such
   *  as name, VIN, description, and rental availability. Once instantiated, the vehicles are added
   *  to the map, allowing easy retrieval based on their names.
   *
   * @return myVehInfoMap A map containing vehicle objects with their names as keys.
   */
  private final Map<String, AbstractVehicle> createVehicles() {
    // Car category
    final Car car1 = new Car("Toyota Camry", "V008",
                             "The Toyota Camry is a midsize sedan known for its reliability,"
                             + " comfort, and practicality. It offers a smooth ride, spacious"
                             + " interior, and a range of fuel-efficient engines. With a reputation"
                             + " for durability and value, the Camry appeals to a broad"
                             + " seeking a comfortable and dependable daily driver.", 
                             true, false, false, false);

    final Car car2 = new Car("Honda Accord", "V007", 
                             "The Honda Accord is another popular midsize sedan known for its"
                             + " refine driving dynamics, spacious cabin, and strong resale"
                             + " value. It offers a comfortable ride, responsive handling, and"
                             + " a host of standard safety features. The Accord's reputation for"
                             + " reliability and fuel efficiency makes it a top choice among"
                             + " midsize car drivers.", 
                             true, true, true, false);

    final Car car3 = new Car("Toyota RAV4", "V006",
                             "The Toyota RAV4 is a compact crossover SUV known for its reliability,"
                             + " versatility, and strong resale value. It offers a spacious"
                             + " interior, comfortable ride, and available all-wheel drive for"
                             + " added traction in inclement weather. With its fuel-efficient"
                             + " engines and abundance of standard safety features, the RAV4"
                             + " is a popular choice among small SUV buyers.",
                             true, true, true, true);

    final Car car4 = new Car("Honda CR-V", "V005",
                             "The Honda CR-V is another compact crossover SUV known for its"
                             + " practicality, comfort, and efficiency. It offers a roomy and"
                             + " well-appointed interior, smooth ride quality, and responsive"
                             + " handling. With its reputation for reliability and"
                             + " family-friendly features, the CR-V remains a top-selling SUV"
                             + " in its segment.", 
                             true, true, true, false);

    final Car car5 = new Car("Jeep Wrangler", "V004",
                             "The Jeep Wrangler is an iconic off-road SUV known for its ruggedness,"
                             + " capability, and open-air driving experience. It offers a range"
                             + " of four-wheel-drive systems, robust suspension, and removable roof"
                             + " and doors for outdoor adventures. With its distinctive styling and"
                             + " legendary off-road prowess, the Wrangler appeals to outdoor"
                             + " enthusiasts and off-road enthusiasts alike.",
                             true, true, true, true);

    final Car car6 = new Car("Porche 911", "V003",
                             "The Porsche 911 is a legendary sports car known for its iconic"
                             + " design, precision engineering, and exhilarating performance."
                             + " It offers a rear-engine layout, responsive handling, and a"
                             + " range of powerful flat-six engines. With its timeless styling"
                             + " and motorsport heritage, the 911 remains one of the most coveted"
                             + " sports cars in the world.",
                             true, true, true, true);

    final Car car7 = new Car("Lexus RX", "V002",
                             "The Lexus RX is a midsize luxury SUV known for its smooth ride,"
                             + " spacious cabin, and high-quality materials. It offers a range"
                             + " of powerful and efficient engines, along with a long list of"
                             + " standard and available features such as advanced safety systems"
                             + " and luxury amenities. With its reputation for reliability and"
                             + " comfort, the RX is a top choice among luxury SUV buyers.",
                             true, true, true, false);

    final Car car8 = new Car("Volvo XC90", "V001",
                             "The Volvo XC90 is a luxury midsize SUV known for its safety features,"
                             + " upscale interior, and Scandinavian design aesthetic. It offers a"
                             + " range of powerful and efficient engines, along with advanced"
                             + " driver-assistance technologies such as automatic emergency braking"
                             + " and lane-keeping assist. With its emphasis on safety and comfort,"
                             + " the XC90 appeals to families and luxury SUV buyers.",
                             true, true, true, true);

    // EcoCar category
    final EcoCar ecocar1 = new EcoCar("Toyota Camry", "V008",
                                      "The Toyota Corolla is a compact sedan known for its"
                                      + " reliability, fuel efficiency, and practicality."
                                      + " It offers a comfortable ride, ample interior space,"
                                      + " and a reputation for low maintenance costs. With a"
                                      + " history spanning decades, it has remained one of the"
                                      + " best-selling cars globally.",
                                      true, false, false, false);

    final EcoCar ecocar2 = new EcoCar("Honda Civic", "V007",
                                      "The Honda Civic is a versatile compact car available in"
                                      + " sedan, coupe, and hatchback body styles. Known for its"
                                      + " sporty design, efficient engines, and engaging driving"
                                      + " dynamics, the Civic is a popular choice among a wide"
                                      + " range of consumers. It offers a comfortable ride,"
                                      + " spacious cabin, and a host of standard safety features.",
                                      true, true, true, false);

    final EcoCar ecocar3 = new EcoCar("Volkswagen Golf", "V006",
                                      "The Volkswagen Golf is a compact hatchback renowned for its"
                                      + " solid build quality, refined interior, and enjoyable"
                                      + " driving experience. With responsive handling, a range of"
                                      + " engine options, versatile cargo space, the Golf appeals"
                                      + " to drivers seeking both practicality and driving"
                                      + " enjoyment.",
                                      true, true, true, true);

    final EcoCar ecocar4 = new EcoCar("Ford Focus", "V005",
                                      "The Ford Focus is a compact car available as a sedan or"
                                      + " hatchback. It offers a comfortable ride, responsive"
                                      + " handling, and a range of technology features, making"
                                      + " it a popular choice among budget-conscious consumers."
                                      + " With its stylish design and fuel-efficient engines,"
                                      + " the Focus appeals to urban commuters and small families"
                                      + " alike.",
                                      true, true, true, false);

    final EcoCar ecocar5 = new EcoCar("Chevrolet Cruze", "V004",
                                      "The Chevrolet Cruze is a compact sedan known for its fuel"
                                      + " efficiency, spacious interior, and user-friendly"
                                      + " technology. It offers a smooth ride and a range of"
                                      + " safety features, making it a practical choice for"
                                      + " everyday driving. With its competitive pricing and"
                                      + " modern design, the Cruze remains a popular option"
                                      + " in the economy car segment.",
                                      true, true, true, true);

    final EcoCar ecocar6 = new EcoCar("Nissan Sentra", "V003",
                                      "The Nissan Sentra is a compact sedan with a spacious cabin,"
                                      + " smooth ride, and good fuel economy. It offers a balance"
                                      + " of comfort and agility, along with a variety of available"
                                      + " features such as a user-friendly infotainment system and"
                                      + " advanced safety technologies. The Sentra's reliability"
                                      + " and affordability make it a popular choice among"
                                      + " budget-conscious consumers.",
                                      true, true, true, true);

    final EcoCar ecocar7 = new EcoCar("Hyundai Elantra", "V002",
                                      "The Hyundai Elantra is a compact sedan known for its value,"
                                      + " reliability, and generous standard features. It offers a"
                                      + " comfortable ride, efficient engines, and a user-friendly"
                                      + " infotainment system. With its stylish design and"
                                      + " competitive pricing, the Elantra appeals to buyers"
                                      + " seeking an affordable yet well-equipped compact car.",
                                      true, true, true, false);

    final EcoCar ecocar8 = new EcoCar("Kia Forte", "V001",
                                      "The Kia Forte is a compact sedan available with a range of"
                                      + " features and options. It offers a stylish design,"
                                      + " comfortable interior, and competitive pricing, making it"
                                      + " an attractive choice for budget-conscious buyers. With"
                                      + " its efficient engines and comprehensive warranty"
                                      + " coverage, the Forte delivers a compelling value"
                                      + " proposition in the economy car segment.",
                                      true, true, true, true);

    // Truck category
    final Truck truck1 = new Truck("Dodge Ram 1500", "V100",
                                   "The Ram 1500 is a full-size pickup truck known for its"
                                    + " luxurious interior, smooth ride, and innovative features."
                                    + " It offers a powerful engine, includes advanced technology"
                                    + " features such as a large touchscreen infotainment system"
                                    + " and available air suspension for improved ride quality."
                                    + " With its upscale design and comfortable cabin, the Ram"
                                    + " 1500 sets a high standard for full-size trucks.",
                                    true, false, false, false);

    final Truck truck2 = new Truck("Toyota Tacoma", "V100",
                                   "The Toyota Tacoma is a midsize pickup truck known for its"
                                    + " off-road capability, reliability, and resale value. It"
                                    + " is a rugged TRD Pro model. With its robust construction"
                                    + " and Toyota's reputation for durability, the Tacoma is a"
                                    + " popular choice among outdoor enthusiasts and adventure"
                                    + " seekers.",
                                    true, false, false, false);

    final Truck truck3 = new Truck("Ford F-150", "V100",
                                   "The Ford F-Series is a lineup of full-size pickup trucks known"
                                   + " for their versatility, capability, and durability. The F-150"
                                   + " truck caters to a wide range of customers, from casual"
                                   + " drivers to heavy-duty haulers. They offer spacious cabins,"
                                   + " advanced technology features, and impressive towing and"
                                   + " payload capacities, making them one of the best-selling"
                                   + " vehicles in the United States for decades.",
                                   true, false, false, false);

    final Truck truck4 = new Truck("Chevrolet Silverado", "V100",
                                   "The Chevrolet Silverado is another full-size pickup truck"
                                   + " series known for its ruggedness, performance, and"
                                   + " dependability. It offers a range of powerful engines,"
                                   + " advanced towing technologies, and spacious interiors"
                                   + " with modern amenities. With its bold styling and"
                                   + " versatile configurations, the Silverado is a top choice"
                                   + " among truck enthusiasts and commercial users alike.",
                                   true, false, false, false);

    final Truck truck5 = new Truck("Toyota Tundra", "V100",
                                   "The Toyota Tundra is a full-size pickup truck known for its"
                                   + " reliability, towing capacity, and spacious cabin. It offers"
                                   + " a powerful V8 engine, along with standard safety features"
                                   + " such as Toyota Safety Sense P. With its Toyota build quality"
                                   + " and reputation for longevity, the Tundra is a popular choice"
                                   + " among truck buyers seeking a dependable and capable"
                                   + " vehicle.",
                                   true, false, false, false);

    final Truck truck6 = new Truck("Nissan Frontier", "V100",
                                   "The Nissan Frontier is a midsize pickup truck known for its"
                                   + " affordability, reliability, and ruggedness. It offers a"
                                   + " choice of engines, including a powerful V6 option, along"
                                   + " with available four-wheel drive for off-road capability."
                                   + " With its straightforward design and proven durability,"
                                   + " the Frontier is a popular choice among budget-conscious"
                                   + " truck buyers.",
                                   true, false, false, false);

    final Truck truck7 = new Truck("GMC Sierra", "V100",
                                   "The GMC Sierra is a full-size pickup truck known for its"
                                   + " premium features, upscale interior, and professional-grade"
                                   + " capability. It offers a range of powerful engines, including"
                                   + " a diesel option, along with innovative features such as the"
                                   + " MultiPro tailgate and the ProGrade Trailering System. With "
                                   + "its distinctive styling and refined craftsmanship, the Sierra"
                                   + " appeals to discerning truck buyers seeking luxury and"
                                   + " performance.",
                                   true, false, false, false);

    final Truck truck8 = new Truck("Ford Raptor", "V100",
                                   "The Ford Raptor is a high-performance off-road pickup truck"
                                   + " known for its ruggedness, capability, and aggressive design."
                                   + " Developed by Ford's Special Vehicle Team (SVT), later known"
                                   + " as Ford Performance, the Raptor is designed to conquer"
                                   + " challenging terrain with ease while providing exhilarating"
                                   + " performance on and off the road.",
                                   true, false, false, false);


    // Motorcycle Category
    final Motorcycle motorcycle1 = new Motorcycle("Honda CB650R", "B100",
                                                  "The Honda CB650R is a naked sportbike known"
                                                  + " for its sleek design, agile handling, and"
                                                  + " versatile performance. It features a powerful"
                                                  + " inline-four engine, responsive brakes, and"
                                                  + " adjustable suspension. With its comfortable"
                                                  + " riding position and modern styling, the"
                                                  + " CB650R appeals to riders seeking a blend"
                                                  + " of performance and everyday usability.",
                                                  true, false, false, false);

    final Motorcycle motorcycle2 = new Motorcycle("Yamaha R6", "B101",
                                                  "The Yamaha R6 is a high-performance"
                                                  + " supersport motorcycle known for its"
                                                  + " track-ready performance, aggressive"
                                                  + " styling, and advanced technology. It"
                                                  + " features a potent inline-four engine,"
                                                  + " race-inspired chassis, and aerodynamic"
                                                  + " bodywork. With its razor-sharp handling"
                                                  + " and race-winning pedigree, the R6 is a"
                                                  + " top choice among dedicated sportbike"
                                                  + " enthusiasts.",
                                                  true, true, false, false);

    final Motorcycle motorcycle3 = new Motorcycle("Kawasaki Ninja 650", "B100",
                                                  "The Kawasaki Ninja 650 is a sporty middleweight"
                                                  + " motorcycle known for its balanced"
                                                  + " performance, comfortable ergonomics,"
                                                  + " and user-friendly nature. It features"
                                                  + " a responsive parallel-twin engine, nimble"
                                                  + " handling, and aggressive styling inspired"
                                                  + " by Kawasaki's supersport models. With its"
                                                  + " versatility and affordability, the Ninja 650"
                                                  + " is a popular choice among both new and"
                                                  + " experienced riders.",
                                                  true, false, false, false);

    final Motorcycle motorcycle4 = new Motorcycle("Suzuki GSX-R750", "B100",
                                                  "The Suzuki GSX-R750 is a legendary sportbike"
                                                  + " known for its winning heritage, exceptional"
                                                  + " handling, and exhilarating performance. It"
                                                  + " features a responsive inline-four engine,"
                                                  + " race-derived suspension, and lightweight"
                                                  + " chassis. With its race-proven technology"
                                                  + " and precision engineering, the GSX-R750"
                                                  + " offers an unmatched combination of power"
                                                  + " and agility on both the street and the"
                                                  + " track.",
                                                  true, false, false, false);

    final Motorcycle motorcycle5 = new Motorcycle("Ducati Monster 821", "B101",
                                                  "The Ducati Monster 821 is a naked sportbike"
                                                  + " known for its Italian style, spirited"
                                                  + " performance, and agile handling. It features"
                                                  + " a torquey V-twin engine, premium components,"
                                                  + " and modern electronics. With its minimalist"
                                                  + " design and urban-friendly characteristics,"
                                                  + " the Monster 821 offers a thrilling riding"
                                                  + " experience for enthusiasts seeking a blend"
                                                  + " of performance and style.",
                                                  true, true, false, false);

    final Motorcycle motorcycle6 = new Motorcycle("BMW R1250GS", "B100",
                                             "The BMW R1250GS is an adventure touring motorcycle"
                                             + " known for its off-road capability, long-distance"
                                             + " comfort, and innovative technology. It features a"
                                             + " potent boxer-twin engine, advanced electronics,"
                                             + " and adjustable suspension. With its rugged design"
                                             + " and versatile performance, the R1250GS is a"
                                             + " popular choice for adventure riders exploring both"
                                             + " paved and unpaved roads around the world.",
                                             true, false, false, false);

    final Motorcycle motorcycle7 = new Motorcycle("Harley-Davidson Sportster Iron 883", "B100",
                                                  "The Harley-Davidson Sportster Iron 883 is an"
                                                  + " iconic cruiser known for its classic styling,"
                                                  + " raw power, and rich heritage. It features a"
                                                  + " torquey V-twin engine, low-slung profile, and"
                                                  + " minimalist design. With its customizable"
                                                  + " options and timeless appeal, the Sportster"
                                                  + " Iron 883 attracts riders looking for a"
                                                  + " traditional American cruiser experience.",
                                                  true, false, false, false);

    final Motorcycle motorcycle8 = new Motorcycle("Triumph Bonneville T120", "B101",
                                                  "The Triumph Bonneville T120 is a modern classic"
                                                  + " motorcycle known for its timeless design,"
                                                  + " refined performance, and British heritage."
                                                  + " It features a torquey parallel-twin engine,"
                                                  + " retro styling cues, and modern amenities such"
                                                  + " as ABS and traction control. With its smooth"
                                                  + " power delivery and iconic silhouette, the"
                                                  + " Bonneville T120 embodies the spirit of"
                                                  + " classic motorcycling with modern"
                                                  + " sophistication",
                                                  true, true, false, false);



    // Quad Category
    final Quad quad1 = new Quad("Yamaha Raptor 700R", "V102",
                                "The Yamaha Raptor 700R is a powerful sport ATV known for its"
                                + " muscular engine, comfortable ergonomics, and impressive"
                                + " handling. It features a fuel-injected, liquid-cooled engine,"
                                + " sport-tuned suspension, and aggressive styling. With its"
                                + " broad powerband and responsive throttle, the Raptor 700R"
                                + " offers exhilarating performance for riders of all skill"
                                + " levels.",
                                true, true, true, true);

    final Quad quad2 = new Quad("Honda TRX450R", "V102",
                                "The Honda TRX450R is a high-performance sport ATV known for its"
                                + " agility, power, and durability. It features a liquid-cooled,"
                                + " four-stroke engine, lightweight chassis, and long-travel"
                                + " suspension for aggressive off-road riding. With its"
                                + " race-inspired design and proven reliability, the TRX450R"
                                + " is a top choice for enthusiasts seeking adrenaline-pumping"
                                + " performance on the trails and track.",
                                true, true, true, true);

    final Quad quad3 = new Quad("Polaris Sportsman 570", "V102",
                                "The Polaris Sportsman 570 is a versatile utility ATV known for its"
                                + " ruggedness, versatility, and ease of use. It features a"
                                + " fuel-injected, four-stroke engine, independent rear suspension,"
                                + " and selectable four-wheel drive for confident traction in all"
                                + " conditions. With its towing and hauling capability, ample"
                                + " storage, and comfortable ride, the Sportsman 570 is a popular"
                                + " choice for work and recreation.",
                                true, true, true, true);

    final Quad quad4 = new Quad("Can-Am Outlander 650", "V102",
                                "The Can-Am Outlander 650 is a powerful utility ATV known for its"
                                + " performance, comfort, and innovation. It features a Rotax"
                                + " V-twin engine, dynamic power steering, and advanced suspension"
                                + " technology for smooth handling and control. With its versatile"
                                + " cargo racks, towing capacity, and customizable accessories, the"
                                + " Outlander 650 is well-suited for both work and play.",
                                true, true, true, true);

    final Quad quad5 = new Quad("Suzuki KingQuad 750AXi", "V102",
                                "The Suzuki KingQuad 750AXi is a rugged utility ATV known for its"
                                + " durability, power, and versatility. It features a"
                                + " fuel-injected, four-stroke engine, independent suspension,"
                                + " and selectable four-wheel drive with differential lock for"
                                + " maximum traction. With its robust construction and comfortable"
                                + " ergonomics, the KingQuad 750AXi is a dependable workhorse for"
                                + " demanding tasks and adventurous rides.",
                                true, true, true, true);

    final Quad quad6 = new Quad("Kawasaki brute Force 750", "V102",
                                "The Kawasaki Brute Force 750 is a robust utility ATV known for its"
                                + " brute strength, rugged design, and impressive towing capacity."
                                + " It features a fuel-injected V-twin engine, fully independent"
                                + " suspension, and Kawasaki's Variable Front Differential Control"
                                + " for enhanced traction. With its durable construction and smooth"
                                + " power delivery, the Brute Force 750 is ready to tackle tough"
                                + " jobs and challenging terrain.",
                                true, true, true, true);

    final Quad quad7 = new Quad("Arctic Cat Alterra 700", "V102",
                                "The Arctic Cat Alterra 700 is a versatile utility ATV known for"
                                + " its reliability, comfort, and performance. It features a"
                                + " powerful single-cylinder engine, electronic fuel injection,"
                                + " and independent front and rear suspension for a smooth ride."
                                + " With its ergonomic design and practical features such as racks"
                                + " and storage compartments, the Alterra 700 is a capable"
                                + " companion for work and recreation.",
                                true, true, true, true);

    final Quad quad8 = new Quad("CFMoto CForce 600", "V102",
                                "The CFMoto CForce 600 is a value-packed utility ATV known for its"
                                + " affordability, durability, and capability. It features a"
                                + " liquid-cooled, single-cylinder engine, selectable four-wheel"
                                + " drive, and durable steel frame construction. With its"
                                + " user-friendly controls and practical features such as cargo"
                                + " racks and towing hitch, the CForce 600 is a versatile option"
                                + " for a wide range of outdoor activities.",
                                true, true, true, true);



    // Boat Category
    final Boat boat1 = new Boat("Boston Whaler Montauk 170", "B101",
                                "The Boston Whaler Montauk 170 is a versatile center console boat"
                                + " known for its durability, stability, and all-around"
                                + " performance. It features a classic Whaler hull design, a"
                                + " spacious layout with ample seating and storage, and a deep-V"
                                + " hull for a smooth and dry ride. With its rugged construction"
                                + " and functional design, the Montauk 170 is popular for fishing,"
                                + " watersports, and cruising.",
                                Boat.BoatType.MOTORBOAT);

    final Boat boat2 = new Boat("Sea Ray Sundancer 320", "B101",
                                "The Sea Ray Sundancer 320 is a luxury express cruiser known for"
                                + " its elegant design, spacious cabin, and smooth ride. It"
                                + " features a sleek profile, a comfortable cockpit with versatile"
                                + " seating arrangements, and a well-appointed cabin with sleeping"
                                + " accommodations, a galley, and a head. With its blend of"
                                + " performance and comfort, the Sundancer 320 is ideal for weekend"
                                + " getaways and entertaining.",
                                Boat.BoatType.SPORT);

    final Boat boat3 = new Boat("Grady-White Fisherman", "B101",
                                "The Grady-White Fisherman 236 is a premium center console boat"
                                + " known for its quality construction, fishing features, and"
                                + " offshore capability. It features a wide beam, a large bow"
                                + " casting platform, and a deep-V hull for stability and comfort"
                                + " in rough seas. With its spacious layout, abundant storage, and"
                                + " reliable Yamaha outboard power, the Fisherman 236 is a top"
                                + " choice for serious anglers.",
                                Boat.BoatType.MOTORBOAT);

    final Boat boat4 = new Boat("Regal 23 OBX", "B101",
                                "The Regal 23 OBX is a stylish and versatile bowrider known for its"
                                + " performance, comfort, and luxury amenities. It features a"
                                + " spacious cockpit with wraparound seating, a convertible sun"
                                + " lounge, and a swim platform with a folding swim step. With its"
                                + " sleek lines, powerful outboard propulsion, and upscale finishes"
                                + ", the 23 OBX is well-suited for watersports, cruising, and"
                                + " entertaining.",
                                Boat.BoatType.SPORT);

    final Boat boat5 = new Boat("Chaparral 21 SSi", "B101",
                                "The Chaparral 21 SSi is a sporty and agile bowrider known for its"
                                + " performance, versatility, and value. It features a spacious"
                                + " cockpit with comfortable seating, a large swim platform, and a"
                                + " wide bow area with storage compartments. With its responsive"
                                + " handling, fuel-efficient power options, and stylish design,"
                                + " the 21 SSi is ideal for family fun on the water.",
                                Boat.BoatType.SURF);

    final Boat boat6 = new Boat("Tracker Pro Team 175 TXW", "B101",
                                "The Tracker Pro Team 175 TXW is a reliable and affordable aluminum"
                                + " bass boat known for its fishing features, stability, and ease"
                                + " of use. It features a wide beam, a large casting deck, and a"
                                + " low-maintenance aluminum hull. With its reliable outboard"
                                + " power, trolling motor, and fishfinder, the Pro Team 175 TXW is"
                                + " popular among freshwater anglers looking for value and"
                                + " performance.",
                                Boat.BoatType.MOTORBOAT);

    final Boat boat7 = new Boat("MasterCraft NXT22", "B101",
                                "The MasterCraft NXT22 is a premium wake and surf boat known for"
                                + " its performance, versatility, and luxury features. It features"
                                + " a deep-V hull, a customizable wake system, and a spacious"
                                + " cockpit with comfortable seating. With its powerful engine"
                                + " options, advanced surf technology, and upscale amenities, the"
                                + " NXT22 delivers an exceptional on-water experience for"
                                + " wakeboarding, wakesurfing, and cruising.",
                                Boat.BoatType.SPORT);

    final Boat boat8 = new Boat("Bayliner Element E18", "B101",
                                "The Bayliner Element E18 is a versatile and affordable deck boat"
                                + " known for its spacious layout, stability, and ease of"
                                + " operation. It features a M-hull design for improved stability"
                                + " and handling, a comfortable cockpit with wraparound seating,"
                                + " and a large swim platform with a boarding ladder. With its"
                                + " simple and practical design, the Element E18 is ideal for"
                                + " family outings, watersports, and cruising.",
                                Boat.BoatType.SURF);



    // Jet-ski Category
    final JetSki jetski1 = new JetSki("Sea-Doo Spark", "123",
                                      "The Sea-Doo Spark is a lightweight and playful personal"
                                      + " watercraft known for its affordability, agility, and ease"
                                      + " of use. It features a compact hull design, a choice of"
                                      + " Rotax engines for varying performance levels, and an"
                                      + " optional intelligent brake and reverse system for"
                                      + " enhanced maneuverability. With its customizable options"
                                      + " and accessible price point, the Spark is popular among"
                                      + " riders of all skill levels.",
                                      JetSki.JetskiType.TOWING);

    final JetSki jetski2 = new JetSki("Yamaha VX Cruiser HO", "132",
                                      "The Yamaha VX Cruiser HO is a versatile and comfortable"
                                      + " personal watercraft known for its reliability, comfort,"
                                      + " and powerful performance. It features Yamaha's renowned"
                                      + " High Output engine, a spacious and ergonomic seat for up"
                                      + " to three passengers, and Yamaha's signature RiDE"
                                      + " technology for intuitive handling and control. With its"
                                      + " premium features and smooth ride, the VX Cruiser HO is"
                                      + " ideal for cruising and recreational water sports.",
                                      JetSki.JetskiType.CRUISER);

    final JetSki jetski3 = new JetSki("Kawaski Jet Ski Ultra LX", "321",
                                      "The Kawasaki Jet Ski Ultra LX is a high-performance personal"
                                      + " watercraft known for its power, stability, and luxury"
                                      + " features. It features a powerful four-stroke engine, a"
                                      + " deep-V hull for smooth handling in rough water, and a"
                                      + " comfortable saddle seat for three passengers. With its"
                                      + " spacious storage compartments, cruise control, and"
                                      + " electronic trim control, the Ultra LX offers a premium"
                                      + " riding experience for long-distance cruising and"
                                      + " touring.",
                                      JetSki.JetskiType.SPORT);

    final JetSki jetski4 = new JetSki("Sea-Doo RXP-X 300", "123",
                                      "The Sea-Doo RXP-X 300 is a high-performance personal"
                                      + " watercraft known for its adrenaline-pumping acceleration,"
                                      + " agile handling, and advanced technology features. It"
                                      + " features a supercharged Rotax engine delivering 300"
                                      + " horsepower, a lightweight and responsive hull design,"
                                      + " and Sea-Doo's Ergolock seating system for superior"
                                      + " control and comfort. With its race-inspired design and"
                                      + " cutting-edge performance, the RXP-X 300 is designed for"
                                      + " thrill-seeking riders and competitive racing.",
                                      JetSki.JetskiType.SPORT);

    final JetSki jetski5 = new JetSki("Yamaha gp1800R SVHO", "132",
                                      "The Yamaha GP1800R SVHO is a race-inspired personal"
                                      + " watercraft known for its speed, agility, and"
                                      + " championship-winning performance. It features Yamaha's"
                                      + " Super Vortex High Output engine, a lightweight NanoXcel 2"
                                      + " hull, and race-inspired handling components for precision"
                                      + " control in rough water conditions. With its aggressive"
                                      + " styling and championship pedigree, the GP1800R SVHO is"
                                      + " designed for serious enthusiasts and competitive racers.",
                                      JetSki.JetskiType.SPORT);

    final JetSki jetski6 = new JetSki("Kawasaki Jet Ski STX 160", "321",
                                      "The Kawasaki Jet Ski STX 160 is a versatile personal"
                                      + " watercraft known for its balance of performance,"
                                      + " affordability, and comfort. It features a responsive"
                                      + " four-stroke engine, a lightweight hull design for"
                                      + " nimble handling, and a spacious saddle seat for up to"
                                      + " three passengers. With its smart steering and braking"
                                      + " systems, the STX 160 offers a fun and enjoyable riding"
                                      + " experience for families and recreational riders.",
                                      JetSki.JetskiType.TOWING);

    final JetSki jetski7 = new JetSki("Sea-Doo GTX", "123",
                                      "The Sea-Doo GTX Limited 300 is a luxury touring personal"
                                      + " watercraft known for its comfort, convenience, and"
                                      + " premium features. It features a powerful Rotax engine"
                                      + " delivering 300 horsepower, a luxurious touring seat with"
                                      + " multiple riding positions, and Sea-Doo's exclusive LinQ"
                                      + " attachment system for easy accessory mounting. With its"
                                      + " advanced technology features such as a touchscreen"
                                      + " display, Bluetooth audio system, and intelligent"
                                      + " suspension, the GTX Limited 300 offers the ultimate in"
                                      + " luxury and performance for long-distance cruising and"
                                      + " touring.",
                                      JetSki.JetskiType.LUXURY);

    final JetSki jetski8 = new JetSki("Yamaha EX Deluxe", "132",
                                      "The Yamaha EX Deluxe is an entry-level personal watercraft"
                                      + " known for its affordability, reliability, and"
                                      + " user-friendly design. It features a compact and"
                                      + " lightweight hull, a fuel-efficient Yamaha engine, and"
                                      + " Yamaha's signature RiDE technology for intuitive handling"
                                      + " and control. With its comfortable seat for up to three"
                                      + " passengers, practical storage compartments, and"
                                      + " affordable price point, the EX Deluxe is ideal for"
                                      + " first-time buyers and casual riders.",
                                      JetSki.JetskiType.CRUISER);


    Stream.of(car1, car2, car3, car4, car5, car6, car7, car8, ecocar1, 
              ecocar2, ecocar3, ecocar4, ecocar5, ecocar6, ecocar7, ecocar8,
              truck1, truck2, truck3, truck4, truck5, truck6, truck7, truck8,
              motorcycle1, motorcycle2, motorcycle3, motorcycle4, motorcycle5,
              motorcycle6, motorcycle7, motorcycle8,
              quad1, quad2, quad3, quad4, quad5, quad6, quad7, quad8,
              boat1, boat2, boat3, boat4, boat5, boat6, boat7, boat8,
              jetski1, jetski2, jetski3, jetski4, jetski5, jetski6, jetski7, jetski8)
              .forEach(vehicle -> myVehInfoMap.put(vehicle.getMyName(), vehicle));

    return myVehInfoMap;

  }

  /** Retrieves the populated map.
   *
   * <p>This returns the static map {@code myVehInfoMap}, which contains information
   * about vehicles. The map associates vehicle names (keys) with AbstractVehicle objects
   * (values), allowing easy access to vehicle information.
   *
   * @return myVehInfoMap The map containing information about vehicles.
   *  
   *  */
  public static Map<String, AbstractVehicle> getMap() {
    return myVehInfoMap;
  }
  
  /**
   * Creates an array of JButton objects based on the provided arrays of file names
   * for still and moving images.
   *
   * @param theStillFileName An array of file names for still images.
   * @param theMovingFileNames An array of file names for moving images.
   * @return An array of JButton objects, each representing a button
   *         with a still image and a moving image as its icon and rollover icon.
   */
  private JButton[] createButtonsFromPath(String[] theStillFileName, String[] theMovingFileNames) {

    JButton[] keyButtons = new JButton[NUM_OF_BUTTONS];

    for (int i = 0; i < NUM_OF_BUTTONS; i++) {
      String stillFilePath = "src/stillImages/" + theStillFileName[i];
      JButton categoryButton = new JButton(new ImageIcon(stillFilePath));
      keyButtons[i] = categoryButton;
      String movingFilePath = "src/movingImages/" + theMovingFileNames[i];
      keyButtons[i].setPreferredSize(BUTTON_SIZE);
      keyButtons[i].setRolloverIcon(new ImageIcon(movingFilePath));
    }
    return keyButtons;
  }
 
  /**
   * Creates a JPanel containing the provided array of JButtons.
   *
   * @param theKeyButtons An array of JButton objects to be added to the panel.
   * @return A JPanel containing the provided buttons arranged in a horizontal layout.
   */
  private JPanel createButtonPanel(JButton[] theKeyButtons) {
    JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
    for (int i = 0; i < theKeyButtons.length; i++) {
      buttonPanel.add(theKeyButtons[i]);
    }
    return buttonPanel;
  }
  
  /**
   * Creates a JTabbedPane containing a tab for rental and user management actions.
   * This JTabbedPane only consist of one tab. Adding more features is possible by adding another
   * tab.
   *
   * @return A JTabbedPane with tabs for renting, dropping rentals, and accessing the login page.
   */
  private JTabbedPane createTabbedPane() {

    final JTabbedPane tabbedPane = new JTabbedPane(); 
    final JPanel tab1 = new JPanel(new GridLayout(1, 3));

    JButton rentButton = new JButton("Rentz");
    rentButton.setPreferredSize(LOWER_BUTTON_SIZE);
    JButton dropButton = new JButton("Dropz");
    dropButton.setPreferredSize(LOWER_BUTTON_SIZE);
    JButton loginButton = new JButton("Login Page");

    rentButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        final RentalManager rentalManager = new RentalManager(myReg);

        try {
          rentalManager.printRent(chosenVehName, chosenVehId);
          String updatedCurrList = updatelistScrollPane();
          //Container myContainerPane = frame.getContentPane();
          myInfoCurrTextArea.setText(updatedCurrList);
          } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(null, "Kindly consult with one of our associates to "
                                                + "inquire about the availability of this "
                                                + "rental.");
        }

      }
    });

    dropButton.addActionListener(new ActionListener() {
      String myCurrentUser = UserLoginGui.getMyCurrentUser();
      @Override
      public void actionPerformed(ActionEvent e) {
        final RentalManager rentalManager = new RentalManager(myReg);
        rentalManager.printDrop(myCurrentUser, chosenVehName);
        String updatedCurrList = updatelistScrollPane();
        myInfoCurrTextArea.setText(updatedCurrList);
        }
        });

    loginButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        myLoginGui = new UserLoginGui(myFrame);
        myLoginPanel = myLoginGui.loginPanelStart(myFrame);
        
        myFrame.getContentPane().removeAll();
        myFrame.getContentPane().setBackground(new Color(50, 204, 202)); 
        myFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
                               
        myFrame.add(myLoginPanel, gbc);
        myFrame.revalidate();
        myFrame.repaint();
        myFrame.setVisible(true);
      }

    });

    String theUserName = UserLoginGui.getMyCurrentUser();

    JScrollPane rentList  = rentlistScrollPane(theUserName);

    tab1.add(rentList);
    tab1.add(rentButton);
    tab1.add(dropButton);
    tab1.add(loginButton);

    tabbedPane.addTab("RENTZ DROPZ", tab1);

    return tabbedPane;

  }

  /**
   * Adds components to the appropriate content panel.
   *
   * @param theFrame JFrame that is considered main.
   * @param theButPane JPanel that consisted of the buttons of different vehicles.
   * @param theSpane JScrollPane that contains a list of vehicles for renting and observing.
   * @param theTabPane JTabbedPane that has functional buttons
   *        and a display of the user's current rental.
   */
  private void addCompsToContPane(JFrame theFrame, JPanel theButPane,
                                  JScrollPane theSpane, JTabbedPane theTabPane) {
    Container myContPane = myFrame.getContentPane();

    myContPane.setLayout(new BorderLayout());

    myContPane.add(theButPane, BorderLayout.NORTH);
    myContPane.add(theSpane, BorderLayout.CENTER);
    myContPane.add(theTabPane, BorderLayout.SOUTH);
    JScrollPane myVehSpane = new JScrollPane(myVehList);

    myVehSpane.setPreferredSize(new Dimension(250, 300));
    myContPane.add(myVehSpane, BorderLayout.WEST);

  }
  
  /**
   * Retrieves the current user's rentals for display.
   *
   * @return newCurrList
   */
  public String updatelistScrollPane() {
    String myCurrentUser = UserLoginGui.getMyCurrentUser();
    String currList = RentList.updateUserCurrRentals(myCurrentUser);
    String newCurrList = currList.replace(",", "\n");
    
    return newCurrList;
  }

  /**
  * Retrieves the available vehicles for display on a JScrollPane.
  *
  * @param theUserName The name of the current user.
  * @return infoScrollPane
  */
  public JScrollPane rentlistScrollPane(String theUserName) {
    final Container myContentPane = myFrame.getContentPane();

    myInfoCurrTextArea = new JTextArea();
    myInfoCurrTextArea.setEditable(false);
    myInfoCurrTextArea.setLineWrap(true);
    myInfoCurrTextArea.setWrapStyleWord(true);
    myInfoCurrTextArea.setBackground(Color.WHITE);
    
    String currList = RentList.updateUserCurrRentals(theUserName);
    String newCurrList = currList.replace(",", "\n");
    myInfoCurrTextArea.setText(newCurrList);
    myContentPane.add(myInfoCurrTextArea);

    JScrollPane infoScrollPane = new JScrollPane(myInfoCurrTextArea);
    return infoScrollPane;
  }

  /** 
   * Adds ActionListeners to the array of buttons to be able to retrieve
   * a list of vehicles of the same class.
   *
   * @param theKeyButtons The array of buttons with added
   *        actionListeners that retrieves a list when 
   *        choosing a rental.
   */
  private void addActionListenerToButtons(JButton[] theKeyButtons) {
    for (int i = 0; i < theKeyButtons.length; i++) {
      int buttonIndex = i;
      theKeyButtons[i].addActionListener(e -> {
        String[] listItems = getListItemsForButton(buttonIndex);
        myVehList.setListData(listItems);
      });
    }
  }

  /**
   * A switch-case method that retrieves an array of the same 
   * type of vehicle based on the given button index.
   *
   * @param buttonIndex An index for each of the vehicles.
   * @return  String array of any type of vehicle.
   *          
   */
  private String[] getListItemsForButton(int buttonIndex) {
    switch (buttonIndex) {
      case 0:
        return filterAndGetVehicleNames(Car.class);
      case 1:
        return filterAndGetVehicleNames(EcoCar.class);
      case 2:
        return filterAndGetVehicleNames(Truck.class);
      case 3:
        return filterAndGetVehicleNames(Motorcycle.class);
      case 4:
        return filterAndGetVehicleNames(Quad.class);
      case 5:
        return filterAndGetVehicleNames(Boat.class);
      case 6:
        return filterAndGetVehicleNames(JetSki.class);
      default:
        return new String[0];
    }
  }
  
  /**
   * Filters the vehicle information map by the specified vehicle type and retrieves the names
   * of vehicles belonging to that type.
   *
   * @param vehicleType The class representing the type of vehicles to filter by.
   * @return An array of strings containing the names of vehicles of the specified type.
   */
  private String[] filterAndGetVehicleNames(Class<? extends AbstractVehicle> vehicleType) {
    return myVehInfoMap.values().stream().filter(vehicleType::isInstance)
      .map(AbstractVehicle::getMyName).toArray(String[]::new);
  }

  /**
   * Handles the selection change of the item list.
   *
   * @param e The ListSelectionEvent object that represents the change event.
   */
  private void itemListSelectionChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      String selectedName = myVehList.getSelectedValue();
      AbstractVehicle vehicle = myVehInfoMap.get(selectedName);
      if (vehicle != null) {
        chosenVehName = vehicle.getMyName();
        chosenVehStatus = vehicle.isRentable();

        myInfoTextArea.setText(vehicle.getMyDiscription());
        updateTextAreaSize();
      }
    }
  }
  
  /**
   * Updates the size of the information text area.
   */
  private void updateTextAreaSize() {
    int preferredWidth = myInfoTextArea.getPreferredSize().width;
    int preferredHeight = myInfoTextArea.getPreferredSize().height;
    Dimension preferredSize = new Dimension(preferredWidth, preferredHeight);
    myInfoTextArea.setPreferredSize(preferredSize);
    revalidate();
  }

  String[] theCurrentlyRented = RentList.getAllRentals();
  
  /**
   * Sets the chosen rental's availability status to false upon completion of bill.
   *
   * @param theItInMap The itemInfoMap that contains every vehicle.
   * @param theCurrRented String Array that is currently rented.
   */
  public void setTheRented(Map<String, AbstractVehicle> theItInMap, String[] theCurrRented) {

    for (String currRented : theCurrRented) {
      AbstractVehicle value = theItInMap.get(currRented);
      if (value.isRentable() == true) {
        value.setMyRentalStatus(false);
      }
    }
  }

}
