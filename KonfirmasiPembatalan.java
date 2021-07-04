package id.imam.cobakkp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import id.imam.cobakkp.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class KonfirmasiPembatalan extends AppCompatActivity {
    private TextView keterangan,keteranganwisata,hargawisata,namawisata,namapemesan;
    private FirebaseFirestore firebaseFirestore;
    private Button ajukanpembatalan;
    private RelativeLayout relativeLayoutt;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pembatalan);
        firebaseFirestore = FirebaseFirestore.getInstance();
        ajukanpembatalan = findViewById(R.id.buttonajukanpembatalan);
        relativeLayoutt = findViewById(R.id.relative_layout_progress_order1);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        String uid = firebaseAuth.getCurrentUser().getEmail();
        keterangan = findViewById(R.id.keterangan);
        keteranganwisata = findViewById(R.id.orderketeranganwisata);
        hargawisata = findViewById(R.id.hargawisata);
        namapemesan = findViewById(R.id.idwaktu);
        namawisata = findViewById(R.id.textpesannama);


        keterangan.setText(getIntent().getStringExtra("keterangan"));
        namawisata.setText(getIntent().getStringExtra("nama_wisata"));
        hargawisata.setText(getIntent().getStringExtra("harga_wisata"));
        namapemesan.setText(getIntent().getStringExtra("nama_pemesan"));
        keteranganwisata.setText(getIntent().getStringExtra("keterangan_wisata"));









        // String pembatalanorder = getIntent().getStringExtra("id_wisata");

        ajukanpembatalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showprogres();



                Map<String, Object> orderajukan = new HashMap<>();
             //   orderajukan.put("id_wisata",getIntent().getStringExtra("id_wisata"));
                orderajukan.put("statuspembatalan","1");
                orderajukan.put("keterangan_pembatalan","false");
                orderajukan.put("id_order",getIntent().getStringExtra("id_order"));
                orderajukan.put("nama_wisata",getIntent().getStringExtra("nama_wisata"));
                orderajukan.put("keterangan",getIntent().getStringExtra("keterangan"));
                orderajukan.put("nama_pemesan",getIntent().getStringExtra("nama_pemesan"));
                orderajukan.put("id_dia",getIntent().getStringExtra("id_wisata"));
                orderajukan.put("alamat_email",getIntent().getStringExtra("alamat_email"));
                orderajukan.put("telepon_pemesan",getIntent().getStringExtra("telepon_pemesan"));
                orderajukan.put("keterangan_wisata",getIntent().getStringExtra("keterangan_wisata"));
                orderajukan.put("harga_wisata",getIntent().getStringExtra("harga_wisata"));
                orderajukan.put("tempat_wisata",getIntent().getStringExtra("tempat_wisata"));


                DocumentReference documentReference = firebaseFirestore.collection("order").document("pembatalanorder"+uid+getIntent().getStringExtra("id_wisata"));
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()){
                                hideprogres();

                                Toast.makeText(KonfirmasiPembatalan.this, "Sedang di proses", Toast.LENGTH_LONG).show();


                            }else {
                                firebaseFirestore.collection("pembatalan_order").document(uid+getIntent().getStringExtra("id_wisata")).set(orderajukan).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(KonfirmasiPembatalan.this, "Silahkan tunggu", Toast.LENGTH_LONG).show();
                                        hideprogres();

                                       // onBackPressed();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("keterangan","mengajukan pembatalan");
                                        user.put("keterangan_pembatalan","sedang diproses");

                                        firebaseFirestore.collection("order").document(uid+getIntent().getStringExtra("id_wisata")).set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                onBackPressed();
                                                finish();

                                            }
                                        });


                                        }
                                });
                            }
                        }
                    }
                });



            }
        });

    }
    private void hideprogres() {
        relativeLayoutt.setVisibility(View.GONE);
    }

    private void showprogres() {

        relativeLayoutt.setVisibility(View.VISIBLE);
    }
}
