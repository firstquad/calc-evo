<%@ page contentType="text/html; ISO-8859-1; charset=windows-1251" %>
<script type="text/javascript" src="./test.js"></script>
<script type="text/javascript" src="./lib/jquery-1.8.3.js"></script>
<script type="text/javascript" src="./lib/bootstrap/js/bootstrap.js"></script>
<link href="./lib/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<html>
<head>
    <title>Вычислитель арифметических выражений</title>
</head>
<body>
<h3>Вычислитель арифметических выражений</h3>

<FORM method="post">
    <label> Ввод:</label><INPUT type="text" id="expression" name="expression" value="0" size="40" maxlength="60">&nbsp;&nbsp;&nbsp;&nbsp;
    Результат: <INPUT type="text" id="result" name="result" size="40"
                      value="<%out.println(request.getAttribute("result"));%>"
                      onload="coaleasce(request.getAttribute('result'))"><BR>
    <button type="submit" class="btn btn-default btn-lg">
        <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
        Вычислить
    </button>
    <button type="submit" class="btn btn-default btn-lg" onclick="clearResult();">
        <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
        Очистить
    </button>

    <div class="dropdown">
        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            Dropdown
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li><a href="#">Separated link</a></li>
        </ul>
    </div>

    <div class="dropup clearfix open">
        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            Dropup
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
        </ul>
    </div>
</FORM>

</body>
</html>