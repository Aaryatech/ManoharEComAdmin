package com.ats.manoharadmin.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.manoharadmin.common.Commons;
import com.ats.manoharadmin.common.Constant;
import com.ats.manoharadmin.common.FormValidation;
import com.ats.manoharadmin.models.CatImgBean;
import com.ats.manoharadmin.models.Category;
import com.ats.manoharadmin.models.Images;
import com.ats.manoharadmin.models.Info;
import com.ats.manoharadmin.models.UpdateImageSeqNo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@Scope("session")
public class ProductController {

	@RequestMapping(value = "/showCategories", method = RequestMethod.GET)
	public ModelAndView categoryList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

		model = new ModelAndView("product/categoryList");

		HttpSession session = request.getSession();
		int companyId = (int) session.getAttribute("companyId");
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();		
		map.add("compId", companyId);
		
		Category[] catArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllCategory", map, Category[].class);
		List<Category>	catList = new ArrayList<Category>(Arrays.asList(catArr));
		
		for (int i = 0; i < catList.size(); i++) {

			catList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(catList.get(i).getCatId())));
		}
		
		System.out.println("Cat List----------------"+catList);
		
		model.addObject("catList", catList);		
		
		model.addObject("title", "Category List");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		Category category = new Category();
		model = new ModelAndView("product/addCategory");

		model.addObject("title", "Add Category");

		return model;
	}
	
//	@RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
//	@ResponseBody
//	public Category saveCategory(HttpServletRequest request, HttpServletResponse response) {
//		Category res = new Category();
//		try {
//			int catId= Integer.parseInt(request.getParameter("cat_id"));
//			String catName = request.getParameter("cat_name");
//			String catCode = request.getParameter("cat_code");
//			String description = request.getParameter("description");
//			String sortNo = request.getParameter("sort_no");
//			System.out.println("In saveCategory"+" "+catName+" "+catCode+" "+description+" "+sortNo);
//	
//			HttpSession session = request.getSession();
//			int companyId = (int) session.getAttribute("companyId");
//			
//			Category category = new Category();
//			
//			category.setCategoryCode(catCode);
//			category.setCatId(catId);
//			category.setCatName(catName);
//			category.setCompanyId(companyId);
//			category.setDelStatus(0);
//			category.setDescription(description);
//			category.setExInt1(0);
//			category.setExInt2(0);
//			category.setExVar1("NA");
//			category.setExVar2("NA");
//			category.setItemImage("NA");
//			category.setSeqNo(Integer.parseInt(sortNo));
//			
//			res = Constant.getRestTemplate().postForObject(Constant.url + "insertCategory", category, Category.class); 
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return res;
//	}
	
	
	@RequestMapping(value = "/getCatCode", method = RequestMethod.GET)
	@ResponseBody
	public Info getCatCode(HttpServletRequest request, HttpServletResponse response) {
		Info info = new Info();
		String catCode = "";
		try {
			int catId = 0;
			try {
				catId = Integer.parseInt(request.getParameter("catId"));
			}catch (Exception e) {
				e.printStackTrace();
				catId = 0;
			}
			
			String catName = request.getParameter("catName");
			
			
			System.out.println("Cat Name: "+catName);
			
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");
			
			char ch = catName.charAt(0);			
			System.out.println("Character at 0 index is: "+ch);																		
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			
			map.add("catName", catName);
			map.add("catId", catId);
			map.add("compId", companyId);
			Category res = Constant.getRestTemplate().postForObject(Constant.url + "getCategoryByName", map, Category.class); 
			
			map.add("catName", catName);
			map.add("compId", companyId);
			
			Integer cat = Constant.getRestTemplate().postForObject(Constant.url + "getCatCodeCount", map, Integer.class); 
	
			catCode = ch+String.format("%03d", (cat + 1));
			System.out.println("Cat Code----"+catCode);
			
			if(res!=null) {
				info.setError(true);
				info.setMessage(catCode);
			}else {
				info.setError(false);
				info.setMessage(catCode);
			}
		}catch (Exception e) {
			e.printStackTrace();
			
			info.setError(true);
			info.setMessage(catCode);			
		}
		return info;
	}
	
	
	@RequestMapping(value = "/saveCategoryImages", method = RequestMethod.POST)
	public String saveCategoryAndSubCategoryImages(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("files") List<MultipartFile> files) {
		try {
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");
			
			int catId=0;
			try {
			 catId= Integer.parseInt(request.getParameter("cat_id"));
			}catch (Exception e) {
				e.printStackTrace();
				catId=0;
			}
			String catName = request.getParameter("cat_name");
			String catCode = request.getParameter("cat_code");
			String description = request.getParameter("description");
			String sortNo = request.getParameter("sort_no");
			System.out.println("In saveCategory"+" "+catName+" "+catCode+" "+description+" "+sortNo);
	
			
			
			Category category = new Category();
			
			category.setCategoryCode(catCode);
			category.setCatId(catId);
			category.setCatName(catName);
			category.setCompanyId(companyId);
			category.setDelStatus(0);
			category.setDescription(description);
			category.setExInt1(0);
			category.setExInt2(0);
			category.setExVar1("NA");
			category.setExVar2("NA");
			category.setItemImage("NA");
			category.setSeqNo(Integer.parseInt(sortNo));
			
			Category res = Constant.getRestTemplate().postForObject(Constant.url + "insertCategory", category, Category.class); 
			
			
			if(res!=null) {
			
				System.err.println("saveCategoryImages--- " + files.size());
				int imageId = 0;
				try {
						imageId = Integer.parseInt(request.getParameter("imgId"));
				}catch (Exception e) {
					e.printStackTrace();
				}
			
			int type = 1;
			System.err.println("TYPE - " + type);
			int selectId = res.getCatId();
			System.err.println("selectId - " + selectId);

			List<Images> imageList = new ArrayList<>();

			if (files.size() > 0) {

				for (int i = 0; i < files.size(); i++) {

					String ext = files.get(i).getOriginalFilename().split("\\.")[1];
					String fileName = Commons.getCurrentTimeStamp() + "." + ext;
					new ImageUploadController().saveUploadedFiles(files.get(i), 1, fileName);

					Images images = new Images(imageId, type, selectId, fileName, (i + 1), 0, 0, companyId, 0, 0, "", "", "", "", 0,
							0, 0);
					imageList.add(images);
				}

				Info info = Constant.getRestTemplate().postForObject(Constant.url + "saveMultipleImage", imageList,
						Info.class);

				if (info.getError()) {
					session.setAttribute("errorMsg", info.getMessage());
				} else {
					session.setAttribute("successMsg", info.getMessage());
				}

			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showCategories";
	}
	
	List<Images> imageList = new ArrayList<>();

	@RequestMapping(value = "/editCategory", method = RequestMethod.GET)
	public ModelAndView getImagesByDocId(HttpServletRequest request, HttpServletResponse response) {
		CatImgBean catImg = new CatImgBean();
		ModelAndView model = null;
		try {
			
			model = new ModelAndView("product/addCategory");

			model.addObject("title", "Edit Category");
			
			String base64encodedString = request.getParameter("catId");
			String categoryId = FormValidation.DecodeKey(base64encodedString); 
			
			int catId = Integer.parseInt(categoryId);
			System.err.println("catId - " + catId);
	
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("catId", catId);
			
			Category res = Constant.getRestTemplate().postForObject(Constant.url + "getCategoryById", map, Category.class); 
			
			catImg.setCategory(res);
			
			imageList = new ArrayList<>();
			map.add("docId", catId);
			Images[] imgArr = Constant.getRestTemplate().postForObject(Constant.url + "getImagesByDocIdAndDocType", map,
					Images[].class);
			imageList = new ArrayList<Images>(Arrays.asList(imgArr));
			catImg.setImgList(imageList);
			
			System.err.println("Category Beans=-------"+catImg);
			model.addObject("catImg", catImg);
			model.addObject("imgPath", Constant.showDocSaveUrl);
			
			
			model.addObject("isEdit", 1);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/deleteImageAjax", method = RequestMethod.GET)
	public @ResponseBody Info deleteImageAjax(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();

		int imageId = Integer.parseInt(request.getParameter("imageId"));
		String imageName = request.getParameter("imageName");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("imageId", imageId);

		info = Constant.getRestTemplate().postForObject(Constant.url + "deleteByImageId", map, Info.class);

		try {

			if (!info.getError()) {
				File f = new File(Constant.UPLOAD_URL + imageName);
				f.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return info;
	}
	
	
	@RequestMapping(value = "/updateImageSequenceOrderAjax", method = RequestMethod.GET)
	public @ResponseBody Info updateImageSequenceOrderAjax(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();

		try {

			String jsonStr = request.getParameter("json");

			ObjectMapper mapper = new ObjectMapper();
			List<UpdateImageSeqNo> imgSeqList = mapper.readValue(jsonStr, new TypeReference<List<UpdateImageSeqNo>>() {
			});

			if (imgSeqList != null) {

				List<Images> imgList = new ArrayList<>();

				for (int i = 0; i < imgSeqList.size(); i++) {
					for (int j = 0; i < imageList.size(); j++) {

						System.err.println(i + " aa " + imgSeqList.get(i).getId());

						if (imgSeqList.get(i).getId() == imageList.get(j).getImagesId()) {
							
							Images img = imageList.get(j);
							img.setSeqNo(imgSeqList.get(i).getSeq());
							imgList.add(img);
							break;
						}
					}
				}
				//System.err.println("SAVE MODEL - " + imgList);
				info = Constant.getRestTemplate().postForObject(Constant.url + "saveMultipleImage", imgList,
						Info.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return info;
	}
	
	@RequestMapping(value = "/getImagesByDocIdAndDocType", method = RequestMethod.GET)
	public @ResponseBody List<Images> getImagesByDocIdAndDocType(HttpServletRequest request, HttpServletResponse response) {

		int type = Integer.parseInt(request.getParameter("type"));
		System.err.println("TYPE - " + type);
		int selectId = Integer.parseInt(request.getParameter("selectId"));
		System.err.println("selectId - " + selectId);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("docId", selectId);
		map.add("docType", type);

		imageList = new ArrayList<>();

		Images[] imgArr = Constant.getRestTemplate().postForObject(Constant.url + "getImagesByDocId", map,
				Images[].class);
		imageList = new ArrayList<Images>(Arrays.asList(imgArr));

		return imageList;
	}
	
	
	// Author-Mahendra Singh Created On-20-07-2020
	// Modified By-Mahendra Singh Created On-20-07-2020
	// Desc- Delete Category.
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
	public String deleteCategory(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		try {

			String base64encodedString = request.getParameter("catId");
			String catId = FormValidation.DecodeKey(base64encodedString);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("catId", Integer.parseInt(catId));

			Info res = Constant.getRestTemplate().postForObject(Constant.url + "deleteCategoryById", map, Info.class);

				if (!res.getError()) {
					session.setAttribute("successMsg", res.getMessage());
				} else {
						session.setAttribute("errorMsg", res.getMessage());
				}

			} catch (Exception e) {
					System.out.println("Execption in /deleteCategory : " + e.getMessage());
					e.printStackTrace();
			}
				return "redirect:/showCategories";
		}

}