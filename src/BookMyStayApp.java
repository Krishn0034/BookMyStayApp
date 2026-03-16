import java.util.*;

/**
 * BookMyStayApp - UC11: Concurrent Booking Simulation (Thread Safety)
 * @author [Your Name]
 * @version 11.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Welcome to Book My Stay App!         ");
        System.out.println("   UC11: Multi-threaded Simulation      ");
        System.out.println("========================================");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Deluxe Room", 1); // Only ONE room left!

        // Creating two threads (Users) trying to book the SAME room at the SAME time
        Runnable bookingTask1 = () -> processConcurrentBooking("User_Alice", "Deluxe Room", inventory);
        Runnable bookingTask2 = () -> processConcurrentBooking("User_Bob", "Deluxe Room", inventory);

        Thread thread1 = new Thread(bookingTask1);
        Thread thread2 = new Thread(bookingTask2);

        System.out.println("\nSimulating simultaneous booking attempts...");
        thread1.start();
        thread2.start();
    }

    /**
     * Using 'synchronized' to ensure only one thread can execute this logic at once.
     * This prevents the "Double Booking" race condition.
     */
    public static synchronized void processConcurrentBooking(String guest, String type, RoomInventory inv) {
        int available = inv.getAvailability(type);

        System.out.println(guest + " is checking availability... found: " + available);

        if (available > 0) {
            // Simulating a small processing delay
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            inv.updateAvailability(type, available - 1);
            System.out.println("SUCCESS: Room allocated to " + guest);
        } else {
            System.out.println("FAILED: No rooms left for " + guest);
        }
    }
}

// --- RoomInventory must also be thread-safe ---
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public synchronized void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public synchronized int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public synchronized void updateAvailability(String type, int newCount) {
        inventory.put(type, newCount);
    }
}