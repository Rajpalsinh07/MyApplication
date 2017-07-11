package login1.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;
public class complaint extends AppCompatActivity {

    Button bstatus,bcomplain;
    LinearLayout l2;
    String TAG;
    String url = "http://192.168.42.168";
    ProgressDialog pd;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(complaint.this,mainpage.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        ActionBar actionbar=getSupportActionBar();
        actionbar.setLogo(R.drawable.images1);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);

        pd = new ProgressDialog(complaint.this);
        pd.setMessage("Please wait");
        pd.setCancelable(false);

        bstatus=(Button)findViewById(R.id.bstatus);
        bcomplain=(Button)findViewById(R.id.bcomplain);
        l2=(LinearLayout)findViewById(R.id.l2);
        bstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a=new Intent(complaint.this,viewstatus.class);
                startActivity(a);

            }

        });

        bcomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*
               Intent a=new Intent(complaint.this,compreg1.class);
                startActivity(a);*/

               Intent a=new Intent(complaint.this,Register_cmplent.class);
                startActivity(a);

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

                SharedPreferences myPrefs = getSharedPreferences("MY",
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.commit();
                AppState.getSingleInstance().setLoggingOut(true);
                Log.d(TAG, "Now log out and start the activity login");
                Intent intent = new Intent(complaint.this,
                        login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            case R.id.home:

                Intent s1=new Intent(complaint.this,mainpage.class);
                startActivity(s1);
                finish();
                return true;

             default:
                 return super.onOptionsItemSelected(item);
        }

    }
}
