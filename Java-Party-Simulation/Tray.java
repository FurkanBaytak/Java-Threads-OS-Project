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