import java.util.Arrays;

class IntList{
    private int[] array;
    private int count;
    
    IntList() {
        array = new int[1];
        count = 0;
    }
    
    IntList(int number) {
        array = new int[]{number};
        count = 1;
    } 

    public void add(int number) {
        if (count == array.length) {
            array = Arrays.copyOf(array, 2 * array.length);
        }
        array[count] = number;
        count++;
    }
    
    public int[] get() {
        return Arrays.copyOfRange(array, 0, count);
    }

    public int get(int index) {
        return array[index];
    }

    public void replace(int index, int number){
        array[index] = number;
    }
    
    public int size(){
        return count;
    }
}

