import java.util.*;

/**
 * BookMyStayApp - UC6: Reservation Confirmation & Room Allocation
 * @author [Your Name]
 * @version 6.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // UC1: Welcome Message
        System.out.println("========================================");
        System.out.println("   Welcome to Book My Stay App!         ");
        System.out.println("   Application Version: 6.0             ");
        System.out.println("========================================");

        // Setup Inventory (UC3)
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Suite Room", 1);

        // Setup Request Queue (UC5)
        BookingRequestQueue intakeQueue = new BookingRequestQueue();
        intakeQueue.addRequest(new Reservation("Alice", "Single Room"));
        intakeQueue.addRequest(new Reservation("Bob", "Single Room"));
        intakeQueue.addRequest(new Reservation("Charlie", "Single Room")); // This should fail (only 2 left)
        intakeQueue.addRequest(new Reservation("David", "Suite Room"));

        // UC6: Initialize Booking Service
        BookingService bookingService = new BookingService(inventory);

        System.out.println("\n--- Starting Room Allocation Process ---");

        // Process the queue until empty
        while (!intakeQueue.isEmpty()) {
            Reservation currentRequest = intakeQueue.getNextRequest();
            bookingService.processAllocation(currentRequest);
        }

        // Display Final State
        bookingService.displayAllocations();
    }
}

/**
 * UC6: BookingService Class
 * Handles allocation logic and prevents double booking using Sets.
 */
class BookingService {
    private RoomInventory inventory;
    // Maps Room Type -> Set of Unique Room IDs assigned
    private Map<String, Set<String>> allocatedRooms;
    private int roomCounter = 100; // To generate unique IDs

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
    }

    public void processAllocation(Reservation res) {
        String type = res.getRequestedRoomType();
        int available = inventory.getAvailability(type);

        if (available > 0) {
            // Generate unique Room ID (e.g., Single-101)
            String roomID = type.substring(0, 1) + (++roomCounter);

            // Ensure the set exists for this room type
            allocatedRooms.putIfAbsent(type, new HashSet<>());

            // Add to Set (Prevents Double Booking)
            if (allocatedRooms.get(type).add(roomID)) {
                inventory.updateAvailability(type, available - 1);
                System.out.println("CONFIRMED: " + res.getGuestName() + " assigned to " + roomID);
            }
        } else {
            System.out.println("REJECTED: No availability for " + res.getGuestName() + " (" + type + ")");
        }
    }

    public void displayAllocations() {
        System.out.println("\n--- Final Allocation Report ---");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " Assignments: " + entry.getValue());
        }
    }
}

/** * Note: Ensure your Reservation class from UC5 has these getter methods:
 * public String getGuestName() { return guestName; }
 * public String getRequestedRoomType() { return requestedRoomType; }
 * * And BookingRequestQueue needs:
 * public Reservation getNextRequest() { return requestQueue.poll(); }
 * public boolean isEmpty() { return requestQueue.isEmpty(); }
 */