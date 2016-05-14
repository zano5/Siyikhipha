package macmain.co.za.siyikhipha;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import macmain.co.za.siyikhipha.anim.AnimationUtils;

/**
 * Created by ProJava on 12/23/2015.
 */
public class RVPromotionAdapter  extends RecyclerView.Adapter<RVPromotionAdapter.PromotionViewHolder>{

    public static List<Promotion> promotionList;
    private Context context;
    private int previousPosition = 0;

    private static final String baseUrlImage = "http://10.0.2.2/webmacmain/images/promotion/";
    public RVPromotionAdapter(Context context, List<Promotion> promotionList){

        this.context =context;
        this.promotionList = promotionList;
    }

    @Override
    public PromotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_promotional,null);

        PromotionViewHolder pvh = new PromotionViewHolder(view);

        return pvh;
    }

    @Override
    public void onBindViewHolder(final PromotionViewHolder holder, final int position) {

        holder.tvCustomPromotionSpecialName.setText(promotionList.get(position).getName());
        holder.tvCustomPromotionStart.setText("Starts:" +promotionList.get(position).getStart());
        holder.tvCustomPromotionEnd.setText("Ends: "+promotionList.get(position).getEnd());
        holder.tvCustomPromotionalPrice.setText("Price:R" +promotionList.get(position).getPrice());







        String urlImage = baseUrlImage + promotionList.get(position).getPicture();

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

                promotionList.get(position).setBitImage(bitmap);
                Bitmap image = bitmap;
                //Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                //holder.ivCustomPromotion.setBackground(d);

                holder.ivCustomPromotion.setImageBitmap(bitmap);


            }



        }.execute(urlImage);



        holder.ivCustomPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.custom_image_view,null);

                ImageView ivCustom = (ImageView) view.findViewById(R.id.ivCustomImageView);

                ivCustom.setImageBitmap(promotionList.get(position).getBitImage());

                builder.setView(view).setCancelable(true).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

                // builder = new AlertDialog.Builder(context).create();
                //builder.setTitle("Login Information...");
            }
        });


        if(position > previousPosition){

            AnimationUtils.animateDefault(holder,true);

        }else{

            AnimationUtils.animateDefault(holder,false);
        }

        previousPosition = position;




    }

    @Override
    public int getItemCount() {
        return (null != promotionList ? promotionList.size() : 0);
    }

    public static class PromotionViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivCustomPromotion;
        private TextView tvCustomPromotionStart,tvCustomPromotionEnd,tvCustomPromotionSpecialName,tvCustomPromotionalPrice;
        private CardView cdPromotion;

        public PromotionViewHolder(View view) {
            super(view);

            ivCustomPromotion = (ImageView) view.findViewById(R.id.ivCustomPromotionalSpecial);
            tvCustomPromotionEnd = (TextView) view.findViewById(R.id.tvCustomPromontionalEnd);
            tvCustomPromotionStart = (TextView) view.findViewById(R.id.tvCustomPromontionalStart);
            tvCustomPromotionSpecialName = (TextView) view.findViewById(R.id.tvCustomPromotionalSpecialName);
            tvCustomPromotionalPrice = (TextView) view.findViewById(R.id.tvCustomPromontionalPrice);
            cdPromotion = (CardView) view.findViewById(R.id.cdPromotion);


        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
