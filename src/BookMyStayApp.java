import java.util.*;

/**
 * BookMyStayApp - UC10: Booking Cancellation & Inventory Rollback
 * @author [Your Name]
 * @version 10.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // UC1: Welcome Message
        System.out.println("========================================");
        System.out.println("   Welcome to Book My Stay App!         ");
        System.out.println("   Application Version: 10.0            ");
        System.out.println("========================================");

        // Setup Inventory (UC3)
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Suite Room", 2);

        // Setup Cancellation Service
        CancellationService cancellationService = new CancellationService(inventory);

        // Simulating a booking that we might want to cancel
        String roomID = "SU-505";
        System.out.println("\n--- Initial State ---");
        System.out.println("Suite Availability: " + inventory.getAvailability("Suite Room"));

        // UC10: Performing a Cancellation (Rollback)
        System.out.println("\nAction: Guest cancels reservation for " + roomID);
        cancellationService.processCancellation("Suite Room", roomID);

        System.out.println("\n--- Final State ---");
        System.out.println("Suite Availability: " + inventory.getAvailability("Suite Room"));
        cancellationService.displayRollbackHistory();
    }
}

/**
 * UC10: CancellationService Class
 * Uses a Stack to track released room IDs for LIFO rollback behavior.
 */
class CancellationService {
    private RoomInventory inventory;
    // Stack tracks room IDs in the order they are cancelled
    private Stack<String> releasedRoomsStack;

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.releasedRoomsStack = new Stack<>();
    }

    public void processCancellation(String type, String roomID) {
        // 1. Validation: Ensure the roomID is not null/empty
        if (roomID == null || roomID.isEmpty()) {
            System.err.println("Error: Invalid Room ID for cancellation.");
            return;
        }

        // 2. Rollback Logic: Add to Stack (LIFO)
        releasedRoomsStack.push(roomID);

        // 3. Inventory Restoration: Increment count
        int currentCount = inventory.getAvailability(type);
        inventory.updateAvailability(type, currentCount + 1);

        System.out.println("Success: Room " + roomID + " rolled back to inventory.");
    }

    public void displayRollbackHistory() {
        System.out.println("\n--- Room Rollback Stack (Recent to Oldest) ---");
        if (releasedRoomsStack.isEmpty()) {
            System.out.println("No cancellations recorded.");
        } else {
            // Displaying stack content
            for (int i = releasedRoomsStack.size() - 1; i >= 0; i--) {
                System.out.println("Cancelled: " + releasedRoomsStack.get(i));
            }
        }
    }
}

// --- Previous classes (RoomInventory) remain the same ---
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();
    public void addRoomType(String type, int count) { inventory.put(type, count); }
    public int getAvailability(String type) { return inventory.getOrDefault(type, 0); }
    public void updateAvailability(String type, int newCount) { inventory.put(type, newCount); }
}