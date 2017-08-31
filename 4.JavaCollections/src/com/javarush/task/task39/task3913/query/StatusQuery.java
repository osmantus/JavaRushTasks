package com.javarush.task.task39.task3913.query;

import com.javarush.task.task39.task3913.Event;
import com.javarush.task.task39.task3913.Status;

import java.util.Date;
import java.util.Set;

/**
 * Created by ua053202 on 31.08.2017.
 */
public interface StatusQuery {
    Set<Status> getAllUniqueStatuses();

    Set<Status> getStatusesForIP(String ip);

    Set<Status> getStatusesForUser(String user);

    Set<Status> getStatusesForDate(Date date);

    Set<Status> getStatusesForEvent(Event event);
}
