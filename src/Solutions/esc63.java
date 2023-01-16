package Solutions;

import geneticFunctions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class esc63 {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        long start = System.currentTimeMillis();
        int[][] adjMatrix = Parser.parse("problems/esc63.sop");

        int popSize = 1000;
        ArrayList<Chromosome> chromosomes = new ArrayList<>();
        while(chromosomes.size() < popSize){
            chromosomes.add(new Chromosome(adjMatrix));
        }

        int generations = (int)Math.ceil(chromosomes.get(0).getDim()/4.0);

        for (int j = 0; j < generations; j++) {
            ArrayList<Chromosome> newChromosomes = new ArrayList<>(Selections.tournament(chromosomes, popSize));

            int totalMutationThreads = (int)Math.ceil(popSize*0.1);
            Mutation[] mutationThreads = new Mutation[totalMutationThreads];
            for (int i = 0; i < totalMutationThreads; i++) {
                mutationThreads[i] = new Mutation(newChromosomes);
            }

            Thread[] threads = new Thread[totalMutationThreads];
            for (int i = 0; i < totalMutationThreads; i++) {
                int num = i;
                threads[num] = new Thread(() -> mutationThreads[num].scramble());
            }

            for (Thread thread : threads) thread.start();
            for (Thread thread : threads) thread.join();
            for (Mutation mutationThread: mutationThreads) newChromosomes.add(mutationThread.getMutation());

            int totalCrossoverThreads = (int)Math.ceil(popSize*0.95);
            Crossover[] crossoverThreads = new Crossover[totalCrossoverThreads];

            for (int i = 0; i < totalCrossoverThreads; i++) {
                crossoverThreads[i] = new Crossover(newChromosomes);
            }
            threads = new Thread[totalCrossoverThreads];
            for (int i = 0; i < totalCrossoverThreads; i++) {
                int num = i;
                threads[i] = new Thread(() -> crossoverThreads[num].orderBasedCrossover());
            }

            for (Thread thread : threads) thread.start();
            for (Thread thread : threads) thread.join();
            for (Crossover crossoverThread: crossoverThreads) newChromosomes.addAll(crossoverThread.getRecombinations());


            chromosomes = newChromosomes;

            Collections.sort(chromosomes);
            System.out.println(j+1 + "° Generation finished");
        }
        long end = System.currentTimeMillis();

        System.out.println("\n------------Final Chromosome------------");
        System.out.println(chromosomes.get(chromosomes.size()-1));
        System.out.println("Total time to complete algorithm: " + (double)(end-start)/1000.0 + " seconds");
    }
}
