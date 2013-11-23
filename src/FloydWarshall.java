import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;


public class FloydWarshall {

    public static void main(String[] args) throws IOException {
//        FloydWarshall floydWarshall = new FloydWarshall("/Users/vadimivanov/Downloads/g1.txt");
//        System.out.println("Min Path = " + floydWarshall.getMinPath());
//        System.exit(0);

//        String filePath = "/Users/vadimivanov/Downloads/g1test1.txt";
//        String filePath = "/Users/vadimivanov/Downloads/g1test1.txt";
        String filePath = "/Users/vadimivanov/Downloads/g3.txt";
        Scanner s = new Scanner(new File(filePath)).useDelimiter(System.getProperty("line.separator"));
        String line = s.next().replace("\r"," ").replace("\t"," ").replace("  ", " ");
        String[] numbers = line.split(" ");
        int V = Integer.parseInt(numbers[0]) + 1;
        int E = Integer.parseInt(numbers[1]);
        int n1, n2;
        AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(V);
        while (s.hasNext()){

            line = s.next().replace("\r"," ").replace("\t"," ").replace("  ", " ");
            numbers = line.split(" ");

            n1 = Integer.valueOf(numbers[0]);
            n2 = Integer.valueOf(numbers[1]);
//            w = Integer.valueOf(numbers[2]);
            G.addEdge(new DirectedEdge(n1, n2, Double.parseDouble(numbers[2])));

        }
        s.close();


        // random graph with V vertices and E edges, parallel edges allowed
//        int V = Integer.parseInt(args[0]);
//        int E = Integer.parseInt(args[1]);
//        AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(V);
//        for (int i = 0; i < E; i++) {
//            int v = (int) (V * Math.random());
//            int w = (int) (V * Math.random());
//            double weight = Math.round(100 * (Math.random() - 0.15)) / 100.0;
//            if (v == w) G.addEdge(new DirectedEdge(v, w, Math.abs(weight)));
//            else        G.addEdge(new DirectedEdge(v, w, weight));
//        }

        StdOut.println(G);

        // run Floyd-Warshall algorithm
        FloydWarshall spt = new FloydWarshall(G);

        // print all-pairs shortest path distances
        StdOut.printf("     ");
//        for (int v = 0; v < G.V(); v++) {
//            StdOut.printf("%6d ", v);
//        }
        StdOut.println();
        double min = Double.MAX_VALUE;
        for (int v = 0; v < G.V(); v++) {
//            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); w++) {
                if (spt.hasPath(v, w)) //StdOut.printf("%6.2f !", spt.dist(v, w));
                    if (spt.dist(v, w) < min)
                        min = spt.dist(v, w);
//                else                   StdOut.printf("   Inf ");
            }
//            StdOut.println();
        }

        System.out.println(" min path = " + min);
        // print negative cycle
        if (spt.hasNegativeCycle()) {
            StdOut.println("Negative cost cycle:");
//            for (DirectedEdge e : spt.negativeCycle())
//                StdOut.println(e);
            StdOut.println();
        }


        // print all-pairs shortest paths
        else {
//            for (int v = 0; v < G.V(); v++) {
//                for (int w = 0; w < G.V(); w++) {
//                    if (spt.hasPath(v, w)) {
//                        StdOut.printf("%d to %d (%5.2f)  ", v, w, spt.dist(v, w));
//                        for (DirectedEdge e : spt.path(v, w))
//                            StdOut.print(e + "  ");
//                        StdOut.println();
//                    }
//                    else {
//                        StdOut.printf("%d to %d          no path\n", v, w);
//                    }
//                }
//            }
        }
    }
    public FloydWarshall(String filePath) throws FileNotFoundException {
        developGraph(filePath);
    }

    public int getMinPath(){
        List<Integer> vertices = new ArrayList<Integer>(graph.keySet());
        double[][][] A = new double[N+1][N+1][N+1];
        int n1, n2;
        Double c, min = Double.MAX_VALUE;

        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                if(i == j) {
                    c = 0.0;
                } else {
                    if (graph.containsKey(i) && graph.get(i).containsKey(j)) {
                        c = Double.valueOf(graph.get(i).get(j));
                    } else {
                        c = Double.POSITIVE_INFINITY;
                    }
                }
                 A[i][j][0] = c;
            }
        }

        double[] I, Ip, J, Jp, K, Kp;

        for(int k = 1; k <= N; k++) {
            for(int i = 1; i <= N; i++) {
                I = new double[N+1];
                for(int j = 1; j <= N; j++) {

                    A[i][j][k] = Math.min(
                            A[i][j][k - 1],
                            A[i][k][k - 1] + A[k][j][k - 1]
                    );
                    if(A[i][j][k] <= min)
                        min = A[i][j][k];
                }
            }
        }

        return min.intValue();
    }
    private Hashtable<Integer, Hashtable<Integer, Integer>> graph;
    private int N;
    private void developGraph(String filePath) throws FileNotFoundException {
//        Date start = new Date();
        Scanner s = new Scanner(new File(filePath)).useDelimiter(System.getProperty("line.separator"));

        graph = new Hashtable<Integer, Hashtable<Integer, Integer>>();

        String line = s.next().replace("\r"," ").replace("\t"," ").replace("  ", " ");
        String[] numbers = line.split(" ");
        N = Integer.parseInt(numbers[0]);
        int n1, n2, w;
        while (s.hasNext()){

            line = s.next().replace("\r"," ").replace("\t"," ").replace("  ", " ");
            numbers = line.split(" ");

            n1 = Integer.valueOf(numbers[0]);
            n2 = Integer.valueOf(numbers[1]);
            w = Integer.valueOf(numbers[2]);
            if(!graph.containsKey(n1)) {
                graph.put(n1, new Hashtable<Integer, Integer>());
            }
            if(!graph.containsKey(n2)) {
                graph.put(n2, new Hashtable<Integer, Integer>());
            }
            graph.get(n1).put(n2, w);

        }

        s.close();
//        Helpers.getDiffTime(start, "Graphs creation time is: ");
    }


    private double[][] distTo;        // distTo[v][w] = length of    shortest v->w path
    private DirectedEdge[][] edgeTo;  // edgeTo[v][w] = last edge on shortest v->w path

    public FloydWarshall(AdjMatrixEdgeWeightedDigraph G) {
        int V = G.V();
        distTo = new double[V][V];
        edgeTo = new DirectedEdge[V][V];

        // initialize distances to infinity
        for (int v = 0; v < V; v++) {
            for (int w = 0; w < V; w++) {
                distTo[v][w] = Double.POSITIVE_INFINITY;
            }
        }

        // initialize distances using edge-weighted digraph's
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                distTo[e.from()][e.to()] = e.weight();
                edgeTo[e.from()][e.to()] = e;
            }
            // in case of self-loops
            if (distTo[v][v] >= 0.0) {
                distTo[v][v] = 0.0;
                edgeTo[v][v] = null;
            }
        }

        // Floyd-Warshall updates
        for (int i = 0; i < V; i++) {
            // compute shortest paths using only 0, 1, ..., i as intermediate vertices
            for (int v = 0; v < V; v++) {
                if (edgeTo[v][i] == null) continue;    // optimization
                for (int w = 0; w < V; w++) {
                    if (distTo[v][w] > distTo[v][i] + distTo[i][w]) {
                        distTo[v][w] = distTo[v][i] + distTo[i][w];
                        edgeTo[v][w] = edgeTo[i][w];
                    }
                }
                if (distTo[v][v] < 0.0) return;  // negative cycle
            }
        }
    }

    // is there a negative cycle?
    public boolean hasNegativeCycle() {
        for (int v = 0; v < distTo.length; v++)
            if (distTo[v][v] < 0.0) return true;
        return false;
    }

    // negative cycle
    public Iterable<DirectedEdge> negativeCycle() {
        for (int v = 0; v < distTo.length; v++) {
            // negative cycle in v's predecessor graph
            if (distTo[v][v] < 0.0) {
                int V = edgeTo.length;
                EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
                for (int w = 0; w < V; w++)
                    if (edgeTo[v][w] != null)
                        spt.addEdge(edgeTo[v][w]);
                EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
                assert finder.hasCycle();
                return finder.cycle();
            }
        }
        return null;
    }

    // is there a path from v to w?
    public boolean hasPath(int v, int w) {
        return distTo[v][w] < Double.POSITIVE_INFINITY;
    }


    // return length of shortest path from v to w
    public double dist(int v, int w) {
        return distTo[v][w];
    }

    // return view of shortest path from v to w, null if no such path
    public Iterable<DirectedEdge> path(int v, int w) {
        if (!hasPath(v, w) || hasNegativeCycle()) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v][w]; e != null; e = edgeTo[v][e.from()]) {
            path.push(e);
        }
        return path;
    }

    // check optimality conditions
    private boolean check(EdgeWeightedDigraph G, int s) {

        // no negative cycle
        if (!hasNegativeCycle()) {
            for (int v = 0; v < G.V(); v++) {
                for (DirectedEdge e : G.adj(v)) {
                    int w = e.to();
                    for (int i = 0; i < G.V(); i++) {
                        if (distTo[i][w] > distTo[i][v] + e.weight()) {
                            System.err.println("edge " + e + " is eligible");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
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
