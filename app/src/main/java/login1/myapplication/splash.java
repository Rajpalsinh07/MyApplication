package login1.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.widget.Toast;

public class splash extends AppCompatActivity {

    private static int splash_time_Out = 3000;
    Cursor c = null;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        db = new DatabaseHelper(this);

        Toast.makeText(getApplicationContext(), "" + db.countUser(), Toast.LENGTH_LONG).show();

        Log.e(">>>>>>>>>>>>", "" + db.countUser());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /*Intent i=new Intent(splash.this,mainpage.class);
                startActivity(i);
                finish();*/


                if (db.countUser() > 0) {
                    Intent i = new Intent(splash.this, mainpage.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent main = new Intent(splash.this, login.class);
                    startActivity(main);
                    finish();

                }

                System.out.println("user count" + db.countUser());

            }
        }, splash_time_Out);

    }
}
