# Shortest Path Finder
This program essentially acts as a way to find the shortest path among various points. At command line, the user can input a file of an adjacency matrix which serves as a list of locations and the distances between other location listed in the file. Through a genetic algorithm, the program finds an optimal by creating randomly generated paths and "crossing over" the most optimal ones to output a short path. **NOTE: Because I am using a genetic algorithm, the program will most likely not give the absolute shortest path. This is to ensure that the program can be computable in cases where the given adjacency matrix lists, say hundreds, of locations.**

# How to use
The main method is within the Driver class. compile Driver.java and, at runtime, input the textfile (or the location of the textfile) as one of the command-line arguments. For instance:

**javac Driver.java**
**java Driver textfile.txt**

## File
The file given by the user must be in an **adjacency matrix** format. Each line in the file should have the following format:

**Name of location, Distance from location 1, Distance from location 2, ... Distance from location x**

Here is an example:

**Location1, 0, 5, 7, 8**
**Location2, 3, 0, 2, 1**
**Location3, 4, 2, 0, 1**
**Location4, 8, 9, 2, 0**

## Classes
Aside from the Driver class, the program functions off of 2 classes:

