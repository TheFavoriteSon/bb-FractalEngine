import java.util.Scanner;

public class FractalEngine {
    private static int WIDTH = 90; // Width of the output
    private static int HEIGHT = 40; // Height of the output
    private static int MAX_ITERATIONS = 100; // Maximum number of iterations
    private static double ZOOM = 1.5; // Zoom level
    private static double X_OFFSET = -0.5; // X-axis offset
    private static double Y_OFFSET = 0; // Y-axis offset

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        renderFractal();
        while (true) {
            System.out.print("Enter control (z/x/w/s/d/a): ");
            String input = scanner.nextLine();
            
            if (input.equals("z")) {
                ZOOM -= 0.1;
            } else if (input.equals("x")) {
                ZOOM += 0.1;
            } else if (input.equals("w")) {
                Y_OFFSET -= 0.1;
            } else if (input.equals("s")) {
                Y_OFFSET += 0.1;
            } else if (input.equals("d")) {
                X_OFFSET += 0.1;
            } else if (input.equals("a")) {
                X_OFFSET -= 0.1;
            } else {
                break;
            }

            renderFractal();
        }
    }

    private static void renderFractal() {
        System.out.print("\u001b[H\u001b[0J");
        System.out.flush();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                double zx = 0;
                double zy = 0;
                double cx = (x - WIDTH / 2.0) * ZOOM / WIDTH + X_OFFSET;
                double cy = (y - HEIGHT / 2.0) * ZOOM / HEIGHT + Y_OFFSET;

                int iteration = 0;
                while (zx * zx + zy * zy < 4 && iteration < MAX_ITERATIONS) {
                    double temp = zx * zx - zy * zy + cx;
                    zy = 2.0 * zx * zy + cy;
                    zx = temp;
                    iteration++;
                }

                if (iteration == MAX_ITERATIONS) {
                    // Inside the fractal set
                    System.out.print("\u001B[40m \u001B[0m");
                } else {
                    // Outside the fractal set
                    int color = (iteration % 24) + 232;
                    System.out.print("\u001B[48;5;" + color + "m " + "\u001B[0m");
                }
            }
            System.out.println();
        }
    }
}
