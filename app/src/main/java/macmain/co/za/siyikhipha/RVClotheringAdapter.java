package macmain.co.za.siyikhipha;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import macmain.co.za.siyikhipha.anim.AnimationUtils;

/**
 * Created by ProJava on 12/18/2015.
 */
public class RVClotheringAdapter extends RecyclerView.Adapter<RVClotheringAdapter.ClotheringViewHolder> {

    public static List<Clothering> clotheringList;
    private Context context;
    private static final String baseUrlImage = "http://10.0.2.2/webmacmain/images/clothering/";
    private int previousPosition=0;

    public RVClotheringAdapter(Context context, List<Clothering> clotheringList){

        this.context = context;
        this.clotheringList = clotheringList;
    }


    @Override
    public ClotheringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_clothering,null);

        ClotheringViewHolder cvh = new ClotheringViewHolder(view);



        return cvh;
    }

    @Override
    public void onBindViewHolder(final ClotheringViewHolder holder, int position) {

        holder.tvCustomLine.setText(clotheringList.get(position).getLine());
        holder.tvCutsomPrice.setText("Price: " + clotheringList.get(position).getPrice());
        holder.tvCustomContact.setText("Contact: " + clotheringList.get(position).getContact());
        holder.tvCustomerProducer.setText("Producer: " + clotheringList.get(position).getProducer());

        String urlImage = baseUrlImage +  clotheringList.get(position).getPicture();

        new AsyncTask<String,Void,Bitmap>(){



            @Override
            protected Bitmap doInBackground(String... params) {

                //Download image

                String url = params[0];
                Bitmap icon = null;

                try {
                    InputStream in = new java.net.URL(url).openStream();
                    icon = BitmapFactory.decodeStream(in);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return icon;
            }


            @Override
            protected void onPostExecute(Bitmap bitmap) {

                Bitmap image = bitmap;
                //Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                holder.ivCustomClothering.setImageBitmap(bitmap);


            }
        }.execute(urlImage);



        if(position > previousPosition){

            AnimationUtils.animateDefault(holder,true);

        }else{

            AnimationUtils.animateDefault(holder,false);
        }

        previousPosition = position;



    }

    @Override
    public int getItemCount() {
      return   null != clotheringList? clotheringList.size() : 0;
    }

    public static class ClotheringViewHolder extends RecyclerView.ViewHolder{

        private TextView tvCustomLine, tvCustomerProducer, tvCutsomPrice, tvCustomContact;
        private ImageView ivCustomClothering;
        private CardView cd;

        public ClotheringViewHolder(View view) {
            super(view);

            cd = (CardView) view.findViewById(R.id.cdClothering);
            tvCustomContact = (TextView) view.findViewById(R.id.tvCustomContact);
            tvCustomerProducer = (TextView) view.findViewById(R.id.tvCustomProducer);
            tvCutsomPrice = (TextView) view.findViewById(R.id.tvCustomPrice);
            tvCustomLine = (TextView) view.findViewById(R.id.tvCustomLine);
            ivCustomClothering = (ImageView) view.findViewById(R.id.ivCustomClothering);



        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
