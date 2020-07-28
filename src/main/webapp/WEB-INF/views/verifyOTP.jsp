
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

<body>

	<!-- Page content -->
	<div class="page-content">

		<!-- Main content -->
		<div class="content-wrapper">

			<!-- Content area -->
			<div
				class="content d-flex justify-content-center align-items-center login_bg">
				
				
				<div class="loginInner">
					
						<div class="login_l">
						<div class="lgn_logo_cont">
										<img 
											src="${pageContext.request.contextPath}/resources/images/logo.png"
											alt="" width="300">
									</div>
						
						
						
						
						</div>
						
						<div class="login_r forgot">
						
						<%
									if (session.getAttribute("errorMsg") != null) {
								%>
								 <h3 style="text-align: center;"> <% out.println(session.getAttribute("errorMsg")); %></h3>
								<%
										session.removeAttribute("errorMsg");
									}
								%>
					
						<form action="otpVerification" method="post" id="otp_form">
						<h2 class="login_head_one">							
							Verify OTP
						</h2>
						<div class="clr"></div>
						

							<div
										class="form-group form-group-feedback form-group-feedback-left">
										<input type="text" class="form-control  logn_input text-white"
											placeholder="Enter OTP." maxlength="6" id="otp" name="otp">
										<div class="form-control-feedback">
											<i class="icon-mail5  text-white"></i>
										</div>
										<input type="hidden" id="mob" name="mob" value="${contact}">
										
										<span class="error_form" id="error_otp"
								style="display: none; color: #fff;">Enter OTP.</span>
									</div>

										<div class="form-group">
											<button type="submit" class="btn  btn-block" style="background: #9ccd2b">
												<i class="icon-spinner11 mr-2"></i>Submit OTP
											</button>
										</div>
									</form>
						</div>
						
					<div class="clr"></div>
					</div>
				
				<!-- old -->
				
				<%-- <div class="login_bx">
					<!--password-recovery-->
					<div class="card mb-0" style="margin: 20px 0 0 0;">
						<div class="card-body">
							<div class="lgn_cont">
								<div class="lgn_cont_l reset">
									<div class="lgn_logo_cont">
										<img src="${pageContext.request.contextPath}/resources/images/Madhvi_Logo.jpg" alt="">
										
									</div>
								</div>
								<div class="lgn_cont_r">
									<h2 class="lgn-title">
										<i
											class="icon-spinner11 icon-2x blue-bor border-3 rounded-round p-3 mt-1"></i>
										<span>Password Recovery </span>
									</h2>

									<div
										class="form-group form-group-feedback form-group-feedback-right">
										<input type="email" class="form-control"
											placeholder="Your email">
										<div class="form-control-feedback">
											<i class="icon-mail5 text-muted"></i>
										</div>
									</div>


									<div class="form-group">
										<button type="submit" class="btn bg-primary btn-block">
											<i class="icon-spinner11 mr-2"></i> Reset Password
										</button>
									</div>


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
	$(document)
	.ready(
			function($) {

				$("#otp_form")
						.submit(
								function(e) {
									var isError = false;
									var errMsg = "";
									
									if (!$("#otp").val()) {
										isError = true;
										$("#error_otp").show()
									} else {
										$("#error_otp").hide()
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

</body>
</html>
