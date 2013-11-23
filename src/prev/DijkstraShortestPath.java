package prev;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class DijkstraShortestPath {

    public static void main(String[] args) throws IOException {
//        prev.DijkstraShortestPath dijkstraShortestPath = new prev.DijkstraShortestPath("/Users/vadimivanov/Downloads/dijkstraDataTest.txt");
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath("/Users/vadimivanov/Downloads/dijkstraData.txt");
//                prev.DijkstraShortestPath dijkstraShortestPath = new prev.DijkstraShortestPath("/Users/vadimivanov/Downloads/dijkstraDataTest3.txt");
//        prev.DijkstraShortestPath dijkstraShortestPath = new prev.DijkstraShortestPath("/Users/vadimivanov/Downloads/dijkstraDataTest4.txt");
//          prev.DijkstraShortestPath dijkstraShortestPath = new prev.DijkstraShortestPath("/Users/vadimivanov/Downloads/dijkstraDataTest5.txt");
          ArrayList<Integer> toVertices = new ArrayList<Integer>();

//        toVertices.add(5);
//        toVertices.add(25);
//        toVertices.add(36);
//        toVertices.add(54);
//        toVertices.add(80);
//        toVertices.add(95);
//        toVertices.add(112);
//        toVertices.add(128);
//        toVertices.add(146);
//        toVertices.add(167);
//        toVertices.add(189);
//                          /*
        toVertices.add(7);
        toVertices.add(37);
        toVertices.add(59);
        toVertices.add(82);
        toVertices.add(99);
        toVertices.add(115);
        toVertices.add(133);
        toVertices.add(165);
        toVertices.add(188);
        toVertices.add(197);
//        */
        HashSet<String> results = new HashSet<String>();
        for(int i=0; i<1; i++){
            results.add(dijkstraShortestPath.getWeightPaths(1, toVertices));
        }
        for(String s : results){
            System.out.println(s);
        }

        System.exit(0);
    }


    private Hashtable<Integer, HashSet<Edge>> graph;
    private HashSet<Integer> exploredVertices;
    private Hashtable<Integer, Integer> graphWeight;


    public DijkstraShortestPath(String path) throws FileNotFoundException {
        graph = getGraphFromFile(path);
        exploredVertices = new HashSet<Integer>();
        graphWeight = new Hashtable<Integer, Integer>();
//        computePaths(graph);
    }

    public String getWeightPaths(int fromVert, ArrayList<Integer> toVertices){
        computePathFromVertice(graph, fromVert);
        String result="";
        ArrayList<Integer> weights = new ArrayList<Integer>();
        if(toVertices.size()>0){
            String r ="";
            int w;
            for(Integer v : toVertices){
                w = graphWeight.containsKey(v) ? graphWeight.get(v) : 1000000;
                weights.add(w);
                r += "v:"+v+", w:"+w+"; ";
            }
            System.out.println(r);
            result = StringUtils.join(weights, ",");
        }
        else{
            ArrayList<Integer> verts = new ArrayList<Integer>(graphWeight.keySet());
            int v, w;
            for(int i = 0; i<verts.size(); i++){
                v = verts.get(i);
                w = graphWeight.get(v);
                result += "v:"+v+", w:"+w+"; ";
            }

        }
        return result;
    }
    private void computePathFromVertice(Hashtable<Integer, HashSet<Edge>> graph, int vertice){
        Paths paths = new Paths();
        ArrayList<Integer> vs = new ArrayList<Integer>();
        vs.add(vertice);
        paths.addVertices(vs);
        while (paths.isContainVertices()){
            paths.addVertices(computeWeight(paths.getVertice()));
        }
    }
    private void computePaths(Hashtable<Integer, HashSet<Edge>> graph){
        ArrayList<Integer> vertices = new ArrayList<Integer>(graph.keySet());
        Paths paths = new Paths();
        ArrayList<Integer> vs = new ArrayList<Integer>();
        vs.add(vertices.get(vertices.size()-1));
        paths.addVertices(vs);
        while (paths.isContainVertices()){
            paths.addVertices(computeWeight(paths.getVertice()));
        }
    }
    private ArrayList<Integer> computeWeight(int vertice){
        ArrayList<Integer> verts = new ArrayList<Integer>();
        if(!exploredVertices.contains(vertice)){
            exploredVertices.add(vertice);
            int w = graphWeight.containsKey(vertice) ? graphWeight.get(vertice) : 0;
            int ew;
            for(Edge edge : graph.get(vertice)){
                ew = w + edge.weight;
                if(!graphWeight.containsKey(edge.edge)){
                    graphWeight.put(edge.edge, ew);
                }else{
                    if(graphWeight.get(edge.edge)>ew){
                        graphWeight.put(edge.edge, ew);
                    }
                }
                verts.add(edge.edge);
            }
        }
        return verts;
    }
    private Hashtable<Integer, HashSet<Edge>> getGraphFromFile(String path) throws FileNotFoundException {
        Date start = new Date();
        Scanner s = new Scanner(new File(path)).useDelimiter(System.getProperty("line.separator"));
        Hashtable<Integer, HashSet<Edge>> graph = new Hashtable<Integer, HashSet<Edge>>();
        HashSet<Edge> edges;
        Edge edge;
        String[] numbers;
        String[] EW;
        String line;
        int v;
        while (s.hasNext()){
            edges = new HashSet<Edge>();
            line=s.next().replace("\r"," ").replace("\t"," ").replace("  "," ");
            numbers = line.split(" ");
            v = Integer.valueOf(numbers[0]);
            for(int i = 1; i<numbers.length; i++){
                EW = numbers[i].split(",");
                edge = new Edge(Integer.valueOf(EW[0]), Integer.valueOf(EW[1]));
                edges.add(edge);
            }
            graph.put(v, edges);
        }
        s.close();
        Helpers.getDiffTime(start, "Graphs creation time is: ");
        return graph;
    }

    private class Paths{
        public Paths(){
            vertices = new ArrayList<Integer>();
        }
        private ArrayList<Integer> vertices;
        public boolean isContainVertices(){
            return vertices.size()>0;
        }
        public int getVertice(){
            int minW = 1000000, minE = 0;
            if(vertices.size()==0)
                return minE;
            ArrayList<Integer> vertsIndexesToRemove = new ArrayList<Integer>();
            for(Integer v : vertices){
                if(graphWeight.containsKey(v)){
                    if(!exploredVertices.contains(v))
                        if(minW > graphWeight.get(v)){
                            minE = v;
                            minW = graphWeight.get(v);
                        }
                    //else
                    //    vertsIndexesToRemove.add(vertices.indexOf(v));
                }
            }
//            for(Integer index : vertsIndexesToRemove){
//                if(index>0)
//                    vertices.remove(index);
//            }
//            if(vertices.size()==0)
//                return 0;
            minE = minE > 0 ? minE : vertices.get(0);
            int index = vertices.indexOf(minE);
            vertices.remove(index);
            return minE;
        }
        public void addVertices(List<Integer> verts){
            for(Integer v : verts){
                if(!exploredVertices.contains(v))
                    vertices.add(v);
                else if(vertices.indexOf(v)>0)
                    vertices.remove(vertices.indexOf(v));
            }
        }
        public void exploreVertice(int v){
            int index = vertices.indexOf(v);
            if(index>=0)
                vertices.remove(index);
        }
    }

    private class Edge{
        public int edge;
        public int weight;
        public Edge(int e, int w){
            weight = w;
            edge = e;
        }
    }
}
