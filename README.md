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

### Guest

The `Guest` class represents a guest at the party. Each guest has limits on the number of items they can consume from each tray.

### Waiter

The `Waiter` class represents the waiter responsible for refilling the trays. The waiter continuously checks the trays and refills them if the number of items falls below a certain threshold.

## Synchronization

Java's synchronized blocks and methods are used to manage access to the shared tray resources, ensuring that only one thread can modify the state of a tray at a time. This prevents race conditions and ensures the integrity of the simulation.

## Contributors

- [Furkan BAYTAK](https://github.com/FurkanBaytak)
- [Furkan ÖZKAYA](https://github.com/Elhier0)

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
