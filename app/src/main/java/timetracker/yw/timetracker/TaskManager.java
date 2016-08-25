package timetracker.yw.timetracker;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskManager {
    public List<Task> mTasks;

    public TaskManager() {
        mTasks = new ArrayList<>();
    }

    public void getTasksFromFile() {
        // TODO: read task from file
    }

    public List<Task> getTaskByDate(Date date) {
        List<Task> result = new ArrayList<>();
        for (int i = 0; i < mTasks.size(); i++) {
            Task t = mTasks.get(i).getSegmentsByDate(date);
            if (t != null) {
                result.add(t);
            }
        }
        return result;
    }
}
