package com.roys.recipemgmt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-integrationtest.properties")
@SpringBootTest
class RecipeMgmtApplicationTests {

	@Test
	public void whenSpringContextIsBootloading_thenNoException() {

	}
}
