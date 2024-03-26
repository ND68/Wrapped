package com.example.spotifywrappedgroup5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifywrappedgroup5.databinding.SignupPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignupPage extends Fragment {
    private SignupPageBinding binding;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SignupPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        EditText signupEmail = view.findViewById(R.id.email_id);
        EditText signupPass = view.findViewById(R.id.password);
        Button signupButton = view.findViewById(R.id.submit_signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPass.getText().toString().trim();

                if (user.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                }

                if (pass.isEmpty()) {
                    signupPass.setError("Password cannot be empty");
                } else {
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Signup Successful", Toast.LENGTH_SHORT).show();
                                NavHostFragment.findNavController(SignupPage.this)
                                        .navigate(R.id.action_LandingPage_to_SpotifySummary);
                            } else {
                                Toast.makeText(getActivity(), "Signup Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}