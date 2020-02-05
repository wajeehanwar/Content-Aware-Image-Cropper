import java.awt.Color;

public class SeamCarver {
    private Picture picture;     // current picture

    // Create a seam carver object based on the given picture, making a 
    // defensive copy of picture.
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture.width(), picture.height());
        
        for (int i = 0; i < picture.width(); i++)
            for (int j = 0; j < picture.height(); j++)
                this.picture.set(i, j, picture.get(i, j));
        
    }
    
    // Return current picture.
    public Picture picture() {
        return this.picture;
    }

    // Return width of current picture.
    public int width() {
        return this.picture.width();
    }

    // Return height of current picture.
    public int height() {
        return this.picture.height();
    }

    // Return energy of pixel at column x and row y.
    public double energy(int x, int y) {
        //calculate the dimensions
        int[] xDim = setD(x, true);
        int[] yDim = setD(y, false);
        
        //set the colors
        Color xp = picture.get(xDim[0], y);
        Color xs = picture.get(xDim[1], y);
        Color yp = picture.get(x, yDim[0]);
        Color ys = picture.get(x, yDim[1]);
        
        //return the diff in energy
        return energy(xp, xs) + energy(yp, ys);
    }
    
    private int[] setD(int d, boolean isX) {
        //d is the int dimension (x or y) given by the input
        
        //which side should be compared?
        int side = (isX) ? this.width() - 1 : this.height() - 1;
        
        //the x1 and x2 or y1 and y2 to be returned
        int[] dim = new int[2];
        
        dim[0] = (d == side) ? d - 1 : d + 1;
        dim[1] = (d == 0) ? side : (d == side) ? 0 : d - 1; 
        
        return dim;
    }
    
    private double absDS(double a, double b) {
        //The absolute value of the difference, squared
        return Math.abs(a - b) * Math.abs(a - b);
    }
    
    private double energy(Color a, Color b) {
        //the compiled RGB energy difference
        return absDS(a.getRed(), b.getRed()) 
             + absDS(a.getGreen(), b.getGreen()) 
             + absDS(a.getBlue(), b.getBlue());
    }
    
    // Return sequence of indices for horizontal seam.
    public int[] findHorizontalSeam() {
        return new int[0];
    }

    // Return sequence of indices for vertical seam.
    public int[] findVerticalSeam() {
        return new int[0];
    }

    // Remove horizontal seam from current picture.
    public void removeHorizontalSeam(int[] seam) {
        // TBD
    }

    // Remove vertical seam from current picture.
    public void removeVerticalSeam(int[] seam) {
        // TBD
    }
}
