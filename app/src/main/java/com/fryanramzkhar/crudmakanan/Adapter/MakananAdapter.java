package com.fryanramzkhar.crudmakanan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fryanramzkhar.crudmakanan.Model.Makanan.MakananData;
import com.fryanramzkhar.crudmakanan.R;
import com.fryanramzkhar.crudmakanan.UI.DetailMakanan.DetailMakananActivity;
import com.fryanramzkhar.crudmakanan.UI.DetailMakananByUser.DetailMakananByUserActivity;
import com.fryanramzkhar.crudmakanan.UI.MakananByKategori.MakananByKategoriActivity;
import com.fryanramzkhar.crudmakanan.Utils.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {

    //TYPE 1 Makanan Baru
    public static final int TYPE_1 = 1;
    //TYPE 2 Makanan Populer
    public static final int TYPE_2 = 2;
    //TYPE 3 Kategori Makanan
    public static final int TYPE_3 = 3;
    //TYPE 4 Makanan By Kategori
    public static final int TYPE_4 = 4;
    //TYPE 5 Makanan By User
    public static final int TYPE_5 = 5;


    Integer viewType;
    private final Context context;
    private final List<MakananData> makananDataList;


    public MakananAdapter(Integer viewType, Context context, List<MakananData> makananDataList) {
        this.viewType = viewType;
        this.context = context;
        this.makananDataList = makananDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case TYPE_1:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_news, null);
                return new FoodNewsViewHolder(view);

            case TYPE_2:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_populer, null);
                return new FoodPopulerViewHolder(view);

            case TYPE_3:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_kategory, null);
                return new FoodKategoriViewHolder(view);

            case TYPE_4:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_by_kategory, null);
                return new FoodNewsViewHolder(view);

            case TYPE_5:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_by_kategory, null);
                return new FoodByUserViewHolder(view);

            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //Mengambil data lalau memasukkan data ke model
        final MakananData makananData = makananDataList.get(i);


        //Mengambil view type dari user atau constractor
        int mViewType = viewType;
        switch (mViewType) {
            case TYPE_1:
                FoodNewsViewHolder foodNewsViewHolder = (FoodNewsViewHolder) viewHolder;

                //RequestOption untuk Error dan placeHolder gmabar
                RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).into(foodNewsViewHolder.imgMakanan);

                //Menampilkan title dan jumlah View
                foodNewsViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder.txtView.setText(makananData.getView());

                //Menampilkan Data Time
                foodNewsViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));


                foodNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
                break;
            case TYPE_2:
                FoodPopulerViewHolder foodPopulerViewHolder = (FoodPopulerViewHolder) viewHolder;
                RequestOptions options2 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).into(foodPopulerViewHolder.imgMakanan);

                foodPopulerViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodPopulerViewHolder.txtView.setText(makananData.getView());

                //Menampilkan Data Time
                foodPopulerViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));

                foodPopulerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));

                    }
                });
                break;
            case TYPE_3:
                FoodKategoriViewHolder foodKategoriViewHolder = (FoodKategoriViewHolder) viewHolder;
                RequestOptions options3 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).into(foodKategoriViewHolder.image);

                foodKategoriViewHolder.txtNamaKategory.setText(makananData.getNamaKategori());
                foodKategoriViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("Cek Id Kategori Adapter", "onClick: " + makananData.getIdKategori());
                        context.startActivity(new Intent(context, MakananByKategoriActivity.class).putExtra(Constant.KEY_EXTRA_ID_CATEGORY, makananData.getIdKategori()));

                    }
                });
                break;

            case TYPE_4:
                FoodNewsViewHolder foodNewsViewHolder2 = (FoodNewsViewHolder) viewHolder;

                //RequestOption untuk Error dan placeHolder gmabar
                RequestOptions options4 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).into(foodNewsViewHolder2.imgMakanan);

                //Menampilkan title dan jumlah View
                foodNewsViewHolder2.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder2.txtView.setText(makananData.getView());

                //Menampilkan Data Time
                foodNewsViewHolder2.txtTime.setText(newDate(makananData.getInsertTime()));


                foodNewsViewHolder2.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Berpindah Halaman ke detailMakanan
                        context.startActivity(new Intent(context, DetailMakananActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
                break;

            case TYPE_5:
                FoodByUserViewHolder foodByUserViewHolder = (FoodByUserViewHolder) viewHolder;

                //RequestOption untuk Error dan placeHolder gmabar
                RequestOptions options5 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options5).into(foodByUserViewHolder.imgMakanan);

                //Menampilkan title dan jumlah View
                foodByUserViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodByUserViewHolder.txtView.setText(makananData.getView());

                //Menampilkan Data Time
                foodByUserViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));


                foodByUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Berpindah Halaman ke detailMakanan
                        context.startActivity(new Intent(context, DetailMakananByUserActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
                break;
        }

    }

    private String newDate(String insertTime) {
        //Membuat variable penampung tanggal
        Date date = null;
        //Membuat penampung date dengan format yang baru
        String newDate = insertTime;

        //Membuat format sesuai dengan tanggal yang sudah dimiliki
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Mengubah tanggal yang dimiliki menjadi tipe date
        try {
            date = sdf.parse(insertTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Mengecek format date yang dimiliki sesuai dengan yang diminta
        if (date != null) {
            //Mengubah date yang dimiliki menjadi format date yang baru
            newDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(date);
        }
        return newDate;
    }

    @Override
    public int getItemCount() {
        return makananDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class FoodNewsViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodNewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FoodPopulerViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodPopulerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }

    public class FoodKategoriViewHolder extends ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.txt_nama_kategory)
        TextView txtNamaKategory;

        public FoodKategoriViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }

    public class FoodByUserViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        public FoodByUserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }
}
