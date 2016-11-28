package kr.hs.emirim.neon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.*;
import android.provider.*;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.OnColorSelectedListener;
import com.pavelsikun.vintagechroma.colormode.ColorMode;

import java.io.File;
import java.io.*;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;



/**
 * Created by 내컴퓨터 on 2016-10-31.
 */

public class WorkAcitivity extends AppCompatActivity {

    PhotoViewAttacher mAttacher;
 //   Button sizetblur;
    ImageButton write,neon,blur,color,font,save,share, neoncolor;
    ImageView pho;
    TextView text,textBlur ;
    private SeekBar sb;
    private int neonArea = 5;
    LinearLayout sbar;
    private int mColor;
    public static final int PICK_FROM_CAMERA = 0;
    public static final int PICK_FROM_ALBUM = 1;
    public static final int CROP_FROM_CAMERA = 2;
    Uri mImageCaptureUri;
    View rootView;
    static int blurCheck=0;
    int check;

    final CharSequence[] c_items = {"빨간색", "노란색", "파란색", "귤색"};
    int co;
    final static float STEP = 200;
    float mRatio = 1.0f;
    int mBaseDist;
    float mBaseRatio;
    int num;
    int c;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        pho = (ImageView) findViewById(R.id.back);
        write = (ImageButton) findViewById(R.id.btn_write);
        font = (ImageButton) findViewById(R.id.btn_font);
        color = (ImageButton) findViewById(R.id.btn_chcolor);
        neon = (ImageButton) findViewById(R.id.btn_neon);
        text = (TextView) findViewById(R.id.edit_write);
        save=(ImageButton)findViewById(R.id.btn_save);
        share=(ImageButton)findViewById(R.id.btn_share);
        neoncolor=(ImageButton)findViewById(R.id.btn_neoncolor);
       // tblur=(Button)findViewById(R.id.btn_tblur);
        blur=(ImageButton) findViewById(R.id.btn_blur);
        textBlur=(TextView) findViewById(R.id.edit_write);
        rootView=findViewById(R.id.content) ;
        sb = (SeekBar) findViewById(R.id.seekBar1);
        sb.setProgress(neonArea);
        sbar=(LinearLayout)findViewById(R.id.bar);
        sbar.setVisibility(View.INVISIBLE);


        text.setTextColor(Color.WHITE);
        if (S.check == 1) getPhotoFromCamera();
        else if (S.check == 2) getPhotoFromGallery();
      //  sb = (SeekBar) findViewById(R.id.seekBar1);
     //   sb.setProgress(neonArea);

     //   sbar.setVisibility(View.INVISIBLE);
        /*text.setOnTouchListener(new android.view.View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN :
                    case MotionEvent.ACTION_MOVE :
                    case MotionEvent.ACTION_UP   :
                        // 이미지 뷰의 위치를 옮기기
                        text.setX(event.getX());
                        text.setY(event.getY());
                }
                return true;
            }
        });*/
//        tblur.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v)
//            {
//                sbar.setVisibility(View.INVISIBLE);
////                textBlur.setText(text.getText().toString());
////                float radius =textBlur.getTextSize() /6;
////                BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL);
////                textBlur.getPaint().setMaskFilter(filter);
//                textBlur.setText(text.getText().toString());
//                /*if(Build.VERSION.SDK_INT>=11)
//                {
//                    text.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
//                }
//                float rad=text.getTextSize()/5;
//
//                BlurMaskFilter filter=new BlurMaskFilter(rad,BlurMaskFilter.Blur.NORMAL);
//                text.getPaint().setMaskFilter(filter);*/
//            }
//        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"페이스북 공유", "트위터 공유"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getLayoutInflater().getContext());
                alertDialogBuilder.setTitle("공유하기");
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
// 프로그램을 종료한다
                        Toast.makeText(getApplicationContext(),
                                items[id] + " 선택했습니다.",
                                Toast.LENGTH_SHORT).show();
                        if (id == 0) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///"+S.absoultePath));
                            PackageManager packManager = getBaseContext().getPackageManager();
                            List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                            boolean resolved = false;
                            for(ResolveInfo resolveInfo: resolvedInfoList) {
                                if(resolveInfo.activityInfo.packageName.startsWith("com.facebook.katana")){
                                    intent.setClassName(
                                            resolveInfo.activityInfo.packageName,
                                            resolveInfo.activityInfo.name );
                                    resolved = true;
                                    break;
                                }
                            }
                            if(resolved) {
                                startActivity(intent);
                            } else {
                                Toast.makeText(WorkAcitivity.this, "페이스북 앱이 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else if (id == 1) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///"+S.absoultePath));
                            intent.setPackage("com.twitter.android");
                            try {
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(WorkAcitivity.this, "트위터 앱이 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog1 = alertDialogBuilder.create();
                alertDialog1.show();

            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sbar.setVisibility(View.INVISIBLE);
                String filename = System.currentTimeMillis() + ".jpg";
                rootView.buildDrawingCache();
             //   Bitmap photo =view.getDrawingCache();

                rootView.setDrawingCacheEnabled(true);
                Bitmap photo = rootView.getDrawingCache();
                MakeCache(getBaseContext(),photo,filename);

            }

        });
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sbar.setVisibility(View.INVISIBLE);
                android.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getLayoutInflater().getContext());
                alertDialogBuilder.setTitle("문구 입력");
                final EditText name = new EditText(getBaseContext());
                alertDialogBuilder.setView(name);
                alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                      //  String username = name.getText().toString();
                        text.setText(name.getText().toString());
                        text.setTextSize(mRatio+13);
                     //   textBlur.setText(name.getText().toString());
                   //     textBlur.setTextSize(mRatio+13);
                    }

                });
                alertDialogBuilder.setNegativeButton("no",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alertDialogBuilder.show();
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sbar.setVisibility(View.INVISIBLE);
                new ChromaDialog.Builder()
                        .initialColor(Color.GREEN)
                        .colorMode(ColorMode.ARGB) // RGB, ARGB, HVS, CMYK, CMYK255, HSL
                        .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL; Note that (HSV || HSL || CMYK) && IndicatorMode.HEX is a bad idea
                        .onColorSelected(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(@ColorInt int color) {
                                //updateTextView(color);
                                text.setTextColor(color);
                            }
                        })
                        .create()
                        .show(getSupportFragmentManager(), "ChromaDialog");


            }
        });
        font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"나눔 고딕", "아리따 돋움", "맑은 고딕", "굴림","제주고딕","나눔펜","오성과 한음","AmaticSC-Bold","AmaticSC-Regular","Daum_Regular","Daum_SemiBold","Dynalight-Regular"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getLayoutInflater().getContext());
                alertDialogBuilder.setTitle("폰트 선택");
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
// 프로그램을 종료한다
                        Toast.makeText(getApplicationContext(),
                                items[id] + " 선택했습니다.",
                                Toast.LENGTH_SHORT).show();
                        if (id == 0) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "aGodic.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        } else if (id == 1) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "arita.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        } else if (id == 2) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "malgun.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        } else if (id == 3) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "gulim.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        }else if (id == 4) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "JejuGothic.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        }else if (id == 5) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "NanumPen.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        }else if (id == 6) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "OSeongandHanEum-Regular.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        }else if (id == 7) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "AmaticSC-Bold.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        }else if (id == 8) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "AmaticSC-Regular.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        }else if (id == 9) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "Daum_Regular.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        }else if (id == 10) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "Daum_SemiBold.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        }else if (id == 11) {
                            Typeface typeFace = Typeface.createFromAsset(getAssets(), "Dynalight-Regular.ttf");
                            text.setTypeface(typeFace);
                            textBlur.setTypeface(typeFace);

                        }

                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog1 = alertDialogBuilder.create();
                alertDialog1.show();
            }
        });
       neoncolor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                new ChromaDialog.Builder()
                        .initialColor(Color.GREEN)
                        .colorMode(ColorMode.ARGB) // RGB, ARGB, HVS, CMYK, CMYK255, HSL
                        .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL; Note that (HSV || HSL || CMYK) && IndicatorMode.HEX is a bad idea
                        .onColorSelected(new OnColorSelectedListener() {
                            @Override
                             public void onColorSelected(int color) {
                                //updateTextView(color);
                                c=color;
                               // text.setShadowLayer(num,0,0,color);

                            }
                        })
                        .create()
                        .show(getSupportFragmentManager(), "ChromaDialog");

            }
          /*          sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            setneonBlur(progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            //
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                }*/

        });
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 printfSelected(progress);
                //seek바의 값 가져와줌
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        neon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sbar.setVisibility(View.VISIBLE);

            }
        });
        blur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                blurCheck++;

                if (blurCheck % 2 == 1) {
                    textBlur.setText(text.getText().toString());
                    float radius = textBlur.getTextSize() / 3;
                    textBlur.getPaint().setMaskFilter(new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL));

                    float radius1 = text.getTextSize() / 30;
                    text.getPaint().setMaskFilter(new BlurMaskFilter(radius1, BlurMaskFilter.Blur.OUTER));
                } else {
                    textBlur.getPaint().setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL));
                    text.getPaint().setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.NORMAL));
                }
                rootView.setDrawingCacheEnabled(true);

            }

        });



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    private void getPhotoFromGallery() { // 갤러리에서 이미지 가져오기
        Intent intent = new Intent(Intent.ACTION_PICK);
        Log.d("#####갤러리####","성공");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM);
        Log.d("#####갤러리####","함수종료성공");

    }

    private void getPhotoFromCamera() { // 카메라 촬영 후 이미지 가져오기
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

// 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    public void printfSelected(int value) {
        neonArea=setneonArea(value);
        Log.d("",value+"d");

    }

    private int setneonArea(int value) {
        if(value>20){
            value=20;
        }
        neonArea = value;
        text.setShadowLayer(neonArea, 0, 0, c);
        textBlur.setShadowLayer(neonArea,0,0,c);
        //if (co == 0) {
        //text.setShadowLayer(neonArea, 0, 0,Color.RED);
        /*} else if (co == 1) {
            text.setShadowLayer(neonArea, 0, 0, Color.YELLOW);
        } else if (co == 2) {
            text.setShadowLayer(neonArea, 0, 0, Color.BLUE);
        } else if (co == 3) {
            text.setShadowLayer(neonArea, 0, 0, Color.parseColor("#F3F532"));
        }*/
       return neonArea;

    }
    private int xDelta,yDelta;
   public boolean onTouchEvent(MotionEvent event) {
       final int X = (int) event.getRawX();
       final int Y = (int) event.getRawY();
       switch (event.getAction() & MotionEvent.ACTION_MASK) {
           case MotionEvent.ACTION_DOWN:
               xDelta = (int) (X - text.getTranslationX());
               yDelta = (int) (Y - text.getTranslationY());
               break;
           case MotionEvent.ACTION_UP:
               break;
           case MotionEvent.ACTION_POINTER_DOWN:
               break;
           case MotionEvent.ACTION_POINTER_UP:
               break;
           case MotionEvent.ACTION_MOVE:
               text.setTranslationX(X - xDelta);
               text.setTranslationY(Y - yDelta);
               break;
       }

       if (event.getPointerCount() == 2) {
            int action = event.getAction();
            int pureaction = action & MotionEvent.ACTION_MASK;
            if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                mBaseDist = getDistance(event);
                mBaseRatio = mRatio;
            } else {
                float delta = (getDistance(event) - mBaseDist) / STEP;
                float multi = (float) Math.pow(2, delta);
                mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
                text.setTextSize(mRatio + 13);
                textBlur.setTextSize(mRatio+13);
            }
        }

        return true;
    }
    private void updateTextView(int color) {
        text.setText(String.format("#%06X", 0xFFFFFF & color));
    }
    int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }

    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.i("NR", mImageCaptureUri.getPath().toString());

// 이후의 처리가 카메라 부분과 같아 break 없이 진행
            }
            case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

// crop한 이미지를 저장할때 200x200 크기로 저장
//                intent.putExtra("outputX", 200); // crop한 이미지의 x축 크기
 //               intent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
 //               intent.putExtra("aspectX", 1); // crop 박스의 x축 비율
 //               intent.putExtra("aspectY", 1); // crop 박스의 y축 비율

 //               intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;
            }
            case CROP_FROM_CAMERA: {
                final Bundle extras = data.getExtras();
// crop된 이미지를 저장하기 위한 파일 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/" + System.currentTimeMillis();

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data"); // crop된 bitmap
                    pho.setImageBitmap(photo);
                    storeCropImage(photo, filePath+".jpg");
                    //0
                    //
                    // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()))); // 갤러리를 갱신하기 위해..
                  //  sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse(filePath)));
                }

                File file = new File(mImageCaptureUri.getPath());
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }
    private void storeCropImage(Bitmap bitmap, String filePath) {
        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // final Context thisContext = this.getApplicationContext();
    static public void MakeCache(Context context, Bitmap bitmap, String filename){

        String StoragePath =
                Environment.getExternalStorageDirectory().getAbsolutePath();
        String savePath = StoragePath + "/Pictures";
        File f = new File(savePath);
        if (!f.isDirectory())f.mkdirs();

        FileOutputStream fos;
        try{
            fos = new FileOutputStream(savePath+"/"+filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,fos);

        }catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(context,"저장이 성공하였습니다!",Toast.LENGTH_LONG).show();

    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("WorkAcitivity Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}