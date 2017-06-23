import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;
    
    public RandomizedQueue() {                // construct an empty randomized queue
        a = (Item[]) new Object[2];
        n = 0;
    }
       
   public boolean isEmpty() {                // is the queue empty?
       return n == 0;    
   }
                
   public int size() {                       // return the number of items on the queue
       return n;
   }
   /*
   public int lengths() {
       return a.length;
   }
   */
       
   private void resize(int capacity) {       //resize the underlying array holding the elements
       assert capacity >= n;
       Item[] temp = (Item[]) new Object[capacity];
       for (int i = 0; i < n; i++) {
           if (a[i] != null) {
               temp[i] = a[i];
           }
       }
       a = temp;
   }
   
   /**
    * Adds the item to the stack
    * @param item the item to add
    */
   public void enqueue(Item item) {           // add the item
       if (item == null) throw new NullPointerException();
       if (n == a.length) resize(2*a.length);
       a[n++] = item;
   }
           
   public Item dequeue() {                   // remove and return a random item
       if (isEmpty()) throw new NoSuchElementException("Stack underflow");
       int rand = StdRandom.uniform(n);
       Item item = a[rand];
       a[rand] = a[n-1];
       a[n-1] = null;
       n--;
       //shrink the array if necessary
       if (n>0 && n == a.length/4) resize(a.length/2);
       return item;
   }
           
   public Item sample() {                    // return (but do not remove) a random item
       if (isEmpty()) throw new NoSuchElementException("Stack underflow");
       int rand = StdRandom.uniform(n);
       return a[rand];
   }
           
   public Iterator<Item> iterator() {
        return new RandomOrderIterator();
    }
   
   private class RandomOrderIterator implements Iterator<Item> {
       private int[] shuffler;
       private int j;
       
       public RandomOrderIterator() {
           j = 0;
           shuffler = new int[n];
           for (int i = 0; i < n; i++) {
               shuffler[i] = i;
           }
           StdRandom.shuffle(shuffler);
       }
       
       public boolean hasNext() {
           return j < n;
       }
       
       public void remove() {
           throw new UnsupportedOperationException();
       }
       
       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           return a[shuffler[j++]];
       }
   }
           
   public static void main(String[] args) {  // unit testing (optional)
        RandomizedQueue<String> stack = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("f")) {
                for (String i:stack) {
                    StdOut.print(i);
                }
            }
            else if (!item.equals("-")) stack.enqueue(item);
            else if (!stack.isEmpty()) StdOut.print(stack.dequeue() + " ");
            StdOut.println("(" + stack.size() + " on stack)"); 
            //StdOut.println("Array Size: " + stack.lengths());
        }
          
   }
}