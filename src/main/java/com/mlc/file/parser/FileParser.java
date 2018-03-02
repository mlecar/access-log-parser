package com.mlc.file.parser;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileParser {

    private ConcurrentLinkedQueue<AccessLog> queue;
    private ConcurrentHashMap<String, Integer> map;

    private LocalDateTime startLocalDateTime;
    private LocalDateTime finalDate;

    @Value("${access.log.parser.threshold}")
    private long threshold;

    @Autowired
    private AccessLogFileRepository respository;

    @Autowired
    private BlockedIpRepository blockedIpRespository;

    @Value("${access.log.parser.startDate}")
    private String startDate;

    @Value("${access.log.parser.duration}")
    private String duration;

    public FileParser() {
        queue = new ConcurrentLinkedQueue<>();
        map = new ConcurrentHashMap<>();
    }

    @PostConstruct
    private void initConfig() {
        startLocalDateTime = LocalDateTime.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss"));
        finalDate = startLocalDateTime.plus(1, getRange(duration));
    }

    public void add(String line) {
        String[] infos = line.split("\\|");
        AccessLog accessLog = new AccessLog();

        LocalDateTime logDate = LocalDateTime.parse(infos[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        accessLog.setTime(Date.from(logDate.toInstant(ZoneOffset.UTC)));

        String ip = infos[1];
        accessLog.setOriginIp(ip);

        if (map.containsKey(ip)) {
            if (logDate.isAfter(startLocalDateTime) && logDate.isBefore(finalDate)) {
                map.put(ip, map.get(ip) + 1);
            }
        } else {
            map.put(ip, 1);
        }
        accessLog.setHttpMethod(infos[2]);
        accessLog.setHttpResponse(infos[3]);
        accessLog.setUserAgentRequestHeader(infos[4]);
        queue.add(accessLog);
    }

    ChronoUnit getRange(String duration) {
        switch (duration) {
        case "hourly":
            return ChronoUnit.HOURS;
        case "daily":
            return ChronoUnit.DAYS;
        default:
            throw new IllegalArgumentException("Duration must be [hourly] or [daily]");
        }
    }

    public void saveFileToDatabase() throws IOException, ParseException {
        System.out.println("Saving file to database. Please wait...");
        respository.save(queue);
        System.out.println("Retrieving by MYSQL query");
        System.out.println(respository.findByEventTimeBetweenHavingCountMoreThan(Date.from(startLocalDateTime.toInstant(ZoneOffset.UTC)), Date.from(finalDate.toInstant(ZoneOffset.UTC)), threshold));
        System.out.println("Finished");
    }

    public void blockIpsbyThreshold() {
        System.out.println("Retrieving data faster");
        for (String ip : map.keySet()) {
            if (map.get(ip) > threshold) {
                // I could print the ips right from here to speed up the
                // response, but as I should save to database, Iam not doing
                // this here :)
                System.out.println(ip);
                blockedIpRespository.save(new BlockedIp(ip, "More than " + threshold + " requests."));
            }
        }
    }

}
