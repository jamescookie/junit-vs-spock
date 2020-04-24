package com.example.demo

import spock.lang.Specification
import spock.lang.Unroll

class TestingExamplesSpec extends Specification {

    def "add two numbers"() {
        when:
        Integer result = TestingExamples.add(1, 2)

        then:
        result == 3
    }

    // multiple tests
    @Unroll
    def "add #first and #second together"() {
        when:
        Integer result = TestingExamples.add(first, second)

        then:
        result == expected

        where:
        first | second | expected
        1     | 2      | 3
        null  | 2      | 2
        1     | null   | 1
        null  | null   | 0
    }

    //exceptions
    def "division by zero"() {
        when:
        TestingExamples.divide(1, 0)

        then:
        ArithmeticException exception = thrown()
        exception.message.contains("by zero")
    }

    //mocking
    PlanetRepo planetRepo = Mock()

    def "mass of solar system"() {
        given:
        Planet jupiter = new Planet(name: "Jupiter", mass: 18980000)
        Planet earth = new Planet(name: "Earth", mass: 59720)
        TestingExamples testingExamples = new TestingExamples(planetRepo)

        when:
        long result = testingExamples.massOfSolarSystem()

        then:
        result == earth.mass + jupiter.mass

        and:
        1 * planetRepo.findAll() >> [jupiter, earth]
    }

    //json
    def "json parsing"() {
        when:
        def parser = new groovy.json.JsonSlurper()
        Planet planet = parser.parseText(TestingExamples.firstPlanet()) as Planet

        then:
        planet.name == "Mercury"
    }

    //async
    def "async assertion"() {
        given:
        def conditions = new spock.util.concurrent.PollingConditions(timeout: 1)
        int initial = TestingExamples.counter

        when:
        TestingExamples.count()

        then:
        conditions.eventually {
            assert TestingExamples.counter == initial + 1
        }
    }

}
