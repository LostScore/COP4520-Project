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
        queue = new LinkedBlockingQueue<>();
        nextQueue = new LinkedBlockingQueue<>();
        try {
            queue.add(i);
            visited[i].set(true);
            for (int j = 0; j < 20; j++)
                threadPool.execute(new HelperBFS(i));
            latch.countDown();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
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
                    if (!queue.isEmpty()) {
                        int index = queue.poll();
                        for (int idx : adjacencyList[index]) {
                            if (!visited[index].get()) {
                                nextQueue.add(idx);
                                visited[index].set(true);
                            }
                        }
                    } else {
                        int num = barrier.getNumberWaiting();
                        if (num == 19) {
                            queue = nextQueue;
                            nextQueue = new LinkedBlockingQueue<Integer>();
                            barrier.await();
                            barrier.reset();
                        } else
                            barrier.await();

                        if (queue.isEmpty() && nextQueue.isEmpty())
                            break;
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                latch.countDown();
            }

        }

    }
}