<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
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
								<span class="font-size-sm text-uppercase font-weight-semibold"><a class="card-title"
									href="${pageContext.request.contextPath}/showProductStatusList"
									style="color: white;">Product Status List</a></span>
							</div>



							<div class="card-body">

								<form action="${pageContext.request.contextPath}/insertNewProductStatus"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>


									<%-- <input type="hidden" class="form-control" name="productStat_id"
										id="productStat_id" value="${product.productStatusId}"> --%>
										
										
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="item_name">Item Name<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="item_name"
												id="item_name"  maxlength="30" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label text-danger" id="error_item_name"
												style="display: none;">This field is required.</span><!-- value="${product.productStatus}" -->
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="item_code">Item Code <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="item_code"
												id="item_code"  maxlength="80" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label text-danger" id="error_item_code"
												style="display: none;">This field is required.</span><!-- value="${product.description}" -->
										</div>
									</div>
										
									<div class="form-group row">									
										<label class="col-form-label font-weight-bold col-lg-2"
											for="sub_category">Short Name <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="short_name"
												id="short_name"  maxlength="30" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label text-danger" id="error_short_name"
												style="display: none;">This field is required.</span><!-- value="${product.description}" -->
										</div>
										
										
										<label class="col-form-label font-weight-bold col-lg-2">Is Item Used
											<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled"
													id="yActive" name="isActive" checked data-fouc
													value="0"> In-Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled"
													id="yActive" name="isActive" data-fouc value="1">
													Active
												</label>
											</div>
										</div>										
									</div>
										
										
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="category">Select Category<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="category" id="category">
												<option value="">Select Category</option>

												<c:forEach items="${catList}" var="catList">
												<option value="${catList.catId}">${catList.catName}</option>
												<%-- 	<option value="${catList.cityId}"
														${catList.cityId == config.cityIds ? 'selected="selected"' : ''}>${catList.cityName}</option> --%>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger" id="error_cityIds"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="sub_category">Select Sub-Category <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="sub_category" id="sub_category"
												data-placeholder="Select Sub Category">
												
											</select> <span class="validation-invalid-label text-danger" id="error_areaIds"
												style="display: none;">This field is required.</span>
										</div>
										<input type="hidden" class="form-control" name=" editSubCatId"
											id="editSubCatId" autocomplete="off" onchange="trim(this)"><!-- value="1" -->
									</div>
										
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="category">Select Product Status<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="product_stat" id="product_stat" multiple="multiple">
												<option value="">Select Product Status</option>

												<c:forEach items="${prdctList}" var="prdctList">
												<option value="${prdctList.productStatusId}">${prdctList.productStatus}</option>
												<%-- 	<option value="${catList.cityId}"
														${catList.cityId == config.cityIds ? 'selected="selected"' : ''}>${catList.cityName}</option> --%>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger" id="error_product_stat"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="sub_category">Select Flavour <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="flavour" id="flavour">
												<option value="">Select Flavour</option>

												<c:forEach items="${flavourList}" var="flavourList">
												<option value="${flavourList.flavourId}">${flavourList.flavourName}</option>
												<%-- 	<option value="${catList.cityId}"
														${catList.cityId == config.cityIds ? 'selected="selected"' : ''}>${catList.cityName}</option> --%>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger" id="error_flavour"
												style="display: none;">This field is required.</span>
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
												<option value="${brandList.brandId}">${brandList.brandName}</option>
												<%-- 	<option value="${catList.cityId}"
														${catList.cityId == config.cityIds ? 'selected="selected"' : ''}>${catList.cityName}</option> --%>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger" id="error_brand"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="sub_category">Select Tax <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="tax" id="tax">
												<option value="">Select Tax</option>

												<c:forEach items="${taxList}" var="taxList">
												<option value="${taxList.taxInfoId}">${taxList.taxTitle}</option>
												<%-- 	<option value="${catList.cityId}"
														${catList.cityId == config.cityIds ? 'selected="selected"' : ''}>${catList.cityName}</option> --%>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger" id="error_flavour"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="purchase_uom">Purchase UOM<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc
												name="purchase_uom" id="purchase_uom">
												<option value="">Select Purchase UOM</option>

												<c:forEach items="${uomList}" var="uomList">
												<option value="${uomList.uomId}">${uomList.uom}</option>
												<%-- 	<option value="${catList.cityId}"
														${catList.cityId == config.cityIds ? 'selected="selected"' : ''}>${catList.cityName}</option> --%>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger" id="error_purchase_uom"
												style="display: none;">This field is required.</span>
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
												<option value="${uomList.uomId}">${uomList.uom}</option>
												<%-- 	<option value="${catList.cityId}"
														${catList.cityId == config.cityIds ? 'selected="selected"' : ''}>${catList.cityName}</option> --%>
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger" id="error_stock_uom"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="stock_purchase_uom">Stock To Purchase UOM<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="stock_purchase_uom"
												id="stock_purchase_uom"  maxlength="30" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label text-danger" id="error_stock_purchaseUom"
												style="display: none;">This field is required.</span><!-- value="${product.productStatus}" -->
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="min_qty">Minimum Qty. <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="min_qty"
												id="min_qty"  maxlength="5" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label text-danger" id="error_item_code"
												style="display: none;">This field is required.</span><!-- value="${product.description}" -->
										</div>
									</div>
									
									<div class="form-group row">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="is_sale">Is Sale <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-2">

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled"
													id="nSale" name="isSale" checked data-fouc
													value="0"> No
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled"
													id="ySale" name="isSale" data-fouc value="1">
													Yes
												</label>
											</div>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2">Is Bill
											<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-2">

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled"
													id="nBill" name="isBill" checked data-fouc
													value="0"> No
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled"
													id="yBill" name="isBill" data-fouc value="1">
													Yes
												</label>
											</div>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2">Is Decimal
											<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-2">

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled"
													id="nDecimal" name="isDecimal" checked data-fouc
													value="0"> No
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled"
													id="yDecimal" name="isDecimal" data-fouc value="1">
													Yes
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
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled"
													id="nShowOrder" name="showOrder" checked data-fouc
													value="0"> No
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled"
													id="yShowOrder" name="showOrder" data-fouc value="1">
													Yes
												</label>
											</div>
										</div>

										
										<label class="col-form-label font-weight-bold col-lg-2">Is Tag Applicabel
											<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-2">

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled" onclick="showTagDiv(this.value)"
													id="yTag" name="isTag" checked data-fouc
													value="0"> No
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-input-styled" onclick="showTagDiv(this.value)"
													id="yTag" name="isTag" data-fouc value="1">
													Yes
												</label>
											</div>
										</div>
									</div>
									
									<div class="form-group row">
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
										<div></div>
									</div>
									
									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-danger" id="submtbtn">
											Save <i class="icon-paperplane ml-2"></i>
										</button>
									</div>
								</form>
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
		$("#category").on(
				'change',
				function() {

					var category = $("#category").val();
					var id = $("#editSubCatId").val();

					$.getJSON('${getSubCatByCatId}', {
						category : category,
						ajax : 'true',
					}, function(data) {

						$('#sub_category').find('option').remove().end()
						$("#sub_category").append(
								$("<option value=''>Select Sub-Category</option>"));
						
						
						for (var i = 0; i < data.length; i++) {	
							var flag=0;
							if (data[i].subCatId == id) {
								flag = 1;
							}
							if (flag == 1) {
								$("#sub_category").append(
										$("<option selected></option>").attr("value",
												data[i].subCatId).text(
												data[i].subCatName));
							}else{
								$("#sub_category").append(
										$("<option></option>").attr("value",
												data[i].subCatId).text(
												data[i].subCatName));
							}
						} 						
						$("#sub_category").trigger("chosen:updated");
					});
				});
		
		function showTagDiv(id) {

			if (id > 0) {
				document.getElementById("tag_div").style.display = "block";
			} else {
				document.getElementById("tag_div").style.display = "none";

			}
		}
	</script>

	<script type="text/javascript">
		/* $(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#prdct_stat").val()) {
					isError = true;
					$("#error_prdct_stat").show()
				} else {
					$("#error_prdct_stat").hide()
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
		}); */
		
		function trim(el) {
			el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
			replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
			replace(/\n +/, "\n"); // Removes spaces after newlines

			return;
		}

		$('.maxlength-options').maxlength({
		    alwaysShow: true,
		    threshold: 10,
		    warningClass: 'text-success form-text',
		    limitReachedClass: 'text-danger form-text',
		    separator: ' of ',
		    preText: 'You have ',
		    postText: ' chars remaining.',
		    validate: true
		});

		$('.maxlength-badge-position').maxlength({
		    alwaysShow: true,
		    placement: 'top'
		});
	</script>

</body>
</html>