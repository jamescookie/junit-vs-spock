package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestingExamplesTests {

    @Test
    void addTwoNumbers() {
        Integer result = TestingExamples.add(1, 2);

        assertThat(result).isEqualTo(3);
    }

    // multiple tests

	@ParameterizedTest
	@MethodSource
	void addTwoNumbersTogether(Integer first, Integer second, Integer expected) {
		Integer result = TestingExamples.add(first, second);

		assertThat(result).isEqualTo(expected);
	}

	private static Stream<Arguments> addTwoNumbersTogether() {
		return Stream.of(
				Arguments.of(1, 2, 3),
				Arguments.of(null, 2, 2),
				Arguments.of(1, null, 1),
				Arguments.of(null, null, 0)
		);
	}

    //exceptions
    @Test
    void divisionByZero() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            TestingExamples.divide(1, 0);
        });

        assertThat(exception.getMessage()).contains("by zero");
    }

    //mocking
    @Mock
    private PlanetRepo planetRepo;

    @Test
    void massOfSolarSystem() {
        Planet jupiter = Planet.builder().name("Jupiter").mass(18980000).build();
        Planet earth = Planet.builder().name("Earth").mass(59720).build();
        TestingExamples mathsStuff = new TestingExamples(planetRepo);

        when(planetRepo.findAll()).thenReturn(List.of(
                jupiter,
                earth
        ));

        long result = mathsStuff.massOfSolarSystem();

        assertThat(result).isEqualTo(earth.getMass() + jupiter.getMass());
        verify(planetRepo).findAll();
    }

    //json
    @Test
    void jsonParsing() throws java.io.IOException {
        var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        Planet planet = objectMapper.readValue(TestingExamples.firstPlanet(), Planet.class);

        assertThat(planet.getName()).isEqualTo("Mercury");
    }

    //async
    @Test
    void asyncAssertion() {
        int initial = TestingExamples.counter;

        TestingExamples.count();

        org.awaitility.Awaitility.await().atMost(1, java.util.concurrent.TimeUnit.SECONDS).until(() ->
                TestingExamples.counter == initial + 1
        );
    }

}
