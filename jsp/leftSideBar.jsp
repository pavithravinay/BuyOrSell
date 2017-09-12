<%@page import="dbutil.CacheHandler"%>
<%@page import="bean.Category"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<div class="col-sm-3">
	<div class="left-sidebar">
		<h2>Category</h2>
		<div class="panel-group category-products" id="accordian">
			<!--category-productsr-->
			<%
			HashMap<Integer, List<Category>> displayCategories = CacheHandler.getArrangedCategories();
			for(Map.Entry<Integer, List<Category>> parentCategory: displayCategories.entrySet()) {
				Category key = CacheHandler.getCategory(parentCategory.getKey());
			%>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordian"
							href="#<%=key.getName().toLowerCase() %>"> <span class="badge pull-right"><i
								class="fa fa-plus"></i></span> <%=key.getName() %>
						</a>
					</h4>
				</div>
				<% 
				List<Category> childCategories = parentCategory.getValue();
				if (childCategories != null && childCategories.size() > 0) {
				%>
				<div id="<%=key.getName().toLowerCase() %>" class="panel-collapse collapse">
					<div class="panel-body">
						<ul>
							<% for(Category c: childCategories) { %>
							<li><a href="advertisements?categoryId=<%=c.getId()%>"><%=c.getName() %></a></li>
							<% } %>
						</ul>
					</div>
				</div>
				<% } %>
			</div>			
			<% } %>
		</div>
		<!--/category-products-->

		<!-- <div class="price-range">
			price-range
			<h2>Price Range</h2>
			<div class="well text-center">
				<input type="text" class="span2" value="" data-slider-min="0"
					data-slider-max="600" data-slider-step="5"
					data-slider-value="[250,450]" id="sl2"><br /> <b
					class="pull-left">$ 0</b> <b class="pull-right">$ 600</b>
			</div>
		</div> -->
		<!--/price-range-->
	</div>
</div>