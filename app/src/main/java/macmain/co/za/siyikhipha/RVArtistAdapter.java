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
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import macmain.co.za.siyikhipha.anim.AnimationUtils;

/**
 * Created by ProJava on 11/28/2015.
 */
public class RVArtistAdapter extends RecyclerView.Adapter<RVArtistAdapter.ArtistViewHolder> {

    public static List<Artist> artists;
    private Context context;
    private static final String baseUrlImage2 = "http://10.0.2.2/webmacmain/images/artist/";
    private int previousPosition= 0;


    public RVArtistAdapter(Context context, List<Artist> artists){
        this.context = context;
        this.artists = artists;

    }


    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_event,null);
      View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_artist,null);

        ArtistViewHolder avh = new ArtistViewHolder(view);



        return avh;
    }



    @Override
    public void onBindViewHolder(final ArtistViewHolder holder, final int position) {


        holder.tvArtistName.setText(artists.get(position).getName());
        holder.tvArtistLatestAlbum.setText("Latest album: "+artists.get(position).getLastest_album());
        holder.getTvArtistLatestSingle.setText("Latest Single: "+ artists.get(position).getLastest_single());
        holder.getTvArtistNumberOfAlbums.setText("Number Of Albums: " + String.valueOf(artists.get(position).getNumber_of_albums()));


        String urlImage = baseUrlImage2 + artists.get(position).getImage();

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

                artists.get(position).setBitImage(bitmap);
                Bitmap image = bitmap;
                Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                holder.ivArtist.setBackground(d);


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

        return (null != artists ? artists.size() : 0);
    }

    public  static class ArtistViewHolder extends RecyclerView.ViewHolder {

        private CardView cv;
        private TextView tvArtistLatestAlbum,getTvArtistLatestSingle,getTvArtistNumberOfAlbums, tvArtistName;
        private ImageView ivArtist;


        public ArtistViewHolder(View view){



            super(view);

            cv = (CardView) view.findViewById(R.id.cdArtist);
            tvArtistLatestAlbum = (TextView) view.findViewById(R.id.tvArtistLatestAlbum);
            getTvArtistLatestSingle =(TextView) view.findViewById(R.id.tvArtistSingle);
            tvArtistName =(TextView)view.findViewById(R.id.tvArtistName);
            getTvArtistNumberOfAlbums =(TextView)view.findViewById(R.id.tvArtistNumberAlbum);
            ivArtist =(ImageView) view.findViewById(R.id.ivArtist);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
