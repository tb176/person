package com.mo9.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by beiteng on 2016/10/31.
 */
@RequestMapping(value="/umeng")
public class FirstController {

    public ModelAndView sendUnicast(HttpServletRequest request, HttpServletResponse response){
        return  new ModelAndView("/success");
    }
}
