import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Parser {
    public static Graph parse(String path) throws FileNotFoundException {
        File file = new File(path);
        Graph graph = null;
        int dimension;

        Scanner sc = new Scanner(file);

        try {
            while (!sc.hasNext("DIMENSION:")){
                sc.next();
            }
            sc.next();

            if(sc.hasNextInt()){
                dimension = sc.nextInt();
                graph = new Graph(dimension);
            }else {
                sc.close();
                throw new IllegalArgumentException("DIMENSION field incomplete or lacking value entirely");
            }
        }catch (NoSuchElementException e){
            sc.close();
            throw new NoSuchElementException("Problem must have DIMENSION field");
        }

        while (!sc.hasNext("EDGE_WEIGHT_SECTION")){
            sc.next();
        }
        //Skip EDGE_WEIGHT_SECTION field
        sc.next();
        //Skip field value
        sc.next();

        try {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    int tmp = Integer.parseInt(sc.next());
                    if (tmp != 0 && tmp != -1){
                        graph.addEdge(i,j, tmp);
                    }
                }
            }
        } catch (NumberFormatException e){
            sc.close();
            throw new NumberFormatException("\"DIMENSION\" field doesn't match the matrix values");
        }
        sc.close();
        return graph;
    }
}
