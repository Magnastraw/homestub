package com.unc.home;

import com.unc.home.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StubController {

    @RequestMapping("/")
    public String critMode() {
        Utils.isAlarm=true;
        return HttpStatus.OK.toString();
    }
}
