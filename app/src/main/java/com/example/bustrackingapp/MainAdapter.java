package com.example.bustrackingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bustrackingapp.entities.Route;
import com.example.bustrackingapp.entities.RouteStop;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainAdapter extends FirebaseRecyclerAdapter<RouteStop,MainAdapter.MyViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<RouteStop> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull RouteStop model) {
        holder.name1.setText(model.getName1());
        holder.name2.setText(model.getName2());
        holder.name3.setText(model.getName3());
        holder.name4.setText(model.getName4());
        holder.name5.setText(model.getName5());
        holder.name6.setText(model.getName6());

        //route2

//        holder.name7.setText(model.getName7());
//        holder.name8.setText(model.getName8());
//        holder.name9.setText(model.getName9());
//        holder.name10.setText(model.getName10());
//        holder.name11.setText(model.getName11());
//        holder.name12.setText(model.getName12());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_item,parent,false);
        return new MyViewHolder(v);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name1,name2,name3,name4,name5,name6;
        TextView name7,name8,name9,name10,name11,name12;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //route1
            name1 = itemView.findViewById(R.id.name1);
            name2 = itemView.findViewById(R.id.name2);
            name3 = itemView.findViewById(R.id.name3);
            name4 = itemView.findViewById(R.id.name4);
            name5 = itemView.findViewById(R.id.name5);
            name6 = itemView.findViewById(R.id.name6);

            //route2

//            name8 = itemView.findViewById(R.id.name8);
//            name9 = itemView.findViewById(R.id.name9);
//            name10 = itemView.findViewById(R.id.name10);
//            name11 = itemView.findViewById(R.id.name11);
//            name12 = itemView.findViewById(R.id.name12);


        }
    }


}
