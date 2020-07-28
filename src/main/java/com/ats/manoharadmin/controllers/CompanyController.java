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
			
			
			
}
