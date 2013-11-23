package prev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TwoSUM {
    public static void main(String[] args) throws IOException {

//        prev.TwoSUM twoSUM = new prev.TwoSUM("/Users/vadimivanov/Downloads/2sumTest1.txt");
//        prev.TwoSUM twoSUM = new prev.TwoSUM("/Users/vadimivanov/Downloads/algo1-programming_prob-2sum.txt");
        //50195
//        System.out.println(twoSUM.countPairs());
//        twoSUM.throwNumbers();

        String s = "Hello World";
        s.toUpperCase();
        s.substring(6, 11);
        System.out.println(s);

        System.exit(0);
    }

    private HashSet<Long> numbers;
    public TwoSUM(String filePath) throws FileNotFoundException {
        numbers = getNumbers(filePath);

    }

    public int countPairs(){
        int sum = 0;
        Long y;
        Date start = new Date();
        HashSet<Integer> sums = new HashSet<Integer>();
        for(int i = -10000; i<=10000; i++){
            sums.add(i);
        }
        Helpers.getDiffTime(start,"sums creation time:");
        ArrayList<Integer> deleteSums;
        int sumSize=0;
        for(long number: numbers){
            deleteSums = new ArrayList<Integer>();
            sumSize=sums.size();
            for(int t : sums){
                y = t - number;
                if(number!=y && numbers.contains(y)){
                    sum++;
                    deleteSums.add(t);
                }
            }
            sums.removeAll(deleteSums);
            if(sums.size()==0)
                break;
            if(sums.size()!=sumSize)
                System.out.println("Sums count:"+sums.size());
        }
        Helpers.getDiffTime(start,"countPairs time: ");
        return sum;
    }
    private void throwNumbers(){
        Date start = new Date();
        Long i = Long.valueOf(1);
        TreeSet<Long> ns = new TreeSet<Long>(numbers);
        Hashtable<Long, HashSet<Long>> numbs = new Hashtable<Long, HashSet<Long>>();
        HashSet<Long> localNumbers = new HashSet<Long>();
        int width = 20001;
        long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
        for (long n : ns){
            if(max<n){
                if(min!=Long.MAX_VALUE)
                    numbs.put(i, localNumbers);
                min = n;
                max = min + width*i;
                localNumbers = new HashSet<Long>();
                i++;
            }
            localNumbers.add(n);

        }
        numbs.put(i, localNumbers);
        Helpers.getDiffTime(start, "i = "+i+" -- total time:");
    }


    private HashSet<Long> getNumbers(String filePath) throws FileNotFoundException {
        Date start = new Date();
        Scanner s = new Scanner(new File(filePath)).useDelimiter(System.getProperty("line.separator"));
        HashSet<Long> numbers = new HashSet<Long>();

        String number;
        while (s.hasNext()){
            number=s.next().replace("\r","").replace("\t","");
            numbers.add(Long.valueOf(number));
        }

        s.close();
        Helpers.getDiffTime(start, "Numbers creation time is: ");
        return numbers;
    }


}
