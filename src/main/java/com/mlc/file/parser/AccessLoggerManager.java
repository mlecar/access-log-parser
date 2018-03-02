package com.mlc.file.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AccessLoggerManager implements CommandLineRunner {

    @Autowired
    private FileParser fileParser;

    @Autowired
    private FileReader fileReader;

    public void run(String... arg0) throws Exception {

        fileReader.readFile();

        fileParser.blockIpsbyThreshold();

        fileParser.saveFileToDatabase();
    }

}
