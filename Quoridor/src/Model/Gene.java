package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Gene implements Serializable {

    double[] chromosome;
    int point = 0;

    public Gene() {
        chromosome = new double[4];
    }

    public Gene(double n1, double n2, double n3, double n4) {
        chromosome = new double[]{n1, n2, n3, n4};
    }

    public Gene(Gene gene) {

        chromosome = new double[4];
        for (int i = 0; i < 4; i++) {
            this.chromosome[i] = gene.chromosome[i];
        }
        this.point = 0;
    }

}

class Generation implements Serializable {

    ArrayList<Gene> genes;

    public Generation() {
        genes = new ArrayList<>();
    }
}

class initial_Generation implements Serializable {
//    public static void main(String[] args) {
//        ArrayList<Generation> generations = new ArrayList<>();
//
//        Generation generation = new Generation();
//
//        Gene gene = new Gene(1.5, 1.5, 1, 2);
//        Gene gene2 = new Gene(1.9, 1.8, 1.3, 0.9);
//        generation.genes.add(gene);
//        generation.genes.add(gene2);
//
//        for (int i = 0; i < 7; i++) { //crossover - baznamaie khati - average
//            Random r = new Random();
//            Gene gene3 = new Gene();
//            for (int j = 0; j < 4; j++) {
//                double a = r.nextDouble() * 2;
//                gene3.chromosome[j] = gene.chromosome[j] * a + gene2.chromosome[j] * (1 - a);
//            }
//            generation.genes.add(gene3);
//        }
//        generations.add(generation);
//
//        try {
//            FileOutputStream os = new FileOutputStream(new File("src/Source/current_generation.txt"));
//            FileOutputStream os2 = new FileOutputStream(new File("src/backup.txt"));
//
//            ObjectOutputStream oo = new ObjectOutputStream(os);
//            ObjectOutputStream oo2 = new ObjectOutputStream(os2);
//
//            oo.writeObject(generation);
//            oo2.writeObject(generations);
//
//            oo.close();
//            os.close();
//            oo2.close();
//            os2.close();

//            FileInputStream is = new FileInputStream("src/backup.txt");
//            ObjectInputStream oi = new ObjectInputStream(is);
//            Generation generation1 = (Generation) oi.readObject();
//            for (int i = 0; i < generation1.genes.size(); i++) {
//                for (int j = 0; j < 4; j++) {
//                    System.out.print(generation1.genes.get(i).chromosome[j] + " , ");
//                }
//                System.out.println("");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}