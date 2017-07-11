package login1.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity implements Validator.ValidationListener {
    DatabaseHelper Helper = new DatabaseHelper(this);
    TextView tvreg;
    Button blogin;
    @NotEmpty
    EditText etName;
    @NotEmpty
    @Password(min = 6,scheme = Password.Scheme.ALPHA_NUMERIC,message = "enter min 8 digit pass")
    EditText etPassword;

    String url = "http://192.168.42.76";
    ProgressDialog pd;

    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        validator=new Validator(this);
        validator.setValidationListener(this);

        pd = new ProgressDialog(login.this);
        pd.setMessage("Please wait");
        pd.setCancelable(false);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setLogo(R.drawable.images1);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);

        tvreg = (TextView) findViewById(R.id.tvReg);
        blogin = (Button) findViewById(R.id.blogin);
        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validator.validate();
            }
        });

        tvreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerintent = new Intent(login.this, register1.class);
                startActivity(registerintent);
            }

        });

    }

    public void login(final String name, String password) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        REGAPI services = retrofit.create(REGAPI.class);
        Call<String> call = services.login(name, password);
        System.out.println(name + " " + password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().toString().equals("logged_in")) {
                    Intent i = new Intent(login.this, mainpage.class);
                   // Helper.insertContact(new contact(etName.getText().toString(),etPassword.getText().toString()));
                    startActivity(i);
                } else {
                    Toast.makeText(login.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                }

                pd.dismiss();
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

        String str = etName.getText().toString();
        String str1 = etPassword.getText().toString();

        login(str, str1);
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











