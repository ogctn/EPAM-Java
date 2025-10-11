package com.epam.rd.autotasks;

public class CountDownTask implements Task{
    private boolean isFinished = false;
    private int counter;

    public CountDownTask(int value) {
        if (value > 0)
            counter = value;
        else {
            counter = 0;
            setFinished();
        }
    }

    public int getValue() { return (counter); }

    @Override
    public void execute() {
        if (getValue() <= 0)
            return;

        if (getValue() > 0) {
            decrementCounter();
            if (getValue() == 0)
                setFinished();
        }
    }

    void    setFinished() { isFinished = true; }

    @Override
    public boolean isFinished() { return (isFinished); }

    private void decrementCounter() { --counter; }

}
