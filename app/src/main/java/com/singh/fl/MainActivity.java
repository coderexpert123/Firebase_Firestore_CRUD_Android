package com.singh.fl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
Button btn;
EditText ed1;
EditText ed2;
FirebaseFirestore db;
ArrayList<UserModel> alldataList;
RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create
        //delete
        //fetch
        //update

        btn=findViewById(R.id.btn1);
        ed1=findViewById(R.id.txt1);
        ed2=findViewById(R.id.txt2);
        recyclerView=findViewById(R.id.rcvview);


        //get firebase instance
        db=FirebaseFirestore.getInstance();

//******************************************Saving Data **************************************************//
        btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        //taking value from edit text
        String name=ed1.getText().toString();
        String email=ed2.getText().toString();

        String uid=db.collection("user").document().getId();


        //crete object of name and email
        UserModel data= new UserModel(name,email,uid);
        //save the data in collection
        db.collection("user").document(uid).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               //task is suuccess or not check

                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Data is added ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data Not is added ", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
});

        //get all data
        getdata();

//******************************************************ENd of saving data into fireabase**************************************************



    }
           // method for getting all the data
            public void getdata(){
            alldataList=new ArrayList<>();
            alldataList.clear();
            db.collection("user").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
            if (error==null){
            List<UserModel> data= value.toObjects(UserModel.class);
            alldataList.addAll(data);

            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(new ReadDataAdapter(MainActivity.this,alldataList));
            }
            }
        });
    }
}