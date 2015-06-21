package ru.evo.calc.service;

import java.util.Stack;

public class ReverseNote
{
    public String getReverseNote(String expression) {

        //Перевод в постфиксную нотацию
        String reverseNote = "";
        final Stack stack = new Stack();
        final Stack outString = new Stack();

        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == ')') {
                while (String.valueOf(stack.peek()).charAt(0) != '(') {
                    outString.push(stack.pop());
                }
                stack.pop();
            }
            if (expression.charAt(i) == '(') {
                stack.push('(');
            }
            if ((expression.charAt(i) == '+') || (expression.charAt(i) == '-') ||
                (expression.charAt(i) == '/') || (expression.charAt(i) == '*') ||
                (expression.charAt(i) == '^')) {
                if (stack.size() == 0) {
                    stack.push(expression.charAt(i));
                } else
                    if (priority(expression.charAt(i)) > priority(String.valueOf(stack.peek()).charAt(0))) {
                        stack.push(expression.charAt(i));
                    } else {
                        while ((stack.size() != 0) && (priority(String.valueOf(stack.peek()).charAt(0)) >= priority(expression.charAt(i)))) {
                            outString.push(stack.pop());
                        }
                        stack.push(expression.charAt(i));
                    }
            } else {
                if (expression.charAt(i) != '(' && expression.charAt(i) != ')'){
                    outString.push(expression.charAt(i));
                }
            }
        }
        for (int j = 0; j < outString.size(); j++) {
            reverseNote = reverseNote + String.valueOf(outString.get(j));

        }
        reverseNote = reverseNote.replace("_*", "*"); reverseNote = reverseNote.replace("_^", "^"); reverseNote = reverseNote.replace("_/", "/");
        reverseNote = reverseNote.replace("_-", "-"); reverseNote = reverseNote.replace("_+", "+");
        return reverseNote;
    }

    //Добавление разделителей
    public String doEdit(String expression){
        expression = "(" + expression + ")";
        expression = expression.replace("(-", "(0-"); expression = expression.replace(" ", "");
        expression = expression.replace("(+", "(0+"); expression = expression.replace("+", "_+");
        expression = expression.replace("-", "_-");   expression = expression.replace("/", "_/");
        expression = expression.replace("*", "_*");   expression = expression.replace("^", "_^");
        return expression;
    }

    //Приоритет операций
    private int priority(final char op) {
        switch (op) {
            case '^':
                return 4;
            case '*':
                return 3;
            case '/':
                return 3;
            case '-':
                return 2;
            case '+':
                return 2;
            case '(':
                return 1;
        }
        return 0;
    }

}