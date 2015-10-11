<%@ page contentType="text/html; ISO-8859-1; charset=windows-1251" %>
<html>
<head>
    <title>Вычислитель арифметических выражений</title>
</head>
<body>
<h1>Вычислитель арифметических выражений</h1>

<FORM method="post">
    Ввод:<INPUT type="text" id="expression" name="expression" value="0" size="40" maxlength="60">&nbsp;&nbsp;&nbsp;&nbsp;
    Результат: <INPUT type="text" id="result" name="result" size="40" value="<%out.println(request.getAttribute("result"));%>">
    <BR>
    <INPUT type="submit" name="submit" value="Вычислить">
    <BR>
    <INPUT type="reset" name="clear" value="Очистить" onreset="clearResult()">
    <BR><BR>
</FORM>

<script type="text/javascript" src="test.js">
</script>

</body>
</html>