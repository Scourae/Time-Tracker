package timetracker.yw.timetracker;

import java.util.Date;

public class NamedTimeSegment extends TimeSegment {
    private String name;

    public NamedTimeSegment(String name, Date begin, Date end) {
        super(begin, end);
        this.name = name;
    }

    public NamedTimeSegment(String name, TimeSegment segment) {
        super(segment);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
