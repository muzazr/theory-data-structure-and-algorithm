public class StringSplit {

    public static String[] getKataDasar(String kalimat) {
        String[] words = kalimat.split(" ");

        String[] temp = new String[words.length];
        int count = 0;

        for (int i = 0; i < words.length; i++) {
            String w = words[i].toLowerCase();

            // hapus tanda baca
            w = w.replaceAll("[^a-z]", "");

            // === HAPUS AWALAN ===
            if (w.startsWith("me")) {
                w = w.substring(2);
            } else if (w.startsWith("di")) {
                w = w.substring(2);
            } else if (w.startsWith("ke")) {
                w = w.substring(2);
            } else if (w.startsWith("ber")) {
                w = w.substring(3);
            } else if (w.startsWith("ter")) {
                w = w.substring(3);
            }

            // === HAPUS AKHIRAN ===
            if (w.endsWith("kan")) {
                w = w.substring(0, w.length() - 3);
            } else if (w.endsWith("nya")) {
                w = w.substring(0, w.length() - 3);
            } else if (w.endsWith("an")) {
                w = w.substring(0, w.length() - 2);
            } else if (w.endsWith("i")) {
                w = w.substring(0, w.length() - 1);
            }

            if (!w.equals("")) {
                temp[count] = w;
                count++;
            }
        }

        // final array
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }

        // tampilkan N
        System.out.println("Jumlah kata (N): " + count);

        return result;
    }
    
    public static void main(String[] args) {
        String kalimat = "Hujan deras mengguyur kota telah menyebabkan banyak rumah warga terendam air, sehingga tim penyelamat mengevakuasi penduduk, Bantuan pemerintah mulai disalurkan ke pengungsian untuk memenuhi kebutuhan makannya";
        String[] hasil = getKataDasar(kalimat);
        for(int i = 0; i < hasil.length; i++) {
            System.out.print(hasil[i] + " | ");
        }
    }
}

