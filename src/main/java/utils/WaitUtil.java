package utils;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;

@Log4j2
public final class WaitUtil {

    private WaitUtil() {
        //Utility class
    }

    /**
     * Sets wait for the process
     *
     */
    public static void setWait(int seconds) {
        if (seconds > 15) log.info("Waiting seconds:" + seconds);
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage());
        }
    }

    public static void setWait(long seconds) {
        if (seconds > 15) log.info("Waiting seconds:" + seconds);
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage());
        }
    }

    public static void setWaitMs(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage());
        }
    }
}
