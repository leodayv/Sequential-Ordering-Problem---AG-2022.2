package Solutions;

import geneticFunctions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class p43_1 {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        int[][] adjMatrix = Parser.parse("problems/p43.1.sop");

        ArrayList<Chromosome> chromosomes = new ArrayList<>();

        int popSize = 1000;
        while (chromosomes.size() < popSize) {
            chromosomes.add(new Chromosome(adjMatrix));
        }

        int generations = 5;

        for (int j = 0; j < generations; j++) {
            ArrayList<Chromosome> newChromosomes = Selections.tournament(chromosomes, popSize);

            int totalMutationThreads = (int) Math.round(chromosomes.size() * 0.1);
            Mutation[] mutationThreads = new Mutation[totalMutationThreads];
            for (int i = 0; i < totalMutationThreads; i++) {
                mutationThreads[i] = new Mutation(newChromosomes);
            }

            Thread[] threads = new Thread[totalMutationThreads];
            for (int i = 0; i < totalMutationThreads; i++) {
                int num = i;
                if (num % 2 == 0) threads[num] = new Thread(() -> mutationThreads[num].scramble());
                else threads[num] = new Thread(() -> mutationThreads[num].displacement());
            }

            for (Thread thread : threads) thread.start();
            for (Thread thread : threads) thread.join();
            for (Mutation mutationThread : mutationThreads) newChromosomes.add(mutationThread.getMutations());

            int totalCrossoverThreads = (int) Math.round(chromosomes.size() * 0.1);
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
            for (Crossover crossoverThread : crossoverThreads) newChromosomes.addAll(crossoverThread.getRecombinations());


            chromosomes = newChromosomes;

            Collections.sort(chromosomes);
        }

        System.out.println("\n------------Final Chromosome------------");
        System.out.println(chromosomes.get(chromosomes.size() - 1));
    }
}
