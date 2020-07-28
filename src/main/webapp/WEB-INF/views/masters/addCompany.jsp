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
</style>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs">
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
								<span class="font-size-sm text-uppercase font-weight-semibold"><h6
										class="card-title">${title}</h6></span> <span
									class="font-size-sm text-uppercase font-weight-semibold"><a
									href="${pageContext.request.contextPath}/showCompanies"
									style="color: white;" class="card-title">Companies List</a></span>
							</div>


							<div class="card-body">

								<form action="${pageContext.request.contextPath}/insertNewCompany" id="submitInsert" 
								method="post" enctype="multipart/form-data">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
										<input type="hidden" class="form-control" value="${company.companyId}" name="company_id" id="company_id">
									<div class="form-group row">
										
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="company_name">Company Name <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											 maxlength="30" autocomplete="off" onchange="trim(this)"
											value="${company.companyName}" name="company_name" id="company_name">
											<span
												class="validation-invalid-label" id="error_company_name"
												style="display: none;">This field is required.</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="address">Address <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"class="form-control maxlength-badge-position" 
											 maxlength="70" autocomplete="off" onchange="trim(this)"
											 value="${company.companyAddress}" name="address" id="address">
											<span
												class="validation-invalid-label" id="error_address"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									

									<div class="form-group row">
									<label class="col-form-label font-weight-bold col-lg-2"
											for="contact_no">Contact No.<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											 maxlength="10" autocomplete="off" onchange="trim(this)"
											value="${company.companyContactNo}" name="contact_no" id="contact_no">
											<span
												class="validation-invalid-label" id="error_contact_no"
												style="display: none;">This field is required.</span>												
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="email">Email <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="email" class="form-control maxlength-badge-position" 
											 maxlength="30" autocomplete="off" onchange="trim(this)"
											 value="${company.companyEmail}" name="email" id="email">
											<span
												class="validation-invalid-label" id="error_email"
												style="display: none;">This field is required.</span>
										</div>								
										
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="contact_person">Contact Person<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											 autocomplete="off" maxlength="30"
											value="${company.contactPersonName}" name="contact_person" id="contact_person">
											<span
												class="validation-invalid-label" id="error_contact_person"
												style="display: none;">This field is required.</span>
										</div>		
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="mob_no">Mobile No.<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											 autocomplete="off"  maxlength="10"
											value="${company.contactPersonMobile}" name="mob_no" id="mob_no">
											<span
												class="validation-invalid-label" id="error_mob_no"
												style="display: none;">This field is required.</span>
										</div>																											
									</div>
									
									<div class="form-group row">									
										<label class="col-form-label font-weight-bold col-lg-2"
											for="contact_person1">Contact Person1<span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											 autocomplete="off" maxlength="30"
											value="${company.contactPersonName1}" name="contact_person1" id="contact_person1">
											<span
												class="validation-invalid-label" id="error_contact_person1"
												style="display: none;">This field is required.</span>
										</div>		
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="mob_no1">Mobile No.1<span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											 autocomplete="off"  maxlength="10"
											value="${company.contactPersonMobile1}" name="mob_no1" id="mob_no1">
											<span
												class="validation-invalid-label" id="error_mob_no1"
												style="display: none;">This field is required.</span>
										</div>	
									</div>
									
									
									<div class="form-group row">									
									<label class="col-form-label font-weight-bold col-lg-2"
											for="city">City<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<select class="form-control select-search" data-fouc name="city" id="city">
												<option value="">Select City</option>
												
												<c:forEach items="${cityList}" var="cityList" varStatus="count">	
													<c:choose>
													 <c:when test="${cityList.cityId==company.city}">								
														<option selected value="${cityList.cityId}">${cityList.cityName}</option>																								
													</c:when>
													<c:otherwise>
														<option value="${cityList.cityId}">${cityList.cityName}</option>		
													</c:otherwise>
													</c:choose>		
												</c:forEach>
											</select>
											<span
												class="validation-invalid-label" id="error_city"
												style="display: none;">This field is required.</span>
										</div>	
											
									<label class="col-form-label font-weight-bold col-lg-2"
											for="website">Web Site <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											 maxlength="30" autocomplete="off" onchange="trim(this)"											
											value="${company.companyWebsite}" name="website" id="website">
											<span
												class="validation-invalid-label" id="error_website"
												style="display: none;">This field is required.</span>
										</div>										
									</div>
									
									<div class="form-group row">									
									<label class="col-form-label font-weight-bold col-lg-2"
											for="mob_no1">Company GST No<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											 autocomplete="off" maxlength="15" style="text-transform: uppercase;"
											value="${company.companyGstNo}" name="gst_no" id="gst_no">
											<span
												class="validation-invalid-label" id="error_gst_no"
												style="display: none;">This field is required.</span>
										</div>	
											
									<label class="col-form-label font-weight-bold col-lg-2"
											for="cin_no">CIN No.<span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											 maxlength="21" autocomplete="off" onchange="trim(this)"  style="text-transform: uppercase;"									
											value="${company.cinNo}" name="cin_no" id="cin_no">
											<span
												class="validation-invalid-label" id="error_cin_no"
												style="display: none;">This field is required.</span>
										</div>										
									</div>
									
									<div class="form-group row">									
									<label class="col-form-label font-weight-bold col-lg-2"
											for="mob_no1">Registration Date<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control datepickerclass" 
											 autocomplete="off"
											value="${company.regDate}" name="reg_date" id="reg_date">
											<span
												class="validation-invalid-label" id="error_reg_date"
												style="display: none;">This field is required.</span>
										</div>
											
										<label class="col-form-label font-weight-bold col-lg-2"
											for="mob_no1">Starting Date<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control datepickerclass" 
											 autocomplete="off" 
											value="${company.startingDate}" name="start_date" id="start_date">
											<span
												class="validation-invalid-label" id="error_start_date"
												style="display: none;">This field is required.</span>
										</div>									
									</div>
									
									<div class="form-group row">		
									
																	
									<label class="col-form-label font-weight-bold col-lg-2"
											for="mob_no1">PAN Card<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											 autocomplete="off"  maxlength="10" style="text-transform: uppercase;"
											value="${company.panCard}" name="pan_card" id="pan_card">
											<span
												class="validation-invalid-label" id="error_pan_card"
												style="display: none;">This field is required.</span>
										</div>	
										
											 <label class="col-form-label font-weight-bold col-lg-2"
											for="email">Status <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
										<div class="form-check form-check-inline">
										<label class="form-check-label">
											<input type="radio" class="form-check-input" checked value="0" name="act_comp" id="comp_y"
											${company.isUsed==0 ? 'checked' : ''}>
											Active
										</label>
									</div>

									<div class="form-check form-check-inline">
										<label class="form-check-label">
											<input type="radio" class="form-check-input" value="1" name="act_comp" id="comp_n"
											${company.isUsed==1 ? 'checked' : ''}>
											In-Active
										</label>
									</div>
									</div> 				
									</div>
									
									<div class="form-group row">										
									<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Company Logo <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
										<label class="form-check-label">
										<img id="output" width="150" src="${imgPath}${company.companyLogo}"/>
											<input type="file" class="form-control-uniform" data-fouc onchange="loadFile(event)" name="doc" id="doc">
											<input type="hidden" class="form-control-uniform"  name="editImg" id="editImg" value="${company.companyLogo}">
											<span
												class="validation-invalid-label" id="error_doc"
												style="display: none;">This field is required.</span>
										</label>
									</div>	
									</div>

									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-danger">
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
 <script type="text/javascript">
 var loadFile = function(event) {
	 try {
		var image = document.getElementById('output');
		image.src = URL.createObjectURL(event.target.files[0]);
	 } catch(err) {
		 console.log(err);
		}
	};
</script>
	
	<script type="text/javascript">

$(document)
		.ready(
				function($) {

					$("#submitInsert")
							.submit(
									function(e) {
										var isError = false;
										var errMsg = "";
										
										if (!$("#company_name").val()) {
											isError = true;
											$("#error_company_name").show()
										} else {
											$("#error_company_name").hide()
										}
										
										if (!$("#contact_no").val()) {
											isError = true;
											$("#error_contact_no").show()
										} else {
											$("#error_contact_no").hide()
										}
										
										if (!$("#mob_no").val()|| !validateMobile($(
										"#mob_no")
										.val())) {
											isError = true;
											$("#error_mob_no").show()
										} else {
											$("#error_mob_no").hide()
										}
										
										 if (!$("#address").val()) {
											isError = true;
											$("#error_address").show()
										} else {
											$("#error_address").hide()
										} 
										
										if (!$("#contact_person").val()) {
											isError = true;
											$("#error_contact_person").show()
										} else {
											$("#error_contact_person").hide()
										}
										
										if (!$("#gst_no").val()) {
											isError = true;
											$("#error_gst_no").show()
										} else {
											$("#error_gst_no").hide()
										}
										
										if (!$("#reg_date").val()) {
											isError = true;
											$("#error_reg_date").show()
										} else {
											$("#error_reg_date").hide()
										}
										
										if (!$("#start_date").val()) {
											isError = true;
											$("#error_start_date").show()
										} else {
											$("#error_start_date").hide()
										}
										
										if (!$("#email").val() || !validateEmail($(
										"#email")
										.val())) {
											isError = true;
											$("#error_email").show()
										} else {
											$("#error_email").hide()
										}
										
										if (!$("#city").val()) {
											isError = true;
											$("#error_city").show()
										} else {
											$("#error_city").hide()
										}
										
										if (!$("#website").val()) {
											isError = true;
											$("#error_website").show()
										} else {
											$("#error_website").hide()
										}
										
										
										if (!$("#pan_card").val()) {
											isError = true;
											$("#error_pan_card").show()
										} else {
											$("#error_pan_card").hide()
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
				
</script>
<script type="text/javascript">
function trim(el) {
	el.value = el.value.replace(/(^\s*)|(\s*$)/gi, ""). // removes leading and trailing spaces
	replace(/[ ]{2,}/gi, " "). // replaces multiple spaces with one space 
	replace(/\n +/, "\n"); // Removes spaces after newlines
	return;
}

function validateEmail(email) {
	var eml = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	if (eml.test($.trim(email)) == false) {
		return false;
	}
	return true;
}

function validateMobile(mobile) {
	var mob = /^[1-9]{1}[0-9]{9}$/;
	if (mob.test($.trim(mobile)) == false) {
		return false;
	}
	return true;
}

function validatePAN(pan){
	var regex1 = /^[A-Z]{5}\d{4}[A-Z]{1}$/;
	if (regex1.test($.trim(pan)) == false) {
		return false;
	}else{
		return true
	}
}

function validateGST(gst){
	var regex = /^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}[0-9A-Z]{1}[0-9]{1}$/;
	if (regex.test($.trim(gst)) == false) {
		return false;
	}else{
		return true
	}
} 

$('.datepickerclass').daterangepicker({
	"autoUpdateInput" : true,
	singleDatePicker : true,
	selectMonths : true,
	selectYears : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

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