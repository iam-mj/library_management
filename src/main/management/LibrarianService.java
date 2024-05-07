package management;

import items.*;
import user.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public abstract class LibrarianService {
    static short addBook(Scanner in, List<LendableItem> items)
    {
        String title, genre, authorFirstName, authorLastName;
        int total;
        System.out.println("Adding a new Book!");
        System.out.println("Title:");
        title = in.nextLine();
        System.out.println("Genre: ");
        genre = in.nextLine();
        System.out.println("Author's first name:");
        authorFirstName = in.nextLine();
        System.out.println("Author's last name:");
        authorLastName = in.nextLine();
        System.out.println("Total number:");
        total = in.nextInt();
        Author author = new Author(authorFirstName, authorLastName);
        Book book = new Book(title, genre, total, author);
        items.add(book);
        System.out.println("\nBook added!");
        System.out.println("Would you like to:");
        System.out.println("1) Add another");
        System.out.println("2) Go back");
        System.out.println("\nYour choice: ");
        short addChoice = in.nextShort();
        in.nextLine(); // consume the \n character left
        return addChoice;
    }

    static short addAudioBook(Scanner in, List<LendableItem> items)
    {
        String firstName, lastName, name, genre;
        Format format = Format.MP3;
        int total, formatIndex, duration;
        System.out.println("Let's add an audiobook!");
        System.out.println("Title: ");
        name = in.nextLine();
        System.out.println("Genre: ");
        genre = in.nextLine();
        System.out.println("Author's first name: ");
        firstName = in.nextLine();
        System.out.println("Author's last name: ");
        lastName = in.nextLine();
        System.out.println("Choose audiobook format: ");
        System.out.println("1) mp3");
        System.out.println("2) wma");
        System.out.println("3) flac");
        formatIndex = in.nextInt();
        switch(formatIndex)
        {
            case 2:
            {
                format = Format.WMA;
                break;
            }
            case 3:
            {
                format = Format.FLAC;
                break;
            }
        }
        System.out.println("How long is it? (in minutes): ");
        duration = in.nextInt();
        System.out.println("How many copies are there? ");
        total = in.nextInt();
        // create the author object
        Author author = new Author(firstName, lastName);

        //create the audiobook
        AudioBook audioBook = new AudioBook(name, genre, total, author,
                format, duration);

        items.add(audioBook);
        System.out.println("\nAudioBook added!");
        System.out.println("Would you like to:");
        System.out.println("1) Add another");
        System.out.println("2) Go back");
        System.out.println("\nYour choice: ");
        short addChoice = in.nextShort();
        in.nextLine(); // consume the \n character left
        return addChoice;
    }

    static short addGame(Scanner in, List<LendableItem> items)
    {
        System.out.println("Let's add a DVD Game!");
        String name, genre, company;
        int total;
        System.out.println("Title: ");
        name = in.nextLine();
        System.out.println("Genre: ");
        genre = in.nextLine();
        System.out.println("Company: ");
        company = in.nextLine();
        System.out.println("How many copies are there?");
        total = in.nextInt();
        DVDGame game = new DVDGame(name, genre, total, company);
        items.add(game);
        System.out.println("\nGame added!");
        System.out.println("Would you like to:");
        System.out.println("1) Add another");
        System.out.println("2) Go back");
        System.out.println("\nYour choice: ");
        short addChoice = in.nextShort();
        in.nextLine(); //consume the \n character left}
        return addChoice;
    }

    static void editItem(Scanner in, List<LendableItem> items)
    {
        while(true)
        {
            Menu.clearScreen();
            System.out.println("Edit an item!\n");
            System.out.println("Would you like to edit:");
            System.out.println("1) A Book");
            System.out.println("2) An Audiobook");
            System.out.println("3) A DVD Game");
            System.out.println("\nYour choice: ");
            short editChoice = in.nextShort();
            Menu.clearScreen();

            // edit a book
            if(editChoice == 1)
            {
                System.out.println("Edit Books!");
                // show all books
                for(int i = 0; i < items.size(); i++)
                {
                    if(items.get(i) instanceof Book && !(items.get(i) instanceof AudioBook))
                    {
                        System.out.println("\nBook " + i);
                        System.out.println(items.get(i));
                    }
                }
                System.out.println("\nWhich book would you like to edit?");
                int itemIndex = in.nextInt();
                LendableItem item = items.get(itemIndex);
                Menu.clearScreen();
                editBook(in, item);
            }

            // edit an audiobook
            if(editChoice == 2)
            {
                System.out.println("Edit AudioBooks!");
                // show all audiobooks
                for(int i = 0; i < items.size(); i++)
                {
                    if(items.get(i) instanceof AudioBook)
                    {
                        System.out.println("\nAudioBook " + i);
                        System.out.println(items.get(i));
                    }
                }
                System.out.println("\nWhich audiobook would you like to edit?");
                int itemIndex = in.nextInt();
                LendableItem item = items.get(itemIndex);
                Menu.clearScreen();
                editAudioBook(in, item);
            }

            // if we want to edit a game
            if(editChoice == 3)
            {
                System.out.println("Edit Games!");
                // show all games
                for(int i = 0; i < items.size(); i++)
                {
                    if(items.get(i) instanceof DVDGame)
                    {
                        System.out.println("\nGame " + i);
                        System.out.println(items.get(i));
                    }
                }
                System.out.println("\nWhich game would you like to edit?");
                int itemIndex = in.nextInt();
                LendableItem item = items.get(itemIndex);
                Menu.clearScreen();
                editGame(in, item);
            }
            System.out.println("Would you like to:");
            System.out.println("1) Edit other items");
            System.out.println("2) Return");
            System.out.println("\nYour choice: ");
            editChoice = in.nextShort();
            if(editChoice == 2)
                break;
        }
    }

    static void editBook(Scanner in, LendableItem item)
    {
        System.out.println("Edit Book!\n");
        System.out.println(item);
        System.out.println("What would you like edit?");
        System.out.println("1) The title");
        System.out.println("2) The genre");
        System.out.println("3) The author's first name");
        System.out.println("4) The author's last name");
        System.out.println("\nYour choice: ");
        int edit = in.nextInt();
        in.nextLine(); // consume the \n character left
        switch(edit)
        {
            case 1:
            {
                System.out.println("\nNew title: ");
                String title = in.nextLine();
                item.setName(title);
                Menu.clearScreen();
                System.out.println(item);
                System.out.println("\nTitle changed!");
                break;
            }
            case 2:
            {
                System.out.println("\nNew genre: ");
                String genre = in.nextLine();
                item.setGenre(genre);
                System.out.println("\nGenre changed!");
                break;
            }
            case 3:
            {
                System.out.println("\nNew author's first name: ");
                String firstName = in.nextLine();
                // downcast needed !!
                // it should be a book, but we'll still check
                Menu.clearScreen();
                if(item instanceof Book && !(item instanceof AudioBook))
                {
                    ((Book)item).getAuthor().setFirstName(firstName);
                    System.out.println(item);
                    System.out.println("\nAuthor's first name was changed!");
                }
                else
                {
                    System.out.println(item);
                    System.out.println("\nCould not change the author's name!");
                    System.out.println("The selected item isn't a valid book");
                }
                break;
            }
            case 4:
            {
                System.out.println("\nNew author's last name: ");
                String lastName = in.nextLine();
                Menu.clearScreen();
                // downcast needed !!
                if(item instanceof Book && !(item instanceof AudioBook))
                {
                    ((Book)item).getAuthor().setLastName(lastName);
                    System.out.println(item);
                    System.out.println("\nAuthor's first name was changed!");
                }
                else
                {
                    System.out.println(item);
                    System.out.println("\nCould not change the author's name!");
                    System.out.println("The selected item isn't a valid book");
                }
                break;
            }
        }
    }

    static void editAudioBook(Scanner in, LendableItem item)
    {
        System.out.println("Edit Audiobook!\n");
        System.out.println(item);
        System.out.println("What would you like edit?");
        System.out.println("1) The title");
        System.out.println("2) The genre");
        System.out.println("3) The author's first name");
        System.out.println("4) The author's last name");
        System.out.println("5) The format");
        System.out.println("6) The duration");
        System.out.println("\nYour choice: ");
        int edit = in.nextInt();
        in.nextLine(); // consume the \n character left
        switch(edit)
        {
            case 1:
            {
                System.out.println("\nNew title: ");
                String title = in.nextLine();
                item.setName(title);
                Menu.clearScreen();
                System.out.println(item);
                System.out.println("\nTitle changed!");
                break;
            }
            case 2:
            {
                System.out.println("\nNew genre: ");
                String genre = in.nextLine();
                item.setGenre(genre);
                Menu.clearScreen();
                System.out.println(item);
                System.out.println("\nGenre changed!");
                break;
            }
            case 3:
            {
                System.out.println("\nNew author's first name: ");
                String firstName = in.nextLine();
                Menu.clearScreen();
                // downcast needed !!
                // it should be an audiobook, but we'll still check
                if(item instanceof AudioBook)
                {
                    ((AudioBook)item).getAuthor().setFirstName(firstName);
                    System.out.println(item);
                    System.out.println("\nAuthor's first name was changed!");
                }
                else
                {
                    System.out.println(item);
                    System.out.println("\nCould not change the author's name!");
                    System.out.println("The selected item isn't a valid audiobook");
                }
                break;
            }
            case 4:
            {
                System.out.println("\nNew author's last name: ");
                String lastName = in.nextLine();
                Menu.clearScreen();
                // downcast needed !!
                // it should be an audiobook, but we'll still check
                if(item instanceof AudioBook)
                {
                    ((AudioBook)item).getAuthor().setLastName(lastName);
                    System.out.println(item);
                    System.out.println("\nAuthor's last name was changed!");
                }
                else
                {
                    System.out.println(item);
                    System.out.println("\nCould not change the author's name!");
                    System.out.println("The selected item isn't a valid audiobook");
                }
                break;
            }
            case 5:
            {
                Format format = Format.MP3;
                int formatIndex;
                System.out.println("\nChoose new audiobook format: ");
                System.out.println("1) mp3");
                System.out.println("2) wma");
                System.out.println("3) flac");
                formatIndex = in.nextInt();
                Menu.clearScreen();
                switch(formatIndex)
                {
                    case 2:
                    {
                        format = Format.WMA;
                        break;
                    }
                    case 3:
                    {
                        format = Format.FLAC;
                        break;
                    }
                }
                // downcast needed!
                // it should be an audiobook, but we'll still check
                if(item instanceof AudioBook)
                {
                    ((AudioBook)item).setFormat(format);
                    System.out.println(item);
                    System.out.println("\nFormat changed!");
                }
                else
                {
                    System.out.println(item);
                    System.out.println("\nCould not change the format!");
                    System.out.println("The selected item isn't a valid audiobook");
                }
                break;
            }
            case 6:
            {
                System.out.println("\nNew duration (in minutes): ");
                int duration = in.nextInt();
                Menu.clearScreen();
                // downcast
                // it should be an audiobook, but we'll still check
                if(item instanceof AudioBook)
                {
                    ((AudioBook)item).setDuration(duration);
                    System.out.println(item);
                    System.out.println("\nDuration changed!");
                }
                else
                {
                    System.out.println(item);
                    System.out.println("\nCould not change the duration!");
                    System.out.println("The selected item isn't a valid audiobook");
                }
                break;
            }
        }
    }

    static void editGame(Scanner in, LendableItem item)
    {
        System.out.println("Edit Game!\n");
        System.out.println(item);
        System.out.println("What would you like edit?");
        System.out.println("1) The title");
        System.out.println("2) The genre");
        System.out.println("3) The company");
        System.out.println("\nYour choice: ");
        int edit = in.nextInt();
        in.nextLine();
        switch(edit)
        {
            case 1:
            {
                System.out.println("\nNew title: ");
                String title = in.nextLine();
                item.setName(title);
                Menu.clearScreen();
                System.out.println(item);
                System.out.println("\nTitle changed!");
                break;
            }
            case 2:
            {
                System.out.println("\nNew genre: ");
                String genre = in.nextLine();
                Menu.clearScreen();
                item.setGenre(genre);
                System.out.println(item);
                System.out.println("\nGenre changed!");
                break;
            }
            case 3:
            {
                System.out.println("\nNew company: ");
                String company = in.nextLine();
                Menu.clearScreen();
                // downcast needed
                // it should be a game, but we'll still check
                if(item instanceof DVDGame)
                {
                    ((DVDGame)item).setCompany(company);
                    System.out.println(item);
                    System.out.println("Company changed!");
                }
                else {
                    System.out.println(item);
                    System.out.println("\nCould not change the company name!");
                    System.out.println("The item is not a game!");
                }
                break;
            }
        }
    }

    static void deleteItem(Scanner in, List<LendableItem> items)
    {
        while(true)
        {
            Menu.clearScreen();
            System.out.println("Delete an item!");
            // show all items
            for(int i = 0; i < items.size(); i++)
            {
                System.out.println("\nItem " + i);
                System.out.println(items.get(i));
            }
            System.out.println("\nWhich item would you like to delete?");
            int itemIndex = in.nextInt();
            // if there is even one copy borrowed we can't delete it
            if(items.get(itemIndex).getAvailable() < items.get(itemIndex).getTotal())
                System.out.println("\nCan't delete an item whose copies are borrowed at the moment!");
            else {
                System.out.println("\nAre you sure you want to permanently delete this item? [0/1]:");
                short response = in.nextShort();
                if(response == 1)
                {
                    items.remove(itemIndex);
                    System.out.println("Item deleted successfully!");
                }
            }
            System.out.println("Would you like to:");
            System.out.println("1) Delete another item");
            System.out.println("2) Go back");
            System.out.println("\nYour choice:");
            short deleteChoice = in.nextShort();
            if(deleteChoice == 2)
                break;
        }
    }

    static void showOverdueUsers(Scanner in, List<Client> clients)
    {
        System.out.println("Clients with overdue items:\n");
        // go through the clients and see which of them have
        // overdue items
        for(Client client : clients)
        {
            int overdue = client.getOverdue();
            if(overdue > 0)
                System.out.println(client.getName() + " has " +
                        overdue + " overdue items!");
        }
        System.out.println("\nAre you ready to go back? [1]:");
        in.next();
        //we go back no matter what
    }

    static void showItemWhereabouts(Scanner in, List<LendableItem> items, List<Client> clients)
    {
        while(true)
        {
            Menu.clearScreen();
            System.out.println("Inquire after the copies of an item:");
            for(int i = 0; i < items.size(); i++)
            {
                System.out.println("\nItem " + i);
                System.out.println(items.get(i));
            }
            System.out.println("\nAfter which item would you like to inquire?");
            int itemIndex = in.nextInt();
            Menu.clearScreen();

            System.out.println("Details of copies of item " + itemIndex + '\n');
            LendableItem item = items.get(itemIndex);
            System.out.println(item);
            System.out.println();

            // go through the client's lending cards and see who has a copy of
            // the item
            for(Client client : clients)
            {
                LocalDate date = item.findItem(client.getLendingCards());
                // if a date was found we show the client + date
                if(date != null)
                {
                    System.out.println(client.getName() + " has a copy, " +
                            "which should be brought back on " + date);
                }
            }
            if(item.getAvailable() > 0)
                System.out.println("There are still " + item.getAvailable() +
                    " copies of this item available");
            else
                System.out.println("There are no more copies of this item available");
            System.out.println("\nWould you like to:");
            System.out.println("1) Inquire after another item");
            System.out.println("2) Return");
            short back = in.nextShort();
            if(back == 2)
                break;
        }
    }
}
