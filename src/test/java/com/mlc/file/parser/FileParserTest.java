package com.mlc.file.parser;

import static org.junit.Assert.assertTrue;

import java.time.temporal.ChronoUnit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FileParserTest {

    @InjectMocks
    private FileParser fileParser;

    @Mock
    private AccessLogFileRepository respository;

    @Mock
    private BlockedIpRepository blockedIpRespository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getHourlyRange() {
        assertTrue(fileParser.getRange("hourly").equals(ChronoUnit.HOURS));
    }

    @Test
    public void getDailyRange() {
        assertTrue(fileParser.getRange("daily").equals(ChronoUnit.DAYS));

    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownRange() {
        assertTrue(fileParser.getRange("monthy").equals(ChronoUnit.MONTHS));

    }

}
