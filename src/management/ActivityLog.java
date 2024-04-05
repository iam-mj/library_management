package management;

import user.User;
import items.LendableItem;

import java.time.LocalDateTime;

// intended to be used to monitor both the librarians' and the clients' activity,
// but I couldn't find the time to use it before the first project checkpoint

public class ActivityLog {
    User user;
    LendableItem item;
    LocalDateTime date; // will be kept in the format: "dd/mm/yyyy hh:mm"
    String action;

    ActivityLog()
    {
        user = null;
        item = null;
        date = null;
        action = "";
    }
    ActivityLog(User user, LendableItem item, LocalDateTime date, String action)
    {
        this.user = user;
        this.item = item;
        this.date = date;
        this.action = action;
    }
}
