package Model;


import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Learning implements Serializable {
    PlayAIvsAI playAIvsAI;
    Generation generation;
    Generation nextGeneration;

    void readGeneration() {
        try {
            FileInputStream is = new FileInputStream("src/Source/current_generation.txt");
            ObjectInputStream oi = new ObjectInputStream(is);
            generation = (Generation) oi.readObject();

            oi.close();
            is.close();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void play() {
        for (int i = 0; i < 3; i += 3) {
            int k = i;
            for (int j = k + 1; j < i + 3; j++) {
                playAIvsAI = new PlayAIvsAI();
                playAIvsAI.turnA = true;
                playAIvsAI.gene1 = generation.genes.get(k);
                playAIvsAI.gene2 = generation.genes.get(j);

                System.out.println("/////////////////////");
                System.out.println("gene " + k + " , playing with gene " + j);
                System.out.println("/////////////////////");

                for (int h = 0; h < 200; h++) {
                    playAIvsAI.setGene();
                    playAIvsAI.AIvsAITurn();
                    if (playAIvsAI.goalState()) {
                        if (playAIvsAI.turnA) {
                            System.out.println("gene " + k + " won");
                            generation.genes.get(k).point += 1;
                        }
                        else {
                            System.out.println("gene " + j + " won");
                            generation.genes.get(j).point += 1;
                        }
                        break;
                    } else {
                        playAIvsAI.updateTurn();
                    }
                }
            }
            k++;
        }

    }

    void generateNextGeneration() {

        //sorts the generation
        sort(generation.genes, 0, generation.genes.size() - 1);

        nextGeneration = new Generation();

        for (int i = 0; i < 3; i++) {
            nextGeneration.genes.add(new Gene(generation.genes.get(i)));
        }
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            int a1 = r.nextInt(9);
            int a2 = r.nextInt(9);
            double alpha = r.nextDouble();
            Gene gene = new Gene();
            for (int j = 0; j < 4; j++) {
                gene.chromosome[j] = (generation.genes.get(a1).chromosome[j] * alpha) + (generation.genes.get(a2).chromosome[j] * (1 - alpha));
            }
            nextGeneration.genes.add(gene);
        }
        for (int i = 0; i < 3; i++) {
            Gene gene = new Gene();
            int a1 = r.nextInt(9);
            int a2 = r.nextInt(9);
            int cut = r.nextInt(4);
            for (int j = 0; j < cut; j++) {
                gene.chromosome[j] = generation.genes.get(a1).chromosome[j];
            }
            for (int j = cut; j < 4; j++) {
                gene.chromosome[j] = generation.genes.get(a2).chromosome[j];
            }
            nextGeneration.genes.add(gene);
        }
    }

    void writeGeneration() {
        try {
            FileInputStream is = new FileInputStream("src/backup.txt");
            ObjectInputStream oi = new ObjectInputStream(is);
            ArrayList<Generation> backupGeneration = (ArrayList<Generation>) oi.readObject();

            backupGeneration.add(nextGeneration);

            FileOutputStream os = new FileOutputStream(new File("src/Source/current_generation.txt"));
            FileOutputStream os2 = new FileOutputStream(new File("src/backup.txt"));

            ObjectOutputStream oo = new ObjectOutputStream(os);
            ObjectOutputStream oo2 = new ObjectOutputStream(os2);

            oo.writeObject(nextGeneration);
            oo2.writeObject(backupGeneration);

            oo.close();
            os.close();
            oo2.close();
            os2.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    int partition(ArrayList<Gene> arr, int low, int high) {
        int pivot = arr.get(high).point;
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr.get(j).point > pivot) {
                i++;
                Gene temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }

        Gene temp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, temp);
        return i + 1;
    }

    void sort(ArrayList<Gene> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            sort(arr, low, pi - 1);
            sort(arr, pi + 1, high);
        }
    }

}

class AutoLearning {
    public static void main(String[] args) {

        Learning learning = new Learning();
        learning.readGeneration();
        learning.play();
        learning.generateNextGeneration();
        learning.writeGeneration();

    }
}
