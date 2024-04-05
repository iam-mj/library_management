package items;

import java.util.Objects;

public class DVDGame extends LendableItem{
    String company;

    public DVDGame()
    {
        super();
        this.company = "";
    }
    public DVDGame(String name, String gender, int total, String company)
    {
        super(name, gender, total);
        this.company = company;
    }

    // setters
    public void setCompany(String company)
    {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DVDGame dvdGame = (DVDGame) o;
        return Objects.equals(company, dvdGame.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), company);
    }

    @Override
    public String toString() {
        return "DVD Game\n" +
                "Title = " + name + '\n' +
                "Company = " + company + '\n' +
                "Genre = " + genre + '\n';
    }
}
