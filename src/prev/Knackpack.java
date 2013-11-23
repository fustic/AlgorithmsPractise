package prev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class Knackpack {
    public static void main(String[] args) throws IOException {
//        prev.Knackpack knackpack = new prev.Knackpack("/Users/vadimivanov/Downloads/knapsacktest1.txt");
//        prev.Knackpack knackpack = new prev.Knackpack("/Users/vadimivanov/Downloads/knapsacktest3.txt");
//        prev.Knackpack knackpack = new prev.Knackpack("/Users/vadimivanov/Downloads/knapsack1.txt");
        Knackpack knackpack = new Knackpack("/Users/vadimivanov/Downloads/knapsack_big.txt");
        Date start = new Date();
        System.out.println("max = "+knackpack.getOptimalSolution());
        System.exit(0);
    }



    public Knackpack(String filePath) throws FileNotFoundException {
        constructData(filePath);
    }
    public int getOptimalSolution() {
        int optimalSolution = 0;
//        int[][] A = new int[numberOfItems][capacity];
//        for(int x = 0; x < capacity; x++){
//            A[0][x] = 0;
//        }
        int[] A = new int[capacity];
        int[] B = new int[capacity];
        for(int x = 0; x < capacity; x++) {
            A[x] = 0;
        }
        for(int i = 1; i < numberOfItems; i++){
            for(int x = 0; x < capacity; x++){
                if (x >= weights[i]) {
                    //A[i][x] = Math.max(A[i - 1][x], A[i - 1][x - weights[i]] + values[i]);
                    B[x] = Math.max(A[x], (A[x - weights[i]] + values[i]));
                } else {
//                    A[i][x] = A[i - 1][x];
                    B[x] = A[x];
                }
            }
            A = B;
            B = new int[capacity];
        }
        return A[capacity - 1];
    }

    private int numberOfItems;
    private int capacity;
    private int[] values;
    private int[] weights;
    private void constructData(String filePath) throws FileNotFoundException {
        Date start = new Date();
        Scanner s = new Scanner(new File(filePath)).useDelimiter(System.getProperty("line.separator"));
        String[] numbers;
        numbers = s.next().replace("\r"," ").replace("\t"," ").replace("  ", " ").split(" ");
        numberOfItems = Integer.valueOf(numbers[1]);
        capacity = Integer.valueOf(numbers[0]);
        values = new int[numberOfItems];
        weights = new int[numberOfItems];
        int i = 0;
        while (s.hasNext()) {
            numbers = s.next().replace("\r"," ").replace("\t"," ").replace("  ", " ").split(" ");
            values[i] = Integer.valueOf(numbers[0]);
            weights[i] = Integer.valueOf(numbers[1]);
            i++;
        }
        s.close();
        Helpers.getDiffTime(start, "Structure creation time is: ");

    }

}
