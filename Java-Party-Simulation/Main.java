// Furkan BAYTAK 
// Furkan ÖZKAYA 

// Main class to demonstrate the interaction between guests and trays
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Create trays for borek, cake, and drink
        Tray borekTray = new Tray("borek", 5, 30);
        Tray cakeTray = new Tray("cake", 5, 15);
        Tray drinkTray = new Tray("drink", 5, 30);

        // Create a waiter with the trays
        Waiter waiter = new Waiter(borekTray, cakeTray, drinkTray);
        waiter.start();

        // Create guests and start their threads
        Guest[] guests = new Guest[8];
        for (int i = 0; i < guests.length; i++) {
            guests[i] = new Guest("Guest " + (i + 1), borekTray, cakeTray, drinkTray);
            guests[i].start();
        }

        // Wait for all guests to finish and then interrupt the waiter
        for (Guest guest : guests) {
            guest.join();
        }
        waiter.interrupt();
        waiter.join();

        // Print remaining items on each tray after all guests have finished
        System.out.println("All food and drink is consumed.");
        System.out.println("Remaining borek: " + borekTray.getRemainingItems());
        System.out.println("Remaining cake: " + cakeTray.getRemainingItems());
        System.out.println("Remaining drink: " + drinkTray.getRemainingItems());
    }
}