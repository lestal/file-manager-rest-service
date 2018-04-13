package com.smartvid.directory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*@Configuration
@ComponentScan(value = "com.smartvid.directory",
        useDefaultFilters = false)*/
@SpringBootApplication
public class DirectoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectoryApplication.class, args);
	}
}
