package com.myApp.scientificcaliculator;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class ReversePolishNotation {

    public ArrayList<String> toRPN(String formula,String replaceAns){
        ArrayList<String>rpn=new ArrayList<>();
        Deque<String> operator = new ArrayDeque<>();
        String num="";
        boolean error_flag=false;
        boolean isOperatorBefore=true;
        if(replaceAns.isEmpty()){
            replaceAns="0";
        }
        for(int i=0;i<formula.length();i++){
            char c=formula.charAt(i);
            switch(c){
                case '×':
                case '÷':
                    if(!isOperatorBefore){
                        if(!num.isEmpty()){
                            rpn.add(num);
                            num="";
                        }
                    }
                    if(operator.size()!=0) {
                        String stackTop = operator.peek();
                        Log.d("top",stackTop);
                        while (stackTop.equals("√")) {
                            rpn.add(String.valueOf(stackTop));
                            Log.d("pop",stackTop);
                            operator.pop();
                            stackTop = operator.peek();
                            if(stackTop==null)break;
                        }
                    }
                    operator.push(String.valueOf(c));
                    isOperatorBefore=true;
                    break;
                case '+':
                case '-':
                    if(i==0){
                        num+=String.valueOf(c);
                        break;
                    }
                    if(!isOperatorBefore){
                        if(!num.isEmpty()){
                            rpn.add(num);
                            num="";
                        }
                    }
                    if(operator.size()!=0) {
                        String stackTop = operator.peek();
                        Log.d("top",stackTop);
                        while (stackTop.equals("×") || stackTop.equals("÷")||stackTop.equals("√")) {
                            rpn.add(String.valueOf(stackTop));
                            Log.d("pop",stackTop);
                            operator.pop();
                            stackTop = operator.peek();
                            if(stackTop==null)break;
                        }
                    }
                    operator.push(String.valueOf(c));
                    isOperatorBefore=true;
                    break;
                case '(':
                    if(!isOperatorBefore){
                        if(!num.isEmpty()){
                            rpn.add(num);
                            num="";
                        }
                        operator.push("×");
                    }
                    String tmp=formula.substring(i);
                    int BracketsEnd=getBracketsEndIndex(tmp);
                    if(BracketsEnd==-1){
                        //()の個数違いのerror処理
                        error_flag=true;
                        break;
                    }
                    String child_formula=formula.substring(i+1,i+BracketsEnd);
                    ArrayList<String> child_rpn=toRPN(child_formula,replaceAns);
                    rpn.addAll(child_rpn);
                    i+=BracketsEnd;
                    isOperatorBefore=false;
                    break;
                case 'π':
                    if(!isOperatorBefore){
                        if(!num.isEmpty()){
                            rpn.add(num);
                            num="";
                        }
                        operator.push("×");
                    }
                    rpn.add(String.valueOf(Math.PI));
                    isOperatorBefore=false;
                    break;
                case 'e':
                    if(!isOperatorBefore){
                        if(!num.isEmpty()){
                            rpn.add(num);
                            num="";
                        }
                        operator.push("×");
                    }
                    rpn.add(String.valueOf(Math.E));
                    isOperatorBefore=false;
                    break;
                case '√':
                    if(!isOperatorBefore){
                        if(!num.isEmpty()){
                            rpn.add(num);
                            num="";
                        }
                        operator.push("×");
                    }
                    operator.push(String.valueOf(c));
                    isOperatorBefore=true;
                    break;
                case 's':
                    if(!isOperatorBefore){
                        if(!num.isEmpty()){
                            rpn.add(num);
                            num="";
                        }
                        operator.push("×");
                        Log.d("push","*");
                    }
                    operator.push("sin");
                    //Log.d("push","sin");
                    i+=2;
                    isOperatorBefore=true;
                    break;
                case 'c':
                    if(!isOperatorBefore){
                        if(!num.isEmpty()){
                            rpn.add(num);
                            num="";
                        }
                        operator.push("×");
                    }
                    operator.push("cos");
                    i+=2;
                    isOperatorBefore=true;
                    break;
                case 't':
                    if(!isOperatorBefore){
                        if(!num.isEmpty()){
                            rpn.add(num);
                            num="";
                        }
                        operator.push("×");
                    }
                    operator.push("tan");
                    i+=2;
                    isOperatorBefore=true;
                    break;
                case 'l':
                    if(!isOperatorBefore){
                        if(!num.isEmpty()){
                            rpn.add(num);
                            num="";
                        }
                        operator.push("×");
                    }
                    operator.push("log");
                    i+=2;
                    isOperatorBefore=true;
                    break;
                case 'A':
                    if(!isOperatorBefore){
                        if(!num.isEmpty()){
                            rpn.add(num);
                            num="";
                        }
                        Log.d("push","*");
                        operator.push("×");
                    }
                    rpn.add(replaceAns);
                    i+=2;
                    isOperatorBefore=false;
                    break;
                default:
                    num+=String.valueOf(c);
                    isOperatorBefore=false;
                    break;
            }
        }
        if(!num.isEmpty()){
            rpn.add(num);
        }
        while(operator.size()!=0){
            String c=operator.pop();
            rpn.add(String.valueOf(c));
        }
        if(error_flag)return null;
        return rpn;
    }

    public String culculate(String formula,String replaceAns){
        ArrayList<String> rpn=toRPN(formula,replaceAns);
        Deque<Double> numbers=new ArrayDeque<>();
        String res;
        try {
            for (String s : rpn) {
                Log.d("RPN",s);
                double x, y;
                switch (s) {
                    case "+":
                        y = numbers.pop();
                        x = numbers.pop();
                        numbers.push(x + y);
                        //Log.d("add",String.valueOf(x)+"+"+String.valueOf(y));
                        break;
                    case "-":
                        y = numbers.pop();
                        x = numbers.pop();
                        numbers.push(x - y);
                        //Log.d("div",String.valueOf(x)+"-"+String.valueOf(y));
                        break;
                    case "×":
                        y = numbers.pop();
                        x = numbers.pop();
                        numbers.push(x * y);
                        //Log.d("mul",String.valueOf(x)+"*"+String.valueOf(y));
                        break;
                    case "÷":
                        y = numbers.pop();
                        x = numbers.pop();
                        numbers.push(x / y);
                        //Log.d("div",String.valueOf(x)+"/"+String.valueOf(y));
                        break;
                    case "√":
                        x=numbers.pop();
                        numbers.push(Math.sqrt(x));
                        //Log.d("sqrt","√"+String.valueOf(Math.sqrt(x)));
                        break;
                    case "sin":
                        x=numbers.pop();
                        numbers.push(Math.sin(x));
                        Log.d("sin",String.valueOf(Math.sin(Math.PI/2)));
                        break;
                    case "cos":
                        x=numbers.pop();
                        numbers.push(Math.cos(x));
                        //Log.d("sqrt","√"+String.valueOf(Math.sqrt(x)));
                        break;
                    case "tan":
                        x=numbers.pop();
                        numbers.push(Math.tan(x));
                        //Log.d("sqrt","√"+String.valueOf(Math.sqrt(x)));
                        break;
                    case "log":
                        x=numbers.pop();
                        numbers.push(Math.log(x));
                        //Log.d("sqrt","√"+String.valueOf(Math.sqrt(x)));
                        break;
                    default:
                        numbers.push(Double.parseDouble(s));
                        break;
                }
            }
            double ans=numbers.pop();
            BigDecimal round=new BigDecimal(ans);
            round=round.setScale(10, RoundingMode.HALF_UP);
            ans=round.doubleValue();
            if(numbers.isEmpty()) {
                if (ans == (long) ans) {
                    res = String.valueOf((long) ans);
                } else {
                    res = String.valueOf(ans);
                }
            }else{
                res="Error";
            }
        }catch(Exception e){
            res="Error";
        }
        return res;
    }

    private int getBracketsEndIndex(String formula){
        int priority=1;
        int index=1;
        while(priority>0&&index<formula.length()){
            char c=formula.charAt(index);
            if(c=='('){
                priority++;
            }else if(c==')'){
                priority--;
            }
            index++;
        }
        if(priority!=0)return -1;
        return index-1;
    }
}