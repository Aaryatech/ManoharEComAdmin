package com.ats.manoharadmin.controllers;
//Author-Mahendra Singh Created On-27-07-2020
// Modified By-Mahendra Singh Created On-27-07-2020
// Desc- Master Controller.

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.ats.manoharadmin.models.Category;
import com.ats.manoharadmin.models.ConfigRelatedProduct;
import com.ats.manoharadmin.models.DeliveryInstruction;
import com.ats.manoharadmin.models.GetConfiguredItemsList;
import com.ats.manoharadmin.models.GetItem;
import com.ats.manoharadmin.models.GetProductRelatedList;
import com.ats.manoharadmin.models.GrievencesInstruction;
import com.ats.manoharadmin.models.GrievencesTypeInstructn;
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
		
		/**************************************************************************************/

		// Author-Mahendra Singh Created On-20-07-2020
		// Modified By-Mahendra Singh Created On-20-07-2020
		// Desc- Show Language.
		@RequestMapping(value = "/showGrievences", method = RequestMethod.GET)
		public ModelAndView showGrievences(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("product/grievanceList");

				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				GrievencesInstruction[] grievArr = Constant.getRestTemplate()
						.postForObject(Constant.url + "getAllGrievancesInstructns", map, GrievencesInstruction[].class);
				List<GrievencesInstruction> grievList = new ArrayList<GrievencesInstruction>(Arrays.asList(grievArr));

				for (int i = 0; i < grievList.size(); i++) {

					grievList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(grievList.get(i).getGrievanceId())));
				}
				model.addObject("grievList", grievList);
				model.addObject("title", "Grievances Instruction List");

			} catch (Exception e) {
				System.out.println("Execption in /showGrievences : " + e.getMessage());
				e.printStackTrace();
			}

			return model;
		}

		// Author-Mahendra Singh Created On-20-07-2020
		// Modified By-Mahendra Singh Created On-20-07-2020
		// Desc- Add Grievance Type Instructn.
		@RequestMapping(value = "/addGrievanceInstructn", method = RequestMethod.GET)
		public ModelAndView addGrievanceInstructn(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			GrievencesInstruction grievance = new GrievencesInstruction();
			try {
				model = new ModelAndView("product/addGrievances");

				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);

				GrievencesTypeInstructn[] grievArr = Constant.getRestTemplate()
						.postForObject(Constant.url + "getAllGrievTypeInstruct", map, GrievencesTypeInstructn[].class);

				List<GrievencesTypeInstructn> grievList = new ArrayList<GrievencesTypeInstructn>(Arrays.asList(grievArr));
				model.addObject("grievList", grievList);

				model.addObject("grievance", grievance);
				model.addObject("title", "Add Grievances Instruction");

			} catch (Exception e) {
				System.out.println("Execption in /addGrievanceInstructn : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}

		@RequestMapping(value = "/insertGrievanceInstruction", method = RequestMethod.POST)
		public String insertGrievanceInstruction(HttpServletRequest request, HttpServletResponse response) {

			try {
				GrievencesInstruction grievance = new GrievencesInstruction();

				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				int grievanceId = Integer.parseInt(request.getParameter("grievances_id"));

				grievance.setGrievanceId(grievanceId);
				grievance.setGrievenceTypeId(Integer.parseInt(request.getParameter("griev_type")));
				grievance.setCaption(request.getParameter("griev_cap"));
				grievance.setCompanyId(companyId);
				grievance.setDelStatus(0);
				grievance.setDescription(request.getParameter("griev_decp"));
				grievance.setExInt1(0);
				grievance.setExInt2(0);
				grievance.setExVar1("NA");
				grievance.setExVar2("NA");
				grievance.setIsActive(Integer.parseInt(request.getParameter("grievance")));

				GrievencesInstruction grievanceRes = Constant.getRestTemplate().postForObject(Constant.url + "addGrievance",
						grievance, GrievencesInstruction.class);

				if (grievanceRes.getGrievanceId() > 0) {
					if (grievanceId == 0)
						session.setAttribute("successMsg", "Grievances Instruction Saved Sucessfully");
					else
						session.setAttribute("successMsg", "Grievances Instruction Update Sucessfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save Grievances Instruction");
				}

			} catch (Exception e) {
				System.out.println("Execption in /insertGrievanceInstruction : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showGrievences";

		}

		@RequestMapping(value = "/editGrievance", method = RequestMethod.GET)
		public ModelAndView editGrievance(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("product/addGrievances");

				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				String base64encodedString = request.getParameter("grievId");
				String grievanceId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("grievanceId", Integer.parseInt(grievanceId));
				map.add("compId", companyId);

				GrievencesInstruction grievance = Constant.getRestTemplate()
						.postForObject(Constant.url + "getGrievanceInstructnById", map, GrievencesInstruction.class);
				model.addObject("grievance", grievance);

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				GrievencesTypeInstructn[] grievArr = Constant.getRestTemplate()
						.postForObject(Constant.url + "getAllGrievTypeInstruct", map, GrievencesTypeInstructn[].class);

				List<GrievencesTypeInstructn> grievList = new ArrayList<GrievencesTypeInstructn>(Arrays.asList(grievArr));
				model.addObject("grievList", grievList);

				model.addObject("title", "Edit Grievances Instruction");
			} catch (Exception e) {
				System.out.println("Execption in /editGrievance : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}

		// Author-Mahendra Singh Created On-20-07-2020
		// Modified By-Mahendra Singh Created On-20-07-2020
		// Desc- Delete Grievance Instruction.
		@RequestMapping(value = "/deleteGrievance", method = RequestMethod.GET)
		public String deleteGrievance(HttpServletRequest request, HttpServletResponse response) {

			HttpSession session = request.getSession();
			try {

				String base64encodedString = request.getParameter("grievId");
				String grievanceId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("grievanceId", Integer.parseInt(grievanceId));

				Info res = Constant.getRestTemplate().postForObject(Constant.url + "deleteGrievanceInstructnById", map,
						Info.class);

				if (!res.getError()) {
					session.setAttribute("successMsg", res.getMessage());
				} else {
					session.setAttribute("errorMsg", res.getMessage());
				}

			} catch (Exception e) {
				System.out.println("Execption in /deleteGrievance : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showGrievences";
		}

		@RequestMapping(value = "/getGrievanceInfo", method = RequestMethod.GET)
		@ResponseBody
		public Info getGrievanceInfo(HttpServletRequest request, HttpServletResponse response) {

			Info info = new Info();
			try {
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				String caption = request.getParameter("caption");
				int grievanceId = Integer.parseInt(request.getParameter("grievancesId"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("caption", caption);
				map.add("grievanceId", grievanceId);
				map.add("compId", companyId);

				GrievencesInstruction captionRes = Constant.getRestTemplate()
						.postForObject(Constant.url + "getGrievancenstructnByCaptn", map, GrievencesInstruction.class);
				System.out.println("captionRes  ------  " + captionRes);
				if (captionRes != null) {
					info.setError(false);
					info.setMessage("Caption Found");
				} else {
					info.setError(true);
					info.setMessage("Caption Not Found");
				}
			} catch (Exception e) {
				System.out.println("Execption in /getGrievanceInfo : " + e.getMessage());
				e.printStackTrace();
			}
			return info;
		}
		
		/****************************************************************************/

		// Author-Mahendra Singh Created On-01-08-2020
		// Modified By-Mahendra Singh Created On-01-08-2020
		// Desc- Show Language.
		@RequestMapping(value = "/showGrievencesTypeIntructn", method = RequestMethod.GET)
		public ModelAndView GrievencesTypeInstructn(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("product/grievanceTypeList");

				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", userDetail.getUser().getCompanyId());

				GrievencesTypeInstructn[] grievArr = Constant.getRestTemplate()
						.postForObject(Constant.url + "getAllGrievTypeInstruct", map, GrievencesTypeInstructn[].class);
				List<GrievencesTypeInstructn> grievList = new ArrayList<GrievencesTypeInstructn>(Arrays.asList(grievArr));

				for (int i = 0; i < grievList.size(); i++) {

					grievList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(grievList.get(i).getGrevTypeId())));
				}
				model.addObject("grievList", grievList);
				model.addObject("title", "Grievances Type Instruction List");

			} catch (Exception e) {
				System.out.println("Execption in /showGrievencesTypeIntructn : " + e.getMessage());
				e.printStackTrace();
			}

			return model;
		}

		// Author-Mahendra Singh Created On-01-08-2020
		// Modified By-Mahendra Singh Created On-01-08-2020
		// Desc- Add Grievance Type Instructn.
		@RequestMapping(value = "/addGrievanceTypInstruct", method = RequestMethod.GET)
		public ModelAndView addGrievanceTypInstruct(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			GrievencesTypeInstructn griev = new GrievencesTypeInstructn();
			try {
				model = new ModelAndView("product/addGrievInstruct");

				model.addObject("griev", griev);
				model.addObject("title", "Add Grievances Type Instruction");

			} catch (Exception e) {
				System.out.println("Execption in /addGrievanceTypInstruct : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}

		@RequestMapping(value = "/insertGrievanceTypeInstruction", method = RequestMethod.POST)
		public String insertGrievanceTypeInstruction(HttpServletRequest request, HttpServletResponse response) {

			try {
				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");

				GrievencesTypeInstructn griev = new GrievencesTypeInstructn();

				int grievId = Integer.parseInt(request.getParameter("griev_id"));

				griev.setGrevTypeId(grievId);
				griev.setCaption(request.getParameter("griev_cap"));
				griev.setCompanyId(userDetail.getUser().getCompanyId());
				griev.setDelStatus(0);
				griev.setDescription(request.getParameter("griev_decp"));
				griev.setExInt1(0);
				griev.setExInt2(0);
				griev.setExVar1("NA");
				griev.setExVar2("NA");
				griev.setIsActive(Integer.parseInt(request.getParameter("grievance")));

				GrievencesTypeInstructn instructRes = Constant.getRestTemplate()
						.postForObject(Constant.url + "addGrievTypeInstruct", griev, GrievencesTypeInstructn.class);

				if (instructRes.getGrevTypeId() > 0) {
					if (grievId == 0)
						session.setAttribute("successMsg", "Grievances Type Instruction Saved Sucessfully");
					else
						session.setAttribute("successMsg", "Grievances Type Instruction Update Sucessfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save Grievances Type Instruction");
				}

			} catch (Exception e) {
				System.out.println("Execption in /insertGrievanceTypeInstruction : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showGrievencesTypeIntructn";

		}

		@RequestMapping(value = "/getGrievanceCaptionInfo", method = RequestMethod.GET)
		@ResponseBody
		public Info getGrievanceCaptionInfo(HttpServletRequest request, HttpServletResponse response) {

			Info info = new Info();
			try {
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				String caption = request.getParameter("caption");
				int grievTypeId = Integer.parseInt(request.getParameter("grievId"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("caption", caption);
				map.add("grievTypeId", grievTypeId);
				map.add("compId", companyId);

				GrievencesTypeInstructn captionRes = Constant.getRestTemplate()
						.postForObject(Constant.url + "getGrievTypeInstructByCaptn", map, GrievencesTypeInstructn.class);
				System.out.println("captionRes  ------  " + captionRes);
				if (captionRes != null) {
					info.setError(false);
					info.setMessage("Caption Found");
				} else {
					info.setError(true);
					info.setMessage("Caption Not Found");
				}
			} catch (Exception e) {
				System.out.println("Execption in /getGrievanceCaptionInfo : " + e.getMessage());
				e.printStackTrace();
			}
			return info;
		}

		@RequestMapping(value = "/editGrievanceTypeInsrtuctn", method = RequestMethod.GET)
		public ModelAndView editGrievanceTypeInsrtuctn(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				model = new ModelAndView("product/addGrievInstruct");

				String base64encodedString = request.getParameter("grievId");
				String grievTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("grievTypeId", Integer.parseInt(grievTypeId));
				map.add("compId", companyId);

				GrievencesTypeInstructn griev = Constant.getRestTemplate()
						.postForObject(Constant.url + "getGrievTypeInstructById", map, GrievencesTypeInstructn.class);
				model.addObject("griev", griev);

				model.addObject("title", "Edit Grievances Type Instruction");
			} catch (Exception e) {
				System.out.println("Execption in /editGrievanceTypeInsrtuctn : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}

		// Author-Mahendra Singh Created On-01-08-2020
		// Modified By-Mahendra Singh Created On-01-08-2020
		// Desc- Delete Grievance Type Instruction.
		@RequestMapping(value = "/deleteGrievanceType", method = RequestMethod.GET)
		public String deleteGrievanceType(HttpServletRequest request, HttpServletResponse response) {

			HttpSession session = request.getSession();
			try {

				String base64encodedString = request.getParameter("grievId");
				String grievTypeId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("grievTypeId", Integer.parseInt(grievTypeId));

				Info res = Constant.getRestTemplate().postForObject(Constant.url + "deleteGrievTypeInstructById", map,
						Info.class);

				if (!res.getError()) {
					session.setAttribute("successMsg", res.getMessage());
				} else {
					session.setAttribute("errorMsg", res.getMessage());
				}

			} catch (Exception e) {
				System.out.println("Execption in /deleteGrievanceType : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showGrievencesTypeIntructn";
		}
		
		/****************************************************************************************/
		
		@RequestMapping(value = "/showRelatedProductsList", method = RequestMethod.GET)
		public ModelAndView showRelatedroductConfigure(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView model = null;
			//List<GetOfferFrConfiguredList> list = new ArrayList<GetOfferFrConfiguredList>();
			try {
				model = new ModelAndView("product/relatedProductConfigList");
				
				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", userDetail.getUser().getCompanyId());
				
				GetProductRelatedList[] areaArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllRelatedProducts", map,
						GetProductRelatedList[].class);
				List<GetProductRelatedList> list = new ArrayList<GetProductRelatedList>(Arrays.asList(areaArr));
				
				for (int i = 0; i < list.size(); i++) {

					list.get(i).setExVar1(
							FormValidation.Encrypt(String.valueOf(list.get(i).getRelatedProductId())));
					
					list.get(i).setExVar2(
							FormValidation.Encrypt(String.valueOf(list.get(i).getProductId())));
				}
				model.addObject("relatedProductList", list);
				
				model.addObject("title", "Related Poduct Configuration List");
				
			}catch (Exception e) {
				System.out.println("Execption in /showOfferConfigurationList : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}
		
		// Author-Mahendra Singh Created On-25-07-2020
		// Modified By-Mahendra Singh Created On-25-07-2020
		// Desc- Related Products.
		@RequestMapping(value = "/configRelatedProduct", method = RequestMethod.GET)
		public ModelAndView showRelatedProductsList(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = new ModelAndView("product/relatedProductConfig");

			try {
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");
					
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();		
				
				
				ConfigRelatedProduct product = new ConfigRelatedProduct();
				model.addObject("product", product);
				
				GetItem[] itemArr = Constant.getRestTemplate().getForObject(Constant.url + "getAllItems", GetItem[].class);
				List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(itemArr));
				model.addObject("itemList", itemList);

				map.add("compId", companyId);
				Category[] catArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllCategory", map, Category[].class);
				List<Category>	catList = new ArrayList<Category>(Arrays.asList(catArr));
				model.addObject("catList", catList);

				System.out.println("mCategoryList List---------" + catList);
				model.addObject("catList", catList);

				model.addObject("title", "Configure Related Product");

			} catch (Exception e) {
				System.out.println("Execption in /configTagItems : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}
		
		GetConfiguredItemsList configItemList = new GetConfiguredItemsList();
		
		@RequestMapping(value = "/getConfiguredItemsList", method = RequestMethod.GET)
		@ResponseBody
		public GetConfiguredItemsList getConfiguredItemTags(HttpServletRequest request, HttpServletResponse response) {
			
			configItemList = new GetConfiguredItemsList();
			try {
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");
				
				int itemId = Integer.parseInt(request.getParameter("itemId"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("itemId", itemId);

				GetItem[] itemArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllItemsByitemId", map, GetItem[].class);
				List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(itemArr));

				configItemList.setItemsList(itemList);

				map = new LinkedMultiValueMap<>();
				map.add("compId", companyId);
				Category[] catArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllCategory", map, Category[].class);
				List<Category>	catList = new ArrayList<Category>(Arrays.asList(catArr));
				
				configItemList.setCatList(catList);

				System.out.println("Config List---------" + itemList);

			} catch (Exception e) {
				System.out.println("Execption in /configTagItems : " + e.getMessage());
				e.printStackTrace();
			}
			return configItemList;
		}
		
		
		
		@RequestMapping(value = "/saveRelatedItemConfigure", method = RequestMethod.POST)
		public String saveRelatedItemConfigure(HttpServletRequest request, HttpServletResponse response) {
			try {
				System.err.println("saveFrConfiguration--- ");
				
				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");
				
				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				String itemIdsStr = "";

				int itemId = Integer.parseInt(request.getParameter("itemId"));
				
				String[] itemIds = request.getParameterValues("chk");
				if (itemIds.length > 0) {
					StringBuilder sb = new StringBuilder();
					for (String s : itemIds) {
						sb.append(s).append(",");
					}
					itemIdsStr = sb.deleteCharAt(sb.length() - 1).toString();
					System.out.println("Product---"+itemId+" **** "+itemIdsStr);

				}
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("itemId", itemId);
				
				ConfigRelatedProduct itemRes  = Constant.getRestTemplate().postForObject(Constant.url + "getProductInfoById", map,
						ConfigRelatedProduct.class);
				System.out.println("itemRes----"+itemRes);
				
				
				if(itemRes==null) {
					int id = 0;
					try {
					id = Integer.parseInt(request.getParameter("relatedPrdctId"));
					}catch (Exception e) {
						e.printStackTrace();
						id=0;
					}
					ConfigRelatedProduct product = new ConfigRelatedProduct();
					
					product.setDelStatus(0);
					product.setExInt1(userDetail.getUser().getCompanyId());
					product.setExInt2(0);
					product.setExVar1("NA");
					product.setExVar2("NA");
					product.setProductId(itemId);
					product.setIsActive(0);
					product.setMakerDatetime(sf.format(date));
					product.setMakerUserId(userDetail.getUser().getUserId());
					product.setConfigrRelatedProductIds(itemIdsStr);
					product.setRelatedProductId(id);
					product.setUpdatedDateTime(sf.format(date));
					
					System.out.println("Configure Offer---------------"+product);
					
					ConfigRelatedProduct productRes  = Constant.getRestTemplate().postForObject(Constant.url + "addRelatedProductConfig", product,
							ConfigRelatedProduct.class);
					
					if (productRes!=null)
	                    session.setAttribute("successMsg", "Related Product Configure Successfully");
					else
						session.setAttribute("errorMsg", "Failed to Configure Related Product");
				}else {
					 map = new LinkedMultiValueMap<>();
					 map.add("relatedItemIds", itemIdsStr);
					 map.add("productId", itemId);
					 map.add("updateDatTime", sf.format(date));
					 map.add("userId", userDetail.getUser().getUserId());
				
				Info res =  Constant.getRestTemplate().postForObject(Constant.url + "updateRelatedProductConfig", map,
						Info.class);
				
				if (!res.getError())
                    session.setAttribute("successMsg", res.getMessage());
				else
					session.setAttribute("errorMsg", res.getMessage());
				}
				
				
				
			}catch (Exception e) {
				System.out.println("Execption in /saveRelatedItemConfigure : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showRelatedProductsList";
		}
		
		
		@RequestMapping(value = "/editRelatedProduct", method = RequestMethod.GET)
		public ModelAndView editRelatedProduct(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView model = null;
			try {
				model = new ModelAndView("product/relatedProductConfig");				
				
				String base64encodedString = request.getParameter("relatedProductId");
				String relatedProductId = FormValidation.DecodeKey(base64encodedString);
				
				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", userDetail.getUser().getCompanyId());
				
				GetItem[] itemArr = Constant.getRestTemplate().getForObject(Constant.url + "getAllItems", GetItem[].class);
				List<GetItem> itemList = new ArrayList<GetItem>(Arrays.asList(itemArr));
				model.addObject("itemList", itemList);

				map = new LinkedMultiValueMap<>();
				map.add("compId", userDetail.getUser().getCompanyId());
				Category[] catArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllCategory", map, Category[].class);
				List<Category>	catList = new ArrayList<Category>(Arrays.asList(catArr));

				System.out.println("mCategoryList List---------" + catList);
				model.addObject("catList", catList);

				model.addObject("title", "Configure Items And Tag");
				
				model.addObject("relProductId", relatedProductId);
				
				model.addObject("title", "Edit Configure Related Product");
				
				
			}catch (Exception e) {
				System.out.println("Execption in /editRelatedProduct : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}
		
		
		@RequestMapping(value = "/deleteRelatedProduct", method = RequestMethod.GET)
		public String deleteRelatedProduct(HttpServletRequest request, HttpServletResponse response) {

			HttpSession session = request.getSession();
			try {

				String base64encodedString = request.getParameter("relatedProductId");
				String relatedProductId = FormValidation.DecodeKey(base64encodedString);
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("relatedProductId", Integer.parseInt(relatedProductId));

				Info info = Constant.getRestTemplate().postForObject(Constant.url + "deleteRelatedProductById", map,
						Info.class);

				if (!info.getError()) {
					session.setAttribute("successMsg", info.getMessage());
				} else {
					session.setAttribute("errorMsg", info.getMessage());
				}

			} catch (Exception e) {
				System.out.println("Execption in /deleteFrOfferConfig : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showRelatedProductsList";
		}
		
		/******************************************************************************************/
		

}
