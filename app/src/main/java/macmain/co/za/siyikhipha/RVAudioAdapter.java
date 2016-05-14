package macmain.co.za.siyikhipha;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import macmain.co.za.siyikhipha.anim.AnimationUtils;

/**
 * Created by ProJava on 12/11/2015.
 */
public class RVAudioAdapter extends RecyclerView.Adapter<RVAudioAdapter.AudioViewHolder>{

    public static List<Audio> audioList;
    private Context context;
    private static final String baseUrlImage2 = "http://10.0.2.2/webmacmain/images/artistaudio/";
    private MediaPlayer mPlayer;
    private Uri audio;
    private static final String baseUrlAudio ="http://10.0.2.2/webmacmain/audio/";
    private int duration;
    private int previousPosition =0;


    long startTime = 0;

    Runnable timerRunnable;



    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();




    public RVAudioAdapter(Context context, List<Audio> audioList){

        this.context = context;
        this.audioList =audioList;
    }





    @Override
    public AudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_audio,null);

        AudioViewHolder avh = new AudioViewHolder(view);

        return avh;
    }

    @Override
    public void onBindViewHolder(final AudioViewHolder holder, final int position) {

        timerRunnable = new Runnable() {

            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                holder.tvTimerAudio.setText(String.format("%d:%02d", minutes, seconds));

                timerHandler.postDelayed(this, 500);

                


            }
        };


        holder.tvCustomAudioSongName.setText(audioList.get(position).getSongName());
       // holder.tvCustomAudioProduced.setText(audioList.get(position).getArtistName());

        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = new MediaPlayer();

                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                try {

                    if(!mPlayer.isPlaying()) {
                        mPlayer.setDataSource(context, audio);



                        mPlayer.prepare();
                        mPlayer.start();

                        startTime = System.currentTimeMillis();
                        timerHandler.postDelayed(timerRunnable, 0);

                        holder.btnPlay.setEnabled(false);
                        holder.btnStop.setEnabled(true);
                        holder.btnPause.setEnabled(true);
                    }else{

                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer.start();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        holder.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPlayer.stop();
                mPlayer.release();
                holder.btnPlay.setEnabled(true);
                holder.btnStop.setEnabled(false);
                holder.btnPause.setEnabled(false);
            }
        });

        holder.btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPlayer.pause();
                holder.btnPause.setEnabled(false);
                holder.btnPlay.setEnabled(true);
            }
        });





        String urlImage = baseUrlImage2 + audioList.get(position).getPicture();
        new AsyncTask<String,Void,Bitmap>(){

            @Override
            protected Bitmap doInBackground(String... params) {

                //Download image

                String url = params[0];
                Bitmap icon = null;

                try {
                    InputStream in = new URL(url).openStream();
                    icon = BitmapFactory.decodeStream(in);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return icon;
            }




            @Override
            protected void onPostExecute(Bitmap bitmap) {

               audioList.get(position).setBitImage(bitmap);
                Bitmap image = bitmap;
                Drawable d = new BitmapDrawable(Resources.getSystem(),image);
                holder.ivCustomArtistAudio.setBackground(d);


            }
        }.execute(urlImage);


        final String urlAudio = baseUrlAudio + audioList.get(position).getAudioName();

        new AsyncTask<String,Void,Uri>(){



            @Override
            protected Uri doInBackground(String... params) {

                String url = params[0];
                 audio = null;

                audio = Uri.parse(urlAudio);
                return null;
            }

            @Override
            protected void onPostExecute(Uri uri) {
                super.onPostExecute(uri);
            }
        }.execute(urlAudio);


        if(position > previousPosition){

            AnimationUtils.animateDefault(holder,true);

        }else{

            AnimationUtils.animateDefault(holder,false);
        }

        previousPosition = position;


    }

    @Override
    public int getItemCount() {
        return (null != audioList ? audioList.size() : 0);
    }

    public static class AudioViewHolder extends RecyclerView.ViewHolder {

        private CardView cvCustomAudio;
        private TextView tvCustomAudioProduced, tvCustomAudioSongName;
        private Button btnStop, btnPause, btnPlay;
        private ImageView ivCustomArtistAudio;
        private TextView tvTimerAudio;


        public AudioViewHolder(View view) {
            super(view);

            cvCustomAudio = (CardView)view.findViewById(R.id.cvArtistAudioCardView);
            tvCustomAudioProduced = (TextView) view.findViewById(R.id.tvCustomArtistProduced);
            tvCustomAudioSongName = (TextView) view.findViewById(R.id.tvCustomArtistSong);
            btnPause = (Button) view.findViewById(R.id.btnPause);
            btnPlay = (Button) view.findViewById(R.id.btnPlay);
            btnStop = (Button) view.findViewById(R.id.btnStop);
            ivCustomArtistAudio = (ImageView) view.findViewById(R.id.ivCustomArtistAudio);
            tvTimerAudio = (TextView) view.findViewById(R.id.tvTimerAudio);



        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
