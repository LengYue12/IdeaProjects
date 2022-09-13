package com.lagou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;

/**
 * @BelongsProject springbootdemo
 * @Author lengy
 * @CreateTime 2022/8/15 20:20
 * @Description
 */
@Controller
public class LoginController {

    @RequestMapping("toLogin")
    public String toLoginView(Model model){

        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        return "login"; // resource/templates/login.html
    }
}
