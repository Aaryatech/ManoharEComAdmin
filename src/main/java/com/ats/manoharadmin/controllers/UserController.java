package com.ats.manoharadmin.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ats.manoharadmin.HomeController;

@Controller
@Scope("session")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
}
