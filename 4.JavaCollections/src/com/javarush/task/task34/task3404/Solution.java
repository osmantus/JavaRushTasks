package com.javarush.task.task34.task3404;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Рекурсия для мат. выражения
*/
public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.recursion("sin(2*(-5+1.5*4)+28)", 0); //expected output 0.5 6
    }

    public void recursion(final String expression, int countOperation) {
        String cleanedExpression = expression.replaceAll("\\s+", "");

        if (countOperation == 0) {
            Matcher m = Pattern.compile("(sin)|(cos)|(tan)|(\\^)|(\\*)|(\\/)|(\\+)|(\\-)").matcher(cleanedExpression);
            while (m.find()) countOperation++;

            if (countOperation == 0) countOperation = -1;
        }

        String result = calculatePower(cleanedExpression);
        if (result == null) {
            result = calculateUnaryPlusOrMinus(cleanedExpression);
            if (result == null) {
                result = calculateMultiplicationOrDivision(cleanedExpression);
                if (result == null) {
                    result = calculateBlock(cleanedExpression);
                    if (result == null) {
                        result = calculateAdditionOrSubtraction(cleanedExpression);
                    }
                }
            }
        }

        if (result != null) {
            recursion(result, countOperation);
            return;
        }

        Locale.setDefault(Locale.ENGLISH);
        System.out.println(
                new DecimalFormat("#.##").format(Double.parseDouble(cleanedExpression))
                        + " "
                        + ((countOperation == -1) ? 0 : countOperation));
    }

    private String calculateUnaryPlusOrMinus(String expression) {
        String resultExpression = null;
        String result;

        Matcher m = Pattern.compile("(\\+\\-)|(\\-\\+)|(\\+\\+)|(\\-\\-)").matcher(expression);
        if (m.find()) {
            result = (m.group(1) != null || m.group(2) != null) ? "-" : "+";
            resultExpression
                    = expression.substring(0, m.start())
                    + result
                    + expression.substring(m.end());
        }
        return resultExpression;
    }

    private String calculateMultiplicationOrDivision(String expression) {
        String resultExpression = null;
        Double operand1, operand2, result = null;

        Matcher m = Pattern.compile("(?<=^|[\\+\\-\\*\\/\\(\\)])(\\(([-+]?(\\d+(\\.\\d+)?|\\.\\d+))\\)|([-+]?(\\d+(\\.\\d+)?|\\.\\d+)))([\\*\\/])(\\(([-+]?(\\d+(\\.\\d+)?|\\.\\d+))\\)|([-+]?(\\d+(\\.\\d+)?|\\.\\d+)))(?=[\\+\\-\\*\\/\\(\\)]|$)").matcher(expression);
        if (m.find()) {
            String op1 = calculateBlock(m.group(1));
            op1 = (op1 == null) ? m.group(1) : op1;
            String op2 = calculateBlock(m.group(9));
            op2 = (op2 == null) ? m.group(9) : op2;

            operand1 = Double.parseDouble(op1);
            operand2 = Double.parseDouble(op2);
            switch (m.group(8)) {
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    result = operand1 / operand2;
                    break;
            }

            if (result != null) {
                result = new BigDecimal(result).setScale(4, RoundingMode.HALF_UP).doubleValue();
                resultExpression
                        = expression.substring(0, m.start())
                        + ((result >= 0) ? String.valueOf(result) : "(" + String.valueOf(result) + ")")
                        + expression.substring(m.end());
            }
        }
        return resultExpression;
    }

    private String calculatePower(String expression) {
        String resultExpression = null;
        Matcher m = Pattern.compile("(?<=^|[\\+\\-\\*\\/\\(])(\\(([-+]?(\\d+(\\.\\d+)?|\\.\\d+))\\)|(\\d+(\\.\\d+)?|\\.\\d+))([\\^])(\\(([-+]?(\\d+(\\.\\d+)?|\\.\\d+))\\)|([-+]?(\\d+(\\.\\d+)?|\\.\\d+)))(?=[\\+\\-\\*\\/\\)]|$)").matcher(expression);
        if (m.find()) {
            String op1 = calculateBlock(m.group(1));
            op1 = (op1 == null) ? m.group(1) : op1;
            String op2 = calculateBlock(m.group(8));
            op2 = (op2 == null) ? m.group(8) : op2;

            Double result = Math.pow(Double.parseDouble(op1), Double.parseDouble(op2));

            result = new BigDecimal(result).setScale(4, RoundingMode.HALF_UP).doubleValue();

            resultExpression
                    = expression.substring(0, m.start())
                    + ((result >= 0) ? String.valueOf(result) : "(" + String.valueOf(result) + ")")
                    + expression.substring(m.end());
        }
        return resultExpression;
    }

    private String calculateAdditionOrSubtraction(String expression) {
        String resultExpression = null;
        Double operand1, operand2, result = null;

        Matcher m = Pattern.compile("(?<=^|[\\+\\-\\(\\)])(\\(([-+]?(\\d+(\\.\\d+)?|\\.\\d+))\\)|([-+]?(\\d+(\\.\\d+)?|\\.\\d+)))([+-])(\\(([-+]?(\\d+(\\.\\d+)?|\\.\\d+))\\)|([-+]?(\\d+(\\.\\d+)?|\\.\\d+)))(?=[\\+\\-\\(\\)]|$)").matcher(expression);
        if (m.find()) {
            String op1 = calculateBlock(m.group(1));
            op1 = (op1 == null) ? m.group(1) : op1;
            String op2 = calculateBlock(m.group(9));
            op2 = (op2 == null) ? m.group(9) : op2;

            operand1 = Double.parseDouble(op1);
            operand2 = Double.parseDouble(op2);
            switch (m.group(8)) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
            }

            if (result != null) {
                result = new BigDecimal(result).setScale(4, RoundingMode.HALF_UP).doubleValue();
                resultExpression
                        = expression.substring(0, m.start())
                        + ((result >= 0) ? String.valueOf(result) : "(" + String.valueOf(result) + ")")
                        + expression.substring(m.end());
            }
        }
        return resultExpression;
    }

    private String calculateBlock(String expression) {
        String resultExpression = null;
        Double operand, result = null;

        Matcher m = Pattern.compile("(sin|cos|tan)?\\(([-+]?(\\d+(\\.\\d+)?|\\.\\d+))\\)").matcher(expression);
        if (m.find()) {
            operand = Double.parseDouble(m.group(2));
            try {
                switch (m.group(1)) {
                    case "sin":
                        result = Math.sin(Math.toRadians(operand));
                        break;
                    case "cos":
                        result = Math.cos(Math.toRadians(operand));
                        break;
                    case "tan":
                        result = Math.tan(Math.toRadians(operand));
                        break;
                }
                if (result != null) {
                    result = new BigDecimal(result).setScale(4, RoundingMode.HALF_UP).doubleValue();
                    resultExpression
                            = expression.substring(0, m.start())
                            + ((result >= 0) ? String.valueOf(result) : "(" + String.valueOf(result) + ")")
                            + expression.substring(m.end());
                }
            } catch (NullPointerException e) {
                result = operand;
                result = new BigDecimal(result).setScale(4, RoundingMode.HALF_UP).doubleValue();
                resultExpression
                        = expression.substring(0, m.start())
                        + ((result >= 0) ? "+" : "")
                        + String.valueOf(result)
                        + expression.substring(m.end());
            }
        }
        return resultExpression;
    }

    public Solution() {
        //don't delete
    }
}
