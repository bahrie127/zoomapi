package api;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Queue;

public class Throttle {

    Queue<Long> queue;
    private int maxNumberCalls;
    private Long timeFrame;

    public Throttle(int maxNumberCalls, Long timeFrame) {
        this.queue = new CircularFifoQueue<>(maxNumberCalls);
        this.maxNumberCalls = maxNumberCalls;
        this.timeFrame = timeFrame;
    }

    public void permit() throws InterruptedException {
        Long now = System.currentTimeMillis();

        if ((queue.size() == maxNumberCalls) && ((now - queue.peek()) < timeFrame)) {
            Long sleep = timeFrame - (now - queue.peek());
            System.out.println("Calls/second rate exceeded. Slowing down for " + sleep + " milliseconds.");
            Thread.sleep(sleep);
        }

        queue.add(System.currentTimeMillis());
    }


}
