package login1.myapplication;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moksha on 4/13/2017.
 */
public class FormAdapter extends RecyclerView.Adapter<FormAdapter.MyViewHolder> {

    private ArrayList<FormModel> form;
    String pdfdata;

    float per = 0;
    private Context context;


    public FormAdapter(ArrayList<FormModel> formlist, Context applicationContext) {
        this.form = formlist;
        //this.context = context;
        this.context = applicationContext;
        Log.e(">>>>>>>", "Adepter");
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, pdf;

        //`public TextView no;
        // public LinearLayout ll1;
        public MyViewHolder(View view) {
            super(view);
            //       ll1 = (LinearLayout) view.findViewById(R.id.card_view);
            name = (TextView) view.findViewById(R.id.name1);
            pdf = (TextView) view.findViewById(R.id.pdf);
            // no=(TextView) view.findViewById(R.id.no);
        }
    }

    @Override
    public FormAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_formrow, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final FormAdapter.MyViewHolder holder, final int position) {


        holder.name.setText(form.get(position).getName());
        holder.pdf.setText(form.get(position).getForms());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfdata = holder.pdf.getText().toString();
                downloadPDF(form.get(position).getForms());

            }
        });
    }

    public void downloadPDF(String pdfname) {
        Log.e("PDFNAME>>", "http://192.168.42.168/Smart_Guj/certi/" + pdfname);
        new DownloadFile().execute("http://192.168.42.168/Smart_Guj/certi/" + pdfname, pdfname);

    }

    public void viewPDF() {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/PDFDOWNLOAD/" + pdfdata);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            context.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            //  Toast.makeText(FormAdapter.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }



    private class DownloadFile extends AsyncTask<String, Void, Void> {

        ProgressDialog pDialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Downloading...");
            pDialog.show();
        }


        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];
            String fileName = strings[1];
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "PDFDOWNLOAD");
            //folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileDownloader.downloadFile(fileUrl, pdfFile);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            viewPDF();
            Log.d("Download complete", "----------");
        }

    }


    @Override
    public int getItemCount() {
        return form.size();
    }
}

