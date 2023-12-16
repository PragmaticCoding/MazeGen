# MazeGen

This is a fork of the JavaFX maze generation project at https://github.com/gchapidze/maze-gen

The intent was to convert the original from Java to Kotlin, implement my [MVCI](https://www.pragmaticcoding.ca/javafx/mvci/) framework, and use a Reactive approach to the GUI.  

## Architecture 

Logically the maze generation algorithms treat the maze as a collection of `Cells`.  Each `Cell` has references to its immediate neighbours indicated as "north", "south", "east" and "west".  Visually, the maze is represented by a collection of `Walls`.  Each `Wall` has to states - "open" or "blocked" - represented by an `isBlocked` boolean property.  Each `Cell` has a reference to each of the `Walls` that surround it.  

The key function is `Cell.unblock(otherCell: Cell)`, that figures out which wall is between the two `Cells` and sets its status to "open".  

Graphically, only the `Walls` are shown on the GUI (well, before the animation was added).  `Walls` are represented as `Lines` on a `Pane` and their x/y coordinates are determined from the x/y values of the `Wall`.  The `visibleProperty()` of each `Line` is bound to the `isBlocked` Property of the corresponding `Wall`.  So when the `Wall` between two `Cells` is unblocked by the maze algorithm, it disappears from the GUI.

## Animation

When it was up and running, it worked well but it was a bit *underwhelming* as an application.  Yes, mazes were generated lightning fast, it wasn't a lot of fun.  However, I was curious as to how the recursive backtracking algorithm was working, so I decided to animate it.

To do this, the algorithm code - which had run fast enough to run on the FXAT - was executed from a `Task`, and the Model elements were update via `Platform.runLater()`.  Then calls to `Thread.sleep()` were added to slow the process down so that progress could be seen.  Finally, visualization of the `Cells` was added to the GUI in the form of `Rectangles`.  Nomal `Cells` had the same colour as the background, but the `Cell` currently being evaluated via the algorithm was coloured blue if the algorithm was working forwards, and pink if it was backtracking.  

The result, IMHO, is fascinating and way more fun than solving a maze could ever be.  

## Running It

This is a Gradle project.  Download the project code and the open the root directory of the project with a Gradle compatible/enabled IDE and it should build and run.
