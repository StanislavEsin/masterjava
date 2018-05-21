package ru.javaops.masterjava.matrix;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws InterruptedException, ExecutionException {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        CountDownLatch latch = new CountDownLatch(matrixSize);

        IntStream.range(0, matrixSize)
                .forEach(row -> executor.submit(() -> {
                    int thatColumn[] = new int[matrixSize];

                    for (int j = 0; j < matrixSize; j++) {
                        thatColumn[j] = matrixB[j][row];
                    }

                    for (int j = 0; j < matrixSize; j++) {
                        int thisRow[] = matrixA[row];
                        int sum = 0;
                        for (int k = 0; k < matrixSize; k++) {
                            sum += thisRow[k] * thatColumn[k];
                        }
                        matrixC[row][j] = sum;
                    }

                    latch.countDown();
                }));

        try {
            latch.await();
        } catch (InterruptedException E) {
            System.out.println("interrupted");
        }

        return matrixC;
    }

    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        int thatColumn[] = new int[matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                thatColumn[j] = matrixB[j][i];
            }

            for (int j = 0; j < matrixSize; j++) {
                int thisRow[] = matrixA[i];
                int sum = 0;
                for (int k = 0; k < matrixSize; k++) {
                    sum += thisRow[k] * thatColumn[k];
                }
                matrixC[i][j] = sum;
            }
        }
        return matrixC;
    }

    public static int[][] create(int size) {
        int[][] matrix = new int[size][size];
        Random rn = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextInt(10);
            }
        }
        return matrix;
    }

    public static boolean compare(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
