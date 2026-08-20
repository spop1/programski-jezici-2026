package rs.ac.singidunum.pj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ProgramskiJeziciApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgramskiJeziciApplication.class, args);
	}

}
