package com.myApp.scientificcaliculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    private MyEditText formula_view;
    private TextView ans_view;
    private StringBuilder formula;
    private String ans;
    private ReversePolishNotation rpn=new ReversePolishNotation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        formula_view=(MyEditText) findViewById(R.id.formula_view);
        ans_view=(TextView) findViewById(R.id.answer_view);
        formula=new StringBuilder();
        ans="";
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }

    public void onClick(View v){
        int cursolPos=formula_view.getSelectionEnd();
        switch(v.getId()){
            case R.id.button_clear:
                formula=formula.delete(0,formula.length());
                cursolPos=0;
                break;
            case R.id.button_del:
                cursolPos--;
                formula=formula.deleteCharAt(cursolPos);
                break;
            case R.id.button_equal:
                String tmp=formula.toString();
                ans=rpn.culculate(tmp,ans);
                formula.delete(0,formula.length());
                cursolPos=0;
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int cursolPos=formula_view.getSelectionEnd();
        //define toolbar actions
        switch (item.getItemId()) {
            case R.id.to_statistic:
                Intent intent=new Intent(this,StatisticActivity.class);
                startActivity(intent);
                break;
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