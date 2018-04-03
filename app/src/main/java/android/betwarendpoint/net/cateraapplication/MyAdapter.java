package android.betwarendpoint.net.cateraapplication;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Betwar-mac on 30/3/18.
 */

public class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private ArrayList<Bitmap> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Bitmap> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_text_view, parent, false);

       return new BaseViewHolder(null);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
      //  holder.mTextView.setText(mDataset[position]);
    holder.setupDate();


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}