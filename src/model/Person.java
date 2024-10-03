package model;

public abstract class Person {
    protected static String name;

    // Constructor with name parameter
    public Person(String name) {
        super();
        this.name = name;
    }

    // Default constructor
    public Person() {
    }
}
