package api;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Queue;

public class Throttle {

    Queue<Long> queue;
    private static final Long SECOND = 1000L;
    private int maxNumberCalls;

    public Throttle(int maxNumberCalls) {
        this.queue = new CircularFifoQueue<>(maxNumberCalls);
        this.maxNumberCalls = maxNumberCalls;
    }

    public void permit() throws InterruptedException {
        Long now = System.currentTimeMillis();

        if (queue.size() == maxNumberCalls && (now - queue.peek()) < SECOND) {
            Long sleep = SECOND - (now - queue.peek());
            System.out.println("Calls/second rate exceeded. Slowing down for " + sleep + " milliseconds.");
            Thread.sleep(sleep);
        }

        queue.add(System.currentTimeMillis());
    }


}
