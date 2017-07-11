package login1.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;
import static android.R.attr.theme;

public class Register_cmplent extends AppCompatActivity implements Validator.ValidationListener{



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

    Validator validator;

    public String com;

    String url="http://192.168.42.168/Smart_Guj/services/complaint.php";

    Bitmap bitmap;
    Uri filepath;

    String imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cmplent);


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

               // new Reg().execute();
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

    public class Reg extends AsyncTask<String,String,String>{

        ProgressDialog pg=new ProgressDialog(Register_cmplent.this);
        String location=etLocation.getText().toString();
        String details=etDetail.getText().toString();
        String mobile=etMobile.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg.setMessage("Loding..");
            pg.show();
        }

        @Override
        protected String doInBackground(String... params) {

            StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(Register_cmplent.this, response, Toast.LENGTH_SHORT).show();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(Register_cmplent.this, error.toString() , Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    HashMap<String,String> p=new HashMap<String, String>();

                    p.put("Category",com);
                    p.put("Location",location);
                    p.put("Detail",details);
                    p.put("img",imageString);
                    p.put("Mobile",mobile);


                    return p;
                }
            };


            RequestQueue rq= Volley.newRequestQueue(Register_cmplent.this);
            rq.add(sr);


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pg.dismiss();
        }
    }



    @Override
    public void onValidationSucceeded() {

        Toast.makeText(Register_cmplent.this, "Complaint Submitted", Toast.LENGTH_LONG).show();
        new Reg().execute();

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
