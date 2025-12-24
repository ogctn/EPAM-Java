package com.epam.rd.autotasks.sprintplanning.tickets;

public class UserStory extends Ticket {

    private UserStory[] _dependencyArr = null;

    public UserStory(int id, String name, int estimate, UserStory... dependsOn) {
        super(id, name, estimate);
        if (dependsOn == null)
            _dependencyArr = new UserStory[0];
        else
            _dependencyArr = java.util.Arrays.copyOf(dependsOn, dependsOn.length);
    }

    @Override
    public void complete() {
        for (int i = 0; i < getDependencies().length; i++) {
            if (getDependencies()[i].isCompleted() == false)
                return;
        }
        if (isCompleted() == false)
            switchStatus();
    }

    public UserStory[] getDependencies() {
        return (java.util.Arrays.copyOf(_dependencyArr, _dependencyArr.length));
    }

    @Override
    public String toString() {
        return ("[US " + getId() + "] " + getName());
    }
}
