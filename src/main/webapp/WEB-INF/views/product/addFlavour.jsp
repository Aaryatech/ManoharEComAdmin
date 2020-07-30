<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs"><!--  -->

	<c:url var="getFlavourCode" value="/getFlavourCode"></c:url>
	

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
								 <span
									class="font-size-sm text-uppercase font-weight-semibold"><a
									href="${pageContext.request.contextPath}/showFlavourList"
									style="color: white;">Flavours List</a></span>
							</div>
							<br>
								
							<div class="card-body">
								
							<form
									action="${pageContext.request.contextPath}/addNewFlavour"
									id="submitForm" method="post">

									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>

								<input type="hidden" value="${flavour.flavourId}" name="flavour_id" id="flavour_id">
									<div class="form-group row">										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="flavour_name">Flavour Name <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											placeholder="Flavour"
											 maxlength="30" autocomplete="off" onchange="trim(this)"
											value="${flavour.flavourName}" name="flavour_name" id="flavour_name">
											<span
												class="validation-invalid-label" id="error_flavour_name"
												style="display: none;">This field is required.</span>
												
											<span
												class="validation-invalid-label" id="unq_flavour_name"
												style="display: none;">Flavour Already Exist.</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="address">Flavour Code<span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"class="form-control maxlength-badge-position" 
											placeholder="Enter First Letter Of Flavour"
											 maxlength="70" autocomplete="off" onchange="trim(this)" readonly="readonly"
											 value="${flavour.flavourCode}" name="flavour_code" id="flavour_code">
											<span
												class="validation-invalid-label" id="error_flavour_code"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="rate">Rate<span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text"class="form-control maxlength-badge-position" 
											 maxlength="70" autocomplete="off" onchange="trim(this)"
											 value="${flavour.rate}" name="rate" id="rate">
											<span
												class="validation-invalid-label" id="error_rate"
												style="display: none;">This field is required.</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="seq_no">Sort No.<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"class="form-control maxlength-badge-position" 
											 maxlength="70" autocomplete="off" onchange="trim(this)"
											 value="${flavour.seqNo}" name="seq_no" id="seq_no">
											<span
												class="validation-invalid-label" id="error_seq_no"
												style="display: none;">This field is required.</span>
										</div>
									</div>
									
									<div class="form-group row">										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="description">Description <span
											class="text-danger"></span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											 maxlength="80" autocomplete="off" onchange="trim(this)"
											value="${flavour.description}" name="description" id="description">
											<span
												class="validation-invalid-label" id="error_company_name"
												style="display: none;">This field is required.</span>
										</div>										
									</div>									
									<br>
									<div class="text-center" >
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
	<!-- /page content -->


	<script type="text/javascript">
	$("#flavour_name").on('input', function(){    
		
		var flavourName = $("#flavour_name").val();		
		var flavourId = $("#flavour_id").val();
		
		//alert(flavourName+" "+flavourId)
		
		$.getJSON('${getFlavourCode}', {
			flavourName : flavourName,
			flavourId : flavourId,
			ajax : 'true'
		}, function(data) {			
			 if(!data.error) {	
				 $("#unq_flavour_name").hide()
				$("#flavour_code").val(data.message);				
			}else{
				$("#unq_flavour_name").show()
				$("#flavour_code").val('');		
			}
 
		});
	});
	
	
	 $(document).ready(function($) {

		$("#submitForm").submit(function(e) {
			var isError = false;
			var errMsg = "";

			if (!$("#flavour_name").val()) {
				isError = true;
				$("#error_flavour_name").show()
			} else {
				$("#error_flavour_name").hide()
			}

			
			if (!$("#rate").val()) {
				isError = true;
				$("#error_rate").show()
			} else {
				$("#error_rate").hide()
			}	
			
			if (!$("#seq_no").val()) {
				isError = true;
				$("#error_seq_no").show()
			} else {
				$("#error_seq_no").hide()
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
	
		function setDropdownData(id) {

			if (id == 1) {

				$.getJSON('${getAllCategoryAjax}', {
					ajax : 'true'
				}, function(data) {

					var len = data.length;

					$('#selectId').find('option').remove().end()

					$("#selectId").append(
							$("<option selected></option>").attr("value", 0)
									.text("Select Option"));

					for (var i = 0; i < len; i++) {

						$("#selectId").append(
								$("<option></option>").attr("value",
										data[i].catId).text(data[i].catName));
					}

					$("#selectId").trigger("chosen:updated");
				});

			} else if (id == 2) {

				$.getJSON('${getAllSubCategoryAjax}', {
					ajax : 'true'
				}, function(data) {

					var len = data.length;

					$('#selectId').find('option').remove().end()

					$("#selectId").append(
							$("<option selected></option>").attr("value", 0)
									.text("Select Option"));

					for (var i = 0; i < len; i++) {

						$("#selectId").append(
								$("<option></option>").attr("value",
										data[i].subCatId).text(
										data[i].subCatName));
					}

					$("#selectId").trigger("chosen:updated");
				});

			}

		}
	</script>
	
	<script type="text/javascript">
		function deleteImage(id, name) {

			//alert(id);

			$('#loader').show();

			$.getJSON('${deleteImageAjax}', {
				imageId : id,
				imageName : name,
				ajax : 'true'
			}, function(data) {

				$('#loader').hide();

				//alert(JSON.stringify(data));

				if (data.error) {
					//alert(data.message);
				} else {
					getImageData();
				}

			});

		}
	</script>


	 <script type="text/javascript">
		function getImageData() {
		//alert(1)
			var type = 0;
			var selectId = document.getElementById("cat_id").value;

			if (selectId > 0) {

				$('#loader').show();

				$
						.getJSON(
								'${getImagesByDocIdAndDocType}',
								{
									type : 1,
									selectId : selectId,
									ajax : 'true'

								},
								function(data) {

								//	alert(JSON.stringify(data));

									$('#loader').hide();

									if (data == "") {
										document.getElementById("dispImageDiv").innerHTML = "";
										document.getElementById("cat").style.display = "none";
									} else {
										document.getElementById("cat").style.display = "block";
									}

									var img = "";

									$
											.each(
													data,
													function(key, image) {

														var divCard = "<div class=card style='width: 12%; height: 8%; margin: 10px;'>";

														var divCardImg = "<div class='card-img-actions mx-1 mt-1'>"
																+ "<img class='card-img img-fluid'"+
						"src='${imageUrl}"+image.imageName+"' alt=''>"
																+ "<div class='card-img-actions-overlay card-img'><a target='_blank' href='${imageUrl}"+image.imageName+
						"' class='btn btn-outline bg-white text-white border-white border-2 btn-icon rounded-round'"+
						"data-popup='lightbox' rel='group'><i class=icon-plus3></i></a></div></div>";

														var divCardImg2 = "<div class=card-body style='padding: 5px;'>"
																+ "<div class='d-flex align-items-start flex-nowrap'><div style='text-align: center;'><input type='hidden' value='"+image.imagesId+"' class=imgId> "
																+ "<input type=number id='seq"
																+ image.itemId
																+ "' name='seq"
																+ image.itemId
																+ "' class=seq value='"
																+ image.seqNo
																+ "' "
																+ "style='text-align: left; border: 1px solid #080808; border-radius: 1px; border-top: 1px solid #000000; box-sizing: border-box; width: 60%'>"
																+ "</div><div class='list-icons list-icons-extended ml-auto' style='text-align: center;'>"
																+ "<a href=# onclick=deleteImage('"
																+ image.imagesId
																+ "','"
																+ image.imageName
																+ "') class='list-icons-item'><i class='icon-bin top-0'></i></a></div></div></div></div>";

														img = img + divCard
																+ ""
																+ divCardImg
																+ ""
																+ divCardImg2;
													});

									document.getElementById("dispImageDiv").innerHTML = img;

								});

			}
			
		}
	</script>


	


	<script type="text/javascript">
		function updateSeqNo() {
			$('#loader').show();

			var imgList = [];

			$(".imgId")
					.each(
							function(counter) {

								var id = document
										.getElementsByClassName("imgId")[counter].value;
								var seq = document
										.getElementsByClassName("seq")[counter].value;

								var img = {
									id : id,
									seq : seq
								}

								imgList.push(img);

							});

			alert(JSON.stringify(imgList));

			$.getJSON('${updateImageSequenceOrderAjax}', {
				json : JSON.stringify(imgList),
				ajax : 'true'
			}, function(data) {

				$('#loader').hide();

				//alert(JSON.stringify(data));

				if (data.error) {
					//alert(data.message);
				} else {
					getImageData();
				}

			});

		}
	</script>

</body>
</html>