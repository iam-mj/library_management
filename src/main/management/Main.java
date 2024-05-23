package management;

import java.util.ArrayList;
import java.util.List;

import database.OracleDB;
import database.dao.*;
import management.Menu;
import user.Client;
import items.*;

public class Main {
    public static void main(String[] args) {
        Menu menu = Menu.getMenu();

        List<Client> clients = ClientDAO.readAll();
        List<LendableItem> items = new ArrayList<LendableItem>();

        // get all the items
        items.addAll(BookDAO.readAll());
        items.addAll(AudioBookDAO.readAll());
        items.addAll(DVDGameDAO.readAll());

        menu.showMenu(clients, items);

        OracleDB.closeConnection();
    }
}