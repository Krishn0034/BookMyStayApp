import java.util.HashMap;
import java.util.Map;

/**
 * BookMyStayApp - UC3: Centralized Room Inventory Management
 * @author [Your Name]
 * @version 3.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // UC1: Welcome Message
        System.out.println("========================================");
        System.out.println("   Welcome to Book My Stay App!         ");
        System.out.println("   Application Version: 3.0             ");
        System.out.println("========================================");

        // UC3: Initialize Centralized Inventory
        RoomInventory inventory = new RoomInventory();

        // Registering Room Types with initial counts
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        // Display Current Inventory
        System.out.println("\n--- Initial Room Inventory ---");
        inventory.displayInventory();

        // Demonstrate a controlled update (e.g., a booking happens)
        System.out.println("\nAction: Booking 1 Double Room...");
        inventory.updateAvailability("Double Room", 2); // Updating count to 2

        // Display Inventory after update
        System.out.println("\n--- Updated Room Inventory ---");
        inventory.displayInventory();
    }
}

/**
 * UC3: RoomInventory Class
 * Manages availability using a HashMap for O(1) lookup.
 */
class RoomInventory {
    // HashMap acts as the Single Source of Truth
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    // Method to register/add room types
    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    // Method to get current availability
    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    // Controlled update of room availability
    public void updateAvailability(String type, int newCount) {
        if (inventory.containsKey(type)) {
            inventory.put(type, newCount);
        }
    }

    // Display all inventory status
    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }
    }
}

// --- Keep your Room classes from UC2 below ---
abstract class Room {
    private String roomNumber;
    private String type;
    private double price;

    public Room(String roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room: [" + roomNumber + "] | Type: " + type + " | Price: Rs." + price);
    }
}
// (SingleRoom, DoubleRoom, SuiteRoom classes remain the same as UC2)