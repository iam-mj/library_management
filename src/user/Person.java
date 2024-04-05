package user;

import java.util.Objects;

// some common attributes and methods for all the Person entities
public abstract class Person {
    protected String firstName;
    protected String lastName;

    // publici fiindca avem Author intr-un pachet diferit
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    public String getName()
    {
        return firstName + ' ' + lastName;
        //cam asa ca pun 4 stringuri in string pool?
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
