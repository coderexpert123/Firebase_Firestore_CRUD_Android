package com.singh.fl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class updateActiviy extends AppCompatActivity {
String key ;
FirebaseFirestore db;
    Button btn;
    EditText ed1;
    EditText ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_activiy);
        db=FirebaseFirestore.getInstance();


        ed1=findViewById(R.id.txt1);
        ed2=findViewById(R.id.txt2);
        btn=findViewById(R.id.btn1);
        //taking unuiques id
        key=getIntent().getStringExtra("key");
        //function calling
        getdata(key);

        //performing update action

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=ed1.getText().toString();
                String email=ed2.getText().toString();db.collection("user").
                        document(key).update("email",email,"name",name).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(updateActiviy.this, "Data Updates Successfully........", Toast.LENGTH_SHORT).show();

                                }else {

                                    Toast.makeText(updateActiviy.this, "Data Not updated successfully........", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }




    public void getdata(String key){

        db.collection("user").document(key).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error==null){
                    UserModel onePerson=value.toObject(UserModel.class);
                    ed1.setText(onePerson.getName());
                    ed2.setText(onePerson.getEmail());
                }
            }
        });


}}