package com.example.hishara.mapapp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;


public class ImageUpload extends AppCompatActivity {

    private ImageView image;
    private static final int IMAGE_REQUEST_CODE = 10;
    private Button uploadButton;
    private Bitmap bitmap;
    private Button selectImageButton;

    // number of images to select
    private static final int PICK_IMAGE = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // find the views
        image = (ImageView) findViewById(R.id.uploadImage);
        uploadButton = (Button) findViewById(R.id.uploadButton);

        // on click select an image
        selectImageButton = (Button) findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImageFromGallery();

            }
        });

        // when uploadButton is clicked
        uploadButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new ImageUploadTask().execute();
            }
        });


    }


    public void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    /**
     * Retrives the result returned from selecting image, by invoking the method
     *
     selectImageFromGallery()

     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String picturePath = "";
            Log.v("************:", "image" + selectedImage);
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            bitmap=BitmapFactory.decodeFile(picturePath);
            image.setImageBitmap(bitmap);


        }
    }



    /**
     * The class connects with server and uploads the photo
     *
     *
     */
    class ImageUploadTask extends AsyncTask<Void, Void, String> {
        private String webAddressToPost = "http://guideme.orgfree.com/customer/image/";

        // private ProgressDialog dialog;
        private ProgressDialog dialog = new ProgressDialog(ImageUpload.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Uploading...");
            dialog.show();
        }

        //FTPClient con = null;

        @Override
        protected String doInBackground(Void... params) {
            try {

//                con = new FTPClient();
//                con.connect("192.168.2.57");
//
//                if (con.login("Administrator", "KUjWbk")){
//
//                }
                URL url = new URL(webAddressToPost);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");

                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.JPEG, 100, bos);
                byte[] data = bos.toByteArray();
                ByteArrayBody bab = new ByteArrayBody(data, "test.jpg");
                entity.addPart("file", bab);

                entity.addPart("someOtherStringToSend", new StringBody("your string here"));

                conn.addRequestProperty("Content-length", entity.getContentLength() + "");
                conn.addRequestProperty(entity.getContentType().getName(), entity.getContentType().getValue());

                OutputStream os = conn.getOutputStream();
                entity.writeTo(conn.getOutputStream());
                os.close();
                conn.connect();


                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return readStream(conn.getInputStream());
                }


            } catch (Exception e) {
                e.printStackTrace();
                // something went wrong. connection with the server error
            }
            return null;
        }


        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuilder builder = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "file uploaded",
                    Toast.LENGTH_LONG).show();
        }

    }


}
