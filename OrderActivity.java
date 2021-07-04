package id.imam.cobakkp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import id.imam.cobakkp.R;
import id.imam.cobakkp.fragment.HomeFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
   private TextView Namawisata,Harga,Keteranganwisata,TempatWisata,Tanggalberangkat;
    private TextView totalbayar;
    private ImageView imageVieworder;
    private Button btnorder;
   private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
     private FirebaseUser firebaseUser;
     private RelativeLayout relativeLayoutt;
     private  DatePickerDialog.OnDateSetListener setListener;

  //  private int taskId;
    //  String st;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Harga = findViewById(R.id.textviewharga);
        relativeLayoutt = findViewById(R.id.relative_layout_progress_order);
        imageVieworder =  findViewById(R.id.imageorder);
        TempatWisata =  findViewById(R.id.tempat_wisata);
        totalbayar=findViewById(R.id.texttotalbayar);
        Namawisata = findViewById(R.id.idnamawisata);
        Keteranganwisata = findViewById(R.id.idketeranganwisata);
        btnorder=findViewById(R.id.btn_order);

        Tanggalberangkat = findViewById(R.id.tanggalberangkat1);
        /**
        Calendar calendar = Calendar.getInstance();
        final int tahun = calendar.get(Calendar.YEAR);
        final int bulan = calendar.get(Calendar.MONTH);
        final int hari = calendar.get(Calendar.DAY_OF_MONTH);
         */
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();



        String getemail= firebaseAuth.getCurrentUser().getEmail();
        String uid = firebaseAuth.getCurrentUser().getEmail();


        Intent intentambildata = getIntent();
        if (intentambildata.hasExtra("harga_wisata")) {
            String tanggall = intentambildata.getStringExtra("tanggal_berangkat");
            String hargawisata = intentambildata.getStringExtra("harga_wisata");
            String gambarwisata =  intentambildata.getStringExtra("gambar_wisata");
            String namawisata = intentambildata.getStringExtra("nama_wisata");
            String tempatwisata = intentambildata.getStringExtra("tempat_wisata");
            String keteranganwisata = intentambildata.getStringExtra("keterangan_wisata");
           // String Image_url ="http://192.168.52.105:1337";
            Glide.with(this)
                    .load(gambarwisata)
                    .placeholder(R.drawable.gradient1)
                    .into(imageVieworder);


            //String idorder = intentambildata.getStringExtra("id_order");

            Namawisata.setText(namawisata);
            TempatWisata.setText(tempatwisata);
            Keteranganwisata.setText(keteranganwisata);
            Harga.setText(hargawisata);
            Tanggalberangkat.setText(tanggall);


        }else {
            Toast.makeText(this,"tidak ada data",Toast.LENGTH_LONG).show();

        }
       // st= getIntent().getExtras().getString("Value");

       // Nama.setText(st);

/**
        Tanggalberangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tanggal=Tanggalberangkat.getText().toString().trim();
                if (tanggal.isEmpty()) {
                    Tanggalberangkat.setError("Masukan Tanggal anda..");
                    Tanggalberangkat.requestFocus();
                    return;
                }else {


                    DatePickerDialog datePickerDialog = new DatePickerDialog(OrderActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, tahun, bulan, hari);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            }
        });
        setListener =  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int tahun, int bulan, int DayOfMonth) {
                bulan = bulan + 1;
                String date = hari+"-"+bulan+"-"+tahun;
                Tanggalberangkat.setText(date);

            }
        };
*/
        btnorder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /**
                String Pilih = "Pilih Tanggal Berangkat";
                String tanggall=Tanggalberangkat.getText().toString();

                if (tanggall.equals(Pilih)) {
                    Tanggalberangkat.setError("Masukan Tanggal anda..");
                    Tanggalberangkat.requestFocus();
                    return;
                }else {
                 */
                showprogres();


                // String idku = firebaseFirestore.collection("order").getId();

               // String status = totalbayar.getText().toString();
               String harga= Harga.getText().toString();
               //convert ke integer int hargaWisata = Integer.parseInt(harga);
                //
                    String tanggalll= Tanggalberangkat.getText().toString();
                    String namawisataa =  Namawisata.getText().toString();
                String keteranganwisataa = Keteranganwisata.getText().toString();
                String tempatwisataa = TempatWisata.getText().toString();
                    Date date = Calendar.getInstance().getTime();
                  //  DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                      DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                    String uidd = uid+getIntent().getStringExtra("id_wisata");
                    String strDate = dateFormat.format(date);
                    Map<String, Object>ordermap = new HashMap<>();
                ordermap.put("dibuat", FieldValue.serverTimestamp());
                ordermap.put("status","false");
                ordermap.put("orang",1);
                ordermap.put("keterangan","menunggu pembayaran");
                ordermap.put("tanggal_berangkat",tanggalll);
                ordermap.put("id_wisata",getIntent().getStringExtra("id_wisata"));
                ordermap.put(uidd,"false");
                //ini coy

                ordermap.put("nama_wisata",namawisataa);
                ordermap.put("keterangan_wisata",keteranganwisataa);
                ordermap.put("tempat_wisata",tempatwisataa);
                ordermap.put("waktu_pesan",strDate);


                // ordermap.put("harga");
                ordermap.put("harga_wisata",harga);
               // ordermap.put("alamat",);
                ordermap.put("alamat_email",getemail);


                    DocumentReference documentReference = firebaseFirestore.collection("order").document(uid+getIntent().getStringExtra("id_wisata"));
                        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        hideprogres();
                                        Toast.makeText(OrderActivity.this, "Anda sudah order paket ini", Toast.LENGTH_LONG).show();
                                    } else {
                                        firebaseFirestore.collection("order").document(uid + getIntent().getStringExtra("id_wisata")).set(ordermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                toast();
                                                hideprogres();
                                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                                startActivity(intent);

                                                finish();

                                            }
                                        });
                                    }
                                }
                            }
                        });
/**
                    firebaseFirestore.collection("order").add(ordermap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            toast();
                            hideprogres();
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);

                            finish();
                           // onBackPressed();
                        }
                    });
                    */
                    //
            }
        });






    }

    private void toast() {
        Toast.makeText(this,"Silahkan lihat Pesanan Anda",Toast.LENGTH_LONG).show();

    }

    private void hideprogres() {
        relativeLayoutt.setVisibility(View.GONE);
    }

    private void showprogres() {

        relativeLayoutt.setVisibility(View.VISIBLE);
    }
}