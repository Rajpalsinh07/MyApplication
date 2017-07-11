package login1.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static login1.myapplication.R.id.listview;

public class ahm extends AppCompatActivity {

    ListView listView;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahm1);

        Intent intent = getIntent();
        city = intent.getStringExtra("_CITY_");


        listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> nadapter = new ArrayAdapter<String>(ahm.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.amd));

        listView.setAdapter(nadapter);


    }
}
