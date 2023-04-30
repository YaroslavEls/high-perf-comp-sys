import java.util.Random;

public class Data {
    public static int N;
    public int P;
    public int H;
    public static boolean isRandom;

    public Monitor1 M1;
    public Monitor2 M2;
    public Monitor3 M3;

    public Data(int N, boolean isRandom) {
        Data.N = N;
        this.P = 4;
        this.H = Data.N / this.P;
        Data.isRandom = isRandom;

        M1 = new Monitor1();
        M2 = new Monitor2();
        M3 = new Monitor3();
    }

    public void printMessage(String thread, String message) {
        synchronized (System.in) {
            System.out.println(thread + ": " + message);
        }
    }

    public void printNumber(String name, int number) {
        synchronized (System.in) {
            System.out.println(name);
            System.out.println(number);
        }
    }

    public void printVector(String name, int[] vector) {
        synchronized (System.in) {
            System.out.println(name);
            for (int i = 0; i < N; i++) {
                System.out.print(vector[i] + " ");
            }
            System.out.println();
        }
    }

    public void printMatrix(String name, int[][] matrix) {
        synchronized (System.in) {
            System.out.println(name);
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    public static int number() {
        if (!Data.isRandom) {
            return 1;
        }
        Random random = new Random();
        int number = random.nextInt(21) - 10;
        return number;
    }

    public static int[] vector() {
        int[] vector = new int[Data.N];
        Random random = new Random();
        for (int i = 0; i < Data.N; i++) {
            if (!Data.isRandom) {
                vector[i] = 1;
                continue;
            }
            vector[i] = random.nextInt(21) - 10;
        }
        return vector;
    }

    public static int[][] matrix() {
        int[][] matrix = new int[Data.N][Data.N];
        Random random = new Random();
        for (int i = 0; i < Data.N; i++) {
            for (int j = 0; j < Data.N; j++) {
                if (!Data.isRandom) {
                    matrix[i][j] = 1;
                    continue;
                }
                matrix[i][j] = random.nextInt(21) - 10;
            }
        }
        return matrix;
    }
}
