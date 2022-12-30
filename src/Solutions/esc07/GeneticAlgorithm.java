package Solutions.esc07;

import geneticFunctions.Chromosome;
import geneticFunctions.Parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
public class GeneticAlgorithm {
    public static void main(String[] args) throws FileNotFoundException {
        Random rd = new Random();
        int[][] adjMatrix = Parser.parse("Problems/esc07.sop");

        int runs = 5;
        Chromosome best = null;
        for(int i = 0; i < runs; i++){
            ArrayList<Chromosome> chromosomes = new ArrayList<>();

            int popSize = 1000;
            while (chromosomes.size() < popSize){
                chromosomes.add(new Chromosome(adjMatrix));
            }

            int generations = 100;

            for (int j = 0; j < generations; j++) {

                int totalSelections = (int)Math.round(chromosomes.size() * 0.8);
                ArrayList<Chromosome> selections = new ArrayList<>();

                while(selections.size() < totalSelections){
                    ArrayList<Chromosome> tournament = new ArrayList<>();

                    for (int k = 0; k < 10; k++) {
                        tournament.add(chromosomes.get(rd.nextInt(chromosomes.size())));
                    }
                    int winnerPos = 0;
                    double winnerFitness = Double.MIN_VALUE;

                    for (int k = 0; k < tournament.size(); k++) {
                        if (tournament.get(k).getFitness() > winnerFitness){
                            winnerPos = k;
                            winnerFitness = tournament.get(k).getFitness();
                        }
                    }
                    selections.add(tournament.get(winnerPos));
                }
                ArrayList<Chromosome> newChromosomes = new ArrayList<>(selections);

                int totalMutations = (int)Math.round(chromosomes.size() * 0.1);
                ArrayList<Chromosome> mutations = new ArrayList<>();

                while (mutations.size() < totalMutations){
                    Chromosome mutation = new Chromosome(chromosomes.get(rd.nextInt(chromosomes.size())));
                    if (mutation.isValid()) mutations.add(mutation);
                }
                newChromosomes.addAll(mutations);

                int totalRecombinations = (int)Math.round(chromosomes.size() * 0.1);
                ArrayList<Chromosome> recombinations = new ArrayList<>();

                while (recombinations.size() < totalRecombinations){
                    Chromosome parent1 = chromosomes.get(rd.nextInt(chromosomes.size()));
                    Chromosome parent2 = chromosomes.get(rd.nextInt(chromosomes.size()));

                    boolean[] mask = new boolean[parent1.getDim()];
                    for (int k = 0; k < mask.length; k++) {
                        mask[k] = rd.nextBoolean();
                    }
                    Chromosome child1 = new Chromosome(parent1, parent2, mask);
                    if (child1.isValid()) recombinations.add(child1);

                    Chromosome child2 = new Chromosome(parent2, parent1, mask);
                    if (child2.isValid()) recombinations.add(child2);
                }
                newChromosomes.addAll(recombinations);

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