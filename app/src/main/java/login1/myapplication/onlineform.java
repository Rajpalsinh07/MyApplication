package login1.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class onlineform extends AppCompatActivity {

    private RecyclerView recyclerview;
    private ArrayList formlist;
    private FormAdapter formA;

    String url = "http://192.168.42.168";
    ProgressDialog pd;

    @Override
    public void onBackPressed() {
        Intent i=new Intent(onlineform.this,mainpage.class);
        startActivity(i);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlineform);

        pd = new ProgressDialog(onlineform.this);
        pd.setMessage("Please wait");
        pd.setCancelable(false);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setLogo(R.drawable.images1);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);

        isStoragePermissionGranted();

        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "PDFDOWNLOAD");

        //if(!folder.isDirectory())
        //{
            folder.mkdir();
        //}

        recyclerview = (RecyclerView) findViewById(R.id.formlist);

        RecyclerView.LayoutManager layout_Manager = new LinearLayoutManager(onlineform.this);
        recyclerview.setLayoutManager(layout_Manager);
        Log.e(">>>>>>>", "asdfghjkl");
        Form();

    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

            //resume tasks needing this permission
        }
    }

    private void Form() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        REGAPI services = retrofit.create(REGAPI.class);
        Call<List<FormModel>> call = services.form();
        Log.e("mm","calback");
        call.enqueue(new Callback<List<FormModel>>() {
            @Override
            public void onResponse(Call<List<FormModel>> call, Response<List<FormModel>> response) {


                //fml_arr = new ArrayList<>();

                Log.e(">>>>>>x", response.body().get(0).getName());

                formlist=new ArrayList();
                for (int i = 0; i < response.body().size(); i++) {
                    FormModel fml = new FormModel();
                    //TModel historical_list = new TModel();
                    Log.e(">>>>>l", response.body().get(i).getName());

                    fml.setName(response.body().get(i).getName());
                    fml.setForms(response.body().get(i).getForms());
                    formlist.add(fml);
                }

                Log.e("fml_arr", "" + formlist.size());

                //formA.notifyDataSetChanged();

                formA = new FormAdapter(formlist, onlineform.this);
                recyclerview.setAdapter(formA);
            }

            @Override
            public void onFailure(Call<List<FormModel>> call, Throwable t) {

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                Intent s = new Intent(onlineform.this, setting.class);
                startActivity(s);
                finish();
                return true;

            case R.id.home:

                Intent s1 = new Intent(onlineform.this, mainpage.class);
                startActivity(s1);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}


