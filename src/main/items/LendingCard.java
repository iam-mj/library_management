package items;

import database.dao.LendingCardDAO;
import user.Client;

import java.time.LocalDate;
import java.util.Objects;

public class LendingCard implements Comparable<LendingCard> {
    private int id;
    private static int nrInstances;
    private static int lendingPeriod = 3; // in weeks
    private Client client;
    private LendableItem item;
    private LocalDate lentDate; // format "dd/mm/yyyy"
    private LocalDate bringBackDate; // format "dd/mm/yyyy"

    static {
        nrInstances = LendingCardDAO.getMax() + 1;
    }
    {
        id = nrInstances + 1;
    }

    public LendingCard()
    {
        client = null;
        item = null;
        lentDate = null;
        bringBackDate = null;
    }

    // we need to access it from other packages
    public LendingCard(Client client, LendableItem item)
    {
        this.id = 0;
        this.client = client;
        this.item = item;
        this.lentDate = LocalDate.now();
        this.bringBackDate = lentDate.plusWeeks(lendingPeriod);
    }
    // for data from the db
    public LendingCard(int id, Client client, LendableItem item, LocalDate lentDate, LocalDate bringBackDate)
    {
        this.id = id;
        this.client = client;
        this.item = item;
        this.lentDate = lentDate;
        this.bringBackDate = bringBackDate;
    }

    // getters
    public int getId() { return id; }
    public Client getClient() { return client; }
    public LendableItem getItem()
    {
        return item;
    }
    public LocalDate getBringBackDate()
    {
        return bringBackDate;
    }
    // for the db
    public String getLentDateString()
    {
        return "to_date('" + lentDate.toString() + "', 'YYYY-MM-DD')";
    }
    public String getBringBackDateString()
    {
        return "to_date('" + bringBackDate.toString() + "', 'YYYY-MM-DD')";
    }


    @Override
    public int compareTo(LendingCard o) {
        // we compare them after the lending date
        if(this.lentDate.isBefore(o.lentDate))
            return -1;
        if(this.lentDate.isAfter(o.lentDate))
            return 1;
        if(this.equals(o)) // return 0 only if they're actually equal
            return 0;
        return 1;
    }

    // lending card are equal if they have the same client & item
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LendingCard that = (LendingCard) o;
        return Objects.equals(client, that.client) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, item);
    }

    @Override
    public String toString() {
        return "Client = " + client.getName() + '\n' +
                "Item = " + item.name + '\n' +
                "Lent Date = " + lentDate + '\n' +
                "Bring Back Date = " + bringBackDate;
    }
}
