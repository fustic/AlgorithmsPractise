package facebook.square.detector;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Solution {
    public static void main(String[] args) throws IOException {
        Solution solution = new Solution("/Users/vadimivanov/Downloads/square_detector_example_input.txt");
        System.exit(0);
    }

    public Solution(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        proceedSquares(file);
    }


    private void proceedSquares(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file).useDelimiter(System.getProperty("line.separator"));
        int N = Integer.parseInt(s.next());

        String line;
        String[] elements;
        int dimension;
        int numberBlacks;
        int lineLen;
        while (s.hasNext()) {
            dimension = Integer.parseInt(s.next());
            int[][] square = new int[dimension][dimension];
            numberBlacks = 0;
            for (int i = 0; i < dimension; ++i) {
                line = s.next().replace("\r"," ").replace("\t"," ").replace("  ", " ");
                lineLen = line.length();
                for (int j = 0; j < lineLen; ++j) {

                    if (Character.toString(line.charAt(j)).equals("#")) {
                        numberBlacks++;
                        square[i][j] = 1;
                    } else {
                        square[i][j] = 0;
                    }

                }
            }
            proceedSquare(square, numberBlacks);

        }
    }

    private int caseNumber = 0;
    private void proceedSquare(int[][] square, int nBlacks) {
        int blackCounter = 0;
        int foundBlacks;
        if (nBlacks <= 3 || (nBlacks != Math.pow(Math.sqrt(nBlacks), 2))) {
            System.out.println("Case #" + (++caseNumber) + ": NO");
        }
          for (int i = 0; i < square.length; ++i) {
            for (int j = 0; j < square.length; ++j) {
                if (square[i][j] == 1) {
//                    if ()
                    System.out.println(ToStringBuilder.reflectionToString(square[i]));
                }
            }
        }

        System.out.println("Case #" + (++caseNumber) + ": YES");

    }

}
