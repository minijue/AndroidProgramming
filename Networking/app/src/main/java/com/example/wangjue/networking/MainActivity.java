package com.example.wangjue.networking;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    final private int REQUEST_INTERNET = 123;
    ImageView img;

    private InputStream OpenHttpConnetion(String urlString) throws IOException {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            throw new IOException("Error connecting");
        }

        return in;
    }

    private Bitmap DownloadImage(String URL) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnetion(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bitmap;
    }

    private String DownloadText(String URL) {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnetion(URL);
        } catch (IOException ex) {
            return "";
        }

        InputStreamReader reader = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = reader.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException ex) {
            return "";
        }
        return str;
    }

    private String WordDefinition(String word) {
        InputStream in = null;
        String strDefinition = "";
        try {
            in = OpenHttpConnetion("http://services.aonaware.com/DictService/DictService.asmx/Define?word=" + word);
            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            try {
                db = dbf.newDocumentBuilder();
                doc = db.parse(in);
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            doc.getDocumentElement().normalize();

            NodeList definitionElements = doc.getElementsByTagName("Definition");
            for (int i = 0; i < definitionElements.getLength(); i++) {
                Node itemNode = definitionElements.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element definitionElement = (Element) itemNode;
                    NodeList wordDefinitionElements = definitionElement.getElementsByTagName("WordDefinition");
                    strDefinition = "";
                    for (int j = 0; j < wordDefinitionElements.getLength(); j++) {
                        Element wordDefinitionElement = (Element) wordDefinitionElements.item(j);
                        NodeList textNodes = wordDefinitionElement.getChildNodes();
                        strDefinition += textNodes.item(0).getNodeValue() + ".\n";
                    }
                }
            }
        } catch (IOException ex) {

        }
        return strDefinition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},
                    REQUEST_INTERNET);
        } else {
            new DownloadImageTask().execute("http://xw.ecjtu.jx.cn/_upload/tpl/00/68/104/template104/images/head.jpg");
            new DownloadTextTask().execute("http://www.ecjtu.jx.cn/2018/0615/c275a58432/page.htm");
            new AccessWebServiceTask().execute("apple");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_INTERNET:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new DownloadImageTask().execute("http://xw.ecjtu.jx.cn/_upload/tpl/00/68/104/template104/images/head.jpg");
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView img = findViewById(R.id.imageView);
            img.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            return DownloadImage(urls[0]);
        }
    }

    private class DownloadTextTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            TextView tv = findViewById(R.id.textView);
            tv.setText(s);
        }
    }

    private class AccessWebServiceTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return WordDefinition(strings[0]);
        }
    }
}