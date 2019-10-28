package com.ontrack.retrofit3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private Context mContext;
    private ArrayList<Post> mExampleList;

    public ExampleAdapter(Context context, ArrayList<Post> exampleList){
        mContext= context;
        mExampleList= exampleList;
    }
//to overide the mainactivity layout with recyclerView Layout. Here recycler View Layout is example_item.xml
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }

    //onBindViewHolder is use to get current item position from arrayList
//fetch all data from json file
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        //Post is name of class from where we are getting item
        Post currentItem = mExampleList.get(position);

        String Bikeimageurl= currentItem.getImage();
        String BikeName= currentItem.getName();
        String BikeId= currentItem.getId();
        String bikeIgnition= currentItem.getIgnition();

        holder.mBikeName.setText(BikeName);
        holder.mBikeId.setText(BikeId);
        holder.mBikeIgnition.setText(bikeIgnition);

        Glide
                .with(mContext)
                .load(Bikeimageurl)
                .into(holder.mBikeImage);

    }

    //to return number of item in recyclerview eg: return 10; this willreturn only 10 items

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    //to assign item from layout

    public class ExampleViewHolder extends RecyclerView.ViewHolder{

       public ImageView mBikeImage;
       public TextView mBikeName;
       public TextView mBikeId;
       public TextView mBikeIgnition;


       public ExampleViewHolder(@NonNull View itemView) {
           super(itemView);

           mBikeImage = itemView.findViewById(R.id.tv_bike_image);
           mBikeName = itemView.findViewById(R.id.tv_bike_name);
           mBikeId = itemView.findViewById(R.id.tv_bike_id);
           mBikeIgnition= itemView.findViewById(R.id.tv_bike_ignition);
       }
   }
}
