package com.ats.manoharadmin.controllers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.manoharadmin.common.Commons;
import com.ats.manoharadmin.common.Constant;
import com.ats.manoharadmin.models.BuyAndGetFreeOfferModel;
import com.ats.manoharadmin.models.Category;
import com.ats.manoharadmin.models.GetBuyGetFreeItemList;
import com.ats.manoharadmin.models.GetItemListForOfferDetail;
import com.ats.manoharadmin.models.Images;
import com.ats.manoharadmin.models.Info;
import com.ats.manoharadmin.models.ItemBuyGetFreeOffer;
import com.ats.manoharadmin.models.ItemListForOfferDetail;
import com.ats.manoharadmin.models.OfferDetail;
import com.ats.manoharadmin.models.OfferHeader;


@Controller
@SessionScope
public class OfferController {

	@RequestMapping(value = "/addNewOffers/{offerId}", method = RequestMethod.GET)
	public ModelAndView newOffers(@PathVariable int offerId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("offer/offers");

		try {

			int setOfferId = 0;
			int frequencyType = 1;
			int tab = 1;
			int itemSubTypeOffer = 0;

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			String dateStr = sdf.format(cal.getTime()) + " to " + sdf.format(cal.getTime());

			if (offerId > 0) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerId", offerId);

				OfferHeader res = Constant.getRestTemplate().postForObject(Constant.url + "getOfferHeaderById", map,
						OfferHeader.class);

				System.err.println("RES = " + res);

				if (res != null && res.getOfferId() > 0) {
					model.addObject("offer", res);
					setOfferId = res.getOfferId();

					if (res.getOfferType() == 0) {
						tab = 2;
					} else {
						tab = 1;
					}

					if (!res.getFrequency().isEmpty()) {
						List<Integer> dayIds = Stream.of(res.getFrequency().split(",")).map(Integer::parseInt)
								.collect(Collectors.toList());

						model.addObject("dayIds", dayIds);
					} else {
						model.addObject("dayIds", "");
					}

					if (!res.getApplicableFor().isEmpty()) {
						List<Integer> applicableIds = Stream.of(res.getApplicableFor().split(","))
								.map(Integer::parseInt).collect(Collectors.toList());

						model.addObject("applicableIds", applicableIds);
					} else {
						model.addObject("applicableIds", "");
					}

					frequencyType = res.getFrequencyType();

					String fromDate = Commons.convertToDMY(res.getFromDate());
					String toDate = Commons.convertToDMY(res.getToDate());

					dateStr = fromDate + " to " + toDate;

					try {

						if (res.getOfferType() > 0) {

							MultiValueMap<String, Object> map1 = new LinkedMultiValueMap<>();
							map1.add("offerId", offerId);

							OfferDetail[] detailList = Constant.getRestTemplate().postForObject(
									Constant.url + "getOfferDetailListByOfferId", map1, OfferDetail[].class);
							List<OfferDetail> offerDetailList = new ArrayList<OfferDetail>(Arrays.asList(detailList));
							model.addObject("offerDetailList", offerDetailList);
							System.err.println("OFFER DETAIL LIST  = "+offerDetailList);

							if (offerDetailList != null) {
								itemSubTypeOffer = offerDetailList.get(0).getOfferSubType();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				model.addObject("itemSubTypeOffer", itemSubTypeOffer);

			}

			HttpSession session = request.getSession();
			int compId = (int) session.getAttribute("companyId");
			System.err.println("COMP ID = " + compId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			ItemListForOfferDetail[] arr = Constant.getRestTemplate().postForObject(
					Constant.url + "getItemListForOfferDetailByCompId", map, ItemListForOfferDetail[].class);
			List<ItemListForOfferDetail> itemList = new ArrayList<ItemListForOfferDetail>(Arrays.asList(arr));
			System.err.println("ITEM LIST = " + itemList);

			model.addObject("frequencyType", frequencyType);
			model.addObject("dateStr", dateStr);
			model.addObject("tab", tab);
			model.addObject("itemList", itemList);
			model.addObject("offerId", setOfferId);
			
			model.addObject("imageUrl", Constant.IMAGE_URL);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/saveOfferHeader", method = RequestMethod.POST)
	public String saveOfferHeader(HttpServletRequest request, HttpServletResponse response) {

		int offerId = 0;

		try {
			HttpSession session = request.getSession();
			System.err.println("saveOfferHeader--- ");

			offerId = Integer.parseInt(request.getParameter("offerId"));
			String title = request.getParameter("offerTitle");
			String desc = request.getParameter("offerDesc");
			int type = Integer.parseInt(request.getParameter("selectType"));
			String offerDate = request.getParameter("offerDate");
			String fromTime = request.getParameter("fromTime");
			String toTime = request.getParameter("toTime");
			int freqType = Integer.parseInt(request.getParameter("freq_type"));

			int userId = (int) session.getAttribute("userId");
			int compId = (int) session.getAttribute("companyId");

			String daysIdsArray[] = request.getParameterValues("selectDay");

			String daysList = "";
			if (daysIdsArray != null) {
				List<String> daysIdList = new ArrayList<>();
				daysIdList = Arrays.asList(daysIdsArray);

				daysList = daysIdList.toString().substring(1, daysIdList.toString().length() - 1);
				daysList = daysList.replaceAll("\"", "");
				daysList = daysList.replaceAll(" ", "");
			}

			String applicableArray[] = request.getParameterValues("selectApplicableTo");

			String applicableList = "";
			if (applicableArray != null) {
				List<String> applicableTempList = new ArrayList<>();
				applicableTempList = Arrays.asList(applicableArray);

				applicableList = applicableTempList.toString().substring(1, applicableTempList.toString().length() - 1);
				applicableList = applicableList.replaceAll("\"", "");
				applicableList = applicableList.replaceAll(" ", "");
			}

			String fromDate = "";
			String toDate = "";

			String temp[] = offerDate.split(" to ");
			fromDate = Commons.convertToYMD(temp[0]);
			toDate = Commons.convertToYMD(temp[1]);

			if (freqType == 1) {
				// applicableList = "";
			} else if (freqType == 2) {
				daysList = "";
			}

			System.err.println("DATE = " + offerDate);
			System.err.println("DAYS = " + daysList);
			System.err.println("APPLICABLE = " + applicableList);

			OfferHeader offer = new OfferHeader(offerId, title, desc, type, applicableList, 0, freqType, daysList,
					fromDate, toDate, fromTime, toTime, userId, Commons.getCurrentYMDDateTime(),
					Commons.getCurrentYMDDateTime(), compId, 0, 0, 0, 0, 0, 0, "", "", "", "", 0, 0, 0, 0);

			System.err.println("OFFER = " + offer);

			OfferHeader res = Constant.getRestTemplate().postForObject(Constant.url + "saveOfferHeader", offer,
					OfferHeader.class);

			System.err.println("OFFER SAVED = " + res);

			if (res != null) {
				offerId = res.getOfferId();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addNewOffers/" + offerId;
	}

	// -----------------------SAVE OFFER DETAIL----------------------------
	@RequestMapping(value = "/saveOfferDetail", method = RequestMethod.POST)
	public String saveOfferDetail(HttpServletRequest request, HttpServletResponse response) {

		int offerId = 0;

		try {
			HttpSession session = request.getSession();
			System.err.println("saveOfferDetail--- ");

			offerId = Integer.parseInt(request.getParameter("tab2OfferId"));

			int billWiseOfferDetailId = 0;
			try {
				billWiseOfferDetailId = Integer.parseInt(request.getParameter("billWiseDetailId"));
			} catch (Exception e) {
			}

			int type = Integer.parseInt(request.getParameter("offerTypeWise"));
			String coupon = request.getParameter("billWiseCoupon");

			float disc = 0, limit = 0;
			try {
				disc = Float.parseFloat(request.getParameter("billWiseDisc"));
				limit = Float.parseFloat(request.getParameter("billWiseLimit"));
			} catch (Exception e) {
			}

			int subType = 0;
			if (type == 1) {
				subType = 1;
			} else {
				subType = Integer.parseInt(request.getParameter("itemWiseOfferSelect"));
			}

			List<OfferDetail> detailList = new ArrayList<>();

			String deleteUncheckItemDiscIds = "";
			String deleteBuyGetFreeIds = "";

			if (type == 1) { // SAVE BILL WISE OFFER DETAIL
				
				int noOfTimes=0;
				try {
					noOfTimes=Integer.parseInt(request.getParameter("noOfTimes"));
				}catch(Exception e) {
				}

				OfferDetail detail = new OfferDetail(billWiseOfferDetailId, offerId, subType, 0, 1, disc, limit, coupon,
						0, 0, 0, 0, noOfTimes, 0, 0, 0, "", "", "", "", 0, 0, 0, 0);
				detailList.add(detail);

			} else if (type == 2 && subType == 1) {// SAVE ITEM WISE DISCOUNT OFFER DETAIL

				if (itemListByOffer != null) {

					if (itemListByOffer.getItemList() != null) {

						for (int i = 0; i < itemListByOffer.getItemList().size(); i++) {

							int pkItemId = itemListByOffer.getItemList().get(i).getItemId();
							int itemCatId = itemListByOffer.getItemList().get(i).getCatId();
							int offerDetailId = itemListByOffer.getItemList().get(i).getOfferDetailId();

							float itemDiscPer = 0;

							if (request.getParameter("itemCheck" + pkItemId) != null) {

								if (request.getParameter("itemDisc#" + pkItemId + "#" + itemCatId) != null) {
									itemDiscPer = Float
											.parseFloat(request.getParameter("itemDisc#" + pkItemId + "#" + itemCatId));
								}

								OfferDetail detail = new OfferDetail(offerDetailId, offerId, subType, pkItemId, 1,
										itemDiscPer, 0, "", 0, 0, 0, 0, 0, 0, 0, 0, "", "", "", "", 0, 0, 0, 0);
								detailList.add(detail);

							} else {

								if (itemListByOffer.getItemList().get(i).getChecked() == 1) {
									deleteUncheckItemDiscIds = deleteUncheckItemDiscIds + "," + offerDetailId;
								}

							}

						}

					}

				}

			} else if (type == 2 && subType == 2) {// SAVE ITEM WISE BUY GET FREE OFFER DETAIL

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerId", offerId);

				ItemBuyGetFreeOffer[] arr = Constant.getRestTemplate()
						.postForObject(Constant.url + "getBuyGetFreeOfferList", map, ItemBuyGetFreeOffer[].class);
				List<ItemBuyGetFreeOffer> offerList = new ArrayList<ItemBuyGetFreeOffer>(Arrays.asList(arr));
				System.err.println("PREV OFFER DETAIL LIST = " + offerList);

				if (buyGetFreeList != null) {

					if (buyGetFreeList.getItemList() != null) {

						for (ItemBuyGetFreeOffer item : buyGetFreeList.getItemList()) {

							float pQty = 0;
							if (request.getParameter("selectPQty" + item.getPrimaryItemId()) != null) {
								pQty = Float.parseFloat(request.getParameter("selectPQty" + item.getPrimaryItemId()));
							}

							float sQty = 0;
							if (request.getParameter(
									"qty#" + item.getPrimaryItemId() + "#" + item.getSecondaryItemId()) != null) {
								sQty = Float.parseFloat(request.getParameter(
										"qty#" + item.getPrimaryItemId() + "#" + item.getSecondaryItemId()));
							}

							OfferDetail detail = new OfferDetail(item.getOfferDetailId(), offerId, subType,
									item.getPrimaryItemId(), pQty, 0, 0, "", item.getSecondaryItemId(), sQty, 0, 0, 0,
									0, 0, 0, "", "", "", "", 0, 0, 0, 0);
							detailList.add(detail);

						}

						if (offerList != null) {

							for (int i = 0; i < offerList.size(); i++) {

								int flag = 0;

								for (int j = 0; j < buyGetFreeList.getItemList().size(); j++) {
									if (offerList.get(i).getOfferDetailId() == buyGetFreeList.getItemList().get(j)
											.getOfferDetailId()) {
										flag = 1;
										break;
									}
								}

								if (flag == 0) {
									deleteBuyGetFreeIds = deleteBuyGetFreeIds + ","
											+ offerList.get(i).getOfferDetailId();
								}
							}

						}

						System.err.println("DELETE IDS ============ " + deleteBuyGetFreeIds);

					}

				}

			}

			Info info = Constant.getRestTemplate().postForObject(Constant.url + "saveOfferDetailList", detailList,
					Info.class);
			System.err.println("RESULT = " + info);

			if (info != null) {
				if (!info.getError()) {

					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("offerId", offerId);
					map.add("type", type);

					Info info1 = Constant.getRestTemplate().postForObject(Constant.url + "updateOfferType", map,
							Info.class);
					System.err.println("UPDATE TYPE RESULT = " + info1);
				}
			}

			if (!deleteUncheckItemDiscIds.isEmpty()) {
				deleteUncheckItemDiscIds = deleteUncheckItemDiscIds.substring(1, deleteUncheckItemDiscIds.length());

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerDetailIds", deleteUncheckItemDiscIds);

				Info infoUpdate = Constant.getRestTemplate().postForObject(Constant.url + "removeOfferDetailIds", map,
						Info.class);
				System.err.println("DELETE RES = " + infoUpdate);
				System.err.println("DELETE IDS =" + deleteUncheckItemDiscIds);
			}

			if (!deleteBuyGetFreeIds.isEmpty()) {
				deleteBuyGetFreeIds = deleteBuyGetFreeIds.substring(1, deleteBuyGetFreeIds.length());

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerDetailIds", deleteBuyGetFreeIds);

				Info infoUpdate = Constant.getRestTemplate().postForObject(Constant.url + "removeOfferDetailIds", map,
						Info.class);
				System.err.println("DELETE RES = " + infoUpdate);
				System.err.println("DELETE IDS =" + deleteBuyGetFreeIds);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addNewOffers/" + offerId;
	}

	// -----------------AJAX-ITEM LIST FOR OFFER DETAIL-------------------
	GetItemListForOfferDetail itemListByOffer = new GetItemListForOfferDetail();

	@RequestMapping(value = "/getItemListForOfferDetail", method = RequestMethod.GET)
	public @ResponseBody GetItemListForOfferDetail getItemListForOfferDetail(HttpServletRequest request,
			HttpServletResponse response) {

		int offerId = Integer.parseInt(request.getParameter("offerId"));
		
		HttpSession session = request.getSession();
		int companyId = (int) session.getAttribute("companyId");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("offerId", offerId);

		ItemListForOfferDetail[] arr = Constant.getRestTemplate()
				.postForObject(Constant.url + "getItemListForOfferDetail", map, ItemListForOfferDetail[].class);
		List<ItemListForOfferDetail> itemList = new ArrayList<ItemListForOfferDetail>(Arrays.asList(arr));
		
		map = new LinkedMultiValueMap<>();		
		map.add("compId", companyId);
		Category[] catArr = Constant.getRestTemplate().postForObject(Constant.url + "getAllCategory", map, Category[].class);
		List<Category>	catList = new ArrayList<Category>(Arrays.asList(catArr));

		itemListByOffer = new GetItemListForOfferDetail();
		itemListByOffer.setCatList(catList);
		itemListByOffer.setItemList(itemList);

		return itemListByOffer;
	}

	// -----------------AJAX-SAVE OFFER DETAIL-------------------
	@RequestMapping(value = "/saveOfferDetailAjax", method = RequestMethod.GET)
	public @ResponseBody Info saveOfferDetailAjax(HttpServletRequest request, HttpServletResponse response) {

		Info info = new Info();

		int offerId = Integer.parseInt(request.getParameter("offerId"));
		int type = Integer.parseInt(request.getParameter("type"));
		String coupon = request.getParameter("coupon");
		float disc = Float.parseFloat(request.getParameter("disc"));
		float limit = Float.parseFloat(request.getParameter("limit"));

		List<OfferDetail> detailList = new ArrayList<>();
		OfferDetail detail = new OfferDetail(0, offerId, 1, 0, 1, disc, limit, coupon, 0, 0, 0, 0, 0, 0, 0, 0, "", "",
				"", "", 0, 0, 0, 0);
		detailList.add(detail);

		info = Constant.getRestTemplate().postForObject(Constant.url + "saveOfferDetailList", detailList, Info.class);
		System.err.println("RESULT = " + info);

		if (info != null) {
			if (!info.getError()) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
				map.add("offerId", offerId);
				map.add("type", type);

				Info info1 = Constant.getRestTemplate().postForObject(Constant.url + "updateOfferType", map,
						Info.class);
				System.err.println("UPDATE TYPE RESULT = " + info1);
			}
		}

		return info;
	}

	List<BuyAndGetFreeOfferModel> offerList = new ArrayList<>();

	GetBuyGetFreeItemList buyGetFreeList = new GetBuyGetFreeItemList();

	// AJAX - BUY 1 GET 1 FREE OFFER---
	@RequestMapping(value = "/getBuyAndGetFreeOfferData", method = RequestMethod.GET)
	public @ResponseBody GetBuyGetFreeItemList getBuyAndGetFreeOfferData(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			buyGetFreeList = new GetBuyGetFreeItemList();

			int offerId = Integer.parseInt(request.getParameter("offerId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("offerId", offerId);

			ItemBuyGetFreeOffer[] arr = Constant.getRestTemplate()
					.postForObject(Constant.url + "getBuyGetFreeOfferList", map, ItemBuyGetFreeOffer[].class);
			List<ItemBuyGetFreeOffer> itmList = new ArrayList<ItemBuyGetFreeOffer>(Arrays.asList(arr));
			System.err.println("OFFER DETAIL LIST = " + itmList);

			buyGetFreeList.setItemList(itmList);

			Set<Integer> idSet = new HashSet<>();

			if (itmList != null) {
				for (ItemBuyGetFreeOffer item : itmList) {
					idSet.add(item.getPrimaryItemId());
				}
			}

			List<Integer> uniquePIds = new ArrayList<>();
			uniquePIds.addAll(idSet);

			buyGetFreeList.setIdList(uniquePIds);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return buyGetFreeList;
	}

	@RequestMapping(value = "/addBuyGetFreeItemToListAjax", method = RequestMethod.GET)
	public @ResponseBody GetBuyGetFreeItemList addBuyGetFreeItemToListAjax(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int pkItemId = Integer.parseInt(request.getParameter("primaryId"));
			int pkQty = Integer.parseInt(request.getParameter("pQty"));
			System.err.println("QTY = " + pkQty);

			String secIds = request.getParameter("secIds");

			secIds = secIds.substring(1, secIds.length() - 1);
			secIds = secIds.replaceAll("\"", "");

			System.err.println("SEC IDS - " + secIds);

			List<Integer> secIdList = Stream.of(secIds.split(",")).map(Integer::parseInt).collect(Collectors.toList());

			System.err.println("pId = " + pkItemId + "     pQty = " + pkQty + "     Sec Ids = " + secIdList);

			HttpSession session = request.getSession();
			int compId = (int) session.getAttribute("companyId");
			System.err.println("COMP ID = " + compId);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("compId", compId);

			ItemListForOfferDetail[] arr = Constant.getRestTemplate().postForObject(
					Constant.url + "getItemListForOfferDetailByCompId", map, ItemListForOfferDetail[].class);
			List<ItemListForOfferDetail> itemList = new ArrayList<ItemListForOfferDetail>(Arrays.asList(arr));
			System.err.println("ALL ITEM LIST = " + itemList);

			List<ItemBuyGetFreeOffer> saveList = new ArrayList<>();

			if (buyGetFreeList != null) {

				System.err.println("LIST = " + buyGetFreeList.getItemList());
				if (!buyGetFreeList.getItemList().isEmpty()) {

					if (buyGetFreeList.getIdList().contains(pkItemId)) {

						if (itemList != null) {

							for (int i = 0; i < secIdList.size(); i++) {

								int isPresent = 0;
								for (int j = 0; j < buyGetFreeList.getItemList().size(); j++) {
									if (secIdList.get(i) == buyGetFreeList.getItemList().get(j).getSecondaryItemId()
											&& buyGetFreeList.getItemList().get(j).getPrimaryItemId() == pkItemId) {
										isPresent = 1;
										break;
									}
								}

								if (isPresent == 0) {

									String pItemName = "", secItemName = "";
									for (ItemListForOfferDetail item : itemList) {

										if (item.getItemId() == pkItemId) {
											pItemName = item.getItemName();
										}

										if (item.getItemId() == secIdList.get(i)) {
											secItemName = item.getItemName();
										}
									}

									ItemBuyGetFreeOffer model = new ItemBuyGetFreeOffer(0, pkItemId, pItemName, pkQty,
											secIdList.get(i), secItemName, 1);

									buyGetFreeList.getItemList().add(model);
								}
							}

							System.err.println("FINAl = " + buyGetFreeList);

						}

					} else {

						if (itemList != null) {

							for (int i = 0; i < secIdList.size(); i++) {

								String pItemName = "", secItemName = "";
								for (ItemListForOfferDetail item : itemList) {

									if (item.getItemId() == pkItemId) {
										pItemName = item.getItemName();
									}

									if (item.getItemId() == secIdList.get(i)) {
										secItemName = item.getItemName();
									}
								}

								ItemBuyGetFreeOffer model = new ItemBuyGetFreeOffer(0, pkItemId, pItemName, pkQty,
										secIdList.get(i), secItemName, 1);
								buyGetFreeList.getItemList().add(model);
							}

							buyGetFreeList.getIdList().add(pkItemId);

						}

					}

				} else {
					// NULL IDS

					if (itemList != null) {

						for (int i = 0; i < secIdList.size(); i++) {

							String pItemName = "", secItemName = "";
							for (ItemListForOfferDetail item : itemList) {

								if (item.getItemId() == pkItemId) {
									pItemName = item.getItemName();
								}

								if (item.getItemId() == secIdList.get(i)) {
									secItemName = item.getItemName();
								}
							}

							ItemBuyGetFreeOffer model = new ItemBuyGetFreeOffer(0, pkItemId, pItemName, pkQty,
									secIdList.get(i), secItemName, 1);
							saveList.add(model);
						}

						buyGetFreeList.setItemList(saveList);

						List<Integer> newIds = new ArrayList<>();
						newIds.add(pkItemId);
						buyGetFreeList.setIdList(newIds);

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return buyGetFreeList;
	}

	// DELETE BY AJAX
	@RequestMapping(value = "/deleteBuyGetOfferBySecId", method = RequestMethod.GET)
	public @ResponseBody GetBuyGetFreeItemList deleteBuyGetOfferBySecId(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int pId = Integer.parseInt(request.getParameter("pId"));
			int sId = Integer.parseInt(request.getParameter("sId"));

			if (buyGetFreeList != null) {

				if (!buyGetFreeList.getItemList().isEmpty()) {

					List<ItemBuyGetFreeOffer> itemList = new ArrayList<>();

					for (int i = 0; i < buyGetFreeList.getItemList().size(); i++) {

						if (pId == buyGetFreeList.getItemList().get(i).getPrimaryItemId()
								&& sId == buyGetFreeList.getItemList().get(i).getSecondaryItemId()) {
							System.err.println("Matched!");
						} else {
							itemList.add(buyGetFreeList.getItemList().get(i));
						}

					}

					buyGetFreeList.setItemList(itemList);

					Set<Integer> idSet = new HashSet<>();

					if (itemList != null) {
						for (ItemBuyGetFreeOffer item : itemList) {
							idSet.add(item.getPrimaryItemId());
						}
					}

					List<Integer> uniquePIds = new ArrayList<>();
					uniquePIds.addAll(idSet);

					buyGetFreeList.setIdList(uniquePIds);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return buyGetFreeList;
	}

	// DELETE BY AJAX
	@RequestMapping(value = "/deleteBuyGetOfferByPrimaryId", method = RequestMethod.GET)
	public @ResponseBody GetBuyGetFreeItemList deleteBuyGetOfferByPrimaryId(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			int pId = Integer.parseInt(request.getParameter("pId"));

			if (buyGetFreeList != null) {

				if (!buyGetFreeList.getItemList().isEmpty()) {

					List<ItemBuyGetFreeOffer> itemList = new ArrayList<>();

					for (int i = 0; i < buyGetFreeList.getItemList().size(); i++) {

						if (pId == buyGetFreeList.getItemList().get(i).getPrimaryItemId()) {
							System.err.println("Matched!");
						} else {
							itemList.add(buyGetFreeList.getItemList().get(i));
						}

					}

					buyGetFreeList.setItemList(itemList);

					Set<Integer> idSet = new HashSet<>();

					if (itemList != null) {
						for (ItemBuyGetFreeOffer item : itemList) {
							idSet.add(item.getPrimaryItemId());
						}
					}

					List<Integer> uniquePIds = new ArrayList<>();
					uniquePIds.addAll(idSet);

					buyGetFreeList.setIdList(uniquePIds);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return buyGetFreeList;
	}

	// IMAGE UPLOAD-------------------
	@ResponseBody
	@RequestMapping(value = "/ajaxImageUploadOffer/{offerId}", method = RequestMethod.POST)
	public String ajaxImageUploadOffer(@PathVariable int offerId, HttpServletRequest request,
			HttpServletResponse response, @RequestParam("files") List<MultipartFile> files) {

		System.err.println("ajaxImageUploadOffer--- " + files.size());

		try {

			if (offerId > 0) {
				List<Images> imageList = new ArrayList<>();

				if (files.size() > 0) {

					for (int i = 0; i < files.size(); i++) {

						String ext = files.get(i).getOriginalFilename().split("\\.")[1];
						String fileName = Commons.getCurrentTimeStamp() + "." + ext;
						new ImageUploadController().saveUploadedFiles(files.get(i), 1, fileName);

						Images images = new Images(0, 4, offerId, fileName, (i + 1), 0, 0, 0, 0, 0, "", "", "", "", 0,
								0, 0);
						imageList.add(images);
					}

					Info info = Constant.getRestTemplate().postForObject(Constant.url + "saveMultipleImage", imageList,
							Info.class);

				}
			} else {
				return "false";
			}

		} catch (Exception e) {
			return "false";
		}
		return "true";

	}

	@RequestMapping(value = "/showOfferList", method = RequestMethod.GET)
	public ModelAndView showOfferList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("offer/offersList");

		HttpSession session = request.getSession();
		int compId = (int) session.getAttribute("companyId");
		System.err.println("COMP ID = " + compId);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("compId", compId);

		OfferHeader[] arr = Constant.getRestTemplate().postForObject(Constant.url + "getAllOfferHeaderListByCompId",
				map, OfferHeader[].class);
		List<OfferHeader> offerList = new ArrayList<OfferHeader>(Arrays.asList(arr));
		System.err.println("offerList = " + offerList);

		model.addObject("offerList", offerList);

		return model;
	}

	// ------DELETE OFFER HEADER------------
	@RequestMapping(value = "/deleteOfferHeaderById", method = RequestMethod.GET)
	public String deleteOfferHeaderById(HttpServletRequest request, HttpServletResponse response) {

		try {
			HttpSession session = request.getSession();
			int offerId = Integer.parseInt(request.getParameter("offerId"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			map.add("offerId", offerId);
			map.add("status", 1);

			Info res = Constant.getRestTemplate().postForObject(Constant.url + "deleteOfferHeaderById", map, Info.class);

			if (!res.getError()) {
				session.setAttribute("successMsg", res.getMessage());
			} else {
				session.setAttribute("errorMsg", res.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showOfferList";
	}

}
