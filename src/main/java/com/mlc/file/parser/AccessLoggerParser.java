package com.mlc.file.parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AccessLoggerParser implements CommandLineRunner {

    @Autowired
    private FileParser fileParser;

    @Autowired
    private AccessLogFileRepository repository;

    @Value("${access.log.parser.startDate}")
    private String startDate;

    @Value("${access.log.parser.duration}")
    private String duration;

    @Value("${access.log.parser.threshold}")
    private long threshold;

    public void run(String... arg0) throws Exception {

        fileParser.saveFileToDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");

        Calendar startDateCal = Calendar.getInstance();
        startDateCal.setTimeInMillis(sdf.parse(startDate).getTime());

        Calendar finalDate = Calendar.getInstance();
        finalDate.setTime(startDateCal.getTime());

        switch (duration) {
        case "hourly":
            finalDate.add(Calendar.HOUR, 1);
            break;
        case "daily":
            finalDate.add(Calendar.DAY_OF_MONTH, 1);
            break;
        default:
            throw new IllegalArgumentException("Duration must be informed");
        }

        List<AccessLog> results = repository.findByEventTimeBetweenHavingCountMoreThan(startDateCal.getTime(), finalDate.getTime(), threshold);

        System.out.println(results);
    }

}
