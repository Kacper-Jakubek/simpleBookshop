package pl.sdacademy.bookstore.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.sdacademy.bookstore.service.ICartService;
@Component
public class CacheCleaner {

        private static final Logger LOG = LoggerFactory.getLogger(pl.sdacademy.bookstore.cache.CacheCleaner.class);

        private final ICartService cartService;

        public CacheCleaner(ICartService cartService) {
            this.cartService = cartService;
        }

        @Scheduled(fixedDelayString = "${cache.carts.clean-timeout:5000}")
        public void scheduledClean() {
            LOG.info("looking for obsolete carts");
            LOG.info("deleted {} carts", cartService.clean());
        }
}
