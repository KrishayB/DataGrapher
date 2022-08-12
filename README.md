# Graphing

### About:
- This program helps to visualize data by graphing a set of x and y values provided in a .txt file.
- It automatically scales the points to fit within a certain grid size.
- It is not recommended to use this to visualize an equation, but if you want to, then click this footnote to find more information about it.[^bignote]

- Comments are added if you would like to understand how the code works or make changes.

### Running the program:
1. Clone the repository, download the code, or copy/paste it into a .java file. Make sure you have at least one .txt file with correctly formatted text.
2. Run the Graphing.java file.

![Example](GraphingExample.png)

### Other notes:
- This program only graphs positive numbers.
- There is a range of numbers, and really large numbers might cause some bugs.

[^bignote]: Since you have to enter the x and y values in a .txt file to graph an equation, it will be hard
    to calculate those points yourself and enter them in manually. For this, the GetDataPoints.java file exists.
    <br>In the `printPoints()` method, the `LENGTH` variable should be set to the number of data points that you want. Right now, it is set to 100 data points.
    <br>The first for-loop will print the x-coordinates. If you want different x-coordinates, you can change it.
    <br>The second for-loop will print the y-coordinates, and this is where you can
    enter the equation. The equation that is there right now is $200\cos (x) + 200$.