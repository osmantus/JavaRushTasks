package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.DateQuery;
import com.javarush.task.task39.task3913.query.EventQuery;
import com.javarush.task.task39.task3913.query.IPQuery;
import com.javarush.task.task39.task3913.query.UserQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery {

    //private List<Path> logFiles;
    private Path logDir;
    private Set<Record> setOfLogObjects;

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
    // разбор строк в объекты Record
    private Set<Record> parseStringsToRecordObjects(List<String> listOfLogStrings){
        Set<Record> result = new HashSet<>();
        for (String recordString : listOfLogStrings) {
            String[] recordStringArray = recordString.split("\\t");
            Record record = new Record();
            // ip адрес
            record.setIpAddress(recordStringArray[0]);
            // имя пользователя
            record.setUserName(recordStringArray[1]);
            // дата
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
            try {
                Date date = dateFormat.parse(recordStringArray[2]);
                record.setDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // event
            record.setEvent(Event.valueOf(recordStringArray[3].split("\\s")[0])) ;
            // номер задачи
            if (recordStringArray[3].split("\\s").length>1){
                record.setTaskNumber(Integer.parseInt(recordStringArray[3].split("\\s")[1]));
            }
            // status
            record.setStatus(Status.valueOf(recordStringArray[4]));
            // добавление записи в список
            result.add(record);
        }
        return result;
    }
    // конструктор
    public LogParser(Path logDir){
        this.setOfLogObjects = parseStringsToRecordObjects(readLogFiles(logDir));
    }

    // проверка, попадает ли дата в интервал
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

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {

        Set<String> ipSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(), after, before)) {
                ipSet.add(record.getIpAddress());
            }
        }
        return ipSet.size();

        /*List<Path> logFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logDir, "*.{log}")) {
            for (Path entry: stream) {
                logFiles.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (logFiles.size() == 0)
            return 0;

        String filePath;
        String line;
        String[] fields;
        String ip;
        String dateTime;
        Date parsedDate;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        int result = 0;

        Set<String> ipSet = new HashSet<>();

        for (Path path : logFiles) {
            if (!path.toString().equals(""))
            {
                try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8))
                {
                    while (reader.ready()) {
                        line = reader.readLine();
                        fields = line.split("\t");
                        if (!fields[0].trim().equals(""))
                        {
                            ip = fields[0];
                            dateTime = fields[2];

                            parsedDate = formatter.parse(dateTime);

                            if (after != null && before != null) {
                                if (parsedDate.after(after) && parsedDate.before(before)) {
                                    if (!ipSet.contains(ip)) {
                                        ipSet.add(ip);
                                        result++;
                                    }
                                }
                            }
                            else if (after != null)
                            {
                                if (parsedDate.after(after)) {
                                    if (!ipSet.contains(ip)) {
                                        ipSet.add(ip);
                                        result++;
                                    }
                                }
                            }
                            else if (before != null)
                            {
                                if (parsedDate.before(before)) {
                                    if (!ipSet.contains(ip)) {
                                        ipSet.add(ip);
                                        result++;
                                    }
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
        */
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

        /*List<Path> logFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logDir, "*.{log}")) {
            for (Path entry: stream) {
                logFiles.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (logFiles.size() == 0)
            return null;

        String filePath;
        String line;
        String[] fields;
        String ip;
        String dateTime;
        Date parsedDate;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        int result = 0;

        Set<String> ipSet = new HashSet<>();

        for (Path path : logFiles) {
            if (!path.toString().equals(""))
            {
                try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8))
                {
                    while (reader.ready()) {
                        line = reader.readLine();
                        fields = line.split("\t");
                        if (!fields[0].trim().equals(""))
                        {
                            ip = fields[0];
                            dateTime = fields[2];

                            parsedDate = formatter.parse(dateTime);

                            if (after != null && before != null) {
                                if (parsedDate.after(after) && parsedDate.before(before))
                                    ipSet.add(ip);
                            }
                            else if (after != null) {
                                if (parsedDate.after(after))
                                    ipSet.add(ip);
                            }
                            else if (before != null)
                            {
                                if (parsedDate.before(before))
                                    ipSet.add(ip);
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return ipSet;
        */
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {

        Set<String> ipSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if ((isDateFromInterval(record.getDate(), after, before)) && (user.equals(record.getUserName()))) {
                ipSet.add(record.getIpAddress());
            }
        }
        return ipSet;

        /*List<Path> logFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logDir, "*.{log}")) {
            for (Path entry: stream) {
                logFiles.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (logFiles.size() == 0)
            return null;

        String filePath;
        String line;
        String[] fields;
        String ip;
        String dateTime;
        String userName;

        Date parsedDate;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        int result = 0;

        Set<String> ipSet = new HashSet<>();

        for (Path path : logFiles) {
            if (!path.toString().equals(""))
            {
                try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8))
                {
                    while (reader.ready()) {
                        line = reader.readLine();
                        fields = line.split("\t");
                        if (!fields[0].trim().equals(""))
                        {
                            ip = fields[0];
                            dateTime = fields[2];
                            userName = fields[1];

                            if (userName.equals(user)) {
                                parsedDate = formatter.parse(dateTime);

                                if (after != null && before != null) {
                                    if (parsedDate.after(after) && parsedDate.before(before))
                                        ipSet.add(ip);
                                } else if (after != null) {
                                    if (parsedDate.after(after))
                                        ipSet.add(ip);
                                } else if (before != null) {
                                    if (parsedDate.before(before))
                                        ipSet.add(ip);
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return ipSet;
        */
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

        /*List<Path> logFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logDir, "*.{log}")) {
            for (Path entry: stream) {
                logFiles.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (logFiles.size() == 0)
            return null;

        String filePath;
        String line;
        String[] fields;
        String ip;
        String dateTime;
        String eventStr;
        Event eventVar;

        Date parsedDate;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        int result = 0;

        Set<String> ipSet = new HashSet<>();

        for (Path path : logFiles) {
            if (!path.toString().equals(""))
            {
                try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8))
                {
                    while (reader.ready()) {
                        line = reader.readLine();
                        fields = line.split("\t");
                        if (!fields[0].trim().equals(""))
                        {
                            ip = fields[0];
                            dateTime = fields[2];
                            eventStr = fields[3].trim();
                            if (eventStr.contains(" "))
                                eventStr = eventStr.split(" ")[0];
                            eventVar = Event.valueOf(eventStr);

                            if (eventVar.name().equals(event.name())) {
                                parsedDate = formatter.parse(dateTime);

                                if (after != null && before != null) {
                                    if (parsedDate.after(after) && parsedDate.before(before))
                                        ipSet.add(ip);
                                } else if (after != null) {
                                    if (parsedDate.after(after))
                                        ipSet.add(ip);
                                } else if (before != null) {
                                    if (parsedDate.before(before))
                                        ipSet.add(ip);
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return ipSet;
        */
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

        /*List<Path> logFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logDir, "*.{log}")) {
            for (Path entry: stream) {
                logFiles.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (logFiles.size() == 0)
            return null;

        String filePath;
        String line;
        String[] fields;
        String ip;
        String dateTime;
        String statusStr;
        Status statusVar;

        Date parsedDate;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        int result = 0;

        Set<String> ipSet = new HashSet<>();

        for (Path path : logFiles) {
            if (!path.toString().equals(""))
            {
                try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8))
                {
                    while (reader.ready()) {
                        line = reader.readLine();
                        fields = line.split("\t");
                        if (!fields[0].trim().equals(""))
                        {
                            ip = fields[0];
                            dateTime = fields[2];
                            statusStr = fields[4].trim();
                            statusVar = Status.valueOf(statusStr);

                            if (statusVar.name().equals(status.name())) {
                                parsedDate = formatter.parse(dateTime);

                                if (after != null && before != null) {
                                    if (parsedDate.after(after) && parsedDate.before(before))
                                        ipSet.add(ip);
                                } else if (after != null) {
                                    if (parsedDate.after(after))
                                        ipSet.add(ip);
                                } else if (before != null) {
                                    if (parsedDate.before(before))
                                        ipSet.add(ip);
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return ipSet;
        */
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
                    //userEventsNumber++;
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
                //if (record.getEvent().compareTo(Event.LOGIN) == 0 && record.getStatus().compareTo(Status.OK) == 0)
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
                //if (record.getEvent().compareTo(Event.SOLVE_TASK) == 0 && record.getStatus().compareTo(Status.OK) == 0)
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
                    //if (record.getEvent().compareTo(Event.SOLVE_TASK) == 0 && record.getStatus().compareTo(Status.OK) == 0) {
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
                //if (record.getEvent().compareTo(Event.DONE_TASK) == 0 && record.getStatus().compareTo(Status.OK) == 0)
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
                    //if (record.getEvent().compareTo(Event.DONE_TASK) == 0 && record.getStatus().compareTo(Status.OK) == 0) {
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
        for (Record record : setOfLogObjects) {
            Event event = record.getEvent();
            if (record.getUserName().equals(user)) {
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
}