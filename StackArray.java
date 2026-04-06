//membuat input IKAN MAKAN SAYA DAN
//output DAN SAYA MAKAN IKAN
public class StackArray {
    private int maxSize;
    private int top;
    private String[] stackArray;

    public StackArray(int size) {
        maxSize = size;
        top = -1;
        stackArray = new String[maxSize];
    }

    public boolean isEmpty() {
        if(top == -1) return true;
        return false;
    }

    public boolean isFull() {
        if(top == maxSize - 1) return true;
        return false;
    }

    public void push(String add){
        if(isFull()){}//penuh
        else{
            top++;
            stackArray[top] = add;
        }
    }

    public String pop() {
        if(isEmpty()) {return "takda";}//gabisa dah pokonya
        else{
            String temp = stackArray[top];
            top--;
            return temp; 
        }
    }
    
    public static void main(String[] args) {
        String kalimat = "SAYA MAKAN IKAN DAN";
        String[] split = kalimat.split(" ");
        StackArray a = new StackArray(split.length);

        
        for(int i = 0; i < split.length; i++) {
            a.push(split[i]);
        }

        for(int i = 0; i < split.length; i++){
            System.out.print(a.pop() + " ");
        }
    }
}