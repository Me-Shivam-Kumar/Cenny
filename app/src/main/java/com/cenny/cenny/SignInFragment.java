package com.cenny.cenny;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.cenny.cenny.RegisterActivity.onResetPasswordFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText email,password;
    private ImageButton closeBtn;
    private Button signInBtn;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private TextView forgotPassword;

    public static boolean disableCloseBtn=false;


    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount=view.findViewById(R.id.do_not_have_an_account);
        parentFrameLayout=getActivity().findViewById(R.id.register_frame_layout);

        email=view.findViewById(R.id.sign_in_email_id);
        password=view.findViewById(R.id.sign_in_password);
        closeBtn=view.findViewById(R.id.closeBtn_signIn);
        signInBtn=view.findViewById(R.id.sign_in_btn);
        progressBar=view.findViewById(R.id.progressBar);
        firebaseAuth=FirebaseAuth.getInstance();
        forgotPassword=view.findViewById(R.id.forgot_password);

        if(disableCloseBtn){
            closeBtn.setVisibility(View.GONE);
        }else{
            closeBtn.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment=true;
                setFragment(new ResetPasswordFragment());
            }
        });

    }

    private void checkEmailAndPassword() {
        if(email.getText().toString().matches(emailPattern)){
            if(password.length()>=6){
                progressBar.setVisibility(View.VISIBLE);
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mainIntent();

                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signInBtn.setEnabled(true);
                                    signInBtn.setTextColor(Color.rgb(255,255,255));
                                    Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else {

                Toast.makeText(getActivity(),"Incorrect Email Id or Password",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getActivity(),"Incorrect Email Id or Password",Toast.LENGTH_SHORT).show();
        }

    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.rgb(255,255,255));

            }else {
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50,255,255,255));

            }
        }else {
            signInBtn.setEnabled(false);
            signInBtn.setTextColor(Color.argb(50,255,255,255));

        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void mainIntent(){
        if(disableCloseBtn){
            disableCloseBtn=false;
        }else {
            Intent mainIntent=new Intent(getActivity(),MainActivity.class);
            startActivity(mainIntent);
        }
        getActivity().finish();

    }
}
