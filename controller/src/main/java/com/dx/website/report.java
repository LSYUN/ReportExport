package com.dx.website;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/10/25.
 */
@RestController
@RequestMapping("/api")
public class report {
    @RequestMapping(value = "/report",method = RequestMethod.GET)
    public String getReport(){
        return "succeed";
    }
}
