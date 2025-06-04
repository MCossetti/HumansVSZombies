# HumansVSZombies

This project was proposed in the **Concurrent Programming** course, aiming to apply the knowledge acquired in class. HumansVSZombies is a simulation project of a game where humans and zombies compete against each other. The objective is to study concepts of concurrency, communication between threads/processes, and game logic.

## Project Requirements
This project consists of a concurrent simulation where humans and zombies compete on a 50x50 board. The original requirements and rules are as follows:

- The entities are initially placed on the board, with humans positioned on the far left column and zombies on the far right column.

- Whenever a zombie is immediately to the right of a human, that human is automatically transformed into a zombie.

- All entities move randomly, either horizontally or vertically, one position at a time.

- Two entities cannot occupy the same position on the board simultaneously, ensuring exclusive space occupation.

- The game ends under the following conditions:

    - When all humans have been transformed into zombies.

    - Or when a human manages to reach the far right column of the board.

## Features
- Simulation of humans and zombies in a virtual environment
- Movement and interaction between entities
- Rules for converting humans into zombies
- Time and rounds control

## Technologies Used

- Language: Java
- Libraries: 
    | Library / Package      | Main Usage                              |
    | ---------------------- | ------------------------------------- |
    | `java.util.concurrent` | Synchronization, semaphores, atomicity|
    | `java.util`            | General utilities (Random)             |
    | `javax.swing`          | Graphical interface (window, panel)   |
    | `java.awt`             | Graphics drawing, colors, and sizing   |
    | `java.util.Objects`    | Utilities for equals and hashCode      |
- Platform: Windows

## How to Run

1. Clone the repository:
    ```bash
    git clone https://github.com/MCossetti/HumansVSZombies.git
    ```
2. Enter the project folder:
    ```bash
    cd HumansVSZombies
    ```
3. Follow the specific execution instructions below:
    ```bash
    # Example of execution
    java ./src/main/Game.java
    ```
