package com.example.mcc1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editTextUserName;
    EditText editTextUserNumber;
    EditText editTextUserAddress;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUserName = findViewById(R.id.user_name);
        editTextUserNumber = findViewById(R.id.user_number);
        editTextUserAddress = findViewById(R.id.user_address);
    }



    public void saveToFirebase(View v) {
        String userName = editTextUserName.getText().toString();
        String userNumber = editTextUserNumber.getText().toString();
        String userAddress = editTextUserAddress.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("user_name", userName);
        user.put("user_number", userNumber);
        user.put("user_address", userAddress);
        user.put("user_create", new Date().getTime());

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent = new Intent();
                        setResult(222, intent.putExtra("IsUpdate", true));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Faild to add to DB" + e);
                    }
                });


    }
}