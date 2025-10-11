package com.epam.rd.autotasks;

public class CompleteByRequestTask implements Task {
    private boolean isFinished = false;
    private boolean willBeCompleted = false;

    @Override
    public void execute() {
        if (getWillBeCompleted() == true)
            setFinished();
    }

    private void    setFinished() { isFinished = true; }

    @Override
    public boolean  isFinished() { return (isFinished); }

    public void     complete() { setWillBeCompleted(); }

    private boolean getWillBeCompleted() { return (willBeCompleted); }
    private void    setWillBeCompleted() { willBeCompleted = true; }


}
