<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<link href="http://bootswatch.com/cerulean/bootstrap.min.css" type="text/css" rel="stylesheet">
<style type="text/css">
	body {
		padding-top: 60px;
		/* 60px to make the container go all the way to the bottom of the topbar */
	}
</style>
<title>eLunch</title>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="brand">eLunch</a>
			</div>
		</div>
	</div>
	<div class="container">
		<h1>Let's Go</h1>
        <div class="row-fluid">
            <div class="span6">
                <form action="order" class="well form-horizontal">
                    <legend>Welcome back, <c:out value="${requestScope.displayName}"></c:out></legend>
                    <div class="control-group">
                        <label for="set">Select Set</label>
                        <select name="set" id="set">
                            <option value="A">A</option>
                            <option value="B">B</option>
                            <option value="C">C</option>
                            <option value="D">D</option>
                        </select>
                    </div>
                    <div class="control-group">
                        <label for="note">Note</label>
                        <textarea id="note" rows="4" cols="10" name="note"></textarea>
                    </div>
                    <div class="control-group">
                        <button type="submit" class="btn btn-large btn-primary">Submit</button>
                    </div>


                </form>


            </div>
            <div class="span6">
                <div class="well">
                    <legend>Menu for today</legend>
                </div>
            </div>
        </div>


		<div class="listOrder span6">
			<ul>
			<%
				List<Entity> orders = (List<Entity>) request.getAttribute("orders");
				if (orders == null || orders.size() == 0) {
					%>
						<b>There is no set today.</b>
					<%
				} else {
					for (Entity e : orders) {
                        pageContext.setAttribute("user", e.getProperty("user"));
                        pageContext.setAttribute("set", e.getProperty("set"));
                        pageContext.setAttribute("note", e.getProperty("note"));
						%>
						<li>
							<b>${fn:escapeXml(user)}</b>
							Set <span>${fn:escapeXml(set)}</span><c:if test = "${note != ''}"> <span>(${fn:escapeXml(note)})</c:if></span>
						</li>
						<%	
					}
				}
			%>
			</ul>
		</div>
	</div>
	<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>