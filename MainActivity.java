package id.imam.cobakkp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import id.imam.cobakkp.R;
import id.imam.cobakkp.fragment.HomeFragment;
import id.imam.cobakkp.fragment.PengaturanFragment;
import id.imam.cobakkp.fragment.PesanFragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class MainActivity extends AppCompatActivity{
    ChipNavigationBar chipNavigationBar;
    TextView display;
    private String text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        chipNavigationBar= findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        bottomMenu();

      //  getWisataResponse();


    }



    private void bottomMenu() {
       chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
           @Override
           public void onItemSelected(int i) {
               Fragment fragment=null;
               switch (i){
                   case R.id.btm_home:
                       fragment=new HomeFragment();
                       break;
                   case R.id.btm_pesann:
                       fragment=new PesanFragment();
                       break;
                   case R.id.btm_pengaturan:
                       fragment=new PengaturanFragment();
                       break;
               }
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
           }
       });
    }


//    @Override

  //  public void onStart() {

    //    super.onStart();

        //chekuserstatus();

    //}

}

    //http://192.168.52.105:1