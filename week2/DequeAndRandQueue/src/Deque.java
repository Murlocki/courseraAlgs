import java.util.Iterator;
import java.util.NoSuchElementException;

class ListNode<Item>{
    private Item value;
    private ListNode<Item> nextNode;
    public ListNode(Item value,ListNode<Item> nextNode){
        this.value = value;
        this.nextNode = nextNode;
    }
    public ListNode(Item value){
        this.value = value;
        this.nextNode = null;
    }

    public Item getValue() {
        return value;
    }

    public ListNode<Item> getNextNode() {
        return nextNode;
    }

    public void setNextNode(ListNode<Item> nextNode) {
        this.nextNode = nextNode;
    }

    public void setValue(Item value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}

class DequeIterator<Item> implements Iterator<Item>{

    ListNode<Item>currentNode;

    public DequeIterator(ListNode<Item> root){
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

public class Deque<Item> implements Iterable<Item> {
    private ListNode<Item> root;
    private int size;
    public Deque(){
        root = null;
        size = 0;
    }
    // is the deque empty?
    public boolean isEmpty(){
        return this.size==0;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if(item==null) throw new IllegalArgumentException();

        if(this.isEmpty()){
            root = new ListNode<Item>(item);
        }
        else{
            this.root = new ListNode<>(item,root);
        }
        this.size++;
    }

    // add the item to the back
    public void addLast(Item item){
        if(item==null) throw new IllegalArgumentException();

        if(this.isEmpty()){
            root = new ListNode<Item>(item);
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

    // remove and return the item from the front
    public Item removeFirst(){
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }
        ListNode<Item> firstElem = root;
        root = root.getNextNode();
        this.size--;
        return firstElem.getValue();
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(this.isEmpty()){
            throw new NoSuchElementException();
        }

        if(this.size==1){
            ListNode<Item> firstElem = root;
            this.root = null;
            this.size--;
            return firstElem.getValue();
        }

        ListNode<Item> currentNode = root;
        while(currentNode.getNextNode().getNextNode() != null){
            currentNode = currentNode.getNextNode();
        }
        ListNode<Item> firstElem = currentNode.getNextNode();
        currentNode.setNextNode(null);
        this.size--;
        return firstElem.getValue();
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new DequeIterator<>(root);
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer>  deq = new Deque<>();
        deq.isEmpty();
        deq.size();
        deq.addFirst(123);
        deq.addLast(12333);
        deq.addLast(1232);
        System.out.println(deq.removeFirst());
        System.out.println(deq.removeLast());
        System.out.println(deq.iterator());
    }
}
