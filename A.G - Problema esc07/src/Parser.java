import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    public static int[][] parse(String path) throws FileNotFoundException {
        File file = new File(path);
        int[][] adjMatrix = null;
        int dimension = 0;

        Scanner sc = new Scanner(file);

        while (!sc.hasNext("DIMENSION:")) {
            sc.next();
        }
        sc.next();

        if (sc.hasNextInt()) {
            dimension = sc.nextInt();
            adjMatrix = new int[dimension][dimension];
        }

        while (!sc.hasNext("EDGE_WEIGHT_SECTION")){
            sc.next();
        }
        sc.next();
        sc.next();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                adjMatrix[i][j] = Integer.parseInt(sc.next());
            }
        }

        sc.close();
        return adjMatrix;
    }
}
