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
				<!-- ColReorder integration -->
				<div class="card">

					<div
						class="card-header bg-danger text-white d-flex justify-content-between">
						<span class="font-size-sm text-uppercase font-weight-semibold card-title" >
						<h6 class="card-title">${title}</h6></span> 
						<!--  -->
						<span
							class="font-size-sm text-uppercase font-weight-semibold"><a class="card-title"
							href="${pageContext.request.contextPath}/addGrievanceInstructn"
							style="color: white;">Add Grievances Instruction</a></span>
					</div>
					
					<div class="form-group row"></div>
				<jsp:include page="/WEB-INF/views/include/response_msg.jsp"></jsp:include>

					<table class="table datatable-header-basic">
						<thead>
							<tr>
								<th width="5%">SR. No.</th>
								<th>Grievance Caption</th>
								<th>Grievance Type</th>
								<th>Status</th>		
								<th style="display: none;"></th>
								<th style="display: none;"></th>	
									
								<th class="text-center">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${grievList}" var="grievList" varStatus="count">
								 <tr>
									<td>${count.index+1}</td>
									<td>${grievList.caption}</td>
									<td>${grievList.exVar3}</td>
									<c:set value="" var="status"/>
									<c:if test="${grievList.isActive==0}">
										<c:set value="Active" var="status"/>
									</c:if>
									<c:if test="${grievList.isActive==1}">
										<c:set value="In-Active" var="status"/>
									</c:if>
									<td>${status}</td>	
									
									<td style="display: none;"></td>
									<td style="display: none;"></td>	
																	
															
									<td class="text-center">
										<div class="list-icons">
											<a href="${pageContext.request.contextPath}/editGrievance?grievId=${grievList.exVar1}"
												class="list-icons-item" title="Edit"> <i
												class="icon-database-edit2"></i>
											</a>
										</div>
										<div class="list-icons">
										<a href="javascript:void(0)"
													class="list-icons-item text-danger-600 bootbox_custom"
													data-uuid="${grievList.exVar1}" data-popup="tooltip"
													title="" data-original-title="Delete"><i
													class="icon-trash"></i></a>
										</div>										
									</td>								
								</tr>
							</c:forEach>					
						</tbody>
					</table>
				</div>
				<!-- /colReorder integration -->

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
										location.href = "${pageContext.request.contextPath}/deleteGrievance?grievId="
												+ uuid;

									}
								}
							});
				});
</script>
</body>
</html>