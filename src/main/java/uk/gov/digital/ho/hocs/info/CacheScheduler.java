package uk.gov.digital.ho.hocs.info;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uk.gov.digital.ho.hocs.info.user.UserService;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class CacheScheduler {

    private UserService userService;

    @Scheduled(fixedDelayString = "${cache.user.refresh}000")
    public void refreshUserCache(){
        userService.refreshUserCache();
    }

}
