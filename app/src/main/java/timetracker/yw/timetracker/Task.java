package timetracker.yw.timetracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Person on 2016-05-03.
 */
public class Task {

    private String name;
    private String description;
    private List<TimeSegment> mTimeList;
    private UUID id;

    public Task(String name, String description) {
        mTimeList = new ArrayList<>();
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

    public void addTime(Date begin, Date end) {
        mTimeList.add(new TimeSegment(begin, end));
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
