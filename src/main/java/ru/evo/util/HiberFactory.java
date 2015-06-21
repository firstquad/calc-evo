package ru.evo.util;

import ru.evo.calc.dao.CalcLogDAO;
import ru.evo.calc.dao.CalcLogDAOImpl;

public class HiberFactory {
    private static CalcLogDAOImpl calcLogDAO = null;
    private static HiberFactory instance = null;

    public static synchronized HiberFactory getInstance() {
        if (instance == null) {
            instance = new HiberFactory();
        }
        return instance;
    }

    public CalcLogDAOImpl getCalcLogDAO() {
        if (calcLogDAO == null) {
            calcLogDAO = new CalcLogDAOImpl();
        }
        return calcLogDAO;
    }
}
