package com.gzk.gss.controller;

import com.gzk.gss.timewheel.TimerLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: TestController
 * @description: TODO
 * @author: gzk0329
 * @date: 2024/6/25
 * @version: V1.0
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/cancel")
    public void cancel(){
        TimerLauncher timerLauncher = new TimerLauncher();
        timerLauncher.cancel(222L);
    }
}
