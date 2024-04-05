package user;

import items.LendableItem;
import items.LendingCard;

import java.time.LocalDate;
import java.util.*;

public class Client extends User{
    SortedSet<LendingCard> lendingCards;
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

    @Override
    public String toString() {
        return "Client " + firstName + ' ' + lastName + ":\n" +
                "Id = " + id + '\n' +
                "Email = '" + email + '\n' +
                "Number of lending cards = " + lendingCards.size();
    }

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

    // when a client borrows an item
    // returns true is successful
    public boolean borrow(LendableItem item)
    {
        //create a lending card
        LendingCard lendingCard = new LendingCard(this, item);

        // add the new lending card in the client's list if there isn't one there already
        if(lendingCards.contains(lendingCard))
            return false;

        lendingCards.add(lendingCard);

        item.borrow(); // decrease the available counter
        return true;
    }

    public void returnItem(LendingCard lendingCard)
    {
        // increse the available counter of the item
        lendingCard.getItem().broughtBack();

        // delete the lending card
        lendingCards.remove(lendingCard);
    }
}
