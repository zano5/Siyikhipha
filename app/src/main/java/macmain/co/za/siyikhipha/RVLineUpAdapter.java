package macmain.co.za.siyikhipha;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
 * Created by ProJava on 12/24/2015.
 */
public class RVLineUpAdapter extends RecyclerView.Adapter<RVLineUpAdapter.LineUpViewHolder> {

    private List<Line> lineList;
    private Context context;
    private static final String baseUrlImage= "http://10.0.2.2/webmacmain/images/event/artist/";
    private int previousPosition = 0;

    public RVLineUpAdapter(Context context, List<Line> lineList ){

        this.context = context;
        this.lineList = lineList;
    }


    @Override
    public LineUpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_lineup,null);

        LineUpViewHolder lvh = new LineUpViewHolder(view);


        return lvh;
    }

    @Override
    public void onBindViewHolder(final LineUpViewHolder holder, final int position) {

        holder.tvCustomLineUpArtistName.setText(lineList.get(position).getArtistName());
        holder.tvCustomLineUpArtistStart.setText(lineList.get(position).getStart());
        holder.tvCustomLineUpArtistEnd.setText(lineList.get(position).getEnd());



        String urlImage = baseUrlImage +  lineList.get(position).getPicture();

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



                lineList.get(position).setBitImage(bitmap);
                Bitmap image = bitmap;
                //Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                // holder.ivMerchandise.setBackground(d);

                holder.ivCustomLineUpArtistImage.setImageBitmap(bitmap);


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
        return (null != lineList ? lineList.size() : 0);
    }

    public static class LineUpViewHolder extends RecyclerView.ViewHolder{

        private TextView tvCustomLineUpArtistName, tvCustomLineUpArtistStart, tvCustomLineUpArtistEnd;
        private ImageView ivCustomLineUpArtistImage;
        private CardView cvLineUp;

        public LineUpViewHolder(View view) {
            super(view);


            tvCustomLineUpArtistEnd = (TextView)  view.findViewById(R.id.tvCustomLineUpArtistEnd);
            tvCustomLineUpArtistName = (TextView) view.findViewById(R.id.tvCustomLineUpArtistName);
            tvCustomLineUpArtistStart = (TextView) view.findViewById(R.id.tvCustomLineUpArtistStart);
            cvLineUp = (CardView) view.findViewById(R.id.cvLineUp);
            ivCustomLineUpArtistImage = (ImageView) view.findViewById(R.id.ivCustomLineUpArtistImage);
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
