package isys3461.lab_test_2.user_service.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import isys3461.lab_test_2.user_service.auth.repo.AuthRepo;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DataSeedConfig implements CommandLineRunner {
    @Autowired
    private AuthRepo authRepo;

    @Value("${seed-data.key}")
    private String seedKey;

    @SuppressWarnings("null")
    @Override
    public void run(String... args) throws Exception {
        if (!seedKey.equals("seed_db")) {
            log.info("skip seeding...");
            return;
        }
    }
}
