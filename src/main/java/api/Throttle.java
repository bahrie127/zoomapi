package api;

public class Throttle {

    private Long lastChecked = null;
    private Integer currentNumberCalls = 0;
    private int maxNumberCalls;
    private static final Long SECOND = 1000L;

    public Throttle(int maxNumberCalls) {
        this.maxNumberCalls = maxNumberCalls;
    }

    public void permit() throws InterruptedException {
        System.out.println(currentNumberCalls);
        Long now = System.currentTimeMillis();
        if (currentNumberCalls == 0 || (lastChecked - now) > SECOND) {
            currentNumberCalls = 1;
            lastChecked = System.currentTimeMillis();
        } else if (currentNumberCalls == maxNumberCalls) {
            currentNumberCalls = 1;
            Long sleep = SECOND - (lastChecked - now);
            System.out.println("Calls/second rate exceeded. Slowing down for " + sleep + " milliseconds.");
            Thread.sleep(sleep);
            lastChecked = System.currentTimeMillis();
        } else {
            currentNumberCalls++;
        }
    }

}
