package prev;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SCC {

    public static void main(String[] args) throws IOException {

//        JFileChooser file = new JFileChooser("/Users/vadimivanov/Downloads/SCCtest.txt");
//        file.showDialog(null, "Choose");
//        System.out.println(graph);

        //expected 3,2,2,2,1
//        prev.SCC scc = new prev.SCC("/Users/vadimivanov/Downloads/SCCtest.txt");
        SCC scc = new SCC("/Users/vadimivanov/Downloads/prev.SCC.txt");
        System.out.println(scc.getCountConnectedComponents());

        //3,3,2,0,0
//        scc = new prev.SCC("/Users/vadimivanov/Downloads/SCCtest2.txt");
//        System.out.println(scc.getCountConnectedComponents());

        //3,3,1,1,0
//        scc = new prev.SCC("/Users/vadimivanov/Downloads/SCCtest3.txt");
//        System.out.println(scc.getCountConnectedComponents());
        System.exit(0);
    }
    private Hashtable<Integer, List<Integer>> graph;
    private Hashtable<Integer, List<Integer>> reversedGraph;
    private HashSet<Integer> exploredVertices;
    private HashMap<Integer, Integer> finishingTimeOfV;
    private ArrayList<Integer> sccs = new ArrayList<Integer>();
    private ArrayList<Integer> scc = new ArrayList<Integer>();
    private int finishingTime = 0;
    public SCC(String filePath) throws FileNotFoundException {
        getGraphFromFile(filePath);
    }

    public String getCountConnectedComponents(){
        String result = "";
        exploredVertices = new HashSet<Integer>();
        finishingTimeOfV = new HashMap<Integer, Integer>();
        computeFinishingTime();
        reversedGraph = new Hashtable<Integer, List<Integer>>();
        exploredVertices = new HashSet<Integer>();
        ArrayList<Integer> sccs = countSCCs();
        Collections.sort(sccs);
        System.out.println(sccs);
        result = StringUtils.join(sccs,",");
        return result;
    }

    private ArrayList<Integer> countSCCs() {
        Date start = new Date();
        int vertice;
        int scc;
        for(int i = finishingTimeOfV.size(); i>0; i--){
            vertice = finishingTimeOfV.get(i);
            if(!exploredVertices.contains(vertice)){
                scc = dfsGraph(vertice);
                if(scc>0)
                    sccs.add(scc);
            }
        }
//
        Helpers.getDiffTime(start, "second dfs: ");
        return sccs;
    }

    private void computeFinishingTime(){
        Date start = new Date();
        List<Integer> reversedKeys = new ArrayList<Integer>(reversedGraph.keySet());
        int vertice;
        for(int i = 0; i<reversedKeys.size(); i++){
            vertice = reversedKeys.get(i);
            dfsRecGraph(vertice);
        }
        Helpers.getDiffTime(start, "first dfs: ");
    }

    private void dfsRecGraph(int vertice){
        if(!exploredVertices.contains(vertice)){// && reversedGraph.containsKey(vertice)){
            exploredVertices.add(vertice);
            List<Integer> vertices = reversedGraph.get(vertice);
            if(vertices!=null){
                for(int i=0; i<vertices.size();i++){
                    dfsRecGraph(vertices.get(i));
                }
                finishingTime++;
                finishingTimeOfV.put(finishingTime, vertice);
            }
        }

    }
    private int dfsGraph(int vertice){
        int count = 0;
        if(!exploredVertices.contains(vertice) && graph.containsKey(vertice)){
            exploredVertices.add(vertice);
            count = 1;
            List<Integer> vertices = graph.get(vertice);
            for(int i=0; i < vertices.size();i++){
                count +=dfsGraph(vertices.get(i));
            }
        }
        return count;
    }

    public Hashtable<Integer, List<Integer>> getGraph(){
        return graph;
    }
    private Hashtable<Integer, List<Integer>> getGraphFromFile(String filePath) throws FileNotFoundException {
        Date start = new Date();
        Scanner s = new Scanner(new File(filePath)).useDelimiter(System.getProperty("line.separator"));
        graph = new Hashtable<Integer, List<Integer>>();
        reversedGraph = new Hashtable<Integer, List<Integer>>();
        List<Integer> subList;
        String[] numbers;
        int v1, v2;
        while (s.hasNext()){
            numbers = s.next().split(" ");
            v1 = Integer.valueOf(numbers[0]);
            v2 = Integer.valueOf(numbers[1]);
            if(graph.containsKey(v1)){
                graph.get(v1).add(v2);
            }else{
                subList = new ArrayList<Integer>();
                subList.add(v2);
                graph.put(v1, subList);
            }
            if(reversedGraph.containsKey(v2)){
                reversedGraph.get(v2).add(v1);
            }else{
                subList = new ArrayList<Integer>();
                subList.add(v1);
                reversedGraph.put(v2, subList);
            }
        }

        s.close();
        Helpers.getDiffTime(start, "Graphs creation time is: ");
        return graph;
    }
}
