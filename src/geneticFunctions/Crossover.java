package geneticFunctions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Crossover {
    static Random rd = new Random();

    public static ArrayList<Chromosome> uniformCrossover(ArrayList<Chromosome> chromosomes, double percentage){
        int totalRecombinations = (int)Math.round(chromosomes.size() * percentage);
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

        return recombinations;
    }
    public static ArrayList<Chromosome> singlePoint(ArrayList<Chromosome> chromosomes, double percentage) {

        int totalRecombinations = (int) Math.round(chromosomes.size() * percentage);
        ArrayList<Chromosome> recombinations = new ArrayList<>();

        while (recombinations.size() < totalRecombinations) {
            Chromosome parent1 = chromosomes.get(rd.nextInt(chromosomes.size()));
            Chromosome parent2 = chromosomes.get(rd.nextInt(chromosomes.size()));
            int[] parent1Genes = parent1.getGenes();
            int[] parent2Genes = parent2.getGenes();

            int point = rd.nextInt(parent1Genes.length);
            int[] child1 = new int[parent1Genes.length];
            int[] child2 = new int[parent1Genes.length];

            for (int i = 0; i < point ; i++) {
                child1[i] = parent1Genes[i];
                child2[i] = parent2Genes[i];
            }

            for (int i = point; i < parent2Genes.length ; i++) {
                child1[i] = parent2Genes[i];
                child2[i] = parent1Genes[i];
            }

            Chromosome C1 = new Chromosome(child1, parent1.getAdjMatrix());
            Chromosome C2 = new Chromosome(child2, parent2.getAdjMatrix());

            if(C1.isValid()){
                recombinations.add(C1);
            }
            if(C2.isValid()){
                recombinations.add(C2);
            }

        }
        return recombinations;
    }


}
