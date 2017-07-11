package login1.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.ULocale;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class compreg1 extends AppCompatActivity implements Validator.ValidationListener {

    Spinner myspinner;
    Button bsubmit,bpic;
    int REQUEST_CODE=1;
    ImageView img;
    @NotEmpty
    EditText etLocation;
    @NotEmpty
    EditText etDetail;
    @NotEmpty
    @Length(min = 10,max = 10,message = "Invalid")
    EditText etMobile;



    String url = "http://192.168.42.168";
    ProgressDialog pd;
    public String com;


    Bitmap bitmap;
    Uri filepath;
    Validator validator;

    String imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compreg1);

        pd = new ProgressDialog(compreg1.this);
        pd.setMessage("Please wait");
        pd.setCancelable(false);

        validator=new Validator(this);
        validator.setValidationListener(this);


        ActionBar actionbar = getSupportActionBar();
        actionbar.setLogo(R.drawable.images1);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);

        myspinner = (Spinner) findViewById(R.id.spinner1);
        bsubmit = (Button) findViewById(R.id.bsubmit);
        bpic = (Button) findViewById(R.id.bpic);
        img = (ImageView) findViewById(R.id.img);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etDetail = (EditText) findViewById(R.id.etDetail);
        etMobile = (EditText) findViewById(R.id.etMobile);

        final String[] item ={"Garbage and Cleanliness","Mosquitoes and Mosquito born Diseases","Roads and Footpath","Water Supply,Drainage",
                "Street Light","Dead Animals","Food Safety Act","Hospital and Dispensaries","Door to Door Garbage Collection","Stray Animals"};

        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, item);

        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myadapter);

       myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               com = String.valueOf(parent.getItemAtPosition(position));
               Log.e(">>>>>>",com);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        bsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        bpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, REQUEST_CODE);

            }
        });



    }

    public void complaint(String com, String etLocation, String etDetail, String imageString, String etMobile) {



        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
        REGAPI services = retrofit.create(REGAPI.class);
        Call<String> call = services.complaint(com, etLocation, etDetail, imageString, etMobile);
        Log.e(">>>>>>",com);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                System.out.println("Done" + response.body().toString());
                Toast.makeText(compreg1.this, "Done"+response, Toast.LENGTH_SHORT).show();
                pd.dismiss();

                if (response.body().toString().equals("inserted")) {
 Intent i = new Intent(compreg1.this, mainpage.class);
                    startActivity(i);

                    Toast.makeText(compreg1.this, "hello", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Error" + t.toString());
                Toast.makeText(compreg1.this, "Hello"+t.toString(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(compreg1.this,mainpage.class);
        startActivity(i);
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

                Intent s= new Intent(compreg1.this,setting.class);
                startActivity(s);
                finish();
                return true;

            case R.id.home:

                Intent s1=new Intent(compreg1.this,mainpage.class);
                startActivity(s1);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onValidationSucceeded() {

        //Toast.makeText(compreg1.this, "Complaint Submitted", Toast.LENGTH_LONG).show();
        complaint(com,etLocation.getText().toString(),etDetail.getText().toString(),imageString,etMobile.getText().toString());
        //complaint();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        filepath = data.getData();
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);

            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] imageByte=baos.toByteArray();
            imageString= Base64.encodeToString(imageByte,Base64.DEFAULT);

            img.setVisibility(View.VISIBLE);
        }
    }
}

