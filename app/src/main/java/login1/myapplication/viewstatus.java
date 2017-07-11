package login1.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class viewstatus extends AppCompatActivity {

    private RecyclerView recyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private view_complain_Adapter v_c_adapter;
    ArrayList<complain_data> c_data;

    String url="http://192.168.42.165/Smart_Guj/services/view_complain.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstatus);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setLogo(R.drawable.images1);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);


        recyclerview=(RecyclerView)findViewById(R.id.recycler);
        recyclerview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);


        new view_complaint().execute();

    }

    private class view_complaint extends AsyncTask<String,String,String>{

        ProgressDialog pg=new ProgressDialog(viewstatus.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pg.setMessage("Loding..");
            pg.show();
        }

        @Override
        protected String doInBackground(String... params) {

            StringRequest sr =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject  jsonObject=new JSONObject(response);

                        if(jsonObject.getString("Status").equalsIgnoreCase("True")){

                            JSONArray jsonArray=jsonObject.getJSONArray("response");

                            c_data=new ArrayList<>();

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject obj=jsonArray.getJSONObject(i);
                                //Toast.makeText(viewstatus.this, obj.getString("id"), Toast.LENGTH_SHORT).show();

                                complain_data c=new complain_data();


                                c.setC_Name(obj.getString("Category"));
                                c.setDetails(obj.getString("Detail"));
                                c.setLocation(obj.getString("Location"));
                                c.setC_Image(obj.getString("img"));
                              //  Toast.makeText(viewstatus.this, obj.getString("img"), Toast.LENGTH_SHORT).show();
                                c.setStatus(obj.getString("Complaint_status"));
                                c_data.add(c);



                            }

                            adapter = new view_complain_Adapter(c_data,viewstatus.this);
                            recyclerview.setAdapter(adapter);
                        }
                        else {
                            Toast.makeText(viewstatus.this, "else", Toast.LENGTH_SHORT).show();
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(viewstatus.this, error.toString(), Toast.LENGTH_SHORT).show();


                }
            });


            RequestQueue rq= Volley.newRequestQueue(viewstatus.this);
            rq.add(sr);



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pg.dismiss();
        }
    }





}
