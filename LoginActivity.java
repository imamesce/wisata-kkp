package id.imam.cobakkp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import id.imam.cobakkp.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText edt_username,edt_password;
    private Button btn_login;
    private TextView belumpunyaakunn,lupapassword;
   private FirebaseAuth.AuthStateListener mAuthlistener;
   private ProgressBar progressBar;
   private RelativeLayout relativeLayout;
   private ConstraintLayout roottlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login= findViewById(R.id.btn_login);
        edt_username = findViewById(R.id.usernamelogin);
        edt_password= findViewById(R.id.passwordlogin);
        belumpunyaakunn=findViewById(R.id.belumpunyaakun);
        progressBar =findViewById(R.id.progressbar);
        relativeLayout = findViewById(R.id.relative_layout_progress_activity_main);
        roottlayout = findViewById(R.id.rootlayout);


        belumpunyaAkun();


        mAuth=FirebaseAuth.getInstance();
        mAuthlistener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser()!=null){


                    startActivity(new Intent(LoginActivity.this, MainActivity.class) );

                    finish();
                }
            }
        };


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=edt_username.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                if (email.isEmpty()){
                    Snackbar.make(roottlayout,"masukan email anda",Snackbar.LENGTH_SHORT).show();
                    edt_username.setError("Masukan email anda..");
                    edt_username.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    Snackbar.make(roottlayout,"masukan password anda dengan benar",Snackbar.LENGTH_SHORT).show();
                    edt_password.setError("Masukan password anda");
                    edt_password.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edt_username.setError("Masukan email dengan benar...!");
                    edt_username.requestFocus();
                    return;
                }
                if (password.length()<6){
                    edt_password.setError("Masukan password,,Minimal 6 digit..!");
                    edt_password.requestFocus();
                    return;
                }

                //.
                logiuser(email,password);
            }
        });


    }



    private void belumpunyaAkun() {
        belumpunyaakunn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();

            }
        });
    }

    private void logiuser(String email, String password) {

            showprogres();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            hideprogres();

                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "YEAY,,ANDA BERHASIL MASUK", Toast.LENGTH_LONG).show();




                        } else {

                            showprogres();
                            showMesagebox("Login gagal,Silahkan ulangi lagi..Pastikan email dan password benar");
                            //Toast.makeText(LoginActivity.this, "EROR", Toast.LENGTH_LONG).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                hideprogres();
               // Toast.makeText(LoginActivity.this,"Terjadi kesalahan,,saat login",Toast.LENGTH_LONG).show();
            }
        });
                }


    private void showMesagebox(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Keterangan");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setIcon(getResources().getDrawable(R.drawable.warna_button_masuk));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.show();
    }


    private void showprogres() {
        relativeLayout.setVisibility(View.VISIBLE);
        edt_username.setEnabled(false);
        edt_password.setEnabled(false);
    }

    private void hideprogres() {
        relativeLayout.setVisibility(View.GONE);
        edt_username.setEnabled(true);
        edt_password.setEnabled(true);
    }


    @Override

    protected void onStart() {

        super.onStart();



        mAuth.addAuthStateListener(mAuthlistener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthlistener != null){
            mAuth.removeAuthStateListener(mAuthlistener);
        }
    }




}