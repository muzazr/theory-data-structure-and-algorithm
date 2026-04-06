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

    // Method bantuan untuk mengecek apakah suatu angka adalah bilangan prima
private boolean isPrime(int n) {
    if (n <= 1) return false;
    for (int i = 2; i <= Math.sqrt(n); i++) {
        if (n % i == 0) return false;
    }
    return true;
}

public void removePrime() {
    if (head == null) {
        return;
    }

    // Geser head terus menerus sampai menemukan bilangan BUKAN prima atau list habis
    while (head != null && isPrime(head.item)) {
        head = head.next;
    }

    // Jika setelah head digeser ternyata list menjadi kosong (semua isinya prima)
    if (head == null) {
        tail = null;
        return;
    }

    Node current = head;
    while (current.next != null) {
        if (isPrime(current.next.item)) {
            current.next = current.next.next;
            // Jangan geser 'current' di sini (current = current.next), 
            // karena node yang baru saja menjadi 'current.next' BISA JADI bilangan prima juga.
        } else {
            // Jika BUKAN prima, baru kita aman untuk bergeser ke node selanjutnya
            current = current.next;
        }
    }
    tail = current;
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
