import java.util.Scanner;

public class FractalEngine {
    private static int width = 90;
    private static int height = 35;
    private static int maxIterations = 100; // max num of iterations before declared out of set
    private static double zoom = 1.5; // zoom level
    private static double xOffset = -0.5; // X-axis offset
    private static double yOffset = 0; // Y-axis offset

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            renderFractal();
            while (true) {
                String input = scanner.nextLine();
                if (input.equals("z")) { zoom /= 2; }
                else if (input.equals("x")) { zoom *= 2; }
                else if (input.equals("w")) { yOffset -= zoom/2; }
                else if (input.equals("s")) { yOffset += zoom/2; }
                else if (input.equals("d")) { xOffset += zoom/2; }
                else if (input.equals("a")) { xOffset -= zoom/2; }
                else { break; }
                renderFractal();
            }
        }
    }

    private static void renderFractal() {
        System.out.print("\u001b[H\u001b[0J"); // cursor to 0,0 before clear wipe from cursor -> end
        System.out.flush();
        for (int y = 0; y < height; y++) { // all px on screen
            for (int x = 0; x < width; x++) {
                double zx = 0;
                double zy = 0;
                double cx = (x - width / 2.0) * zoom / width + xOffset;
                double cy = (y - height / 2.0) * zoom / height + yOffset;

                int iteration = 0;
                while (zx * zx + zy * zy < 4 && iteration < maxIterations) { // Z^2 under 4 & within iteration count
                    double temp = zx * zx - zy * zy + cx;
                    zy = 2.0 * zx * zy + cy;
                    zx = temp; // idk why but program breaks w/o reassignment
                    iteration++;
                }

                if (iteration == maxIterations) {
                    System.out.print("\u001B[40m \u001B[0m"); // black whitespace
                } else {
                    int color = (iteration % 24) + 232;
                    System.out.print("\u001B[48;5;" + color + "m " + "\u001B[0m"); // grayscale palette
                }
            }
            System.out.println();
        }
    }
}
