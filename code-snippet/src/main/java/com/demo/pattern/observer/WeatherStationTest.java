package com.demo.pattern.observer;

public class WeatherStationTest {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionDisplay display = new CurrentConditionDisplay(weatherData);
        display.update(80, 65, 30.4F);
    }
}
