<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <label> Ввод:</label><INPUT type="text" id="expression" name="expression" value="0" size="40" maxlength="60"
                                title="Введите выражение">&nbsp;&nbsp;&nbsp;&nbsp;
    <label>Результат: </label><INPUT type="text" id="result" name="result" size="40"
                                     value="<%=request.getAttribute("result") == null ? '0' : request.getAttribute("result")%>"
                                     title="Результат вычисления"><BR>
    <button type="submit" <%--onclick="calculate();" --%> class="btn btn-default btn-lg" onclick="">
        <span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span>
        Вычислить
    </button>
    <button type="submit" class="btn btn-default btn-lg" onclick="clearResult();">
        <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
        Очистить
    </button>
    <br>
    <br>
    <table class="table">
        <caption> История</caption>

        <thead>
        <tr>
            <th>№</th>
            <th>Выражение</th>
            <th>Результат</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="calc" items='<%=request.getAttribute("calcLogs")%>'>
            <tr>
                <td><c:out value="${calc.id}"/></td>
                <td><c:out value="${calc.expression}"/></td>
                <td><c:out value="${calc.result}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</FORM>

</body>
</html>