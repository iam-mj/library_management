package items;

import user.Person;

public class Author extends Person {

    public Author()
    {
        super();
    }
    public Author(String firstName, String lastName)
    {
        super(firstName, lastName);
    }
    public Author(int id, String firstName, String lastName) { super(id, firstName, lastName); }

    @Override
    public String toString()
    {
        return firstName + ' ' + lastName;
    }
}
