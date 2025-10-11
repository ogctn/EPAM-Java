package com.epam.rd.autotasks;

public class TaskCarousel {

    private final int cap;
    private int size;
    private final Task[] _taskArr;
    private int idx = 0;

    public TaskCarousel(int capacity) {
        cap = capacity;
        size = 0;
        _taskArr = new Task[capacity];
    }

    private boolean checkTask(Task task) {
        return (task != null && !task.isFinished() && !isFull());
    }
    public boolean addTask(Task task) {
        if (!checkTask(task))
            return (false);

        Task[] tasks = getTaskArr();

        for (int i = 0; i < getCap(); i++) {
            if (tasks[i] == null) {
                setTask(task, i);
                incrementSize();
                return (true);
            }
        }
        return (false);
    }

    private int getNextIdx(int i) { return ((i == getCap() - 1) ? 0 : i + 1); }

    public boolean execute() {
        if (isEmpty())
            return (false);

        Task[] tasks = getTaskArr();

        int i;
        while (true) {
            i = getIdx();
            if (tasks[i] != null) {
                tasks[i].execute();
                if (tasks[i].isFinished()) {
                    setTask(null, i);
                    decrementSize();
                }
                setIdx(getNextIdx(i));
                return (true);
            }
            setIdx(getNextIdx(i));
        }
    }

    public boolean isFull() { return (getSize() == cap); }

    public boolean isEmpty() { return (getUncompletedLen() == 0) ; }

    private int getCap() { return (cap); }
    private int getIdx() { return (idx); }
    private void setIdx(int val) { idx = val; }

    private int getSize() { return (size); }
    private void incrementSize() { ++size; }
    private void decrementSize() { --size; }

    private Task[] getTaskArr() { return (_taskArr); }

    private int getUncompletedLen() {
        Task[] tasks = getTaskArr();

        int len = 0;
        for (int i = 0; i < getCap(); i++) {
            if (tasks[i] != null && !tasks[i].isFinished())
                ++len;
        }
        return (len);
    }

    private void setTask(Task task, int idx) {
        Task[] tasks = getTaskArr();

        if (idx >= 0 && idx < getCap())
            tasks[idx] = task;
    }

}
