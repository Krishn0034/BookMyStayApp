import java.util.*;

/**
 * BookMyStayApp - UC8: Booking History & Reporting
 * @author [Your Name]
 * @version 8.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // UC1: Welcome Message
        System.out.println("========================================");
        System.out.println("   Welcome to Book My Stay App!         ");
        System.out.println("   Application Version: 8.0             ");
        System.out.println("========================================");

        // UC8: Initialize History and Reporting
        BookingHistory history = new BookingHistory();
        ReportService reportService = new ReportService(history);

        // Simulating confirmed bookings being added to history
        System.out.println("\n--- Recording Confirmed Bookings to History ---");
        history.recordBooking("RES101: Alice - Single Room");
        history.recordBooking("RES102: Bob - Double Room");
        history.recordBooking("RES103: Charlie - Suite Room");

        // Admin requests a report
        reportService.generateSummaryReport();
    }
}

/**
 * UC8: BookingHistory Class
 * Uses a List to maintain a chronological audit trail of all confirmed bookings.
 */
class BookingHistory {
    // List preserves the order of confirmation
    private List<String> historyLog;

    public BookingHistory() {
        this.historyLog = new ArrayList<>();
    }

    public void recordBooking(String bookingDetails) {
        historyLog.add(bookingDetails);
        System.out.println("History: Archived [" + bookingDetails + "]");
    }

    public List<String> getHistoryLog() {
        // Returning a copy to ensure Read-Only access (Defensive Programming)
        return new ArrayList<>(historyLog);
    }
}

/**
 * UC8: ReportService Class
 * Decouples reporting logic from data storage.
 */
class ReportService {
    private BookingHistory history;

    public ReportService(BookingHistory history) {
        this.history = history;
    }

    public void generateSummaryReport() {
        List<String> logs = history.getHistoryLog();

        System.out.println("\n********** ADMIN OPERATIONAL REPORT **********");
        System.out.println("Total Bookings Processed: " + logs.size());
        System.out.println("----------------------------------------------");

        if (logs.isEmpty()) {
            System.out.println("No history found.");
        } else {
            for (int i = 0; i < logs.size(); i++) {
                System.out.println((i + 1) + ". " + logs.get(i));
            }
        }
        System.out.println("**********************************************");
    }
}