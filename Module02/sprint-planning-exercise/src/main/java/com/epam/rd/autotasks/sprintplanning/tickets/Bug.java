package com.epam.rd.autotasks.sprintplanning.tickets;

import com.epam.rd.autotasks.sprintplanning.tickets.UserStory;
import com.epam.rd.autotasks.sprintplanning.tickets.*;


public class Bug extends Ticket {

    private UserStory _relatedTo = null;

    public static Bug createBug(int id, String name, int estimate, UserStory userStory) {
        if (userStory == null || userStory.isCompleted() == false)
            return (null);
        return (new Bug(id, name, estimate, userStory));
    }

    private Bug(int id, String name, int estimate, UserStory userStory) {
        super(id, name, estimate);
        _relatedTo = userStory;
    }

    @Override
    public String toString() {
        return ("[Bug " + getId()+ "] " +
                _relatedTo.getName() + ": " +
                getName());
    }
}
