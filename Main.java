// Furkan BAYTAK 
// Furkan ÖZKAYA 

// Tray class representing a tray with a specific capacity and maximum total items
class Tray {
    // Variables to track item count, capacity, maximum total items, total served items, and name of the tray
    // itemCount: current number of items on the tray
    // capacity: maximum capacity of the tray
    // maxTotalItems: maximum total items that can be served from the tray
    // totalServedItems: total number of items served from the tray
    // name: name of the tray
    private int itemCount = 0;
    private final int capacity;
    private final int maxTotalItems;
    private int totalServedItems = 0;
    private final String name;

    // Constructor to initialize the tray with a given name, capacity, and maximum total items
    public Tray(String name, int capacity, int maxTotalItems) {
        this.name = name;
        this.capacity = capacity;
        this.maxTotalItems = maxTotalItems;
    }

    // Method to allow a guest to take an item from the tray
    public synchronized boolean takeItem(String guestName) {
        if (this.itemCount > 0 && this.totalServedItems < this.maxTotalItems) {
            this.itemCount--;
            this.totalServedItems++;
            System.out.println(guestName + " took 1 " + this.name + ". Items left on tray: " + this.itemCount);
            return true;
        }
        return false;
    }

    // Method to refill the tray with items
    public synchronized void refillItem() {
        if (this.itemCount < this.capacity && this.totalServedItems < this.maxTotalItems) {
            this.itemCount = Math.min(this.capacity, this.maxTotalItems - this.totalServedItems);
            System.out.println("Waiter refilled the " + this.name + ". Items now: " + this.itemCount);
        }
    }

    // Method to get the remaining items that can be served from the tray
    public synchronized int getRemainingItems() {
        return maxTotalItems - totalServedItems;
    }

    // Method to check if there are items left to be served from the tray
    public synchronized boolean hasItemsLeft() {
        return totalServedItems < maxTotalItems;
    }
}

// Guest class representing a guest that can take items from different trays
class Guest extends Thread {
    // Tray objects for borek, cake, and drink
    private Tray borekTray, cakeTray, drinkTray;
    // Limits for the number of items each guest can take
    private int borekLimit = 4, cakeLimit = 2, drinkLimit = 4;
    // Counters for the number of items taken from each tray
    private int borekTaken = 0, cakeTaken = 0, drinkTaken = 0;

    // Constructor to initialize the guest with a name and trays for borek, cake, and drink
    public Guest(String name, Tray borekTray, Tray cakeTray, Tray drinkTray) {
        super(name);
        this.borekTray = borekTray;
        this.cakeTray = cakeTray;
        this.drinkTray = drinkTray;
    }

    @Override
    public void run() {
        try {
            // Guest continues to take items while there are items left on any tray and the limits have not been reached
            while ((borekTray.hasItemsLeft() || cakeTray.hasItemsLeft() || drinkTray.hasItemsLeft()) &&
                    (borekTaken < borekLimit || cakeTaken < cakeLimit || drinkTaken < drinkLimit)) {
                // Attempt to take items from each tray based on limits
                // Sleep for a random duration to simulate time between taking items
                if (borekTray.hasItemsLeft() && borekTaken < borekLimit && borekTray.takeItem(getName())) borekTaken++;
                if (cakeTray.hasItemsLeft() && cakeTaken < cakeLimit && cakeTray.takeItem(getName())) cakeTaken++;
                if (drinkTray.hasItemsLeft() && drinkTaken < drinkLimit && drinkTray.takeItem(getName())) drinkTaken++;
                Thread.sleep((long) (Math.random() * 1500));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Waiter class representing a waiter that refills trays
class Waiter extends Thread {
    private Tray[] trays;
    // Constructor to initialize the waiter with trays
    public Waiter(Tray... trays) {
        this.trays = trays;
    }

    @Override
    public void run() {
        try {
            // Waiter continuously refills trays while not interrupted
            // Refill each tray and sleep for 500 milliseconds between checks
            while (!isInterrupted()) {
                for (Tray tray : trays) {
                    tray.refillItem();
                }
                Thread.sleep(500); // Check every half second
            }
        } catch (InterruptedException e) {
            System.out.println("Waiter finished his job.");
        }
    }
}

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