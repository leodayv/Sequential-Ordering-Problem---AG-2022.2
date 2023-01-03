package geneticFunctions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Selections {
    static Random rd = new Random();
    public static ArrayList<Chromosome> tournament(ArrayList<Chromosome> chromosomes, double percentage){
        int totalSelections = (int)Math.round(chromosomes.size() * percentage);
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

    public static ArrayList<Chromosome> roulette(ArrayList<Chromosome> chromosomes, double percentage){

        ArrayList<Chromosome> selections = new ArrayList<>();
        double fitnesssum = 0;

        for (int i = 0; i < chromosomes.size(); i++) {
            fitnesssum += chromosomes.get(i).getFitness();
        }
        int totalselections = (int) Math.round(chromosomes.size() * percentage); //João Paulo utilizou 80% de seleção em sala;

        while (selections.size() < totalselections) {

            double iSum = 0;
            int j = 0;
            double alpha = rd.nextDouble(fitnesssum);

            do {
                iSum += chromosomes.get(j).getFitness();
                j += 1;
            } while (iSum < alpha && j < chromosomes.size());

            selections.add(chromosomes.get(j));
        }
        return selections;
    }


    public static ArrayList<Chromosome> rank(ArrayList<Chromosome> chromosomes, double percentage){
        Collections.sort(chromosomes);

        double totalSelections = (int)Math.round(chromosomes.size() * percentage);
        ArrayList<Chromosome> selections = new ArrayList<>();

        double sum = 1.0/(chromosomes.size()-2001);

        while (selections.size() < totalSelections){
            for (int i = 0; i < chromosomes.size(); i++) {
                double alpha = rd.nextDouble(sum);
                double probability = i/chromosomes.size()*(chromosomes.size()-1);

                if (probability <= alpha){
                    selections.add(chromosomes.get(i));
                    break;
                }
            }
        }
        return selections;
    }
}
