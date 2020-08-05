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
							href="${pageContext.request.contextPath}/showCompanies"
							class="nav-link">Companies List</a></li>
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showDesignations"
							class="nav-link">Designations List</a></li>
					
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showUserTypes"
							class="nav-link">Users Type List</a></li>
					
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/showUsers"
						class="nav-link">Users List</a></li>
							
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/showLanguages"
						class="nav-link">Languages List</a></li>
							
					<li class="nav-item"><a
						href="${pageContext.request.contextPath}/showCities"
						class="nav-link">City List</a></li>
							
					</ul></li>

					
					<li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Product</span></a>
					<ul class="nav nav-group-sub" data-submenu-title="Product">
					
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showCategories"
							class="nav-link">Category List</a></li>
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showSubCategories"
							class="nav-link">Sub-Category List</a></li>		
						
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showTagList"
							class="nav-link">Tag List</a></li>
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showFlavourList"
							class="nav-link">Flavour List</a></li>							
					
					
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showBrandList"
							class="nav-link">Brand List</a></li>
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showTaxList"
							class="nav-link">Tax List</a></li>
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showProductStatusList"
							class="nav-link">Product Status List</a></li>
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showItemList"
							class="nav-link">Item Show</a></li>					
					
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/addItemSKUConfig/0"
							class="nav-link">Add Item SKU Configuration</a></li>
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showItemList"
							class="nav-link">Items List</a></li>		
														
					</ul></li>
					
					
				<li class="nav-item nav-item-submenu"><a href="#"
					class="nav-link"><i class="icon-list-unordered"></i><span>Basic Masters</span></a>
					<ul class="nav nav-group-sub" data-submenu-title="Basic Masters">
					
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showDeliveryInstructn"
							class="nav-link">Delivery Instruction List
						</a></li>
						
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showGrievences"
							class="nav-link">Grievance List</a></li>
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showGrievencesTypeIntructn"
							class="nav-link">Grievance Type Instruction List</a></li>
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showRelatedProductsList"
							class="nav-link">Related Product Configuration</a></li>
							
					<li class="nav-item"><a
							href="${pageContext.request.contextPath}/showOfferList"
							class="nav-link">Offer List</a></li>

					</ul></li> 
					
					



			</ul>
		</div>
		<!-- /main navigation -->

	</div>
	<!-- /sidebar content -->

</div>

