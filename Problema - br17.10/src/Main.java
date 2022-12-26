import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = Parser.parse("br17.10.sop");
        System.out.println(graph.toString() + "\n");


    }
}