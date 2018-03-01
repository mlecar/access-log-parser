package com.mlc.file.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class FileParser {

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${access.log.file.path}")
    private String accessLogFile;

    @Autowired
    private AccessLogFileRepository respository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public void saveFileToDatabase() throws IOException, ParseException {
        Resource resource = resourceLoader.getResource(accessLogFile);

        InputStream is = resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        ConcurrentLinkedQueue<AccessLog> queue = new ConcurrentLinkedQueue<>();

        String line;
        while ((line = br.readLine()) != null) {
            String[] infos = line.split("\\|");
            AccessLog accessLog = new AccessLog();
            accessLog.setTime(sdf.parse(infos[0]));
            accessLog.setOriginIp(infos[1]);
            accessLog.setHttpMethod(infos[2]);
            accessLog.setHttpResponse(infos[3]);
            accessLog.setUserAgentRequestHeader(infos[4]);
            queue.add(accessLog);
        }
        br.close();

        System.out.println("Saving file to database. Please wait...");
        respository.save(queue);
    }

    /*
     * public static void main(String[] args) throws ParseException { String
     * line = "2017-01-01 00:02:45.250|192.168.142.23|\"GET / HTTP/1.1\"|200|";
     * System.out.println(line.split("\\|")[0]); SimpleDateFormat sdf = new
     * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
     * System.out.println(sdf.parse(line.split("\\|")[0])); }
     */

}
