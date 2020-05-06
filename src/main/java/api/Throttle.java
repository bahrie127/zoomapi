package api;

import org.apache.commons.collections4.QueueUtils;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Throttle {

    Queue<Long> queue;
    private int maxNumberCalls;
    private Long timeFrame;
    private Logger logger = Logger.getLogger(Throttle.class.getName());

    public Throttle(int maxNumberCalls, Long timeFrame) {
        this.queue = QueueUtils.synchronizedQueue(new CircularFifoQueue<>(maxNumberCalls));
        this.maxNumberCalls = maxNumberCalls;
        this.timeFrame = timeFrame;
    }

    public void permit() throws InterruptedException {
        synchronized (this) {
            Long now = System.currentTimeMillis();

            if ((queue.size() == maxNumberCalls) && ((now - queue.peek()) < timeFrame)) {
                Long sleep = timeFrame - (now - queue.peek());
                logger.log(Level.INFO, "Calls/second rate exceeded. Slowing down for " + sleep + " milliseconds.");
                Thread.sleep(sleep);
            }

            queue.add(System.currentTimeMillis());
        }
    }


}
