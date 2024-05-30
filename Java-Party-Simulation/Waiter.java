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