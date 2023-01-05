package geneticFunctions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Crossover {
    Random rd = new Random();

    private int totalRecombinations;

    private ArrayList<Chromosome> chromosomes;

    private ArrayList<Chromosome> recombinations;

    public Crossover(ArrayList<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
        recombinations = new ArrayList<>();
    }

    public void uniformCrossover(){

        while (recombinations.size() < 2){
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
    }
    public void singlePoint() {

        while (recombinations.size() < 2) {
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
    }

    public void orderBasedCrossover(){
        while (recombinations.size() < 2){
            Chromosome parent1 = chromosomes.get(rd.nextInt(chromosomes.size()));
            Chromosome parent2 = chromosomes.get(rd.nextInt(chromosomes.size()));
            int[] parent1Genes = parent1.getGenes();
            int[] parent2Genes = parent2.getGenes();

            int segmentLength = (int)Math.floor(parent1Genes.length/5.0);
            int segmentPos = rd.nextInt(1, (parent1Genes.length - segmentLength) - 1);

            int[] child1Genes = new int[parent1Genes.length];
            int[] child2Genes = new int[parent2Genes.length];

            for (int i = segmentPos, j = 0; j < segmentLength; i++, j++) {
                child1Genes[i] = parent1Genes[i];
                child2Genes[i] = parent2Genes[i];
            }

            for (int i = 0; i < child1Genes.length; i++) {
                if (i == 0){
                    child1Genes[i] = i;
                    child2Genes[i] = i;
                } else if (i == child1Genes.length-1){
                    child1Genes[i] = i;
                    child2Genes[i] = i;
                }else {
                    if (child1Genes[i] == 0){
                        int aux = 0;
                        int index = 1;
                        do {
                            aux = parent2Genes[index++];
                        }while (contains(child1Genes, aux));

                        child1Genes[i] = aux;

                        index = 1;
                        aux = 0;
                        do {
                            aux = parent1Genes[index++];
                        }while (contains(child2Genes, aux));
                        child2Genes[i] = aux;
                    }
                }
            }

            Chromosome child1 = new Chromosome(child1Genes, parent1.getAdjMatrix());
            Chromosome child2 = new Chromosome(child2Genes, parent2.getAdjMatrix());

            if (child1.isValid()) recombinations.add(child1);
            if (child2.isValid()) recombinations.add(child2);
        }
    }
    private boolean contains(int[] nums, int num){
        for (int j : nums) {
            if (j == num) return true;
        }
        return false;
    }

    public ArrayList<Chromosome> getRecombinations() {
        return recombinations;
    }
}
