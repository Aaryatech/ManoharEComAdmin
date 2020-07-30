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
import java.util.Random;

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
import com.ats.manoharadmin.models.Flavour;
import com.ats.manoharadmin.models.Images;
import com.ats.manoharadmin.models.Info;
import com.ats.manoharadmin.models.SubCat;
import com.ats.manoharadmin.models.Tags;
import com.ats.manoharadmin.models.UpdateImageSeqNo;
import com.ats.manoharadmin.models.login.UserResponse;
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
		HttpSession session = request.getSession();
		try {
			
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
			System.out.println("In saveCategory"+" "+catName+" "+catCode+" "+description+" "+sortNo+" "+catId);
	
			
			
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
				try {
				for (int i = 0; i < files.size(); i++) {
					Random randNum = new Random();
					String ext = files.get(i).getOriginalFilename().split("\\.")[1];
					String fileName = Commons.getCurrentTimeStamp()+"_"+randNum.nextInt(5*1000)+ "." + ext;
					new ImageUploadController().saveUploadedFiles(files.get(i), 1, fileName);

					Images images = new Images(imageId, type, selectId, fileName, (i + 1), 0, 0, companyId, 0, 0, "", "", "", "", 0,
							0, 0);
					imageList.add(images);
				}
				}catch (Exception e) {
					e.printStackTrace();
				}

				Info info = Constant.getRestTemplate().postForObject(Constant.url + "saveMultipleImage", imageList,
						Info.class);

				int flag = 0;
				if (info.getError()){	
					flag = 1;
				} else {					
					flag = 0;
				}
				
				if(res.getCatId()>0) {
					if(catId==0) {
						session.setAttribute("successMsg", "Category Saved SuccessFully");
					}else {
						session.setAttribute("successMsg", "Category Update SuccessFully");
					}
				}else {
					session.setAttribute("errorMsg", "Failed To Save Category");
				}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMsg", "Failed To Save Category");
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
			
			//System.err.println("Category Beans=-------"+catImg);
			model.addObject("catImg", catImg);
			model.addObject("imgPath", Constant.showDocSaveUrl);			
		
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

	/**********************************************************************/
	
	@RequestMapping(value = "/showSubCategories", method = RequestMethod.GET)
	public ModelAndView showSubCategories(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {

		model = new ModelAndView("product/subCategoryList");

		HttpSession session = request.getSession();
		int companyId = (int) session.getAttribute("companyId");
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();		
		map.add("compId", companyId);
		
		SubCat[] catArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllSubCategory", map, SubCat[].class);
		List<SubCat>subCatList = new ArrayList<SubCat>(Arrays.asList(catArr));
		
		for (int i = 0; i < subCatList.size(); i++) {

			subCatList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(subCatList.get(i).getSubCatId())));
		}
		
		System.out.println("Sub Cat List----------------"+subCatList);
		
		model.addObject("subCatList", subCatList);		
		
		model.addObject("title", "Sub-Category List");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "/addSubCategory", method = RequestMethod.GET)
	public ModelAndView addSubCategory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		Category category = new Category();
		model = new ModelAndView("product/addSubCategory");
		
		HttpSession session = request.getSession();
		int companyId = (int) session.getAttribute("companyId");
		
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();		
		map.add("compId", companyId);
		
		Category[] catArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllCategory", map, Category[].class);
		List<Category>	catList = new ArrayList<Category>(Arrays.asList(catArr));
		
		model.addObject("catList", catList);
		
		model.addObject("title", "Add Sub-Category");

		return model;
	}
	
	@RequestMapping(value = "/saveSubCategoryImages", method = RequestMethod.POST)
	public String saveSubCategoryImages(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("files") List<MultipartFile> files) {
		try {
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");
			
			int subCatId=0;
			try {
				subCatId= Integer.parseInt(request.getParameter("sub_cat_id"));
			}catch (Exception e) {
				e.printStackTrace();
				subCatId=0;
			}
			int catId = Integer.parseInt(request.getParameter("category"));
			String subCatName = request.getParameter("sub_cat_name");
			String prefix = request.getParameter("prefix");
			String description = request.getParameter("description");
			String sortNo = request.getParameter("sort_no");
			
			System.out.println("In saveCategory" +subCatId+" "+subCatName+" "+description+" "+sortNo);	
			
			SubCat subCat = new SubCat();
			
			subCat.setCatId(catId);
			subCat.setSubCatId(subCatId);
			subCat.setSubCatName(subCatName);
			subCat.setPrefix(prefix);
			subCat.setCompanyId(companyId);
			subCat.setDelStatus(0);
			subCat.setExInt1(0);
			subCat.setExInt2(0);
			subCat.setExVar1("NA");
			subCat.setExVar2("NA");
			subCat.setSeqNo(Integer.parseInt(sortNo));
			
			SubCat res = Constant.getRestTemplate().postForObject(Constant.url + "insertSubCategory", subCat, SubCat.class); 
			
			
			if(res!=null) {
			
				System.err.println("saveCategoryImages--- " + files.size());				
			
			int type = 2;
			System.err.println("TYPE - " + type);
			int selectId = res.getSubCatId();
			System.err.println("selectId - " + selectId);

			List<Images> imageList = new ArrayList<>();

			if (files.size() > 0) {
				try {
					for (int i = 0; i < files.size(); i++) {	
						Random randNum = new Random();
							
						String ext = files.get(i).getOriginalFilename().split("\\.")[1];
						String fileName = Commons.getCurrentTimeStamp()+"_"+randNum.nextInt(5*1000)+ "." + ext;
						new ImageUploadController().saveUploadedFiles(files.get(i), 1, fileName);
	
						Images images = new Images(0, type, selectId, fileName, (i + 1), 0, 0, companyId, 0, 0, "", "", "", "", 0,
								0, 0);
						imageList.add(images);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				Info info = Constant.getRestTemplate().postForObject(Constant.url + "saveMultipleImage", imageList,
						Info.class);

				int flag = 0;
				if (info.getError()){	
					flag = 1;
				} else {					
					flag = 0;
				}
				
				if(res.getCatId()>0) {
					if(subCatId==0) {
						session.setAttribute("successMsg", "Sub-Category Saved SuccessFully");
					}else {
						session.setAttribute("successMsg", "Sub-Category Update SuccessFully");
					}
				}else {
					session.setAttribute("errorMsg", "Failed To Save Sub-Category");
				}

			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showSubCategories";
	}
	
	

	@RequestMapping(value = "/editSubCategory", method = RequestMethod.GET)
	public ModelAndView editSubCategory(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		try {
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");
			
			model = new ModelAndView("product/addSubCategory");

			model.addObject("title", "Edit Category");
			
			String base64encodedString = request.getParameter("subCatId");
			String subcatid = FormValidation.DecodeKey(base64encodedString); 
			
			int subCatId = Integer.parseInt(subcatid);
			System.err.println("catId - " + subCatId);
	
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("subCatId", subCatId);
			
			SubCat subCat = Constant.getRestTemplate().postForObject(Constant.url + "getSubCategoryById", map, SubCat.class); 
			
			model.addObject("subCat", subCat);		
			
			map = new LinkedMultiValueMap<>();		
			map.add("compId", companyId);
			Category[] catArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllCategory", map, Category[].class);
			List<Category>	catList = new ArrayList<Category>(Arrays.asList(catArr));
			
			model.addObject("catList", catList);
			
			model.addObject("imgPath", Constant.showDocSaveUrl);
			
			model.addObject("title", "Edit Sub-Category");
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	
	
	// Author-Mahendra Singh Created On-20-07-2020
	// Modified By-Mahendra Singh Created On-20-07-2020
	// Desc- Delete Category.
	@RequestMapping(value = "/deleteSubCategory", method = RequestMethod.GET)
		public String deleteSubCategory(HttpServletRequest request, HttpServletResponse response) {

			HttpSession session = request.getSession();
			try {

				String base64encodedString = request.getParameter("subCatId");
				String subCatId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("subCatId", Integer.parseInt(subCatId));

				Info res = Constant.getRestTemplate().postForObject(Constant.url + "deleteSubCategoryById", map, Info.class);

					if (!res.getError()) {
						session.setAttribute("successMsg", res.getMessage());
					} else {
							session.setAttribute("errorMsg", res.getMessage());
					}

				} catch (Exception e) {
						System.out.println("Execption in /deleteSubCategory : " + e.getMessage());
						e.printStackTrace();
				}
					return "redirect:/showSubCategories";
			}
	
	
	@RequestMapping(value = "/chkUnqSubCat", method = RequestMethod.GET)
	@ResponseBody
	public Info chkUnqSubCat(HttpServletRequest request, HttpServletResponse response) {
		Info info = new Info();
		try {
			int subCatId = 0;
			try {
				subCatId = Integer.parseInt(request.getParameter("subCatId"));
			}catch (Exception e) {
				e.printStackTrace();
				subCatId = 0;
			}
			
			String subCatName = request.getParameter("subCatName");			
			
			System.out.println("Cat Name: "+subCatName);
			
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			
			map.add("subCatName", subCatName);
			map.add("subCatId", subCatId);
			map.add("compId", companyId);
			SubCat res = Constant.getRestTemplate().postForObject(Constant.url + "getSubCategoryByName", map, SubCat.class); 
			
			if(res!=null) {
				info.setError(true);
				info.setMessage("Sub-Cat Found");
			}else {
				info.setError(false);
				info.setMessage("Sub-Cat Not Found");
			}
			System.out.println("Res--------"+info);
		}catch (Exception e) {
			e.printStackTrace();
			
			info.setError(true);
			info.setMessage("Sub-Cat Found");			
		}
		return info;
	}
	
	@RequestMapping(value = "/chkUnqPrefix", method = RequestMethod.GET)
	@ResponseBody
	public Info chkUnqPrefix(HttpServletRequest request, HttpServletResponse response) {
		Info info = new Info();
		try {
			int subCatId = 0;
			try {
				subCatId = Integer.parseInt(request.getParameter("subCatId"));
			}catch (Exception e) {
				e.printStackTrace();
				subCatId = 0;
			}
			
			String prefix = request.getParameter("prefix");			
			
			System.out.println("Cat prefix: "+prefix);
			
			HttpSession session = request.getSession();
			int companyId = (int) session.getAttribute("companyId");
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			
			map.add("prefix", prefix);
			map.add("subCatId", subCatId);
			map.add("compId", companyId);
			SubCat res = Constant.getRestTemplate().postForObject(Constant.url + "getSubCategoryByPrefix", map, SubCat.class); 
			
			if(res!=null) {
				info.setError(true);
				info.setMessage("Sub-Cat Found");
			}else {
				info.setError(false);
				info.setMessage("Sub-Cat Not Found");
			}
			System.out.println("Res--------"+info);
		}catch (Exception e) {
			e.printStackTrace();
			
			info.setError(true);
			info.setMessage("Sub-Cat Found");			
		}
		return info;
	}
	
	/*************************************************************************/
	// Author-Mahendra Singh Created On-30-07-2020
		// Modified By-Mahendra Singh Created On-30-07-2020
		// Desc- Show Tag List.
		@RequestMapping(value = "/showTagList", method = RequestMethod.GET)
		public ModelAndView showTagList(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			List<Tags> tagList = new ArrayList<Tags>();
			try {
				
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");
				
				model = new ModelAndView("product/tagList");
				model.addObject("title", "Tag List");
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();				
				map.add("compId", companyId);

				Tags[] tagArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllTags", map, Tags[].class);
				tagList = new ArrayList<Tags>(Arrays.asList(tagArr));

				for (int i = 0; i < tagList.size(); i++) {

					tagList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(tagList.get(i).getTagId())));
				}
				model.addObject("tagList", tagList);

			} catch (Exception e) {
				System.out.println("Execption in /showTagList : " + e.getMessage());
				e.printStackTrace();
			}

			return model;
		}
	
	
		// Author-Mahendra Singh Created On-30-07-2020
		// Modified By-Mahendra Singh Created On-30-07-2020
		// Desc- Add Tag List.
		@RequestMapping(value = "/addTag", method = RequestMethod.GET)
		public ModelAndView addNewTag(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = new ModelAndView("product/addTag");

			Tags tag = new Tags();

			model.addObject("tag", tag);

			model.addObject("title", "Add Tag");

			return model;
		}
		
		// Author-Mahendra Singh Created On-30-07-2020
		// Modified By-Mahendra Singh Created On-30-07-2020
		// Desc- Insert Tag List.
		@RequestMapping(value = "/saveTag", method = RequestMethod.POST)
		public String saveTag(HttpServletRequest request, HttpServletResponse response) {
			try {
				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");

				Tags tag = new Tags();

				int tagId = Integer.parseInt(request.getParameter("tag_id"));
				tag.setExInt1(userDetail.getUser().getCompanyId());
				tag.setExInt2(0);
				tag.setExVar1("NA");
				tag.setExVar2("NA");
				tag.setTagDeleteStatus(0);
				tag.setTagDesc(request.getParameter("tag_desc"));
				tag.setTagId(tagId);
				tag.setTagIsActive(Integer.parseInt(request.getParameter("r_tag")));
				tag.setTagName(request.getParameter("tag_name"));
				tag.setTagSortNumber(1);

				Tags tagRes = Constant.getRestTemplate().postForObject(Constant.url + "saveNewTag", tag, Tags.class);

				if (tagRes.getTagId() > 0) {
					if (tagId == 0)
						session.setAttribute("successMsg", "Tag Saved Sucessfully");
					else
						session.setAttribute("successMsg", "Tag Update Sucessfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save Tag");
				}
			} catch (Exception e) {
				System.out.println("Execption in /saveTag : " + e.getMessage());
				e.printStackTrace();
			}

			return "redirect:/showTagList";
		}

		// Author-Mahendra Singh Created On-30-07-2020
		// Modified By-Mahendra Singh Created On-30-07-2020
		// Desc- Edit Tag List.
		@RequestMapping(value = "/editTag", method = RequestMethod.GET)
		public ModelAndView editTag(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("product/addTag");

				String base64encodedString = request.getParameter("tagId");
				String tagId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("tagId", Integer.parseInt(tagId));

				Tags tag = Constant.getRestTemplate().postForObject(Constant.url + "getTagById", map, Tags.class);
				model.addObject("tag", tag);

				model.addObject("title", "Edit Tag");
			} catch (Exception e) {
				System.out.println("Execption in /editTag : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}

		// Author-Mahendra Singh Created On-30-07-2020
		// Modified By-Mahendra Singh Created On-30-07-2020
		// Desc- Delete Tag.
		@RequestMapping(value = "/deleteTag", method = RequestMethod.GET)
		public String deleteTag(HttpServletRequest request, HttpServletResponse response) {

			HttpSession session = request.getSession();
			try {

				String base64encodedString = request.getParameter("tagId");
				String tagId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("tagId", Integer.parseInt(tagId));
				
				Info info = Constant.getRestTemplate().postForObject(Constant.url + "deleteTagById", map, Info.class);
				if (!info.getError()) {
					session.setAttribute("successMsg", info.getMessage());
				} else {
					session.setAttribute("errorMsg", info.getMessage());
				}

//				Info res = Constant.getRestTemplate().postForObject(Constant.url + "isTagAssign", map, Info.class);
//				if (!res.getError()) {
//					session.setAttribute("errorMsg", res.getMessage());
//				} else {
//					Info info = Constant.getRestTemplate().postForObject(Constant.url + "deleteTagById", map, Info.class);
//
//					if (!info.getError()) {
//						session.setAttribute("successMsg", info.getMessage());
//					} else {
//						session.setAttribute("errorMsg", info.getMessage());
//					}
//				}

			} catch (Exception e) {
				System.out.println("Execption in /deleteTag : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showTagList";
		}
		
		
		
		// Author-Mahendra Singh Created On-30-07-2020
		// Modified By-Mahendra Singh Created On-30-07-2020
		// Desc- Flavour List.
		@RequestMapping(value = "/showFlavourList", method = RequestMethod.GET)
		public ModelAndView showFlavourList(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView model = new ModelAndView("product/flavourList");
			try {			
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();				
				map.add("compId", companyId);

				Flavour[] flavourArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllFlavours", map, Flavour[].class);
				List<Flavour> flavourList = new ArrayList<Flavour>(Arrays.asList(flavourArr));

				for (int i = 0; i < flavourList.size(); i++) {

					flavourList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(flavourList.get(i).getFlavourId())));
				}
				model.addObject("flavourList", flavourList);
				model.addObject("title", "Flavour List");
			}catch (Exception e) {
				System.out.println("Execption in /showFlavourList : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}
		
		
				// Author-Mahendra Singh Created On-30-07-2020
				// Modified By-Mahendra Singh Created On-30-07-2020
				// Desc- Add Flavour.
				@RequestMapping(value = "/addFlavour", method = RequestMethod.GET)
				public ModelAndView addFlavour(HttpServletRequest request, HttpServletResponse response) {
					ModelAndView model = new ModelAndView("product/addFlavour");
					try {
			
						Flavour flavour = new Flavour();
						
						model.addObject("flavour", flavour);
						model.addObject("title", "Add Flavour");
					}catch (Exception e) {
						System.out.println("Execption in /addFlavour : " + e.getMessage());
						e.printStackTrace();
					}
					return model;
				}
				
				
				@RequestMapping(value = "/addNewFlavour", method = RequestMethod.POST)
				public String insertFlavour(HttpServletRequest request, HttpServletResponse response) {
					
					try {
						HttpSession session = request.getSession();
						int companyId = (int) session.getAttribute("companyId");
			
						Flavour flavour = new Flavour();
						
						int flavourId = Integer.parseInt(request.getParameter("flavour_id"));
						
						flavour.setCompanyId(companyId);
						flavour.setDelStatus(0);
						flavour.setDescription(request.getParameter("description"));
						flavour.setExInt1(0);
						flavour.setExInt2(0);
						flavour.setExVar1("NA");
						flavour.setExVar2("NA");
						flavour.setFlavourCode(request.getParameter("flavour_code"));
						flavour.setFlavourId(flavourId);
						flavour.setFlavourName(request.getParameter("flavour_name"));
						flavour.setRate(Float.parseFloat(request.getParameter("rate")));
						flavour.setSeqNo(Integer.parseInt(request.getParameter("seq_no")));
						
						Flavour favRes = Constant.getRestTemplate().postForObject(Constant.url + "insertFlavour", flavour, Flavour.class);

						if (favRes.getFlavourId()> 0) {
							if (flavourId == 0)
								session.setAttribute("successMsg", "Flavour Saved Sucessfully");
							else
								session.setAttribute("successMsg", "Flavour Update Sucessfully");
						} else {
							session.setAttribute("errorMsg", "Failed to Save Flavour");
						}
						
					}catch (Exception e) {
						System.out.println("Execption in /insertFlavour : " + e.getMessage());
						e.printStackTrace();
					}
					return "redirect:/showFlavourList";
				}
				
				
				
				
				// Author-Mahendra Singh Created On-30-07-2020
				// Modified By-Mahendra Singh Created On-30-07-2020
				// Desc- Edit Tag List.
				@RequestMapping(value = "/editFlavour", method = RequestMethod.GET)
				public ModelAndView editFlavour(HttpServletRequest request, HttpServletResponse response) {

					ModelAndView model = null;
					try {
						model = new ModelAndView("product/addFlavour");

						String base64encodedString = request.getParameter("flavourId");
						String flavourId = FormValidation.DecodeKey(base64encodedString);

						MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
						map.add("flavourId", Integer.parseInt(flavourId));

						Flavour flavour = Constant.getRestTemplate().postForObject(Constant.url + "getFlavourById", map, Flavour.class);
						model.addObject("flavour", flavour);

						model.addObject("title", "Edit Tag");
					} catch (Exception e) {
						System.out.println("Execption in /editFlavour : " + e.getMessage());
						e.printStackTrace();
					}
					return model;
				}
				
				// Author-Mahendra Singh Created On-30-07-2020
				// Modified By-Mahendra Singh Created On-30-07-2020
				// Desc- Delete Flavour.
				@RequestMapping(value = "/deleteFlavour", method = RequestMethod.GET)
				public String deleteFlavour(HttpServletRequest request, HttpServletResponse response) {

					HttpSession session = request.getSession();
					try {

						String base64encodedString = request.getParameter("flavourId");
						String flavourId = FormValidation.DecodeKey(base64encodedString);

						MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
						map.add("flavourId", Integer.parseInt(flavourId));
						
						Info info = Constant.getRestTemplate().postForObject(Constant.url + "deleteFlavourById", map, Info.class);
						if (!info.getError()) {
							session.setAttribute("successMsg", info.getMessage());
						} else {
							session.setAttribute("errorMsg", info.getMessage());
						}

					} catch (Exception e) {
						System.out.println("Execption in /deleteFlavour : " + e.getMessage());
						e.printStackTrace();
					}
					return "redirect:/showFlavourList";
				}
				
				@RequestMapping(value = "/getFlavourCode", method = RequestMethod.GET)
				@ResponseBody
				public Info getFlavourCode(HttpServletRequest request, HttpServletResponse response) {
					Info info = new Info();
					String catCode = "";
					try {
						int flavourId = 0;
						try {
							flavourId = Integer.parseInt(request.getParameter("flavour_id"));
						}catch (Exception e) {
							e.printStackTrace();
							flavourId = 0;
						}
						
						String flavourName = request.getParameter("flavourName");
						
						
						System.out.println("Flavour Name: "+flavourName);
						
						HttpSession session = request.getSession();
						int companyId = (int) session.getAttribute("companyId");
						
						char ch = flavourName.charAt(0);			
						System.out.println("Character at 0 index is: "+ch);																		
						
						MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
						
						map.add("flavourName", flavourName);
						map.add("flavourId", flavourId);
						map.add("compId", companyId);
						Category res = Constant.getRestTemplate().postForObject(Constant.url + "getFlavourByName", map, Category.class); 
						
						map.add("flavourName", flavourName);
						map.add("compId", companyId);
						
						Integer cat = Constant.getRestTemplate().postForObject(Constant.url + "getFlavourCodeCount", map, Integer.class); 
				
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
}
