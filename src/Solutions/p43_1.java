package Solutions;

import geneticFunctions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class p43_1 {
    public static void main(String[] args) throws FileNotFoundException {
        int[][] adjMatrix = Parser.parse("problems/p43.1.sop");

        int popSize = 1000;
        ArrayList<Chromosome> chromosomes = new ArrayList<>();
        while(chromosomes.size() < popSize){
            chromosomes.add(new Chromosome(adjMatrix));
        }

        int generations = 10;

        for (int j = 0; j < generations; j++) {
            ArrayList<Chromosome> newChromosomes = new ArrayList<>(Selections.rank(chromosomes, popSize));

            newChromosomes.addAll(Mutations.displacement(newChromosomes, 0.2));
            newChromosomes.addAll(Crossover.uniformCrossover(newChromosomes, 0.80));

            chromosomes = newChromosomes;

            Collections.sort(chromosomes);
        }

        System.out.println("\n------------Final Chromosome------------");
        System.out.println(chromosomes.get(chromosomes.size()-1));
    }
}
