package Solutions.p43_1;

import geneticFunctions.Chromosome;
import geneticFunctions.Mutations;
import geneticFunctions.Parser;
import geneticFunctions.Selections;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {
    public static void main(String[] args) throws FileNotFoundException {
        Random rd = new Random();
        int[][] adjMatrix = Parser.parse("problems/p43.1.sop");

        int runs = 15;
        Chromosome best = null;
        for (int i = 0; i < runs; i++) {
            int popSize = 1000;
            ArrayList<Chromosome> chromosomes = new ArrayList<>();
            
            while(chromosomes.size() < popSize){
                chromosomes.add(new Chromosome(adjMatrix));
            }
            
            int generations = 100;

            for (int j = 0; j < generations; j++) {
                ArrayList<Chromosome> newChromosome = new ArrayList<>(Selections.rank(chromosomes, 0.8));

                newChromosome.addAll(Mutations.displacement(chromosomes, 0.1));
            }
        }
    }
}