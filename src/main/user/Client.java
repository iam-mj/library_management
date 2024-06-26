package user;

import database.dao.LendingCardDAO;
import items.LendableItem;
import items.LendingCard;

import java.time.LocalDate;
import java.util.*;

public class Client extends User{
    private SortedSet<LendingCard> lendingCards;

    public Client()
    {
        super();
        lendingCards = null;
    }

    public Client(String firstName, String lastName, String email, String password)
    {
        super(firstName, lastName, email, password);
        lendingCards = new TreeSet<LendingCard>();
    }

    public Client(int id, String firstName, String lastName, String email, String password)
    {
        super(id, firstName, lastName, email, password);
        lendingCards = new TreeSet<>();
    }

    // getters
    public SortedSet<LendingCard> getLendingCards()
    {
        // return a reference to a copy
        return new TreeSet<LendingCard>(lendingCards);
    }

    // a client's overdue lending cards
    public int getOverdue()
    {
        int nr = 0;
        for(LendingCard lendingCard : lendingCards)
        {
            if(lendingCard.getBringBackDate().isBefore(LocalDate.now()))
                nr++;
        }
        return nr;
    }

    // setters
    public void setLendingCards(SortedSet<LendingCard> lendingCards) { this.lendingCards = lendingCards; }

    // when a client borrows an item
    // returns true is successful
    public boolean borrow(LendableItem item)
    {
        //create a lending card
        LendingCard lendingCard = new LendingCard(this, item);

        // add the new lending card in the client's list if there isn't one there already
        if(lendingCards.contains(lendingCard))
            return false;

        boolean success = LendingCardDAO.create(lendingCard);
        if(!success) return false;
        lendingCards.add(lendingCard);

        item.borrow(); // decrease the available counter
        return true;
    }

    public void returnItem(LendingCard lendingCard)
    {
        // increase the available counter of the item
        lendingCard.getItem().broughtBack();

        // delete the lending card from the db as well
        LendingCardDAO.delete(lendingCard.getId());
        // as from the code
        lendingCards.remove(lendingCard);
    }


    @Override
    public String toString() {
        return super.toString() +
                "Number of lending cards: " + lendingCards.size();
    }
}
