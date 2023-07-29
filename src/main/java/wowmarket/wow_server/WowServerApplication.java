package wowmarket.wow_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WowServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WowServerApplication.class, args);
	}

}
