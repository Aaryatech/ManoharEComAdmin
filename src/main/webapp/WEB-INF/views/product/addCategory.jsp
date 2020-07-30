<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

<jsp:include page="/WEB-INF/views/include/metacssjs.jsp"></jsp:include>


</head>

<body class="sidebar-xs" onload="getImageData()"><!--  -->

	<c:url var="getCatCode" value="/getCatCode"></c:url>
	<c:url var="getAllSubCategoryAjax" value="/getAllSubCategoryAjax"></c:url>

	<c:url var="getImagesByDocIdAndDocType"
		value="/getImagesByDocIdAndDocType"></c:url>

	<c:url var="deleteImageAjax" value="/deleteImageAjax"></c:url>

	<c:url var="updateImageSequenceOrderAjax"
		value="/updateImageSequenceOrderAjax"></c:url>

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
									href="${pageContext.request.contextPath}/showCategories"
									style="color: white;">Category List</a></span>
							</div>
							<br>
								<div id="fail_div"
									class="alert bg-danger text-white alert-styled-left alert-dismissible" style="display: none;">
									<button type="button" class="close" data-dismiss="alert">
										<span>×</span>
									</button>
									<span class="font-weight-semibold"> Failed To Save Category</span>
								</div>
								
								<div id="success_div"
									class="alert bg-success text-white alert-styled-left alert-dismissible" style="display: none;">
									<button type="button" class="close" data-dismiss="alert">
										<span>×</span>
									</button>
									<span class="font-weight-semibold">Category Save Successfully</span>									
								</div>
								
							<div class="card-body">
								
							<form
									action="${pageContext.request.contextPath}/saveCategoryImages"
									id="submitForm" method="post" enctype="multipart/form-data">

									<p class="desc text-danger fontsize11">Note : * Fields are
										mandatory.</p>

							<input type="hidden" value="${catImg.category.catId}" name="cat_id" id="cat_id">
									<div class="form-group row">										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cat_name">Category Name <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text" class="form-control maxlength-badge-position" 
											placeholder="Category"
											 maxlength="30" autocomplete="off" onchange="trim(this)"
											value="${catImg.category.catName}" name="cat_name" id="cat_name">
											<span
												class="validation-invalid-label" id="error_cat_name"
												style="display: none;">This field is required.</span>
												
											<span
												class="validation-invalid-label" id="unq_cat_name"
												style="display: none;">Category Already Exist.</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="address">Category Code<span
											class="text-danger">*</span>:
										</label>
										<div class="col-lg-4">
											<input type="text"class="form-control maxlength-badge-position" 
											placeholder="Enter First Letter Of Category"
											 maxlength="70" autocomplete="off" onchange="trim(this)" readonly="readonly"
											 value="${catImg.category.categoryCode}" name="cat_code" id="cat_code">
											<span
												class="validation-invalid-label" id="error_cat_code"
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
											value="${catImg.category.description}" name="description" id="description">
											<span
												class="validation-invalid-label" id="error_company_name"
												style="display: none;">This field is required.</span>
										</div>
										
										<label class="col-form-label font-weight-bold col-lg-2"
											for="sort_no">Sort No.<span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-4">
											<input type="text"class="form-control maxlength-badge-position" 
											 maxlength="70" autocomplete="off" onchange="trim(this)"
											 value="${catImg.category.seqNo}" name="sort_no" id="sort_no">
											<span
												class="validation-invalid-label" id="error_sort_no"
												style="display: none;">This field is required.</span>
										</div>
									</div>

									<div align="center" id="loader" style="display: none;">

										<span>
											<h4>
												<font color="#343690">Loading</font>
											</h4>
										</span> <span class="l-1"></span> <span class="l-2"></span> <span
											class="l-3"></span> <span class="l-4"></span> <span
											class="l-5"></span> <span class="l-6"></span>
									</div>



									<div class="form-group row" style="display: none;">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Item Description <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<textarea rows="3" cols="3" class="form-control"
												placeholder="Description..."></textarea>
										</div>

									</div>
									
									<!-- <br>
									
									<div class="text-center">
										<button type="button" class="btn btn-danger" id="submtbtn_cat">
											Submit <i class="icon-paperplane ml-2"></i>
										</button>
									</div> -->

									<br>
									
									<c:if test="${isEdit==1}">									
										<div class="form-group" id="cat">

										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Category Image List : </label>


										<div class="row"
											style="text-align: center; margin: auto; width: 90%;"
											id="dispImageDiv">

											<%-- <c:forEach items="${catImg.imgList}" var="imgList" varStatus="count">

												<!-- IMAGE GRID  -->
												<div class="card"
													style="width: 12%; height: 8%; margin: 10px;">
													<div class="card-img-actions mx-1 mt-1">
													
													<input type="text" value="${imgList.imagesId}" class="imgId${imgList.imagesId}" name="imgId${imgList.imagesId}">
														<img class="card-img img-fluid"
															src="${imgPath}${imgList.imageName}"
															alt="">
														<div class="card-img-actions-overlay card-img">
															<a
																href="${imgPath}${imgList.imageName}"
																class="btn btn-outline bg-white text-white border-white border-2 btn-icon rounded-round"
																data-popup="lightbox" rel="group"> <i
																class="icon-plus3"></i>
															</a>
														</div>
													</div>

													<div class="card-body" style="padding: 5px;">
														<div class="d-flex align-items-start flex-nowrap">

															<div style="text-align: center;">

																<input type="number" value="${imgList.seqNo}" id="seq${imgList.imagesId}" name="seq${imgList.imagesId}"
																	style="text-align: left; border: 1px solid #080808; border-radius: 1px; border-top: 1px solid #000000; box-sizing: border-box; width: 60%">
															</div>

															<div class="list-icons list-icons-extended ml-auto"
																style="text-align: center;">

																<a href="#" onclick="deleteImage('${imgList.imagesId}', '${imgList.imageName}')" class="list-icons-item"><i
																	class="icon-bin top-0"></i></a>
															</div>
														</div>
													</div>
												</div>

											</c:forEach>   --%>


										</div>

										<div class="text-center">
											<button type="button" class="btn btn-primary"
												onclick="updateSeqNo()">Update Image Sequence</button>
										</div>
									</div>									
									</c:if>
									
									
									
									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="cust_name">Image Upload <span
											class="text-danger">* </span>:
										</label>
										<div class="col-lg-10">
											<input type="file" class="file-input" multiple="multiple"
												id="files" name="files" data-fouc>
											<!-- <input type="file" id="files" name="files" multiple class="file-input"
												 required /> -->
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
	$("#cat_name").on('input', function(){    
		
		var catName = $("#cat_name").val();
		var catId = $("#cat_id").val();

		$.getJSON('${getCatCode}', {
			catName : catName,
			catId : catId,
			ajax : 'true'
		}, function(data) {			
			 if(!data.error) {	
				 $("#unq_cat_name").hide()
				$("#cat_code").val(data.message);				
			}else{
				$("#unq_cat_name").show()
			}
 
		});
	});
	
	
	$("#submtbtn_cat").click(function(e) {
		
	    e.preventDefault();
	    $.ajax({
	        type: "POST",
	        url: "${pageContext.request.contextPath}/saveCategory",
	        data: { 
	        	cat_id: $("#cat_id").val(),
	        	cat_name: $("#cat_name").val(),
	        	cat_code: $("#cat_code").val(),
	        	description: $("#description").val(),
	        	sort_no: $("#sort_no").val() 
	        },
	        success: function(result) {
	        	$("#success_div").show();
	        	
	            $("#cat_name").val(''),
	        	$("#cat_code").val(''),
	        	$("#description").val(''),
	        	$("#sort_no").val('')
	        	
	        	$("#catId").val(result.catId);
	        	
	        },
	        error: function(result) {
	        	
	        	$("#fail_div").show();
	        }
	    });
	});
	
	
	$(document).ready(function($) {

		$("#submitForm").submit(function(e) {
			var isError = false;
			var errMsg = "";

			if (!$("#cat_name").val()) {
				isError = true;
				$("#error_cat_name").show()
			} else {
				$("#error_cat_name").hide()
			}

			if (!$("#sort_no").val()) {
				isError = true;
				$("#error_sort_no").show()
			} else {
				$("#error_sort_no").hide()
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
		/* $("#files")
				.fileinput(
						{
							theme : 'fas',
							uploadUrl : 'http://192.168.2.3:8080/madhvickadmin/resources/images/',
							//uploadUrl : '${pageContext.request.contextPath}/saveCategoryAndSubCategoryImages', // you must set a valid URL here else you will get an error
							allowedFileExtensions : [ 'jpg', 'png', 'gif' ],
							overwriteInitial : false,
							maxFileSize : 1000,
							maxFilesNum : 10,
							//allowedFileTypes: ['image', 'video', 'flash'],
							slugCallback : function(filename) {
								alert(filename);
								return filename.replace('(', '_').replace(']',
										'_');
							}
						}); */
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

			alert(id);

			$('#loader').show();

			$.getJSON('${deleteImageAjax}', {
				imageId : id,
				imageName : name,
				ajax : 'true'
			}, function(data) {

				$('#loader').hide();

				//alert(JSON.stringify(data));

				if (data.error) {
					alert(data.message);
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

				alert(JSON.stringify(data));

				if (data.error) {
					alert(data.message);
				} else {
					getImageData();
				}

			});

		}
	</script>

</body>
</html>