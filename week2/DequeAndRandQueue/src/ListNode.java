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
