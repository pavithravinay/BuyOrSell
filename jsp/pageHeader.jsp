<%@page import="service.MessageService"%>
<%@page import="bean.User"%>
<%@page import="dbutil.CacheHandler"%>
<%@page import="javax.sql.rowset.CachedRowSet"%>
<body>
	<header id="header">
		<!--header-->
		<div class="header-middle">
			<!--header-middle-->
			<div class="container">
				<div class="row">
					<div class="col-sm-4">
						<div class="logo pull-left">
							<h4>BuyOrSell.com</h4>
						</div>
					</div>
					<div class="col-sm-8">
						<div class="shop-menu pull-right">
							<ul class="nav navbar-nav">
								<% 
								if (session.getAttribute("user") != null) { 
									User user = (User) session.getAttribute("user");
									int count = (new MessageService()).getUnreadMessagesCount(user.getId());
									String messageCount = count > 0 ? " <b>(" + count + ")</b>" : " ";
								%>
								<li style="margin-top: 10px; color:#48D1CC;"><b>Hello, <%=user.getFirstName() %></b></li>
								<li><a href="wishlist"><i class="fa fa-star"></i> Wishlist</a></li>
								<li><a href="my-advertisements"><i class="fa fa-user"></i> My Advertisements</a></li>
								<li><a href="messages"><i class="fa fa-envelope"></i> Messages<%=messageCount %></a></li>
								<li><a href="profile"><i class="fa fa-user"></i> Account Settings</a></li>
								<li><a href="LoginServlet?action=logout"><i class="fa fa-lock"></i> Logout</a></li>
								<% } else { %>
								<li><a href="login"><i class="fa fa-user"></i> Sign Up</a></li>
								<li><a href="login"><i class="fa fa-lock"></i> Login</a></li>
								<% } %>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--/header-middle-->

		<div class="header-bottom">
			<!--header-bottom-->
			<div class="container">
				<div class="row">
					<div class="col-sm-9">
						<!-- <div class="navbar-header">
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target=".navbar-collapse">
								<span class="sr-only">Toggle navigation</span> <span
									class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
						</div> -->
						<div class="mainmenu pull-left">
							<ul class="nav navbar-nav collapse navbar-collapse">
								<li><a href="home" class="active">Home</a></li>
								<li class="dropdown"><a href="advertisements">Browse<i
										class="fa fa-angle-down"></i></a>
									<ul role="menu" class="sub-menu">
									<% for(Integer id: CacheHandler.getArrangedCategories().keySet()) { %>
										<li><a href="advertisements?categoryId=<%=id %>"><%=CacheHandler.getCategory(id).getName() %></a></li>
									<% } %>
									</ul></li>
								<li><a href="myads">Post Advertisement</a></li>
								<li><a href="trending">See What's Trending</a></li>
								<!-- <li><a href="contact-us">Contact Us</a></li> -->
							</ul>
						</div>
					</div>
					<div class="col-sm-3">
						<div class="search_box pull-right" style="margin-top:-7px;">
							<input type="text" id="search" placeholder="Search" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--/header-bottom-->
	</header>
	<!--/header-->
	<section>
		<div class="container">
			<div class="row" style="margin-bottom:25px;">