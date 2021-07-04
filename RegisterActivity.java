package id.imam.cobakkp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import id.imam.cobakkp.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText reg_nama,reg_email,reg_password,reg_telepon;
     Button reg_button;
     TextView log_button;
     FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
String userID;
private static final String TAG=RegisterActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg_nama=  findViewById(R.id.namaregister);
        reg_email= findViewById(R.id.emailregister);
        reg_password= findViewById(R.id.passwordregister);
        reg_telepon= findViewById(R.id.teleponregister);
        reg_button= findViewById(R.id.button_register);
        log_button= findViewById(R.id.textview_login);
        firebaseFirestore=FirebaseFirestore.getInstance();

        firebaseAuth= FirebaseAuth.getInstance();
        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent oo = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(oo);
                finish();
            }
        });

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = reg_nama.getText().toString().trim();
                String email = reg_email.getText().toString();
                String password= reg_password.getText().toString().trim();
                String telepon= reg_telepon.getText().toString();

                if (TextUtils.isEmpty(nama)){
                    reg_nama.setError("nama harus di isi");
                    reg_nama.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    reg_email.setError("Email yang benar");
                    reg_email.requestFocus();
                    return;
                }if (password.length()<6){
                    reg_password.setError("password minimal 6");
                    reg_password.requestFocus();
                    return;
                }if (!Patterns.PHONE.matcher(telepon).matches() | telepon.length()<10){
                    reg_telepon.setError("masukan telepon");
                    reg_telepon.requestFocus();
                    return;
                }else{
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"user dibuat",Toast.LENGTH_LONG).show();
                            userID= firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("user").document(userID);

                            Map<String, Object>user = new HashMap<>();
                            user.put("username",nama);
                            user.put("email",email);
                            user.put("telepon",telepon);
                            user.put("biodata","biodata");
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                            Log.d(TAG,"onSucces"+userID);
                                        }
                            });
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this, "gagal", Toast.LENGTH_LONG).show();

                        }
                        }
                    });}
            }
        });
    }
}