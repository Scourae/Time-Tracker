package timetracker.yw.timetracker;

import java.util.Date;

/**
 * Created by Person on 2016-08-23.
 */
public class TimeSegment {
    private Date begin;
    private Date end;

    public TimeSegment(Date begin, Date end) {
        this.begin = begin;
        this.end = end;
    }

    public TimeSegment(TimeSegment copy) {
        this(copy.getBegin(), copy.getEnd());
    }

    public boolean onDate(Date date) {
        // TODO test this
        if ((date.equals(end))||(date.equals(begin))) {
            return true;
        }
        else if ((date.after(begin))&&(date.before(end))) {
            return true;
        }
        return false;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getBegin() {
        return this.begin;
    }

    public Date getEnd() {
        return this.end;
    }
}
