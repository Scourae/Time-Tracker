package timetracker.yw.timetracker;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Person on 2016-05-03.
 */
public class Task {

    private String name;
    private String description;
    private Date begin;
    private Date end;
    private UUID id;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = UUID.randomUUID();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void BeginTime(Date begin) {
        this.begin = begin;
    }

    public void EndTime(Date end) {
        this.end = end;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getID() {
        return id.toString();
    }
}
