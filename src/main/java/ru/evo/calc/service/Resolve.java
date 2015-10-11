package ru.evo.calc.service;

import java.util.Stack;
import static ru.evo.calc.service.util.ReverseNoteUtil.*;

public class Resolve {
    private Double res;
    private String temp = "";

    public String calculate(String expression) {
        return resolve(convertToReverseNote(expression));
    }

    //Вычисление результата
    public String resolve(String reverseNote){
        Stack<Double> valStack = new Stack<Double>();
        if (Character.isDigit(reverseNote.charAt(reverseNote.length() - 1)))
            return reverseNote;
        Integer i = 0;
        for (i = 0; i < reverseNote.length(); i++) {
            if (Character.isDigit(reverseNote.charAt(i)) || reverseNote.charAt(i) == '_' || reverseNote.charAt(i) == '.') {
                if (reverseNote.charAt(i) != '_') {
                    temp = temp + reverseNote.charAt(i);
                }
                if (!Character.isDigit(reverseNote.charAt(i + 1)) && reverseNote.charAt(i + 1) != '.') {
                    valStack.push(Double.valueOf(temp));
                    temp = "";
                }
            } else {
                Double n2 = valStack.pop();
                Double n1 = valStack.pop();
                switch (reverseNote.charAt(i)) {
                    case '^':
                        res = Math.pow(n1, n2);
                        break;
                    case '+':
                        res = n1 + n2;
                        break;
                    case '-':
                        res = n1 - n2;
                        break;
                    case '*':
                        res = n1 * n2;
                        break;
                    case '/':
                        res = n1 / n2;
                        break;
                    default:
                        ;
                }
                valStack.push(res);
            }
        }
        reverseNote = (String.valueOf(valStack.pop())).replace("Infinity", "Деление на ноль");
        return reverseNote;
    }
}
