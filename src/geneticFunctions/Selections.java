package geneticFunctions;

import java.util.ArrayList;
import java.util.Random;

public class Selections {
    static Random rd = new Random();
    public static ArrayList<Chromosome> tournament(ArrayList<Chromosome> chromosomes){
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
        return selections;
    }

    public ArrayList<Chromosome> roulette(ArrayList<Chromosome> chromosomes){

        ArrayList<Chromosome> selections = new ArrayList<>();
        Random random = new Random();
        double fitnesssum = 0;

        for (int i = 0; i < chromosomes.size(); i++) {
            fitnesssum += chromosomes.get(i).getFitness();
        }
        int totalselections = (int) Math.round(chromosomes.size() * 0.8); //João Paulo utilizou 80% de seleção em sala;

        while (selections.size() < totalselections) {

            double iSum = 0;
            int j = 0;
            double alpha = random.nextDouble(fitnesssum);

            do {
                iSum += chromosomes.get(j).getFitness();
                j += 1;
            } while (iSum < alpha && j < chromosomes.size());

            selections.add(chromosomes.get(j));
        }
        return selections;
    }

}
