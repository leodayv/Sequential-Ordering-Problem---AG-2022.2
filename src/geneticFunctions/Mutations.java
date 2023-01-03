package geneticFunctions;

import java.util.ArrayList;
import java.util.Random;

public class Mutations {
    static Random rd = new Random();

    public static ArrayList<Chromosome> simpleInversion(ArrayList<Chromosome> chromosomes){

        int totalMutations = (int)Math.round(chromosomes.size() * 0.1);
        ArrayList<Chromosome> mutations = new ArrayList<>();

        while (mutations.size() < totalMutations){
            Chromosome mutation = new Chromosome(chromosomes.get(rd.nextInt(chromosomes.size())));
            if (mutation.isValid()) mutations.add(mutation);
        }

        return mutations;
    }
}
