package ru.evo.calc.dao;

import org.hibernate.Session;
import ru.evo.calc.model.CalcLog;
import ru.evo.util.HiberUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CalcLogDAOImpl implements CalcLogDAO {
    private Session session = openSession();

    public Session openSession() {
        session = HiberUtil.getSessionFactory().openSession();
        return session;
    }

    public void closeSession(){
        session.close();
    }

    public List<CalcLog> getAll() throws SQLException {
        return openSession().createCriteria(CalcLog.class).list();
    }

    public void save(CalcLog calcLog) {
        session.beginTransaction();
        session.save(calcLog);
        session.getTransaction().commit();
    }

}
