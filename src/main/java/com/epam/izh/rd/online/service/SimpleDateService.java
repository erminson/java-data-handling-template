package com.epam.izh.rd.online.service;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class SimpleDateService implements DateService {

    /**
     * Метод парсит дату в строку
     *
     * @param localDate дата
     * @return строка с форматом день-месяц-год(01-01-1970)
     */
    @Override
    public String parseDate(LocalDate localDate) {
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        return String.format("%02d-%02d-%04d", day, month, year);
    }

    /**
     * Метод парсит строку в дату
     *
     * @param string строка в формате год-месяц-день часы:минуты (1970-01-01 00:00)
     * @return дата и время
     */
    @Override
    public LocalDateTime parseString(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(string, formatter);
    }

    /**
     * Метод конвертирует дату в строку с заданным форматом
     *
     * @param localDate исходная дата
     * @param formatter формат даты
     * @return полученная строка
     */
    @Override
    public String convertToCustomFormat(LocalDate localDate, DateTimeFormatter formatter) {
        return localDate.format(formatter);
    }

    /**
     * Метод получает следующий високосный год
     *
     * @return високосный год
     */
    @Override
    public long getNextLeapYear() {
        LocalDate now = LocalDate.now();
        int nextLeapYear = now.getYear() + 1;

        while (!isLeap(nextLeapYear)) {
            nextLeapYear++;
        }

        return nextLeapYear;
    }

    boolean isLeap(int year) {
        return ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
    }

    boolean isLeap2(int year) {
        YearMonth ym = YearMonth.of(year, 2);
        return ym.lengthOfMonth() == 29;
    }

    /**
     * Метод считает число секунд в заданном году
     *
     * @return число секунд
     */
    @Override
    public long getSecondsInYear(int year) {
        ZoneId zoneId = ZoneId.systemDefault();
        Year y = Year.of(year);

        LocalDate start = y.atDay(1) ;
        LocalDate stop = start.plusYears(1);

        ZonedDateTime startZDT = start.atStartOfDay(zoneId) ;
        ZonedDateTime stopZDT = stop.atStartOfDay(zoneId) ;

        Duration duration = Duration.between(startZDT, stopZDT) ;

        return duration.getSeconds();
    }
}
