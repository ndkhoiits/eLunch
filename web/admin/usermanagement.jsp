<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<html>
<head>
    <title>eLunch Admin Page</title>
    <link rel="stylesheet" href="http://bootswatch.com/cerulean/bootstrap.min.css" >
    <link rel="stylesheet" href="http://bootswatch.com/default/bootstrap-responsive.min.css">
    <style>
        body {
            padding-top: 80px;
            padding-bottom: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="container">
    <h2>User Management</h2>

    <div class="row">
        <div class="span4">
            <form class="well" action="usermanagement">
                <input type="hidden" name="action" value="add">
                <div class="control-group">
                    <label class="control-label" for="email">Email Address</label>
                    <div class="controls">
                        <input type="text" name="email" id="email">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="displayName">Display Name</label>
                    <div class="controls">
                        <input type="text" name="displayName" id="displayName">
                    </div>
                </div>
                <div class="form-actions">
                    <button class="btn btn-primary" type="submit">Add</button>
                </div>
            </form>
        </div>
        <div class="span7 well">
            <div id="loading" class="progress progress-striped active">
                <div class="bar" style="width: 100%;"></div>
            </div>
            <table id="result" class="table table-striped table-hover table-condensed" style="display: none;">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Email</th>
                        <th>Display Name</th>
                    </tr>
                </thead>
                <tbody data-bind="foreach: users">
                    <tr>
                        <td data-bind="text: $index() + 1"></td>
                        <td data-bind="text: emailAddress"></td>
                        <td data-bind="text: displayName"></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <script type="text/javascript" src="http://twitter.github.com/bootstrap/assets/js/jquery.js"></script>
    <script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/knockout/2.2.0/knockout-min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            var UserViewModel = function(data) {
                var self = this;
                self.users = ko.observableArray(data);
            };
            var viewModel = new UserViewModel([]);
            ko.applyBindings(viewModel);
            var populateData = function(data) {
                viewModel.users(data);
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
                url: '/admin/usermanagement',
                data: {"action": "getUsers"},
                success: function(response) {
                    populateData(response);
                    showResult();
                }
            });
        });
    </script>
</div>
</body>
</html>