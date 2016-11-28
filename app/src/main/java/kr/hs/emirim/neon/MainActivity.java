package kr.hs.emirim.neon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

import static android.R.attr.bitmap;
import static android.support.v7.appcompat.R.styleable.AlertDialog;

public class MainActivity extends Activity {

    ImageButton img;
    final int REQ_CODE_SELECT_IMAGE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageButton) findViewById(R.id.start);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplication(), WorkAcitivity.class);
                        startActivity(intent);
                        //doTakePhotoAction();
                        S.check=1;
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplication(), WorkAcitivity.class);
                        startActivity(intent);
                        //doTakeAlbumAction();
                        S.check=2;
                    }
                };
                DialogInterface.OnClickListener cancleListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                new AlertDialog.Builder(getLayoutInflater().getContext())
                        .setTitle("배경 이미지 선택")
                        .setPositiveButton("사진 촬영", cameraListener)
                        .setNeutralButton("앨범 선택", albumListener)
                        .setNegativeButton("취소", cancleListener)
                        .show();
            }
        });

    }


}
