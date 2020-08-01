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


									<input type="hidden" class="form-control" name="productStat_id"
										id="productStat_id" value="${product.productStatusId}">
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="prdct_stat">Product Status<span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="prdct_stat"
												id="prdct_stat" value="${product.productStatus}" maxlength="30" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label text-danger" id="error_prdct_stat"
												style="display: none;">This field is required.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="description">Description <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" name="description"
												id="description" value="${product.description}" maxlength="80" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label text-danger" id="error_tag_desc"
												style="display: none;">This field is required.</span>
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
		$(document).ready(function($) {

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
		});
		
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