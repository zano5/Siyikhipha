package macmain.co.za.siyikhipha;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import macmain.co.za.siyikhipha.anim.AnimationUtils;

/**
 * Created by ProJava on 12/16/2015.
 */
public class RVEventScheduleAdapter extends RecyclerView.Adapter<RVEventScheduleAdapter.ScheduleViewHolder> {

    private List<EventSchedule> schedules;
    private Context context;
    private int previousPosition= 0;




    public RVEventScheduleAdapter(Context context, List<EventSchedule> schedules){

        this.context = context;
        this.schedules = schedules;
    }


    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_event_schedule, null);

        ScheduleViewHolder svh = new ScheduleViewHolder(view);




        return svh;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {


       holder.tvCustomScheduleArtistName.setText(schedules.get(position).getArtist());










        if(position > previousPosition){

            AnimationUtils.animateDefault(holder,true);

        }else{

            AnimationUtils.animateDefault(holder,false);
        }

        previousPosition = position;


    }

    @Override
    public int getItemCount() {

        return (null != schedules ? schedules.size() : 0);
    }

    public static class ScheduleViewHolder extends  RecyclerView.ViewHolder {

        private CardView cvSchedule;
        private TextView tvCustomScheduleArtistName, tvCustomScheduleStartTime, tvCustomScheduleEndTime;


        public ScheduleViewHolder(View view) {
            super(view);

            cvSchedule = (CardView) view.findViewById(R.id.cvSchedule);
            tvCustomScheduleArtistName = (TextView) view.findViewById(R.id.tvCustomScheduleArtistName);
            tvCustomScheduleEndTime = (TextView) view.findViewById(R.id.tvCustomScheduleEndTime);
            tvCustomScheduleStartTime = (TextView) view.findViewById(R.id.tvCustomScheduleStartTime);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
