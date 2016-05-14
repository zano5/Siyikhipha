package macmain.co.za.siyikhipha;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import macmain.co.za.siyikhipha.anim.AnimationUtils;

/**
 * Created by ProJava on 11/27/2015.
 */
public class RVEventAdapter extends RecyclerView.Adapter<RVEventAdapter.EventViewHolder> {

    public static List<Event> events;
    private Context mContext;
    private static final String baseUrlImage2 = "http://10.0.2.2/webmacmain/images/event/";
    public static Bitmap image;
    private int previousPosition=0;




    public RVEventAdapter(Context mContext,List<Event> events){
        this.events =events;
        this.mContext = mContext;
    }


    public void add(Event event){

        events.add(event);

    }




    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_event,null);

        EventViewHolder evh = new EventViewHolder(view);

        return evh;
    }

    @Override
    public void onBindViewHolder(final EventViewHolder holder, final int position) {




       holder.tvTitle.setText(events.get(position).getTitle());
       holder.tvStart.setText("Start:" +events.get(position).getStart());
       holder.tvEnd.setText("End:"+events.get(position).getEnd());
        holder.tvDate.setText(events.get(position).getDate());
       holder.tvArea.setText(events.get(position).getArea());
        holder.tvPrice.setText("R"+String.valueOf(events.get(position).getPrice()));
        holder.tvVip.setText("R"+String.valueOf(events.get(position).getVip()));



        String urlImage = baseUrlImage2 + events.get(position).getImage();

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

               // image= bitmap;
                events.get(position).setBitImage(bitmap);
                holder.ivEvent.setImageBitmap(bitmap);




            }
        }.execute(urlImage);



        holder.ivEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                LayoutInflater factory = LayoutInflater.from(mContext);
                final View view = factory.inflate(R.layout.custom_image_view,null);

                ImageView ivCustom = (ImageView) view.findViewById(R.id.ivCustomImageView);

                ivCustom.setImageBitmap(events.get(position).getBitImage());

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
        return (null != events ? events.size() : 0);
    }

    public static class EventViewHolder extends  RecyclerView.ViewHolder{

        private CardView cv;
        private ImageView ivEvent;
        private TextView tvArea, tvDate, tvTitle,tvStart,tvEnd,tvPrice,tvVip;





        public EventViewHolder(View view){

            super(view);

            cv = (CardView) view.findViewById(R.id.cdEvent);
            ivEvent = (ImageView) view.findViewById(R.id.ivEvent);
            tvArea =(TextView) view.findViewById(R.id.tvArea);
            tvDate =(TextView) view.findViewById(R.id.tvDate);
            tvTitle =(TextView) view.findViewById(R.id.tvTitle);
            tvStart =(TextView) view.findViewById(R.id.tvStart);
            tvEnd =(TextView) view.findViewById(R.id.tvEnd);
            tvPrice=(TextView) view.findViewById(R.id.tvNormalPrice);
            tvVip = (TextView)view.findViewById(R.id.tvVipPrice);



        }







    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
