package Classes;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Genes {

    private int size = 32;


    private int[] geneTab;

    public int[] getGeneTab() {
        return geneTab;
    }

    public Genes(){
        geneTab  = new int[size];
        Random rand = new Random();

        for (int i = 0; i<7; i++){
            geneTab[i] = i;
        }
        for (int i = 7; i<size; i++){
            geneTab[i] = rand.nextInt(8);
        }
        Arrays.sort(geneTab);
    }

    public Genes(Genes g1, Genes g2){
        geneTab  = new int[size];
        Random rand = new Random();

        int firstDiv = rand.nextInt(size) -1;
        int secondDiv = firstDiv;

        while (secondDiv == firstDiv) {
            secondDiv = rand.nextInt(size) -1;
        }
        if (firstDiv > secondDiv) {
            int tmp = firstDiv;
            firstDiv = secondDiv;
            secondDiv = tmp;
        }

        for (int i = 0; i <= firstDiv; i++) {
            geneTab[i] = g1.getGeneTab()[i];
        }
        for (int i = firstDiv + 1; i <= secondDiv; i++) {
            geneTab[i] = g2.getGeneTab()[i];
        }
        for (int i = secondDiv; i < size; i++) {
            geneTab[i] = g1.getGeneTab()[i];
        }

        int[] randomPositions = generateRandomNumbers(8,size);
        for (int i = 0; i<8; i++) {
            geneTab[randomPositions[i]] = i;
        }
        Arrays.sort(geneTab);

    }


    public int[] generateRandomNumbers(int numberCount, int maxValue){
        int[] result = new int[numberCount];
        Random rand = new Random();
        for (int i = 0; i<numberCount;i++){
            result[i] = rand.nextInt(maxValue);
        }
        return result;
    }

    public int returnRandomGen() {
        Random rand = new Random();
        int returnGene = rand.nextInt(size);
        return geneTab[returnGene];
    }

    @Override
    public String toString() {
        return Arrays.toString(geneTab);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genes genes = (Genes) o;
        return size == genes.size &&
                Arrays.equals(geneTab, genes.geneTab);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(geneTab);
        return result;
    }
}
