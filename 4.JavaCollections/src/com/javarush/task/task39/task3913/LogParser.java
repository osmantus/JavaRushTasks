package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery, StatusQuery {

    private Path logDir;
    private Set<Record> setOfLogObjects;

    private DateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private class Record
    {
        private String ipAddress;
        private String userName;
        private Date date;
        private Event event;
        private int taskNumber;
        private Status status;

        public int getTaskNumber() {
            return taskNumber;
        }
        public void setTaskNumber(int taskNumber) {
            this.taskNumber = taskNumber;
        }
        public String getIpAddress() {
            return ipAddress;
        }
        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public Date getDate() {
            return date;
        }
        public void setDate(Date date) {
            this.date = date;
        }
        public Event getEvent() {
            return event;
        }
        public void setEvent(Event event) {
            this.event = event;
        }
        public Status getStatus() {
            return status;
        }
        public void setStatus(Status status) {
            this.status = status;
        }
    }

    private List<String> readLogFiles(Path logDir){
        List<String> result = new ArrayList<>();
        if (Files.isDirectory(logDir)) {
            try {
                DirectoryStream<Path> directoryStream = Files.newDirectoryStream(logDir);
                for (Path file: directoryStream) {
                    if (file.getFileName().toString().endsWith(".log")) {
                        BufferedReader bufferedReader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            result.add(line);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Set<Record> parseStringsToRecordObjects(List<String> listOfLogStrings){
        Set<Record> result = new HashSet<>();
        for (String recordString : listOfLogStrings) {
            String[] recordStringArray = recordString.split("\\t");
            Record record = new Record();

            record.setIpAddress(recordStringArray[0]);
            record.setUserName(recordStringArray[1]);
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
            try {
                Date date = dateFormat.parse(recordStringArray[2]);
                record.setDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            record.setEvent(Event.valueOf(recordStringArray[3].split("\\s")[0])) ;
            if (recordStringArray[3].split("\\s").length > 1){
                record.setTaskNumber(Integer.parseInt(recordStringArray[3].split("\\s")[1]));
            }
            record.setStatus(Status.valueOf(recordStringArray[4]));
            result.add(record);
        }
        return result;
    }

    public LogParser(Path logDir){
        this.setOfLogObjects = parseStringsToRecordObjects(readLogFiles(logDir));
    }

    private boolean isDateFromInterval(Date current, Date after, Date before){
        boolean result = false;
        if (after == null)
            after = current;
        if (before == null)
            before = current;
        if ((current.getTime() >= after.getTime()) && (current.getTime() <= before.getTime()))
            result = true;
        return result;
    }

    private List<Record> getLogRecords(String ip, String user, Date date, Event event, Status status, Date from, Date to) {
        List<Record> logRecords = new ArrayList<>();
        for (Record logRecord : setOfLogObjects) {
            int compareAfter = (from != null) ? logRecord.date.compareTo(from) : 1;
            int compareBefore = (to != null) ? logRecord.date.compareTo(to) : -1;
            if ((compareAfter >= 0) && (compareBefore <= 0)
                    && ((ip == null) || logRecord.ipAddress.equals(ip))
                    && ((user == null) || logRecord.userName.equals(user))
                    && ((date == null) || (logRecord.date.compareTo(date) == 0))
                    && ((event == null) || (logRecord.event == event))
                    && ((status == null) || (logRecord.status == status))) {
                logRecords.add(logRecord);
            }
        }
        return logRecords;
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {

        Set<String> ipSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), after, before)) {
                ipSet.add(record.getIpAddress());
            }
        }
        return ipSet.size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {

        Set<String> ipSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), after, before)) {
                ipSet.add(record.getIpAddress());
            }
        }
        return ipSet;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {

        Set<String> ipSet = new HashSet<>();
        String userName = "";
        String[] userNameParts = null;
        List<String> list = null;
        for (Record record : setOfLogObjects) {
            userName = record.getUserName().replaceAll("\\s", " ");
            userNameParts = userName.split(" ");
            list = Arrays.asList(userNameParts);
            if (list.contains(user)) {
                if ((isDateFromInterval(record.getDate(), after, before))) {
                    ipSet.add(record.getIpAddress());
                }
            }
        }
        return ipSet;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {

        Set<String> ipSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if ((isDateFromInterval(record.getDate(), after, before)) && (event.equals(record.getEvent()))) {
                ipSet.add(record.getIpAddress());
            }
        }
        return ipSet;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {

        Set<String> ipSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if ((isDateFromInterval(record.getDate(), after, before)) && (status.equals(record.getStatus()))) {
                ipSet.add(record.getIpAddress());
            }
        }
        return ipSet;
    }

    @Override
    public Set<String> getAllUsers() {

        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            String userName = record.getUserName();
            users.add(userName);
        }
        return users;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {

        int usersNumber = 0;
        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), after, before)) {
                String userName = record.getUserName();
                if (!users.contains(userName)) {
                    users.add(userName);
                    usersNumber++;
                }
            }
        }
        return usersNumber;
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {

        int userEventsNumber = 0;
        Set<Event> eventSet = new HashSet<>();

        for (Record record : setOfLogObjects) {
            Event event = record.getEvent();
            String userName = record.getUserName();
            if (userName.equals(user))
            {
                if (isDateFromInterval(record.getDate(), after, before)) {
                    eventSet.add(event);
                }
            }
        }
        userEventsNumber = eventSet.size();
        return userEventsNumber;
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {

        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (ip.equals(record.getIpAddress()))
            {
                if (isDateFromInterval(record.getDate(), after, before)) {
                    String userName = record.getUserName();
                    users.add(userName);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {

        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), after, before)) {
                if (record.getEvent().compareTo(Event.LOGIN) == 0)
                {
                    String userName = record.getUserName();
                    users.add(userName);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {

        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), after, before)) {
                if (record.getEvent().compareTo(Event.DOWNLOAD_PLUGIN) == 0 && record.getStatus().compareTo(Status.OK) == 0)
                {
                    String userName = record.getUserName();
                    users.add(userName);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {

        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), after, before)) {
                if (record.getEvent().compareTo(Event.WRITE_MESSAGE) == 0 && record.getStatus().compareTo(Status.OK) == 0)
                {
                    String userName = record.getUserName();
                    users.add(userName);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {

        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), after, before)) {
                if (record.getEvent().compareTo(Event.SOLVE_TASK) == 0)
                {
                    String userName = record.getUserName();
                    users.add(userName);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {

        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (record.getTaskNumber() == task) {
                if (isDateFromInterval(record.getDate(), after, before)) {
                    if (record.getEvent().compareTo(Event.SOLVE_TASK) == 0) {
                        String userName = record.getUserName();
                        users.add(userName);
                    }
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {

        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), after, before)) {
                if (record.getEvent().compareTo(Event.DONE_TASK) == 0)
                {
                    String userName = record.getUserName();
                    users.add(userName);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {

        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (record.getTaskNumber() == task) {
                if (isDateFromInterval(record.getDate(), after, before)) {
                    if (record.getEvent().compareTo(Event.DONE_TASK) == 0) {
                        String userName = record.getUserName();
                        users.add(userName);
                    }
                }
            }
        }
        return users;
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {

        Set<Date> dateSet = new HashSet<>();
        Date date;
        for (Record record : setOfLogObjects) {
            String userName = record.getUserName();
            if (userName.equals(user)) {
                if (record.getEvent().compareTo(event) == 0) {
                    date = record.getDate();
                    if (isDateFromInterval(date, after, before)) {
                        dateSet.add(date);
                    }
                }
            }
        }
        return dateSet;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {

        Set<Date> dateSet = new HashSet<>();
        Date date;
        Status status;
        for (Record record : setOfLogObjects) {
            status = record.getStatus();
            if (status.compareTo(Status.FAILED) == 0) {
                date = record.getDate();
                if (isDateFromInterval(date, after, before)) {
                    dateSet.add(date);
                }
            }
        }
        return dateSet;
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {

        Set<Date> dateSet = new HashSet<>();
        Date date;
        Status status;
        for (Record record : setOfLogObjects) {
            status = record.getStatus();
            if (status.compareTo(Status.ERROR) == 0) {
                date = record.getDate();
                if (isDateFromInterval(date, after, before)) {
                    dateSet.add(date);
                }
            }
        }
        return dateSet;
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {

        Date date, minDate = null;
        Event event;
        Status status;
        for (Record record : setOfLogObjects) {
            String userName = record.getUserName();
            if (userName.equals(user)) {
                date = record.getDate();
                if (isDateFromInterval(date, after, before)) {
                    event = record.getEvent();
                    status = record.getStatus();
                    if (event.compareTo(Event.LOGIN) == 0 && status.compareTo(Status.OK) == 0) {
                        if (minDate == null) {
                            minDate = date;
                        }
                        else
                        {
                            if (date.before(minDate))
                                minDate = date;
                        }
                    }
                }
            }
        }
        return minDate;
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {

        Date date, minDate = null;
        Event event;
        Status status;
        for (Record record : setOfLogObjects) {
            String userName = record.getUserName();
            if (userName.equals(user)) {
                if (record.getTaskNumber() == task) {
                    date = record.getDate();
                    if (isDateFromInterval(date, after, before)) {
                        event = record.getEvent();
                        status = record.getStatus();
                        if (event.compareTo(Event.SOLVE_TASK) == 0) {
                            if (minDate == null) {
                                minDate = date;
                            } else {
                                if (date.before(minDate))
                                    minDate = date;
                            }
                        }
                    }
                }
            }
        }
        return minDate;
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {

        Date date, minDate = null;
        Event event;
        Status status;
        for (Record record : setOfLogObjects) {
            String userName = record.getUserName();
            if (userName.equals(user)) {
                if (record.getTaskNumber() == task) {
                    date = record.getDate();
                    if (isDateFromInterval(date, after, before)) {
                        event = record.getEvent();
                        status = record.getStatus();
                        if (event.compareTo(Event.DONE_TASK) == 0) {
                            if (minDate == null) {
                                minDate = date;
                            }
                            else
                            {
                                if (date.before(minDate))
                                    minDate = date;
                            }
                        }
                    }
                }
            }
        }
        return minDate;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {

        Set<Date> dateSet = new HashSet<>();
        Date date;
        Event event;
        Status status;
        for (Record record : setOfLogObjects) {
            String userName = record.getUserName();
            if (userName.equals(user)) {
                date = record.getDate();
                if (isDateFromInterval(date, after, before)) {
                    event = record.getEvent();
                    status = record.getStatus();
                    if (event.compareTo(Event.WRITE_MESSAGE) == 0 && status.compareTo(Status.OK) == 0) {
                        dateSet.add(date);
                    }
                }
            }
        }
        return dateSet;
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {

        Set<Date> dateSet = new HashSet<>();
        Date date;
        Event event;
        Status status;
        for (Record record : setOfLogObjects) {
            String userName = record.getUserName();
            if (userName.equals(user)) {
                date = record.getDate();
                if (isDateFromInterval(date, after, before)) {
                    event = record.getEvent();
                    status = record.getStatus();
                    if (event.compareTo(Event.DOWNLOAD_PLUGIN) == 0 && status.compareTo(Status.OK) == 0) {
                        dateSet.add(date);
                    }
                }
            }
        }
        return dateSet;
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        int userEventsNumber = 0;
        Set<Event> eventSet = new HashSet<>();

        for (Record record : setOfLogObjects) {
            Event event = record.getEvent();
            if (isDateFromInterval(record.getDate(), after, before)) {
                eventSet.add(event);
            }
        }
        userEventsNumber = eventSet.size();
        return userEventsNumber;
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        Set<Event> eventSet = new HashSet<>();

        for (Record record : setOfLogObjects) {
            Event event = record.getEvent();
            if (isDateFromInterval(record.getDate(), after, before)) {
                eventSet.add(event);
            }
        }
        return eventSet;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Set<Event> eventSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            Event event = record.getEvent();
            if (record.getIpAddress().equals(ip)) {
                if (isDateFromInterval(record.getDate(), after, before)) {
                    eventSet.add(event);
                }
            }
        }
        return eventSet;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set<Event> eventSet = new HashSet<>();
        String userName = "";
        String[] userNameParts = null;
        List<String> list = null;
        for (Record record : setOfLogObjects) {
            userName = record.getUserName().replaceAll("\\s", " ");
            userNameParts = userName.split(" ");
            list = Arrays.asList(userNameParts);
            if (list.contains(user)) {
                Event event = record.getEvent();
                if (isDateFromInterval(record.getDate(), after, before)) {
                    eventSet.add(event);
                }
            }
        }
        return eventSet;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        Set<Event> eventSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            Event event = record.getEvent();
            Status status = record.getStatus();
            if (status.compareTo(Status.FAILED) == 0) {
                if (isDateFromInterval(record.getDate(), after, before)) {
                    eventSet.add(event);
                }
            }
        }
        return eventSet;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        Set<Event> eventSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            Event event = record.getEvent();
            Status status = record.getStatus();
            if (status.compareTo(Status.ERROR) == 0) {
                if (isDateFromInterval(record.getDate(), after, before)) {
                    eventSet.add(event);
                }
            }
        }
        return eventSet;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        int solveAttempts = 0;
        for (Record record : setOfLogObjects) {
            if (record.getTaskNumber() == task) {
                if (isDateFromInterval(record.getDate(), after, before)) {
                    Event event = record.getEvent();
                    if (event.compareTo(Event.SOLVE_TASK) == 0)
                        solveAttempts++;
                }
            }
        }
        return solveAttempts;
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        int successAttempts = 0;
        for (Record record : setOfLogObjects) {
            if (record.getTaskNumber() == task) {
                if (isDateFromInterval(record.getDate(), after, before)) {
                    Event event = record.getEvent();
                    Status status = record.getStatus();
                    if (event.compareTo(Event.DONE_TASK) == 0)
                        successAttempts++;
                }
            }
        }
        return successAttempts;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {

        Map<Integer, Integer> map = new HashMap<>();
        Integer task = 0;

        int solveAttempts = 0;
        for (Record record : setOfLogObjects) {
            task = record.getTaskNumber();
            if (isDateFromInterval(record.getDate(), after, before)) {
                Event event = record.getEvent();
                if (event.compareTo(Event.SOLVE_TASK) == 0) {
                    if (map.containsKey(task)) {
                        solveAttempts = map.get(task);
                        solveAttempts++;
                        map.put(task, solveAttempts);
                    }
                    else
                        map.put(task, 1);
                }
            }
        }
        return map;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {

        Map<Integer, Integer> map = new HashMap<>();
        Integer task = 0;

        int successAttempts = 0;
        for (Record record : setOfLogObjects) {
            task = record.getTaskNumber();
            if (isDateFromInterval(record.getDate(), after, before)) {
                Event event = record.getEvent();
                Status status = record.getStatus();
                if (event.compareTo(Event.DONE_TASK) == 0)
                    if (map.containsKey(task)) {
                        successAttempts = map.get(task);
                        successAttempts++;
                        map.put(task, successAttempts);
                    }
                    else
                        map.put(task, 1);
            }
        }
        return map;
    }

    @Override
    public Set<Date> getAllUniqueDates() {
        Set<Date> dateSet = new HashSet<>();
        Date date;
        for (Record record : setOfLogObjects) {
            date = record.getDate();
            dateSet.add(date);
        }
        return dateSet;
    }

    @Override
    public Set<Status> getAllUniqueStatuses() {
        Set<Status> statusSet = new HashSet<>();
        Status status;
        for (Record record : setOfLogObjects) {
            status = record.getStatus();
            statusSet.add(status);
        }
        return statusSet;
    }


    @Override
    public Set<String> getUsersForDate(Date after, Date before) {
        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), after, before)) {
                String userName = record.getUserName();
                users.add(userName);
            }
        }
        return users;
    }

    @Override
    public Set<String> getUsersForEvent(Event event, Date after, Date before) {
        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (record.getEvent().compareTo(event) == 0) {
                if (isDateFromInterval(record.getDate(), after, before)) {
                    String userName = record.getUserName();
                    users.add(userName);
                }
            }
        }
        return users;
    }

    @Override
    public Set<String> getUsersForStatus(Status status, Date after, Date before) {
        Set<String> users = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (record.getStatus().compareTo(status) == 0) {
                if (isDateFromInterval(record.getDate(), after, before)) {
                    String userName = record.getUserName();
                    users.add(userName);
                }
            }
        }
        return users;
    }

    @Override
    public Set<Date> getDatesForIP(String ip) {
        Set<Date> dateSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (record.getIpAddress().equals(ip))
                dateSet.add(record.getDate());
        }
        return dateSet;
    }

    @Override
    public Set<Date> getDatesForUser(String user) {
        Set<Date> dateSet = new HashSet<>();
        String userName = "";
        String[] userNameParts = null;
        List<String> list = null;
        for (Record record : setOfLogObjects) {
            userName = record.getUserName().replaceAll("\\s", " ");
            userNameParts = userName.split(" ");
            list = Arrays.asList(userNameParts);
            if (list.contains(user)) {
                dateSet.add(record.getDate());
            }
        }
        return dateSet;
    }

    @Override
    public Set<Date> getDatesForEvent(Event event) {
        Set<Date> dateSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (record.getEvent().compareTo(event) == 0)
                dateSet.add(record.getDate());
        }
        return dateSet;
    }

    @Override
    public Set<Date> getDatesForStatus(Status status) {
        Set<Date> dateSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (record.getStatus().compareTo(status) == 0)
                dateSet.add(record.getDate());
        }
        return dateSet;
    }

    @Override
    public Set<Event> getEventsForDate(Date date) {
        Set<Event> eventSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), date, date)) {
                eventSet.add(record.getEvent());
            }
        }
        return eventSet;
    }

    @Override
    public Set<Event> getEventsForStatus(Status status) {
        Set<Event> eventSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (record.getStatus().compareTo(status) == 0)
                eventSet.add(record.getEvent());
        }
        return eventSet;
    }

    @Override
    public Set<Status> getStatusesForIP(String ip) {
        Set<Status> statusSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (record.getIpAddress().equals(ip))
                statusSet.add(record.getStatus());
        }
        return statusSet;
    }

    @Override
    public Set<Status> getStatusesForUser(String user) {
        Set<Status> statusSet = new HashSet<>();
        String userName = "";
        String[] userNameParts = null;
        List<String> list = null;
        for (Record record : setOfLogObjects) {
            userName = record.getUserName().replaceAll("\\s", " ");
            userNameParts = userName.split(" ");
            list = Arrays.asList(userNameParts);
            if (list.contains(user)) {
                statusSet.add(record.getStatus());
            }
        }
        return statusSet;
    }

    @Override
    public Set<Status> getStatusesForDate(Date date) {
        Set<Status> statusSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), date, date)) {
                statusSet.add(record.getStatus());
            }
        }
        return statusSet;
    }

    @Override
    public Set<Status> getStatusesForEvent(Event event) {
        Set<Status> statusSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (record.getEvent().compareTo(event) == 0)
                statusSet.add(record.getStatus());
        }
        return statusSet;
    }


    @Override
    public Set<Object> execute(String query) {

        if (query == null) return null;

        Set<?> objectSet = new HashSet<>();
        String value = "";

        query = query.replaceAll("\\s+", " ");

        String[] strArray = null;
        if (query.contains(" "))
        {
            strArray = query.split(" ");
            if (strArray.length == 2)
            {
                if (strArray[0].equals("get"))
                {
                    switch (strArray[1])
                    {
                        case "ip":
                            objectSet = getUniqueIPs(null, null);
                            break;

                        case "user":
                            objectSet = getAllUsers();
                            break;

                        case "date":
                            objectSet = getAllUniqueDates();
                            break;

                        case "event":
                            objectSet = getAllEvents(null, null);
                            break;

                        case "status":
                            objectSet = getAllUniqueStatuses();
                            break;
                    }
                }
            }
            else if (strArray.length == 6 || strArray.length == 7)
            {
                if (strArray[2].equals("for") && strArray[4].equals("=") &&
                        ((strArray.length == 6 && strArray[5].startsWith("\"") && strArray[5].endsWith("\"")) ||
                                (strArray.length == 7 && strArray[5].startsWith("\"") && strArray[6].endsWith("\"")))) {

                    if (strArray.length == 6)
                        value = strArray[5].substring(1, strArray[5].length()-1);
                    else {
                        value = strArray[5].substring(1, strArray[5].length()) + " " + strArray[6].substring(0, strArray[6].length()-1);
                    }

                    if (!value.equals("")) {
                        switch (strArray[1]) {
                            case "ip":
                                switch (strArray[3]) {
                                    case "user":
                                        objectSet = getIPsForUser(value, null, null);
                                        break;

                                    case "date":
                                        Date date = getDateFromStr(value);
                                        objectSet = getUniqueIPs(date, date);
                                        break;

                                    case "event":
                                        objectSet = getIPsForEvent(Event.valueOf(value), null, null);
                                        break;

                                    case "status":
                                        objectSet = getIPsForStatus(Status.valueOf(value), null, null);
                                        break;
                                }
                                break;

                            case "user":
                                switch (strArray[3]) {
                                    case "ip":
                                        objectSet = getUsersForIP(value, null, null);
                                        break;

                                    case "date":
                                        Date date = getDateFromStr(value);
                                        objectSet = getUsersForDate(date, date);
                                        break;

                                    case "event":
                                        objectSet = getUsersForEvent(Event.valueOf(value), null, null);
                                        break;

                                    case "status":
                                        objectSet = getUsersForStatus(Status.valueOf(value), null, null);
                                        break;
                                }
                                break;

                            case "date":
                                switch (strArray[3]) {
                                    case "ip":
                                        objectSet = getDatesForIP(value);
                                        break;

                                    case "user":
                                        objectSet = getDatesForUser(value);
                                        break;

                                    case "event":
                                        objectSet = getDatesForEvent(Event.valueOf(value));
                                        break;

                                    case "status":
                                        objectSet = getDatesForStatus(Status.valueOf(value));
                                        break;
                                }
                                break;

                            case "event":
                                switch (strArray[3]) {
                                    case "ip":
                                        objectSet = getEventsForIP(value, null, null);
                                        break;

                                    case "user":
                                        objectSet = getEventsForUser(value, null, null);
                                        break;

                                    case "date":
                                        Date date = getDateFromStr(value);
                                        objectSet = getEventsForDate(date);
                                        break;

                                    case "status":
                                        objectSet = getEventsForStatus(Status.valueOf(value));
                                        break;
                                }
                                break;

                            case "status":
                                switch (strArray[3]) {
                                    case "ip":
                                        objectSet = getStatusesForIP(value);
                                        break;

                                    case "user":
                                        objectSet = getStatusesForUser(value);
                                        break;

                                    case "date":
                                        Date date = getDateFromStr(value);
                                        objectSet = getStatusesForDate(date);
                                        break;

                                    case "event":
                                        objectSet = getStatusesForEvent(Event.valueOf(value));
                                        break;
                                }
                                break;
                        }
                    }
                }
            }
        }

        if (objectSet == null)
            return null;
        else {
            if (objectSet.isEmpty())
                return null;
            else
                return (Set<Object>) objectSet;
        }
    }

    private Date getDateFromStr(String str)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
        }
        return date;
    }
}