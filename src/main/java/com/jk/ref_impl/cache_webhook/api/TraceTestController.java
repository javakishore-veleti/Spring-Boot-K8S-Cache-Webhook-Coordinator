package com.jk.ref_impl.cache_webhook.api;

import io.micrometer.observation.annotation.Observed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TraceTestController {

    // This will automatically generate a trace + span in OTEL / Jaeger
    @Observed(name = "trace.test.endpoint")
    @GetMapping("/trace/test")
    public String traceTest() throws InterruptedException {
        Thread.sleep(100); // simulate some work
        return "Trace created successfully!";
    }
}