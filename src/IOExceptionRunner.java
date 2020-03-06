/**
 * JUSTIN GIRGIS AND SERENITY BROWN
 * CECS 277
 * MARCH 5, 2020
 */

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class IOExceptionRunner {

    public static void main(String[] args) throws FileNotFoundException, IncorrectFileName {


        JFileChooser chooser = new JFileChooser();
        Scanner in = null;				//Input file
        boolean foundInFile = false;			//Flag that we don't have an input file.

        do {
            try {
                File file = new File("/Users/mac/Downloads/trianglepoints.txt");
                in = new Scanner(file);
                Scanner userinput = new Scanner(System.in);
                System.out.println("Your input File: " + userinput); // prompt user to put in a file
                foundInFile = true;

            } catch (FileNotFoundException exception){
                // if statement?
                throw new IncorrectFileName("File input does not exist, please try another!");
            } // end try/catch statement

        } while (!foundInFile);


        boolean foundOutFile = false;			//Flag that we don't have an output file
        PrintWriter out = null;				//Output file

        do {
            try{
                out = new PrintWriter("out.txt");
                foundOutFile = true;
            } catch (FileNotFoundException exception){
                throw new IncorrectFileName("Output path does not exist, please try another");
            } // end of second try/catch statement


        } while (!foundOutFile);

        String line;					//The current line of the ASCII text file
        Point vertices [] = new Point [3];		//The array of triangle vertices

        while (in.hasNextLine()) {
            line = in.nextLine();
            System.out.println("Line: " + line);
            Scanner thisLine = new Scanner (line);
            boolean success = false;
            for (int i = 0; i < 3; i++) {
                //Use getNextPoint to read the next point from the thisLine scanner.
                //Once you have all three points read in, set success to be true.
                vertices[i] = getNextPoint(thisLine);
                if(i ==2)
                    success = true;

            } // End of reading in the vertices
            if (success) {				//Output the area to the output file.
                out.write(String.format("Next area: %.2f \n" , area(vertices)));
            }

            thisLine.close();
        }
        in.close();
        out.close();
        System.out.println("Completed satisfactorily.");
    }


    /**
     * Read the next two double precision numbers from the scanner that we get,
     * and make them into a Point instance.  Check to see that we can get both
     * the x and the y coordinates from the scanner.  Throw a IllegalArgumentException
     * if you cannot get two double precision numbers out of the scanner.
     * @param	line	The scanner that has the current line of input.  The caller
     * 					reads in a line of text from the input file, then creates a
     * 					scanner on that line of text, all by itself.  Not the whole file.
     * 					Then passes that scanner to this routine to get two double
     * 					precision values.
     * @return			A new instance of Point.
     */

    public static Point getNextPoint(Scanner line) {
        //There may be no more double precision numbers left, just one or two.
        //Throw the IllegalArgumentException if you have < 2 double precision numbers
        //left in the scanner.
        Point point = null;

        if(line.hasNextInt()) {
            if(!line.hasNextInt()) {
                throw new NullPointerException("No x exists");
            }
            int x = line.nextInt();

            if(!line.hasNextInt()) {
                throw new NullPointerException("No x exists");
            }
            int y = line.nextInt();
            point = new Point(x,y);
        }

        return point;
    }

    /**
     * Find the area of the triangle, given the verticies that the user passes in.
     *
     * @param vertices An array of three Points that are the corners of the triangle.
     *                 The order is unimportant.  Note that this does not check to
     *                 make sure that there are exactly three.
     * @return The area of the triangle, using Heron's formula.
     */
    public static double area(Point[] vertices) {
        double side1, side2, side3;
        //Find the length of each of the sides of the triangle
        side1 = vertices[0].distance(vertices[1]);
        side2 = vertices[1].distance(vertices[2]);
        side3 = vertices[2].distance(vertices[0]);
        //s is an intermediate value needed by Heron's formula
        double s = (side1 + side2 + side3) / 2.0;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }
}