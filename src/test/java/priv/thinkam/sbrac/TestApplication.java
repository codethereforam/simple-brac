package priv.thinkam.sbrac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import priv.thinkam.sbrac.config.EnableSbrac;

/**
 * @author thinkam
 * @date 2019/12/8 21:10
 */
@EnableSbrac
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
