package prev;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: vadimivanov
 * Date: 7/13/13
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuickSort {
    public static int total;
    public static void main(String[] args) throws IOException {
        JFileChooser file = new JFileChooser("/Users/vadimivanov/Downloads/QuickSortTest.txt");
        file.showDialog(null, "Choose");
        Scanner s = new Scanner(new File(file.getSelectedFile().getAbsolutePath())).useDelimiter(System.getProperty("line.separator"));
        ArrayList<Integer> list = new ArrayList<Integer>();
        String number;
        while (s.hasNext()){
            number=s.next().replace("\r","");
            list.add(Integer.valueOf(number));
        }
        ArrayList<Integer> copyList;
        s.close();
        total = 0;
        copyList = (ArrayList<Integer>) list.clone();
        QuickSort(copyList, 0, (copyList.size()-1));
        System.out.println("total is: "+total);
        total = 0;
        copyList = (ArrayList<Integer>) list.clone();
        QuickSortLast(copyList, 0, (copyList.size() - 1));
        System.out.println("total is: "+total);
        total = 0;
        copyList = (ArrayList<Integer>) list.clone();
        QuickSortMiddle(copyList, 0, (copyList.size() - 1));
        System.out.println("total is: "+total);
        //162085
        //160361
        //155191 161408
    }

    public static void QuickSort(List<Integer> array, int l, int r){
        if((r-l) < 1)
            return;
        int i = Partition(array, l, r);
        QuickSort(array, l, i-1);
        QuickSort(array, i+1, r);
    }
    public static void QuickSortLast(List<Integer> array, int l, int r){
        if((r-l) < 1)
            return;
        int i = PartitionLast(array, l, r);
        QuickSort(array, l, i-1);
        QuickSort(array, i+1, r);
    }
    public static void QuickSortMiddle(List<Integer> array, int l, int r){
        if((r-l) < 1)
            return;
        int i = PartitionMiddle(array, l, r);
        QuickSort(array, l, i-1);
        QuickSort(array, i+1, r);
    }
    public static int Partition(List<Integer> array, int l, int r){
        int p = array.get(l);
        int i = l+1;
        int n, temp;
        total += r-l;
        for(int j = l+1; j<=r; j++){
            n = array.get(j);
            if(n<p){
                temp = array.get(j);
                array.set(j, array.get(i));
                array.set(i, temp);
                i++;
            }
        }
        i = i-1;
        array.set(l, array.get(i));
        array.set((i), p);
        return i;
    }
    public static int PartitionMiddle(List<Integer> array, int l, int r){
        int index = (l+r)/2+l;
        int p = array.get(index);
        int i = l;
        int j = r;
        int n, temp;
        total+=r-l+1;
        do{
            while (array.get(i)<p) ++i;
            while (array.get(j)>p) --j;
            if(i<=j){
                temp = array.get(i);
                array.set(i, array.get(j));
                array.set(j, temp);
                i++;
                j--;
            }
        }while (i<j);


//        for(int j = l; j<=r; j++){
//            if(j==index)
//                continue;
//            n = array.get(j);
//            if(n<p){
//                temp = array.get(j);
//                array.set(j, array.get(i));
//                array.set(i, temp);
//                i++;
//            }
//        }
//        i = i - 1;
//        array.set(index, array.get(i));
//        array.set(i, p);

        return i;
    }
    public static int PartitionLast(List<Integer> array, int l, int r){
        int n, temp;
        int p = array.get(r);
        temp = array.get(l);
        array.set(l, p);
        array.set(r, temp);
        p = array.get(l);
        int i = l+1;

        total += r-l;
        for(int j = l+1; j<=r; j++){
            n = array.get(j);
//            total++;
            if(n<p){
                temp = array.get(j);
                array.set(j, array.get(i));
                array.set(i, temp);
                i++;
            }
        }
        i = i-1;
        array.set(l, array.get(i));
        array.set((i), p);
        /*int p = array.get(r);
        int i = l;
        int n, temp;
        total += r-l;
        for(int j = l; j<r; j++){
            n = array.get(j);
            if(n<p){
                temp = array.get(j);
                array.set(j, array.get(i));
                array.set(i, temp);
                i++;
            }
        }
        array.set(r, array.get(i));
        array.set((i), p);           */
        return i;
    }
}
