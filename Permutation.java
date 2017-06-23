import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> stack = new RandomizedQueue<String>();
        int k = StdIn.readInt();
        int s = 0;
        while (!StdIn.isEmpty()) {
            stack.enqueue(StdIn.readString());
            
        }
        while (s<k) {
            for (String i:stack) {        
                StdOut.println(i);
                s++;
            }
        }
    }
    
}