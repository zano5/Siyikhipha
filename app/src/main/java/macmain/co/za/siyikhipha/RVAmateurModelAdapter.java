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
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import macmain.co.za.siyikhipha.anim.AnimationUtils;

/**
 * Created by ProJava on 12/7/2015.
 */
public class RVAmateurModelAdapter extends RecyclerView.Adapter<RVAmateurModelAdapter.AmateurModelViewHolder> {

    public static List<Amateur> amateurList;
    private Context context;
    private static final String baseUrlImage2 = "http://10.0.2.2/webmacmain/images/amateurmodel/";
    private int previousPosition=0;

    public void add(Amateur amateur){

        amateurList.add(amateur);

    }

    public RVAmateurModelAdapter(Context context, List<Amateur> amateurList){

        this.context = context;
        this.amateurList = amateurList;
    }


    @Override
    public AmateurModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_amateur_model,null);

        AmateurModelViewHolder avh = new AmateurModelViewHolder(view);


        return avh;
    }

    @Override
    public void onBindViewHolder(final AmateurModelViewHolder holder, final int position) {


        String urlImage = baseUrlImage2 + amateurList.get(position).getPicture();

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

                //  events.get(position).setBitImage(bitmap);
                amateurList.get(position).setBitImage(bitmap);
                Bitmap image = bitmap;
                // Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                // holder.ivCustomAmateurModel.setBackground(d);
                holder.ivCustomAmateurModel.setImageBitmap(bitmap);

            }
        }.execute(urlImage);

        //holder.ratingBar.setIsIndicator(true);
        holder.tvCustomAmateurModelName.setText(amateurList.get(position).getName());
        holder.tvAmateurModelView.setText("Views: "+ amateurList.get(position).getView());
        holder.tvAmateurModelLikes.setText("Likes: "+ amateurList.get(position).getLike());
        holder.ivCustomAmateurModel.setImageBitmap(amateurList.get(position).getBitImage());

        //holder.tvCustomAmateurRating.setText("Rating: " + amateurList.get(position).getRating() );
        holder.ratingBar.setRating(amateurList.get(position).getRating());

        holder.ratingBar.setClickable(false);

        holder.ratingBar.setIsIndicator(true);


        if(position > previousPosition){

            AnimationUtils.animate(holder,true);

        }else{

            AnimationUtils.animate(holder,false);
        }

        previousPosition = position;











        holder.ivCustomAmateurModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.custom_image_view,null);

                ImageView ivCustom = (ImageView) view.findViewById(R.id.ivCustomImageView);

                ivCustom.setImageBitmap(amateurList.get(position).getBitImage());

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


    }

    @Override
    public int getItemCount() {
        return (null != amateurList ? amateurList.size() : 0);
    }

    public static class AmateurModelViewHolder extends RecyclerView.ViewHolder {

        private CardView cv;
        private TextView tvCustomAmateurModelName, tvAmateurModelView,tvAmateurModelLikes,tvCustomAmateurRating;
        private ImageView ivCustomAmateurModel;
        private RatingBar ratingBar;


        public AmateurModelViewHolder(View view) {
            super(view);

            cv = (CardView) view.findViewById(R.id.cvAmateurModel);
            tvAmateurModelLikes = (TextView) view.findViewById(R.id.tvAmateurModelLikes);
            tvAmateurModelView = (TextView) view.findViewById(R.id.tvAmateurModelViews);
            tvCustomAmateurModelName = (TextView) view.findViewById(R.id.tvCustomAmateurModelName);
          //  tvCustomAmateurRating = (TextView) view.findViewById(R.id.tvAmateurModelRatings);
            ivCustomAmateurModel = (ImageView) view.findViewById(R.id.ivCustomAmateurModel);
            ratingBar = (RatingBar) view.findViewById(R.id.customRatingBarModel);
        }



    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
