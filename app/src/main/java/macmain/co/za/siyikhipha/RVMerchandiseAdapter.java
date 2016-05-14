package macmain.co.za.siyikhipha;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import macmain.co.za.siyikhipha.anim.AnimationUtils;

/**
 * Created by ProJava on 11/30/2015.
 */
public class RVMerchandiseAdapter extends RecyclerView.Adapter<RVMerchandiseAdapter.MerchandiseViewHolder> {

    public static List<Merchandise> merchandiseList;
    private Context context;
    private static final String baseUrlImage2 = "http://10.0.2.2/webmacmain/images/culturalitem/";
    private int previousPosition = 0;



    public RVMerchandiseAdapter(Context context, List<Merchandise> merchandiseList){

        this.context = context;
        this.merchandiseList = merchandiseList;



    }

    @Override
    public MerchandiseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_event,null);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_merchandise,null);

        MerchandiseViewHolder mvh = new MerchandiseViewHolder(view);

        return mvh;
    }

    @Override
    public void onBindViewHolder(final MerchandiseViewHolder holder, final int position) {


        holder.tvArea.setText("Area: "+merchandiseList.get(position).getArea());
        holder.tvContact.setText("Contact: "+merchandiseList.get(position).getContact());
        holder.tvCity.setText("City: "+merchandiseList.get(position).getCity());
        holder.tvTown.setText("Town: "+merchandiseList.get(position).getTown());
        holder.tvPrice.setText("Price: " +String.valueOf(merchandiseList.get(position).getPrice()));
        holder.tvTitle.setText(merchandiseList.get(position).getTitle());




        String urlImage = baseUrlImage2 +  merchandiseList.get(position).getPicture();

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



                merchandiseList.get(position).setBitImage(bitmap);
                Bitmap image = bitmap;
                //Drawable d = new BitmapDrawable(Resources.getSystem(),image);
               // holder.ivMerchandise.setBackground(d);

                holder.ivMerchandise.setImageBitmap(bitmap);


            }
        }.execute(urlImage);



        holder.ivMerchandise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.custom_image_view,null);

                ImageView ivCustom = (ImageView) view.findViewById(R.id.ivCustomImageView);

                ivCustom.setImageBitmap(merchandiseList.get(position).getBitImage());

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
        return (null != merchandiseList ? merchandiseList.size() : 0);
    }

    public static class MerchandiseViewHolder extends  RecyclerView.ViewHolder {

        private CardView cd;
        private TextView tvTitle, tvPrice, tvArea, tvTown, tvContact, tvCity;
        private ImageView ivMerchandise;


        public MerchandiseViewHolder(View view) {
            super(view);

            cd = (CardView) view.findViewById(R.id.cdMerchandise);
            tvArea = (TextView) view.findViewById(R.id.tvMerchandiseArea);
            tvPrice = (TextView) view.findViewById(R.id.tvMerchandisePrice);
            tvTown = (TextView) view.findViewById(R.id.tvMerchandiseTown);
            tvCity = (TextView) view.findViewById(R.id.tvMerchandiseCity);
            tvContact = (TextView) view.findViewById(R.id.tvMerchandiseContact);
            tvTitle = (TextView) view.findViewById(R.id.tvMerchandiseTitle);
            ivMerchandise =(ImageView) view.findViewById(R.id.ivMerchandise);





        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
