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