package com.cenny.cenny;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.AvailableNetworkInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import javax.microedition.khronos.opengles.GL;




public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private FrameLayout frameLayout;


    private static final int HOME_FRAGMENT=0;
    private static final int CART_FRAGMENT=1;
    private static final int ORDER_FRAGMENT=2;
    private static final int MYWISH_LIST_FRAGMENT=3;
    private static final int MYREWARD_FRAGMENT=4;
    private static final int MYACCOUNT_FRAGMENT=5;

    public static Boolean showCart=false;


    private  int currentFragment=-1;
    NavigationView navigationView;
    public static DrawerLayout drawer;
    private ImageView actionBarLogo;
    private TextView actionBarTitle;
    private boolean setSignUpFragment=false;
    private Dialog signIndialog;

    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        actionBarLogo=findViewById(R.id.action_bar_logo);
        actionBarTitle=findViewById(R.id.action_bartitle);

        drawer = findViewById(R.id.drawer_layout);

        navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        frameLayout=findViewById(R.id.main_frame_layout);

        if (showCart) {
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            goToFragment("My Cart", new MyCartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);

            drawer.addDrawerListener(toggle);
            toggle.syncState();
            toggle.getDrawerArrowDrawable().setColor(Color.parseColor("#FF6F35A5"));
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        }


        signIndialog=new Dialog(MainActivity.this);
        signIndialog.setContentView(R.layout.sign_in_dialog);
        signIndialog.setCancelable(true);
        signIndialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        final Button dialogSignInBtn=signIndialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUpButton=signIndialog.findViewById(R.id.sign_up_btn);


        final Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);

        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIndialog.dismiss();
                setSignUpFragment=false;
                SignInFragment.disableCloseBtn=true;
                setSignUpFragment=true;
                startActivity(registerIntent);

            }
        });

        dialogSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment.disableCloseBtn=true;
                signIndialog.dismiss();
                setSignUpFragment=true;
                SignInFragment.disableCloseBtn=true;
                startActivity(registerIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null){
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);
        }else{
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);

        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            if(currentFragment==HOME_FRAGMENT){
                currentFragment=-1;
                super.onBackPressed();
            }else {
                if(showCart){
                    showCart=false;
                    finish();
                }else {
                    actionBarTitle.setVisibility(View.VISIBLE);
                    actionBarLogo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(currentFragment==HOME_FRAGMENT){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_search) {
            //todo:search
            return true;
        } else if (id == R.id.action_notification) {
            // todo:notification
            return true;
        } else if (id == R.id.main_cart_icon) {
            if(currentUser==null){
                signIndialog.show();
            }else{
                goToFragment("My Cart",new MyCartFragment(),CART_FRAGMENT);
            }


        }else if(id==android.R.id.home){
            if(showCart){
                showCart=false;
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToFragment(String title,Fragment fragment,int fragmentNo) {
        actionBarLogo.setVisibility(View.GONE);
        actionBarTitle.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment,fragmentNo);
        if(fragmentNo==CART_FRAGMENT) {
            navigationView.getMenu().getItem(2).setChecked(true);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        if(currentUser!=null){
            int id=menuItem.getItemId();
            if(id==R.id.Home){
                actionBarTitle.setVisibility(View.VISIBLE);
                actionBarLogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();;
                setFragment(new HomeFragment(),HOME_FRAGMENT);
            }
            else if(id==R.id.nav_my_orders){
                goToFragment("My Orders",new MyOrdersFragment(),ORDER_FRAGMENT);

            }else if(id==R.id.nav_my_cart){
                goToFragment("My Cart",new MyCartFragment(),CART_FRAGMENT);

            }else if(id==R.id.nav_my_rewards){
                goToFragment("My Rewards",new MyRewardsFragment(),MYREWARD_FRAGMENT);

            }else if(id==R.id.nav_my_wishlist){
                goToFragment("My Wish List",new MyWishListFragment(),MYWISH_LIST_FRAGMENT);

            }else if(id==R.id.nav_my_account){
                goToFragment("My Account",new MyAccountFragment(),MYACCOUNT_FRAGMENT);

            }else if(id==R.id.nav_sign_out){
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }else{
            drawerLayout.closeDrawer(GravityCompat.START);
            signIndialog.show();
            return false;

        }



    }
    private void setFragment(Fragment fragment,int fragmentNo){
        if(fragmentNo!=currentFragment){
            currentFragment=fragmentNo;
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(),fragment);
            fragmentTransaction.commit();
        }

    }
}