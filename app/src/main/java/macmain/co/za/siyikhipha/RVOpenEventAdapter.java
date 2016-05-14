package macmain.co.za.siyikhipha;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import macmain.co.za.siyikhipha.anim.AnimationUtils;

/**
 * Created by ProJava on 12/21/2015.
 */
public class RVOpenEventAdapter extends RecyclerView.Adapter<RVOpenEventAdapter.OpenEventViewHolder> {

    public static List<Event> openEventList;
    private Context context;
    private static final String baseUrlImage = "http://10.0.2.2/webmacmain/images/openevent/";
    private int previousPosition=0;

    public RVOpenEventAdapter(Context context, List<Event> openEventList){

        this.context = context;
        this.openEventList = openEventList;
    }




    @Override
    public OpenEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_open_event,null);

        OpenEventViewHolder ovh = new OpenEventViewHolder(view);



        return ovh;
    }

    @Override
    public void onBindViewHolder(final OpenEventViewHolder holder, final int position) {

        holder.tvCustomOpenRecommandation.setText("" + openEventList.get(position).getRecommandation() + " Recommandations");
       holder.tvCustomOpenEventTitle.setText("" + openEventList.get(position).getTitle());

        String urlImage = baseUrlImage +  openEventList.get(position).getImage();

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

                openEventList.get(position).setBitImage(bitmap);
                Bitmap image = bitmap;
                Drawable d = new BitmapDrawable(Resources.getSystem(),image);
               holder.ivCustomOpenEvent.setBackground(d);


            }
        }.execute(urlImage);

        holder.btnCustomRecommandation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eventName = openEventList.get(position).getTitle();


                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.custom_email_recommandation,null);

                final EditText etRecommandationName = (EditText) view.findViewById(R.id.etRecommandationName);
                final EditText etRecommandationEmail = (EditText) view.findViewById(R.id.etRecommandationEmail);

                final String email = etRecommandationEmail.getText().toString();

                final String message = " Dear " + etRecommandationName.getText().toString() + "/nWe would like you to have a look at one of " +
                        "the event of the year(" + eventName + ")" +"For more information, download the Siyikhipha app from google " +
                        "play store";

                final String subject = "Event";
                final String cc = "zanoxolomngadis@gmail.com";

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sendEmail("zano@gmail.com","zain@gmail.com","Hi",message);

                        etRecommandationEmail.setText("");
                        etRecommandationName.setText("");

                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();


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
        return (null != openEventList ? openEventList.size() : 0);
    }

    public static class OpenEventViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivCustomOpenEvent;
        private TextView tvCustomOpenEventTitle,tvCustomOpenRecommandation;
        private Button btnCustomRecommandation;
        private CardView cvOpenEvent;

        public OpenEventViewHolder(View view) {
            super(view);

            ivCustomOpenEvent = (ImageView) view.findViewById(R.id.ivCustomOpenEvent);
            tvCustomOpenEventTitle = (TextView) view.findViewById(R.id.tvCustomOpenEventTitle);
            btnCustomRecommandation = (Button) view.findViewById(R.id.btnCustomReccommandation);
            tvCustomOpenRecommandation = (TextView) view.findViewById(R.id.tvCustomOpenRecommandation);
            cvOpenEvent = (CardView) view.findViewById(R.id.cvOpenEvent);



        }
    }

    private void sendEmail(String emailAddresses, String carbonCopies,
                           String subject, String message)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        String to = emailAddresses;
        String cc = carbonCopies;
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.setType("message/rfc822");
        context.startActivity(Intent.createChooser(emailIntent, "Email"));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}

