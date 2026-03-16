import java.util.HashMap;
import java.util.Map;

/**
 * BookMyStayApp - UC4: Room Search & Availability Check
 * @author [Your Name]
 * @version 4.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // UC1: Welcome Message
        System.out.println("========================================");
        System.out.println("   Welcome to Book My Stay App!         ");
        System.out.println("   Application Version: 4.0             ");
        System.out.println("========================================");

        // Setup Inventory (from UC3)
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 0); // Sold out for testing
        inventory.addRoomType("Suite Room", 2);

        // Setup Room Details (from UC2)
        Map<String, Room> roomTemplates = new HashMap<>();
        roomTemplates.put("Single Room", new SingleRoom("SR-Basic", 1500.0));
        roomTemplates.put("Double Room", new DoubleRoom("DR-Deluxe", 2500.0));
        roomTemplates.put("Suite Room", new SuiteRoom("SU-Luxury", 5000.0));

        // UC4: Room Search Service (Read-Only)
        SearchService searchService = new SearchService(inventory, roomTemplates);

        System.out.println("\nGuest is searching for available rooms...");
        searchService.displayAvailableRooms();
    }
}

/**
 * UC4: SearchService Class
 * Reinforces safe data access by only reading inventory, not modifying it.
 */
class SearchService {
    private RoomInventory inventory;
    private Map<String, Room> roomTemplates;

    public SearchService(RoomInventory inventory, Map<String, Room> roomTemplates) {
        this.inventory = inventory;
        this.roomTemplates = roomTemplates;
    }

    public void displayAvailableRooms() {
        System.out.println("--- Search Results: Available Options ---");
        boolean found = false;

        for (String type : roomTemplates.keySet()) {
            int count = inventory.getAvailability(type);

            // Defensive Programming: Filter out unavailable rooms
            if (count > 0) {
                Room details = roomTemplates.get(type);
                details.displayDetails();
                System.out.println("Status: " + count + " rooms available");
                System.out.println("------------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }
    }
}

// --- Previous UC Classes (RoomInventory, Room, etc.) remain below ---
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();
    public void addRoomType(String type, int count) { inventory.put(type, count); }
    public int getAvailability(String type) { return inventory.getOrDefault(type, 0); }
}

abstract class Room {
    private String roomNumber, type;
    private double price;
    public Room(String rn, String t, double p) { this.roomNumber = rn; this.type = t; this.price = p; }
    public void displayDetails() {
        System.out.println("Type: " + type + " | Price: Rs." + price);
    }
}

class SingleRoom extends Room { public SingleRoom(String rn, double p) { super(rn, "Single Room", p); } }
class DoubleRoom extends Room { public DoubleRoom(String rn, double p) { super(rn, "Double Room", p); } }
class SuiteRoom extends Room { public SuiteRoom(String rn, double p) { super(rn, "Suite Room", p); } }