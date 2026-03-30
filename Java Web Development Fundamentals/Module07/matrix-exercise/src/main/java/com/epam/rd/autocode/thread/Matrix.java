package com.epam.rd.autocode.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Matrix {

	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 1000;

	public static int[][] matrixGenerator(int m, int n) {
		int[][] res = new int[m][n];
		Random r = new Random();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				res[i][j] = r.nextInt(MAX_VALUE - MIN_VALUE) + MIN_VALUE;
			}
		}
		return (res);
	}

	public static String oneThreadSearch(int[][] ar, int pause) throws InterruptedException {

        long start = System.currentTimeMillis();
        int max = Integer.MIN_VALUE;

        for (int[] row : ar) {
            for (int el : row) {
                Thread.sleep(pause);
                if (el > max)
                    max = el;
            }
        }
        long end = System.currentTimeMillis();
        return (max + " " + (end - start));
	}

	public static String multipleThreadSearch(int[][] ar, int pause) throws InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();
        int rows = ar.length;

        ExecutorService executor = Executors.newFixedThreadPool(rows);
        List<Future<Integer>> futures = new ArrayList<>();
        for (int[] row : ar) {
            Callable<Integer> task = () -> {
                int max = Integer.MIN_VALUE;
                for (int el : row) {
                    Thread.sleep(pause);
                    if (el > max)
                        max = el;
                }
                return (max);
            };
            futures.add(executor.submit(task));
        }

        int max = Integer.MIN_VALUE;
        for (Future<Integer> future : futures) {
            int rowMax = future.get();
            if (rowMax > max) {
                max = rowMax;
            }
        }
        executor.shutdown();

        long endTime = System.currentTimeMillis();
        return (max + " " + (endTime - startTime));
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int[][] ar = matrixGenerator(4, 50);

		System.out.println(oneThreadSearch(ar, 1));
		System.out.println(multipleThreadSearch(ar, 1));
	}

}
