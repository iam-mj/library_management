package items;

import database.dao.LendableItemDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;

public abstract class LendableItem {

    private int id;
    private static int nrInstances;
    protected String name;
    protected String genre;
    protected int total;
    protected int available;

    static {
        nrInstances = LendableItemDAO.getMax() + 1;
    }
    {
        id = nrInstances++;
    }

    public LendableItem()
    {
        id = 0;
        available = 0;
        total = 0;
        name = "";
        genre = "";
    }
    // need to access it from other packages
    public LendableItem(String name, String genre, int total)
    {
        this.name = name;
        this.genre = genre;
        this.total = total;
        this.available = total; //initially all are available
    }
    // constructor for data from db
    public LendableItem(int id, String name, String genre, int total, int available)
    {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.total = total;
        this.available = available; //initially all are available
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LendableItem that = (LendableItem) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(genre, that.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre);
    }


    // getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public int getAvailable() {
        return available;
    }
    public int getTotal() {return total; }

    // setters
    public void setName(String name)
    {
        this.name = name;
    }
    public void setGenre(String genre)
    {
        this.genre = genre;
    }
    public void setTotal(int total) { this.total = total; }
    public void setAvailable(int available) { this.available = available; }


    public void borrow()
    {
        // update in the db as well
        LendableItemDAO.update(id, "available", String.valueOf(available - 1));
        available--;
    }
    public void broughtBack()
    {
        // update in the db as well
        LendableItemDAO.update(id, "available", String.valueOf(available + 1));
        available++;
    }
    public LocalDate getBringBackDate(List<LendingCard> lendingCards)
    {
        LocalDate firstDate = LocalDate.now().plusMonths(1);
        // they should all be back within three weeks
        for(LendingCard lendingCard : lendingCards)
        {
            // reference equality
            if(lendingCard.getItem() == this && lendingCard.getBringBackDate().isBefore(firstDate))
                firstDate = lendingCard.getBringBackDate();
        }
        return firstDate;
    }
    public LocalDate findItem(SortedSet<LendingCard> lendingCards)
    {
        for(LendingCard lendingCard : lendingCards)
        {
            // if we find it we return the bring back date
            if(lendingCard.getItem() == this)
                return lendingCard.getBringBackDate();
        }
        return null;
    }
}
