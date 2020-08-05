<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Theme JS files -->
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/plugins/forms/styling/uniform.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/plugins/forms/styling/switchery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/global_assets/js/plugins/forms/styling/switch.min.js"></script>

	<script src="${pageContext.request.contextPath}/resources/global_assets/js/demo_pages/form_checkboxes_radios.js"></script>
	<!-- /theme JS files -->
<style type="text/css">
.daterangepicker {
	width: 100%;
}

.daterangepicker.show-calendar .calendar {
	display: inline--block;
}

.daterangepicker .calendar, .daterangepicker .ranges {
	float: right;
}

.select2-selection--multiple .select2-selection__rendered {
	border-bottom: 1px solid #ddd;
}
</style>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs">
<%-- <c:url value="/updtSku" var="updtSku"></c:url>
<c:url value="/deleteSkuDetail" var="deleteSkuDetail"></c:url> --%>


	<c:url value="/getSubCatByCatId" var="getSubCatByCatId"></c:url>
	<!-- Main navbar -->
	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<!-- /main navbar -->

	<!-- Page content -->
	<div class="page-content">

		<!-- Main sidebar -->
		<jsp:include page="/WEB-INF/views/include/left.jsp"></jsp:include>
		<!-- /main sidebar -->


		<!-- Main content -->
		<div class="content-wrapper">

			<!-- Page header -->
			<div class="page-header page-header-light"></div>
			<!-- /page header -->

			<!-- Content area -->
			<div class="content">

				<!-- Form validation -->
				<div class="row">
					<div class="col-md-12">
						<div class="card">

							<div
								class="card-header bg-danger text-white d-flex justify-content-between">
								<span
									class="font-size-sm text-uppercase font-weight-semibold card-title">
									${title}</span>
								<!--  -->
								<span class="font-size-sm text-uppercase font-weight-semibold"><a
									class="card-title"
									href="${pageContext.request.contextPath}/showItemList"
									style="color: white;">Item List</a></span>
							</div>

							<br>

				<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

							<div class="card-body">

								<ul class="nav nav-tabs nav-tabs-highlight mb-0">
									<li class="nav-item"><a href="#bordered-tab1" id="tab1"
										name="tab1" class="nav-link active" data-toggle="tab">Add
											Items </a></li>
									<c:if test="${item.itemId > 0}">
										<li class="nav-item"><a href="#bordered-tab2" id="tab2"
											name="tab2" class="nav-link" data-toggle="tab">SKU
												Configuration</a></li>
									</c:if>



								</ul>
								<div
									class="tab-content card card-body border border-top-0 rounded-top-0 shadow-0 mb-0">
									<!--------------------- TAB 1 ---------------------------->

									<div class="tab-pane fade show active" id="bordered-tab1">


									<form action="${pageContext.request.contextPath}/saveItem"
										id="submitInsert" method="post">  


										<p class="desc text-danger fontsize11">Note : * Fields are
											mandatory.</p>


										<input type="hidden" class="form-control" name="item_id"
											id="item_id" value="${item.itemId}">


										<div class="form-group row">
											<label class="col-form-label font-weight-bold col-lg-2"
												for="item_name">Item Name<span class="text-danger">*
											</span>:
											</label>
											<div class="col-lg-4">
												<input type="text"
													class="form-control maxlength-badge-position"
													name="item_name" id="item_name" maxlength="30"
													autocomplete="off" onchange="trim(this)" value="${item.itemName}"> <span
													class="validation-invalid-label text-danger"
													id="error_item_name" style="display: none;">This
													field is required.</span>
											</div>

											<label class="col-form-label font-weight-bold col-lg-2"
												for="item_code">Item Code <span class="text-danger"></span>:
											</label>
											<div class="col-lg-4">
												<input type="text"
													class="form-control maxlength-badge-position"
													name="item_code" id="item_code" maxlength="80"
													autocomplete="off" onchange="trim(this)" value="${item.itemCode}"> <span
													class="validation-invalid-label text-danger"
													id="error_item_code" style="display: none;">This
													field is required.</span>
												<!-- value="${product.description}" -->
											</div>
										</div>

										<div class="form-group row">
											<label class="col-form-label font-weight-bold col-lg-2"
												for="sub_category">Short Name <span
												class="text-danger"> </span>:
											</label>
											<div class="col-lg-4">
												<input type="text"
													class="form-control maxlength-badge-position"
													name="short_name" id="short_name" maxlength="30"
													autocomplete="off" onchange="trim(this)" value="${item.shortName}"> <span
													class="validation-invalid-label text-danger"
													id="error_short_name" style="display: none;">This
													field is required.</span>
											</div>


											<label class="col-form-label font-weight-bold col-lg-2">Is
												Item Used <span class="text-danger">* </span>:
											</label>
											<div class="col-lg-4">

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled" id="n_active"
														name="status"  data-fouc value="1"  ${item.itemIsUsed==1 ? 'checked' : ''}>
														In-Active
													</label>
												</div>

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled" id="y_active"
														name="status" checked data-fouc value="0" ${item.itemIsUsed==0 ? 'checked' : ''}> Active
													</label>
												</div>
											</div>
										</div>


										<div class="form-group row">
											<label class="col-form-label font-weight-bold col-lg-2"
												for="category">Select Category<span
												class="text-danger">* </span>:
											</label>
											<div class="col-lg-4">
												<select class="form-control select-search" data-fouc
													name="category" id="category">
													<option value="">Select Category</option>

													<c:forEach items="${catList}" var="catList">
														<%-- <option value="${catList.catId}">${catList.catName}</option> --%> 
														<option value="${catList.catId}"
														${catList.catId == item.catId ? 'selected' : ''}>${catList.catName}</option> 
													</c:forEach>
												</select> <span class="validation-invalid-label text-danger"
													id="error_category" style="display: none;">This
													field is required.</span>
											</div>

											<label class="col-form-label font-weight-bold col-lg-2"
												for="sub_category">Select Sub-Category <span
												class="text-danger">* </span>:
											</label>
											<div class="col-lg-4">
												<select class="form-control select-search" data-fouc
													name="sub_category" id="sub_category"
													data-placeholder="Select Sub Category">

												</select> <span class="validation-invalid-label text-danger"
													id="error_sub_category" style="display: none;">This
													field is required.</span>
											</div>
											<input type="hidden" class="form-control"
												name=" editSubCatId" id="editSubCatId" autocomplete="off"
												onchange="trim(this)" value="${item.subCatId}">
										</div>

										<div class="form-group row">
											<label class="col-form-label font-weight-bold col-lg-2"
												for="category">Select Product Status<span
												class="text-danger">* </span>:
											</label>
											<div class="col-lg-4">
												<select class="form-control select-search" data-fouc
													name="product_stat" id="product_stat">
													<option value="">Select Product Status</option>

													<c:forEach items="${prdctList}" var="prdctList">
														<%-- <option value="${prdctList.productStatusId}">${prdctList.productStatus}</option> --%>
														<option value="${prdctList.productStatusId}"
														${prdctList.productStatusId == item.productStatusId ? 'selected="selected"' : ''}>${prdctList.productStatus}</option>
													</c:forEach>
												</select> <span class="validation-invalid-label text-danger"
													id="error_product_stat" style="display: none;">This
													field is required.</span>
											</div>

											<label class="col-form-label font-weight-bold col-lg-2"
												for="sub_category">Select Flavour <span
												class="text-danger">* </span>:
											</label>
											<div class="col-lg-4">
												<select class="form-control select-search" data-fouc
													name="flavour" id="flavour">
													<option value="">Select Flavour</option>

													<c:forEach items="${flavourList}" var="flavourList">
														<%-- <option value="${flavourList.flavourId}">${flavourList.flavourName}</option> --%>
														<option value="${flavourList.flavourId}"
														${flavourList.flavourId == item.flavourId ? 'selected="selected"' : ''}>${flavourList.flavourName}</option>
													</c:forEach>
												</select> <span class="validation-invalid-label text-danger"
													id="error_flavour" style="display: none;">This field
													is required.</span>
											</div>
										</div>

										<div class="form-group row">
											<label class="col-form-label font-weight-bold col-lg-2"
												for="category">Select Brand<span class="text-danger">*
											</span>:
											</label>
											<div class="col-lg-4">
												<select class="form-control select-search" data-fouc
													name="brand" id="brand">
													<option value="">Select Brand</option>

													<c:forEach items="${brandList}" var="brandList">
														<%-- <option value="${brandList.brandId}">${brandList.brandName}</option> --%>
														<option value="${brandList.brandId}"
														${brandList.brandId == item.brandId ? 'selected="selected"' : ''}>${brandList.brandName}</option>
													</c:forEach>
												</select> <span class="validation-invalid-label text-danger"
													id="error_brand" style="display: none;">This field
													is required.</span>
											</div>

											<label class="col-form-label font-weight-bold col-lg-2"
												for="sub_category">Select Tax <span
												class="text-danger">* </span>:
											</label>
											<div class="col-lg-4">
												<select class="form-control select-search" data-fouc
													name="tax" id="tax">
													<option value="">Select Tax</option>

													<c:forEach items="${taxList}" var="taxList">
														<%-- <option value="${taxList.taxInfoId}">${taxList.taxTitle}</option> --%>
														<option value="${taxList.taxInfoId}"
														${taxList.taxInfoId == item.taxId ? 'selected="selected"' : ''}>${taxList.taxTitle}</option> --%>
													</c:forEach>
												</select> <span class="validation-invalid-label text-danger"
													id="error_tax" style="display: none;">This field is
													required.</span>
											</div>
										</div>

										<div class="form-group row">
											<label class="col-form-label font-weight-bold col-lg-2"
												for="purchase_uom">Purchase UOM<span
												class="text-danger">* </span>:
											</label>
											<div class="col-lg-4">
												<select class="form-control select-search" data-fouc
													name="purchase_uom" id="purchase_uom">
													<option value="">Select Purchase UOM</option>

													<c:forEach items="${uomList}" var="uomList">
														<%-- <option value="${uomList.uomId}">${uomList.uom}</option> --%>
														<option value="${uomList.uomId}"
														${uomList.uomId == item.purchaseUom ? 'selected="selected"' : ''}>${uomList.uom}</option>
													</c:forEach>
												</select> <span class="validation-invalid-label text-danger"
													id="error_purchase_uom" style="display: none;">This
													field is required.</span>
											</div>

											<label class="col-form-label font-weight-bold col-lg-2"
												for="stock_uom">Stock UOM<span class="text-danger">*
											</span>:
											</label>
											<div class="col-lg-4">
												<select class="form-control select-search" data-fouc
													name="stock_uom" id="stock_uom">
													<option value="">Select Stock UOM</option>

													<c:forEach items="${uomList}" var="uomList">
														<%-- <option value="${uomList.uomId}">${uomList.uom}</option> --%>
														<option value="${uomList.uomId}"
														${uomList.uomId == item.stockUom ? 'selected="selected"' : ''}>${uomList.uom}</option>
													</c:forEach>
												</select> <span class="validation-invalid-label text-danger"
													id="error_stock_uom" style="display: none;">This
													field is required.</span>
											</div>
										</div>

										<div class="form-group row">
											<label class="col-form-label font-weight-bold col-lg-2"
												for="stock_purchase_uom">Stock To Purchase UOM<span
												class="text-danger">* </span>:
											</label>
											<div class="col-lg-4">
												<input type="text"
													class="form-control maxlength-badge-position" value="${item.stockToPurchaseUom}"
													name="stock_purchase_uom" id="stock_purchase_uom"
													maxlength="30" autocomplete="off" onchange="trim(this)">
												<span class="validation-invalid-label text-danger"
													id="error_stock_purchaseUom" style="display: none;">This
													field is required.</span>
											</div>

											<label class="col-form-label font-weight-bold col-lg-2"
												for="min_qty">Minimum Qty. <span class="text-danger"></span>:
											</label>
											<div class="col-lg-4">
												<input type="text"
													class="form-control maxlength-badge-position"
													name="min_qty" id="min_qty" maxlength="5" value="${item.minQty}"
													autocomplete="off" onchange="trim(this)"> <span
													class="validation-invalid-label text-danger"
													id="error_min_qty" style="display: none;">This field
													is required.</span>
											</div>
										</div>

										<div class="form-group row">

											<label class="col-form-label font-weight-bold col-lg-2"
												for="is_sale">Is Sale <span class="text-danger">*
											</span>:
											</label>
											<div class="col-lg-2">

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled" id="nSale"
														name="isSale" checked data-fouc value="1"
														${item.isSalebale==1 ? 'checked' : ''}> No
													</label>
												</div>

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled" id="ySale"
														name="isSale" data-fouc value="0"
														${item.isSalebale==0 ? 'checked' : ''}> Yes
													</label>
												</div>
											</div>

											<label class="col-form-label font-weight-bold col-lg-2">Is
												Bill <span class="text-danger">* </span>:
											</label>
											<div class="col-lg-2">

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled" id="nBill"
														name="isBill" checked data-fouc value="1"
														${item.isBillable==1 ? 'checked' : ''}> No
													</label>
												</div>

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled" id="yBill"
														name="isBill" data-fouc value="0"
														${item.isBillable==0 ? 'checked' : ''}> Yes
													</label>
												</div>
											</div>

											<label class="col-form-label font-weight-bold col-lg-2">Is
												Decimal <span class="text-danger">* </span>:
											</label>
											<div class="col-lg-2">

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled" id="nDecimal"
														name="isDecimal" checked data-fouc value="1"
														${item.isDecimal==1 ? 'checked' : ''}> No
													</label>
												</div>

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled" id="yDecimal"
														name="isDecimal" data-fouc value="0"
														${item.isDecimal==0 ? 'checked' : ''}> Yes
													</label>
												</div>
											</div>
										</div>

										<div class="form-group row">
											<label class="col-form-label font-weight-bold col-lg-2"
												for="show_order">Show Order Frequently <span
												class="text-danger">* </span>:
											</label>
											<div class="col-lg-2">

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled" id="nShowOrder"
														name="showOrder" checked data-fouc value="1"
														${item.showInOrderFrquntly==1 ? 'checked' : ''}> No
													</label>
												</div>

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled" id="yShowOrder"
														name="showOrder" data-fouc value="0"
														${item.showInOrderFrquntly==0 ? 'checked' : ''}> Yes
													</label>
												</div>
											</div>


											<label class="col-form-label font-weight-bold col-lg-2">Is
												Tag Applicable <span class="text-danger">* </span>:
											</label>
											<div class="col-lg-2">

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled"
														onclick="showTagDiv(this.value)" id="nTag" name="isTag"
														checked data-fouc value="1"
														${item.isTagApplicable==1 ? 'checked' : ''}> No
													</label>
												</div>

												<div class="form-check form-check-inline">
													<label class="form-check-label"> <input
														type="radio" class="form-input-styled"
														onclick="showTagDiv(this.value)" id="yTag" name="isTag"
														data-fouc value="0"
														${item.isTagApplicable==0 ? 'checked' : ''}> Yes
													</label>
												</div>
											</div>
										</div>

										<div class="form-group row">
										<c:choose>
											<c:when test="${item.isTagApplicable==0}">
											<div class="col-lg-12" id="tag_div">
												<label class="col-form-label font-weight-bold col-lg-2"
													style="float: left;" for="cust_name">Tags <span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-10" style="float: left;">
													<select class="form-control select-search" data-fouc
														name="selectTag" id="selectTag" multiple="multiple">
														<c:forEach items="${tagList}" var="tag">
															<%-- <option value="${tag.tagId}">${tag.tagName}</option> --%>
															<option value="${tag.tagId}"
																	${fn:contains(tagIds, tag.tagId) ? 'selected' : ''}>${tag.tagName}</option>
														</c:forEach>
													</select>
													</div>
												</div>
												</c:when>
												
											<c:otherwise>
											<div class="col-lg-12" id="tag_div" style="display: none;">
												<label class="col-form-label font-weight-bold col-lg-2"
													style="float: left;" for="cust_name">Tags <span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-10" style="float: left;">
													<select class="form-control select-search" data-fouc
														name="selectTag" id="selectTag" multiple="multiple">
														<c:forEach items="${tagList}" var="tag">
															<option value="${tag.tagId}">${tag.tagName}</option>
														</c:forEach>
													</select>
													</div>
												</div>
											</c:otherwise>
											</c:choose>
												
												<div>
											</div>
										</div>

										<br>
										<div class="text-center">
											<button type="submit" class="btn btn-danger" id="submtbtn">
												Save <i class="icon-paperplane ml-2"></i>
											</button>
										</div>
										</form>
									</div>
<!-- **************************************************************************************************************************** -->
									<!--------------------- TAB 2---------------------------->
									<div class="tab-pane fade" id="bordered-tab2">
									<form action="${pageContext.request.contextPath}/saveItemSkuConfig"
										id="submitInsertSku" method="post"> 
											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="itemName">Item Name <span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" name="itemName"
														id="itemName" readonly="readonly" value="${item.itemName}">
												</div>
												<div class="col-lg-4">
													<input type="hidden" class="form-control" name="itemId"
														id="itemId" value="${item.itemId}">
												</div>
											</div>

											<hr>

									 
											<div class="form-group row">
											<input type="hidden" id="sku_id" name="sku_id">
											
												<label class="col-form-label font-weight-bold col-lg-2"
													for="sku_name">SKU Name <span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control maxlength-badge-position" name="sku_name"
														id="sku_name" maxlength="30" autocomplete="off" onchange="trim(this)"> <span
														class="validation-invalid-label text-danger"
														id="error_sku_name" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">SKU UOM<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<select class="form-control select-search" data-fouc
														name="sku_uom" id="sku_uom">
														<option value="">Select UOM</option>

														<c:forEach items="${uomList}" var="uomList">
															<option value="${uomList.uomId}">${uomList.uom}</option>
															<%-- 	<option value="${catList.cityId}"
														${catList.cityId == config.cityIds ? 'selected="selected"' : ''}>${catList.cityName}</option> --%>
														</c:forEach>
													</select> <span class="validation-invalid-label text-danger"
														id="error_sku_uom" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="stock">Stock Qty.<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control maxlength-badge-position" name="stock"
														id="stock" maxlength="5" autocomplete="off" onchange="trim(this)"> <span
														class="validation-invalid-label text-danger"
														id="error_stock" style="display: none;">This field
														is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Stock To Sell<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control maxlength-badge-position" name="stock_sell"
														id="stock_sell" maxlength="5" autocomplete="off" onchange="trim(this)"> <span
														class="validation-invalid-label text-danger"
														id="error_stock_sell" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<table class="table">
												<thead>
												<tr>
													<c:forEach items="${rateTypeList}" var="rateTypeList"
														varStatus="count">
																<th>${rateTypeList.rateTypeName}</th>
													</c:forEach>
													</tr>
												</thead>
											 <tbody>
											  <tr>
							<c:forEach items="${rateTypeList}" var="rateTypeList" varStatus="count">
								
									<td>
									<input type="hidden" class="rateTypeId" 
																name="rateTypeId${rateTypeList.skuRateTypeId}"
																id="rateTypeId${rateTypeList.skuRateTypeId}"
																value="${rateTypeList.skuRateTypeId}"
																readonly="readonly">
										<input type="text" class="form-control" name="rate${rateTypeList.skuRateTypeId}"
												id="rate${rateTypeList.skuRateTypeId}">
									</td>																
								
							</c:forEach> 	
							</tr>			
						</tbody> 
											</table>
											<br>
											<div class="text-center">
												<button type="submit" class="btn btn-danger" id="submtSkubtn">
													Save <i class="icon-paperplane ml-2"></i>
												</button>
											</div>
											</form>	
											<br>
											
								<!-- ************************************************************************************** -->
								
								<div class="alert alert-success border-0 alert-dismissible" id="success_div" style="display: none;">
									<button type="button" class="close" data-dismiss="alert"><span>&times;</span></button>
									<span class="font-weight-semibold">SKU Configuration Update Successfully</span> 
							    </div>
								
								
										<c:forEach items="${typeList}" var="typeList"
														varStatus="count">
											<div class="row">
												<div class="col-md-12">
													<div class="card" style="background: #999;">
														<div class="card-body">
														
											<%-- <form action="${pageContext.request.contextPath}/saveItemSkuConfig"
											id="submitInsert" method="post">  --%>
											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="itemName">Item Name <span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" name="itemName" placeholder="Enter Value"
														id="itemName" readonly="readonly" value="${item.itemName}" maxlength="30">
												</div>
												<div class="col-lg-4">
													<input type="hidden" class="form-control" name="itemId"
														id="itemId" value="${item.itemId}">
												</div>
												
											<!-- Toggle Button -->	
											<div class="form-check form-check-switchery form-check-switchery-double">
												<label class="form-check-label">											
												<input type="checkbox" id="chk${typeList.skuId}" onchange="setBtnShow(${typeList.skuId})" 
												class="form-check-input-switchery" data-fouc>
												 </label> 
											 </div>
											</div>

											<hr>

									 
											<div class="form-group row">
											<input type="hidden" id="sku_id${typeList.skuId}" name="sku_id${typeList.skuId}" value="${typeList.skuId}">
											
												<label class="col-form-label font-weight-bold col-lg-2"
													for="sku_name">SKU Name <span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" name="sku_name${typeList.skuId}"
														id="sku_name${typeList.skuId}" value="${typeList.skuName}"> <span
														class="validation-invalid-label text-danger"
														id="error_sku_name" style="display: none;">This
														field is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">SKU UOM<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<select class="form-control select-search" data-fouc
														name="sku_uom${typeList.skuId}" id="sku_uom${typeList.skuId}">
														<option value="">Select UOM</option>

														<c:forEach items="${uomList}" var="uomList">
															<%-- <option value="${uomList.uomId}">${uomList.uom}</option> --%>
															<option value="${uomList.uomId}"
														${uomList.uomId == typeList.saleUom ? 'selected="selected"' : ''}>${uomList.uom}</option> 
														</c:forEach>
													</select> <span class="validation-invalid-label text-danger"
														id="error_sku_uom" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<div class="form-group row">
												<label class="col-form-label font-weight-bold col-lg-2"
													for="stock">Stock Qty.<span class="text-danger">*
												</span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" name="stock${typeList.skuId}"
														id="stock${typeList.skuId}" value="${typeList.skuStockQty}"> <span
														class="validation-invalid-label text-danger"
														id="error_stock" style="display: none;">This field
														is required.</span>
												</div>

												<label class="col-form-label font-weight-bold col-lg-2"
													for="cust_name">Stock To Sell<span
													class="text-danger">* </span>:
												</label>
												<div class="col-lg-4">
													<input type="text" class="form-control" name="stock_sell${typeList.skuId}"
														id="stock_sell${typeList.skuId}"  value="${typeList.stockToSaleUom}"> <span
														class="validation-invalid-label text-danger"
														id="error_stock_sell" style="display: none;">This
														field is required.</span>
												</div>
											</div>

											<table class="table">
												<thead>
												<tr>
												<c:forEach items="${skuDtlList}" var="skuDtlList"
														varStatus="count">
														<c:if test="${typeList.skuId==skuDtlList.skuId}">
																<th>${skuDtlList.rateTypeName}</th>
														</c:if>
												</c:forEach>
													</tr>
												</thead>
											 <tbody>
											  <tr>
							  	<c:forEach items="${skuDtlList}" var="skuDtlList" varStatus="count">
							  
								<c:if test="${typeList.skuId==skuDtlList.skuId}">
							
								<td style="display: none;"><input type="hidden" class="skuDetailId${skuDtlList.skuId}" name="skuDtl${skuDtlList.skuId}"
										id="skuDtl${skuDtlList.skuId}" value="${skuDtlList.skuDetailId}"
										readonly="readonly"></td>
								
								<td>
									<input type="hidden" class="rateTypeId${skuDtlList.skuId}"  name="rateTypeId${skuDtlList.skuId}"
										id="rateTypeId${skuDtlList.skuId}" value="${skuDtlList.rateTypeId}"
										readonly="readonly">
										
									<input type="text" class="form-control form-control-rateTypeValue${skuDtlList.skuId}" value="${skuDtlList.rateTypeMrp}"
										id="typeMrp${skuDtlList.skuId}" name="typeMrp${skuDtlList.skuId}"> 
									</td>	
									</c:if>															
								
							</c:forEach>  	
							</tr>			
						</tbody> 
											</table>
											<br>
											<div class="text-center" id="btnDiv${typeList.skuId}" style="display: none;">
												<button type="submit" class="btn btn-danger"  onclick="updtSku(${typeList.skuId})" id="skuDtlBtn">
													Update <i class="icon-paperplane ml-2"></i>
												</button>
												
												<a href="#" onclick="deleteSkuDetail(${typeList.skuId})"><i class="fas fa-trash mr-3 fa-1x"
												style="float: right; color: #fff;"></i></a>
											</div>
											<!-- </form>	 -->
														
														</div>
													</div>
												</div>
											</div>	
											</c:forEach>
								<!-- ****************************************************************** -->								
									</div>
								</div>
							</div>
						</div>
						<!-- /a legend -->

					</div>
				</div>
			</div>
			<!-- /content area -->


			<!-- Footer -->
			<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
			<!-- /footer -->

		</div>
		<!-- /main content -->

	</div>
	<!-- /page content -->



	<script>
		$("#category")
				.on(
						'change',
						function() {

							var category = $("#category").val();
							var id = $("#editSubCatId").val();
							
							$
									.getJSON(
											'${getSubCatByCatId}',
											{
												category : category,
												ajax : 'true',
											},
											function(data) {

												$('#sub_category').find(
														'option').remove()
														.end()
												$("#sub_category")
														.append(
																$("<option value=''>Select Sub-Category</option>"));

												for (var i = 0; i < data.length; i++) {
													var flag = 0;
													if (data[i].subCatId == id) {
														flag = 1;
													}
													if (flag == 1) {
														$("#sub_category")
																.append(
																		$(
																				"<option selected></option>")
																				.attr(
																						"value",
																						data[i].subCatId)
																				.text(
																						data[i].subCatName));
													} else {
														$("#sub_category")
																.append(
																		$(
																				"<option></option>")
																				.attr(
																						"value",
																						data[i].subCatId)
																				.text(
																						data[i].subCatName));
													}
												}
												$("#sub_category").trigger(
														"chosen:updated");
											});
						});

		function showTagDiv(id) {
			if (id == 0) {
				document.getElementById("tag_div").style.display = "block";
			} else {
				document.getElementById("tag_div").style.display = "none";

			}
		}
	</script>

	<script type="text/javascript">
		function updtSku(skuId) {;
			
			 var itemList = [];

			$(".form-control-rateTypeValue"+skuId)
					.each(
							function(counter) {								

								
								var skuDetailId = document
											.getElementsByClassName("skuDetailId"+skuId)[counter].value;
									var rateId = document
											.getElementsByClassName("rateTypeId"+skuId)[counter].value;
									
									var rateValue = document
											.getElementsByClassName("form-control-rateTypeValue"+skuId)[counter].value;

									var item = {
										skuDetailId : skuDetailId,
										rateTypeId : rateId,
										rateTypeMrp : rateValue
									}

									itemList.push(item);
								

							});
			//alert("Rate-----"+JSON.stringify(itemList))
			//e.preventDefault();
			$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/updtSku",
				data : {
					skuId : skuId,
					skuName : $("#sku_name"+skuId).val(),
					skuUom : $("#sku_uom"+skuId).val(),
					skuStock : $("#stock"+skuId).val(),
					skuSellStock : $("#stock_sell"+skuId).val(),
					itemId : $("#itemId").val(),
					json : JSON.stringify(itemList),
				},
				success : function(result) {
					$("#sucesMsg").val(result.message)
					$("#success_div").show("slow").delay(5000).hide("slow");
										
				},
				error : function(result) {
					$("#sucesMsg").val(result.message)
					$("#success_div").show("slow").delay(5000).hide("slow");
					
				}
			});
		}

		function deleteSkuDetail(skuDtlId){
			//alert(skuDtlId);
			if(skuDtlId>0){
			$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/deleteSkuDetail",
				data : {
					skuDtlId : skuDtlId,					
				},
				success : function(result) {	
					//$("#success_div").show("slow").delay(5000).hide("slow");
										
				},
				error : function(result) {
					location.reload();
					//$("#success_div").show("slow").delay(5000).hide("slow");
				}
			});
		}
			
		/* 	$('.bootbox_custom')
			.on(
					'click',
					function() {
						var uuid = $(this).data() // will return the number 123
									bootbox.confirm({
									title : 'Confirm ',
									message : 'Are you sure you want to delete selected records ?',
									buttons : {
										confirm : {
											label : 'Yes',
											className : 'btn-success'
										},
										cancel : {
											label : 'Cancel',
											className : 'btn-link'
										}
									},
									callback : function(result) {
										if (result) {
											location.href = "${pageContext.request.contextPath}/deleteItem?itemId="
													+ uuid;

										}
									}
								});
					}); */
	}
		
	 $(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#item_name").val()) {
					isError = true;
					$("#error_item_name").show()
				} else {
					$("#error_item_name").hide()
				}
				if (!$("#item_code").val()) {
					isError = true;
					$("#error_item_code").show()
				} else {
					$("#error_item_code").hide()
				}
				if (!$("#category").val()) {
					isError = true;
					$("#error_category").show()
				} else {
					$("#error_category").hide()
				}
				if (!$("#category").val()) {
					isError = true;
					$("#error_category").show()
				} else {
					$("#error_category").hide()
				}

				if (!$("#sub_category").val()) {
					isError = true;
					$("#error_sub_category").show()
				} else {
					$("#error_sub_category").hide()
				}

				if (!$("#flavour").val()) {
					isError = true;
					$("#error_flavour").show()
				} else {
					$("#error_flavour").hide()
				}
				if (!$("#brand").val()) {
					isError = true;
					$("#error_brand").show()
				} else {
					$("#error_brand").hide()
				}
				if (!$("#tax").val()) {
					isError = true;
					$("#error_tax").show()
				} else {
					$("#error_tax").hide()
				}
				if (!$("#product_stat").val()) {
					isError = true;
					$("#error_product_stat").show()
				} else {
					$("#error_product_stat").hide()
				}
				if (!$("#purchase_uom").val()) {
					isError = true;
					$("#error_purchase_uom").show()
				} else {
					$("#error_purchase_uom").hide()
				}
				if (!$("#stock_uom").val()) {
					isError = true;
					$("#error_stock_uom").show()
				} else {
					$("#error_stock_uom").hide()
				}
				if (!$("#stock_purchase_uom").val()) {
					isError = true;
					$("#error_stock_purchaseUom").show()
				} else {
					$("#error_stock_purchaseUom").hide()
				}
				if (!$("#min_qty").val()) {
					isError = true;
					$("#error_min_qty").show()
				} else {
					$("#error_min_qty").hide()
				}
				
				if (!isError) {
					var x = true;
					if (x == true) {
						document.getElementById("submtbtn").disabled = true;
						return true;
					}
				}

				return false;

			});
		}); 
	 
	 $(document).ready(function($) {

			$("#submitInsertSku").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#sku_name").val()) {
					isError = true;
					$("#error_sku_name").show()
				} else {
					$("#error_sku_name").hide()
				}
				
				if (!$("#sku_uom").val()) {
					isError = true;
					$("#error_sku_uom").show()
				} else {
					$("#error_sku_uom").hide()
				}
				
				if (!$("#stock").val()) {
					isError = true;
					$("#error_stock").show()
				} else {
					$("#error_stock").hide()
				}
				
				if (!$("#stock_sell").val()) {
					isError = true;
					$("#error_stock_sell").show()
				} else {
					$("#error_stock_sell").hide()
				}
				
				if (!isError) {
					var x = true;
					if (x == true) {
						document.getElementById("submtSkubtn").disabled = true;
						return true;
					}
				}

				return false;

			});
		}); 
		
		/* $("#submtSkubtn").click(function(e) {
			
			var itemId = $("#itemId").val();
			var skuName = $("#sku_name").val();
			var skuUom = $("#sku_uom").val();
			var stock = $("#stock").val();
			var stockToSell = $("#stock_sell").val();
			
			var itemList = [];

			$(".rateTypeValue")
					.each(
							function(counter) {								

									var rateId = document
											.getElementsByClassName("rateTypeId")[counter].value;
									var rateValue = document
											.getElementsByClassName("rateTypeValue")[counter].value;

									var item = {
										id : rateId,
										rate : rateValue
									}

									itemList.push(item);
								

							});
			alert("Rate-----"+JSON.stringify(itemList))

			e.preventDefault();
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/saveItemSkuConfig",
				data : {
					skuId: $("#sku_id").val(),
					itemId : $("#itemId").val(),
					skuName : $("#sku_name").val(),
					skuUom : $("#sku_uom").val(),
					stock : $("#stock").val(),
					stockToSell : $("#stock_sell").val(),
					jsonStr : JSON.stringify(itemList),
				},
				success : function(result) {
					
					$("#success_div").show();
				},
				error : function(result) {
					$("#fail_div").show();
				}
			});
		}); */

		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines

			return;
		}

		$('.maxlength-options').maxlength({
			alwaysShow : true,
			threshold : 10,
			warningClass : 'text-success form-text',
			limitReachedClass : 'text-danger form-text',
			separator : ' of ',
			preText : 'You have ',
			postText : ' chars remaining.',
			validate : true
		});

		$('.maxlength-badge-position').maxlength({
			alwaysShow : true,
			placement : 'top'
		});
	
		function setBtnShow(id){
			//alert(document.getElementById("chk"+id).checked)
			
			if(document.getElementById("chk"+id).checked==true){
				document.getElementById("btnDiv"+id).style.display = "block";
			}else{
				document.getElementById("btnDiv"+id).style.display = "none";
			}
		}
	</script>
</body>
</html>