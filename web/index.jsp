<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="java.util.Map" %>

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
                    <div id="loading" class="progress progress-striped active">
                        <div class="bar" style="width: 100%;"></div>
                    </div>
                    <div id="result" style="display: none">
                        <div></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">

            <div class="span12">
                <ul>
                <%
                    Map<String, List<Entity>> orderBySet = (Map<String, List<Entity>>) request.getAttribute("ordersbyset");
                    for (String set : orderBySet.keySet()) {
                        List<Entity> values = orderBySet.get(set);
                        %>
                            <li><%=set%>:(<%=values.size()%> SET)
                        <%
                            for (Entity e : values) {
                                String name = (String) e.getProperty("user");
                        %>
                                <%=name%>,
                    <%
                    }


                        %>

                            </li>
                <%
                    }
                %>
                </ul>
            </div>
        </div>

	</div>
	<script type="text/javascript" src="http://code.jquery.com/jquery.min.js"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            var populateData = function(data) {
                $("#result").html(data);
            }
            var showLoading = function() {
                $("#result").hide();
                $("#loading").show();
            }
            var hideLoading = function() {
                $("#loading").hide();
            }
            var showResult = function() {
                hideLoading();
                $("#result").show();
            }
            $.ajax({
                url: '/menumanagement',
                data: {"action": "getmenu"},
                success: function(response) {
                    populateData(response);
                    showResult();
                }
            });
        });

    </script>
</body>
</html>