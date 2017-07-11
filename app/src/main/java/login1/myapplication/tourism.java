package login1.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class tourism extends AppCompatActivity {
    private RecyclerView recyclerview;
    private List<TourismModel> historicallist = new ArrayList<>();
    private HistoricalAdapter historicalA;
    String url = "http://192.168.42.168";
    ProgressDialog pd;

    TModel tml;
    ArrayList<TModel> tml_arr;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(tourism.this,mainpage.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism);

        pd = new ProgressDialog(tourism.this);
        pd.setMessage("Please wait");
        pd.setCancelable(false);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setLogo(R.drawable.images1);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);

        recyclerview = (RecyclerView) findViewById(R.id.Historicallist);

        historical();
    }

    private void historical() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        REGAPI services = retrofit.create(REGAPI.class);
        Call<List<TourismModel>> call = services.historicaldet();

        call.enqueue(new Callback<List<TourismModel>>() {
            @Override
            public void onResponse(Call<List<TourismModel>> call, Response<List<TourismModel>> response) {

                tml_arr = new ArrayList<>();

                Log.e(">>>>>>", response.body().get(0).getName());

                for (int i = 0; i < response.body().size(); i++) {
                    TModel historical_list = new TModel();

                    historical_list.setName(response.body().get(i).getName());
                    historical_list.setImage(response.body().get(i).getImage());
                    historical_list.setAddress(response.body().get(i).getAddress());
                    tml_arr.add(historical_list);
                }


                Log.e("tml_arr", "" + tml_arr.size());

                historicalA = new HistoricalAdapter(tml_arr, getApplicationContext());
                RecyclerView.LayoutManager layout_Manager = new LinearLayoutManager(getApplicationContext());
                recyclerview.setLayoutManager(layout_Manager);
                recyclerview.setAdapter(historicalA);
                historicalA.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TourismModel>> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:

                Intent s= new Intent(tourism.this,setting.class);
                startActivity(s);
                finish();
                return true;

            case R.id.home:

                Intent s1=new Intent(tourism.this,mainpage.class);
                startActivity(s1);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}