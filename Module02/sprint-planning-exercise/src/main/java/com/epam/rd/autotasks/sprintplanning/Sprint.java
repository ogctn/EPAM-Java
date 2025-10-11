package com.epam.rd.autotasks.sprintplanning;

import com.epam.rd.autotasks.sprintplanning.tickets.Bug;
import com.epam.rd.autotasks.sprintplanning.tickets.Ticket;
import com.epam.rd.autotasks.sprintplanning.tickets.UserStory;

import java.util.Arrays;

public class Sprint {

    private final int _timeCapacity;
    private final int _ticketsLimit;
    private Ticket[] _ticketArray = null;

    public Sprint(int capacity, int ticketsLimit) {
        _timeCapacity = capacity;
        _ticketsLimit = ticketsLimit;
        _ticketArray = new Ticket[0];
    }

    public boolean addUserStory(UserStory userStory) {
        if (!acceptTicket(userStory) || !isValidDependent(userStory))
            return (false);
        return (addTicket(userStory));
    }

    public boolean addBug(Bug bugReport) {
        if (!acceptTicket(bugReport))
            return (false);
        return (addTicket(bugReport));
    }

    private boolean acceptTicket(Ticket ticket) {
        return  (!(ticket == null || ticket.isCompleted() ||
                getTotalEstimate() + ticket.getEstimate() > _timeCapacity ||
                isFull()));
    }

    private boolean isInArray(Ticket toFind, Ticket[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == toFind)
                return (true);
        }
        return (false);
    }

    private boolean isValidDependent(UserStory us) {
        Ticket[] deps = us.getDependencies();
        Ticket[] sprintTickets = getTickets();

        if (sprintTickets.length == 0)
            return (deps.length == 0);

        //  sprintTicketlar var
        //      depler var
        //      depler yok
        for (int i = 0; i < deps.length; i++) {
            if (!isInArray(deps[i], sprintTickets))
                return (false);
        }
        return (!isInArray(us, sprintTickets));
    }

    private boolean addTicket(Ticket ticket) {
        Ticket[] newArr;
        if (getTicketCount() ==  _getArr().length)
            newArr = java.util.Arrays.copyOf(getTickets(), getTickets().length + 1);
        else
            newArr = _getArr();

        for (int i = 0; i < newArr.length; i++) {
            if (newArr[i] == null) {
                _ticketArray = newArr;
                return (setTicket(ticket, _ticketArray, i));
            }
        }
        return (false);
    }

    private boolean setTicket (Ticket ticket, Ticket[] arr, int idx) {
        if (ticket == null || idx < 0 || idx > arr.length - 1)
            return (false);
        arr[idx] = ticket;
        return (true);
    }

    public int getTotalEstimate() {
        Ticket[] tmp = getTickets();
        int total = 0;
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] != null)
                total += tmp[i].getEstimate();
        }
        return (total);
    }

    public Ticket[] getTickets() {
        return (java.util.Arrays.copyOf(_ticketArray, _ticketArray.length));
    }

    private int getTicketCount() {
        int k = 0;
        Ticket[] tmp = getTickets();
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] != null)
                k++;
        }
        return (k);
    }

    private Ticket[] _getArr() {
        return (_ticketArray);
    }

    private int getTicketLimit() {
        return (_ticketsLimit);
    }

    private boolean isFull() {
        return (getTicketCount() == getTicketLimit());
    }
}
