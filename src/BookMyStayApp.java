import java.util.*;

/**
 * BookMyStayApp - UC5: Booking Request (First-Come-First-Served)
 * @author [Your Name]
 * @version 5.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // UC1: Welcome Message
        System.out.println("========================================");
        System.out.println("   Welcome to Book My Stay App!         ");
        System.out.println("   Application Version: 5.0             ");
        System.out.println("========================================");

        // UC5: Initialize the Booking Request Queue
        BookingRequestQueue intakeQueue = new BookingRequestQueue();

        System.out.println("\n--- Receiving Incoming Booking Requests ---");

        // Simulating guests submitting requests
        intakeQueue.addRequest(new Reservation("Guest_Alice", "Suite Room"));
        intakeQueue.addRequest(new Reservation("Guest_Bob", "Single Room"));
        intakeQueue.addRequest(new Reservation("Guest_Charlie", "Double Room"));

        // Displaying the queued requests in arrival order
        intakeQueue.displayQueue();

        System.out.println("\nStatus: All requests are queued and waiting for processing.");
        System.out.println("Note: No inventory has been modified yet.");
    }
}

/**
 * UC5: Reservation Class
 * Represents a Guest's intent to book a specific room type.
 */
class Reservation {
    private String guestName;
    private String requestedRoomType;

    public Reservation(String guestName, String requestedRoomType) {
        this.guestName = guestName;
        this.requestedRoomType = requestedRoomType;
    }

    @Override
    public String toString() {
        return "Request [Guest: " + guestName + " | Room: " + requestedRoomType + "]";
    }
}

/**
 * UC5: BookingRequestQueue Class
 * Uses a LinkedList-based Queue to maintain FIFO order.
 */
class BookingRequestQueue {
    // Queue preserves the order of arrival (First-In, First-Out)
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        this.requestQueue = new LinkedList<>();
    }

    // Add request to the back of the line
    public void addRequest(Reservation request) {
        requestQueue.add(request);
        System.out.println("Intake: Received " + request);
    }

    // Display all waiting requests
    public void displayQueue() {
        System.out.println("\n--- Current Booking Queue (FIFO Order) ---");
        if (requestQueue.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            for (Reservation res : requestQueue) {
                System.out.println(res);
            }
        }
    }
}