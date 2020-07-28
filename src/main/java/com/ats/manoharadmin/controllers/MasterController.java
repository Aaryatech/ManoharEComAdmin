package com.ats.manoharadmin.controllers;
//Author-Mahendra Singh Created On-27-07-2020
// Modified By-Mahendra Singh Created On-27-07-2020
// Desc- Master Controller.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.manoharadmin.common.Constant;
import com.ats.manoharadmin.common.FormValidation;
import com.ats.manoharadmin.models.MUser;


public class MasterController {
	// Author-Mahendra Singh Created On-20-07-2020
		// Modified By-Mahendra Singh Created On-20-07-2020
		// Desc- Master Controller.
		@RequestMapping(value = "/showMnUsers", method = RequestMethod.GET)
		public ModelAndView showMnUsers(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");
				RestTemplate rest = new RestTemplate();
				
				model = new ModelAndView("masters/userList");
		
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				MUser[] userArr = rest.postForObject(Constant.url + "getAllMnUsers", map,
						MUser[].class);
				List<MUser> userList = new ArrayList<MUser>(Arrays.asList(userArr));

				for (int i = 0; i < userList.size(); i++) {

					userList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(userList.get(i).getUserId())));
				}
				model.addObject("userList", userList);
				model.addObject("title", "Users List");

			} catch (Exception e) {
				System.out.println("Execption in /showMnUsers : " + e.getMessage());
				e.printStackTrace();
			}

			return model;
		}

}
