package management;

import user.User;
import items.LendableItem;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// used to monitor CRUD actions
// writes them to a CSV file

public abstract class ActivityLog {
    private static final String filePath = "src/main/management/activity.csv";

    public static void writeAction(String action)
    {
        LocalDateTime time = LocalDateTime.now();
        // cast the date time object to a String to write it to file
        String formattedTime = time.format(DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm:ss"));

        try (FileWriter writer = new FileWriter(filePath, true))
        {
            writer.append(action + ", " + formattedTime + '\n');
        }
        catch (Exception e)
        {
            System.out.println("Exception when writing CRUD action to file: " + action);
            System.out.println(e);
        }
    }
}
