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

    public boolean onDate(Date timeBegin, Date timeEnd) {
        if ((timeEnd.before(end))&&(timeBegin.after(begin))) {
            return true;
        }
        else if ((timeBegin.before(begin))&&(timeEnd.after(begin))) {
            return true;
        }
        else if ((timeBegin.before(end))&&(timeEnd.after(end))) {
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
