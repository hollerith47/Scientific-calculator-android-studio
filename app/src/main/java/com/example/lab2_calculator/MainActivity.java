package com.example.lab2_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    TextView tvMain , tvSecondary;
    String pi = "3.14159265";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMain = findViewById(R.id.tvMain);
        tvSecondary = findViewById(R.id.tvSecondary);
    }

    //factorial function
    int factorial(int n)
    {
        return (n==1 || n==0) ? 1 : n*factorial(n-1);
    }

    //eval function
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                try {
                    if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                    return x;
                } catch (RuntimeException err){
                    return 0;
                }
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                try{
                    if (eat('(')) { // parentheses
                        x = parseExpression();
                        eat(')');
                    } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                        while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                        x = Double.parseDouble(str.substring(startPos, this.pos));
                    } else if (ch >= 'a' && ch <= 'z') { // functions
                        while (ch >= 'a' && ch <= 'z') nextChar();
                        String func = str.substring(startPos, this.pos);
                        x = parseFactor();
                        if (func.equals("sqrt")) x = Math.sqrt(x);
                        else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                        else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                        else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                        else if (func.equals("log")) x = Math.log10(x);
                        else if (func.equals("ln")) x = Math.log(x);
                        else throw new RuntimeException("Unknown function: " + func);
                    } else {
                        throw new RuntimeException("Unexpected: " + (char) ch);
                    }

                    if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                    return x;
                } catch (RuntimeException err){
                    return 0;
                }

            }
        }.parse();
    }

    String operator = "";
    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btnAC :
                tvMain.setText("");
                tvSecondary.setText("");
                operator = "";
                break;
            case R.id.btnDivide:
                tvMain.setText(tvMain.getText()+"÷");
                break;
            case R.id.btnMultiple:
                tvMain.setText(tvMain.getText()+"*");
                break;
            case R.id.btnMinus:
                tvMain.setText(tvMain.getText()+"-");
                break;
            case R.id.btnPlus:
                tvMain.setText(tvMain.getText()+"+");
                break;
            case R.id.btnSqrt:
                String val = tvMain.getText().toString();
                double r = Math.sqrt(Double.parseDouble(val));
                tvMain.setText(String.valueOf(r));
                break;
            case R.id.btnBb1:
                tvMain.setText(tvMain.getText()+"(");
                break;

            case R.id.btnBb2:
                tvMain.setText(tvMain.getText()+")");
                break;
            case R.id.btn0:
                tvMain.setText(tvMain.getText()+"0");
                break;
            case R.id.btn1:
                tvMain.setText(tvMain.getText()+"1");
                break;

            case R.id.btn2:
                tvMain.setText(tvMain.getText()+"2");
                break;

            case R.id.btn3:
                tvMain.setText(tvMain.getText()+"3");
                break;

            case R.id.btn4:
                tvMain.setText(tvMain.getText()+"4");
                break;
            case R.id.btn5:
                tvMain.setText(tvMain.getText()+"5");
                break;
            case R.id.btn6:
                tvMain.setText(tvMain.getText()+"6");
                break;
            case R.id.btn7:
                tvMain.setText(tvMain.getText()+"7");
                break;
            case R.id.btn8:
                tvMain.setText(tvMain.getText()+"8");
                break;
            case R.id.btn9:
                tvMain.setText(tvMain.getText()+"9");
                break;
            case R.id.btnDot:
                tvMain.setText(tvMain.getText()+".");
                break;
            case R.id.btnC:
                try {
                    val = tvMain.getText().toString();
                    val = val.substring(0, val.length()-1);
                    tvMain.setText(val);
                    break;
                }catch (IllegalStateException | StringIndexOutOfBoundsException err){
                    break;
                }
            case R.id.btnPi:
                tvSecondary.setText(pi);
                tvMain.setText(tvMain.getText()+pi);
                break;
            case R.id.btnSin:
                tvMain.setText(tvMain.getText()+"sin");
                break;
            case R.id.btnCos:
                tvMain.setText(tvMain.getText()+"cos");
                break;
            case R.id.btnTan:
                try {
                    tvMain.setText(tvMain.getText()+"tan");
                    break;
                }catch (IllegalStateException err){
                    break;
                }

            case R.id.btnOneByX:
                tvMain.setText(tvMain.getText()+"^"+"(-1)");
                break;
            case R.id.btnFact:
                try {
                    int value = Integer.parseInt(tvMain.getText().toString());
                    int fact = factorial(value);
                    tvMain.setText(String.valueOf(fact));
                    tvSecondary.setText(value+"!");
                    break;
                } catch (IllegalArgumentException err){
                    break;
                }

            case R.id.btnSquare:
                try {
                    double d = Double.parseDouble(tvMain.getText().toString());
                    double square = d*d;
                    tvMain.setText(String.valueOf(square));
                    tvSecondary.setText(d+"²");
                    break;
                }catch (IllegalStateException err){
                    break;
                }

            case R.id.btnLn:
                tvMain.setText(tvMain.getText()+"ln");
                break;
            case R.id.btnLog:
                tvMain.setText(tvMain.getText()+"log");
                break;
            case R.id.btnEqual:
                try {
                    String values = tvMain.getText().toString();
                    String replacedstr = values.replace('÷','/').replace('×','*');
                    double result = eval(replacedstr);
                    tvMain.setText(String.valueOf(result));
                    tvSecondary.setText(values);
                    break;
                } catch (IllegalStateException | StringIndexOutOfBoundsException err){
                    break;
                }

            default:
                break;

        }

    }
}