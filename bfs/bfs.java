package bfs;

import java.util.ArrayDeque;
import java.util.LinkedList;

public class bfs {
    private LinkedList<LinkedList<Integer>> adjacencyList;

    public bfs(LinkedList<LinkedList<Integer>> graph) {
        adjacencyList = graph;
    }

    public void GraphBFS(int i) {
        boolean visited[] = new boolean[adjacencyList.size()];

        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();

        visited[i] = true;
        queue.add(i);
        while (!queue.isEmpty()) {
            int index = queue.poll();
            // System.out.print(index + " ");

            for (Integer idx : adjacencyList.get(index)) {
                if (!visited[idx]) {
                    visited[idx] = true;
                    queue.add(idx);
                }

            }

        }
        //System.out.println();
    }
}