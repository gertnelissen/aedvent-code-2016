import java.util.Arrays;

public class AlexDragonsJava {
    public static void main(String[] args) {
        calculate(35651584, new boolean[]{true, false, false, false, true, true, true, false, false, true, true, true, true, false, false, false, false});
    }

    private static void calculate(int discLen, boolean[] input) {
        long t0 = System.nanoTime();
        boolean[] fill = data(input);
        while (fill.length < discLen) {
            fill = data(fill);
        }
        boolean[] sum = checksum(Arrays.copyOfRange(fill, 0, discLen));
        System.out.println(String.format("The checksum is %s",toString(sum)));
        long t1 = System.nanoTime();
        System.out.println((t1 - t0 + " ns"));
    }

    public static String toString(boolean[] sum) {
        StringBuilder result = new StringBuilder();
        for(boolean x : sum){
            if(x){
                result.append("1");
            }else{
                result.append("0");
            }
        }
        return result.toString();
    }

    public static boolean[] data(boolean[] a){
        boolean[] b = new boolean[a.length];
        for(int i = b.length/2; i >= 0 ; i--){
            int opp = b.length - 1 - i;
            b[i] = !a[opp];
            b[opp] = !a[i];
        }

        if (a.length%2 != 0) {
            b[a.length/2] = !a[a.length/2];
        }
        boolean[] c= new boolean[a.length+b.length+1];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length+1, b.length);
        c[a.length] = false;
        return c;
    }


    public static boolean[] checksum(boolean[] a){
        boolean[] sum = new boolean[a.length/2];
        for (int i = 0; i < a.length; i+=2){
            sum[i/2] = a[i] == a[i + 1];
        }
        if (sum.length % 2 != 0) {
            return sum;
        } else {
            return checksum(sum);
        }
    }
}
