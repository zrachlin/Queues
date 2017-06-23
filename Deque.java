import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int n;          // size of the stack
    private Node first;     // top of stack
    private Node last;      // bottom of stack

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }
    
    public Deque() {                          // construct an empty deque
        first = null;
        last = null;
        n = 0;
        assert check();
    }

    public boolean isEmpty() {                // is the deque empty?
        return (first == null || last == null);
    }
    
    public int size() {                       // return the number of items on the deque
        return n;
    }
    
    public void addFirst(Item item) {         // add the item to the front
        if (item == null) throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (size()==0) {
            last = first;
        }
        else {
            oldfirst.previous = first;
        }
        n++;
        assert check();
    }
    public void addLast(Item item) {          // add the item to the end
        if (item == null) throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        
        if (size()==0) {
            first = last;
        }
        else {
            oldlast.next = last;
            last.previous = oldlast;
        }
        n++;
        assert check();
    }
    public Item removeFirst() {               // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        if (size()==1) {
            last = first;
        }
        else {first.previous = null;}
        n--;
        assert check();
        return item;                   // return the saved item
    }
    
    public Item removeLast() {                // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = last.item;        // save item to return
        last = last.previous;
        if (size()==1) {
            first = last;
        }
        else {last.next = null;}
        n--;
        assert check();
        return item;                   // return the saved item
    }
    public Iterator<Item> iterator() {        // return an iterator over items in order from front to end
       return new ListIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
    private boolean check() {

        // check a few properties of instance variable 'first'
        if (n < 0) {
            return false;
        }
        if (n == 0) {
            if (first != null) return false;
            if (last != null) return false;
        }
        else if (n == 1) {
            if (first == null || last == null)      return false;
            if (first.next != null || last.previous != null) return false;
        }
        else {
            if (first == null || last == null)      return false;
            if (first.next == null || last.previous == null) return false;
        }

        // check internal consistency of instance variable n
        int numberOfNodes = 0;
        for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != n) return false;

        return true;
    }
    
    public static void main(String[] args) {  // unit testing (optional)
        Deque<String> z = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("f")) {
                for (String i : z) {
                    StdOut.print(i);
                }
            }
            if (item.equals("n")){StdOut.print(z.size()); continue;}
            if ("123456789".contains(item)) {
                z.addLast(item);
                continue;
            }
            
            if (!z.isEmpty()) {
                if (item.equals("+")) {
                    StdOut.print(z.removeFirst() + " ");
                    continue;
                }
                
                if (item.equals("-")) {
                    StdOut.print(z.removeLast() + " ");
                    continue;
                }
                
            }
            z.addFirst(item);
        }
        StdOut.println("(" + z.size() + " left on stack)");
    }
}