package ru.javaops.masterjava.matrix;

import java.util.concurrent.CountDownLatch;

/**
 * Task.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 19.05.2018
 */
public class Task implements Runnable {
    CountDownLatch latch;
    private int matrixA[][];
    private int matrixB[][];
    private int matrixC[][];
    private int matrixSize;
    private int i;

    public Task(CountDownLatch latch, int[][] matrixA, int matrixB[][], int[][] matrixC, int matrixSize, int i) {
        this.latch = latch;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixC = matrixC;
        this.matrixSize = matrixSize;
        this.i = i;
    }

    @Override
    public void run() {
        int thatColumn[] = new int[matrixSize];

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

        latch.countDown();
    }
}