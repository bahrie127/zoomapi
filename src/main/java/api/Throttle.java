package api;

public class Throttle {

    private Long lastChecked = null;
    private Integer currentNumberCalls = 0;
    private static final Integer MAX_NUMBER_CALLS = 10;
    private static final Long SECOND = 1000L;

    public void permit() throws InterruptedException {
        Long now = System.currentTimeMillis();
        if (currentNumberCalls == 0 || (lastChecked - now) >= SECOND) {
            currentNumberCalls = 1;
            lastChecked = System.currentTimeMillis();
        } else if (currentNumberCalls == MAX_NUMBER_CALLS) {
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
