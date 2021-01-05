package anubhav.calculatorapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    private ListView lv;
    private DBHelper dbHelper;
    private ArrayList<String> list;
    private HistoryLvAdapter adapter;
    private String calcName="";
    private ArrayList<String> EmptyList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv=(ListView)findViewById(R.id.listView);
        dbHelper=new DBHelper(this);
        calcName=getIntent().getStringExtra("calcName");
        list=dbHelper.showHistory(calcName);
        if(!list.isEmpty())
            adapter=new HistoryLvAdapter(this, R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
    }

    public void onClick(View v)
    {
        EmptyList.add("There is  no history yet");
        dbHelper.deleteRecords(calcName);
        adapter=new HistoryLvAdapter(this, R.layout.simple_list_item_1, EmptyList);
        lv.setAdapter(adapter);
    }

}
