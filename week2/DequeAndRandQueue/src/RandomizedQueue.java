import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;


class RandomQueueIterator<Item> implements Iterator<Item> {

    RandomizedQueue<Item> queue;

    public RandomQueueIterator(RandomizedQueue<Item> queue){
        this.queue = queue;
    }


    @Override
    public boolean hasNext() {
        return !this.queue.isEmpty();
    }

    @Override
    public Item next() {
        if(!this.hasNext()) throw new NoSuchElementException();

        return this.queue.dequeue();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}


public class RandomizedQueue<Item> implements Iterable<Item> {

    private ListNode<Item> root;
    private ListNode<Item> lastItem;
    private int size;
    // construct an empty randomized queue
    public RandomizedQueue(){
        root = null;
        lastItem = null;
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return size==0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return size;
    }

    // add the item
    public void enqueue(Item item){
        if(item==null) throw new IllegalArgumentException();

        if(this.isEmpty()){
            this.root = new ListNode<>(item,null);
            this.lastItem = root;
        }
        else{
            lastItem.setNextNode(new ListNode<Item>(item,null));
            lastItem = lastItem.getNextNode();
        }
        this.size++;
    }

    // remove and return a random item
    public Item dequeue(){
        if(this.isEmpty()) throw new NoSuchElementException();


        int index = 0;
        if(this.size!=1){
            index = StdRandom.uniform(0, this.size);
        }
        else{
            this.lastItem = null;
        }
        int currentIndex = 0;
        Item value = null;
        if(index==0){
            value = this.root.getValue();
            this.root = this.root.getNextNode();
        }
        else{
            ListNode<Item>currentNode = this.root;
            while(currentIndex!=index-1){
                currentIndex++;
                currentNode = currentNode.getNextNode();
            }
            if(index==this.size-1){
                this.lastItem = currentNode;
            }
            value = currentNode.getNextNode().getValue();
            currentNode.setNextNode(currentNode.getNextNode().getNextNode());
        }
        this.size = this.size - 1;
        return value;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(this.isEmpty()) throw new NoSuchElementException();

        int index = StdRandom.uniform(0, this.size);
        int currentIndex = 0;

        if(index==0){
            Item value = this.root.getValue();
            return value;
        }

        ListNode<Item>currentNode = this.root;
        while(currentIndex!=index-1){
            currentIndex++;
            currentNode = currentNode.getNextNode();
        }
        Item value = currentNode.getNextNode().getValue();
        return value;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomQueueIterator<>(this);
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer>  rq = new RandomizedQueue<>();
        rq.enqueue(418);
        rq.iterator();
        rq.enqueue(254);
        rq.enqueue(81);
        rq.enqueue(262);
        Iterator<Integer> a = rq.iterator();
        while(a.hasNext()){
            System.out.println(a.next());
        }
    }

}