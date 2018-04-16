package android.betwarendpoint.net.cateraapplication.view;

import android.betwarendpoint.net.cateraapplication.R;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Betwar-mac on 30/3/18.
 */

public class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private ArrayList<String> mDataset;
    private ArrayList<Bitmap> imageData;


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<String> myDataset , ArrayList<Bitmap> images) {
        mDataset = myDataset;
        imageData = images;
    }

    public void addItem(String str , Bitmap image){
        mDataset.add(str);
        imageData.add(image);
        notifyItemInserted(imageData.size() -1);
        notifyItemRangeChanged(imageData.size() -1 , imageData.size());



    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                   int viewType) {

        // create a new view

       return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.placeholder_layout, parent, false));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
      //  holder.mTextView.setText(mDataset[position]);
    holder.setupDate(mDataset.get(position),imageData.get(position));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}