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
<c:url value="/chkUnqTaxTitle" var="chkUnqTaxTitle"></c:url>
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
									href="${pageContext.request.contextPath}/showTaxList"
									style="color: white;" class="card-title">Tax List</a></span>
							</div>


							<div class="card-body">

								<form
									action="${pageContext.request.contextPath}/insertNewTax"
									id="submitInsert" method="post">


									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>
									<input type="hidden" class="form-control"
										value="${tax.taxInfoId}" name="taxId"
										id="taxId">
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="taxTitle">Tax Title <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position"
												value="${tax.taxTitle}" name="taxTitle" maxlength="30"
												id="taxTitle" autocomplete="off" onchange="trim(this)"> <span class="validation-invalid-label text-danger"
												id="error_taxTitle" style="display: none;">This
												field is required.</span>
												
												 <span class="validation-invalid-label text-danger"
												id="unq_taxTitle" style="display: none;">Tax
												Already Exist.</span>
										</div>

										<label class="col-form-label font-weight-bold col-lg-2"
											for="hsn">HSN Code <span class="text-danger">*
										</span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position"
												value="${tax.hsn}" name="hsn" maxlength="6"
												id="hsn" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label text-danger"
												id="error_hsn" style="display: none;">This
												field is required.</span>
										</div>
									</div>

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-1"
											for="description">SGST % <span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-3">
											<input type="text" class="form-control maxlength-badge-position"
												 name="sgstPer" value="${tax.sgstPer}"
												id="sgstPer" maxlength="6" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label text-danger"
												id="error_sgstPer" style="display: none;">This
												field is required.</span>
										</div>
										
										
										<label class="col-form-label font-weight-bold col-lg-1"
											for="seqNo">CGST % <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-3">
											<input type="text" class="form-control maxlength-badge-position"
												 name="cgstPer" value="${tax.cgstPer}"
												id="cgstPer" maxlength="6" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label text-danger"
												id="error_cgstPer" style="display: none;">This
												field is required.</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-1"
											for="igstPer">IGST% <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-3">
											<input type="text" class="form-control maxlength-badge-position"
												 name="igstPer" value="${tax.igstPer}"
												id="igstPer" maxlength="6" autocomplete="off" onchange="trim(this)"> <span
												class="validation-invalid-label text-danger"
												id="error_igstPer" style="display: none;">This
												field is required.</span>
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
		
		$("#taxTitle").on('input', function(){    
			
			var taxTitle = $("#taxTitle").val();
			var taxId = $("#taxId").val();

			$.getJSON('${chkUnqTaxTitle}', {
				taxTitle : taxTitle,
				taxId : taxId,
				ajax : 'true'
			}, function(data) {			
				 if(!data.error) {	
					 $("#unq_taxTitle").hide()			
				}else{
					$("#unq_taxTitle").show()
					$("#taxTitle").val('')
				}
	 
			});
		});
	</script>

	<script type="text/javascript">
	 	 $(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if (!$("#taxTitle").val()) {
					isError = true;
					$("#error_taxTitle").show()
				} else {
					$("#error_taxTitle").hide()
				}

				if (!$("#hsn").val()) {
					isError = true;
					$("#error_hsn").show()
				} else {
					$("#error_hsn").hide()
				}

				if (!$("#cgstPer").val()) {
					isError = true;
					$("#error_cgstPer").show()
				} else {
					$("#error_cgstPer").hide()
				}
				
				if (!$("#sgstPer").val()) {
					isError = true;
					$("#error_sgstPer").show()
				} else {
					$("#error_sgstPer").hide()
				}
				
				if (!$("#igstPer").val()) {
					isError = true;
					$("#error_igstPer").show()
				} else {
					$("#error_igstPer").hide()
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
<script>
$('#sgstPer').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	});
	
$('#cgstPer').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	});
	
$('#igstPer').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
	});

$('#hsn').on('input', function() {
	 this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
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