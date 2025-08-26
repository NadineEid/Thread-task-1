import java.io.*;
import java.util.*;

class MatrixMultiply {
    static int[][] A, B, C;

    public static void main(String[] args) throws Exception {
        A = read("G:/thread task 2/src/A.txt");
        B = read("G:/thread task 2/src/B.txt");

        int n = A.length;
        int m = A[0].length;
        int p = B[0].length;

        C = new int[n][p];

        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(new RowMultiplyTask(i, A, B, C));
            threads[i].start();
        }

        for (int i = 0; i < n; i++) {
            threads[i].join();
        }

        // print result
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }

    static int[][] read(String file) throws Exception {
        List<int[]> rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split(" ");
            int[] nums = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                nums[i] = Integer.parseInt(parts[i]);
            }
            rows.add(nums);
        }
        br.close();
        return rows.toArray(new int[0][]);
    }
}


class RowMultiplyTask implements Runnable {
    int row;
    int[][] A, B, C;

    RowMultiplyTask(int row, int[][] A, int[][] B, int[][] C) {
        this.row = row;
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public void run() {
        int m = A[0].length;
        int p = B[0].length;
        for (int j = 0; j < p; j++) {
            for (int k = 0; k < m; k++) {
                C[row][j] += A[row][k] * B[k][j];
            }
        }
    }
}
