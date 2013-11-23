package facebook.hanoi.moves;

import java.io.IOException;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {


    }
    public Solution(int n, int k){

    }

    private class Peg {
        private int radius;
        private Disk currentDisk;
        private Disk finalDisk;
        public Peg(int r, Disk d){
            radius = r;
            finalDisk = d;
        }
        public void setCurrentDisk(Disk disk){
            currentDisk = disk;
        }
        public int getRadius(){
            return radius;
        }
    }

    private class Disk {
        private List<Peg> pegs;
        private int number;
        private int countPegs;
        public Disk(int n){
            pegs = new ArrayList<Peg>();
            number = n;
            countPegs = 0;
        }
        public void sortPegs(){
            Collections.sort(pegs, new Comparator<Peg>() {
                @Override
                public int compare(Peg peg, Peg peg2) {
                    return peg.getRadius() - peg2.getRadius();
                }
            });
        }
        public void addPeg(Peg peg){
            pegs.add(peg);
            countPegs++;
        }
        public Peg getTopPeg(){
            return pegs.get(--countPegs);
        }


    }
}
