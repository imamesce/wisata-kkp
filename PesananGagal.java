package id.imam.cobakkp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.imam.cobakkp.R;
import id.imam.cobakkp.adapter.AdapterGagal;
import id.imam.cobakkp.adapter.Adapterwisata;
import id.imam.cobakkp.model.ModelGagal;
import id.imam.cobakkp.model.ModelWisatabaru;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PesananGagal extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    RecyclerView recyclerpesanangagal;
    FirebaseAuth mauth;
    FirebaseFirestore firebaseFirestore;
    AdapterGagal adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_gagal);
        mauth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerpesanangagal = findViewById(R.id.recycgagal);
        String emaill= firebaseAuth.getCurrentUser().getEmail();

        Query query2 = firebaseFirestore.collection("gagal_bayarr").whereEqualTo("alamat_email",emaill);
        FirestoreRecyclerOptions<ModelGagal> options = new FirestoreRecyclerOptions.Builder<ModelGagal>()
                .setQuery(query2,ModelGagal.class)
                .build();
        adapter = new AdapterGagal(options);
        recyclerpesanangagal.setAdapter(adapter);
        recyclerpesanangagal.setLayoutManager(new LinearLayoutManager(this));
        recyclerpesanangagal.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterGagal.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
               // String id = documentSnapshot.getId();

             //   Toast.makeText(PesananGagal.this, "Kode transaksi" + id +"GAGAL BAYAR", Toast.LENGTH_LONG).show();
                Toast.makeText(PesananGagal.this, "GAGAL BAYAR,Hubungi Admin jika sudah bayar", Toast.LENGTH_LONG).show();


            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}