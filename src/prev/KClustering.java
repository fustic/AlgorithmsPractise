package prev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class KClustering {
    public static void main(String[] args) throws IOException {
//        prev.KClustering kClustering = new prev.KClustering("/Users/vadimivanov/Downloads/clusteringtest1.txt");
//        prev.KClustering kClustering = new prev.KClustering("/Users/vadimivanov/Downloads/clusteringtest2.txt");
//        prev.KClustering kClustering = new prev.KClustering("/Users/vadimivanov/Downloads/primcase.txt");
        KClustering kClustering = new KClustering("/Users/vadimivanov/Downloads/clustering1.txt");
        Date start = new Date();
        System.out.println("max = "+kClustering.getMaxSpacing());
        System.exit(0);
    }

    public KClustering(String filePath) throws FileNotFoundException {
        developGraph(filePath);
    }
    public int getMaxSpacing(){
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
//            System.out.println("cluster size : " + cluster.size() + " edge: "+ e.node1 + " " + e.node2 + " "+ e.cost);
            if (cluster.size() == k) {
                nextEdge = i;
                break;
            }
            if(nodesRoot.get(e.node1) != nodesRoot.get(e.node2)) {
                cluster1 = nodesRoot.get(e.node1);
                cluster2 = nodesRoot.get(e.node2);
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
        boolean isSameRoot = true;
        while (isSameRoot || nextEdge > listEdges.size()) {
            e = listEdges.get(nextEdge);
            isSameRoot = nodesRoot.get(e.node1) == nodesRoot.get(e.node2);
            nextEdge++;
        }
        return e != null ? e.cost : 0;
    }
    private final int k = 4;
    private int numberOfNodes;
    private Map<Integer, Set<Integer>> cluster;
    private Hashtable<Integer, Integer> nodesRoot;
    private Set<Edge> edges;
    private void developGraph(String filePath) throws FileNotFoundException {
        Date start = new Date();
        Scanner s = new Scanner(new File(filePath)).useDelimiter(System.getProperty("line.separator"));
        cluster = new HashMap<Integer, Set<Integer>>();
        nodesRoot = new Hashtable<Integer, Integer>();
        edges = new HashSet<Edge>();
        Edge edge;
        String[] numbers;
        String line;
        int node1, node2, cost;
        numberOfNodes = Integer.valueOf(s.next());
        while (s.hasNext()){

            line=s.next().replace("\r"," ").replace("\t"," ").replace("  ", " ");
            numbers = line.split(" ");
            node1 = Integer.valueOf(numbers[0]);
            nodesRoot.put(node1, node1);
            node2 = Integer.valueOf(numbers[1]);
            nodesRoot.put(node2, node2);
            cost = Integer.valueOf(numbers[2]);
            if(!cluster.containsKey(node1)){
                cluster.put(node1, new HashSet<Integer>());
            }
            if(!cluster.containsKey(node2)){
                cluster.put(node2, new HashSet<Integer>());
            }
            edge = new KClustering.Edge(node1, node2, cost);
            edges.add(edge);

            cluster.get(node2).add(node2);
            cluster.get(node1).add(node1);

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
