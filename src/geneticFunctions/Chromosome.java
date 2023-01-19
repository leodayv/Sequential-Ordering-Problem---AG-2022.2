package geneticFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome>{

    private final int[][] adjMatrix;

    private final int dim;

    private final int[] genes;

    private final double fitness;

    private final Random rd = new Random();

    public Chromosome(int[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
        this.dim = adjMatrix[0].length;

        this.genes = new int[dim];

        for (int i = 0; i < dim; i++) {
            if (i == 0) this.genes[i] = i;
            else if(i == dim-1) this.genes[i] = i;
            else this.genes[i] = rd.nextInt(1,dim-1);
        }

        deleteMults();

        this.fitness = fitnessCalc();
    }

    public Chromosome(Chromosome parent){
        this.adjMatrix = parent.getAdjMatrix();
        this.dim = parent.getDim();
        this.genes = parent.getGenes();

        int swap = rd.nextInt(1, dim-1);
        if (swap == 1){
            genes[swap] += genes[swap+1];
            genes[swap+1] = genes[swap] - genes[swap+1];
            genes[swap] = genes[swap] - genes[swap+1];
            deleteMults();
            this.fitness = fitnessCalc();
            return;
        } else if (swap == dim-2) {
            genes[swap] += genes[swap-1];
            genes[swap-1] = genes[swap] - genes[swap-1];
            genes[swap] = genes[swap] - genes[swap-1];
            deleteMults();
            this.fitness = fitnessCalc();
            return;
        }

        boolean rightSwap = rd.nextBoolean();
        if (rightSwap){
            genes[swap] += genes[swap+1];
            genes[swap+1] = genes[swap] - genes[swap+1];
            genes[swap] = genes[swap] - genes[swap+1];
        }else {
            genes[swap] += genes[swap-1];
            genes[swap-1] = genes[swap] - genes[swap-1];
            genes[swap] = genes[swap] - genes[swap-1];
        }
        deleteMults();
        this.fitness = fitnessCalc();
    }

    public Chromosome(Chromosome parent1, Chromosome parent2, boolean[] mask){
        this.adjMatrix = parent1.getAdjMatrix();
        this.dim = parent1.getDim();
        this.genes = new int[dim];

        int[] parent1Genes = parent1.getGenes();
        int[] parent2Genes = parent2.getGenes();

        for (int i = 0; i < dim; i++) {
            if (i == 0) genes[i] = i;
            else if (i == dim-1) genes[i] = i;
            else {
                if(mask[i]){
                    genes[i] = parent1Genes[i];
                }else {
                    genes[i] = parent2Genes[i];
                }
            }
        }
        deleteMults();

        this.fitness = fitnessCalc();
    }

    public Chromosome(int[] genes, int[][] adjMatrix){
        this.adjMatrix = adjMatrix;
        this.dim = adjMatrix[0].length;

        this.genes = genes;

        deleteMults();

        this.fitness = fitnessCalc();
    }

    private void deleteMults(){
        int[] occurrences = new int[genes.length+1];
        for (int gene : genes) {
            occurrences[gene] += 1;
        }

        ArrayList<Integer> missing = new ArrayList<>();
        for (int i = 1; i < occurrences.length; i++) {
            if(occurrences[i] == 0){
                missing.add(i);
            }
        }
        if(!missing.isEmpty()){
            boolean[] alreadyIn = new boolean[genes.length + 2];
            for (int i = 0; i < genes.length; i++) {
                if (alreadyIn[genes[i]] || genes[i] > genes.length || genes[i] < 0){
                    genes[i] = missing.remove(0);
                }else {
                    alreadyIn[genes[i]] = true;
                }
            }
        }
    }

    private double fitnessCalc(){
        if(isValid()) return (double)1/pathCost() * 100;
        else return (double)1/pathCost();
    }

    private int pathCost(){
        int pathCost = 0;
        for (int i = 0; i < genes.length - 1; i++) {
            pathCost += adjMatrix[genes[i]][genes[i+1]];
        }
        return pathCost;
    }

    public boolean isValid(){
        for (int i = 0; i < genes.length; i++) {
            for (int j = 0; j < i; j++) {
                if (adjMatrix[genes[j]][genes[i]] == -1 || adjMatrix[genes[j]][genes[i]] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    public int getDim() {
        return dim;
    }

    public int[] getGenes() {
        return genes;
    }

    public double getFitness() {
        return fitness;
    }


    @Override
    public int compareTo(Chromosome x) {
        return Double.compare(this.fitness, x.getFitness());
    }

    @Override
    public String toString(){
        return "Path: " + Arrays.toString(this.genes) + "\nFitness: " + getFitness() + "\nPath Cost: "  + pathCost() + "\nis Valid: " + isValid();
    }
}
