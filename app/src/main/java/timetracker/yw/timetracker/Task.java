package timetracker.yw.timetracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTimeList(List<TimeSegment> timeList) {
        this.mTimeList = timeList;
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

    public List<TimeSegment> getTimeList() {
        return this.mTimeList;
    }

    public Task getSegmentsByDate(Date date) {
        List<TimeSegment> timeList = new ArrayList<>();
        for (int i = 0; i < mTimeList.size(); i++) {
            if (mTimeList.get(i).onDate(date)) {
                timeList.add(new TimeSegment(mTimeList.get(i)));
            }
        }
        if (timeList.size() == 0) {
            return null;
        }
        Task result = new Task(name, description);
        result.setId(id);
        result.setTimeList(timeList);
        return result;
    }
}
