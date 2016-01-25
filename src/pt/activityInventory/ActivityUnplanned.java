package pt.activityInventory;

import java.util.Date;

public class ActivityUnplanned {
    private final boolean unplanned;
    private final Date until;

    public ActivityUnplanned(boolean unplanned, Date until) {
        this.unplanned = unplanned;
        this.until = until;
    }

    public boolean isUnplanned() {
        return unplanned;
    }

    public Date getUntil() {
        return until;
    }

    @Override
    public String toString() {
        return (unplanned) ? "U" : "" + " " + until.toString();
    }
}
