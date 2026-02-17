package com.velocity.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestController {

    @GetMapping("/status")
    public String getApplicationStatus() {

        return """
                <html>
                <head>
                    <title>Application Status</title>
                </head>
                <body style="text-align:center; font-family:Arial; margin-top:100px;">
                    <h1 style="color:green; font-size:48px;">APPLICATION RUNNING</h1>
                    <h2 style="font-size:28px;">Spring Boot Service is UP 🚀</h2>
                    <p style="font-size:20px;">
                        Version: <b>1.0.0</b><br>
                        Time: """ + LocalDateTime.now() + """
                    </p>
                </body>
                </html>
                """;
    }
}
