import java.util.Arrays;

public class Monitor1 {
    private int[][] MM;
    private int[] B;
    private int[][] MX;
    private int[] Z;
    private int[][] MT;
    private int d;
    private int V[] = new int[Data.N];
    private int A[] = new int[Data.N];
    private int a;

    public synchronized void set_MM(int[][] data) {
        MM = data;
    }

    public synchronized void set_B(int[] data) {
        B = data;
    }

    public synchronized void set_MX(int[][] data) {
        MX = data;
    }

    public synchronized void set_Z(int[] data) {
        Z = data;
    }

    public synchronized void set_MT(int[][] data) {
        MT = data;
    }

    public synchronized void set_d(int data) {
        d = data;
    }

    public synchronized int get_d() {
        return d;
    }

    public synchronized int[] get_A() {
        return A;
    }

    public synchronized int[][] get_MX() {
        return MX;
    }

    public synchronized int get_a() {
        return a;
    }

    public synchronized int[] get_Z() {
        return Z;
    }

    public synchronized int[] get_V() {
        return V;
    }

    // Ah = sort(d * Bh + Z * MMh)
    public void operation1(int d, int[] Z, int lower, int upper) {
        int[] tmpArr = new int[upper - lower];
        int index = 0;
        for (int i = lower; i < upper; i++) {
            int tmp = 0;
            for (int j = 0; j < Data.N; j++) {
                tmp += Z[j] * MM[j][i];
            }
            tmpArr[index] = tmp + d * B[i];
            index++;
        }
        Arrays.sort(tmpArr);
        index = 0;
        for (int i = lower; i < upper; i++) {
            A[i] = tmpArr[index];
            index++;
        }
    }

    // A2h = sort(Ah, Ah)
    public void operation2(int lower, int upper) {
        int[] tmpArr = Arrays.copyOfRange(A, lower, upper);
        Arrays.sort(tmpArr);
        int index = 0;
        for (int i = lower; i < upper; i++) {
            A[i] = tmpArr[index];
            index++;
        }
    }

    // A = sort(A2h, A2h)
    public void operation3() {
        Arrays.sort(A);
    }

    // ai = (Bh * Zh)
    public int operation4(int lower, int upper) {
        int tmp = 0;
        for (int i = lower; i < upper; i++) {
            tmp += B[i] * Z[i];
        }
        return tmp;
    }

    // a = a + ai
    public synchronized void calc_a(int ai) {
        a += ai;
    }

    // Vh = A * (MX * MTh) + a * Bh
    public void operation6(int[] A, int[][] MX, int a, int lower, int upper) {
        for (int i = lower; i < upper; i++) {
            int tmp1 = 0;
            for (int j = 0; j < Data.N; j++) {
                int tmp2 = 0;
                for (int k = 0; k < Data.N; k++) {
                    tmp2 += MX[k][j] * MT[i][k];
                }
                tmp1 += tmp2 * A[j];
            }
            tmp1 += a * B[i];
            V[i] = tmp1;   
        }
    }
}
