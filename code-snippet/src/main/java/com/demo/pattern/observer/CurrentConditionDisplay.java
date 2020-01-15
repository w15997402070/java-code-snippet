package com.demo.pattern.observer;

public class CurrentConditionDisplay implements Observer, DisplayElement {

    private float temperature;
    private float humidity;
    private float pressure;

    private Subject weatherData;

    public CurrentConditionDisplay(Subject subject) {
        this.weatherData = subject;
        subject.addObserver(this);
    }

    @Override
    public void displayElement() {
        System.out.println("Current condition: " + temperature + "F degrees and " + humidity
                + "% 	humidity");
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        displayElement();
    }

}
