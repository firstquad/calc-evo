package ru.evo.calc.dao.impl;

import org.hibernate.Session;
import ru.evo.calc.dao.api.CalcLogDAO;
import ru.evo.calc.dao.model.CalcLog;
import ru.evo.calc.dao.util.HiberUtil;

import java.sql.SQLException;
import java.util.List;


public class CalcLogDAOImpl implements CalcLogDAO {
    private Session session = openSession();

    public Session openSession() {
        session = HiberUtil.getSessionFactory().openSession();
        return session;
    }

    @SuppressWarnings("unchecked")
    public List<CalcLog> getAll() throws SQLException {
        return openSession().createCriteria(CalcLog.class).list();
    }

    public void save(CalcLog calcLog) {
        session.beginTransaction();
        session.save(calcLog);
        session.getTransaction().commit();
    }

    public void closeSession() {
        session.close();
    }

}
