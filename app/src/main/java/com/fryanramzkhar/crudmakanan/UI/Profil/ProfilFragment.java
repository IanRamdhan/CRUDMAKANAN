package com.fryanramzkhar.crudmakanan.UI.Profil;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.fryanramzkhar.crudmakanan.Model.Login.LoginData;
import com.fryanramzkhar.crudmakanan.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment implements ProfilContract.View {


    @BindView(R.id.picture)
    CircleImageView picture;
    @BindView(R.id.fabChoosePic)
    FloatingActionButton fabChoosePic;
    @BindView(R.id.layoutPicture)
    RelativeLayout layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_no_telp)
    EditText edtNoTelp;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.layoutProfil)
    CardView layoutProfil;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.layoutJenkel)
    CardView layoutJenkel;
    Unbinder unbinder;

    //TODO 1 membuat variable yang dibutuhkan
    private ProfilPresenter profilPresenter = new ProfilPresenter(this);
    private String idUser, nama, alamat, noTelp;
    private int gender;
    private Menu action;

    private String mGender;
    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;
    private ProgressDialog progressDialog;


    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        unbinder = ButterKnife.bind(this, view);

        //Cara membuat OptionMenu di Fragment
        setHasOptionsMenu(true);

        //MenSetUp spinner
        setupSpinner();

        //Mereqquest data yang dikerjakan oleh Presenter
        profilPresenter.getDataUser(getContext());
        return view;
    }

    private void setupSpinner() {
        //Membuat Adapter Spinner
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.array_gender_option, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        //Memasukkan adapter spinner ke dalam widget spinner kita
        spinGender.setAdapter(genderSpinnerAdapter);

        //Listener Spinner
        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Mengambil posisi Item yang dipilih
                String selection = (String) parent.getItemAtPosition(position);
                //Mengecek position apakah ada isinya
                if (!TextUtils.isEmpty(selection)){
                    //Mengecek apakah 1 atau 2 yang dipilh user
                    if (selection.equals(getString(R.string.gender_male))){
                        mGender = "L";
                    }else if (selection.equals(getString(R.string.gender_female))){
                        mGender = "P";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Saving....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();

    }

    @Override
    public void showSuccessUpdateUser(String msg) {
        Toast.makeText(getContext(),msg , Toast.LENGTH_SHORT).show();
        profilPresenter.getDataUser(getContext());
    }

    @Override
    public void showDataUser(LoginData loginData) {
        readMode();

        //Memasukkan data yang sudah di ambil oleh Presenter
        idUser = loginData.getId_user();
        nama = loginData.getUsername();
        alamat = loginData.getAlamat();
        noTelp = loginData.getNo_telp();
        if (loginData.getJenkel().equals("L")){
            gender = 1;
        }else {
            gender = 2;
        }


        if (!TextUtils.isEmpty(idUser)){
            readMode();
            //MengeSet nama title actionBar
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profil" + nama);
            edtName.setText(nama);
            edtAlamat.setText(alamat);
            edtNoTelp.setText(noTelp);
            //Mengecek Gender dan memilih sesuai gender untuk ditampilkan pada spinner
            switch (gender){
                case GENDER_MALE:
                    spinGender.setSelection(1);
                    break;
                case GENDER_FEMALE:
                    spinGender.setSelection(0);
                    break;
            }
        }else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profil");
        }



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        //Melakukan perintah logout ke presenter
        profilPresenter.logoutSession(getContext());
        //Menutup MainActivity
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                editMode();
                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                //Mengecek apakah semua field masih kosong
                if (!TextUtils.isEmpty(idUser)){
                    if (TextUtils.isEmpty(edtName.getText().toString()) ||
                            TextUtils.isEmpty(edtAlamat.getText().toString()) ||
                            TextUtils.isEmpty(edtNoTelp.getText().toString())){
                        // Menampilkan alertDialog untuk memberitahu user tidak boleh ada field yang kosong
                        AlertDialog.Builder alerDialog = new AlertDialog.Builder(getContext());
                        alerDialog.setMessage("Please Complete the Field!");
                        alerDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alerDialog.show();
                    }else {
                        //Apabila user sudah memasukkan data ke semua kolom
                        LoginData loginData = new LoginData();
                        // Mengisi inputan user ke model loginData
                        loginData.setId_user(idUser);
                        loginData.setUsername(edtName.getText().toString());
                        loginData.setAlamat(edtAlamat.getText().toString());
                        loginData.setNo_telp(edtNoTelp.getText().toString());
                        loginData.setJenkel(mGender);

                        //Mengirim data ke presenter untuk dimasukkan ke dalam database
                        profilPresenter.updateDataUser(getContext(), loginData);


                        readMode();
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);

                    }
                }else {
                    readMode();
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                }
                return true;
                default:
                    return super.onOptionsItemSelected(item);

        }
    }

    private void readMode() {
        edtName.setFocusableInTouchMode(false);
        edtAlamat.setFocusableInTouchMode(false);
        edtNoTelp.setFocusableInTouchMode(false);
        edtName.setFocusable(false);
        edtAlamat.setFocusable(false);
        edtNoTelp.setFocusable(false);

        spinGender.setEnabled(false);
        fabChoosePic.setVisibility(View.VISIBLE);
    }


    private void editMode() {
        edtName.setFocusableInTouchMode(true);
        edtAlamat.setFocusableInTouchMode(true);
        edtNoTelp.setFocusableInTouchMode(true);
        edtName.setFocusable(true);
        edtAlamat.setFocusable(true);
        edtNoTelp.setFocusable(true);

        spinGender.setEnabled(true);
        fabChoosePic.setVisibility(View.VISIBLE);

    }
}
