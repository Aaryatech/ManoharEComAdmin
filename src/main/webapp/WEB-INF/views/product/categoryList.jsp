<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>

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
									href="${pageContext.request.contextPath}/addCategory"
									style="color: white;" class="card-title">Add Category</a></span>
							</div>

							<div class="card-body">


								<table class="table datatable-header-basic">
						<thead>
							<tr>
								<th width="5%">SR. No.</th>
								<th>Category Code</th>
								<th>Category</th>									
								<th style="display: none;"></th>
								<th style="display: none;"></th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${catList}" var="catList" varStatus="count">
								 <tr>
									<td>${count.index+1}</td>
									<td>${catList.categoryCode}</td>
									<td>${catList.catName}</td>
									<td style="display: none;"></td>
									<td style="display: none;"></td>							
									<td class="text-center">
										<div class="list-icons">
											<a href="${pageContext.request.contextPath}/editCategory?catId=${catList.exVar1}"
												class="list-icons-item" title="Edit"> <i
												class="icon-database-edit2"></i>
											</a>
										</div>
										<div class="list-icons">
										<a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${catList.exVar1}" data-popup="tooltip"
													title="" data-original-title="Delete"><i
													class="icon-trash"></i></a>
										</div>										
									</td>								
								</tr>
							</c:forEach>					
						</tbody>
					</table>
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
	//Custom bootbox dialog
	$('.bootbox_custom')
			.on(
					'click',
					function() {
						var uuid = $(this).data("uuid") // will return the number 123
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
											location.href = "${pageContext.request.contextPath}/deleteCategory?catId="
													+ uuid;

										}
									}
								});
					});
	
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
		function getImageData() {

			var type = 0;
			var selectId = document.getElementById("selectId").value;

			if (document.getElementById('type_cat1').checked) {
				type = document.getElementById("type_cat1").value;
			} else {
				type = document.getElementById("type_cat2").value;
			}

			if (selectId > 0) {

				$('#loader').show();

				$
						.getJSON(
								'${getImagesByDocIdAndDocType}',
								{
									type : type,
									selectId : selectId,
									ajax : 'true'

								},
								function(data) {

									//alert(JSON.stringify(data));

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
																+ "<div class='d-flex align-items-start flex-nowrap'><div style='text-align: center;'><input style='display:none;' type=text value='"+image.imagesId+"' class=imgId> "
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
					alert(data.message);
				} else {
					getImageData();
				}

			});

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