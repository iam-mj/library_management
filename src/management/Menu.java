package management;

import user.Client;
import items.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Menu { // I should make it singleton

    // the single reference to a Menu object
    private static Menu menu;

    // private constructor
    private Menu() {}

    // factory method
    public static Menu getMenu() {
        if(menu == null)
            menu = new Menu();
        return menu;
    }

    protected static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void showMenu(List<Client> clients, List<LendableItem> items){
        Scanner in = new Scanner(System.in);

        boolean insideMenu = true;

        while(insideMenu)
        {
            clearScreen();
            System.out.println("Welcome to the TellTale Library!");
            System.out.println("Are you:");
            System.out.println("1) A Client?");
            System.out.println("2) A Librarian?");
            System.out.println("3) Looking for a way out?");
            System.out.println("\nYour option: ");
            short persona = in.nextShort();

            // if we're a client
            while(persona == 1)
            {
                clearScreen();
                System.out.println("Hi, client!");
                System.out.println("Would you like to: ");
                System.out.println("1) Sign In");
                System.out.println("2) Log In");
                System.out.println("3) Return");
                System.out.println("\nYour option: ");
                short signedIn = in.nextShort();
                in.nextLine(); // consume the \n character left
                clearScreen();

                // if a client wants to create an account
                if (signedIn == 1)
                {
                    ClientService.signInClient(in, clients);
                    continue;
                }

                // if a client wants to log in
                while(signedIn == 2)
                {
                    Client currentClient = ClientService.logInClient(in, clients);

                    // if we didn't find the client
                    if(currentClient == null)
                    {
                        System.out.println("\nInvalid login... Would you like to try again? [0/1]: ");
                        short goBack = in.nextShort();
                        if(goBack == 0)
                            break;
                        continue;
                    }

                    boolean loggedIn = true;
                    while(loggedIn)
                    {
                        clearScreen();
                        System.out.println("Hello, " + currentClient.getName() + "!");
                        System.out.println("Would you want to:");
                        System.out.println("1) See all the products");
                        System.out.println("2) See the items you have borrowed");
                        System.out.println("3) Edit your profile");
                        System.out.println("4) Delete your profile");
                        System.out.println("5) Return");
                        System.out.println("6) Exit");
                        System.out.println("\nYour choice: ");
                        short clientChoice = in.nextShort();
                        clearScreen();

                        switch(clientChoice)
                        {
                            // we want to see the items
                            case 1: {
                                ClientService.browseItems(in, currentClient, items, clients);
                                break;
                            }
                            // check the items the client borrowed
                            case 2:
                            {
                                ClientService.seeLentItems(in, currentClient);
                                break;
                            }
                            //edit your profile
                            case 3:
                            {
                                ClientService.editProfile(in, currentClient);
                                break;
                            }
                            // if we want to delete our profile
                            case 4:
                            {
                                int deleteResult = ClientService.deleteProfile(in, currentClient, clients);
                                // we deleted our account
                                if(deleteResult > 1)
                                {
                                    persona = 0;
                                    signedIn = 0;
                                    loggedIn = false;
                                }
                                // we also want to exit
                                if(deleteResult == 3)
                                    persona = 3;

                                break;
                            }
                            // if we want to return
                            case 5:
                            {
                                loggedIn = false;
                                signedIn = 0;
                                break;
                            }
                            // if we want to exit
                            case 6:
                            {
                                loggedIn = false;
                                signedIn = 0;
                                persona = 3;
                                break;
                            }
                        }
                    }
                }
                if(signedIn == 3)
                {
                    persona = 0;
                }
            }

            // if we're a librarian
            while(persona == 2) {
                clearScreen();
                System.out.println("Hi librarian!");
                System.out.println("Would you like to:");
                System.out.println("1) Add an item");
                System.out.println("2) Edit an item");
                System.out.println("3) Delete an item");
                System.out.println("4) See the users with overdue items");
                System.out.println("5) Check an item's whereabouts");
                System.out.println("6) Return");
                System.out.println("7) Exit");
                System.out.println("\nYour choice: ");
                short librarianChoice = in.nextShort();
                clearScreen();

                switch(librarianChoice)
                {
                    // if we want to add an item
                    case 1:
                    {
                        System.out.println("Add an item!\n");
                        System.out.println("Would you like to add:");
                        System.out.println("1) A Book");
                        System.out.println("2) An Audiobook");
                        System.out.println("3) A DVD Game");
                        System.out.println("\nYour choice: ");
                        short addChoice = in.nextShort();
                        in.nextLine(); //consume the \n character left

                        // if we want to add a book
                        while(addChoice == 1)
                        {
                            clearScreen();
                            addChoice = LibrarianService.addBook(in, items);
                            if(addChoice > 1)
                                addChoice = -1; // so it would not get in another while
                        }

                        // if we want to add an audiobook
                        while(addChoice == 2)
                        {
                            clearScreen();
                            addChoice = LibrarianService.addAudioBook(in, items);
                            if(addChoice > 1)
                                addChoice = -1; // so it would not get in another while
                        }

                        // if we want to add a game
                        while(addChoice == 3)
                        {
                            clearScreen();
                            addChoice = LibrarianService.addGame(in, items);
                            if(addChoice > 1)
                                addChoice = -1; // so it would not get in another while
                        }
                        break;
                    }
                    // if we want to edit an item
                    case 2: {
                        LibrarianService.editItem(in, items);
                        break;
                    }
                    //if we want to delete an item
                    case 3:
                    {
                        LibrarianService.deleteItem(in, items);
                        break;
                    }
                    // if we want to see the users with overdue items
                    case 4:
                    {
                        LibrarianService.showOverdueUsers(in, clients);
                        break;
                    }
                    // if we want to see who has the copies of an item
                    // and when each of them should be brought back
                    case 5:
                    {
                        LibrarianService.showItemWhereabouts(in, items, clients);
                        break;
                    }
                    // if we want to return
                    case 6:
                    {
                        persona = -1;
                        break;
                    }
                    // if we want to exit
                    case 7:
                    {
                        persona = 3;
                        break;
                    }
                }
            }

            // if you want to quit the program
            if(persona == 3)
            {
                insideMenu = false;
            }

            // wrong choice
            if(persona > 3) {
                System.out.println("\nInvalid choice! Try again: ");
            }
        }
        // when you went outside
        clearScreen();
        System.out.println("Have a nice day reading! <3");

    }

}
