<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<html>
<head>
    <title>eLunch Admin Page</title>
    <link rel="stylesheet" href="http://bootswatch.com/cerulean/bootstrap.min.css">
    <link rel="stylesheet" href="http://bootswatch.com/default/bootstrap-responsive.min.css">
    <link rel="stylesheet" href="http://www.eyecon.ro/bootstrap-datepicker/css/datepicker.css">
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
    <h2>Menu Management</h2>

    <div class="row">
        <div class="span6">
            <form class="well1" action="menumanagement">
                <input type="hidden" name="action" value="add">
                <div class="control-group">
                    <label class="control-label" for="date">Date</label>
                    <div class="controls">
                        <input type="text" name="date" id="date" class="date-picker" data-date-format="dd/mm/yyyy" >
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="menu">Menu</label>

                    <div class="controls">
                        <textarea class="input-xxlarge" rows="10" cols="145" id="menu" name="menu"></textarea>
                    </div>
                </div>
                <div class="form-actions">
                    <button class="btn btn-primary" type="submit">Submit</button>
                </div>
            </form>
        </div>
        <div class="span6">
            <legend>Menu for today</legend>
            <div id="loading" class="progress progress-striped active">
                <div class="bar" style="width: 100%;"></div>
            </div>
            <div id="result" style="display: none">
                <div class="content"></div>
                <button class="btn" id="sendEmailBtn">Send Email</button>
            </div>
        </div>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://www.eyecon.ro/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            var d = new Date();
            var today_date =
                    ("0" + d.getDate()).slice(-2) + "/" + ("0" + (d.getMonth() + 1)).slice(-2) + "/" + d.getFullYear();
            console.log(today_date);
            $('.date-picker').val(today_date);
            $('.date-picker').datepicker().on('changeDate', function(ev) {
                var selectedDate = getDate(ev.date);
                if (selectedDate != today_date) {
                    alert("It's not today");
                }
            });
            var getDate = function(d) {
                return ("0" + d.getDate()).slice(-2) + "/" + ("0" + (d.getMonth() + 1)).slice(-2) + "/" + d.getFullYear();
            };

            var populateData = function(data) {
                $("#result .content").html(data);
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
                url: '/admin/menumanagement',
                data: {"action": "getmenu"},
                success: function(response) {
                    populateData(response);
                    showResult();
                }
            });

            $("#sendEmailBtn").click(function () {
                if (confirm("Do you want to send the menu to all subscribed emails ?")) {
                    $(this).attr("disabled", "disabled");
                    sendEmail();
                }

            });
            function sendEmail() {
                $.ajax({
                    url: '/admin/email',
                    data: {"action": "send"},
                    success: function(response) {
                        alert(response);
                    }
                });
            }

        });
    </script>
</div>
</body>
</html>
