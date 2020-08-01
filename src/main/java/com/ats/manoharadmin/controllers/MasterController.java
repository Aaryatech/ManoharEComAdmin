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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.manoharadmin.common.Constant;
import com.ats.manoharadmin.common.FormValidation;
import com.ats.manoharadmin.models.DeliveryInstruction;
import com.ats.manoharadmin.models.Info;
import com.ats.manoharadmin.models.MUser;
import com.ats.manoharadmin.models.login.UserResponse;

@Controller
@Scope("session")
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
		
		// Author-Mahendra Singh Created On-01-08-2020
		// Modified By-Mahendra Singh Created On-01-08-2020
		// Desc- Show Language.
		@RequestMapping(value = "/showDeliveryInstructn", method = RequestMethod.GET)
		public ModelAndView showDeliveryInstructions(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("product/delvInsructList");

				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", userDetail.getUser().getCompanyId());

				DeliveryInstruction[] delvArr = Constant.getRestTemplate()
						.postForObject(Constant.url + "getAllDeliveryInstructions", map, DeliveryInstruction[].class);
				List<DeliveryInstruction> delvList = new ArrayList<DeliveryInstruction>(Arrays.asList(delvArr));

				for (int i = 0; i < delvList.size(); i++) {

					delvList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(delvList.get(i).getInstruId())));
				}
				model.addObject("delvList", delvList);
				model.addObject("title", "Delivery Instruction List");

			} catch (Exception e) {
				System.out.println("Execption in /showDeliveryInstructn : " + e.getMessage());
				e.printStackTrace();
			}

			return model;
		}
		
		// Author-Mahendra Singh Created On-20-07-2020
		// Modified By-Mahendra Singh Created On-20-07-2020
		// Desc- Add Language. addLanguage
		@RequestMapping(value = "/addDeliveryInstruction", method = RequestMethod.GET)
		public ModelAndView addDeliveryInstructn(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			DeliveryInstruction instruct = new DeliveryInstruction();
			try {
				model = new ModelAndView("product/addDeliveryInstructn");

				model.addObject("instruct", instruct);
				model.addObject("title", "Add Delivery Instruction");

			} catch (Exception e) {
				System.out.println("Execption in /addDeliveryInstruction : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}

		@RequestMapping(value = "/getCaptionInfo", method = RequestMethod.GET)
		@ResponseBody
		public Info getCaptionInfo(HttpServletRequest request, HttpServletResponse response) {

			Info info = new Info();
			try {
				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");

				String caption = request.getParameter("caption");
				int instructId = Integer.parseInt(request.getParameter("instructId"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("caption", caption);
				map.add("instructId", instructId);
				map.add("compId", userDetail.getUser().getCompanyId());

				DeliveryInstruction captionRes = Constant.getRestTemplate()
						.postForObject(Constant.url + "getDeliveryInstructionByCaptn", map, DeliveryInstruction.class);
				System.out.println("captionRes  ------  " + captionRes);
				if (captionRes != null) {
					info.setError(false);
					info.setMessage("Caption Found");
				} else {
					info.setError(true);
					info.setMessage("Caption Not Found");
				}
			} catch (Exception e) {
				System.out.println("Execption in /getCaptionInfo : " + e.getMessage());
				e.printStackTrace();
			}
			return info;
		}

		@RequestMapping(value = "/insertDeliveryInstruction", method = RequestMethod.POST)
		public String insertDeliveryInstruction(HttpServletRequest request, HttpServletResponse response) {

			try {
				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");

				DeliveryInstruction instructn = new DeliveryInstruction();

				int instructId = Integer.parseInt(request.getParameter("instruct_id"));

				instructn.setInstruId(instructId);
				instructn.setInstructnCaption(request.getParameter("instruct_cap"));
				instructn.setCompanyId(userDetail.getUser().getCompanyId());
				instructn.setDelStatus(0);
				instructn.setDescription(request.getParameter("instruct_decp"));
				instructn.setExInt1(0);
				instructn.setExInt2(0);
				instructn.setExVar1("NA");
				instructn.setExVar2("NA");
				instructn.setIsActive(Integer.parseInt(request.getParameter("instruction")));

				DeliveryInstruction instructRes = Constant.getRestTemplate()
						.postForObject(Constant.url + "addDeliveryInstrunctn", instructn, DeliveryInstruction.class);

				if (instructRes.getInstruId() > 0) {
					if (instructId == 0)
						session.setAttribute("successMsg", "Delivery Instruction Saved Sucessfully");
					else
						session.setAttribute("successMsg", "Delivery Instruction Update Sucessfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save Delivery Instruction");
				}

			} catch (Exception e) {
				System.out.println("Execption in /insertDeliveryInstruction : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showDeliveryInstructn";

		}

		@RequestMapping(value = "/editDeliveryInsrtuctn", method = RequestMethod.GET)
		public ModelAndView editDeliveryInsrtuctn(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("product/addDeliveryInstructn");

				String base64encodedString = request.getParameter("instructId");
				String instructId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("instructId", Integer.parseInt(instructId));

				DeliveryInstruction instruct = Constant.getRestTemplate()
						.postForObject(Constant.url + "getDeliveryInstructionById", map, DeliveryInstruction.class);
				model.addObject("instruct", instruct);

				model.addObject("title", "Edit Delivery Instruction");
			} catch (Exception e) {
				System.out.println("Execption in /editDeliveryInsrtuctn : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}

		// Author-Mahendra Singh Created On-20-07-2020
		// Modified By-Mahendra Singh Created On-20-07-2020
		// Desc- Show City.
		@RequestMapping(value = "/deleteInstructn", method = RequestMethod.GET)
		public String deleteInstructn(HttpServletRequest request, HttpServletResponse response) {

			HttpSession session = request.getSession();
			try {

				String base64encodedString = request.getParameter("instructId");
				String instructId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("instructId", Integer.parseInt(instructId));

				Info res = Constant.getRestTemplate().postForObject(Constant.url + "deleteDeliveryInstructnById", map,
						Info.class);

				if (!res.getError()) {
					session.setAttribute("successMsg", res.getMessage());
				} else {
					session.setAttribute("errorMsg", res.getMessage());
				}

			} catch (Exception e) {
				System.out.println("Execption in /deleteInstructn : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showDeliveryInstructn";
		}

}
