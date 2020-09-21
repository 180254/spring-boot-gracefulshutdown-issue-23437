package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
public class DemoController {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @RequestMapping(path = "/url1", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<String>> demoPost() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "http://localhost:8080/url2");
        ResponseEntity<String> responseEntity = new ResponseEntity<>("OK/Post", httpHeaders, HttpStatus.SEE_OTHER);
        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();
        deferredResult.setResult(responseEntity);
        return deferredResult;
    }

    @RequestMapping(path = "/url2", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<String>> demo() {

        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();

        scheduledExecutorService.schedule(() -> {
            ResponseEntity<String> responseEntity = new ResponseEntity<>("OK/url2", HttpStatus.OK);
            deferredResult.setResult(responseEntity);
        }, 20, TimeUnit.SECONDS);

        return deferredResult;
    }

}
