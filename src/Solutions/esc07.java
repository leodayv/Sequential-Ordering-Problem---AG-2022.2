package Solutions;

import geneticFunctions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class esc07 {
    public static void main(String[] args) throws FileNotFoundException {
        int[][] adjMatrix = Parser.parse("problems/esc07.sop");

        ArrayList<Chromosome> chromosomes = new ArrayList<>();

        int popSize = 5000;
        while (chromosomes.size() < popSize){
            chromosomes.add(new Chromosome(adjMatrix));
        }

        int generations = 5;

        for (int j = 0; j < generations; j++) {
            ArrayList<Chromosome> newChromosomes = Selections.tournament(chromosomes, popSize);

            newChromosomes.addAll(Mutations.simpleInversion(newChromosomes, 0.1));

            newChromosomes.addAll(Crossover.uniformCrossover(newChromosomes, 0.95));

            chromosomes = newChromosomes;

            Collections.sort(chromosomes);
        }

        System.out.println("\n------------Final Chromosome------------");
        System.out.println(chromosomes.get(chromosomes.size()-1));
    }
}