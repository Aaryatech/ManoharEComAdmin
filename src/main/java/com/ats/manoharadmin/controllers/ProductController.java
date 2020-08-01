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
import com.ats.manoharadmin.models.Brands;
import com.ats.manoharadmin.models.CatImgBean;
import com.ats.manoharadmin.models.Category;
import com.ats.manoharadmin.models.Flavour;
import com.ats.manoharadmin.models.Images;
import com.ats.manoharadmin.models.Info;
import com.ats.manoharadmin.models.ProductStatus;
import com.ats.manoharadmin.models.RawMaterialUom;
import com.ats.manoharadmin.models.SubCat;
import com.ats.manoharadmin.models.Tags;
import com.ats.manoharadmin.models.TaxInfo;
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
				
				
		// Author-Mahendra Singh Created On-31-07-2020
		// Modified By-Mahendra Singh Created On-31-07-2020
		// Desc- Show City.
		@RequestMapping(value = "/showBrandList", method = RequestMethod.GET)
		public ModelAndView showBrandList(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {

				model = new ModelAndView("product/brandList");

				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");
					
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();		
				map.add("compId", companyId);
					
				Brands[] brandArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllBrands", map, Brands[].class);
				List<Brands> brandList = new ArrayList<Brands>(Arrays.asList(brandArr));
					
				for (int i = 0; i < brandList.size(); i++) {

					brandList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(brandList.get(i).getBrandId())));
				}
					
				System.out.println(brandList);
					
				model.addObject("brandList", brandList);		
					
				model.addObject("title", "Brand List");
				}catch (Exception e) {
						e.printStackTrace();
				}
					return model;
			}
		
		@RequestMapping(value = "/addBrand", method = RequestMethod.GET)
		public ModelAndView addBrand(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			Brands brand = new Brands();
			model = new ModelAndView("product/addBrand");

			model.addObject("brand", brand);
			model.addObject("title", "Add Band");

			return model;
		}
		
		// Author-Mahendra Singh Created On-31-07-2020
		// Modified By-Mahendra Singh Created On-31-07-2020
		// Desc- Insert Brand.
		@RequestMapping(value = "/insertNewBrand", method = RequestMethod.POST)
		public String insertNewIngredient(HttpServletRequest request, HttpServletResponse response,
				@RequestParam("doc") MultipartFile doc) {
			try {
				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String brandImage = null;

				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");

				if (!doc.getOriginalFilename().equalsIgnoreCase("")) {

					System.err.println("In If ");

					//brandImage = sf.format(date) + "_" + doc.getOriginalFilename();
					Random randNum = new Random();
					
					String ext = doc.getOriginalFilename().split("\\.")[1];
					brandImage = Commons.getCurrentTimeStamp()+"_"+randNum.nextInt(5*1000)+ "." + ext;

					try {
						new ImageUploadController().saveUploadedFiles(doc, 1, brandImage);
					} catch (Exception e) {
					}

				} else {
					System.err.println("In else ");
					brandImage = request.getParameter("editImg");

				}

				Brands brand = new Brands();

				int brandId = Integer.parseInt(request.getParameter("brandId"));
				
				brand.setBrandCode(request.getParameter("brandCode"));
				brand.setBrandId(brandId);
				brand.setBrandImg(brandImage);
				brand.setBrandName(request.getParameter("brandName"));
				brand.setCompanyId(userDetail.getUser().getCompanyId());
				brand.setDescription(request.getParameter("description"));
				brand.setSeqNo(Integer.parseInt(request.getParameter("seqNo")));
				
				brand.setDelStatus(0);				
				brand.setExInt1(0);
				brand.setExInt2(0);
				brand.setExVar1("NA");
				brand.setExVar2("NA");

				Brands res = Constant.getRestTemplate().postForObject(Constant.url + "insertBrand", brand,
						Brands.class);

				if (res.getBrandId() > 0) {
					if (brandId == 0)
						session.setAttribute("successMsg", "Brand Saved Sucessfully");
					else
						session.setAttribute("successMsg", "Brand Update Sucessfully");
				} else {
					session.setAttribute("errorMsg", "Failed to Save Brand");
				}
			} catch (Exception e) {
				System.out.println("Execption in /insertNewBrand : " + e.getMessage());
				e.printStackTrace();
			}

			return "redirect:/showBrandList";

		}
		
		// Author-Mahendra Singh Created On-31-07-2020
		// Modified By-Mahendra Singh Created On-31-07-2020
		// Desc- Edit Brand List.
		@RequestMapping(value = "/editBrand", method = RequestMethod.GET)
		public ModelAndView editBrand(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
				model = new ModelAndView("product/addBrand");
				
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");

				String base64encodedString = request.getParameter("brandId");
				String brandId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("brandId", Integer.parseInt(brandId));
				map.add("compId", companyId);

				Brands brand = Constant.getRestTemplate().postForObject(Constant.url + "getBrandById", map, Brands.class);
				model.addObject("brand", brand);

				model.addObject("title", "Edit Brand");
			} catch (Exception e) {
				System.out.println("Execption in /editBrand : " + e.getMessage());
				e.printStackTrace();
			}
			return model;
		}
		
		// Author-Mahendra Singh Created On-30-07-2020
		// Modified By-Mahendra Singh Created On-30-07-2020
		// Desc- Delete Brand.
		@RequestMapping(value = "/deleteBrand", method = RequestMethod.GET)
		public String deleteBrand(HttpServletRequest request, HttpServletResponse response) {

			HttpSession session = request.getSession();
			try {

				String base64encodedString = request.getParameter("brandId");
				String brandId = FormValidation.DecodeKey(base64encodedString);

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("brandId", Integer.parseInt(brandId));
				
				Info info = Constant.getRestTemplate().postForObject(Constant.url + "deleteBrand", map, Info.class);
				if (!info.getError()) {
					session.setAttribute("successMsg", info.getMessage());
				} else {
					session.setAttribute("errorMsg", info.getMessage());
				}

			} catch (Exception e) {
				System.out.println("Execption in /deleteBrand : " + e.getMessage());
				e.printStackTrace();
			}
			return "redirect:/showBrandList";
		}
		
		
		@RequestMapping(value = "/getBrandCode", method = RequestMethod.GET)
		@ResponseBody
		public Info getBrandCode(HttpServletRequest request, HttpServletResponse response) {
			Info info = new Info();
			String catCode = "";
			try {
				int brandId = 0;
				try {
					brandId = Integer.parseInt(request.getParameter("brandId"));
				}catch (Exception e) {
					e.printStackTrace();
					brandId = 0;
				}
				
				String brandName = request.getParameter("brandName");
				
				
				System.out.println("brand Name: "+brandName);
				
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");
				
				char ch = brandName.charAt(0);			
				System.out.println("Character at 0 index is: "+ch);																		
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				
				map.add("brandName", brandName);
				map.add("brandId", brandId);
				map.add("compId", companyId);
				Brands res = Constant.getRestTemplate().postForObject(Constant.url + "getBrandByName", map, Brands.class); 
				
				map.add("brandName", brandName);
				map.add("compId", companyId);
				
				Integer cat = Constant.getRestTemplate().postForObject(Constant.url + "getBrandCodeCount", map, Integer.class); 
		
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
		/*******************************************************************************/
		
		// Author-Mahendra Singh Created On-31-07-2020
		// Modified By-Mahendra Singh Created On-31-07-2020
		// Desc- Show City.
		@RequestMapping(value = "/showTaxList", method = RequestMethod.GET)
		public ModelAndView showTaxList(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {

				model = new ModelAndView("product/taxList");

				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");
					
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();		
				map.add("compId", companyId);
					
				TaxInfo[] taxArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllTaxes", map, TaxInfo[].class);
				List<TaxInfo> taxList = new ArrayList<TaxInfo>(Arrays.asList(taxArr));
					
				for (int i = 0; i < taxList.size(); i++) {

					taxList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(taxList.get(i).getTaxInfoId())));
				}
					
				System.out.println(taxList);
					
				model.addObject("taxList", taxList);		
					
				model.addObject("title", "Tax List");
				}catch (Exception e) {
						e.printStackTrace();
				}
					return model;
			}
		
		@RequestMapping(value = "/addTax", method = RequestMethod.GET)
		public ModelAndView addTax(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			TaxInfo tax = new TaxInfo();
			model = new ModelAndView("product/addTax");

			model.addObject("tax", tax);
			model.addObject("title", "Add Tax");

			return model;
		}
		
		// Author-Mahendra Singh Created On-31-07-2020
		// Modified By-Mahendra Singh Created On-31-07-2020
		// Desc- Insert Brand.
		@RequestMapping(value = "/insertNewTax", method = RequestMethod.POST)
		public String insertNewTax(HttpServletRequest request, HttpServletResponse response) {
		try {
				
						HttpSession session = request.getSession();
						UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");
					

						TaxInfo tax = new TaxInfo();

						int taxId = Integer.parseInt(request.getParameter("taxId"));						
						
						tax.setHsn(request.getParameter("hsn"));
						tax.setIgstPer(Float.parseFloat(request.getParameter("igstPer")));
						tax.setSgstPer(Float.parseFloat(request.getParameter("sgstPer")));
						tax.setCgstPer(Float.parseFloat(request.getParameter("cgstPer")));
						tax.setTaxInfoId(taxId);
						tax.setTaxTitle(request.getParameter("taxTitle"));
						tax.setCompanyId(userDetail.getUser().getCompanyId());
						
						tax.setDelStatus(0);				
						tax.setExInt1(0);
						tax.setExInt2(0);
						tax.setExVar1("NA");
						tax.setExVar2("NA");

						TaxInfo res = Constant.getRestTemplate().postForObject(Constant.url + "insertTaxInfo", tax,
								TaxInfo.class);

						if (res.getTaxInfoId() > 0) {
							if (taxId == 0)
								session.setAttribute("successMsg", "Tax Saved Sucessfully");
							else
								session.setAttribute("successMsg", "Tax Update Sucessfully");
						} else {
							session.setAttribute("errorMsg", "Failed to Save Tax");
						}
					} catch (Exception e) {
						System.out.println("Execption in /insertNewTax : " + e.getMessage());
						e.printStackTrace();
					}

					return "redirect:/showTaxList";

				}
		// Author-Mahendra Singh Created On-31-07-2020
		// Modified By-Mahendra Singh Created On-31-07-2020
		// Desc- Edit Tax.
		@RequestMapping(value = "/editTax", method = RequestMethod.GET)
			public ModelAndView editTax(HttpServletRequest request, HttpServletResponse response) {

				ModelAndView model = null;
				try {
						model = new ModelAndView("product/addTax");
						
						HttpSession session = request.getSession();
						int companyId = (int) session.getAttribute("companyId");

						String base64encodedString = request.getParameter("taxId");
						String taxId = FormValidation.DecodeKey(base64encodedString);

						MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
						map.add("taxId", Integer.parseInt(taxId));
						map.add("compId", companyId);

						TaxInfo tax = Constant.getRestTemplate().postForObject(Constant.url + "getTaxById", map, TaxInfo.class);
						model.addObject("tax", tax);

						model.addObject("title", "Edit Tax");
					} catch (Exception e) {
						System.out.println("Execption in /editTax : " + e.getMessage());
						e.printStackTrace();
					}
					return model;
				}
		
		// Author-Mahendra Singh Created On-31-07-2020
		// Modified By-Mahendra Singh Created On-31-07-2020
		// Desc- Delete Brand.
			@RequestMapping(value = "/deleteTax", method = RequestMethod.GET)
			public String deleteTax(HttpServletRequest request, HttpServletResponse response) {

				HttpSession session = request.getSession();
				try {

					String base64encodedString = request.getParameter("taxId");
					String taxId = FormValidation.DecodeKey(base64encodedString);
					
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("taxId", Integer.parseInt(taxId));
						
					Info info = Constant.getRestTemplate().postForObject(Constant.url + "/deleteTax", map, Info.class);
						if (!info.getError()) {
							session.setAttribute("successMsg", info.getMessage());
						} else {
							session.setAttribute("errorMsg", info.getMessage());
						}

					} catch (Exception e) {
						System.out.println("Execption in /deleteTax : " + e.getMessage());
						e.printStackTrace();
					}
					return "redirect:/showTaxList";
				}
			
			@RequestMapping(value = "chkUnqTaxTitle", method = RequestMethod.GET)
			@ResponseBody
			public Info chkUnqTaxTitle(HttpServletRequest request, HttpServletResponse response) {
				Info info = new Info();
				
				try {
					int taxId = 0;
					try {
						taxId = Integer.parseInt(request.getParameter("taxId"));
					}catch (Exception e) {
						e.printStackTrace();
						taxId = 0;
					}
					
					String taxTitle = request.getParameter("taxTitle");
					
					
					System.out.println("Tax Name: "+taxTitle);
					
					HttpSession session = request.getSession();
					int companyId = (int) session.getAttribute("companyId");
					
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					
					map.add("taxTitle", taxTitle);
					map.add("taxId", taxId);
					map.add("compId", companyId);
					TaxInfo res = Constant.getRestTemplate().postForObject(Constant.url + "getTaxByTaxName", map, TaxInfo.class); 	
					
					if(res!=null) {
						info.setError(true);
						info.setMessage("Tax Found");
					}else {
						info.setError(false);
						info.setMessage("Tax Not Found");
					}
				}catch (Exception e) {
					e.printStackTrace();
					
					info.setError(true);
					info.setMessage("Tax Found");			
				}
				return info;
			}
			
			/**********************************************************************/
			// Author-Mahendra Singh Created On-31-07-2020
			// Modified By-Mahendra Singh Created On-31-07-2020
			// Desc- Show Product Status.
			@RequestMapping(value = "/showProductStatusList", method = RequestMethod.GET)
			public ModelAndView showProductStatusList(HttpServletRequest request, HttpServletResponse response) {

				ModelAndView model = null;
				try {

					model = new ModelAndView("product/productStatusList");

					HttpSession session = request.getSession();
					int companyId = (int) session.getAttribute("companyId");
						
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();		
					map.add("compId", companyId);
						
					ProductStatus[] prodctArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllProductStatus", map, ProductStatus[].class);
					List<ProductStatus> prdctList = new ArrayList<ProductStatus>(Arrays.asList(prodctArr));
						
					for (int i = 0; i < prdctList.size(); i++) {

						prdctList.get(i).setExVar1(FormValidation.Encrypt(String.valueOf(prdctList.get(i).getProductStatusId())));
					}
						
					System.out.println(prdctList);
						
					model.addObject("prdctList", prdctList);		
						
					model.addObject("title", "Product Status List");
					}catch (Exception e) {
							e.printStackTrace();
					}
						return model;
				}
			
			@RequestMapping(value = "/addProductStatus", method = RequestMethod.GET)
			public ModelAndView addProductStatus(HttpServletRequest request, HttpServletResponse response) {

				ModelAndView model = null;
				ProductStatus product = new ProductStatus();
				model = new ModelAndView("product/addProductStatus");

				model.addObject("product", product);
				model.addObject("title", "Add Product Status");

				return model;
			}
			
			// Author-Mahendra Singh Created On-31-07-2020
			// Modified By-Mahendra Singh Created On-31-07-2020
			// Desc- Insert Product Status.
			@RequestMapping(value = "/insertNewProductStatus", method = RequestMethod.POST)
			public String insertNewProductStatus(HttpServletRequest request, HttpServletResponse response) {
			try {					
					HttpSession session = request.getSession();
					UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");						

					ProductStatus product = new ProductStatus();

					int prdctStatId = Integer.parseInt(request.getParameter("productStat_id"));						
							
					product.setProductStatus(request.getParameter("prdct_stat"));
					product.setProductStatusId(prdctStatId);
					product.setCompanyId(userDetail.getUser().getCompanyId());
					product.setDescription(request.getParameter("description"));
							
					product.setDelStatus(0);				
					product.setExInt1(0);
					product.setExInt2(0);
					product.setExInt3(0);
					product.setExVar1("NA");
					product.setExVar2("NA");
					product.setExVar3("NA");

					ProductStatus res = Constant.getRestTemplate().postForObject(Constant.url + "insertProductStatus", product,
							ProductStatus.class);

					if (res.getProductStatusId() > 0) {
							if (prdctStatId == 0)
								session.setAttribute("successMsg", "Product Status Saved Sucessfully");
							else
								session.setAttribute("successMsg", "Product Status Update Sucessfully");
						} else {
							session.setAttribute("errorMsg", "Failed to Save Product Status");
						}
					} catch (Exception e) {
							System.out.println("Execption in /insertNewProductStatus : " + e.getMessage());
							e.printStackTrace();
					}

					return "redirect:/showProductStatusList";

				}
			
			// Author-Mahendra Singh Created On-31-07-2020
			// Modified By-Mahendra Singh Created On-31-07-2020
			// Desc- Edit Product Status.
			@RequestMapping(value = "/editProductStatus", method = RequestMethod.GET)
				public ModelAndView editProductStatus(HttpServletRequest request, HttpServletResponse response) {

					ModelAndView model = null;
					try {
							model = new ModelAndView("product/addProductStatus");
							
							HttpSession session = request.getSession();
							int companyId = (int) session.getAttribute("companyId");

							String base64encodedString = request.getParameter("productId");
							String productId = FormValidation.DecodeKey(base64encodedString);

							MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
							map.add("productId", Integer.parseInt(productId));
							map.add("compId", companyId);

							ProductStatus product = Constant.getRestTemplate().postForObject(Constant.url + "getProductStatusById", map, ProductStatus.class);
							model.addObject("product", product);

							model.addObject("title", "Edit Product Status");
						} catch (Exception e) {
							System.out.println("Execption in /editProductStatus : " + e.getMessage());
							e.printStackTrace();
						}
						return model;
					}
			
			// Author-Mahendra Singh Created On-31-07-2020
			// Modified By-Mahendra Singh Created On-31-07-2020
			// Desc- Delete ProductStatus.
				@RequestMapping(value = "/deleteProductStatus", method = RequestMethod.GET)
				public String deleteProductStatus(HttpServletRequest request, HttpServletResponse response) {

					HttpSession session = request.getSession();
					try {

						String base64encodedString = request.getParameter("productId");
						String productId = FormValidation.DecodeKey(base64encodedString);
						
						MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
						map.add("productId", Integer.parseInt(productId));
							
						Info info = Constant.getRestTemplate().postForObject(Constant.url + "/deleteProductStatus", map, Info.class);
							if (!info.getError()) {
								session.setAttribute("successMsg", info.getMessage());
							} else {
								session.setAttribute("errorMsg", info.getMessage());
							}

						} catch (Exception e) {
							System.out.println("Execption in /deleteProductStatus : " + e.getMessage());
							e.printStackTrace();
						}
						return "redirect:/showProductStatusList";
					}
				
		/**************************************************************************/
		@RequestMapping(value = "/addItemSKUConfig", method = RequestMethod.GET)
		public ModelAndView addItemSKUConfig(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = null;
			try {
					
					model = new ModelAndView("product/addItemSKUConfig");
	
				//	ProductStatus product = new ProductStatus();
				//	model.addObject("product", product);
					
					
				HttpSession session = request.getSession();
				int companyId = (int) session.getAttribute("companyId");
					
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();		
				map.add("compId", companyId);
					
				Category[] catArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllCategory", map, Category[].class);
				List<Category>	catList = new ArrayList<Category>(Arrays.asList(catArr));
				model.addObject("catList", catList);
					
							
				SubCat[] subCatArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllSubCategory", map, SubCat[].class);
				List<SubCat> subCatList = new ArrayList<SubCat>(Arrays.asList(subCatArr));				
				model.addObject("subCatList", subCatList);
					
				
				Tags[] tagArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllTags", map, Tags[].class);
				List<Tags> tagList = new ArrayList<Tags>(Arrays.asList(tagArr));					
				model.addObject("tagList", tagList);
				
				
				Flavour[] flavourArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllFlavours", map, Flavour[].class);
				List<Flavour> flavourList = new ArrayList<Flavour>(Arrays.asList(flavourArr));				
				model.addObject("flavourList", flavourList);
				
								
				Brands[] brandArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllBrands", map, Brands[].class);
				List<Brands> brandList = new ArrayList<Brands>(Arrays.asList(brandArr));					
				model.addObject("brandList", brandList);
				
						
				TaxInfo[] taxArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllTaxes", map, TaxInfo[].class);
				List<TaxInfo> taxList = new ArrayList<TaxInfo>(Arrays.asList(taxArr));				
				model.addObject("taxList", taxList);
				
			
				
				ProductStatus[] prodctArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllProductStatus", map, ProductStatus[].class);
				List<ProductStatus> prdctList = new ArrayList<ProductStatus>(Arrays.asList(prodctArr));					
				model.addObject("prdctList", prdctList);
				
				RawMaterialUom[] uomArr = Constant.getRestTemplate().getForObject(Constant.url + "getRmUom", RawMaterialUom[].class);
				List<RawMaterialUom> uomList = new ArrayList<RawMaterialUom>(Arrays.asList(uomArr));					
				model.addObject("uomList", uomList);
					
					
					model.addObject("title", "Add Item SKU Configuration");
				}catch (Exception e) {
					System.out.println("Execption in /addItemSKUConfig : " + e.getMessage());
					e.printStackTrace();
				}
				return model;
			}
		
		
		
		@RequestMapping(value = "/getSubCatByCatId", method = RequestMethod.GET)
		@ResponseBody
		public List<SubCat> getSubCatByCatId(HttpServletRequest request, HttpServletResponse response) {
			
			List<SubCat> subCatList = new ArrayList<SubCat>();
			try {
				HttpSession session = request.getSession();
				UserResponse userDetail = (UserResponse) session.getAttribute("UserDetail");
				
				int catId = Integer.parseInt(request.getParameter("category"));
				System.out.println("Cat Id-------------"+catId);
				
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("compId", userDetail.getUser().getCompanyId());
				map.add("catId", catId);

				SubCat[] subCatArr = Constant.getRestTemplate().postForObject(Constant.url + "getSubCatByCatIdAndCompId", map,
						SubCat[].class);
				subCatList = new ArrayList<SubCat>(Arrays.asList(subCatArr));
				
				
			}catch (Exception e) {
				System.out.println("Execption in /getSubCatByCatId : " + e.getMessage());
				e.printStackTrace();
			}
			return subCatList;
			
		}
				
}
