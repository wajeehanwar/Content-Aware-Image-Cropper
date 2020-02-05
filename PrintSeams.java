/*************************************************************************
 *  Compilation:  javac PrintSeams.java
 *  Execution:    java PrintSeams input.png
 *  Dependencies: SeamCarver.java Picture.java StdOut.java
 *
 *  Read image from file specified as command-line argument. Print energy
 *  of each image, a vertical seam, and a horizontal seam.
 *
 *  % java PrintSeams 6x5.png
 *  6-by-5 image
 *
 *  Horizontal seam:
 *   57685   50893   91370   25418   33055   37246  
 *   15421   56334   22808*  54796   11641*  25496  
 *   12344*  19236*  52030   17708*  44735   20663* 
 *   17074   23678   30279   80663   37831   45595  
 *   32337   30796    4909   73334   40613   36556  
 *
 *  Total energy = 104400
 *
 *
 *  Vertical seam:
 *   57685   50893   91370   25418*  33055   37246  
 *   15421   56334   22808   54796   11641*  25496  
 *   12344   19236   52030   17708*  44735   20663  
 *   17074   23678   30279*  80663   37831   45595  
 *   32337   30796    4909*  73334   40613   36556  
 *
 *  Total energy = 89955
 *
 *************************************************************************/

public class PrintSeams {
    private final static boolean HORIZONTAL   = true;
    private final static boolean VERTICAL     = false;

    private static void printSeam(SeamCarver carver, int[] seam, boolean direction) {
        double totalSeamEnergy = 0.0;

        for (int row = 0; row < carver.height(); row++) {
            for (int col = 0; col < carver.width(); col++) {
                String marker = " ";
                if ((direction == HORIZONTAL && row == seam[col])
                 || (direction == VERTICAL   && col == seam[row])) {
                    marker = "*";
                    totalSeamEnergy += carver.energy(col, row);
                }
                StdOut.printf("%6.0f%s ", carver.energy(col, row), marker);
            }
            StdOut.println();
        }                
        StdOut.println();
        StdOut.printf("Total energy = %.0f\n", totalSeamEnergy);
        StdOut.println();
        StdOut.println();
    }

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("%d-by-%d image\n", picture.width(), picture.height());
        StdOut.println();

        SeamCarver carver = new SeamCarver(picture);
        
        StdOut.printf("Horizontal seam:\n");
        int[] horizontalSeam = carver.findHorizontalSeam();
        printSeam(carver, horizontalSeam, HORIZONTAL);

        StdOut.printf("Vertical seam:\n");
        int[] verticalSeam = carver.findVerticalSeam();
        printSeam(carver, verticalSeam, VERTICAL);
    }

}
