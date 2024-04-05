package management;

import user.Client;
import items.LendableItem;
import items.LendingCard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

public abstract class ClientService {
    static void signInClient(Scanner in, List<Client> clients)
    {
        System.out.println("Create your account:");
        System.out.println();
        String firstName, lastName, email, password, confirmPassword;
        System.out.println("Your first name: ");
        firstName = in.nextLine();
        System.out.println("Your last name: ");
        lastName = in.nextLine();
        System.out.println("Your email: ");
        email = in.nextLine();
        System.out.println("Your password: ");
        password = in.nextLine();
        System.out.println("Confirm your password: ");
        confirmPassword = in.nextLine();
        // while the confirmation isn't the same as the password
        while(confirmPassword.compareTo(password) != 0)
        {
            System.out.println("The passwords do not match! Try again: ");
            confirmPassword = in.nextLine();
        }
        Client newClient = new Client(firstName, lastName, email, password);
        clients.add(newClient);
        Menu.clearScreen();
    }

    static Client logInClient(Scanner in, List<Client> clients)
    {
        Menu.clearScreen();
        System.out.println("Log In: ");
        System.out.println();
        String email, password;
        System.out.println("Email: ");
        email = in.nextLine();
        System.out.println("Password: ");
        password = in.nextLine();
        Client currentClient = null;

        // search for the client in the client lists
        for (Client client : clients) {
            if (client.getEmail().compareTo(email) == 0 &&
                    client.getPassword().compareTo(password) == 0) {
                currentClient = client; // reference
                break;
            }
        }

        return currentClient;
    }

    static void browseItems(Scanner in, Client currentClient,
                            List<LendableItem> items, List<Client> clients)
    {
        while(true)
        {
            for (int i = 0; i < items.size(); i++) {
                System.out.println("Index: " + i);
                System.out.println(items.get(i));
            }
            System.out.println("Would you like to: ");
            System.out.println("1) Check an item's availability");
            System.out.println("2) Return");
            System.out.println("\nYour choice: ");
            short checkout = in.nextShort();

            if(checkout == 2)
                break;

            System.out.println("\nWhich item would you like to check out?");
            int itemIndex = in.nextInt();
            Menu.clearScreen();
            System.out.println("Check out an item!");
            LendableItem item = items.get(itemIndex);
            System.out.println(item);
            System.out.println();

            // item availability
            int available = item.getAvailable();
            if(available > 0)
            {
                System.out.println("There are " + available + " copies of this item available!");
                System.out.println("Would you like to:");
                System.out.println("1) Borrow it");
                System.out.println("2) Return");
                System.out.println("\nYour choice:");
                checkout = in.nextShort();

                if(checkout == 2)
                    continue;

                Menu.clearScreen();
                boolean borrow = currentClient.borrow(item);
                System.out.println(item);

                // check if the borrowing process was successful
                if(borrow)
                    System.out.println("\nItem borrowed!");
                else
                    System.out.println("\nYou already have this item borrowed!");

                System.out.println("Would you like to:");
                System.out.println("1) Borrow another item");
                System.out.println("2) Return");
                System.out.println("\nYour choice: ");
                checkout = in.nextShort();

                if(checkout == 2)
                    break;
            }
            else
            {
                System.out.println("There are no copies of this item available at the moment!");
                System.out.println("Would you like to:");
                System.out.println("1) Find out when a copy should be back?");
                System.out.println("2) Return");
                System.out.println("\nYour choice: ");
                checkout = in.nextShort();

                if(checkout == 2)
                    continue;

                // find out when a copy should be back
                whenBack(clients, item);
                System.out.println("\nWould you like to continue looking through the available items? [0/1]: ");
                checkout = in.nextShort();
                if(checkout == 0)
                    break;
            }
        }
    }

    static void whenBack(List<Client> clients, LendableItem item)
    {
        List<LendingCard> lendingCards = new ArrayList<LendingCard>();
        // get all lending cards
        for(Client client : clients)
            lendingCards.addAll(client.getLendingCards());
        int difference = (int) DAYS.between(LocalDate.now(), item.getBringBackDate(lendingCards));
        System.out.println("A copy should be back in " + difference + " days");
    }

    static void seeLentItems(Scanner in, Client currentClient)
    {
        System.out.println("Your Lending Cards:");

        // we'll convert it to an array to show the index
        Object[] cards = (currentClient.getLendingCards()).toArray();
        for(int i = 0; i < cards.length; i++)
        {
            System.out.println("Lending Card " + i + ": ");
            System.out.println(cards[i]);
            System.out.println();
        }

        if(cards.length == 0)
        {
            System.out.println("\n You have no items borrowed! \n");
        }

        // check how many overdue items the client has
        int overdue = currentClient.getOverdue();
        System.out.println("You have " + overdue + " overdue items!");
        System.out.println("Would you like to: ");
        System.out.println("1) Bring back an item");
        System.out.println("2) Return");
        System.out.println("\nYour choice: ");
        short bringBack = in.nextShort();

        while(bringBack == 1)
        {
            System.out.println("\nWhich item would you like to bring back?");
            int itemIndex = in.nextInt();
            while (itemIndex < 0 || itemIndex >= cards.length)
            {
                System.out.println("\nInvalid item index! Try again: ");
                itemIndex = in.nextInt();
            }
            // return the item
            currentClient.returnItem((LendingCard) cards[itemIndex]);
            System.out.println("Item returned!");
            System.out.println("Would you like to: ");
            System.out.println("1) Bring back another item");
            System.out.println("2) Return");
            System.out.println("\nYour choice: ");
            bringBack = in.nextShort();
        }
    }

    static void editProfile(Scanner in, Client currentClient)
    {
        boolean editing = true;
        while(editing)
        {
            System.out.println("Edit your profile!");
            System.out.println(currentClient);
            System.out.println();
            System.out.println("Would you like to change:");
            System.out.println("1) Your first name");
            System.out.println("2) Your last name");
            System.out.println("3) Your email");
            System.out.println("4) Your password");
            System.out.println("\nYour choice:");
            short change = in.nextShort();
            in.nextLine(); // consume the \n character left

            switch(change)
            {
                case 1:
                {
                    System.out.println("\nYour new first name: ");
                    String newFirstName = in.nextLine();
                    currentClient.setFirstName(newFirstName);
                    break;
                }
                case 2:
                {
                    System.out.println("\nYour new last name: ");
                    String newLastName = in.nextLine();
                    currentClient.setLastName(newLastName);
                    break;
                }
                case 3:
                {
                    System.out.println("\nYour new email: ");
                    String newEmail = in.nextLine();
                    currentClient.setEmail(newEmail);
                    break;
                }
                case 4:
                {
                    System.out.println("\nYour new password: ");
                    String newPassword = in.nextLine();
                    currentClient.setPassword(newPassword);
                    break;
                }
            }
            Menu.clearScreen();
            System.out.println("Change registered!");
            System.out.println("Would you like to:");
            System.out.println("1) Edit your profile some more");
            System.out.println("2) Go back");
            System.out.println("\nYour choice: ");
            change = in.nextShort();
            if(change != 1)
                editing = false;
            Menu.clearScreen();
        }
    }

    static int deleteProfile(Scanner in, Client currentClient, List<Client> clients)
    {
        System.out.println("Delete your profile!");
        System.out.println("\nAre you sure you want to permanently delete your profile? [0/1]:");
        short delete = in.nextShort();
        if(delete == 1)
        {
            // if the client still has things burrowed, we won't allow him to delete his account
            if(!currentClient.getLendingCards().isEmpty())
                System.out.println("You can't delete your profile until you don't return all your items!");
            else {
                // delete the client from the list
                clients.remove(currentClient);
                Menu.clearScreen();
                System.out.println("Your account has been deleted!");
                System.out.println("Would you like to:");
                System.out.println("1) Return to the main menu");
                System.out.println("2) Exit the app");
                System.out.println("\nYour choice: ");
                short exit = in.nextShort();

                if(exit == 2)
                    return 3;

                return 2;
            }
        }
        return 1;
    }
}
