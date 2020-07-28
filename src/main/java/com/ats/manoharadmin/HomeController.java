package com.ats.manoharadmin;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
//import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.manoharadmin.common.Constant;
import com.ats.manoharadmin.models.Info;
import com.ats.manoharadmin.models.MnUser;
import com.ats.manoharadmin.models.login.UserResponse;


@Controller
@Scope("session")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("login");
		logger.info("/ request mapping.");

		return model;

	}

	@RequestMapping("/loginProcess")
	public String doLogin(HttpServletRequest request, HttpServletResponse res) throws IOException {

		String output = "";

		String username = request.getParameter("username");
		String encryptPass = request.getParameter("password");
		
		res.setContentType("text/html");
		HttpSession session = request.getSession();
		RestTemplate rest = new RestTemplate();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(encryptPass.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String password = number.toString(16);
			
			System.out.println("Login Process " + username+" "+password);

			if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("") || username == null || password == null) {

				output = "redirect:/login";

			} else {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				//List<Integer> typeList = new ArrayList<Integer>(Arrays.asList(0,2));	
				//System.out.println("typeList    -----------    "+typeList);
				
				map.add("username", username);
				map.add("password", password);
				map.add("type", 0);
				UserResponse userObj = Constant.getRestTemplate().postForObject(
						Constant.url + "login", map, UserResponse.class);
				
				try { 
					
//					Company company = restTemplate.getForObject(Constant.url + "/getCompany", Company.class);
//					Constant.FACTORYNAME = company.getCompanyName();
//					Constant.FACTORYADDRESS = "Address:" + company.getCompanyAddress() + " ,Phone:"
//							+ company.getCompanyContactNo();
//
//					Constant.FACTORYGSTIN = company.getCompanyGstNo();

					session.setAttribute("UserDetail", userObj);
					session.setAttribute("userId", userObj.getUser().getUserId());		
					session.setAttribute("companyId", userObj.getUser().getCompanyId());

				} catch (Exception e) {
					e.printStackTrace();
				}
				

				if (userObj.getErrorMessage().isError() == false) {

					session.setAttribute("userName", username);

					map = new LinkedMultiValueMap<String, Object>();
					int userId = userObj.getUser().getUserId();
					map.add("usrId", userId);
					System.out.println("Before web service");
					try {
//						ParameterizedTypeReference<List<ModuleJson>> typeRef = new ParameterizedTypeReference<List<ModuleJson>>() {
//						};
//						ResponseEntity<List<ModuleJson>> responseEntity = restTemplate.exchange(
//								Constant.url + "getRoleJson", HttpMethod.POST, new HttpEntity<>(map), typeRef);
//
//						List<ModuleJson> newModuleList = responseEntity.getBody();
//
//						System.err.println("new Module List " + newModuleList.toString());
//
//						session.setAttribute("newModuleList", newModuleList);
//						session.setAttribute("sessionModuleId", 0);
//						session.setAttribute("sessionSubModuleId", 0);

						// System.out.println("Role Json "+Commons.newModuleList.toString());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}

					output = "redirect:/home";

					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

					map.add("cDate", dateFormat.format(new Date()));

					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String dt = sdf.format(new Date());
					
					session.removeAttribute("errorMsg");
				} else {
					
					session.setAttribute("errorMsg", "Invalid Login Credentials");

					output = "redirect:/";
					System.out.println("Invalid login credentials");
				}

			}
		} catch (Exception e) {
			output = "redirect:/";
			e.printStackTrace();
			System.out.println("HomeController Login API Excep:  " + e.getMessage());
			session.setAttribute("loginError", " Something Went Wrong! Server Error");
		}

		return output;
	}
	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.info(" /home request mapping.");

		ModelAndView mav = new ModelAndView("home");
		

		return mav;
	}
	
	@RequestMapping(value = "/showForgetPass", method = RequestMethod.GET)
	public ModelAndView showForgetPass(HttpServletRequest request, HttpServletResponse response) {
		logger.info(" /home request mapping.");

		ModelAndView mav = new ModelAndView("forgetPass");
		

		return mav;
	}
	
/****************************************************************************/
	Instant start = null;
	
	@RequestMapping(value = "/getUserInfoByMobNo", method = RequestMethod.POST)
	public ModelAndView getUserInfoByMobNo(HttpServletRequest request, HttpServletResponse response) {
		Info info = new Info();
		ModelAndView model = null;
		
	try{
		HttpSession session = request.getSession();
		RestTemplate rest = new RestTemplate();
		
		String mobNo = request.getParameter("mob");
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("mobNo", mobNo);
		
		MnUser user = Constant.getRestTemplate().postForObject(Constant.url + "getMnUserDetailByMobNo", map, MnUser.class);
		System.err.println("User Info-----------"+user);
		if(user!=null) {
			model = new ModelAndView("verifyOTP");
			model.addObject("contact", user.getUserMobileNo());
			info.setError(false);
			info.setMessage("User Found");			
			System.err.println(info);
			
			start = Instant.now();
			;
		}else {
			info.setError(true);			
			info.setMessage("User Not Found");
			System.err.println(info);
			session.setAttribute("invalidMob", "Invalid Mobile No.");
			model = new ModelAndView("forgetPass");
		}
	}catch (Exception e) {
		e.printStackTrace();		
	}
		
		return model;
		
	}
	
	
	@RequestMapping(value = "/otpVerification", method = RequestMethod.POST)
	public ModelAndView OTPVerificationByContact(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		System.err.println("Hiii  OTPVerification  ");
		Info info = new Info();
		ModelAndView model = null;
		
		try {
			RestTemplate rest = new RestTemplate();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			String otp = request.getParameter("otp");

			map.add("otp", otp);

			MnUser user = Constant.getRestTemplate().postForObject(Constant.url + "verifyOTP", map, MnUser.class);	
		//	System.err.println("OTP User--------------"+user);

			if (user.getUserId() == 0) {
				
				session.setAttribute("errorMsg", "Invalid OTP.");
				model = new ModelAndView("forgetPass");
				model.addObject("msg", "Incorrect OTP");

			} else {				
				System.err.println("User" + user);
				model = new ModelAndView("changePassword");
				model.addObject("userId", user.getUserId());
				
			}

		} catch (Exception e) {
			System.err.println("Exce in otpVerification  " + e.getMessage());
			e.printStackTrace();
		}

		return model;

	}
	
	@RequestMapping(value = "/changeToNewPassword", method = RequestMethod.POST)
	public String changeToNewPassword(HttpServletRequest request, HttpServletResponse response) {
		Info info = new Info();
		ModelAndView model = null;
		HttpSession session = request.getSession();
		RestTemplate rest = new RestTemplate();
		try {
			
			int userId = Integer.parseInt(request.getParameter("userId"));
			
			String pass = request.getParameter("newPass");
			String password = pass;
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(password.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16); 
			
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("userId", userId);
			map.add("newPass", hashtext);

			Info inf = Constant.getRestTemplate().postForObject(Constant.url + "updateToNewPassword", map, Info.class);

			if (inf.getError()) {
				model = new ModelAndView("login");
				session.setAttribute("errorMsg", "Password Not Change");
				info.setError(true);
				info.setMessage("User Not Found");
				System.err.println(info);
			} else {
				model = new ModelAndView("login");			
				session.setAttribute("successMsg", "Password Change Sucessfully");
				info.setError(false);
				info.setMessage("User Found");
				System.err.println(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/";

	}
	
}
