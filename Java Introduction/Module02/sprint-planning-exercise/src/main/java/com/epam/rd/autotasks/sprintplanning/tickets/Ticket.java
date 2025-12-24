package com.epam.rd.autotasks.sprintplanning.tickets;

public class Ticket {

    private int _id = 0;
    private String _name = null;
    private int _estimate = 0;
    protected boolean _isCompleted = false;

    public Ticket(int id, String name, int estimate) {
        this._id = id;
        this._name = name;
        this._estimate = estimate;
        this._isCompleted = false;
    }

    public int getId() {
        return (this._id);
    }

    public String getName() {
        return (this._name);
    }

    public boolean isCompleted() {
        return (this._isCompleted);
    }

    public void switchStatus() {
        this._isCompleted = !this._isCompleted;
    }


    public void complete() {
        if (isCompleted() == false)
            switchStatus();
    }

    public int getEstimate() {
        return (this._estimate);
    }
}
