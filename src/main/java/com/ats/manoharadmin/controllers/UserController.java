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
import com.ats.manoharadmin.models.Language;
import com.ats.manoharadmin.models.MUser;
import com.ats.manoharadmin.models.UserType;
import com.ats.manoharadmin.models.login.UserResponse;

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
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			try {
				model = new ModelAndView("masters/addUser");
				
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				Designation[] desigArr = Constant.getRestTemplate().getForObject(Constant.url + "getDesignations",
						Designation[].class);
				List<Designation> desigList = new ArrayList<Designation>(Arrays.asList(desigArr));
				model.addObject("desigList", desigList);
				
				
				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				
				UserType[] userTypeArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllUserTypes",
						map, UserType[].class);
				List<UserType> usrTypList = new ArrayList<UserType>(Arrays.asList(userTypeArr));
				model.addObject("userTypeList", usrTypList);
				
				
				Company[] compArr = Constant.getRestTemplate().getForObject(Constant.url + "getAllActiveCompany",
						Company[].class);
				List<Company> companyList = new ArrayList<Company>(Arrays.asList(compArr));
				model.addObject("companyList", companyList);

				String base64encodedString = request.getParameter("userId");
				String userId = FormValidation.DecodeKey(base64encodedString);

				map = new LinkedMultiValueMap<>();
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
		/********************************************************************************/
		
		// Author-Mahendra Singh Created On-28-07-2020
		// Modified By-Mahendra Singh Created On-28-07-2020
		// Desc- Show Language.
		@RequestMapping(value = "/showLanguages", method = RequestMethod.GET)
		public ModelAndView showLanguages(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("masters/languageList");
				
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				Language[] langArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllLanguages", map,
						Language[].class);
				List<Language> langList = new ArrayList<Language>(Arrays.asList(langArr));

				for (int i = 0; i < langList.size(); i++) {

					langList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(langList.get(i).getLangId())));
				}

				model.addObject("langList", langList);

				model.addObject("title", "Language List");

			} catch (Exception e) {
				System.out.println("Execption in /showLanguages : " + e.getMessage());
				e.printStackTrace();
			}

			return model;
		}

		// Author-Mahendra Singh Created On-28-07-2020
		// Modified By-Mahendra Singh Created On-28-07-2020
		// Desc- Add Language. addLanguage
		@RequestMapping(value = "/addLanguage", method = RequestMethod.GET)
		public ModelAndView addLanguage(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			Language lang = new Language();
			try {
				model = new ModelAndView("masters/addLanguage");
				model.addObject("lang", lang);
				model.addObject("title", "Add Language");

			} catch (Exception e) {
				System.out.println("Execption in /addLanguage : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}

		@RequestMapping(value = "/insertLanguage", method = RequestMethod.POST)
		public String insertLanguage(HttpServletRequest request, HttpServletResponse response) {
			Language lang = new Language();
			
			HttpSession session = request.getSession();
			UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");
			
			try {
				int langId = Integer.parseInt(request.getParameter("lang_id"));

				lang.setDelStatus(0);
				lang.setExInt1(userDetail.getUser().getCompanyId());
				lang.setExInt2(0);
				lang.setExVar1("NA");
				lang.setExVar2("NA");
				lang.setCompanyId(1);
				lang.setIsActive(Integer.parseInt(request.getParameter("language")));
				lang.setLangCode(request.getParameter("language_code").toUpperCase());
				lang.setLangId(langId);
				lang.setLangName(request.getParameter("language_name"));

				Language langRes = Constant.getRestTemplate().postForObject(Constant.url + "addLanguage", lang,
						Language.class);

				if (langRes.getLangId() > 0) {
					if (langId == 0)
						session.setAttribute("successMsg", "Language Saved Sucessfully");
					else
						session.setAttribute("successMsg", "Language Update Sucessfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save Language");
				}
			} catch (Exception e) {
				System.out.println("Execption in /addLanguage : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showLanguages";
		}

		@RequestMapping(value = "/getLangInfoByCode", method = RequestMethod.GET)
		@ResponseBody
		public Info getLangInfoByCode(HttpServletRequest request, HttpServletResponse response) {

			Info info = new Info();
			try {
				String code = request.getParameter("code");
				int langId = Integer.parseInt(request.getParameter("langId"));
				
				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("code", code);
				map.add("langId", langId);
				map.add("compId", userDetail.getUser().getCompanyId());

				Language langRes = Constant.getRestTemplate().postForObject(Constant.url + "getLanguageByCode", map,
						Language.class);
				System.out.println("LANGUAGE  ------  " + langRes);
				if (langRes != null) {
					info.setError(false);
					info.setMessage("Language Found");
				} else {
					info.setError(true);
					info.setMessage("Language Not Found");
				}
			} catch (Exception e) {
				System.out.println("Execption in /getLangInfoByCode : " + e.getMessage());
				e.printStackTrace();
			}
			return info;
		}

		@RequestMapping(value = "/editLang", method = RequestMethod.GET)
		public ModelAndView editLang(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("masters/addLanguage");
				
				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");

				String base64encodedString = request.getParameter("langId");
				String langId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("langId", Integer.parseInt(langId));
				map.add("compId", userDetail.getUser().getCompanyId());

				Language lang = Constant.getRestTemplate().postForObject(Constant.url + "getLanguageById", map,
						Language.class);
				model.addObject("lang", lang);

				model.addObject("title", "Edit Language");
			} catch (Exception e) {
				System.out.println("Execption in /editLang : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}

		// Author-Mahendra Singh Created On-20-07-2020
		// Modified By-Mahendra Singh Created On-20-07-2020
		// Desc- Show City.
		@RequestMapping(value = "/deleteLang", method = RequestMethod.GET)
		public String deleteLang(HttpServletRequest request, HttpServletResponse response) {

			HttpSession session = request.getSession();
			try {

				String base64encodedString = request.getParameter("langId");
				String langId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("langId", Integer.parseInt(langId));

				Info res = Constant.getRestTemplate().postForObject(Constant.url + "deleteLanguageById", map, Info.class);

				if (!res.getError()) {
					session.setAttribute("successMsg", res.getMessage());
				} else {
					session.setAttribute("errorMsg", res.getMessage());
				}

			} catch (Exception e) {
				System.out.println("Execption in /deleteLang : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showLanguages";
		}
		
/********************************************************************************/
		
		// Author-Mahendra Singh Created On-28-07-2020
		// Modified By-Mahendra Singh Created On-28-07-2020
		// Desc- Show User Type.
		@RequestMapping(value = "/showUserTypes", method = RequestMethod.GET)
		public ModelAndView showUserTypes(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("masters/userTypeList");

				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				UserType[] usrTypArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllUserTypes", map,
						UserType[].class);
				List<UserType> usrTypList = new ArrayList<UserType>(Arrays.asList(usrTypArr));

				for (int i = 0; i < usrTypList.size(); i++) {

					usrTypList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(usrTypList.get(i).getUserTypeId())));
				}

				model.addObject("usrTypList", usrTypList);

				model.addObject("title", "User Types List");

			} catch (Exception e) {
				System.out.println("Execption in /showUserTypes : " + e.getMessage());
				e.printStackTrace();
			}

			return model;
		}
		
		// Author-Mahendra Singh Created On-28-07-2020
		// Modified By-Mahendra Singh Created On-28-07-2020
		// Desc- Add User Type
		@RequestMapping(value = "/addUserType", method = RequestMethod.GET)
		public ModelAndView addUserType(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			UserType userType = new UserType();
			try {
					model = new ModelAndView("masters/addUserType");
					model.addObject("userType", userType);
					model.addObject("title", "Add User Type");

			} catch (Exception e) {
					System.out.println("Execption in /addUserType : " + e.getMessage());
					e.printStackTrace();
			}
			return model;
		}
		
		@RequestMapping(value = "/checkUniqueUserType", method = RequestMethod.GET)
		@ResponseBody
		public Info checkUniqueUserType(HttpServletRequest request, HttpServletResponse response) {

			Info info = new Info();
			try {
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");
				
				int userId = 0;
					try {
						userId = Integer.parseInt(request.getParameter("uId"));
					}catch (Exception e) {
						userId = 0;
						e.printStackTrace();
					}
				String userType = request.getParameter("userType");
			

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("userType", userType);
				map.add("userId", userId);
				map.add("compId", companyId);

				UserType res = Constant.getRestTemplate().postForObject(Constant.url + "uniqueUserType", map, UserType.class);
				System.out.println("userRes  ------  " + res);
				if (res != null) {
					info.setError(false);
					info.setMessage("User Type Found");
				} else {
					info.setError(true);
					info.setMessage("User Type Not Found");
				}
			} catch (Exception e) {
				System.out.println("Execption in /checkUniqueUserType : " + e.getMessage());
				e.printStackTrace();
			}
			return info;
		}
		
		
		@RequestMapping(value = "/insertUserType", method = RequestMethod.POST)
		public String insertUserType(HttpServletRequest request, HttpServletResponse response) {
			UserType userTyp = new UserType();
			
			HttpSession session = request.getSession();
			UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");
			
			try {
				int userTypId = Integer.parseInt(request.getParameter("user_type_id"));

				userTyp.setDelStatus(0);
				userTyp.setExInt2(0);
				userTyp.setExInt3(0);
				userTyp.setExVar1("NA");
				userTyp.setExVar2("NA");
				userTyp.setExVar3("NA");
				userTyp.setExVar4("NA");
				userTyp.setComapnyIdRequired(userDetail.getUser().getCompanyId());
				userTyp.setIsActive(Integer.parseInt(request.getParameter("status")));
				userTyp.setExFloat1(0);
				userTyp.setExFloat2(0);
				userTyp.setExFloat3(0);
				userTyp.setUserTypeId(userTypId);
				userTyp.setUserTypeName(request.getParameter("user_type_name"));
				userTyp.setUserTypeDesc(request.getParameter("description"));
				userTyp.setLoginAppcatbleTo(0);
				
				UserType res = Constant.getRestTemplate().postForObject(Constant.url + "addUserType", userTyp,
						UserType.class);

				if (res.getUserTypeId() > 0) {
					if (userTypId == 0)
						session.setAttribute("successMsg", "User Type Saved Sucessfully");
					else
						session.setAttribute("successMsg", "User Type Update Sucessfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save User Type");
				}
			} catch (Exception e) {
				System.out.println("Execption in /addLanguage : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showUserTypes";
		}
		
		@RequestMapping(value = "/editUserType", method = RequestMethod.GET)
		public ModelAndView editUserType(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("masters/addUserType");
				
				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");

				String base64encodedString = request.getParameter("userTypeId");
				String userTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("userTypeId", Integer.parseInt(userTypeId));
				map.add("compId", userDetail.getUser().getCompanyId());

				UserType userType = Constant.getRestTemplate().postForObject(Constant.url + "getUserTypeById", map,
						UserType.class);
				model.addObject("userType", userType);

				model.addObject("title", "Edit User Type");
			} catch (Exception e) {
				System.out.println("Execption in /editUserType : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}
		
		// Author-Mahendra Singh Created On-20-07-2020
		// Modified By-Mahendra Singh Created On-20-07-2020
		// Desc- Show City.
		@RequestMapping(value = "/deleteUserType", method = RequestMethod.GET)
		public String deleteUserType(HttpServletRequest request, HttpServletResponse response) {

					
					try {
						HttpSession session = request.getSession();
						UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");
						
						String base64encodedString = request.getParameter("userTypeId");
						String userTypeId = FormValidation.DecodeKey(base64encodedString);

						MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
						map.add("userTypeId", Integer.parseInt(userTypeId));
						map.add("compId", userDetail.getUser().getCompanyId());

						Info res = Constant.getRestTemplate().postForObject(Constant.url + "deleteUserType", map, Info.class);

						if (!res.getError()) {
							session.setAttribute("successMsg", res.getMessage());
						} else {
							session.setAttribute("errorMsg", res.getMessage());
						}

					} catch (Exception e) {
						System.out.println("Execption in /deleteUserType : " + e.getMessage());
						e.printStackTrace();
					}
					return "redirect:/showUserTypes";
				}
				
}
