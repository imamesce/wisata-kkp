package id.imam.cobakkp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import id.imam.cobakkp.R;
import id.imam.cobakkp.activity.EditProfileActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE =1023;
TextView  namaprofile,emailprofile,teleponprofile;
FirebaseAuth firebaseAuth;
ImageView gantigambarprofile;
FirebaseFirestore firebaseFirestore;
String userId;

private Button editprofile, buttonverifikasi;
FirebaseUser firebaseUser;
StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        namaprofile= findViewById(R.id.namaprofile);
        emailprofile=findViewById(R.id.emailprofile);
        teleponprofile=findViewById(R.id.teleponprofile);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        editprofile=findViewById(R.id.ganti_imageprofil);
        gantigambarprofile=findViewById(R.id.imageprofile);
        storageReference= FirebaseStorage.getInstance().getReference();
        userId= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();


        StorageReference profilreff= storageReference.child("users/"+firebaseAuth.getCurrentUser().getUid()+"/profile.jpg");
        profilreff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
           Picasso.get().load(uri).rotate(90).placeholder(R.drawable.singapura).into(gantigambarprofile);
            }
        });





        DocumentReference documentReference= firebaseFirestore.collection("user").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                namaprofile.setText(documentSnapshot.getString("username"));
                emailprofile.setText(documentSnapshot.getString("email"));
                teleponprofile.setText(documentSnapshot.getString("telepon"));

            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(view.getContext(), EditProfileActivity.class);
              //  i.putExtra("telepon","0838383");
            i.putExtra("username",namaprofile.getText().toString());
            i.putExtra("email",emailprofile.getText().toString());
            i.putExtra("telepon",teleponprofile.getText().toString());
            startActivity(i);
            finish();
                  // Intent opengaleryintent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(opengaleryintent,1000);
            }
        });









}}









