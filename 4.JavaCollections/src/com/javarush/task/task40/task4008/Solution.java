package com.javarush.task.task40.task4008;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* 
Работа с Java 8 DateTime API
*/

public class Solution {
    public static void main(String[] args) {
        printDate("21.4.2014 15:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        //напишите тут ваш код

        String[] splt = date.split(" ");
        String sdate = null;
        String stime = null;

        if (splt.length == 1) {
            sdate = splt[0].contains(".") ? splt[0] : null;
            stime = splt[0].contains(":") ? splt[0] : null;
        }
        if (splt.length == 2) {
            sdate = splt[0].contains(".") ? splt[0] : null;
            stime = splt[1].contains(":") ? splt[1] : null;
        }

        DateTimeFormatter formatter;
        if (sdate != null) {
            formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            LocalDate dateTime = LocalDate.parse(sdate, formatter);
            System.out.println("День: " + dateTime.getDayOfMonth());
            System.out.println("День недели: " + dateTime.getDayOfWeek().getValue());
            System.out.println("День месяца: " + dateTime.getDayOfMonth());
            System.out.println("День года: " + dateTime.getDayOfYear());
            System.out.println("Неделя месяца: " + dateTime.get(WeekFields.of(Locale.getDefault()).weekOfMonth()));
            System.out.println("Неделя года: " + dateTime.get(WeekFields.of(Locale.getDefault()).weekOfYear()));
            System.out.println("Месяц: " + dateTime.getMonthValue());
            System.out.println("Год: " + dateTime.getYear());
        }
        if (stime != null) {
            formatter = DateTimeFormatter.ofPattern("H:m:s");
            LocalTime dateTime = LocalTime.parse(stime, formatter);
            System.out.println("AM или PM: " + (dateTime.get(ChronoField.AMPM_OF_DAY) == 0 ? "AM" : "PM"));
            System.out.println("Часы: " + dateTime.get(ChronoField.HOUR_OF_AMPM));
            System.out.println("Часы дня: " + dateTime.getHour());
            System.out.println("Минуты: " + dateTime.getMinute());
            System.out.println("Секунды: " + dateTime.getSecond());
        }

        /*int formatType = 0;

        Pattern pattern1 = Pattern.compile("^\\d{1,2}\\.\\d{1,2}\\.\\d{1,4}\\s{1}\\d{2}\\:\\d{2}\\:\\d{2}$");
        Matcher matcher1 = pattern1.matcher(date);

        Pattern pattern2 = Pattern.compile("^\\d{1,2}\\.\\d{1,2}\\.\\d{1,4}$");
        Matcher matcher2 = pattern2.matcher(date);

        Pattern pattern3 = Pattern.compile("^\\d{2}\\:\\d{2}\\:\\d{2}$");
        Matcher matcher3 = pattern3.matcher(date);

        DateTimeFormatter formatter;

        if (matcher1.matches()) {
            formatter = DateTimeFormatter.ofPattern("dd.M.yyyy HH:mm:ss");
            //dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            formatType = 1;
        }
        else if (matcher2.matches()) {
            formatter = DateTimeFormatter.ofPattern("dd.M.yyyy");
            //dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            formatType = 2;
        }
        else if (matcher3.matches()) {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            //dateFormat = new SimpleDateFormat("HH:mm:ss");
            formatType = 3;
        }
        else
            return;

        try
        {
            LocalDate localDate = LocalDate.now();
            LocalTime localTime = LocalTime.now();
            if (formatType == 1) {
                localDate = LocalDate.parse(date, formatter);
                localTime = LocalTime.parse(date, formatter);
            }
            else if (formatType == 2)
                localDate = LocalDate.parse(date, formatter);
            else if (formatType == 3)
                localTime = LocalTime.parse(date, formatter);

            if (formatType == 1 || formatType == 2)
            {
                if (formatType == 1)
                {
                    System.out.println("День: " + localDate.getDayOfMonth());
                    System.out.println("День недели: " + localDate.getDayOfWeek().getValue());
                    System.out.println("День месяца: " + localDate.getDayOfMonth());
                    System.out.println("День года: " + localDate.getDayOfYear());
                    System.out.println("Неделя месяца: " + localDate.get(WeekFields.of(Locale.getDefault()).weekOfMonth()));
                    System.out.println("Неделя года: " + localDate.get(WeekFields.of(Locale.getDefault()).weekOfYear()));
                    System.out.println("Месяц: " + localDate.getMonthValue());
                    System.out.println("Год: " + localDate.getYear());

                    System.out.println("AM или PM: " + (localTime.get(ChronoField.AMPM_OF_DAY) == 0 ? "AM" : "PM"));
                    System.out.println("Часы: " + localTime.get(ChronoField.CLOCK_HOUR_OF_AMPM));
                    System.out.println("Часы дня: " + localTime.getHour());
                    System.out.println("Минуты: " + localTime.getMinute());
                    System.out.println("Секунды: " + localTime.getSecond());
                }
                else
                {
                    System.out.println("День: " + localDate.getDayOfMonth());
                    System.out.println("День недели: " + localDate.getDayOfWeek().getValue());
                    System.out.println("День месяца: " + localDate.getDayOfMonth());
                    System.out.println("День года: " + localDate.getDayOfYear());
                    System.out.println("Неделя месяца: " + localDate.get(WeekFields.of(Locale.getDefault()).weekOfMonth()));
                    System.out.println("Неделя года: " + localDate.get(WeekFields.of(Locale.getDefault()).weekOfYear()));
                    System.out.println("Месяц: " + localDate.getMonthValue());
                    System.out.println("Год: " + localDate.getYear());
                }
            }
            else if (formatType == 3)
            {
                System.out.println("AM или PM: " + (localTime.get(ChronoField.AMPM_OF_DAY) == 0 ? "AM" : "PM"));
                System.out.println("Часы: " + localTime.get(ChronoField.CLOCK_HOUR_OF_AMPM));
                System.out.println("Часы дня: " + localTime.getHour());
                System.out.println("Минуты: " + localTime.getMinute());
                System.out.println("Секунды: " + localTime.getSecond());
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/
    }
}
