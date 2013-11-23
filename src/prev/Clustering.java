package prev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Clustering {
    public static void main(String[] args) throws IOException {
        Clustering clustering = new Clustering("/Users/vadimivanov/Downloads/example1.txt");
        Date start = new Date();
        System.out.println("k = "+clustering.getK());
        System.exit(0);
    }

    public Clustering(String filePath) throws FileNotFoundException {
        developGraph(filePath);
        setEdges();
    }
    private void setEdges(){
        String bits, _bits;
        char[] bitsChar;
        for (int n : nodesDist.keySet()) {
            bits = nodesDist.get(n);
            addEdges(n, bits, 0);
            bitsChar = bits.toCharArray();

            for (int i = 0; i < bitsChar.length; i++) {
                bitsChar[i] = swapDigit(bitsChar[i]);
                _bits = String.valueOf(bitsChar);
                addEdges(n, _bits, 1);
                bitsChar[i] = swapDigit(bitsChar[i]);
            }
            for (int i = 0; i < bitsChar.length - 1; i++) {
                bitsChar[i] = swapDigit(bitsChar[i]);

                for (int j = i+1; j < bitsChar.length; j++) {
                    bitsChar[j] = swapDigit(bitsChar[j]);
                    _bits = bitsChar.toString();
                    addEdges(n, _bits, 2);
                    bitsChar[j] = swapDigit(bitsChar[j]);

                }
                bitsChar[i] = swapDigit(bitsChar[i]);
            }

        }
    }
    private void addEdges (int n, String bits, int cost){
        Edge e;
        if(distances.containsKey(bits)) {
            for(int n2 : distances.get(bits)) {
                if(n != n2) {
                    e = new Edge(n, n2, cost);
                    edges.add(e);


                }
            }
        }
    }
    private char swapDigit(char c){
        int n = Integer.parseInt(String.valueOf(c));
        if(n == 1) {
            return Character.valueOf('0');
        } else {
            return Character.valueOf('1');
        }
    }
    public int getK(){
        int k = 0;
        List<Edge> listEdges = new ArrayList<Edge>(edges);
        Collections.sort(listEdges, new Comparator<Edge>() {
            @Override
            public int compare(Edge edge, Edge edge2) {
                return edge.cost - edge2.cost;
            }
        });
        Edge e = null;
        int nextEdge = 0, cluster1, cluster2;
        for (int i = 0; i < listEdges.size(); i++) {
            e = listEdges.get(i);
//            System.out.println(cluster);
            System.out.println(e.node1+" "+e.node2+" "+e.cost);
            if(nodesRoot.get(e.node1) != nodesRoot.get(e.node2)) {
                cluster1 = nodesRoot.get(e.node1);
                cluster2 = nodesRoot.get(e.node2);
                if(!(cluster.containsKey(cluster1) && cluster.containsKey(cluster2))){
                    continue;
                }
                if(cluster.get(cluster1).size() >= cluster.get(cluster2).size()){
                    for(int n : cluster.get(cluster2)) {
                        cluster.get(cluster1).add(n);
                        nodesRoot.put(n, cluster1);
                    }
                    cluster.remove(cluster2);
                } else {
                    for(int n : cluster.get(cluster1)) {
                        cluster.get(cluster2).add(n);
                        nodesRoot.put(n, cluster2);
                    }
                    cluster.remove(cluster1);
                }
            }
        }

        return cluster.size();
    }

    private int numberOfNodes;
    private Map<String, Set<Integer>> distances;
    private Map<Integer, String> nodesDist;
    private Set<Edge> edges;
    private Map<Integer, Set<Integer>> cluster;
    private Hashtable<Integer, Integer> nodesRoot;
    private void developGraph(String filePath) throws FileNotFoundException {
        Date start = new Date();
        Scanner s = new Scanner(new File(filePath)).useDelimiter(System.getProperty("line.separator"));
        nodesDist = new HashMap<Integer, String>();
        distances = new HashMap<String, Set<Integer>>();
        edges = new HashSet<Edge>();
        cluster = new HashMap<Integer, Set<Integer>>();
        nodesRoot = new Hashtable<Integer, Integer>();
        String[] numbers;
        String line;
        int node1, node2, cost;
        //numberOfNodes = Integer.valueOf(s.next());
        s.next();
        int i = 1;
        String bits = "";
        while (s.hasNext()){

            line=s.next().replace("\r"," ").replace("\t"," ").replace("  ", " ");
            bits = line.replace(" ", "");
            nodesDist.put(i, bits);
            if (!distances.containsKey(bits)) {
                distances.put(bits, new HashSet<Integer>());
            }
            distances.get(bits).add(i);

            if(!cluster.containsKey(i)){
                cluster.put(i, new HashSet<Integer>());
            }
            nodesRoot.put(i, i);
            cluster.get(i).add(i);

            i++;

        }
        s.close();
        Helpers.getDiffTime(start, "Graphs creation time is: ");
    }
    public class Edge {
        public Edge(int n1, int n2, int c){
            node1 = n1;
            node2 = n2;
            cost = c;
        }
        public int node1;
        public int node2;
        public int cost;
    }
}
