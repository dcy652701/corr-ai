package corr.ai.module.metadata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author dongchengye
 */
@SpringBootApplication
@EnableAsync
public class MetadataServerApplicatioin {
    public static void main(String[] args) {
        SpringApplication.run(MetadataServerApplicatioin.class, args);
    }
}
