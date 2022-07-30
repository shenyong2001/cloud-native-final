package com.example.demo.controller;

import com.example.demo.limit.AccessLimit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("access")
public class AccessLimitController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 限流测试
     */
    @GetMapping
    @AccessLimit(maxCount = 100,second = 60)
    public String limit(HttpServletRequest request) {
        logger.info("Access Limit Test");
        return "限流测试";
    }
}