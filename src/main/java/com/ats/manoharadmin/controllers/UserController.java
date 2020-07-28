package com.ats.manoharadmin.controllers;


import java.util.Date;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.manoharadmin.HomeController;
import com.ats.manoharadmin.common.Constant;
import com.ats.manoharadmin.common.FormValidation;
import com.ats.manoharadmin.common.VpsImageUpload;
import com.ats.manoharadmin.models.City;
import com.ats.manoharadmin.models.Company;
import com.ats.manoharadmin.models.Designation;
import com.ats.manoharadmin.models.Info;
import com.ats.manoharadmin.models.MUser;
import com.ats.manoharadmin.models.UserType;

@Controller
@Scope("session")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	// Author-Mahendra Singh Created On-20-07-2020
		// Modified By-Mahendra Singh Created On-20-07-2020
		// Desc- Show Language.
		@RequestMapping(value = "/showUsers", method = RequestMethod.GET)
		public ModelAndView showMnUsers(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");
				
				model = new ModelAndView("masters/userList");
		
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				MUser[] userArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllUsers", map,
						MUser[].class);
				List<MUser> userList = new ArrayList<MUser>(Arrays.asList(userArr));

				for (int i = 0; i < userList.size(); i++) {

					userList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(userList.get(i).getUserId())));
				}
				model.addObject("userList", userList);
				model.addObject("title", "Users List");

			} catch (Exception e) {
				System.out.println("Execption in /showUsers : " + e.getMessage());
				e.printStackTrace();
			}

			return model;
		}

		// Author-Mahendra Singh Created On-22-07-2020
		// Modified By-Mahendra Singh Created On-22-07-2020
		// Desc- AddUser.
		@RequestMapping(value = "/addNewUser", method = RequestMethod.GET)
		public ModelAndView addNewUser(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			MUser user = new MUser();
			try {
				model = new ModelAndView("masters/addUser");

				Designation[] desigArr = Constant.getRestTemplate().getForObject(Constant.url + "getDesignations",
						Designation[].class);
				List<Designation> desigList = new ArrayList<Designation>(Arrays.asList(desigArr));
				model.addObject("desigList", desigList);

				UserType[] userTypeArr = Constant.getRestTemplate().getForObject(Constant.url + "getAllUserTypes",
						UserType[].class);
				List<UserType> usrTypList = new ArrayList<UserType>(Arrays.asList(userTypeArr));
				
				Company[] compArr = Constant.getRestTemplate().getForObject(Constant.url + "getAllActiveCompany",
						Company[].class);
				List<Company> companyList = new ArrayList<Company>(Arrays.asList(compArr));
				
				model.addObject("userTypeList", usrTypList);
				model.addObject("companyList", companyList);

				model.addObject("user", user);
				model.addObject("title", "Add User");

			} catch (Exception e) {
				System.out.println("Execption in /addNewUser : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}

		// Author-Mahendra Singh Created On-15-07-2020
		// Modified By-Mahendra Singh Created On-15-07-2020
		// Desc- Insert Ingredient.
		@RequestMapping(value = "/insertNewUser", method = RequestMethod.POST)
		public String insertNewUser(HttpServletRequest request, HttpServletResponse response,
				@RequestParam("doc") MultipartFile doc) {
			try {
				
				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String profileImage = null;

				HttpSession session = request.getSession();
				//int companyId = (int) session.getAttribute("companyId");

				if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

					System.err.println("In If ");

					profileImage = sf.format(date) + "_" + doc.getOriginalFilename();

					VpsImageUpload upload = new VpsImageUpload();
					Info info = upload.saveUploadedImge(doc, Constant.UPLOAD_URL, profileImage,
							Constant.imageFileExtensions, 0, 0, 0, 0, 0);

				} else {
					System.err.println("In else ");
					profileImage = request.getParameter("editImg");

				}

				int userId = Integer.parseInt(request.getParameter("user_id"));

				MUser user = new MUser();
				user.setUserId(userId);

				user.setDelStatus(0);

				user.setExFloat1(0);
				user.setExFloat2(0);
				user.setExFloat3(0);

				user.setExInt1(0);
				user.setExInt2(0);
				user.setExInt3(0);
				user.setExInt4(0);

				user.setExVar1("NA");
				user.setExVar2("NA");
				user.setExVar3("NA");
				user.setExVar4("NA");

				user.setCompanyId(Integer.parseInt(request.getParameter("company")));
				user.setDesignationId(Integer.parseInt(request.getParameter("designation")));
				user.setIsActive(Integer.parseInt(request.getParameter("user")));

				String pass = request.getParameter("pass");
				String password = pass;
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] messageDigest = md.digest(password.getBytes());
				BigInteger number = new BigInteger(1, messageDigest);
				String hashtext = number.toString(16);

				user.setPassword(hashtext);
				user.setProfilePic(profileImage);
				user.setRegDate(request.getParameter("reg_date"));
				user.setUserAddress(request.getParameter("address"));

				user.setUserEmail(request.getParameter("email"));
				user.setUserMobileNo(request.getParameter("mob_no"));
				user.setUserName(request.getParameter("user_name"));
				user.setUserType(Integer.parseInt(request.getParameter("user_type")));

				MUser res = Constant.getRestTemplate().postForObject(Constant.url + "addUser", user, MUser.class);

				if (res.getUserId() > 0) {
					if (userId == 0)
						session.setAttribute("successMsg", "User Saved Sucessfully");
					else
						session.setAttribute("successMsg", "User Update Sucessfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save User");
				}
			} catch (Exception e) {
				System.out.println("Execption in /insertNewUser : " + e.getMessage());
				e.printStackTrace();
			}

			return "redirect:/showUsers";

		}

		@RequestMapping(value = "/editUser", method = RequestMethod.GET)
		public ModelAndView editUser(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;

			try {
				model = new ModelAndView("masters/addUser");
				
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				Designation[] desigArr = Constant.getRestTemplate().getForObject(Constant.url + "getDesignations",
						Designation[].class);
				List<Designation> desigList = new ArrayList<Designation>(Arrays.asList(desigArr));
				model.addObject("desigList", desigList);

				UserType[] userTypeArr = Constant.getRestTemplate().getForObject(Constant.url + "getAllUserTypes",
						UserType[].class);
				List<UserType> usrTypList = new ArrayList<UserType>(Arrays.asList(userTypeArr));
				model.addObject("userTypeList", usrTypList);
				
				Company[] compArr = Constant.getRestTemplate().getForObject(Constant.url + "getAllActiveCompany",
						Company[].class);
				List<Company> companyList = new ArrayList<Company>(Arrays.asList(compArr));
				model.addObject("companyList", companyList);

				String base64encodedString = request.getParameter("userId");
				String userId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("userId", Integer.parseInt(userId));
				map.add("compId", companyId);

				MUser user = Constant.getRestTemplate().postForObject(Constant.url + "getUserById", map, MUser.class);

				model.addObject("user", user);

				model.addObject("imgPath", Constant.showDocSaveUrl);
				model.addObject("title", "Edit User");

			} catch (Exception e) {
				System.out.println("Execption in /editUser : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}

		// Author-Mahendra Singh Created On-20-07-2020
		// Modified By-Mahendra Singh Created On-20-07-2020
		// Desc- Delete Grievance Instruction.
		@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
		public String deleteUser(HttpServletRequest request, HttpServletResponse response) {

			HttpSession session = request.getSession();
			try {

				String base64encodedString = request.getParameter("userId");
				String userId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("userId", Integer.parseInt(userId));

				Info res = Constant.getRestTemplate().postForObject(Constant.url + "deleteUserById", map, Info.class);

				if (!res.getError()) {
					session.setAttribute("successMsg", res.getMessage());
				} else {
					session.setAttribute("errorMsg", res.getMessage());
				}

			} catch (Exception e) {
				System.out.println("Execption in /deleteUser : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showUsers";
		}

		@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
		@ResponseBody
		public Info getUserInfo(HttpServletRequest request, HttpServletResponse response) {

			Info info = new Info();
			try {
				int userId = 0;
					try {
						userId = Integer.parseInt(request.getParameter("userId"));
					}catch (Exception e) {
						userId = 0;
						e.printStackTrace();
					}
				String mobNo = request.getParameter("mobNo");
			

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("mobNo", mobNo);
				map.add("userId", userId);

				MUser res = Constant.getRestTemplate().postForObject(Constant.url + "getUserByMobNo", map, MUser.class);
				System.out.println("userRes  ------  " + res);
				if (res != null) {
					info.setError(false);
					info.setMessage("User Found");
				} else {
					info.setError(true);
					info.setMessage("User Not Found");
				}
			} catch (Exception e) {
				System.out.println("Execption in /getUserInfo : " + e.getMessage());
				e.printStackTrace();
			}
			return info;
		}
}
