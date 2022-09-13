<!--Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE HTML>
<html lang="zxx">

<head>
	<title>Classic Sign In Form Responsive Widget Template :: w3layouts</title>
	<!-- Meta tag Keywords -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="UTF-8" />
	<meta name="keywords" content="Classic Sign In Form Responsive Widget,Login form widgets, Sign up Web forms , Login signup Responsive web form,Flat Pricing table,Flat Drop downs,Registration Forms,News letter Forms,Elements"
	/>
	<script src="./js/jquery-3.3.1.min.js" type="text/javascript"></script>

	<script>
		addEventListener("load", function () {
			setTimeout(hideURLbar, 0);
		}, false);

		function hideURLbar() {
			window.scrollTo(0, 1);
		}
	</script>

	<!-- Meta tag Keywords -->
	<!-- css files -->
	<link rel="stylesheet" href="./css/style.css" type="text/css" media="all" />
	<!-- Style-CSS -->
	<link rel="stylesheet" href="./css/fontawesome-all.css">
	<!-- Font-Awesome-Icons-CSS -->
	<!-- //css files -->
	<!-- web-fonts -->
	<link href="./fonts.googleapis.com/css?family=Reem+Kufi&amp;subset=arabic" rel="stylesheet">
	<link href="./fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i" rel="stylesheet">
	<!-- //web-fonts -->
</head>

<body>
	<!-- title -->
	<h1>
		<span>C</span>lassic
		<span>S</span>ign
		<span>I</span>n
		<span>F</span>orm</h1>
	<!-- //title -->
	<!-- content -->
	<div class="sub-main-w3">
		<form action="adminLoginServlet" method="post">
			<div class="form-style-agile">
				<label>
					Username
					<i class="fas fa-user"></i>
				</label>
				<input placeholder="Username" name="userName" type="text" required="">
			</div>
			<div class="form-style-agile">
				<label>
					Password
					<i class="fas fa-unlock-alt"></i>
				</label>
				<input placeholder="Password" name="password" type="password" required="">
			</div>
			<!-- checkbox -->
			<div class="wthree-text">
				<ul>
					<li>
						<!-- switch -->
						<div class="checkout-w3l">
							<div class="demo5">
								<div class="switch demo3">
									<input type="checkbox">
									<label>
										<i></i>
									</label>
								</div>
							</div>
							<a href="#">Stay Signed In</a>
						</div>
						<!-- //checkout -->
					</li>
					<li>
						<a href="#">Forgot Password?</a>
					</li>
				</ul>
			</div>
			<!-- //switch -->
			<input type="submit" value="Log In">
			<input id="reset" type="button" style="background-color: #888888;" value="reset">
		</form>
	</div>
	<!-- //content -->

	<!-- copyright -->
	<div class="footer">
		<p>&copy; 2020 Classic Sign In Form. All rights reserved | Design by W3layouts and QiDian </p>
	</div>
	<!-- //copyright -->

</body>
</html>
<script type="text/javascript">
	$("#reset").click(function(){
		$(".form-style-agile").find("input").val("");
	});
</script>