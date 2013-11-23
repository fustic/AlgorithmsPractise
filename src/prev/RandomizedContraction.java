package prev;

import org.apache.commons.lang3.SerializationUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vadimivanov
 * Date: 7/22/13
 * Time: 10:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class RandomizedContraction {
    public static void main(String[] args) throws IOException {
//        JFileChooser file = new JFileChooser("/Users/vadimivanov/Downloads/kargerMinCut.txt");
        JFileChooser file = new JFileChooser("/Users/vadimivanov/Downloads/kargerMinCutTest.txt");
        file.showDialog(null, "Choose");
        Scanner s = new Scanner(new File(file.getSelectedFile().getAbsolutePath())).useDelimiter(System.getProperty("line.separator"));
        HashSet<List<Integer>> list = new HashSet<List<Integer>>();
        //HashSet<Integer, int[]> edges = new HashSet<Integer, int[]>();
        Hashtable<Integer, List<Integer>> edges = new Hashtable<Integer, List<Integer>>();


        String number;
        List<Integer> subList;
        while (s.hasNext()){
            number=s.next().replace("\r"," ").replace("\t"," ").replace("  "," ");
            subList = new ArrayList<Integer>();
            for (String _s: number.split(" ")){
                subList.add(Integer.valueOf(_s));
            }
            list.add(subList);
        }
        s.close();

        Integer v;
        for(List<Integer> _v : list){
            subList = new ArrayList<Integer>();
            v = _v.get(0);
            for(int j = 1; j<_v.size(); j++){
                if(_v.get(j)!=v)
                    subList.add(_v.get(j));

            }
            edges.put(v, subList);
        }
        int min=100000, cuts;
        for(int i=0; i<100; i++){

            cuts = countMinCuts(SerializationUtils.clone(edges));
            if(cuts<min)
                min=cuts;
        }
        System.out.println("min cuts: "+min);
    }

    public static int countMinCuts(Hashtable<Integer, List<Integer>> edges){
        while (edges.size() > 2){
//            System.out.println(edges);
            kargerStep(edges);
        }
//        System.out.println(edges);
//        System.out.println("Key: "+edges.keys().nextElement());
        return edges.get(edges.keys().nextElement()).size();
    }

    public static void kargerStep(Hashtable<Integer, List<Integer>> edges){
        Edge edge = chooseRandomEdge(edges);
        int v1 = edge.l, v2 = edge.r;
        //1. attach v2's list to v1
        edges.get(v1).addAll(edges.get(v2));
        //2. replace all appearance of v2 as v1
        for(Integer i : edges.get(v2)){
            for(Integer j : edges.get(i)){
                if(j == v2){
                    edges.get(i).set(edges.get(i).indexOf(j), v1);
                }
            }
        }
        //3.remove self-loop
        while(edges.get(v1).contains(v1)){
            edges.get(v1).remove(edges.get(v1).indexOf(v1));
        }
        //4. remove v2's list
        edges.remove(v2);
    }

    public static Edge chooseRandomEdge(Hashtable<Integer, List<Integer>> edges){
        List<Integer> vertices = new ArrayList<Integer>(edges.keySet());
        int     l = vertices.get(getRandomNumber(vertices.size()-1)),
                r = edges.get(l).get(getRandomNumber(edges.get(l).size()-1));
        return new Edge(l,r);
    }

    public static int getRandomNumber(int max){
        int min = 0;
        return min + (int)(Math.random() * ((max - min) + min));
    }

    public static class Edge{
        public Edge(int _l, int _r){
            l = _l;
            r = _r;
        }
        public int l;
        public int r;
    }
}
