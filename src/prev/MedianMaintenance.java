package prev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MedianMaintenance {
    public static void main(String[] args) throws IOException {

        MedianMaintenance medianMaintenance = new MedianMaintenance("/Users/vadimivanov/Downloads/Median.txt");
        System.out.println("med sum : "+medianMaintenance.getMedianSum());
        System.exit(0);
    }
    private List<Integer> numbers;
    public MedianMaintenance(String filePath) throws FileNotFoundException {
        numbers = getNumbers(filePath);
    }
    public int getMedianSum(){
        int sum = 0;
        TreeSet<Integer> heap =new TreeSet<Integer>();
        int median;
        for(int number : numbers){
            heap.add(number);
            median = getMedian(heap);
            sum+=median;
        }

        return sum % 10000;
    }
    private int getMedian(TreeSet<Integer> heap){
        List<Integer> list = new ArrayList<Integer>(heap);
        int index = list.size()/2;
        index = list.size() % 2 == 0 ? index - 1  : index;
        return list.get(index);
    }
    private List<Integer> getNumbers(String filePath) throws FileNotFoundException {
        Date start = new Date();
        Scanner s = new Scanner(new File(filePath)).useDelimiter(System.getProperty("line.separator"));
        List<Integer> numbers = new ArrayList<Integer>();
        String number;
        while (s.hasNext()){
            number=s.next().replace("\r","").replace("\t","");
            numbers.add(Integer.valueOf(number));
        }

        s.close();
        Helpers.getDiffTime(start, "Numbers creation time is: ");
        return numbers;
    }
}
