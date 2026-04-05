// buatlah method removeEnd() untuk menghapus node pertama dan node terakhir (perhatikan beberapa case)

class Node {
    int item;
    Node next;

    public Node(int item){
        this.item = item;
        this.next = null;
    }
}



public class SinglyLinkedList {

    Node head;
    Node tail;

    public void insert(int data){
        Node newNode = new Node(data);

        if(head == null){
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
    }

    public void removeEnd() {
        // kalo data kosong
        if(head == null) return;

        
        //kalo head = tail atau hanya ada 2 elemen di linked list (ex. 13 15)
        if(head == tail || head.next == tail){
            head = tail = null;
            return;
        } 
        head = head.next;
        Node current = head;
        while(current.next != tail) {
            current = current.next;
        }

        tail = current;
        tail.next = null;
    }

    public void display(){
        Node current = head;
        while(current != null){
            System.out.print(current.item + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
       SinglyLinkedList list = new SinglyLinkedList();

        list.insert(10);
        list.insert(5);
        list.insert(20);
        list.insert(30);

        list.display(); // 30 -> 20 -> 5 -> 10 -> null

        list.removeEnd();
        list.display(); // 5 -> 20 -> 30 -> null
    }
}
