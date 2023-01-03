package geneticFunctions;

import java.util.ArrayList;
import java.util.Random;

public class Mutations {
    static Random rd = new Random();
    static int totalMutations;

    public static ArrayList<Chromosome> simpleInversion(ArrayList<Chromosome> chromosomes, double percentage) {

        totalMutations = (int) Math.round(chromosomes.size() * percentage);
        ArrayList<Chromosome> mutations = new ArrayList<>();

        while (mutations.size() < totalMutations) {
            Chromosome mutation = new Chromosome(chromosomes.get(rd.nextInt(chromosomes.size())));
            if (mutation.isValid()) mutations.add(mutation);
        }

        return mutations;
    }

     public static ArrayList<Chromosome> displacement(ArrayList<Chromosome> chromosomes, double percentage){

        totalMutations = (int)Math.round(chromosomes.size()*percentage);
        ArrayList<Chromosome> mutations = new ArrayList<>();

        while (mutations.size() < totalMutations){
            Chromosome parent = chromosomes.get(rd.nextInt(chromosomes.size()));

            int[] parentGenes = parent.getGenes();
            int dim = parent.getDim();

            int displacementLength = Math.round(parentGenes.length/3);
            int displacementPos = rd.nextInt(1, (chromosomes.size() - displacementLength) - 1);
            int[] displacementGenes = new int[displacementLength];

            for (int i = 0, index = displacementPos; i < displacementGenes.length; i++, index++) {
                displacementGenes[i] = parentGenes[index];
                parentGenes[index] = 0;
            }

            int[] genes = new int[parentGenes.length];
            for (int i = 0; i < genes.length; i++) {
                if (i == 0) genes[i] = i;
                else if (i == dim-1) genes[i] = i;
                else {
                    if (parentGenes[i] == 0){
                        for (int j = i; j < parentGenes.length; j++) {
                            if (parentGenes[j] == 0) continue;
                            else {
                                genes[i] = parentGenes[j];
                                parentGenes[j] = -1;
                                break;
                            }
                        }
                    }else genes[i] = parentGenes[i];
                }
            }

            for (int i = 0, index = 0; i < genes.length; i++) {
                if (genes[i] != -1) continue;
                else {
                    genes[i] = displacementGenes[index++];
                }
            }
            Chromosome mutation = new Chromosome(genes, parent.getAdjMatrix());
            if (mutation.isValid()) mutations.add(mutation);
        }
        return mutations;
    }

    public static ArrayList<Chromosome> scramble(ArrayList<Chromosome> chromosomes, double percentage) {

        totalMutations = (int) Math.round(chromosomes.size() * percentage);
        ArrayList<Chromosome> mutations = new ArrayList<>();

        while (mutations.size() < totalMutations) {
            Chromosome parent1 = chromosomes.get(rd.nextInt(chromosomes.size()));
            int[] parent1Genes = parent1.getGenes();



        }

        return mutations;
    }
}
