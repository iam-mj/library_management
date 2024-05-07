package user;

// there were supposed to be more librarians who could manipulate the items and
// whose activity would be logged, but I couldn't find the time to use it before
// the first project checkpoint

public class Librarian extends User {
    private String workplace;

    Librarian() {
        super();
        workplace = "";
    }
    Librarian(String firstName, String lastName, String email, String password,
              String workplace)
    {
        super(firstName, lastName, email, password);
        this.workplace = workplace;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Workplace: " + workplace + '\n';
    }
}
