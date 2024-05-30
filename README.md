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

## Setup

To run this project, follow these steps:

1. **Clone the repository**:
    ```bash
    git clone https://github.com/FurkanBaytak/Java-Party-Simulation.git
    cd Java-Party-Simulation
    ```

2. **Compile the project**:
    ```bash
    javac Main.java
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
        this.currentItems = maxTotalItems;
    }
    ```

- **synchronized boolean takeItem(String guestName)**: Method to allow a guest to take an item from the tray. It checks if there are items available and updates the tray state accordingly.

    ```java
    public synchronized boolean takeItem(String guestName) {
        if (currentItems > 0) {
            currentItems--;
            System.out.println(guestName + " took an item from " + name + ". Items left: " + currentItems);
            return true;
        }
        return false;
    }
    ```

- **synchronized void refillItem()**: Method to refill the tray with items. It ensures that the tray is refilled only up to its capacity or the remaining items that can be served.

    ```java
    public synchronized void refillItem() {
        if (currentItems < capacity) {
            int refillAmount = Math.min(capacity - currentItems, maxTotalItems - currentItems);
            currentItems += refillAmount;
            System.out.println("Waiter refilled " + name + ". Items now: " + currentItems);
        }
    }
    ```

- **synchronized int getRemainingItems()**: Method to get the remaining items that can be served from the tray.

    ```java
    public synchronized int getRemainingItems() {
        return currentItems;
    }
    ```

- **synchronized boolean hasItemsLeft()**: Method to check if there are items left to be served from the tray.

    ```java
    public synchronized boolean hasItemsLeft() {
        return currentItems > 0;
    }
    ```

### Guest

The `Guest` class represents a guest at the party. Each guest has limits on the number of items they can consume from each tray.

#### Methods:

- **Guest(String name, Tray borekTray, Tray cakeTray, Tray drinkTray)**: Constructor to initialize the guest with a name and trays for börek, cake, and drink.

    ```java
    public Guest(String name, Tray borekTray, Tray cakeTray, Tray drinkTray) {
        this.name = name;
        this.borekTray = borekTray;
        this.cakeTray = cakeTray;
        this.drinkTray = drinkTray;
    }
    ```

- **void run()**: Method that simulates the guest’s actions. The guest tries to take items from each tray based on their limits and sleeps for a random duration between actions.

    ```java
    @Override
    public void run() {
        while (borekTray.hasItemsLeft() || cakeTray.hasItemsLeft() || drinkTray.hasItemsLeft()) {
            if (borekTray.takeItem(name)) sleepRandom();
            if (cakeTray.takeItem(name)) sleepRandom();
            if (drinkTray.takeItem(name)) sleepRandom();
        }
    }

    private void sleepRandom() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
        while (!Thread.currentThread().isInterrupted()) {
            for (Tray tray : trays) {
                if (tray.getRemainingItems() < tray.capacity / 2) {
                    tray.refillItem();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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
