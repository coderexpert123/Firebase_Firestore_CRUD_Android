package com.singh.fl;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReadDataAdapter extends RecyclerView.Adapter<ReadDataAdapter.ReadDataHolder> {
    MainActivity mainActivity;
    ArrayList<UserModel> alldataList;
    FirebaseFirestore db=FirebaseFirestore.getInstance();


    public ReadDataAdapter(MainActivity mainActivity, ArrayList<UserModel> alldataList) {

        this.mainActivity=mainActivity;
        this.alldataList=alldataList;




    }

    @NonNull
    @Override
    public ReadDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReadDataHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_data,parent,false));
    }

    @Override
    public int getItemCount() {
        return alldataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ReadDataHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.itenname.setText(alldataList.get(position).getName());
        holder.itememail.setText(alldataList.get(position).getEmail());

        //update functionality

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            mainActivity.startActivity(new Intent(mainActivity,updateActiviy.class).putExtra("key",alldataList.get(position).getUid()));

            }
        });


        //delete functionality

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(mainActivity);
                builder.setMessage("Are you want to delete ?");

                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

               db.collection("user").document(alldataList.get(position).getUid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(mainActivity, "Data is Deleted", Toast.LENGTH_SHORT).show();


                        }else{
                            Toast.makeText(mainActivity, "Not Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                    builder.show();

            }
        });

    }

    class ReadDataHolder extends RecyclerView.ViewHolder{
        TextView itenname,itememail;
        Button delete,update;



    public ReadDataHolder(View itemView){
        super(itemView);
        itenname=itemView.findViewById(R.id.name1);
        itememail=itemView.findViewById(R.id.email1);
        delete=itemView.findViewById(R.id.delete);
        update=itemView.findViewById(R.id.update);



    }

}


}
