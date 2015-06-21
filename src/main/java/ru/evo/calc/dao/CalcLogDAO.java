package ru.evo.calc.dao;

import ru.evo.calc.model.CalcLog;

import java.sql.SQLException;
import java.util.List;


public interface CalcLogDAO {
    List<CalcLog> getAll() throws SQLException;

    void save(CalcLog calcLog) throws SQLException;
}
