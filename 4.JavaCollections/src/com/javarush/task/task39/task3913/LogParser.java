package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;

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
import java.util.*;

public class LogParser implements IPQuery {

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
        if (after == null) after = current;
        if (before == null) before = current;
        if ((current.getTime()>=after.getTime())&&(current.getTime()<=before.getTime())) result = true;
        return result;
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {

        Set<String> ipSet = new HashSet<>();
        for (Record record : setOfLogObjects) {
            if (isDateFromInterval(record.getDate(),after,before)) {
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
            if (isDateFromInterval(record.getDate(),after,before)) {
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
            if ((isDateFromInterval(record.getDate(),after,before)) && (user.equals(record.getUserName()))) {
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
            if ((isDateFromInterval(record.getDate(),after,before)) && (event.equals(record.getEvent()))) {
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
            if ((isDateFromInterval(record.getDate(),after,before)) && (status.equals(record.getStatus()))) {
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
}