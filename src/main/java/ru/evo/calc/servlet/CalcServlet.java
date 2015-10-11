package ru.evo.calc.servlet;

import ru.evo.calc.dao.api.CalcLogDAO;
import ru.evo.calc.dao.impl.CalcLogDAOImpl;
import ru.evo.calc.service.Resolve;
import ru.evo.calc.service.Validation;
import ru.evo.calc.service.util.ReverseNoteUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class CalcServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/main1.jsp").forward(req, resp);
    }

    private String resolveResult(HttpServletRequest req) {
        String wrongSign;
        CalcLogDAO calcLogDAO = new CalcLogDAOImpl();
        ReverseNoteUtil st = new ReverseNoteUtil();
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
        return res;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String res = resolveResult(req);
        req.setAttribute("result", res);
        doGet(req, resp);
    }
}