<%@ page import="java.util.*, java.text.*" %>
<%@ page import="ru.evo.calc.*" %>
<%@ page import="ru.evo.calc.service.Validation" %>
<%@ page import="ru.evo.calc.service.util.ReverseNoteUtil" %>
<%@ page import="ru.evo.calc.service.Resolve" %>
<%@ page import="ru.evo.calc.dao.model.CalcLog" %>
<%@ page import="ru.evo.calc.dao.api.CalcLogDAO" %>
<%@ page import="ru.evo.calc.dao.impl.CalcLogDAOImpl" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html; ISO-8859-1; charset=windows-1251" %>
<html>
<head>
    <title>1111
    </title>
</head>

<body>

<%!
    CalcLogDAO calcLogDAO = new CalcLogDAOImpl();
%>

<%-- Форма заполнения данных --%>
<h1>Вычислитель арифметических выражений</h1>

<FORM name="form1" method="post">
    <%--Ввод:<INPUT type="text" name="expresion" value = "<%= (String)request.getSession().getAttribute("result") %>" size="40" maxlength="60">&nbsp;&nbsp;&nbsp;&nbsp;--%>
    Ввод:<INPUT type="text" name="expresion" value="0" size="40" maxlength="60">&nbsp;&nbsp;&nbsp;&nbsp;

    <%-- инициализация --%>
    <%! boolean flagStart = true; %>
    <%! String wrongSign;%>

    <%
        ReverseNoteUtil st = new ReverseNoteUtil();
        Resolve solve = new Resolve();
        Validation validate = new Validation();
        String in_exp = request.getParameter("expresion");
        if (in_exp != null) {
            if (Objects.equals(validate.checkBracets(in_exp), "Brackets checked")) {
                wrongSign = validate.validate(st.convertToReverseNote(st.processExpression(request.getParameter("expresion"))));
            } else {
                wrongSign = validate.checkBracets(in_exp);
            }
        } else {
            wrongSign = "-";
        }
        String res = "0";
        if (request.getParameter("expresion") == null || !"-".equals(wrongSign) || request.getParameter("expresion").equals("")) {
            res = "Нет";
        } else {
            res = solve.calculate(st.convertToReverseNote(st.processExpression(request.getParameter("expresion"))));
            wrongSign = validate.checkInfinity(res);
        }


    %>
    <%--<INPUT type="text" name="result" size="25" <%out.println("value=" + wrongSign);%>><BR>--%>

    <%--Обратная запись с разделителем: <INPUT type="text" name="result" size="25" <%out.println("value=" + st.convertToReverseNote(st.processExpression(request.getParameter("expresion"))));%>><BR>--%>

    Результат: <INPUT type="text" name="result" size="40" <%out.println("value=" + res);%>><BR>

    <%request.getSession().setAttribute("result", res);%>

    <INPUT type="submit" name="submit" value="Вычислить" onclick="<%
    if (session.isNew()) {
        session.setAttribute("in_exp", null);
        }
     CalcLog calcLog = new CalcLog();
        calcLog.setExpression(in_exp);
        calcLog.setResult(res);
     try {

     if (session.getAttribute("in_exp") != null && in_exp != null && !session.getAttribute("in_exp").equals(in_exp))
        calcLogDAO.save(calcLog);
        session.setAttribute("in_exp", in_exp);
        } catch (SQLException e) {
         throw new RuntimeException(e);
        }
     %>"><BR>

    <%--<INPUT type="submit" name="submit" value="Вычислить" onclick="<%if (in_exp != null && in_exp != "") dao.insertLog(in_exp, res, wrongSign);%>"><BR>--%>

    <INPUT type="reset" name="clear" value="Очистить" onreset="clearResult()">

    <BR><BR>

    <script type="text/javascript">
        function clearResult() {
            document.form1.getElementById("expresion").value = "0";
            document.form1.getElementById("result").value = "0";
        }
    </script>

</FORM>

<h2>История</h2>

<FORM name="form2" method="post">

    Выввести последние: <INPUT type="text" id="history_size" name="history_size" value="10" size="5" maxlength="3"><BR>

    <%--<%--%>
    <%--String whichRadio = request.getParameter("radio");--%>
    <%--String r1checked = "";--%>
    <%--if (whichRadio == null) whichRadio = "0";--%>
    <%--dao.updateRadioButton(whichRadio);--%>
    <%--String selectedRadio = dao.getRadioButton();--%>
    <%--%>--%>

    <%--тест    <%=selectedRadio%><BR>--%>

    <%--<INPUT type="radio" name="radio" value="0" <%if (selectedRadio == "0") out.println(" checked");%>>Все записи<BR>--%>
    <%--<INPUT type="radio" name="radio" value="1" <%if (selectedRadio == "1") out.println(" checked");%>>Только корректные записи<BR>--%>
    <%--<INPUT type="radio" name="radio" value="2" <%if (selectedRadio == "2") out.println(" checked");%>>Только некорректные записи<BR>--%>
    <%
        if (session.isNew()) {
            session.setAttribute("radio", "0");
        }
    %>

    <%
        if (request.getParameter("radio") != null) {
            if (request.getParameter("radio").equals("0"))
                session.setAttribute("radio", "0");
            if (request.getParameter("radio").equals("1"))
                session.setAttribute("radio", "1");
            if (request.getParameter("radio").equals("2"))
                session.setAttribute("radio", "2");
        }
    %>
    Session ID: <%=session.getId()%>
    <INPUT type="radio" name="radio"
           value="0" <%if (session.getAttribute("radio").equals("0")) out.println(" checked");%>>Все
    записи<BR>
    <INPUT type="radio" name="radio"
           value="1" <%if (session.getAttribute("radio").equals("1")) out.println(" checked");%>>Только
    корректные записи<BR>
    <INPUT type="radio" name="radio"
           value="2" <%if (session.getAttribute("radio").equals("2")) out.println(" checked");%>>Только
    некорректные записи<BR>


    <INPUT type="submit" name="submit1" value="Применить" onclick="

    "><BR>


    <BR><BR>

    <table border="1px" cellpadding="8px">
        <%-- Названия колонок в таблице --%>
        <tr>
            <th>Выражение</th>
            <th>Результат</th>
            <%--<th>Синтаксическая ошибка</th>--%>
            <%--<th>Дата ввода</th>--%>
        </tr>
        <% if (!flagStart) { %>


        <% Integer max_h;
            if (request.getParameter("history_size") == null || request.getParameter("history_size") == "") {
                max_h = 10;
            } else {
                max_h = Integer.valueOf(request.getParameter("history_size"));
            }
            List<CalcLog> records = calcLogDAO.getAll();
        %>
        Заполняем таблицу
        <%
            for (CalcLog item : records) {
                out.println("<tr>");
                out.println("<td>" + item.getExpression() + "</td>");
                out.println("<td>" + item.getResult() + "</td>");
//                out.println("<td>" + item.getError() + "</td>");
//                out.println("<td>" + item.getDate() + "</td>");
                out.println("</tr>");
            }
        %>

        <% } else
            flagStart = false; %>
    </table>
</FORM>
</body>
</html>