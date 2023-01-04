package Solutions;

import geneticFunctions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class br17_10 {
    public static void main(String[] args) throws FileNotFoundException {
        int[][] adjMatrix = Parser.parse("problems/br17.10.sop");

        int popSize = 5000;
        ArrayList<Chromosome> chromosomes = new ArrayList<>();
        while(chromosomes.size() < popSize){
            chromosomes.add(new Chromosome(adjMatrix));
        }

        int generations = 5;

        for (int j = 0; j < generations; j++) {
            ArrayList<Chromosome> newChromosomes = new ArrayList<>(Selections.tournament(chromosomes, popSize));

            newChromosomes.addAll(Mutations.displacement(newChromosomes, 0.1));

            newChromosomes.addAll(Crossover.orderBasedCrossover(newChromosomes, 0.95));

            chromosomes = newChromosomes;

            Collections.sort(chromosomes);
        }

        System.out.println("\n------------Final Chromosome------------");
        System.out.println(chromosomes.get(chromosomes.size()-1));
    }
}
