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
<c:url value="/checkUniqueDesignation" var="checkUniqueDesignation"></c:url>
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
									href="${pageContext.request.contextPath}/showDesignations"
									style="color: white;" class="card-title">Designation List</a></span>
							</div>


							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/insertNewDesignation"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control" name="desig_id" id="desig_id" value="${designation.designationId}">

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="designation">Designation<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="designation"
												id="designation" maxlength="20" autocomplete="off" onchange="trim(this)" value="${designation.designation}"> 
										<span
												class="validation-invalid-label" id="error_designation"
												style="display: none;">This field is required.</span>	
										<span
												class="validation-invalid-label" id="unq_designation"
												style="display: none;">designation Already Exist.</span>
										</div>
										
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Status <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" checked value="0"
													name="status" id="status_y"
													${designation.isActive==0 ? 'checked' : ''}> Active
												</label>
											</div>

											<div class="form-check form-check-inline">
												<label class="form-check-label"> <input type="radio"
													class="form-check-input" value="1" name="status"
													id="status_n"
													${designation.isActive==1 ? 'checked' : ''}> In-Active
												</label>
											</div>
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
						</div>
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
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/views/include/cmn.js"></script> --%>
	<script
		src="${pageContext.request.contextPath}/resources/assets/js/scrolltable.js"></script>
	<!-- /page content -->
	<script type="text/javascript">
		var loadFile = function(event) {
			try {
				var image = document.getElementById('output');
				image.src = URL.createObjectURL(event.target.files[0]);
			} catch (err) {
				console.log(err);
			}
		};
	</script>

	<script type="text/javascript">
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#designation").val()) {
					isError = true;
					$("#error_designation").show()
				} else {
					$("#error_designation").hide()
				}

				/* if (!$("#language_code").val()) {
					isError = true;
					$("#error_language_code").show()
				} else {
					$("#error_language_code").hide()
				}	 */			
				
				

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
		
		$("#designation").change(function(){   // 1st
			var designation = $("#designation").val();								
			var desigId = $("#desig_id").val();	
		//	alert(userType)
		
			 $.getJSON('${checkUniqueDesignation}', {
				 designation : designation,
				 desigId : desigId,
				ajax : 'true',
			}, function(data) {
				
				if(data.error==false){								
					$("#unq_designation").show();
					$("#designation").val('');
					document.getElementById("designation").focus();
				}else{
					$("#unq_designation").hide();
				}
			});
		});
		
		$("#data_view_button")
		.click(
				function() {
					//blockThis();
					
					$.getJSON('${getSingleCategory}', {
						ajax : 'true',
					}, function(data) {
						//document.getElementById("g_name").innerHTML=" Category Name:" +data.catName;
						//document.getElementById("ifsc_code").innerHTML=" Bank Name: Bank of India";
						document.getElementById("mod_title").innerHTML="Appending Ajax Return Data ";
						document.getElementById("f_name").value=data.catName;
						document.getElementById("l_name").value="Thakur";	
						//unBlock();
					});
				});
	</script>
<script>
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