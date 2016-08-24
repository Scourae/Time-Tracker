package timetracker.yw.timetracker;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends Fragment {

    // views
    private View mView;
    private ListView mListView;
    private ToDoAdapter mAdapter;
    // task storage
    private List<Task> mToDoList;
    // for item button visibility
    private int previousHighlightPosition;

    public ToDoFragment() {
        mToDoList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        previousHighlightPosition = -1;
        mView = inflater.inflate(R.layout.todo_fragment, container, false);
        mListView = (ListView) mView.findViewById(R.id.todo_tasks);
        mAdapter = new ToDoAdapter(getActivity(), mToDoList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Button editButton = (Button) view.findViewById(R.id.todo_item_edit);
                editButton.setVisibility(View.VISIBLE);
                Button deleteButton = (Button) view.findViewById(R.id.todo_item_finish);
                deleteButton.setVisibility(View.VISIBLE);
                previousHighlightPosition = position;
                mAdapter.notifyDataSetChanged();
            }
        });
        Button addTaskButton = (Button) mView.findViewById(R.id.todo_add);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                AddTaskDialogue addTaskDialogue = new AddTaskDialogue();
                addTaskDialogue.show(fragmentTransaction, "dialog");
            }
        });
        return mView;
    }

    public void addTask(String name,String description) {
        Task newTask = new Task(name, description);
        mToDoList.add(newTask);
        mAdapter.notifyDataSetChanged();
    }

    public void editTask(String name, String description, int positionToInner) {
        mToDoList.get(positionToInner).setName(name);
        mToDoList.get(positionToInner).setDescription(description);
        mAdapter.notifyDataSetChanged();
    }

    public Task getTaskById(String id) {
        for (int i = 0; i < mToDoList.size(); i++) {
            if (mToDoList.get(i).getID().equals(id)) {
                return mToDoList.get(i);
            }
        }
        return null;
    }

    public Task getTaskByPosition(int position) {
        return mToDoList.get(position);
    }

    public List<Task> getTasks() {
        return mToDoList;
    }

    private class ToDoAdapter extends ArrayAdapter<Task> {

        private Context mContext;
        private List<Task> mToDoList;

        public ToDoAdapter(Context context, List<Task> toDoList) {
            super(context, -1, toDoList);
            this.mContext = context;
            this.mToDoList = toDoList;
        }

        class ViewHolderItem {
            TextView titleView;
            Button editButton;
            Button deleteButton;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolderItem viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolderItem();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.todo_task_item, parent, false);
                viewHolder.titleView = (TextView) convertView.findViewById(R.id.todo_item_title);
                viewHolder.editButton = (Button) convertView.findViewById(R.id.todo_item_edit);
                viewHolder.deleteButton = (Button) convertView.findViewById(R.id.todo_item_finish);
                convertView.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolderItem) convertView.getTag();
            }
            viewHolder.titleView.setText(mToDoList.get(position).getName());
            if (position != previousHighlightPosition) {
                viewHolder.editButton.setVisibility(View.GONE);
                viewHolder.deleteButton.setVisibility(View.GONE);
            }
            else {
                if (!viewHolder.editButton.hasOnClickListeners()) {
                    final int pos = position;
                    viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString("name", mToDoList.get(pos).getName());
                            bundle.putString("description", mToDoList.get(pos).getDescription());
                            bundle.putInt("position", pos);
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            AddTaskDialogue addTaskDialogue = new AddTaskDialogue();
                            addTaskDialogue.setArguments(bundle);
                            addTaskDialogue.show(fragmentTransaction, "dialog");
                        }
                    });
                }
                if (!viewHolder.deleteButton.hasOnClickListeners()) {
                    final int pos = position;
                    viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mToDoList.remove(pos);
                            notifyDataSetChanged();
                        }
                    });
                }
            }
            return convertView;
        }
    }
}
