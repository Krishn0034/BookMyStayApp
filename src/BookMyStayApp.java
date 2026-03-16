import java.util.*;

/**
 * BookMyStayApp - UC7: Add-On Service Selection
 * @author [Your Name]
 * @version 7.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // UC1: Welcome Message
        System.out.println("========================================");
        System.out.println("   Welcome to Book My Stay App!         ");
        System.out.println("   Application Version: 7.0             ");
        System.out.println("========================================");

        // UC7: Initialize Add-On Manager
        AddOnManager addonManager = new AddOnManager();

        // Assume we have a Reservation ID from UC6 (e.g., "S101")
        String reservationId = "S101";

        System.out.println("\n--- Selecting Add-On Services for Reservation: " + reservationId + " ---");

        // Guest selects services
        addonManager.addServiceToReservation(reservationId, new Service("Breakfast", 500.0));
        addonManager.addServiceToReservation(reservationId, new Service("Late Checkout", 1000.0));
        addonManager.addServiceToReservation(reservationId, new Service("WiFi Plus", 200.0));

        // Display services and total extra cost
        addonManager.displayServicesForReservation(reservationId);
    }
}

/**
 * UC7: Service Class
 * Represents an optional offering with a name and price.
 */
class Service {
    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " (Rs. " + price + ")";
    }
}

/**
 * UC7: AddOnManager Class
 * Uses Map<String, List<Service>> to link one reservation to many services.
 */
class AddOnManager {
    // Reservation ID -> List of selected services
    private Map<String, List<Service>> reservationAddOns;

    public AddOnManager() {
        this.reservationAddOns = new HashMap<>();
    }

    public void addServiceToReservation(String resId, Service service) {
        // If the reservation doesn't have a list yet, create one
        reservationAddOns.putIfAbsent(resId, new ArrayList<>());
        reservationAddOns.get(resId).add(service);
        System.out.println("Added: " + service.getName() + " to " + resId);
    }

    public void displayServicesForReservation(String resId) {
        List<Service> services = reservationAddOns.get(resId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-ons selected for " + resId);
            return;
        }

        System.out.println("\nSummary for " + resId + ":");
        double totalCost = 0;
        for (Service s : services) {
            System.out.println("- " + s);
            totalCost += s.getPrice();
        }
        System.out.println("Total Add-On Cost: Rs. " + totalCost);
    }
}