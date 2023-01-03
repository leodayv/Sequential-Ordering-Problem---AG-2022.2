package Solutions.esc07;

import geneticFunctions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
public class GeneticAlgorithm {
    public static void main(String[] args) throws FileNotFoundException {
        Random rd = new Random();
        int[][] adjMatrix = Parser.parse("problems/esc07.sop");

        int runs = 15;
        Chromosome best = null;
        for(int i = 0; i < runs; i++){
            ArrayList<Chromosome> chromosomes = new ArrayList<>();

            int popSize = 1000;
            while (chromosomes.size() < popSize){
                chromosomes.add(new Chromosome(adjMatrix));
            }

            int generations = 100;

            for (int j = 0; j < generations; j++) {
                ArrayList<Chromosome> newChromosomes = Selections.tournament(chromosomes, 0.8);

                newChromosomes.addAll(Mutations.simpleInversion(chromosomes, 0.1));

                newChromosomes.addAll(Crossover.uniformCrossover(chromosomes, 0.1));

                chromosomes = newChromosomes;
            }

            for (Chromosome chromosome: chromosomes) {
                if(!chromosome.isValid()){
                    continue;
                }
                if(best != null){
                    if (chromosome.getFitness() > best.getFitness()){
                        best = chromosome;
                    }
                }else {
                    if (chromosome.isValid()){
                        best = chromosome;
                    }
                }
            }
        }

        assert best != null;
        System.out.println("\n------------Final Chromosome------------");
        System.out.println(best);
    }
}