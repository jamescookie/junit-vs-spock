package com.example.demo;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestingExamplesTests {

	@Test
	public void addTwoNumbers() {
		Integer result = TestingExamples.add(1, 2);

		assertThat(result).isEqualTo(3);
	}

}
