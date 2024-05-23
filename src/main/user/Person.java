package user;

import database.dao.ClientDAO;
import database.dao.PersonDAO;

import java.util.Objects;

// some common attributes and methods for all the Person entities
public abstract class Person {
    private int id;
    private static int nrInstances;
    protected String firstName;
    protected String lastName;

    static {
        nrInstances = PersonDAO.getMax() + 1;
    }
    {
        id = nrInstances;
        nrInstances += 1;
    }

    // public 'cause we have Author in a different package
    public Person()
    {
        firstName = "";
        lastName = "";
    }
    public Person(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Person(int id, String firstName, String lastName)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // getters and setters
    public int getId() { return id; }
    public String getName() { return firstName + ' ' + lastName; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Name: " + getName();
    }
}
