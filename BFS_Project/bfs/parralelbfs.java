package bfs;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class parralelbfs {
    private LinkedList<Integer> adjacencyList[];
    private LinkedBlockingQueue<Integer> queue;
    private LinkedBlockingQueue<Integer> nextQueue;
    private CyclicBarrier barrier;
    private AtomicBoolean visited[];
    private CountDownLatch latch;

    public parralelbfs(LinkedList<Integer> graph[]) {
        adjacencyList = graph;
    }

    public void GraphBFS(int i) {
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        latch = new CountDownLatch(21);
        barrier = new CyclicBarrier(20);
        visited = new AtomicBoolean[adjacencyList.length];
        for (int x = 0; x < adjacencyList.length; x++) {
            // System.out.println(visited[x]);
            visited[x] = new AtomicBoolean(false);
            // System.out.println(visited[x]);
        }
        // Each thread will have access to this queue that has all vertex in a current level
        queue = new LinkedBlockingQueue<>();
        // Idea, have a seperate queue that is used for the adjacent arrays
        // that is in the next level.
        nextQueue = new LinkedBlockingQueue<>();
        try {
            // Intialize by adding starting vertex
            queue.add(i);
            // Set it to being visited
            visited[i].set(true);
            // Spin up the treadpool
            for (int j = 0; j < 20; j++)
                threadPool.execute(new HelperBFS(i));
            // Latch on the threadPool to main thread
            latch.countDown();
            // Wait for all the thread to finish
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shut down threadpool
            threadPool.shutdown();
        }

    }

    class HelperBFS implements Runnable {
        int threadNum;

        HelperBFS(int i) {
            threadNum = i;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    // If the queue isn't empty it means that there are values still
                    // still in the queue
                    if (!queue.isEmpty()) {
                        // Retrieve the value
                        int index = queue.poll();
                        for (int idx : adjacencyList[index]) {
                            // Check if it's visited
                            if (!visited[index].get()) {
                                // add it to the nextQueue
                                nextQueue.add(idx);
                                visited[index].set(true);
                            }
                        }
                        
                    }
                    // There isn't anything in the queue left 
                    else {
                        // get the waiting number for thread
                        int num = barrier.getNumberWaiting();
                        // if this thread is the last thread i.e all 20 are waiting here
                        if (num == 19) {
                            // This thread makes the queue the next queue and makes a brand new one
                            queue = nextQueue;
                            nextQueue = new LinkedBlockingQueue<Integer>();
                            // join the waiting and reset barrier
                            barrier.await();
                            barrier.reset();
                        } 
                        else
                            // Thread 0-18 wait here
                            barrier.await();

                        // If the queue is empty and nextQueue is empty that means we tranverse every vertext applicable
                        if (queue.isEmpty() && nextQueue.isEmpty())
                            break;
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                // The thread is done and can rejoin main.
                latch.countDown();
            }

        }

    }
}