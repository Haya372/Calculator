package com.myApp.scientificcaliculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {

    private MyEditText formula_view;
    private TextView ans_view;
    private StringBuilder formula;
    private ReversePolishNotation rpn=new ReversePolishNotation();
    private StatisticalCalculation statisticalCalculation=new StatisticalCalculation();
    private String ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        formula_view=findViewById(R.id.statics_formula_view);
        ans_view=findViewById(R.id.statics_answer_view);
        formula=new StringBuilder();
        ans="mean:  dispersion:  sd:";
        ans_view.setText(ans);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_statistic);
        setSupportActionBar(toolbar);
    }

    public void onClick(View v){
        int cursolPos=formula_view.getSelectionEnd();
        switch(v.getId()){
            case R.id.statics_button_clear:
                formula=formula.delete(0,formula.length());
                cursolPos=0;
                break;
            case R.id.statics_button_del:
                cursolPos--;
                formula=formula.deleteCharAt(cursolPos);
                break;
            case R.id.statics_button_equal:
                String[] numbers=formula.toString().split(",");
                for(String n:numbers){
                    n=rpn.culculate(n,"");
                }
                ans=statisticalCalculation.execute(numbers);
                break;
            default:
                Button button=(Button)findViewById(v.getId());
                String symbol=button.getText().toString();
                formula.insert(cursolPos,symbol);
                cursolPos+=symbol.length();
                break;
        }
        formula_view.setText(formula.toString());
        ans_view.setText(ans);
        formula_view.setSelection(cursolPos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_statistic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int cursolPos=formula_view.getSelectionEnd();
        //define toolbar actions
        switch (item.getItemId()) {
            default:
                String symbol=item.getTitle().toString();
                formula.insert(cursolPos,symbol+"()");
                cursolPos+=symbol.length()+1;
                break;

        }
        formula_view.setText(formula.toString());
        formula_view.setSelection(cursolPos);
        return super.onOptionsItemSelected(item);
    }
}
