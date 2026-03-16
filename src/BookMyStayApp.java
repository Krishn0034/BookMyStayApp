import java.io.*;
import java.util.*;

/**
 * BookMyStayApp - UC12: Data Persistence & System Recovery
 * @author [Your Name]
 * @version 12.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Welcome to Book My Stay App!         ");
        System.out.println("   UC12: Persistence & Recovery         ");
        System.out.println("========================================");

        String filename = "system_state.ser";
        RoomInventory inventory = new RoomInventory();

        // 1. Try to recover state from file
        PersistenceService persistence = new PersistenceService();
        RoomInventory recoveredInventory = persistence.loadState(filename);

        if (recoveredInventory != null) {
            inventory = recoveredInventory;
            System.out.println("RECOVERY SUCCESSFUL: Loaded existing inventory.");
        } else {
            System.out.println("INITIALIZING NEW STATE: No persistence file found.");
            inventory.addRoomType("Deluxe Room", 10);
        }

        // 2. Perform a change
        inventory.updateAvailability("Deluxe Room", 8);
        System.out.println("Current Deluxe Rooms: " + inventory.getAvailability("Deluxe Room"));

        // 3. Save state before shutdown
        persistence.saveState(inventory, filename);
        System.out.println("SYSTEM SHUTDOWN: State saved to " + filename);
    }
}

/**
 * UC12: PersistenceService
 * Handles Serialization (Save) and Deserialization (Load).
 */
class PersistenceService {

    public void saveState(RoomInventory inventory, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(inventory);
        } catch (IOException e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }

    public RoomInventory loadState(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (RoomInventory) ois.readObject();
        } catch (FileNotFoundException e) {
            return null; // Normal if it's the first time running
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading state: " + e.getMessage());
            return null;
        }
    }
}

/**
 * RoomInventory must implement Serializable to be saved to a file.
 */
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L; // Ensures version compatibility
    private Map<String, Integer> inventory = new HashMap<>();

    public synchronized void addRoomType(String type, int count) { inventory.put(type, count); }
    public synchronized int getAvailability(String type) { return inventory.getOrDefault(type, 0); }
    public synchronized void updateAvailability(String type, int newCount) { inventory.put(type, newCount); }
}