package android.betwarendpoint.net.cateraapplication.view;

import android.betwarendpoint.net.cateraapplication.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Betwar-mac on 30/3/18.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements UIViewInterface {

    public static int BASE_LAYOUT = R.layout.image_holder_view;

    private BaseView layoutHolder;
    private static class BaseView{
        ImageView imageView;
        TextView textView;

    }

    public BaseViewHolder(View itemView) {
        super(itemView);



        initView(itemView);

    }

    @Override
    public void initView(View itemView) {

        LayoutInflater inflater = (LayoutInflater)itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ConstraintLayout lay =   itemView.findViewById(R.id.layout_hold_id);

        if(inflater != null){
        View viewM = inflater.inflate(BASE_LAYOUT, lay);

        Log.d("BASEVIEWHOLDER","INITVIEW");
        if(layoutHolder == null){
            layoutHolder = new BaseView();
            layoutHolder.imageView = viewM.findViewById(R.id.base_image);
            layoutHolder.textView = viewM.findViewById(R.id.textView);
           }
        }



    }


    public void setupDate(String uri,Bitmap image) {
        Log.d("BASEVIEWHOLDER","SETUP");
        if( layoutHolder != null){
            layoutHolder.textView.setText(uri);
            layoutHolder.imageView.setImageBitmap(image);
        }
    }


}
