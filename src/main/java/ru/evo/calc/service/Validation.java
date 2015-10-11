package ru.evo.calc.service;

import java.util.Stack;

import static ru.evo.calc.service.util.ReverseNoteUtil.convertToReverseNote;

public class Validation {
    private Integer openBracket = 0;
    private Integer closeBracket = 0;
    private Boolean digit = true;
    private Stack<Character> stack = new Stack<>();

    public String validate(String expression) {
        expression = convertToReverseNote(expression);
        if (expression == null || expression.equals(""))
            return "wrongExpression";
        if (!Character.isDigit(expression.charAt(0)))
            return "wrongBeginExpression";
        if (!Character.isDigit(expression.charAt(0)) && !Character.isDigit(expression.charAt(1)))
            return "wrongBeginExpression";
        for (int i = 0; i < expression.length(); i++) {
            if (!Character.isDigit(expression.charAt(i)) &&
                    (expression.charAt(i) != '+') &&
                    (expression.charAt(i) != '-') &&
                    (expression.charAt(i) != '/') &&
                    (expression.charAt(i) != '*') &&
                    (expression.charAt(i) != '^') &&
                    (expression.charAt(i) != '_') &&
                    (expression.charAt(i) != '.')) {
                return "wrongSign" + ": (" + String.valueOf(expression.charAt(i)) + ") " + "Position" + " " + String.valueOf(i + 1);

            }
        }
        int delimeter = 0;
        int operator = 0;
        if (!Character.isDigit(expression.charAt(expression.length() - 1))) {
            for (int i = 0; i < expression.length(); i++) {
                if (expression.charAt(i) == '_' && digit) {
                    digit = false;
                } else {
                    if (!digit && !Character.isDigit(expression.charAt(i))) return "wrongOperation";
                    digit = true;
                }
                if (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '_' || expression.charAt(i) == '.') {
                    if (!Character.isDigit(expression.charAt(i + 1)) && expression.charAt(i + 1) != '.') {
                        delimeter = delimeter + 1;
                    }
                }
                if (expression.charAt(i) != '_' && expression.charAt(i) != '.' && !Character.isDigit(expression.charAt(i)))
                    operator = operator + 1;
            }
            if (delimeter != 0 && delimeter - 1 != operator)
                return "wrongOperation";
        }
        return "-";
    }

    //Проверка баланса скобок
    public String checkBracets(String expression) {
        if (expression == null || expression.equals(""))
            return "wrongExpression";
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                stack.push('(');
            }
            if (expression.charAt(i) == ')' && stack.size() != 0) {
                while (String.valueOf(stack.peek()).charAt(0) != '(') {
                    stack.pop();
                }
                stack.pop();
            }
        }
        if (!stack.empty())
            return "wrongBrackets";
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') openBracket = openBracket + 1;
            if (expression.charAt(i) == ')') closeBracket = closeBracket + 1;
        }
        if (!openBracket.equals(closeBracket))
            return "wrongBracketsBalance";
        return "Brackets checked";
    }

    public String checkInfinity(String expression) {
        if (expression.equals("NullDivide"))
            return "NullDivide";
        else return "-";
    }

}
