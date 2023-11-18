package com.example.springBlog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBlogApplicationTests {
	@Test
	void skippableTest() {
		Assertions.assertTrue(true);
	}
	@Test
	void contextLoads() {
	}

}
