import java.util.ArrayList;
import java.util.List;

import management.Menu;
import user.Client;
import items.*;

public class Main {
    public static void main(String[] args) {
        Menu menu = Menu.getMenu();

        // create some clients
        Client client1 = new Client("Maria", "Verdes", "maria@test.com", "maria1");
        Client client2 = new Client("Alexandra", "Sima", "alexandra@test.com", "alex1");
        List<Client> clients = new ArrayList<Client>();
        clients.add(client1);
        clients.add(client2);

        // add some items
        Author author = new Author("Terry", "Pratchett");
        Book book = new Book("Small Gods", "fantasy", 5, author);
        AudioBook audioBook = new AudioBook("Going Postal", "fantasy", 1, author, Format.MP3, 360);
        DVDGame game = new DVDGame("Sims4", "slice of life", 2, "EA");
        List<LendableItem> items = new ArrayList<LendableItem>();
        items.add(book);
        items.add(audioBook);
        items.add(game);

        menu.showMenu(clients, items);
    }
}