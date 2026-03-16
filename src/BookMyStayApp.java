import java.util.*;

/**
 * BookMyStayApp - UC9: Error Handling & Validation
 * @author [Your Name]
 * @version 9.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // UC1: Welcome Message
        System.out.println("========================================");
        System.out.println("   Welcome to Book My Stay App!         ");
        System.out.println("   Application Version: 9.0             ");
        System.out.println("========================================");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 1); // Only 1 room available

        // UC9: Testing Validation and Error Handling
        System.out.println("\n--- Processing Bookings with Validation ---");

        // Scenario 1: Valid Booking
        processSafeBooking("Alice", "Single Room", inventory);

        // Scenario 2: Invalid Room Type (Should throw exception)
        processSafeBooking("Bob", "Penthouse", inventory);

        // Scenario 3: Out of Stock (Should throw exception)
        processSafeBooking("Charlie", "Single Room", inventory);
    }

    /**
     * Fail-Fast Validation Logic
     */
    public static void processSafeBooking(String guest, String type, RoomInventory inv) {
        try {
            System.out.println("\nAttempting to book: " + type + " for " + guest);

            // 1. Validate Input (Does the room type exist?)
            if (!type.equals("Single Room") && !type.equals("Double Room") && !type.equals("Suite Room")) {
                throw new InvalidBookingException("Error: Room type '" + type + "' does not exist in our system.");
            }

            // 2. Validate State (Is it available?)
            int count = inv.getAvailability(type);
            if (count <= 0) {
                throw new InvalidBookingException("Error: '" + type + "' is currently sold out.");
            }

            // If passes validation, proceed
            inv.updateAvailability(type, count - 1);
            System.out.println("Success: Booking confirmed for " + guest);

        } catch (InvalidBookingException e) {
            // Graceful Failure Handling
            System.err.println(e.getMessage());
        }
    }
}

/**
 * UC9: Custom Exception Class
 * Making error causes explicit and readable.
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// --- Previous RoomInventory classes remain below ---
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();
    public void addRoomType(String type, int count) { inventory.put(type, count); }
    public int getAvailability(String type) { return inventory.getOrDefault(type, 0); }
    public void updateAvailability(String type, int newCount) { inventory.put(type, newCount); }
}