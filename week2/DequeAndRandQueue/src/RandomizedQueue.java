import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

class RandomQueueIterator<Item> implements Iterator<Item> {

    ListNode<Item>currentNode;

    public RandomQueueIterator(ListNode<Item> root){
        this.currentNode = root;
    }


    @Override
    public boolean hasNext() {
        return this.currentNode.getNextNode()!=null;
    }

    @Override
    public Item next() {
        if(!this.hasNext()) throw new NoSuchElementException();

        Item value = this.currentNode.getValue();
        currentNode = currentNode.getNextNode();
        return value;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}


public class RandomizedQueue<Item> implements Iterable<Item> {

    private ListNode<Item> root;
    private Random random;
    private int size;
    // construct an empty randomized queue
    public RandomizedQueue(){
        root = null;
        size = 0;
        random = new Random();
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
        }
        else{
            ListNode<Item> currentNode = root;
            while(currentNode.getNextNode()!=null){
                currentNode = currentNode.getNextNode();
            }
            currentNode.setNextNode(new ListNode<Item>(item,null));
        }
        this.size++;
    }

    // remove and return a random item
    public Item dequeue(){
        int index = 0;
        if(this.size!=1){
            index = random.nextInt(this.size - 1);
        }
        int currentIndex = 0;
        if(this.isEmpty()) throw new NoSuchElementException();

        if(index==0){
            Item value = this.root.getValue();
            this.root = this.root.getNextNode();
            this.size=this.size-1;
            return value;
        }

        ListNode<Item>currentNode = this.root;
        while(currentIndex!=index-1){
            currentIndex++;
            currentNode = currentNode.getNextNode();
        }
        Item value = currentNode.getNextNode().getValue();
        currentNode.setNextNode(currentNode.getNextNode().getNextNode());
        this.size=this.size-1;
        System.out.println(this.size);
        return value;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        int index = random.nextInt(this.size - 1);
        int currentIndex = 0;
        if(this.isEmpty()) throw new NoSuchElementException();

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
        return new RandomQueueIterator<>(this.root);
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer>  deq = new RandomizedQueue<>();
        deq.isEmpty();
        deq.size();
        deq.enqueue(123);
        deq.enqueue(12333);
        deq.enqueue(1232);
        System.out.println(deq.dequeue());
        System.out.println(deq.sample());
        System.out.println(deq.sample());
        System.out.println(deq.dequeue());
        System.out.println(deq.dequeue());
        System.out.println(deq.isEmpty());
        System.out.println(deq.iterator());
    }

}