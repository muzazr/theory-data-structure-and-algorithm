import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.Scanner;

class Karyawan {
    private String nama;
    private String nip;
    private int masaKerja;
    private int gaji;

    public Karyawan(String nama, String nip, int masaKerja, int gaji) {
        this.nama = nama;
        this.nip = nip;
        this.masaKerja = masaKerja;
        this.gaji = gaji;
    }

    public String getNama(){
        return nama;
    }

    public String getNip() {
        return nip;
    }

    public int getMasaKerja() {
        return masaKerja;
    }

    public int getGaji() {
        return gaji;
    }

    public void setGaji(int gaji){
        this.gaji = gaji;
    }

    public void setMasaKerja(int masaKerja) {
        this.masaKerja = masaKerja;
    }

    public void display () {
        System.out.printf("%-10s %-10s %-10d %-10d\n", nama, nip, masaKerja, gaji);
    }

}

class ArrayKaryawan {
    private Karyawan[] data;
    private int jumlah;

    public ArrayKaryawan(int size) {
        data = new Karyawan[size];
        jumlah = 0;
    }

    public int binarySearch(String nip) {
        int low = 0;
        int high = jumlah - 1;

        while(low <= high) {
            int mid = (low + high) / 2;
            int compare = data[mid].getNip().compareTo(nip);

            if(compare == 0) return mid;
            else if(compare < 0) low = mid + 1;
            else high = mid - 1;
        }
        
        return -1;
    }

    public void insertKaryawan(Karyawan tambahKaryawan) {
        
        // is nip exist?
        if(binarySearch(tambahKaryawan.getNip()) != -1){
            System.out.println("Karyawan tersebut sudah ada");
            return;
        }

        int low = 0;
        int high = jumlah -1;
        while(low <= high) {
            int mid = (low + high) / 2;
            if(data[mid].getNip().compareTo(tambahKaryawan.getNip()) < 0){
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        int index = low;
        for(int i = jumlah - 1; i >= index; i--){
            data[i+1] = data[i];
        }

        data[index] = tambahKaryawan;
        jumlah++;

    }

    public void deleteKaryawan(String nip) {
        int index = binarySearch(nip);

        if(index == -1){
            System.out.println("error, data tidak ditemukan!");
            return;
        }

        for(int i = index; i < jumlah - 1; i++){
            data[i] = data[i+1];
        }

        jumlah--;
    }

    public Karyawan find(String nip){
        int index = binarySearch(nip);

        if(index != -1) {
            return data[index];
        }

        return null;
    }

    public void updateGaji(String nip, int gajiBaru){
        int index = binarySearch(nip);

        if(index != -1){
            data[index].setGaji(gajiBaru);
            System.out.println("Gaji dengan NIP " + nip +  " berhasil diupdate");
            return;
        }

        System.out.println("Data dengan NIP " + nip + " tidak ditemukan");
    }

    public void updateMasaKerja(String nip, int masaKerjaBaru){
        int index = binarySearch(nip);

        if(index != -1) {
            data[index].setMasaKerja(masaKerjaBaru);
            System.out.println("Masa kerja dengan NIP " + nip +  " berhasil diupdate");
            return;
        }

        System.out.println("Data dengan NIP " + nip + " tidak ditemukan");
    }

    public int getTotalGaji() {
        int totalGaji = 0;

        for(int i = 0; i < jumlah; i++){
            totalGaji += data[i].getGaji();
        }

        return totalGaji;
    }

    public void display() {

        System.out.println("Nama        NIP     MasaKerja       Gaji");

        for(int i = 0; i < jumlah; i++){
            data[i].display();
        }
    }

    public void saveToFile(String filename) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

            for(int i = 0; i < jumlah; i++) {

                Karyawan karyawan = data[i];

                bw.write(
                    karyawan.getNama() + "," +
                    karyawan.getNip() + "," +
                    karyawan.getMasaKerja() + "," +
                    karyawan.getGaji()
                );

                bw.newLine();
            }

            bw.close();

        } catch(Exception e) {
            System.out.println("Error menyimpan file");
        }
    }
}



public class ManajemenKaryawanArray {


    ArrayKaryawan data = new ArrayKaryawan(100);

    public static void main(String[] args) {
        
        ArrayKaryawan data = new ArrayKaryawan(100);
        Scanner input = new Scanner(System.in);

        try {
            BufferedReader br = new BufferedReader(new FileReader("karyawan.txt"));
            String line;

            while((line = br.readLine()) != null) {
                String[] bagian = line.split(",");

                String nama = bagian[0];
                String nip = bagian[1];
                int masaKerja = Integer.parseInt(bagian[2]);
                int gaji = Integer.parseInt(bagian[3]);

                Karyawan karyawan = new Karyawan(nama, nip, masaKerja, gaji);
                data.insertKaryawan(karyawan);
            }
            
            br.close();
        } catch (Exception err){
            System.out.println("Error, gagal membaca file");
        }

        while(true) {

            System.out.println("\n=== MENU MANAJEMEN KARYAWAN ===");
            System.out.println("1. Tampilkan semua karyawan");
            System.out.println("2. Tambah karyawan");
            System.out.println("3. Hapus karyawan");
            System.out.println("4. Cari karyawan");
            System.out.println("5. Update gaji");
            System.out.println("6. Update masa kerja");
            System.out.println("7. Total gaji");
            System.out.println("8. Keluar");

            System.out.println("Pilih menu: ");
            int pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    data.display();
                    break;

                case 2:
                    System.out.print("Nama: ");
                    String nama = input.nextLine();

                    System.out.print("NIP: ");
                    String nip = input.nextLine();

                    System.out.print("Masa Kerja: ");
                    int masaKerja = input.nextInt();

                    System.out.print("gaji: ");
                    int gaji = input.nextInt();

                    Karyawan karyawan = new Karyawan(nama, nip, masaKerja, gaji);
                    data.insertKaryawan(karyawan);
                    data.saveToFile("karyawan.txt");
                    break;

                case 3:
                    System.out.print("Masukkan NIP yang akan dihapus: ");
                    String deleteNip = input.nextLine();
                    data.deleteKaryawan(deleteNip);
                    data.saveToFile("karyawan.txt");
                    break;

                case 4:
                    System.out.print("Masukkan NIP yang dicari: ");
                    String searchNip = input.nextLine();

                    Karyawan hasil = data.find(searchNip);
                    if(hasil != null){
                        System.out.println("Nama        NIP     MasaKerja       Gaji");
                        hasil.display();
                    } else {
                        System.out.println("Karyawan tidak ditemukan");
                    }

                    break;
                
                case 5:
                    System.out.print("Masukkan NIP: ");
                    String updateNip = input.nextLine();

                    System.out.print("Masukkan gaji baru: ");
                    int updateGaji = input.nextInt();
                    data.updateGaji(updateNip, updateGaji);
                    data.saveToFile("karyawan.txt");
                    break;

                case 6:
                    System.out.print("Masukkan NIP: ");
                    String updateMasaKerjaNip = input.nextLine();
                    
                    System.out.print("Masukkan masa kerja baru: ");
                    int updateMasaKerja = input.nextInt();
                    data.updateMasaKerja(updateMasaKerjaNip, updateMasaKerja);
                    data.saveToFile("karyawan.txt");
                    break;

                case 7:
                    System.out.println("Total gaji: " + data.getTotalGaji());
                    break;

                case 8:
                    System.out.println("Program selesai");
                    input.close();
                    return;

                default:
                    System.out.println("Pilihan anda tidak valid!");
            }
        }
    }

}