package zgq.cool.blogbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author 孑然
 */
@SpringBootApplication
@MapperScan("zgq.cool.blogbackend.mapper")
public class BlogBackendApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(BlogBackendApplication.class, args);
	}

}
