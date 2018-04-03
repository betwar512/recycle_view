package android.betwarendpoint.net.cateraapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Betwar-mac on 30/3/18.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements UIViewInterface {
    public BaseViewHolder(View itemView) {
        super(itemView);
        initView(itemView);

    }

    @Override
    public void initView(View itemView) {
        Log.d("BASEVIEWHOLDER","INITVIEW");
    }

    @Override
    public void setupDate() {
        Log.d("BASEVIEWHOLDER","SETUP");

    }


}
