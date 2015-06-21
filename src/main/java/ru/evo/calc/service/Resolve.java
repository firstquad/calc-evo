package ru.evo.calc.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.evo.calc.dao.CalcLogService;
import ru.evo.calc.model.CalcLog;
import ru.evo.util.HiberFactory;

import java.util.Stack;

public class Resolve
{
    private Integer i = 0;
    private Double n1,n2,res;
    private String temp = "";

    //Вычисление результата
    public String getResolve(String reverseNote, String expression){
        Stack<Double> valStack = new Stack<Double>();
        if (Character.isDigit(reverseNote.charAt(reverseNote.length() - 1)))
            return reverseNote;
        for(i = 0; i < reverseNote.length(); i++) {
            if(Character.isDigit(reverseNote.charAt(i)) || reverseNote.charAt(i) == '_'  || reverseNote.charAt(i) == '.')  {
                if(reverseNote.charAt(i) != '_'){
                  temp = temp + reverseNote.charAt(i);
                }
                if(!Character.isDigit(reverseNote.charAt(i + 1)) && reverseNote.charAt(i+1) != '.'){
                    valStack.push(Double.valueOf(temp));
                    temp = "";
                }
            }
            else {
                n2 = valStack.pop();
                n1 = valStack.pop();
                switch(reverseNote.charAt(i)) {
                    case '^': res = Math.pow(n1, n2); break;
                    case '+': res = n1 + n2; break;
                    case '-': res = n1 - n2; break;
                    case '*': res = n1 * n2; break;
                    case '/': res = n1 / n2; break;
                    default: ;
                }
                valStack.push(res);
            }
        }
        reverseNote = (String.valueOf(valStack.pop())).replace("Infinity", "Деление на ноль");

//        SessionFactory sessionFactory = new Configuration().configure()
//                .buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//
//        session.save(calcLog);
//
//        session.getTransaction().commit();
//        session.close();
//
//        CalcLog calcLog = new CalcLog();
//        calcLog.setExpression(expression);
//        calcLog.setResult(reverseNote);
//        CalcLogService calcLogService = new CalcLogService();
//        calcLogService.persist(calcLog);

        return reverseNote;
    }
}
