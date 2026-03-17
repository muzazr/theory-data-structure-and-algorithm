import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class ManajemenKaryawanDLL {

    public static void main(String[] args) {

        DoublyLinkedList list = new DoublyLinkedList();
        Scanner input = new Scanner(System.in);

        try {

            BufferedReader br = new BufferedReader(new FileReader("karyawan.txt"));
            String line;

            while((line = br.readLine()) != null){

                String[] data = line.split(",");

                String nama = data[0];
                String nip = data[1];
                int masaKerja = Integer.parseInt(data[2]);
                int gaji = Integer.parseInt(data[3]);

                Karyawan k = new Karyawan(nama, nip, masaKerja, gaji);

                list.insertSorted(k);
            }

            br.close();

        } catch(Exception e){
            System.out.println("Gagal membaca file");
        }

        int pilihan;

        do{

            System.out.println("\n=== MENU MANAJEMEN KARYAWAN ===");
            System.out.println("1. Display Data");
            System.out.println("2. Insert Karyawan");
            System.out.println("3. Delete Karyawan");
            System.out.println("4. Find Karyawan");
            System.out.println("5. Update Gaji");
            System.out.println("6. Update Masa Kerja");
            System.out.println("7. Total Gaji");
            System.out.println("0. Keluar");

            System.out.print("Pilihan: ");
            pilihan = input.nextInt();
            input.nextLine();

            switch(pilihan){

                case 1:

                    list.display();
                    break;

                case 2:

                    System.out.print("Nama: ");
                    String nama = input.nextLine();

                    System.out.print("NIP: ");
                    String nip = input.nextLine();

                    System.out.print("Masa Kerja: ");
                    int mk = input.nextInt();

                    System.out.print("Gaji: ");
                    int gaji = input.nextInt();
                    input.nextLine();

                    Karyawan k = new Karyawan(nama,nip,mk,gaji);
                    Node hasil = list.find(k.getNip());
                    if(hasil == null) {
                        list.insertSorted(k);
                        list.saveToFile("karyawan.txt");
                    } else {
                        System.out.println("Data dengan NIP tersebut sudah ada");
                    }

                    break;

                case 3:

                    System.out.print("Masukkan NIP yang ingin dihapus: ");
                    String nipHapus = input.nextLine();

                    list.delete(nipHapus);
                    list.saveToFile("karyawan.txt");
                    break;

                case 4:

                    System.out.print("Masukkan NIP yang dicari: ");
                    String nipCari = input.nextLine();

                    hasil = list.find(nipCari);

                    if(hasil != null){

                        System.out.println("\nData ditemukan:");
                        System.out.printf("%-15s %-10s %-10s %-10s\n",
                                "Nama","NIP","MasaKerja","Gaji");

                        hasil.data.display();
                    }else{
                        System.out.println("NIP tidak ditemukan");
                    }

                    break;

                case 5:

                    System.out.print("Masukkan NIP: ");
                    String nipGaji = input.nextLine();

                    hasil = list.find(nipGaji);

                    if(hasil != null) {
                        System.out.print("Gaji baru: ");
                        int gajiBaru = input.nextInt();
                        input.nextLine();

                        list.updateGaji(nipGaji, gajiBaru);
                        list.saveToFile("karyawan.txt");
                    } else {
                        System.out.println("NIP tidak ditemukan");
                    }

                    break;

                case 6:

                    System.out.print("Masukkan NIP: ");
                    String nipMK = input.nextLine();

                    hasil = list.find(nipMK);

                    if(hasil != null) {
                        System.out.print("Masa kerja baru: ");
                        int mkBaru = input.nextInt();
                        input.nextLine();
    
                        list.updateMasaKerja(nipMK, mkBaru);
                        list.saveToFile("karyawan.txt");
                    } else {
                        System.out.println("NIP tidak ditemukan");
                    }

                    break;

                case 7:
                    System.out.println("Total gaji semua karyawan: " + list.getTotalGaji());
                    break;

                case 0:

                    System.out.println("Program selesai");
                    break;

                default:

                    System.out.println("Pilihan tidak valid");

            }

        } while(pilihan != 0);

        input.close();
    }
}

class Karyawan {

    private String nama;
    private String nip;
    private int masaKerja;
    private int gaji;

    public Karyawan(String nama, String nip, int masaKerja, int gaji){
        this.nama = nama;
        this.nip = nip;
        this.masaKerja = masaKerja;
        this.gaji = gaji;
    }

    public String getNama(){
        return nama;
    }

    public String getNip(){
        return nip;
    }

    public int getMasaKerja(){
        return masaKerja;
    }

    public int getGaji(){
        return gaji;
    }

    public void setGaji(int gaji){
        this.gaji = gaji;
    }

    public void setMasaKerja(int masaKerja){
        this.masaKerja = masaKerja;
    }

    public void display(){
        System.out.printf("%-15s %-10s %-10d %-10d\n",
                nama, nip, masaKerja, gaji);
    }
}

class Node {

    Karyawan data;
    Node next;
    Node prev;

    public Node(Karyawan data){
        this.data = data;
        next = null;
        prev = null;
    }
}

class DoublyLinkedList {

    Node head;
    Node tail;

    public void insertSorted(Karyawan k){

        Node newNode = new Node(k);

        if(head == null){
            head = tail = newNode;
            return;
        }

        if(k.getNip().compareTo(head.data.getNip()) < 0){

            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            return;
        }

        if(k.getNip().compareTo(tail.data.getNip()) > 0){

            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            return;
        }


        Node current = head;

        while(current != null){

            if(k.getNip().compareTo(current.data.getNip()) < 0){

                Node prevNode = current.prev;

                prevNode.next = newNode;
                newNode.prev = prevNode;

                newNode.next = current;
                current.prev = newNode;

                return;
            }

            current = current.next;
        }
    }

    public Node find(String nip){

        Node current = head;

        while(current != null){

            if(current.data.getNip().equals(nip)){
                return current;
            }

            current = current.next;
        }

        return null;
    }

    public void delete(String nip){

        Node target = find(nip);

        if(target == null){
            System.out.println("Data tidak ditemukan");
            return;
        }

        if(target == head && target == tail){
            head = tail = null;
            return;
        }

        if(target == head){
            head = head.next;
            head.prev = null;
            return;
        }

        if(target == tail){
            tail = tail.prev;
            tail.next = null;
            return;
        }

        target.prev.next = target.next;
        target.next.prev = target.prev;
    }

    public void updateGaji(String nip, int gajiBaru){

        Node node = find(nip);

        if(node != null){
            node.data.setGaji(gajiBaru);
        }
    }

    public void updateMasaKerja(String nip, int mkBaru){

        Node node = find(nip);

        if(node != null){
            node.data.setMasaKerja(mkBaru);
        }
    }

    public int getTotalGaji(){

        int total = 0;

        Node current = head;

        while(current != null){

            total += current.data.getGaji();
            current = current.next;

        }

        return total;
    }

    public void display(){

        Node current = head;

        System.out.println("---------------------------------------------");
        System.out.printf("%-15s %-10s %-10s %-10s\n",
                "Nama","NIP","MasaKerja","Gaji");

        while(current != null){

            current.data.display();
            current = current.next;

        }

        System.out.println("---------------------------------------------");
    }

    public void saveToFile(String filename){

        try{

            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

            Node current = head;

            while(current != null){

                Karyawan k = current.data;

                bw.write(
                        k.getNama() + "," +
                        k.getNip() + "," +
                        k.getMasaKerja() + "," +
                        k.getGaji()
                );

                bw.newLine();

                current = current.next;
            }

            bw.close();

            System.out.println("Berhasil menyimpan file");

        }catch(Exception e){
            System.out.println("Gagal menyimpan file.");
        }
    }
}