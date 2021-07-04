package id.imam.cobakkp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.imam.cobakkp.PesananBatal;
import id.imam.cobakkp.R;
import id.imam.cobakkp.adapter.AdapterPesanan;
import id.imam.cobakkp.fragment.PesanFragment;
import id.imam.cobakkp.model.ModelPesan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PesananAnda extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
      FirebaseUser firebaseUser;
      FirebaseFirestore firebaseFirestore;
      RecyclerView recyclerViewpesanan;
      List<ModelPesan> modellist;
  //  ArrayList<ModelPesan> mwaktu =new ArrayList<>();
    CardView cardberhasil,cardgagal,cardbatal;
    AdapterPesanan adapter;
        DocumentSnapshot documentSnapshot;
    private int taskId;
    //  TextView namaa;
     // FirestoreRecyclerAdapter adapter;

//   String emaill = firebaseAuth.getCurrentUser().getEmail();
       // DocumentReference query = firebaseFirestore.collection("order").document(firebaseAuth.getCurrentUser().getEmail()).collection("idorder").document();
       // CollectionReference query = firebaseFirestore.collection("order");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_anda);
        //----------------------------------------------------------------------
        cardgagal = findViewById(R.id.cardgagal);
        cardbatal = findViewById(R.id.cardBATAL);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerViewpesanan = findViewById(R.id.recyclerviewpesanananda);
        recyclerViewpesanan.setHasFixedSize(true);
        recyclerViewpesanan.setLayoutManager(new LinearLayoutManager(this));
        String emaill = firebaseAuth.getCurrentUser().getEmail();
       // adapter = new AdapterPesanan(this,modellist);


        recyclerViewpesanan.setAdapter(adapter);

cardbatal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent hahahahaha = new Intent(PesananAnda.this, PesananBatal.class);
        startActivity(hahahahaha);
    }
});
        cardgagal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hahahaha = new Intent(PesananAnda.this,PesananGagal.class);
                startActivity(hahahaha);
            }
        });
//--------------------------------------------------------------
       // Query query2 = firebaseFirestore.collection("order").whereEqualTo("alamat",emaill);























 //CollectionReference query = firebaseFirestore.collection("order").document(emaill).collection("idorder");

//        String emaill = firebaseAuth.getCurrentUser().getEmail();



       // Query query = firebaseFirestore.collection("order").document(emaill).collection("idorder").document();
        //Query query2 = query.orderBy("nama",Query.Direction.DESCENDING);
       //  Query query2 = firebaseFirestore.collection("order").orderBy("nama",Query.Direction.ASCENDING);


        Query query2 = firebaseFirestore.collection("order").whereEqualTo("alamat_email",emaill).whereEqualTo("status","false");

        //ini untuk bolean   Query query2 = query.whereEqualTo("objek","berhasil");


    FirestoreRecyclerOptions<ModelPesan> options = new FirestoreRecyclerOptions.Builder<ModelPesan>()
                .setQuery(query2,ModelPesan.class)
                .build();


      /**   adapter = new FirestoreRecyclerAdapter<ModelPesan, productViewholder>(options) {
            @NonNull
            @Override
            public productViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pesan, parent, false);
                return new productViewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull productViewholder productViewholder, int i, @NonNull ModelPesan modelPesan) {
                productViewholder.pesanan.setText(modelPesan.getNama());
            }
        };
       */

      adapter = new AdapterPesanan(options);
         recyclerViewpesanan.setHasFixedSize(true);
         recyclerViewpesanan.setLayoutManager(new LinearLayoutManager(this));
         recyclerViewpesanan.setAdapter(adapter);



                adapter.setOnItemClickListener(new AdapterPesanan.OnItemClickListener() {
                    @Override
                    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                        ModelPesan modelPesan = documentSnapshot.toObject(ModelPesan.class);

                        // harga.toString();
                       // Boolean b = (Boolean) document.get("isPublic");       //if the field is Boolean

                        //int hargawisata = Integer.parseInt(harga);

                        String id = documentSnapshot.getId();
                        //ini untuk ambil integer ke string...  Long harga = documentSnapshot.getLong("harga");
                        String harga = documentSnapshot.getString("harga_wisata");
                        String keterangan = documentSnapshot.getString("keterangan");
                        String keteranganwisata = documentSnapshot.getString("keterangan_wisata");
                        String nama = documentSnapshot.getString("nama_wisata");

                        // String keterangan = documentSnapshot.getString("keterangan");
                        String path = documentSnapshot.getReference().getPath();
                        Toast.makeText(PesananAnda.this, "id" + id, Toast.LENGTH_LONG).show();
                       // namaa.setText(documentSnapshot.getString("nama"));
                      //  Date date = Calendar.getInstance().getTime();
                      //  DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                        //------------------------
                        //  Date date = Calendar.getInstance().getTime();
                      //  DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");

                        //String strDate = dateFormat.format(date);
                        //----------------------------
                        String strDatee = documentSnapshot.getString("tanggal_berangkat");


                        Intent intent = new Intent(PesananAnda.this, UpdatePesanan.class);
                        //get id
                       //ini untuk intent integer ke string... intent.putExtra("harga_wisata",""+harga);
                        intent.putExtra("harga_wisata",harga);
                        intent.putExtra("id_order",id);
                        intent.putExtra("nama_wisata",nama);
                        intent.putExtra("keterangan",keterangan);
                        intent.putExtra("keterangan_wisata",keteranganwisata);
                        intent.putExtra("waktu_pesan",strDatee);

                        startActivity(intent);




                    }
                });
    }




     private class productViewholder extends RecyclerView.ViewHolder {
        TextView pesanan;
        public productViewholder(@NonNull View itemView) {
            super(itemView);
            pesanan = itemView.findViewById(R.id.textpesannama);

        }
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