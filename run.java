import java.util.LinkedList;
import bfs.*;

public class run {
    public static LinkedList<Integer> adjacencyList[];

    public static void main(String[] args) {
        adjacencyList = new LinkedList[101];
        int count = 0;
        int j;
        // Create the graph depicted in the image
        for (int i = 0; i < 101; i++)
            adjacencyList[i] = new LinkedList<Integer>();

        for (j = count + 1; j < count + 1 + 6; j++) {
            addEdge(count, j);
        }

        count = j;

        for (int i = 1; i < 7; i++) {
            for (int k = 7 + 3 * (i - 1); k < 7 + (3 * i); k++)
                addEdge(i, k);
        }

        for (int i = 7; i < 25; i++) {
            for (int k = 25 + 2 * (i - 7); k < 25 + 2 * (i - 6); k++)
                addEdge(i, k);
        }

        for (int i = 25; i < 61; i++) {
            int k = 61 + i - 25;
            addEdge(i, k);
        }

        addEdge(61, 97);
        addEdge(97, 98);
        addEdge(98, 99);
        addEdge(99, 100);

        // Get the time in the bfs algorithmn
        long startTime = System.nanoTime();
        bfs sequential = new bfs(adjacencyList);
        sequential.GraphBFS(0);
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000000;
        System.out.println(executionTime + " ms");
        startTime = System.nanoTime();
        // Get the time in the parralel bfs algorithmn
        parralelbfs parralel = new parralelbfs(adjacencyList);
        parralel.GraphBFS(0);
        endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1000000;
        System.out.println(executionTime + " ms");

    }

    static void addEdge(int x, int y) {
        adjacencyList[x].add(y);
        adjacencyList[y].add(x);
    }

}
