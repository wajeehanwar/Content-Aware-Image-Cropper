import java.awt.Color;

public class SeamCarver {
    private Picture picture;     // current picture
    // Create a seam carver object based on the given picture, making a 
    // defensive copy of picture.
    public SeamCarver(Picture picture) {
        // Initilalize and declare defensive copy of picture.
        this.picture = new Picture(picture.width(), picture.height());
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                this.picture.set(x, y, picture.get(x, y));
            }
        }
    }

    // Return current picture.
    public Picture picture() {
        // Return current picture.
        return picture;
    }

    // Return width of current picture.
    public int width() {
        // Return width of current picture.
        return picture.width();
    }

    // Return height of current picture.
    public int height() {
        // Return height of current picture.
        return picture.height();
    }

    // Return energy of pixel at column x and row y.
    public double energy(int x, int y) {
        // Check validity of x and y. (Public method)
        if (x < 0 || x > width() || y < 0 || y > height()) {
            throw new IllegalArgumentException();
        }
        // Compute energy of x baed on position.
        double energyX = (x == 0) 
            ? energy(picture.get(x+1, y), picture.get(width()-1, y)) 
            : (x == width()-1) 
            ? energy(picture.get(x-1, y), picture.get(0, y)) 
            : energy(picture.get(x+1, y), picture.get(x-1, y));
        // Compute energy of y based on position.
        double energyY = (y == 0) 
            ? energy(picture.get(x, y+1), picture.get(x, height()-1)) 
            : (y == height()-1) 
            ? energy(picture.get(x, y-1), picture.get(x, 0)) 
            : energy(picture.get(x, y+1), picture.get(x, y-1));
        // Return energy of pixel at column x and row y.
        return energyX + energyY;
    }
    //***Private helper for public energy.***
    // Return energy of pixel.
    private double energy(Color x1, Color x2) {
        // Return energy of pixel.
        return energy(x1, x2, 'r') + energy(x1, x2, 'g') + energy(x1, x2, 'b');
    }
    //***Private helper for public energy.***
    // Return absolute value color squared.
    private double energy(Color x1, Color x2, char color) {
        // Compute per RGB.
        double x = (color == 'r') 
            ? x1.getRed() - x2.getRed() 
            : (color == 'g') 
            ? x1.getGreen() - x2.getGreen() 
            : x1.getBlue() - x2.getBlue();
        // Return absolute value color squared.
        return Math.abs(x) * Math.abs(x);
    }
    // Return sequence of indices for horizontal seam.
    public int[] findHorizontalSeam() {
        // Initialize return array.
        int[] horizontalSeam = new int[picture.width()];
        // Transpose picture.
        transpose();
        // Delegate to findVerticalSeam.
        horizontalSeam = findVerticalSeam();
        // Transpose picture back to original.
        transpose();
        // Return sequence of indices for horizontal seam.
        return horizontalSeam;
    }
    //***Private helper for findHorizontalSeam and remove Horizontal seam.***
    // Transpose picture.
    private void transpose() {
        // Initialize new picture with transposed height and width.
        Picture transposed = new Picture(height(), width());
        // Set transposed pixel colors to new picture.
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                transposed.set(y, x, picture.get(x, y));
            }
        }
        // Replace picture with transposed picture.
        picture = transposed;
        return;
    }
    // Return sequence of indices for vertical seam.
    public int[] findVerticalSeam() {
        // Initialize vertice tracking variables.
        double[] energy = new double[height() * width()];
        double[] distTo = new double[height() * width()];
        int[] edgeTo = new int[height() * width()];
        int w, v;
        // Compute and initialize for all vertices.
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                // Declare current vertice.
                v = vertice(x, y);
                // Set all vertice energy.
                energy[v] = energy(x, y);
                // Set all vertice edges to -1.
                edgeTo[v] = -1;
                if (y == 0) {
                    // Initialize all top border distTo vertices.
                    distTo[v] = energy(x, y);
                } else {
                    // Set all other  distTo vertices to infinity.
                    distTo[v] = Double.POSITIVE_INFINITY;
                }
            }
        }
        // Compute for all vertices.
        for (int y = 0; y < height() - 1; y++) {
            for (int x = 0; x < width(); x++) {
                // Initialize current vertice tracker.
                v = vertice(x, y);
                // Account for left border.
                if (x >= 1) {
                    // Initialize bottom-left neighbor tracker.
                    w = vertice((x-1), (y+1));
                    // Perform relaxation of edges.
                    relax(v, w, energy, distTo, edgeTo);
                } 
                // Initialize bottom-mid neighbor tracker.
                w = vertice(x, (y+1));
                // Perform relaxation of edges.
                relax(v, w, energy, distTo, edgeTo);
                // Account for right border.
                if (x < width() - 1) {
                    // Initialize bottom-right neighbor tracker.
                    w = vertice((x+1), (y+1));
                    // Perform relaxation of edges.
                    relax(v, w, energy, distTo, edgeTo);
                }
            }
        }
        // Initialize and declare vertice trackers.
        w = 0;
        double bestDistance = Double.POSITIVE_INFINITY;
        // Compute for all vertices on bottom x-axis.
        for (int x = 0; x < width(); x++) {
            // Find shortest path.
            if (distTo[vertice(x, height() - 1)] < bestDistance) {
                // Initialize current vertice tracker.
                v = vertice(x, height() - 1);
                // Update trackers.
                bestDistance = distTo[v];
                w = v;
            }
        }
        // Initialize and declare return variable.
        int[] verticalSeam = new int[height()];
        // Compute shorstest path for y-axis.
        for (int y = 0; y < height(); y++) {
            // Load y index with next y-edge.
            verticalSeam[w / width()] = w % width();
            // Update tracker.
            w = edgeTo[w];
        }
        // Return sequence of indices for vertical seam.
        return verticalSeam;
    }
    //***Private helper for findVerticalSeam***
    // Perform relaxation on edges.
    private void relax(int v, int w, double[] e, double[] dTo, int[]eTo) {
        // Check if neighbour plus energy is shorter.
        if (dTo[w] > dTo[v] + e[w]) {
            // Update distTo and edgeTo trackers.
            dTo[w] = dTo[v] + e[w];
            eTo[w] = v;
        }
        return;
    }
    //***Private helper for findVerticalSeam***
    // Return vertice index.
    private int vertice(int x, int y) {
        // Return vertice index.
        return y * width() + x;
    }
    // Remove horizontal seam from current picture.
    public void removeHorizontalSeam(int[] seam) {
        // Check validity of seam. (Public method)
        if (height() <= 1 || seam.length != width()) {
            throw new IllegalArgumentException("Invalid seam length");
        }
        // Transpose picture.
        transpose();
        // Delegate to removeVerticalSeam.
        removeVerticalSeam(seam);
        // Transpose picture back to original picture.
        transpose();
        return;
    }

    // Remove vertical seam from current picture.
    public void removeVerticalSeam(int[] seam) {
        // Check validity of seam. (Public method)
        if (width() <= 1 || seam.length != height()) {
            throw new IllegalArgumentException("Invalid seam length");
        }
        // Initialize new picture with reduced width.
        Picture resized = new Picture(width() - 1, height());
        // Initialize and declare seam tracker.
        int seamW = seam[0];
        // Compute for all vertices.
        for (int y = 0; y < height(); y++) {
            // Check validity of seam. (Public method)
            if (seam[y] < 0 || seam[y] >= width() 
                || seam[y] < seamW-1 || seam[y] > seamW+1) {
                throw new IllegalArgumentException("Invalid seam");
            }
            // Update seam tracker.
            seamW = seam[y];
            for (int x = 0; x < width() - 1; x++) {
                // Set vertice colors based on side of seam.
                if (x < seamW) {
                    resized.set(x, y, picture.get(x, y));
                } else {
                    resized.set(x, y, picture.get(x+1, y));
                }
            }
        }
        // Update current picture to resized picture.
        picture = resized;
        return;
    }
}