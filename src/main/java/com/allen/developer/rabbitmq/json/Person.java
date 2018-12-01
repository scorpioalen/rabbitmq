package com.allen.developer.rabbitmq.json;

import java.io.Serializable;

/**
 * Created by a.roguljic on 2018-12-01
 */
public class Person implements Serializable {
	private String name;
	private String surname;
	private double weight;

	public Person() {
	}

	public Person(String p_name, String p_surname, double p_weight) {
		name = p_name;
		surname = p_surname;
		weight = p_weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String p_name) {
		name = p_name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String p_surname) {
		surname = p_surname;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double p_weight) {
		weight = p_weight;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", surname='" + surname + '\'' +
				", weight=" + weight +
				'}';
	}
}
