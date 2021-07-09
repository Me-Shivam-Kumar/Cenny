package com.cenny.cenny;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {
    private EditText forgotPasswordEmail;
    private Button resetPaswordBtn;
    private TextView goBackTextView;
    private FrameLayout parentFrameLayout;
    private FirebaseAuth firebaseAuth;
    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ProgressBar progressBar;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reset_password, container, false);
        forgotPasswordEmail=view.findViewById(R.id.forgot_password_email);
        resetPaswordBtn=view.findViewById(R.id.resetPasswordBtn);
        goBackTextView=view.findViewById(R.id.goBackTextView);
        parentFrameLayout=getActivity().findViewById(R.id.register_frame_layout);
        firebaseAuth=FirebaseAuth.getInstance();
        emailIconContainer=view.findViewById(R.id.forgot_password_email_icon_contanier);
        emailIcon=view.findViewById(R.id.forgot_password_email_icon);
        emailIconText=view.findViewById(R.id.forgot_password_email_icon_text);
        progressBar=view.findViewById(R.id.progressBar_forgot_password);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forgotPasswordEmail.addTextChangedListener(new TextWatcher() {
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
        goBackTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());

            }
        });
        resetPaswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.GONE );

                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                resetPaswordBtn.setEnabled(false);
                resetPaswordBtn.setTextColor(Color.argb(50,255,255,255));
                firebaseAuth.sendPasswordResetEmail(forgotPasswordEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    resetPaswordBtn.setEnabled(false);
                                    resetPaswordBtn.setTextColor(Color.argb(50,255,255,255));
                                    emailIconText.setVisibility(View.VISIBLE);


                                }else{;
                                String error=task.getException().getMessage();


                                emailIconText.setText(error);
                                emailIconText.setTextColor(getResources().getColor(R.color.failRed));
                                TransitionManager.beginDelayedTransition(emailIconContainer);
                                emailIconText.setVisibility(View.VISIBLE);
                                }
                                progressBar.setVisibility(View.GONE);
                                resetPaswordBtn.setEnabled(true);
                                resetPaswordBtn.setTextColor(Color.rgb(255,255,255));

                            }
                        });
            }
        });
    }

    private void checkInputs() {
        if(!TextUtils.isEmpty(forgotPasswordEmail.getText())){
            resetPaswordBtn.setEnabled(true);
            resetPaswordBtn.setTextColor(Color.rgb(255,255,255));

        }else{ resetPaswordBtn.setEnabled(false);
            resetPaswordBtn.setTextColor(Color.argb(50,255,255,255));

        }
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
}

}
