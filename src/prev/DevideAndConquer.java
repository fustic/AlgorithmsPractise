package prev;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DevideAndConquer {
    public static void main(String[] args) throws IOException {
        JFileChooser file = new JFileChooser("/Users/vadimivanov/Downloads/integerTest.txt");
        file.showDialog(null, "Choose");
        Scanner s = new Scanner(new File(file.getSelectedFile().getAbsolutePath())).useDelimiter(System.getProperty("line.separator"));
        ArrayList<Integer> list = new ArrayList<Integer>();
        String number;
        while (s.hasNext()){
            number=s.next().replace("\r","");
            list.add(Integer.valueOf(number));
        }
        s.close();

        SortedArray ss = SortAndCountSplitInv(list);
        System.out.println("total is: "+ ss.count);
    }

    public static SortedArray MergeAndCountSplitInv(List<Integer> lArray, List<Integer> rArray){
        long sum = 0;
        List<Integer> array = new ArrayList<Integer>();
        int i=0, j=0, n = lArray.size() + rArray.size(), a, b, nOfL = lArray.size(), nOfR = rArray.size(), count, s=0;

        for(int k=0; k<n; k++){
            if(i<nOfL)
                a = lArray.get(i);
            else{
                array.add(k, rArray.get(j));
                j++;
                continue;
            }
            if(j<nOfR)
                b = rArray.get(j);
            else{
                array.add(k, lArray.get(i));
                i++;
                continue;
            }
            if(a < b){
                array.add(k, a);
                i++;
            }else{
                array.add(k, b);
                count = nOfL - i;
                sum += count > 0 ? count : 0;
                j++;
            }
        }
        return new SortedArray(sum, array);
    }
    public static SortedArray SortAndCountSplitInv(List<Integer> array){
        long sum = 0;
        int n = array.size();
        List<Integer> sArray = array;
        if(n>1){
            List<Integer> left = array.subList(0, n/2);
            List<Integer> right = array.subList(n/2, n);
            SortedArray l = SortAndCountSplitInv(left);
            SortedArray r = SortAndCountSplitInv(right);
            SortedArray s = MergeAndCountSplitInv(l.array, r.array);
            sArray = s.array;
            sum = l.count + r.count + s.count;
        }

        return new SortedArray(sum, sArray);
    }
    public static class SortedArray{
        public SortedArray(long sum, List<Integer> a){
            array = a;
            count = sum;
        }
        public List<Integer> array;
        public long count;
    }
}
