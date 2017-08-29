package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.IPQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogParser implements IPQuery {

    private List<Path> logFiles;

    public LogParser(Path logDir) {

        logFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logDir, "*.{log}")) {
            for (Path entry: stream) {
                logFiles.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            // I/O error encounted during the iteration, the cause is an IOException
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {

        if (logFiles.size() == 0)
            return 0;

        String filePath;
        String line;
        String[] fields;
        String ip;
        String dateTime;
        Date parsedDate;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

        int result = 0;

        Set<String> ipSet = new HashSet<>();

        for (Path path : logFiles) {
            if (!path.toString().equals(""))
            {
                try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8))
                {
                    //BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

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
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {

        if (logFiles.size() == 0)
            return null;

        String filePath;
        String line;
        String[] fields;
        String ip;
        String dateTime;
        Date parsedDate;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

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
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        if (logFiles.size() == 0)
            return null;

        String filePath;
        String line;
        String[] fields;
        String ip;
        String dateTime;
        String userName;

        Date parsedDate;

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

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
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

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
                            eventStr = fields[3];
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
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

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
                            statusStr = fields[3];
                            if (statusStr.contains(" "))
                                statusStr = statusStr.split(" ")[0];
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
    }
}