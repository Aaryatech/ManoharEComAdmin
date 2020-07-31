package com.ats.manoharadmin.controllers;

import java.util.Date;
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
import com.ats.manoharadmin.models.Info;
import com.ats.manoharadmin.models.login.UserResponse;

@Controller
@Scope("session")
public class CompanyController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	 	// Author-Mahendra Singh Created On-28-07-2020
		// Modified By-Mahendra Singh Created On-28-07-2020
		// Desc- Show Company.
		@RequestMapping(value = "/showCompanies", method = RequestMethod.GET)
		public ModelAndView showCompanies(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("masters/companyList");

				Company[] compArr = Constant.getRestTemplate().getForObject(Constant.url + "getAllCompanies", 
						Company[].class);
				List<Company> compList = new ArrayList<Company>(Arrays.asList(compArr));

				for (int i = 0; i < compList.size(); i++) {

					compList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(compList.get(i).getCompanyId())));
				}
				model.addObject("compList", compList);
				model.addObject("title", "Company List");

			} catch (Exception e) {
				System.out.println("Execption in /showCompanies : " + e.getMessage());
				e.printStackTrace();
			}

			return model;
		}
	
		@RequestMapping(value = "/addNewCompany", method = RequestMethod.GET)
		public ModelAndView addNewCompany(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;

			try {
				
				model = new ModelAndView("masters/addCompany");
				
				Company company = new Company();
				model.addObject("company", company);
				
				City[] cityArr = Constant.getRestTemplate().getForObject(Constant.url + "getAllCities",
						City[].class);
				List<City> cityList = new ArrayList<City>(Arrays.asList(cityArr));
				model.addObject("cityList", cityList);
				
				model.addObject("title", "Add Company");

			} catch (Exception e) {
				System.out.println("Execption in /addNewCompany : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}
		
	
	
			// Author-Mahendra Singh Created On-28-07-2020
			// Modified By-Mahendra Singh Created On-28-07-2020
			// Desc- Delete Company.
			@RequestMapping(value = "/deleteCompany", method = RequestMethod.GET)
			public String deleteCompany(HttpServletRequest request, HttpServletResponse response) {

				HttpSession session = request.getSession();
				try {

					String base64encodedString = request.getParameter("compId");
					String compId = FormValidation.DecodeKey(base64encodedString);

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("compId", Integer.parseInt(compId));

					Info res = Constant.getRestTemplate().postForObject(Constant.url + "deleteCompanyById", map, Info.class);

					if (!res.getError()) {
						session.setAttribute("successMsg", res.getMessage());
					} else {
						session.setAttribute("errorMsg", res.getMessage());
					}

				} catch (Exception e) {
					System.out.println("Execption in /deleteCompany : " + e.getMessage());
					e.printStackTrace();
				}
				return "redirect:/showCompanies";
			}
			
			// Author-Mahendra Singh Created On-15-07-2020
			// Modified By-Mahendra Singh Created On-15-07-2020
			// Desc- Insert Ingredient.
			@RequestMapping(value = "/insertNewCompany", method = RequestMethod.POST)
			public String insertNewCompany(HttpServletRequest request, HttpServletResponse response,
					@RequestParam("doc") MultipartFile doc) {
				try {
					Date date = new Date();
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String logoImage = null;

					RestTemplate rest = new RestTemplate();
					HttpSession session = request.getSession();

					if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

						System.err.println("In If ");

						logoImage = sf.format(date) + "_" + doc.getOriginalFilename();

						VpsImageUpload upload = new VpsImageUpload();
						Info info = upload.saveUploadedImge(doc, Constant.UPLOAD_URL, logoImage,
								Constant.imageFileExtensions, 0, 0, 0, 0, 0);

					} else {
						System.err.println("In else ");
						logoImage = request.getParameter("editImg");

					}

					int compId = Integer.parseInt(request.getParameter("company_id"));

					Company company = new Company();

					company.setCompanyId(compId);
					
					company.setCinNo(request.getParameter("cin_no"));
					company.setCity(Integer.parseInt(request.getParameter("city")));
					company.setCompanyAddress(request.getParameter("address"));
					company.setCompanyContactNo(request.getParameter("contact_no"));
					
					company.setCompanyEmail(request.getParameter("email"));
					company.setCompanyGstNo(request.getParameter("gst_no"));
					company.setCompanyLogo(logoImage);
					company.setCompanyName(request.getParameter("company_name"));
					
					company.setCompanyWebsite(request.getParameter("website"));
					company.setContactPersonMobile(request.getParameter("mob_no"));
					company.setContactPersonMobile1(request.getParameter("mob_no1"));
					company.setContactPersonName(request.getParameter("contact_person"));				
					company.setContactPersonName1(request.getParameter("contact_person1"));	
					
					company.setPanCard(request.getParameter("pan_card"));
					company.setRegDate(request.getParameter("reg_date"));
					company.setStartingDate(request.getParameter("start_date"));
					company.setIsUsed(Integer.parseInt(request.getParameter("act_comp")));
					
					company.setDelStatus(0);
					company.setExInt1(0);
					company.setExInt2(0);
					company.setExInt3(0);

					company.setExVar1("NA");
					company.setExVar2("NA");
					company.setExVar3("NA");
					
					Company res = Constant.getRestTemplate().postForObject(Constant.url + "saveCompany", company, Company.class);

					if (res.getCompanyId() > 0) {
						if (compId == 0)
							session.setAttribute("successMsg", "Company Saved Sucessfully");
						else
							session.setAttribute("successMsg", "Company Update Sucessfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Save Company");
					}
				} catch (Exception e) {
					System.out.println("Execption in /insertNewUser : " + e.getMessage());
					e.printStackTrace();
				}

				return "redirect:/showCompanies";

			}
			
			@RequestMapping(value = "/editCompany", method = RequestMethod.GET)
			public ModelAndView editCompany(HttpServletRequest request, HttpServletResponse response) {

				ModelAndView model = null;

				try {
					
					model = new ModelAndView("masters/addCompany");
					
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					
					String base64encodedString = request.getParameter("compId");
					String compId = FormValidation.DecodeKey(base64encodedString);				
					
					map.add("compId", Integer.parseInt(compId));
					
					Company company = Constant.getRestTemplate().postForObject(Constant.url + "getCompanyById", map, Company.class);
					model.addObject("company", company);
					
					City[] cityArr = Constant.getRestTemplate().getForObject(Constant.url + "getAllCities",
							City[].class);
					List<City> cityList = new ArrayList<City>(Arrays.asList(cityArr));
					model.addObject("cityList", cityList);
					
					model.addObject("imgPath", Constant.showDocSaveUrl);
					model.addObject("title", "Add Company");

				} catch (Exception e) {
					System.out.println("Execption in /addNewCompany : " + e.getMessage());
					e.printStackTrace();
				}
				return model;
			}
			
			
			// Author-Mahendra Singh Created On-31-07-2020
			// Modified By-Mahendra Singh Created On-31-07-2020
			// Desc- Show Language.
			@RequestMapping(value = "/showCities", method = RequestMethod.GET)
			public ModelAndView showCities(HttpServletRequest request, HttpServletResponse response) {

				ModelAndView model = null;
				try {
					model = new ModelAndView("masters/cityList");

					City[] cityArr = Constant.getRestTemplate().getForObject(Constant.url + "getAllCities", City[].class);
					List<City> cityList = new ArrayList<City>(Arrays.asList(cityArr));

					for (int i = 0; i < cityList.size(); i++) {

						cityList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(cityList.get(i).getCityId())));
					}

					model.addObject("cityList", cityList);

					model.addObject("title", "City List");

				} catch (Exception e) {
					System.out.println("Execption in /showCities : " + e.getMessage());
					e.printStackTrace();
				}

				return model;
			}
			
			// Author-Mahendra Singh Created On-31-07-2020
			// Modified By-Mahendra Singh Created On-31-07-2020
			// Desc- Add City
			@RequestMapping(value = "/addNewCity", method = RequestMethod.GET)
			public ModelAndView addNewCity(HttpServletRequest request, HttpServletResponse response) {

				ModelAndView model = null;
				City city = new City();
				try {
					model = new ModelAndView("masters/addCity");
					model.addObject("city", city);
					model.addObject("title", "Add City");

				} catch (Exception e) {
					System.out.println("Execption in /addNewCity : " + e.getMessage());
					e.printStackTrace();
				}
				return model;
			}

			@RequestMapping(value = "/getCityInfoByCode", method = RequestMethod.GET)
			@ResponseBody
			public Info getCityInfoByCode(HttpServletRequest request, HttpServletResponse response) {

				Info info = new Info();
				try {
					String code = request.getParameter("code");
					int cityId = Integer.parseInt(request.getParameter("cityId"));

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("code", code);
					map.add("cityId", cityId);

					City cityRes = Constant.getRestTemplate().postForObject(Constant.url + "getCityByCode", map, City.class);
					System.out.println("CITY  ------  " + cityRes);
					if (cityRes != null) {
						info.setError(false);
						info.setMessage("City Found");
					} else {
						info.setError(true);
						info.setMessage("City Not Found");
					}
				} catch (Exception e) {
					System.out.println("Execption in /getCityInfoByCode : " + e.getMessage());
					e.printStackTrace();
				}
				return info;
			}

			@RequestMapping(value = "/insertCity", method = RequestMethod.POST)
			public String insertCity(HttpServletRequest request, HttpServletResponse response) {

				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");

				City city = new City();
				int cityId = Integer.parseInt(request.getParameter("city_id"));

				try {
					city.setCityCode(request.getParameter("city_code").toUpperCase());
					city.setCityId(cityId);
					city.setCityName(request.getParameter("city_name"));
					city.setCompanyId(userDetail.getUser().getCompanyId());
					city.setDelStatus(0);
					city.setDescription(request.getParameter("city_decp"));
					city.setExInt1(0);
					city.setExInt2(0);
					city.setExVar1("NA");
					city.setExVar2("NA");
					city.setIsActive(Integer.parseInt(request.getParameter("city")));

					City cityRes = Constant.getRestTemplate().postForObject(Constant.url + "addCity", city, City.class);

					if (cityRes.getCityId() > 0) {
						if (cityId == 0)
							session.setAttribute("successMsg", "City Saved Sucessfully");
						else
							session.setAttribute("successMsg", "City Update Sucessfully");
					} else {
						session.setAttribute("errorMsg", "Failed to Save City");
					}
				} catch (Exception e) {
					System.out.println("Execption in /insertCity : " + e.getMessage());
					e.printStackTrace();
				}

				return "redirect:/showCities";
			}

			@RequestMapping(value = "/editCity", method = RequestMethod.GET)
			public ModelAndView editCity(HttpServletRequest request, HttpServletResponse response) {

				ModelAndView model = null;
				try {
					model = new ModelAndView("masters/addCity");

					String base64encodedString = request.getParameter("cityId");
					String cityId = FormValidation.DecodeKey(base64encodedString);

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("cityId", Integer.parseInt(cityId));

					City city = Constant.getRestTemplate().postForObject(Constant.url + "getCityById", map, City.class);
					model.addObject("city", city);

					model.addObject("title", "Edit City");
				} catch (Exception e) {
					System.out.println("Execption in /editLang : " + e.getMessage());
					e.printStackTrace();
				}
				return model;
			}

			// Author-Mahendra Singh Created On-20-07-2020
			// Modified By-Mahendra Singh Created On-20-07-2020
			// Desc- Show City.
			@RequestMapping(value = "/deleteCity", method = RequestMethod.GET)
			public String deleteCity(HttpServletRequest request, HttpServletResponse response) {

				HttpSession session = request.getSession();
				try {

					String base64encodedString = request.getParameter("cityId");
					String cityId = FormValidation.DecodeKey(base64encodedString);

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("cityId", Integer.parseInt(cityId));

					Info res = Constant.getRestTemplate().postForObject(Constant.url + "deleteCityById", map, Info.class);

					if (!res.getError()) {
						session.setAttribute("successMsg", res.getMessage());
					} else {
						session.setAttribute("errorMsg", res.getMessage());
					}

				} catch (Exception e) {
					System.out.println("Execption in /deleteCity : " + e.getMessage());
					e.printStackTrace();
				}
				return "redirect:/showCities";
			}
			
			/**************************************************************************************/
			
			
			
}
