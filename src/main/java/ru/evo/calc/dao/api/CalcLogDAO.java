package ru.evo.calc.dao.api;

import ru.evo.calc.dao.model.CalcLog;

import java.sql.SQLException;
import java.util.List;


public interface CalcLogDAO {

    List<CalcLog> getAll() throws SQLException;

    void save(CalcLog calcLog) throws SQLException;
}