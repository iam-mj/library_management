package items;

import java.util.Objects;

public class DVDGame extends LendableItem{
    private String company;

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
    // for the data from the db
    public DVDGame(int id, String name, String gender, int total, int available, String company)
    {
        super(id, name, gender, total, available);
        this.company = company;
    }

    //getters
    public String getCompany() { return company; }

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
