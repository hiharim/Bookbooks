package com.harimi.bookbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditMemoActivity extends AppCompatActivity {
    /** 메모 편집 클래스
     * 인텐트 전달받고
     * 이미지 수정할수있고(이미지뷰 클릭리스너,카메라,갤러리접근가능하게)
     * 수정된 날짜로 인텐트 다시날리기
     *
     * */

    EditMemoActivity editMemoActivity=this;

    private ArrayList<MemoData> arrayList;
    private MemoAdapter memoAdapter;
    private RecyclerView recyclerView;


    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;

    private String mCurrentPhotoPath;
    private ImageView imageView;
    private Uri imageUri;
    private Uri photoURI, albumURI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);



//        final EditText editpage=(EditText)findViewById(R.id.activity_edit_memo_pagenum_et);
//        final EditText editline=(EditText)findViewById(R.id.activity_edit_memo_line_et);
//        final EditText editmemo=(EditText)findViewById(R.id.activity_edit_memo_content_et);
//        final ImageView editpicture=(ImageView)findViewById(R.id.activity_edit_memo_picture_iv);
//
//        Intent intent=getIntent();
//        String pageedit=intent.getExtras().getString("editpage");
//        editpage.setText(pageedit);
//
//        String lineedit=intent.getExtras().getString("editline");
//        editline.setText(lineedit);
//
//        String memoedit=intent.getExtras().getString("editmemo");
//        editmemo.setText(memoedit);
//
//        String pictureedit=intent.getExtras().getString("editpicture");
//        editpicture.setImageURI(Uri.parse(pictureedit));








        //온바인드뷰홀더에서 아이템 클릭했을때 인텐트받는거

//        final EditText updatepage=(EditText)findViewById(R.id.activity_edit_memo_pagenum_et);
//        final EditText updateline=(EditText)findViewById(R.id.activity_edit_memo_line_et);
//        final EditText updatememo=(EditText)findViewById(R.id.activity_edit_memo_content_et);
//        final ImageView updatepicture=(ImageView)findViewById(R.id.activity_edit_memo_picture_iv);
//
//
//        Intent intent=getIntent();
//
//        String line=intent.getExtras().getString("ItemLine");
//        updateline.setText(line);
//        String page=intent.getExtras().getString("ItemPage");
//        updatepage.setText(page);
//        String memo=intent.getExtras().getString("ItemMemo");
//        updatememo.setText(memo);
//
//        final String picture=intent.getExtras().getString("ItemPicture");
//        final Uri pictureUri=Uri.parse(picture);
//        updatepicture.setImageURI(pictureUri);

//-----------------------------------------------------------------------------------------------------

//        final EditText updatepage=(EditText)findViewById(R.id.activity_edit_memo_pagenum_et);
//        final EditText updateline=(EditText)findViewById(R.id.activity_edit_memo_line_et);
//        final EditText updatememo=(EditText)findViewById(R.id.activity_edit_memo_content_et);
//        final ImageView updatepicture=(ImageView)findViewById(R.id.activity_edit_memo_picture_iv);
//
//
//        Intent intent=getIntent();
//
//        String line=intent.getExtras().getString("수정Line");
//        updateline.setText(line);
//        String page=intent.getExtras().getString("수정Page");
//        updatepage.setText(page);
//        String memo=intent.getExtras().getString("수정Memo");
//        updatememo.setText(memo);
//
//        final String picture=intent.getExtras().getString("수정Picture");
//        final Uri pictureUri=Uri.parse(picture);
//        updatepicture.setImageURI(pictureUri);

//-----------------------------------------------------------------------------------------------------

        /**제발도전*/

        final EditText updatepage=(EditText)findViewById(R.id.activity_edit_memo_pagenum_et);
        final EditText updateline=(EditText)findViewById(R.id.activity_edit_memo_line_et);
        final EditText updatememo=(EditText)findViewById(R.id.activity_edit_memo_content_et);
        final ImageView updatepicture=(ImageView)findViewById(R.id.activity_edit_memo_picture_iv);


        Intent intent=getIntent();

        String line=intent.getExtras().getString("제발line");
        updateline.setText(line);
        String page=intent.getExtras().getString("제발page");
        updatepage.setText(page);
        String memo=intent.getExtras().getString("제발memo");
        updatememo.setText(memo);

        final String picture=intent.getExtras().getString("제발picture");
        final Uri pictureUri=Uri.parse(picture);
        updatepicture.setImageURI(pictureUri);

















        //이미지뷰 클릭해서 카메라,갤러리 접근해서 사진 수정 가능하게
        imageView=(ImageView)findViewById(R.id.activity_edit_memo_picture_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(editMemoActivity);
                builder.setTitle("업로드할 이미지 선택");
                builder.setPositiveButton("카메라로 사진찍기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        captureCamera();
                    }
                });


                builder.setNeutralButton("앨범에서 사진선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getAlbum();
                    }
                });

                builder.show();
                checkPermission();
            }
        });



        /**편집 완료 버튼*
         * 수정된 데이터 DetailMemoActivity.class로 전달
         * @param : 페이지,밑줄,사진,메모,날짜
         * 수정된 데이터 리사이클러뷰에 뿌려주기
         *
         */

//        Button finishbtn=(Button)findViewById(R.id.activity_edit_memo_finish_btn);
////        finishbtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent data=new Intent(getApplicationContext(),DetailMemoActivity.class);
//////                EditText etPage=(EditText)findViewById(R.id.activity_edit_memo_pagenum_et);
//////                data.putExtra("EditPage",etPage.getText().toString());
//////
//////                EditText etLine=(EditText)findViewById(R.id.activity_edit_memo_line_et);
//////                data.putExtra("EditLine",etLine.getText().toString());
//////
//////                EditText etMemo=(EditText)findViewById(R.id.activity_edit_memo_content_et);
//////                data.putExtra("EditMemo",etMemo.getText().toString());
//////
//////                Date date=new Date();
//////                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//////                String substr=sdf.format(date);
//////                data.putExtra("EditDate",substr);
//////
//////                imageView=(ImageView)findViewById(R.id.activity_edit_memo_picture_iv);
//////                data.putExtra("EditImageUri",mCurrentPhotoPath);
//////
//////                //setResult에 intent를 넣어주면 onActivityResult에서 이 intent를 받는다
//////                setResult(RESULT_OK,data);
//////                finish();
////
////            }
////        });



        Button finishbtn=(Button)findViewById(R.id.activity_edit_memo_finish_btn);
        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data=new Intent(getApplicationContext(),DetailBookActivity.class);
                EditText etPage=(EditText)findViewById(R.id.activity_edit_memo_pagenum_et);
                data.putExtra("수정Page",etPage.getText().toString());

                EditText etLine=(EditText)findViewById(R.id.activity_edit_memo_line_et);
                data.putExtra("수정Line",etLine.getText().toString());

                EditText etMemo=(EditText)findViewById(R.id.activity_edit_memo_content_et);
                data.putExtra("수정Memo",etMemo.getText().toString());

                Date date=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String substr=sdf.format(date);
                data.putExtra("수정Date",substr);

                imageView=(ImageView)findViewById(R.id.activity_edit_memo_picture_iv);
                data.putExtra("수정ImageUri",mCurrentPhotoPath);

                Log.e("데이터보낸다","수정액티비티"+etPage+etLine+etMemo+etPage+substr);
                //setResult에 intent를 넣어주면 onActivityResult에서 이 intent를 받는다
                setResult(RESULT_OK,data);
                finish();

            }
        });













        /**취소버튼*/
        Button cancelbtn=(Button)findViewById(R.id.activity_edit_memo_cancel_btn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }//onCreate




    private void captureCamera(){
        String state = Environment.getExternalStorageState();
        // 외장 메모리 검사
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e("captureCamera Error", ex.toString());
                }
                if (photoFile != null) {
                    // getUriForFile의 두 번째 인자는 Manifest provier의 authorites와 일치해야 함

                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    imageUri = providerURI;

                    // 인텐트에 전달할 때는 FileProvier의 Return값인 content://로만!!, providerURI의 값에 카메라 데이터를 넣어 보냄
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        } else {
            Toast.makeText(this, "저장공간이 접근 불가능한 기기입니다", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "gyeom");

        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdirs();
        }

        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }


    private void getAlbum(){
        Log.i("getAlbum", "Call");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    private void galleryAddPic(){
        Log.i("galleryAddPic", "Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    // 카메라 전용 크랍
    public void cropImage(){
        Log.i("cropImage", "Call");
        Log.i("cropImage", "photoURI : " + photoURI + " / albumURI : " + albumURI);

        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        // 50x50픽셀미만은 편집할 수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        //cropIntent.putExtra("outputX", 200); // crop한 이미지의 x축 크기, 결과물의 크기
        //cropIntent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
        cropIntent.putExtra("aspectX", 1); // crop 박스의 x축 비율, 1&1이면 정사각형
        cropIntent.putExtra("aspectY", 1); // crop 박스의 y축 비율
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumURI); // 크랍된 이미지를 해당 경로에 저장
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Log.i("REQUEST_TAKE_PHOTO", "OK");
                        galleryAddPic();

                        imageView.setImageURI(imageUri);
                    } catch (Exception e) {
                        Log.e("REQUEST_TAKE_PHOTO", e.toString());
                    }
                } else {
                    Toast.makeText(EditMemoActivity.this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK) {

                    if (data.getData() != null) {
                        try {
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);
                            cropImage();
                        } catch (Exception e) {
                            Log.e("TAKE_ALBUM_SINGLE ERROR", e.toString());
                        }
                    }
                }
                break;

            case REQUEST_IMAGE_CROP:
                if (resultCode == Activity.RESULT_OK) {

                    galleryAddPic();
                    imageView.setImageURI(albumURI);
                }
                break;
        }
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        Toast.makeText(EditMemoActivity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서

                break;
        }
    }



}//class