package user;

import java.util.Objects;

public abstract class User extends Person{
    protected String email;
    protected String password; // can I keep it hidden?

    User()
    {
        super();
        email = "";
        password = "";
    }
    User(String firstName, String lastName, String email, String password)
    {
        super(firstName, lastName);
        this.email = email;
        this.password = password;
    }
    User(int id, String firstName, String lastName, String email, String password)
    {
        super(id, firstName, lastName);
        this.email = email;
        this.password = password;
    }

    // getters
    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    // setters
    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }

    @Override
    public String toString() {
        return super.toString() + '\n' +
                "Email: " + getEmail() + '\n';
    }
}
