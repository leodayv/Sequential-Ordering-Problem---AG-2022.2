package geneticFunctions;

import java.util.ArrayList;
import java.util.Random;
public class Mutation {
    final Random rd = new Random();
    private ArrayList<Chromosome> chromosomes;

    private double percentage;

    private Chromosome mutation;

    public Mutation(ArrayList<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public void simpleInversion() {
        while (mutation == null) {
            Chromosome child = new Chromosome(chromosomes.get(rd.nextInt(chromosomes.size())));
            if(child.isValid()) mutation = child;
        }
    }

     public void displacement(){
        while (mutation == null){
            Chromosome parent = chromosomes.get(rd.nextInt(chromosomes.size()));

            int[] parentGenes = parent.getGenes();
            int dim = parent.getDim();

            int displacementLength = (int)Math.floor(parentGenes.length/4.0);
            int displacementPos = rd.nextInt(1, (parentGenes.length - displacementLength) - 1);

            int[] genes = new int[parentGenes.length];
            for (int i = displacementPos, j = 0; j < displacementLength; i++, j++) {
                genes[i] = parentGenes[i];
            }

            for (int i = 0; i < genes.length; i++) {
                if (i == 0) genes[i] = i;
                else if (i == dim - 1) genes[i] = i;
                else {
                    if (genes[i] == 0){
                        int aux = 0;
                        int index = 1;
                        do {
                            aux = parentGenes[index++];
                        }while (contains(genes, aux));
                        genes[i] = aux;
                    }
                }
            }
            Chromosome child = new Chromosome(genes, parent.getAdjMatrix());
            if(child.isValid()) mutation = child;
        }
    }

    public void scramble() {
        while (mutation == null) {
            Chromosome parent = chromosomes.get(rd.nextInt(chromosomes.size()));

            int[] parentGenes = parent.getGenes().clone();
            int dim = parent.getDim();
            int scrambleLength = (int)Math.floor(parentGenes.length/4.0);
            int scramblePos = rd.nextInt(1, (parentGenes.length - scrambleLength) - 1);

            int [] genes = new int[parentGenes.length];
            int [] scramble = new int [scrambleLength];

            for (int i = scramblePos, j = 0; j < scrambleLength ; j++, i++) {
                scramble[j] = parentGenes[i];
                parentGenes[i] = 0;
            }

            for (int i = 0; i < scrambleLength; i++) {
                int tmp = rd.nextInt(scrambleLength);
                int aux = scramble[tmp];
                scramble[tmp] = scramble[i];
                scramble[i] = aux;
            }

            for (int i = 0, j = 0; i < genes.length ; i++) {
                if ( i == 0){
                    genes[i] = i;
                } else if (i == genes.length - 1) {
                    genes[i] = i;
                } else {
                    if (parentGenes[i] != 0){
                        genes[i] = parentGenes[i];
                    }else {
                        if (scramble[j] != 0 && j+1 < scrambleLength) genes[i] = scramble[j++];
                    }
                }

            }
            Chromosome child = new Chromosome(genes, parent.getAdjMatrix());
            if(child.isValid()) mutation = child;
        }
    }
    private boolean contains(int[] nums, int num){
        for (int j : nums) {
            if (j == num) return true;
        }
        return false;
    }

    public Chromosome getMutation() {
        return mutation;
    }
}
