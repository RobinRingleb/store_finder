package de.mytoysgroup.multishop.storefinder;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

/**
 * This class provides an interface to the monitoring and statistics tool "graphite" used by operations team. 
 *
 * Not an use case. Not used in store finder.
 */
@Service
public class GraphiteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphiteService.class);

    private final StatsDClient statsdClient;
    private final boolean enabled;

    @Autowired
    public GraphiteService(@Value("${graphite.host}") String host,
                           @Value("${graphite.port}") int port,
                           @Value("${graphite.enabled}") boolean enabled,
                           @Value("${graphite.prefix}") String prefix) {
        //Don't initialize connection if not needed, thus avoid application startup failures due to connection failures
        if (enabled) {
            this.statsdClient = new NonBlockingStatsDClient(prefix, host, port);
        } else {
            this.statsdClient = null;
        }

        this.enabled = enabled;
    }

    
    /**
     * constructor for testing reasons
     * 
     * @param statsdClient
     * @param enabled
     */
    GraphiteService(StatsDClient statsdClient, boolean enabled) {
        this.statsdClient = statsdClient;
        this.enabled = enabled;
    }

    @PreDestroy
    public void stop() {
        if (enabled) {
            try {
                this.statsdClient.stop();
            } catch (Exception e) {
                LOGGER.warn("Uncritical Exception while shutting down statsd client", e);
            }
        }
    }

    /**
     * General method to transfer incrementing values to graphite by key.
     * The intention is to have a public method in this class with a hard coded key.
     * For example like this:
     * <pre>
     * 	<code>
     *     private void countRequest() {
                statsdClient.increment(REQUEST_COUNT_KEY);
    	   }
     * 	</code>
     * </pre>
     * @param key The graphite identifier for this metric
     */
    private void increment(String key) {
        if (enabled) {
            try {
                statsdClient.increment(key);

            // be absolutely fault tolerant, we don't really care about statistics
            } catch (Exception e) {
                LOGGER.warn("Uncritical Exception while sending graphite/statsd count UDP datagram", e);
            }
        }
    }
    
    /**
     * General method to transfer time measures to graphite by key.
     * The intention is to have a public method in this class with a hard coded key.
     * For example like this:
     * <pre>
     * 	<code>
     *     private void timeRequestDuration(long timeInMs) {
                statsdClient.time(TIME_DURATION_KEY, timeInMs);
    	   }
     * 	</code>
     * </pre>
     * @param key The graphite identifier for this metric
     * @param timeInMs The measured value
     */
    private void time(String key, long timeInMs) {
        if (enabled) {
            try {
                statsdClient.time(key, timeInMs);

            } catch (Exception e) {
                LOGGER.warn("Uncritical Exception while sending graphite/statsd time UDP datagram", e);
            }
        }
    }
}
