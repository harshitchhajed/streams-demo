package com.example.streamsdemo;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Vector;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping
    public ResponseEntity<InputStreamResource> get() throws FileNotFoundException {

        // Almost 1GB file size
        final File file = new File("/Users/harshit/Documents/test-video.mp4");

        Vector<InputStream> inputStreams = new Vector<>();
        for (int i = 0; i < 4; i++) {
            inputStreams.add(new FileInputStream(file));
        }

        SequenceInputStream sequenceInputStream = new SequenceInputStream(inputStreams.elements());
        InputStreamResource resource = new InputStreamResource(sequenceInputStream);

        // Set up headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=joined-test-video");

        // Return ResponseEntity with InputStreamResource, headers, and status code
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length() * 4)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
