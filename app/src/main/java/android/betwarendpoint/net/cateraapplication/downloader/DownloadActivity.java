package android.betwarendpoint.net.cateraapplication.downloader;

import android.Manifest;
import android.betwarendpoint.net.cateraapplication.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

public class DownloadActivity extends AppCompatActivity  implements Downloader.Listener {
    private Downloader downloader;
    static final Uri URL_APK  = Uri.parse("https://www.contal.biz/ozpol/app-release.apk");


    TextView progressText;
    ProgressBar prBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        downloader = Downloader.newInstance(this);
        progressText = findViewById(R.id.tex_pr);
        prBar = findViewById(R.id.pr_bar);

        checkPErmissions();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void downloadOrCancel() {
        if (downloader.isDownloading()) {
            cancel();
        } else {
            download();
        }
        //  updateUi();
    }

    private void cancel() {
        downloader.cancel();
    }

    private void download() {
        //   Uri uri = Uri.parse(url);
        downloader.download(URL_APK);
    }


    private void checkPErmissions(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    12);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    13);

        } else {
            downloadOrCancel();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 12:
            case 13: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // get locations
                    downloadOrCancel();
                } else {
                    //Close it
                    finish();
                }
            }
            break;
        }
    }

    @Override
    public void fileDownloaded(Uri uri, String mimeType) {

        File file =  new File(uri.getEncodedPath());

        MimeTypeMap map = MimeTypeMap.getSingleton();
        String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
        String type = map.getMimeTypeFromExtension(ext);

        if (type == null)
            type = "*/*";

        Intent intent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent =   new Intent(Intent.ACTION_INSTALL_PACKAGE);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else {
            intent =   new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }


        intent.setDataAndType(uri, type);

        startActivity(intent);
    }

    @Override
    public void getProcess(int total, int byNow) {
           int result = (byNow * 100) / total;
          runOnUiThread(()-> {
              progressText.setText("Prgress:" + result);
              prBar.setMax(100);
              prBar.setProgress(result);
          });
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
