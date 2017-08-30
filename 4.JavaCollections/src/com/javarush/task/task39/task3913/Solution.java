package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("c:/logs/"));
        System.out.println(logParser.getNumberOfUniqueIPs(null, new Date()));
        System.out.println(logParser.getUniqueIPs(null, new Date()));
        System.out.println(logParser.getIPsForUser("Amigo", null, new Date()));
        System.out.println(logParser.getIPsForEvent(Event.DONE_TASK, null, new Date()));
        System.out.println(logParser.getIPsForStatus(Status.OK, null, new Date()));

        System.out.println(logParser.getAllUsers());
        System.out.println(logParser.getNumberOfUsers(null, new Date(2013 - 1900, 1, 1)));
        System.out.println(logParser.getNumberOfUserEvents("Amigo", null, new Date(2032 - 1900, 1, 1)));
        System.out.println(logParser.getUsersForIP("127.0.0.1", null, new Date(2013 - 1900, 1, 1)));
        System.out.println(logParser.getLoggedUsers(null, null));
        System.out.println(logParser.getDownloadedPluginUsers(null, null));
        System.out.println(logParser.getWroteMessageUsers(null, null));
        System.out.println(logParser.getSolvedTaskUsers(null, null));
        System.out.println(logParser.getSolvedTaskUsers(null, null, 1));
        System.out.println(logParser.getDoneTaskUsers(null, null));
        System.out.println(logParser.getDoneTaskUsers(null, null, 15));

        System.out.println(logParser.getDatesForUserAndEvent("Vasya Pupkin", Event.SOLVE_TASK, null, null));
        System.out.println(logParser.getDatesWhenSomethingFailed(null, null));
        System.out.println(logParser.getDatesWhenErrorHappened(null, null));
        System.out.println(logParser.getDateWhenUserLoggedFirstTime("Amigo", null, null));
        System.out.println(logParser.getDateWhenUserSolvedTask("Vasya Pupkin", 1, null, null));
        System.out.println(logParser.getDateWhenUserDoneTask("Vasya Pupkin", 15, null, null));
        System.out.println(logParser.getDatesWhenUserWroteMessage("Amigo", null, null));
        System.out.println(logParser.getDatesWhenUserDownloadedPlugin("Amigo", null, null));

        System.out.println(logParser.getNumberOfAllEvents(null, new Date(2014 - 1900, 0, 1)));
        System.out.println(logParser.getAllEvents(null, new Date(2014 - 1900, 0, 1)));
        System.out.println(logParser.getEventsForIP("127.0.0.1", null, new Date(2014 - 1900, 0, 1)));
        System.out.println(logParser.getEventsForUser("Amigo", null, new Date(2014 - 1900, 0, 1)));
        System.out.println(logParser.getFailedEvents(null, new Date(2014 - 1900, 0, 1)));
        System.out.println(logParser.getErrorEvents(null, new Date(2014 - 1900, 0, 1)));

        System.out.println(logParser.getNumberOfAttemptToSolveTask(1, null, null));
        System.out.println(logParser.getNumberOfSuccessfulAttemptToSolveTask(15, null, null));
        System.out.println(logParser.getAllSolvedTasksAndTheirNumber(null, null));
        System.out.println(logParser.getAllDoneTasksAndTheirNumber(null, null));
    }
}