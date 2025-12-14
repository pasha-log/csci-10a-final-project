# GridWorld Pong

## Purpose of Project

This project implements a classic Pong game using the GridWorld framework in Java. It is designed as a final project for the CSCI-10A Java I Programming Fundamentals class to demonstrate object-oriented programming concepts, event handling, and graphical user interface basics.

## Demo

![Gameplay](docs/demo.gif)

## Version / Date

Version 1.0  
December 2025

## How to Start This Project in BlueJ

1. Ensure you have Java installed (Java 8 or later recommended).
2. Download and install the GridWorld framework.
   - You can download the GridWorld framework from [this link](https://tomrebold.com/csis10a/ch05/GridWorldCode.zip).
3. Clone or download this repository.
4. Compile all `.java` files in the `pong` directory.
5. Run the main class (`PongWorld.java`).

## How to Start This Project Within VSCode

1. Clone or download this repository.
2. Ensure the GridWorld jar is referenced by VS Code:

   - Create a `.vscode/settings.json` in the project root (if it doesn’t exist) and add:
     ```
     {
       "java.project.referencedLibraries": [
         "lib/**/*.jar"
       ]
     }
     ```
   - Then reload Java projects:
     - Open Command Palette and run: `Java: Reload Projects`
     - If issues persist: `Java: Clean Java Language Server Workspace`

3. Compile all sources (via WSL terminal):
   ```
   javac -cp lib/gridworld.jar -d out $(find . -name "*.java")
   ```
4. Run the main class (`PongWorld.java`) with the project root on the classpath so images load:
   ```
   java -cp .:out:lib/gridworld.jar PongWorld
   ```
   - If you prefer not to include `.`, you can set the image path explicitly:
     ```
     java -Dinfo.gridworld.gui.ImagePath=images -cp out:lib/gridworld.jar PongWorld
     ```

## Authors

- Pasha Loguinov

## User Instructions

- Press the `Run` button to start the game. Use the speed slider to increase difficulty.
- Use the arrow keys to control your paddle that is on the left.
- If playing with a second player, use the `W` and `S` keys.
- Try to bounce the ball past your opponent to score points.
- The game ends when a player reaches the winning score.
- Can restart the game or exit with the options presented in the dialog modal.
