package ru.evo.calc.dao;

import org.hibernate.Session;
import ru.evo.calc.model.CalcLog;
import ru.evo.util.HiberFactory;
import java.sql.SQLException;
import java.util.List;


public class CalcLogService {
    private CalcLogDAOImpl calcLogDAO = HiberFactory.getInstance().getCalcLogDAO();

    public List<CalcLog> getAll() throws SQLException {
        calcLogDAO.openSession();
        List<CalcLog> calcLogs = calcLogDAO.getAll();
        calcLogDAO.closeSession();
        return calcLogs;
    }

    public void persist(CalcLog calcLog) {
        Session session = calcLogDAO.openSession();
        session.beginTransaction();
        session.save(calcLog);
        session.getTransaction().commit();
        session.close();
    }
}
