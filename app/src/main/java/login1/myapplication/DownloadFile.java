package login1.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Moksha on 4/17/2017.
 */
public class DownloadFile  extends AsyncTask<String, Void, Void>
    {
        private ProgressDialog pDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showpDialog();
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];
            // -> https://letuscsolutions.files.wordpress.com/2015/07/five-point-someone-chetan-bhagat_ebook.pdf
            String fileName = strings[1];
            // ->five-point-someone-chetan-bhagat_ebook.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "PDF DOWNLOAD");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            try {
                FileDownloader.downloadFile(fileUrl, pdfFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hidepDialog();
            //    Toast.makeText(getApplicationContext(), "Download PDf successfully", Toast.LENGTH_SHORT).show();

            Log.d("Download complete", "----------");
        }

        private void hidepDialog() {
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        }


