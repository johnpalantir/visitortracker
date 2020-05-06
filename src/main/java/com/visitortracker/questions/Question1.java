package com.visitortracker.questions;

import com.visitortracker.model.Visit;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Question1 counts no of home page visitors and Visitors List.
 */
@Component
public final class Question1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Question1.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
    private final List<Visit> visitList = new LinkedList<>();


    private Question1() {

    }

    /**
     * getList of visitors OR None.
     *
     * @param fileName file Name.
     * @return List or null Exception Thrown data discrepancy.
     */
    public synchronized List<Visit> loadLog(final String fileName) {

        if (fileName == null || !fileName.endsWith(".log")) {
            return null;
        }

        try (Stream<String> lines = Files.lines(Paths.get(fileName)).skip(1)) {

            lines.forEach(line -> {
                String[] split = line.split("\\s+");
                visitList.add(new Visit(split[1], split[2], parseDate(split[3] + " " + split[4])));
            });
        } catch (IOException e) {
            LOGGER.error(String.format("Failed to process log file request with '%1$s' source file ", fileName, e));
            throw new RuntimeException(fileName + " does not have the correct format ");
        }

        return visitList;
    }

    public Date parseDate(final String convertDate) {
        try {
            return sdf.parse(convertDate);
        }
        catch (ParseException e) {
            throw new RuntimeException("Not able to parse Date: " + convertDate);
        }
    }

    /**
     * Get Date in pair(start, end).
     *
     * @param startDate startDate (yyyy-mm-dd hh).
     * @param endDate   endDate (yyyy-mm-dd hh)
     * @return Pair (start,End) OR Exception thrown.
     */
    public Pair<Date, Date> parseDate(final String startDate, final String endDate) {
        if (startDate == null || endDate == null) return null;

        try {
            return Pair.of(sdf.parse(startDate), sdf.parse(endDate));
        }
        catch (ParseException e) {
            LOGGER.error("Incorrect Date input...expected format is yyyy-mm-dd hh");
            throw new RuntimeException(String.format("Incorrect Date Format '%1$s' '%2$s' " + startDate, endDate));
        }
    }

    /**
     * No of visitor between start and End Date.
     *
     * @param fileName  fileName.
     * @param startDate startDate.
     * @param endDate   End Date.
     * @return count or 0.
     */
    public long countNumberOfHomepageVisits(final String fileName, final String startDate, final String endDate) {

        if (startDate == null || endDate == null) return 0;
        Pair<Date, Date> pair = parseDate(startDate, endDate);

        return loadLog(fileName).stream()
                .map(Visit::getDate)
                .collect(Collectors.toList())
                .stream()
                .filter(e -> e.after(pair.getLeft()) && e.before(pair.getRight())).count();
    }
}
