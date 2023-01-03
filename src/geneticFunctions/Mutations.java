package geneticFunctions;

import java.util.ArrayList;
import java.util.Random;

public class Mutations {
    static Random rd = new Random();
    static int totalMutations;

    public static ArrayList<Chromosome> simpleInversion(ArrayList<Chromosome> chromosomes, double percentage){

        totalMutations = (int)Math.round(chromosomes.size() * percentage);
        ArrayList<Chromosome> mutations = new ArrayList<>();

        while (mutations.size() < totalMutations){
            Chromosome mutation = new Chromosome(chromosomes.get(rd.nextInt(chromosomes.size())));
            if (mutation.isValid()) mutations.add(mutation);
        }

        return mutations;
    }

    public static ArrayList<Chromosome> displacement(ArrayList<Chromosome> chromosomes, double percentage){

        totalMutations = (int)Math.round(chromosomes.size()*percentage);
        ArrayList<Chromosome> mutations = new ArrayList<>();


    }
}
