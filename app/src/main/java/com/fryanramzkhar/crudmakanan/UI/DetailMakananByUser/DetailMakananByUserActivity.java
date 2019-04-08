package com.fryanramzkhar.crudmakanan.UI.DetailMakananByUser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananData;
import com.fryanramzkhar.crudmakanan.R;
import com.fryanramzkhar.crudmakanan.Utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailMakananByUserActivity extends AppCompatActivity implements DetailMakananByUserContract.View {

    @BindView(R.id.img_picture)
    ImageView imgPicture;
    @BindView(R.id.fab_choose_picture)
    FloatingActionButton fabChoosePicture;
    @BindView(R.id.layoutPicture)
    CardView layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.spin_category)
    Spinner spinCategory;
    @BindView(R.id.layoutSaveMakanan)
    CardView layoutSaveMakanan;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private String namaFotoMakanan;
    private String[] mIdCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan_by_user);
        ButterKnife.bind(this);

        //Melakukan Pengecekan dan permission untuk bisa mengakses gallery
        PermissionGalery();

        //Menangkap id makanan yang dikirimkan dari activity sebelumnya
        idMakanan = getIntent().getStringExtra(Constant.KEY_EXTRA_ID_MAKANAN);

        //Menagmbil data Category untuk ditampilkan di spinner
        detailMakananByUserPresenter.getCategory();

        //Menyetting swipe refresh
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);

                //Mengambil data detail makanan
                detailMakananByUserPresenter.getDetailMakanan(idMakanan);

                //Menagmbil data Category untuk ditampilkan di spinner
                detailMakananByUserPresenter.getCategory();
            }
        });


    }

    private void PermissionGalery() {
        //Mengecek apakah user sudah mengizinkan unutk akses external Storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            // Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.STORAGE_PERMISSION_CODE);

    }

    private DetailMakananByUserPresenter detailMakananByUserPresenter = new DetailMakananByUserPresenter(this);
    private Uri filePath;
    private String idCategory, idMakanan;
    private MakananData mMakananData;



    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);

    }

    @Override
    public void showDetailMakanan(MakananData makananData) {
        //Mengambil semua detail makanan
        mMakananData = makananData;

        //Mengambil NamaFotoMakanan
        namaFotoMakanan = makananData.getFotoMakanan();

        //mengambil idCategory
        idCategory = makananData.getIdKategori();

        //menampilkan semua data ke layar
        edtName.setText(makananData.getNamaMakanan());
        edtDesc.setText(makananData.getDescMakanan());

        //memilih spinner sesuai dengan category makananyang ada di dalam database
        for (int i = 0 ;i < mIdCategory.length; i++){

            Log.i("cek", "isi loop mIdCategory: " + mIdCategory[i]);

            if (Integer.valueOf(mIdCategory[i]).equals(Integer.valueOf(idCategory))){
                spinCategory.setSelection(i);

                Log.i("cek", "isi select mIdCategory: " + mIdCategory[i]);
                Log.i("cek", "isi select idCategory: " + idCategory);

            }
        }



        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
        Glide.with(this).load(makananData.getUrlMakanan()).apply(options).into(imgPicture);

    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successDelete() {
        finish();
    }

    @Override
    public void successUpdate() {
        detailMakananByUserPresenter.getCategory();
    }

    @Override
    public void showSpinnerCategory(final List<MakananData> categoryDataList) {
        //Membuat data penampung untuk Spinner
        List<String> listSpinner = new ArrayList<>();

        String [] namaCategory = new String[categoryDataList.size()];
        mIdCategory = new String[categoryDataList.size()];


        listSpinner.clear();
        for (int i = 0; i < categoryDataList.size(); i++){

            namaCategory[i] = categoryDataList.get(i).getNamaKategori();
            mIdCategory[i] = categoryDataList.get(i).getIdKategori();

            Log.i("cek", "isi show namaCategory: " + namaCategory[i]);
            Log.i("cek", "isi show mIDCategory: " + mIdCategory[i]);

        }

        //Membuat Adapter Spinner
        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, namaCategory);
        //Setting untuk menampilkan spinner dengan 1 line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //Memasukkan Adapter ke Spinner
        spinCategory.setAdapter(categorySpinnerAdapter);


        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Mengambil idKategori sesuai dengan pilihan user
                idCategory = categoryDataList.get(position).getIdKategori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Mengambil data detail makanan
        detailMakananByUserPresenter.getDetailMakanan(idMakanan);

    }

    @OnClick({R.id.fab_choose_picture, R.id.btn_update, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                //Mengambil gambar dari storage
                ShowFileChooser();
                break;
            case R.id.btn_update:
                detailMakananByUserPresenter.updateDataMakanan(this, filePath, edtName.getText().toString(), edtDesc.getText().toString(), idCategory, namaFotoMakanan, idMakanan);
                break;
            case R.id.btn_delete:
                detailMakananByUserPresenter.deleteMakanan(idMakanan, namaFotoMakanan);
                break;
        }
    }

    private void ShowFileChooser() {
        //Membuat Objek Intent
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGallery, "Select Picture"), Constant.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){

            //mengambil data foto dan memasukkan ke dalam variable filePath
            filePath = data.getData();
            try{
                //Mengambil data gambar lalu di convert ke Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    //Tampilkan gambar yang baru dipilih lewat layar
                    imgPicture.setImageBitmap(bitmap);


            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

}
