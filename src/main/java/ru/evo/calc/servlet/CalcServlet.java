package ru.evo.calc.servlet;

import ru.evo.calc.dao.api.CalcLogDAO;
import ru.evo.calc.dao.impl.CalcLogDAOImpl;
import ru.evo.calc.dao.model.CalcLog;
import ru.evo.calc.service.Resolve;
import ru.evo.calc.service.Validation;
import ru.evo.calc.service.util.ReverseNoteUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet("/calc")
public class CalcServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CalcLogDAO calcLogDAO = new CalcLogDAOImpl();
        List<CalcLog> calcLogs = calcLogDAO.getAll();
        req.setAttribute("calcLogs",calcLogs);
        getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
    }

    private String resolveResult(HttpServletRequest req) {
        String wrongSign;
        CalcLogDAO calcLogDAO = new CalcLogDAOImpl();
        Resolve solve = new Resolve();
        Validation validate = new Validation();
        String in_exp = req.getParameter("expression");
        if (in_exp != null) {
            if (Objects.equals(validate.checkBracets(in_exp), "Brackets checked")) {
                wrongSign = validate.validate(req.getParameter("expression"));
            } else {
                wrongSign = validate.checkBracets(in_exp);
            }
        } else {
            wrongSign = "-";
        }
        String res = "0";
        if (req.getParameter("expression") == null || !"-".equals(wrongSign) || req.getParameter("expression").equals("")) {
            res = "Нет";
        } else {
            res = solve.calculate(req.getParameter("expression"));
            wrongSign = validate.checkInfinity(res);
        }
        CalcLog calcLog = new CalcLog();
        calcLog.setExpression(in_exp);
        calcLog.setResult(res);
        calcLogDAO.save(calcLog);
        return res;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String res = resolveResult(req);
        req.setAttribute("result", res);
        CalcLogDAO calcLogDAO = new CalcLogDAOImpl();
        List<CalcLog> calcLogs = calcLogDAO.getAll();
        req.setAttribute("calcLogs",calcLogs);
        doGet(req, resp);
    }
}