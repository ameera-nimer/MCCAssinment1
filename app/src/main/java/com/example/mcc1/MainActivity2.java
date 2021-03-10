package com.example.mcc1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<User> mArrayList = new ArrayList<>();
    AlertDialog.Builder builder;
    FloatingActionButton fab;

    RecyclerView rView;
    UserAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rView = findViewById(R.id.rView);
        builder = new AlertDialog.Builder(this);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivityForResult(intent, 222);
            }
        });

        SetUpRecyclerView();
        GetDataUserList();



    }

    private void GetDataUserList() {
        mArrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        db.collection("users").orderBy("user_create" , Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        progressDialog.dismiss();
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        for (int i = 0; i < list.size(); i++) {
                            String id = list.get(i).getId();
                            String name = list.get(i).get("user_name").toString();
                            String number = list.get(i).get("user_number").toString();
                            String address = list.get(i).get("user_address").toString();
                            mArrayList.add(new User(id, name, number, address));
                        }
                        SetUpRecyclerView();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "Failed to fetch data " + e);

                    }
                });
    }

    private void SetUpRecyclerView() {
            adapter = new UserAdapter(mArrayList, new UserAdapter.OnItemClickListener() {
            @Override
            public void onclick(View v, int postion, String tag) {
                DeleteItemUserById(postion);
            }
        });

        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 222 && requestCode == 222){
            GetDataUserList();
        }
    }

    private void DeleteItemUserById(int pos) {
        progressDialog.show();
        String id = mArrayList.get(pos).getId();
        if (!TextUtils.isEmpty(id)) {
            db.collection("users").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity2.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    mArrayList.remove(pos);
                    adapter.notifyDataSetChanged();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TAG", "Deletion failed");
                }
            });

        }
    }
}