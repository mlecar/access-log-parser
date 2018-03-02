package com.mlc.file.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class FileReader {

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${access.log.file.path}")
    private String accessLogFile;

    @Autowired
    private FileParser fileParser;

    public void readFile() throws IOException {
        Resource resource = resourceLoader.getResource(accessLogFile);

        InputStream is = resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        while ((line = br.readLine()) != null) {
            fileParser.add(line);
        }
        br.close();
    }

}
