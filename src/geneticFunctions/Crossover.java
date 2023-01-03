package geneticFunctions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Crossover {
    static Random rd = new Random();

    public static ArrayList<Chromosome> uniformCrossover(ArrayList<Chromosome> chromosomes){
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

        return recombinations;
    }
}
