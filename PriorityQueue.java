class PesananMBG {
    private String npsn;
    private String namaSekolah;
    private int urgensi;
    private int jarak;
    private int waktuMasuk;

    public PesananMBG(String npsn, String namaSekolah, int urgensi, int jarak, int waktuMasuk){
        this.npsn = npsn;
        this.namaSekolah = namaSekolah;
        this.urgensi = urgensi;
        this.jarak = jarak;
        this.waktuMasuk = waktuMasuk;
    }

    public String getNpsn(){
        return npsn;
    }

    public String getNamaSekolah() {
        return namaSekolah;
    }

    public int getUrgensi() {
        return urgensi;
    }

    public int getJarak () {
        return jarak;
    }

    public int getWaktuMasuk() {
        return waktuMasuk;
    }

    public void setUrgensi(int urgensi) {
        this.urgensi = urgensi;
    }
}

class MaxHeapMBG {
    private PesananMBG[] heapArray;
    private int currentSize;
    private int maxSize;

    public MaxHeapMBG(int maxSize){
        this.maxSize = maxSize;
        this.heapArray = new PesananMBG[maxSize];
        this.currentSize = 0;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    private int parent(int index) { return (index - 1) / 2; }
    private int leftChild(int index) { return (2*index) + 1; }
    private int rightChild(int index) { return (2*index) + 2; }

    private void swap(int i, int j) {
        PesananMBG temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
    }

    private boolean isHigherPriority(PesananMBG a, PesananMBG b) {
        if (a.getUrgensi() > b.getUrgensi()) return true;
        if (a.getUrgensi() < b.getUrgensi()) return false;

        if (a.getJarak() < b.getJarak()) return true;
        if (a.getJarak() > b.getJarak()) return false;

        if (a.getWaktuMasuk() < b.getWaktuMasuk()) return true;

        return false;
    }

    public void insert(PesananMBG pesanan){
        if(currentSize >= maxSize) {
            System.out.println("Max heap penuh men!");
            return;
        }

        heapArray[currentSize] = pesanan;
        heapifyUp(currentSize);
        currentSize++;
    }

    private void heapifyUp(int index) {
        while(index > 0){
            if(isHigherPriority(heapArray[index], heapArray[parent(index)])){
                swap(index, parent(index));
                index = parent(index);
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        while(true) {
            int left = leftChild(index);
            int right = rightChild(index);
            int biggest = index;

            if(left < currentSize && isHigherPriority(heapArray[left], heapArray[biggest])) biggest = left; 
            if(right < currentSize && isHigherPriority(heapArray[right], heapArray[biggest])) biggest = right;
            if(biggest != index) {
                swap(biggest, index);
                index = biggest;
            } else break;
        }
    }

    public PesananMBG extractMax() {
        if(currentSize == 0) {
            return null;
        }

        PesananMBG max = heapArray[0];

        heapArray[0] = heapArray[currentSize - 1];
        heapArray[currentSize - 1] = null;
        currentSize--;
        
        if(currentSize != 0) heapifyDown(0); 
        
        System.out.println("Melakukan extractMax " + max.getNpsn() + "\n");
        return max;
    }

    public void updateUrgensi(String npsn, int newUrgensi){
        int index = -1;

        for(int i = 0; i < currentSize; i++){
            if(heapArray[i].getNpsn().equals(npsn)){
                index = i;
                break;
            }
        }

        if(index == -1) {
            System.out.println("Pesananan tidak ditemukan");
            return;
        }

        int oldUrgensi = heapArray[index].getUrgensi();
        heapArray[index].setUrgensi(newUrgensi);

        if(newUrgensi < oldUrgensi) {
            heapifyDown(index);
        } else if(newUrgensi > oldUrgensi) {
            heapifyUp(index);
        } 

        System.out.println("Melakukan update urgensi pada " + npsn + " dengan urgensi baru " + newUrgensi + "\n");
    }

    public void batalkanPesanan(String npsn) {
        int index = -1;

        for(int i = 0; i < currentSize; i++) {
            if(heapArray[i].getNpsn().equals(npsn)){
                index = i;
                break;
            }
        }

        if(index == -1){
            System.out.println("Pesanan tidak ditemukan");
            return;
        }

        swap(index, currentSize - 1);
        heapArray[currentSize - 1] = null;
        currentSize--;
        
        if(index < currentSize) {
            if(index > 0 && isHigherPriority(heapArray[index], heapArray[parent(index)])){
                heapifyUp(index);
            } else {
                heapifyDown(index);
            }
        }

        System.out.print("Melakukan pembatalan pesanan pada " + npsn + "\n");
    }

    public void printHeap() {
        System.out.println("=== ISI HEAP ===");
        for (int i = 0; i < currentSize; i++) {
            PesananMBG p = heapArray[i];
            System.out.println(
                "Index " + i +
                " | NPSN: " + p.getNpsn() +
                " | Sekolah: " + p.getNamaSekolah() +
                " | Urgensi: " + p.getUrgensi() +
                " | Jarak: " + p.getJarak() +
                " | Waktu: " + p.getWaktuMasuk()
            );
        }
        System.out.println("================\n");
    }
}

public class PriorityQueue {
    
    public static void main(String[] args) {
        MaxHeapMBG dapurBGN = new MaxHeapMBG(50);

        System.out.println("=== PENERIMAAN PESANAN MBG JOGJA ===");

        // misalkan Dapur sentral di area UGM Bulaksumur
        dapurBGN.insert(new PesananMBG("101", "SDN Percobaan 2 (Sekip)", 80, 1, 1));
        dapurBGN.insert(new PesananMBG("102", "SMPN 5 Yogyakarta (Kotabaru)", 90, 2, 2));
        dapurBGN.insert(new PesananMBG("103", "SDN Lempuyangwangi", 80, 3, 3));
        dapurBGN.insert(new PesananMBG("104", "SMAN 3 Yogyakarta", 80, 2, 4));
        dapurBGN.insert(new PesananMBG("105", "SDN Jetisharjo", 80, 1, 5));
        dapurBGN.printHeap();

        System.out.println("\n=== PENGIRIMAN KLOTER 1 ===");
        dapurBGN.extractMax();
        dapurBGN.extractMax();
        dapurBGN.printHeap();

        System.out.println("\n=== UPDATE KONDISI ===");
        dapurBGN.updateUrgensi("103", 95); //SDN Lempuyangwangi urgensi naik
        dapurBGN.updateUrgensi("104", 50); //SMAN 3 urgensi turun
        dapurBGN.batalkanPesanan("105"); // SDN Jetisharjo libur, pesanan dibatalkan
        dapurBGN.printHeap();

        System.out.println("\n=== PENGIRIMAN KLOTER 2 (SISA DAPUR) ===");
        while (dapurBGN.getCurrentSize() > 0) {
            dapurBGN.extractMax();
        }
        dapurBGN.printHeap();
    }
}
