# Java Party Simulation

This project simulates a party scenario with limited resources using multithreading in Java. The simulation involves multiple guests and a waiter, managing the consumption and refilling of resources such as börek, cake, and drinks.

## Table of Contents
- [Introduction](#introduction)
- [Project Structure](#project-structure)
- [Setup](#setup)
- [Usage](#usage)
- [Class Descriptions](#class-descriptions)
  - [Tray](#tray)
  - [Guest](#guest)
  - [Waiter](#waiter)
- [Synchronization](#synchronization)
- [Contributors](#contributors)
- [Project Report](#project-report)
- [License](#license)

## Introduction

This project is a part of the coursework for the "CSE3124 Operating Systems" class at Manisa Celal Bayar University. It demonstrates the coordination between multiple threads representing guests and a waiter, ensuring synchronized access to shared resources.

## Project Structure

The project includes the following main components:
- **Tray**: Represents a tray with a specific capacity and methods to manage items.
- **Guest**: Represents a guest at the party, consuming items from the trays.
- **Waiter**: Responsible for refilling the trays when items are nearly exhausted.

The project files are organized as follows:

'''
  /Java-Party-Simulation
  │
  ├── Tray.java
  ├── Guest.java
  ├── Waiter.java
  └── Main.java
'''

## Setup

To run this project, follow these steps:

1. **Clone the repository**:
    ```bash
    git clone https://github.com/FurkanBaytak/Java-Threads-OS-Project.git
    cd Java-Threads-OS-Project/Java-Party-Simulation
    ```

2. **Compile the project**:
    ```bash
    javac *.java
    ```

3. **Run the project**:
    ```bash
    java Main
    ```

## Usage

The simulation will output messages indicating when a guest takes an item and when the waiter refills a tray. The final output will show the remaining items on each tray, verifying that the simulation correctly managed the resources and synchronized access.

## Class Descriptions

### Tray

The `Tray` class represents a tray with a specific capacity and a maximum number of items that can be served.

#### Methods:

- **Tray(String name, int capacity, int maxTotalItems)**: Constructor to initialize the tray with a given name, capacity, and maximum total items.

    ```java
    public Tray(String name, int capacity, int maxTotalItems) {
        this.name = name;
        this.capacity = capacity;
        this.maxTotalItems = maxTotalItems;
    }
    ```

- **synchronized boolean takeItem(String guestName)**: Method to allow a guest to take an item from the tray. It checks if there are items available and updates the tray state accordingly.

    ```java
    public synchronized boolean takeItem(String guestName) {
        if (this.itemCount > 0 && this.totalServedItems < this.maxTotalItems) {
            this.itemCount--;
            this.totalServedItems++;
            System.out.println(guestName + " took 1 " + this.name + ". Items left on tray: " + this.itemCount);
            return true;
        }
        return false;
    }
    ```

- **synchronized void refillItem()**: Method to refill the tray with items. It ensures that the tray is refilled only up to its capacity or the remaining items that can be served.

    ```java
    public synchronized void refillItem() {
        if (this.itemCount < this.capacity && this.totalServedItems < this.maxTotalItems) {
            this.itemCount = Math.min(this.capacity, this.maxTotalItems - this.totalServedItems);
            System.out.println("Waiter refilled the " + this.name + ". Items now: " + this.itemCount);
        }
    }
    ```

- **synchronized int getRemainingItems()**: Method to get the remaining items that can be served from the tray.

    ```java
    public synchronized int getRemainingItems() {
        return maxTotalItems - totalServedItems;
    }
    ```

- **synchronized boolean hasItemsLeft()**: Method to check if there are items left to be served from the tray.

    ```java
    public synchronized boolean hasItemsLeft() {
        return totalServedItems < maxTotalItems;
    }
    ```

### Guest

The `Guest` class represents a guest at the party. Each guest has limits on the number of items they can consume from each tray.

#### Methods:

- **Guest(String name, Tray borekTray, Tray cakeTray, Tray drinkTray)**: Constructor to initialize the guest with a name and trays for börek, cake, and drink.

    ```java
    public Guest(String name, Tray borekTray, Tray cakeTray, Tray drinkTray) {
        super(name);
        this.borekTray = borekTray;
        this.cakeTray = cakeTray;
        this.drinkTray = drinkTray;
    }
    ```

- **void run()**: Method that simulates the guest’s actions. The guest tries to take items from each tray based on their limits and sleeps for a random duration between actions.

    ```java
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
    ```

### Waiter

The `Waiter` class represents the waiter responsible for refilling the trays. The waiter continuously checks the trays and refills them if the number of items falls below a certain threshold.

#### Methods:

- **Waiter(Tray... trays)**: Constructor to initialize the waiter with trays.

    ```java
    public Waiter(Tray... trays) {
        this.trays = trays;
    }
    ```

- **void run()**: Method that simulates the waiter’s actions. The waiter continuously refills trays while not interrupted and sleeps for 500 milliseconds between checks.

    ```java
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
    ```

## Synchronization

Java's synchronized blocks and methods are used to manage access to the shared tray resources, ensuring that only one thread can modify the state of a tray at a time. This prevents race conditions and ensures the integrity of the simulation.

## Contributors

- [Furkan BAYTAK](https://github.com/FurkanBaytak)
- [Furkan ÖZKAYA](https://github.com/Elhier0)

## Project Report

If you would like to access detailed information about the project and the report, you can use [this link.](https://github.com/FurkanBaytak/Java-Threads-OS-Project/blob/main/ProjectReport.pdf).

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
