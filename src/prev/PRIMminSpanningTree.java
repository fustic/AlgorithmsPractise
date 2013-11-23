package prev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class PRIMminSpanningTree {
    public static void main(String[] args) throws IOException {
        PRIMminSpanningTree priMminSpanningTree = new PRIMminSpanningTree("/Users/vadimivanov/Downloads/g3.txt");
        Date start = new Date();
        System.out.println("Sum = "+priMminSpanningTree.calculateCostMinSpanningTree());
        //-1619
        //-1600
        //-1567
        System.exit(0);
    }

    public PRIMminSpanningTree(String filePath) throws FileNotFoundException {
        graph = developGraph(filePath);
    }
    public long calculateCostMinSpanningTree(){
        long sum = 0;
        HashSet<Integer> exploredVerticies = new HashSet<Integer>();
        List<Integer> verticies = new ArrayList<Integer>(graph.keySet());
        List<Edge> edges = new ArrayList<Edge>();
        List<Edge> edgesToExplore = new ArrayList<Edge>();

        for(int v : verticies){
            for(Edge e : graph.get(v))
                edgesToExplore.add(e);
        }

        Collections.sort(edgesToExplore, new Comparator<Edge>() {
            @Override
            public int compare(Edge edge, Edge edge2) {
                return edge.weight - edge2.weight;
            }
        });
        Edge edge;
        while (exploredVerticies.size() != verticies.size()) {
            edge = null;
            if(exploredVerticies.size() == 0){
                edge = edgesToExplore.get(0);
                edgesToExplore.remove(edge);
                exploredVerticies.add(edge.vertexV);
            }else {
                for (Edge e : edgesToExplore){
                    if(exploredVerticies.contains(e.vertexU) && !exploredVerticies.contains(e.vertexV)){
                        edge = e;
                        break;
                    }
                }
                if(edge!=null){
                    edgesToExplore.remove(edge);
                    exploredVerticies.add(edge.vertexV);
                    edges.add(edge);
//                    System.out.println("edge u to v:" + edge.vertexU + ", "+edge.vertexV);
                }
            }

        }

        for(Edge e : edges){
            sum += e.weight;
        }

        return sum;
    }

    private Hashtable<Integer, HashSet<Edge>> graph;
    private Hashtable<Integer, HashSet<Edge>> developGraph(String filePath) throws FileNotFoundException {
        Date start = new Date();
        Scanner s = new Scanner(new File(filePath)).useDelimiter(System.getProperty("line.separator"));
        Hashtable<Integer, HashSet<Edge>> graph = new Hashtable<Integer, HashSet<Edge>>();
        HashSet<Edge> edges;
        Edge edge;
        String[] numbers;
        String[] EW;
        String line;
        int vertex, toVertex, weight;
        s.next();
        while (s.hasNext()){

            line=s.next().replace("\r"," ").replace("\t"," ").replace("  ", " ");
            numbers = line.split(" ");
            vertex = Integer.valueOf(numbers[0]);
            toVertex = Integer.valueOf(numbers[1]);
            weight = Integer.valueOf(numbers[2]);
            if(!graph.containsKey(vertex)){
                graph.put(vertex, new HashSet<Edge>());
            }
            if(!graph.containsKey(toVertex)){
                graph.put(toVertex, new HashSet<Edge>());
            }
            edge = new Edge(toVertex, weight, vertex);
            graph.get(vertex).add(edge);
            edge = new Edge(vertex, weight, toVertex);
            graph.get(toVertex).add(edge);
        }
        s.close();
        Helpers.getDiffTime(start, "Graphs creation time is: ");
        return graph;
    }





    private class Edge {
        public Edge(int v, int w, int vU){
            vertexV = v;
            vertexU = vU;
            weight = w;
        }
        public int vertexV;
        public int vertexU;
        public int weight;
    }
}
