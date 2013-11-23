import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Date;

public class TSP {
    public static void main(String[] args) throws IOException {
        TSP tsp = new TSP("/Users/vadimivanov/Downloads/tsp1.txt");
        java.util.Date start = new Date();
        System.out.println("k = "+tsp.getMinCost());
        System.exit(0);
    }

    public int getMinCost() {
        //double[][] A = new double[N][N];

        Hashtable<Set<Integer>, Integer> A = new Hashtable<Set<Integer>, Integer>();
        HashSet<Integer> subSet = new HashSet<Integer>();
        subSet.add(1);
        A.put(subSet, 0);

        Set<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        set.add(6);
        Set<Set<Integer>> a = generateSubSets(2, new HashSet<Integer>(set));

        return 0;
    }

//    private Set<Set<Integer>> generateSubSet(){
//        List<List<String>> ncr = new ArrayList<List<String>>();
//        for (long x = (1 << r) - 1; (x >>> r) == 0;) {
//            // x contains a 1 bit for each input we should choose;
//            // iterate over the 1 bits of x
//            long y = x;
//            List<String> combination = new ArrayList<String>();
//            for (int i = Long.numberOfTrailingZeros(y);
//                 y != 0; i = Long.numberOfTrailingZeros(y)) {
//                combination.add(inputs.get(i));
//                y &= ~(1 << i);
//            }
//            long u = x & -x;
//            long v = u + x;
//            x = v + ((v ^ x) / u) >>> 2;
//        }
//        return ncr;
//    }

    private Set<Set<Integer>> generateSubSets(int len, Set<Integer> originalSet){
        Set<Set<Integer>> sets = new HashSet<Set<Integer>>();
        if (originalSet.isEmpty()){// || len < 0 || len >= N) {
            sets.add(new HashSet<Integer>());
            return sets;
        }
        List<Integer> list = new ArrayList<Integer>(originalSet);
        Integer head = list.get(0);
        Set<Integer> rest = new HashSet<Integer>(list.subList(1, list.size()));
        int localLen = new Integer(len);
        for (Set<Integer> set : generateSubSets(--len, rest)) {
            Set<Integer> newSet = new HashSet<Integer>();
            newSet.add(head);
            newSet.addAll(set);
//            if(newSet.size() == localLen) {
                sets.add(newSet);
                sets.add(set);
//            }
        }
        return sets;

    }
    private int N;
    private City[] cities;
    public TSP(String filePath) throws FileNotFoundException {
        Scanner s = new Scanner(new File(filePath)).useDelimiter(System.getProperty("line.separator"));
        N = Integer.valueOf(s.next().replace("\r", " ").replace("\t", " ").replace("  ", " ").split(" ")[0]);
        String[] numbers;
        String line;
        cities = new City[N+1];
        int i = 1;
        while(s.hasNext()){
            line = s.next().replace("\r"," ").replace("\t"," ").replace("  ", " ");
            numbers = line.split(" ");
            cities[i] = new City(Double.valueOf(numbers[0]), Double.valueOf(numbers[1]));
            i++;
        }
        s.close();


    }
    private class City {
        private double x;
        private double y;
        public City(double xx, double yy) {
            x = xx;
            y = yy;
        }
        public double getX(){
            return x;
        }
        public double getY(){
            return y;
        }
        public double countDistanceToCity(City city){
            return Math.sqrt(Math.pow((this.getX() - city.getX()),2) + Math.pow((this.getY() - city.getY()),2));
        }
    }
}
