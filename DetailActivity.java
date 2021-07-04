package id.imam.cobakkp.activity;

import androidx.appcompat.app.AppCompatActivity;
import id.imam.cobakkp.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
private TextView keteranganwisata,deskripsiwisata,tempat,namaWisata,tanggalberangkat;

    private TextView hargawisata;
    private Button pesan_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //keterangan= findViewById
        namaWisata = findViewById(R.id.namawisata);
        ImageView imagedetail = findViewById(R.id.imageViewdetail);
        hargawisata = findViewById(R.id.harga_wisata);
        tempat = findViewById(R.id.tempat_wisata);
       pesan_btn = findViewById(R.id.btn_pesan);
        keteranganwisata = findViewById(R.id.MarqueeText);
        deskripsiwisata = findViewById(R.id.deskripsiwisata);
        keteranganwisata.setSelected(true);


        pesan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent p = new Intent(DetailActivity.this,OrderActivity.class);

              String keteranganWisata = keteranganwisata.getText().toString();
              String harga = hargawisata.getText().toString();
              String Namawisata = namaWisata.getText().toString();
              String tempatwisata = tempat.getText().toString();
              String idwisata = getIntent().getStringExtra("id_wisata");
              String Gambar = getIntent().getExtras().getString("gambar_wisata");
                String tanggalberangkatt=getIntent().getStringExtra("tanggal_berangkat");
                p.putExtra("tanggal_berangkat",tanggalberangkatt);
                p.putExtra("tempat_wisata",tempatwisata);
                p.putExtra("nama_wisata",Namawisata);
                p.putExtra("id_wisata",idwisata);

              p.putExtra("gambar_wisata",Gambar);
              p.putExtra("harga_wisata",harga);
               p.putExtra("keterangan_wisata",keteranganWisata);

               startActivity(p);
              // ord = aja.getText().toString();
                // Intent intent = new Intent(DetailActivity.this,OrderActivity.class);
                //startActivity(intent);
            }
        });


        Intent intentambildata = getIntent();
        if (intentambildata.hasExtra("nama_wisata")){
            String namawisata = intentambildata.getStringExtra("nama_wisata");
            String deskripsiwisatA = getIntent().getExtras().getString("deskripsi_wisata");
            String hargawisatA = getIntent().getExtras().getString("harga_wisata");
            String tempatWisata = getIntent().getExtras().getString("tempat_wisata");
          String keteranganaja = getIntent().getExtras().getString("keterangan_wisata");

            namaWisata.setText(namawisata);
            tempat.setText(tempatWisata);
            deskripsiwisata.setText(deskripsiwisatA);
            hargawisata.setText(hargawisatA);

            keteranganwisata.setText(keteranganaja);
            String gambar=getIntent().getExtras().getString("gambar_wisata");


            Glide.with(this)
                    .load(gambar)
                    .placeholder(R.drawable.gradient1)
                    .into(imagedetail);
          //  String Image_url ="http://192";
           // Glide.with(this)
             //       .load(Image_url+gambar)
               //     .placeholder(R.drawable.gradient1)
                 //   .into(imagedetail);
        }else{
            Toast.makeText(this,"tidak ada data",Toast.LENGTH_LONG).show();
        }

    }
}
