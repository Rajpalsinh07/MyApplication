package login1.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class register1 extends AppCompatActivity implements Validator.ValidationListener {

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this,login.class);
        startActivity(i);
        super.onBackPressed();
    }

    DatabaseHelper Helper;
    @NotEmpty
    EditText etName;
    @NotEmpty
    @Length(min = 10,max = 10,message = "Invalid Length")
    EditText etPhnNo;
    @NotEmpty
    @Email()
    EditText etEmailId;
    @NotEmpty
    @Password(min = 6,scheme = Password.Scheme.ALPHA_NUMERIC,message = "enter min 8 digit pass")
    EditText etPassword;
    @NotEmpty
    @ConfirmPassword
    EditText etConfirmPassword;

    Button bregister;
    contact cn;

    String url = "http://192.168.42.76";
    ProgressDialog pd;

    Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        pd = new ProgressDialog(register1.this);
        pd.setMessage("Please wait");
        pd.setCancelable(false);

        validator=new Validator(this);
        validator.setValidationListener(this);

        cn = new contact();
        Helper = new DatabaseHelper(this);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setLogo(R.drawable.images1);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);


        etName = (EditText) findViewById(R.id.etName);
        etPhnNo = (EditText) findViewById(R.id.etPhnNo);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        bregister = (Button) findViewById(R.id.bRegister);

        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validator.validate();

            }
        });

    }

    public void register1(String name, String phnno, String emailid, String password) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        REGAPI services = retrofit.create(REGAPI.class);
        Call<String> call = services.register1(name, phnno, emailid, password);
        System.out.println(name + " " + password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                System.out.println("Done" + response.body().toString());
                pd.dismiss();

                if (response.body().toString().equals("inserted")) {

                    Log.e(">>>", etName.getText().toString() + ":" + etPhnNo.getText().toString() + ":" + etEmailId.getText().toString() + ":" + etPassword.getText().toString());

                    Helper.insertContact(etName.getText().toString(), etPhnNo.getText().toString(), etEmailId.getText().toString(), etPassword.getText().toString());

                    Intent i = new Intent(register1.this, login.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Error" + t.toString());
                pd.dismiss();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {

        String Namestr = etName.getText().toString();
        String PhnNostr = etPhnNo.getText().toString();
        String EmailIdstr = etEmailId.getText().toString();
        String Passwordstr = etPassword.getText().toString();
        String ConfirmPasswordstr = etConfirmPassword.getText().toString();

        if (!Passwordstr.equals(ConfirmPasswordstr)) {
            Toast.makeText(register1.this, "password dont match!", Toast.LENGTH_SHORT).show();


        } else {
                    /*contact c = new contact();
                    c.setname(Namestr);
                    c.setphnno(PhnNostr);
                    c.setemailid(EmailIdstr);
                    c.setPassword(Passwordstr);
                    Helper.insertContact(c);*/

                   /* Intent regintent = new Intent(register1.this, mainpage.class);
                    startActivity(regintent);
                    finish();*/
            register1(Namestr, PhnNostr, EmailIdstr, Passwordstr);



        }
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
}




















