<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>

    <title>Dream Job</title>
</head>
<script>
    function getCities() {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/dreamjob/reg?reg=false',
            data: 'get=cities',
        }).done(function (data) {
            var cities = data.split("#");
            for (var i = 0; i < cities.length - 1; i++) {
                var city = cities[i];
                var option = new Option(city, city);
                select.append(option);
            }
        }).fail(function () {
            alert("Something went wrong");
        });
    }
</script>

<body onload="getCities()">
<script>
    function validate() {
        var name = document.getElementById("name");
        var email = document.getElementById("email");
        var password = document.getElementById("password");

        if(!name.value) {
            name.style.border = "2px solid red";
            $('form').submit(function() {
                return false;
            });
        }
        if(!email.value) {
            email.style.border = "2px solid red";
            name.style.border = "2px solid red";
            $('form').submit(function() {
                return false;
            });
        }
        if(!password.value) {
            password.style.border = "2px solid red";
            name.style.border = "2px solid red";
            $('form').submit(function() {
                return false;
            });
        }
    }
</script>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Registration
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/reg?get=registratio" method="post"  >
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" name="name" id="name">
                        <label>Email</label>
                        <input type="text" class="form-control" name="email" id="email">
                        <label>Password</label>
                        <input type="text" class="form-control" name="password" id="password">
                        <label>City</label>
                        <select id="select" class="form-control" name="city"> </select>
                        <p class="error-msg"></p>
                    </div>
                    <div class="form-group">
                    </div>
                    <button type="submit" onclick="validate()" class="btn btn-primary">Sign Up</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>