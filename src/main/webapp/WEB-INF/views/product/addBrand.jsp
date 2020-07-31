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
<c:url value="/getBrandCode" var="getBrandCode"></c:url>
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
									class="font-size-sm text-uppercase font-weight-semibold card-title">${title}</span>
								<span class="font-size-sm text-uppercase font-weight-semibold"><a
									href="${pageContext.request.contextPath}/showBrandList"
									style="color: white;" class="card-title">Brand List</a></span>
							</div>


							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/insertNewBrand"
									id="submitInsert" method="post" enctype="multipart/form-data">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${brand.brandId}" name="brandId"
										id="brandId">
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="brandName">Brand Name <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control"
												value="${brand.brandName}" name="brandName"
												id="brandName"> <span class="validation-invalid-label text-danger"
												id="error_brandName" style="display: none;">This
												field is required.</span>
												
												 <span class="validation-invalid-label text-danger"
												id="unq_brandName" style="display: none;">Brand
												Already Exist.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="brandCode">Brand Code <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control"
												value="${brand.brandCode}" name="brandCode"
												id="brandCode" readonly="readonly"> <span
												class="validation-invalid-label text-danger"
												id="error_brandCode" style="display: none;">This
												field is required.</span>
										</div>

									</div>


									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="description">Description <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control"
												value="${brand.description}" name="description"
												id="description"> <span
												class="validation-invalid-label text-danger"
												id="error_description" style="display: none;">This
												field is required.</span>
										</div>
										
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="seqNo">Sequence No: <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control"
												value="${brand.seqNo}" name="seqNo"
												id="seqNo"> <span
												class="validation-invalid-label text-danger"
												id="error_seqNo" style="display: none;">This
												field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Image <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<label class="form-check-label"> <img id="output"
												width="150" src="${imageUrl}${brand.brandImg}" />
												<input type="file" class="form-control-uniform" data-fouc
												onchange="loadFile(event)" name="doc" id="doc"> <input
												type="hidden" class="form-control-uniform" name="editImg"
												id="editImg" value="${brand.brandImg}">
												<span class="validation-invalid-label" id="error_doc"
												style="display: none;">This field is required.</span>
											</label>
										</div>
									</div>
									<br>
									<div class="text-center">
										<button type="submit" class="btn btn-primary">
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
			} catch (err) {
				console.log(err);
			}
		};
		
		$("#brandName").on('input', function(){    
			
			var brandName = $("#brandName").val();
			var brandId = $("#brandId").val();

			$.getJSON('${getBrandCode}', {
				brandName : brandName,
				brandId : brandId,
				ajax : 'true'
			}, function(data) {			
				 if(!data.error) {	
					 $("#unq_brandName").hide()
					$("#brandCode").val(data.message);				
				}else{
					$("#unq_brandName").show()
				}
	 
			});
		});
	</script>

	<script type="text/javascript">
	 	$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#brandName").val()) {
					isError = true;
					$("#error_brandName").show()
				} else {
					$("#error_brandName").hide()
				}

				if (!$("#brandCode").val()) {
					isError = true;
					$("#error_brandCode").show()
				} else {
					$("#error_brandCode").hide()
				}

				if (!$("#seqNo").val()) {
					isError = true;
					$("#error_seqNo").show()
				} else {
					$("#error_seqNo").hide()
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



</body>
</html>