import java.util.LinkedList;
import bfs.*;

public class run {
    public static LinkedList<LinkedList<Integer>> adjacencyList;
    public static LinkedList<LinkedList<Integer>> worstList;
    public static void main(String[] args) {
        int numThread = 20;
        if (args.length == 1)
            numThread = Integer.parseInt(args[0]);
        System.out.println("Testing with "+numThread+" threads");
        adjacencyList =  new LinkedList<>();
        worstList = new LinkedList<>();
        int count = 0;
        int j;
        // Create the graph depicted in the image
        for (int i = 0; i < 101; i++)
            adjacencyList.add(new LinkedList<Integer>()); 

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

        for (int i = 0; i < 100; i++)
            worstList.add(new LinkedList<Integer>());

        for (int i = 1; i < 100; i++)
            addEdge(i, i-1);
            


        System.out.println("Test Case");
        // Get the time in the bfs algorithmn
        long startTime = System.nanoTime();
        bfs sequential = new bfs(adjacencyList);
        sequential.GraphBFS(0);
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1000;
        System.out.println("Sequential Time:  "+executionTime + " microseconds");
        startTime = System.nanoTime();
        // Get the time in the parralel bfs algorithmn
        parralelbfs parralel = new parralelbfs(adjacencyList,numThread);
        parralel.GraphBFS(0);
        endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1000;
        System.out.println("Parralel Time:    "+executionTime + " microseconds");

        System.out.println("Worst Case");
        startTime = System.nanoTime();
        sequential = new bfs(worstList);
        sequential.GraphBFS(0);
        endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1000;
        System.out.println("Sequential Time:  "+executionTime + " microseconds");

        startTime = System.nanoTime();
        parralel = new parralelbfs(worstList, numThread);
        parralel.GraphBFS(0);
        endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1000;
        System.out.println("Parralel Time:    "+executionTime + " microseconds");

    }

    static void addEdge(int x, int y) {
        adjacencyList.get(x).add(y);
        adjacencyList.get(y).add(x);
    }

}
