package items;
import user.Person;
public class Author extends Person {
    // =))
    public Author()
    {
        super();
    }
    public Author(String firstName, String lastName)
    {
        super(firstName, lastName);
    }

    @Override
    public String toString()
    {
        return firstName + ' ' + lastName;
    }
}
