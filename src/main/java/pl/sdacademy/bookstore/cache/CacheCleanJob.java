package pl.sdacademy.bookstore.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.sdacademy.bookstore.service.ICartService;

@Component
public class CacheCleanJob {
    private static final Logger LOG = LoggerFactory.getLogger(CacheCleanJob.class);

    private final ICartService cartService;

    public CacheCleanJob(ICartService cartService) {
        this.cartService = cartService;
    }

    @Scheduled(fixedDelayString = "${bookstore.cart.clean-timeout:10368000}")
    public void scheduledClean() {
        LOG.info("tutaj będzie czyszczenie koszyka... kiedyś :)");
        cartService.clean();
    }
}
