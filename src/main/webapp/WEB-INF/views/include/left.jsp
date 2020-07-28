<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="sidebar sidebar-light sidebar-main sidebar-expand-md">




	<!-- Sidebar content -->
	<div class="sidebar-content">


		<!-- Main navigation -->
		<div class="card card-sidebar-mobile">
			<ul class="nav nav-sidebar" data-nav-type="accordion">

				<!-- Main -->
				<!-- <li class="nav-item-header"><div class="text-uppercase font-size-xs line-height-xs">Main</div> <i class="icon-menu" title="Main"></i></li> -->
				<li class="nav-item"><a
					href="${pageContext.request.contextPath}/home"
					class="nav-link active"> <i class="icon-home4"></i> <span>
							Home </span>
				</a></li>




				<li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Masters</span></a>
					<ul class="nav nav-group-sub" data-submenu-title="Masters">
					
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showMnUsers"
							class="nav-link">User List</a></li>

					</ul></li>
					
					<%-- <li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Basic Masters</span></a>
					<ul class="nav nav-group-sub" data-submenu-title="Basic Masters">
					
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showMnUsers"
							class="nav-link">User List</a></li>
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showCompanies"
							class="nav-link">Company List</a></li>

						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showLanguage"
							class="nav-link">Language List</a></li>
						
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showCities"
							class="nav-link">City List</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showArea"
							class="nav-link">Area List</a></li>
						
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showDeliveryInstructn"
							class="nav-link">Delivery Instruction List</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showGrievences"
							class="nav-link">Grievance List</a></li>
							
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showGrievencesTypeIntructn"
							class="nav-link">Grievance Type Instruction List</a></li>
							
							

					</ul></li> --%>
					
					



			</ul>
		</div>
		<!-- /main navigation -->

	</div>
	<!-- /sidebar content -->

</div>

