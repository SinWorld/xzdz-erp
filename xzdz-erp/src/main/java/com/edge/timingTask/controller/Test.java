package com.edge.timingTask.controller;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException {
		Class c = Class.forName("com.edge.timingTask.controller.TestTask");
		System.out.println(c.getName());
	}
}
