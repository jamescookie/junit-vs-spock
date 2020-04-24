package com.example.demo;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TestingExamples {
    private final PlanetRepo planetRepo;
    static int counter = 0;

    public static Integer add(Integer first, Integer second) {
        return (first == null ? 0 : first) + (second == null ? 0 : second);
    }

    public static int divide(int first, int second) {
        return first / second;
    }

    public static void count() {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
        }).start();
    }

    public long massOfSolarSystem() {
        List<Planet> planets = planetRepo.findAll();
        return planets.stream().mapToLong(Planet::getMass).sum();
    }

    public static String firstPlanet() {
        return "{\n" +
                "  \"name\": \"Mercury\",\n" +
                "  \"mass\": 3285\n" +
                "}";
    }

    public static Planet lastPlanet() {
        return SolarSystem.last();
    }
}
