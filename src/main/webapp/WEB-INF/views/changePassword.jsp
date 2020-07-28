
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Madhvi Cloud kitchen</title>

<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/resources/images/favicon.png"
	type="image/x-icon" />

<!-- Global stylesheets -->
<link
	href="https://fonts.googleapis.com/css?family=Roboto:400,300,100,500,700,900"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/login/assets/css/icons/icomoon/styles.min.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/login/assets/css/bootstrap.min.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/login/assets/css/bootstrap_limitless.min.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/login/assets/css/layout.min.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/login/assets/css/components.min.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.request.contextPath}/resources/login/assets/css/colors.min.css"
	rel="stylesheet" type="text/css">

<link
	href="${pageContext.request.contextPath}/resources/login/assets/css/custom.css"
	rel="stylesheet" type="text/css">
<!-- /global stylesheets -->

<!-- Core JS files -->
<script
	src="${pageContext.request.contextPath}/resources/login/assets/js/main/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/login/assets/js/main/bootstrap.bundle.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/login/assets/js/plugins/loaders/blockui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/login/assets/js/plugins/ui/ripple.min.js"></script>
<!-- /core JS files -->

<!-- Theme JS files -->
<script
	src="${pageContext.request.contextPath}/resources/login/assets/js/plugins/forms/styling/uniform.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/login/assets/js/app.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/login/assets/js/demo_pages/login.js"></script>
<!-- /theme JS files -->

<style type="text/css">
	.form-control:not(.border-bottom-1):not(.border-bottom-2):not(.border-bottom-3):focus{
		border-color: transparent !important;
	}
</style>

</head>

<body style=" background: url(resources/login/images/backgrounds/lgn_bg.jpg) no-repeat left top;">

	

	<!-- Page content -->
	<div class="page-content">
	
		<!-- Main content -->
		<div class="content-wrapper">
			<!-- Content area -->
			 <div
				class="content d-flex justify-content-center align-items-center login_bg" >
				
				
				<div class="loginInner">
					
						<div class="login_l">
						<div class="lgn_logo_cont">
										<img 
											src="${pageContext.request.contextPath}/resources/images/logo.png"
											alt="" width="300">
										<p></p>
									</div>
						
						<!-- <p class="login_txt">
						Welcome to India’s one of most preferred bakery brand !
						<span>Lets make Monginis a part of everybody’s celebration!!</span> </p> -->
						
						</div>
						
						<div class="login_r">
						
						<%
									if (session.getAttribute("errorMsg") != null) {
								%>
								 <h3 style="text-align: center;"> <% out.println(session.getAttribute("errorMsg")); %></h3>
								<%
										session.removeAttribute("errorMsg");
									}
								%>
					
						<form  id="form-login" action="changeToNewPassword" method="post">
						<h2 class="login_head_one">							
							Change Password 
						</h2>
						<input type="hidden" id="userId" name="userId" value="${userId}">
						<div class="clr"></div>
						<div
											class="form-group form-group-feedback form-group-feedback-left ">
											<input type="password" class="form-control logn_input text-white" maxlength="10"
												placeholder="Password" id="newPass" name="newPass" onkeyup="checkPassword()">
											<span toggle="#newPass"
												class="icon-eye field-icon text-white toggle-password"></span>
											<div class="form-control-feedback">
												<i class="icon-lock2 text-white"></i>
											</div>											
											 <span class="error_form text-danger" id="error_newPass"
									style="display: none; color: white;">This field is required</span>
										</div>

										<div
											class="form-group form-group-feedback form-group-feedback-left">
											<input id="password-field" type="password" name="confrmPass"
												class="form-control logn_input text-white"  placeholder="Re-Enter Password"
												onkeyup="checkPassword()"> <span toggle="#password-field"
												class="icon-eye field-icon text-white toggle-password"></span>
											<div class="form-control-feedback">
												<i class="icon-lock2 text-white"></i>
											</div>
							<span class="error_form" id="error_match"
								style="display: none; color: #fff;">New password not matched with confirm password</span>
										</div>

									<%-- 	<div class="form-group d-flex align-items-center">
											<div class="mb-0">
												<label class="form-check-label" style="color: #FFF;"> 
												<input type="checkbox" name="remember" class="lgn_chk"  
													> Remember
												</label>
											</div>

											<a href="${pageContext.request.contextPath}/showForgetPass"
												class="ml-auto " style="color: #FFF;">Forgot password?</a>
										</div>
 --%>
										<div class="form-group">
											<button type="submit" class="btn  btn-block" style="background: #9ccd2b" id="pass_btn">
												Sign in <i class="icon-circle-right2 ml-2"></i>
											</button>
										</div>
									</form>
						</div>
						
					<div class="clr"></div>
					</div>
				
				
				
				
				<%-- <div class="login_bx">
					<div class="card mb-0" style="background: white">
						<div class="card-body">
							<div class="lgn_cont">
								<div class="lgn_cont_l" style="background: white;">
									<div class="lgn_logo_cont">
										<img 
											src="${pageContext.request.contextPath}/resources/images/Madhvi_Logo.jpg"
											alt="">
										<p></p>
									</div>
								</div>
								<div class="lgn_cont_r">

									<form action="${pageContext.request.contextPath}/home">

										<h2 class="lgn-title" style="color: #FD2549;">
											<i
												class="icon-reading icon-2x  border-3 rounded-round p-3 mt-1" style="color: #FD2549;"></i>
											<span>Sign into your account </span>
										</h2>

										<div
											class="form-group form-group-feedback form-group-feedback-left">
											<input type="text" class="form-control"
												placeholder="Username">
											<div class="form-control-feedback">
												<i class="icon-user text-muted"></i>
											</div>
										</div>

										<div
											class="form-group form-group-feedback form-group-feedback-left">
											<input id="password-field" type="password"
												class="form-control" name="password" placeholder="Password"
												value=""> <span toggle="#password-field"
												class="icon-eye field-icon text-muted toggle-password"></span>
											<div class="form-control-feedback">
												<i class="icon-lock2 text-muted"></i>
											</div>

										</div>

										<div class="form-group d-flex align-items-center">
											<div class="form-check mb-0">
												<label class="form-check-label" style="color: #FD2549;"> <input
													type="checkbox" name="remember" class="form-input-styled" 
													checked data-fouc> Remember
												</label>
											</div>

											<a href="${pageContext.request.contextPath}/showForgetPass"
												class="ml-auto " style="color: #FD2549;">Forgot password?</a>
										</div>

										<div class="form-group">
											<button type="submit" class="btn  btn-block" style="background: #fffe03">
												Sign in <i class="icon-circle-right2 ml-2"></i>
											</button>
										</div>
									</form>

								</div>
								<div class="clr"></div>
							</div>
						</div>
					</div>



				</div> --%>

				<div class="clr"></div>



				<div class="clr"></div>









			</div>
			<!-- /content area -->

		</div>
		<!-- /main content -->

	</div>
	<!-- /page content -->

	<!--password eye toggle-->
	<script type="text/javascript">
		$(".toggle-password").click(function() {

			$(this).toggleClass("icon-eye-blocked");
			var input = $($(this).attr("toggle"));
			if (input.attr("type") == "password") {
				input.attr("type", "text");
			} else {
				input.attr("type", "password");
			}
		});
	</script>
<script>
function checkPassword(){
	var npass= $("#newPass").val();
	var cpass = $("#password-field").val();
	//alert("---"+npass+" "+cpass)
if (npass != cpass) {   
	
		$("#error_match")
		.show()
		document.getElementById("pass_btn").disabled = true;
		return false;
	}else {
		$("#error_match")
			.hide()
			document.getElementById("pass_btn").disabled = false;
			return true;
			
	}
}
</script>
</body>
</html>
