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
	<c:url value="/getConfiguredItemsList" var="getConfiguredItemsList"></c:url>

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
								<span class="font-size-sm text-uppercase font-weight-semibold card-title">${title}</span>
								<!--  -->
								<span
							class="font-size-sm text-uppercase font-weight-semibold"><a class="card-title"
							href="${pageContext.request.contextPath}/showRelatedProductsList"
							style="color: white;">Related Product Configure List</a></span>
							</div>
							<div class="form-group row"></div>
							<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

							<div class="card-body">
								<p class="desc text-danger fontsize11">Note : * Fields are
									mandatory.</p>
								<form
									action="${pageContext.request.contextPath}/saveRelatedItemConfigure"
									id="submitInsert" method="post">

									<input type="hidden" id="relatedPrdctId" name="relatedPrdctId"
										value="${product.relatedProductId}"> 

									<div class="form-group row">
										<label class="col-form-label font-weight-bold col-lg-2"
											for="tags">Item<span class="text-danger">* </span>:
										</label>
										<div class="col-lg-3">
											<select class="form-control select-search" data-fouc
												name="itemId" id="itemId">
												<option value="0">Select Item</option>
												<c:forEach items="${itemList}" var="itemList"
													varStatus="count">
													<c:choose>
													 <c:when test="${itemList.itemId == relProductId}">
													 	<option selected value="${itemList.itemId}">${itemList.itemName}</option>
													 </c:when>
													 <c:otherwise>
													 	<option value="${itemList.itemId}">${itemList.itemName}</option>
													 </c:otherwise>
													</c:choose>													
												</c:forEach>
											</select> <span class="validation-invalid-label text-danger" id="error_tags"
												style="display: none;">This field is required.</span>
										</div>
										<div class="col-lg-2" style="text-align: right;">
											<!-- <button type="button" class="btn btn-danger"
												onclick="getConfigItemList()">
												Search <i class="icon-paperplane ml-2"></i>
											</button> -->
										</div>
									</div>


									<!--Table-->
									<table class="table" id="item_tag_table">
										<thead>
											<tr>
												<th style="padding: .5rem 0.5rem"><input type="checkbox" id="allChk1" name="allChk1"
													onclick="selAllItems()"></th>
												<th style="padding: .5rem 0.5rem" colspan=3>Category
													Wise Items</th>

											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
									<span class="validation-invalid-label" id="error_chks"
										style="display: none;">Select Item Check Box.</span>
									<div class="text-center">
										<br>
										<button type="submit" class="btn btn-primary" id="submtbtn">
											Submit <i class="icon-paperplane ml-2"></i>
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
	function selAllItems() {
		
		var checkBox = document.getElementById("allChk1");
		//alert(checkBox.checked);

		if (checkBox.checked == true) {

			$(".chkcls")
					.each(
							function(counter) {

								document.getElementsByClassName("chkcls")[counter].checked = true;

							});

		} else {

			$(".chkcls")
					.each(
							function(counter) {

								if (document
										.getElementsByClassName("chkVal")[counter].value == 0) {
									document.getElementsByClassName("chkcls")[counter].checked = false;
									//document.getElementsByClassName("chkVal" + id)[counter].checked = false;

								}
							});

		}

	}
	
		$("#itemId").on('change', function(){   
			var itemId = $('#itemId').val();
			if (itemId > 0) {
				$("#error_tags").hide()

				$('#item_tag_table td').remove();

				$
						.getJSON(
								'${getConfiguredItemsList}',
								{
									itemId : itemId,
									ajax : 'true'

								},
								function(data) {

										//alert(JSON.stringify(data.itemsList));
										
									for (i = 0; i < data.catList.length; i++) {

										var tr = $('<tr style="background:#cbff92;"></tr>');
										
										tr
										.append($(
												'<td style="padding: 12px; line-height:0; border-top: 1px solid #ddd;""></td>')
												.html(""));
 
										tr
												.append($(
														'<td  style="padding: 12px; line-height:0; border-top: 1px solid #ddd;" colspan=3></td>')
														.html(
																data.catList[i].catName));

										$('#item_tag_table tbody').append(tr);

										for (j = 0; j < data.itemsList.length; j++) {
											var tr1 = $('<tr></tr>');
											if (data.catList[i].catId == data.itemsList[j].catId) {
												
												if (data.itemsList[j].checked) {
													tr1
															.append($(
																	'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;""></td>')
																	.html(
																			'<input type="checkbox" checked name="chk" id="chk" value="'+
															data.itemsList[j].itemId
															+'" class="chkcls">'));
												} else {
													tr1
															.append($(
																	'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;""></td>')
																	.html(
																			'<input type="checkbox" name="chk" id="chk" value="'+
														data.itemsList[j].itemId
														+'" class="chkcls">'));
												}
												
												
												tr1
														.append($(
																'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd;""></td>')
																.html(
																		data.itemsList[j].itemName));

												tr1
														.append($(
																'<td style="padding: 7px; line-height:0; border-top: 1px solid #ddd; display: none;"></td>')
																.html(
																		'<input type="text" value="'+data.itemsList[j].checked+'" class="chkVal">'));

												$('#item_tag_table tbody')
														.append(tr1);
											}
										}
									}

								});
			} else {
				$("#error_tags").show()
			}
		});
		
	</script>
	<script type="text/javascript">
		$(document).ready(function($) {

			$("#submitInsert").submit(function(e) {
				var isError = false;
				var errMsg = "";

				if ($("#itemId").val() == 0) {
					isError = true;
					$("#error_tags").show()
				} else {
					$("#error_tags").hide()
				}

				/* if($(".chk").is(":checked")==false){
					isError = true;
					$("#error_chks").show()
				} else {
					$("#error_chks").hide()
				} */

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