package org.vansoft.karmanyak.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.model.Event;
import org.vansoft.karmanyak.utils.AllData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.VH> {
    Context context;
    AllData allData;

    public EventAdapter(Context c){
        this.context = c;
        this.allData = AllData.getInstance();
    }

   public onButtonClicked buttonClicked;

  public   interface  onButtonClicked{
        void onCheckInBtnClicked(int position);
        void onItemClicked(int position);
        void onVisitLaterBtnClicked(int position);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_item,viewGroup,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        Event event = allData.getEventList().get(i);
        vh.title.setText(event.getEventName());
        vh.reward.setText(String.valueOf(event.getReward()));
        if(event.getEventDesc().length()>154){
            String des;
            des = event.getEventDesc().substring(0,154);
            vh.desc.setText(des+"...");

        }else {
            vh.desc.setText(event.getEventDesc());
        }
//        Date date = new Date(event.getEventDate());
//        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
          vh.date.setText(event.getEventDate().substring(0,event.getEventEndDate().indexOf("T")));

        Glide.with(context).load(event.getEventImageSrc()).into(
                vh.posterImg
        );
        if(allData.isUpcommingEvent(event)){
            vh.visitLtrBtn.setBackgroundResource(R.drawable.added_to_visit_later_btn_bg);
        }else {
            vh.visitLtrBtn.setBackgroundResource(R.drawable.visit_later_btn_bg);
        }

        vh.bind(buttonClicked,i);
    }



    public void setButtonClicked(onButtonClicked buttonClicked) {
        this.buttonClicked = buttonClicked;
    }



    @Override
    public int getItemCount() {
        return allData.getEventList().size();
    }

    public class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.posterImgId)
        ImageView posterImg;
        @BindView(R.id.titleID)
        TextView title;
        @BindView(R.id.descId)
        TextView desc;
        @BindView(R.id.reward_text_id)
        TextView reward;
        @BindView(R.id.dateId)
        TextView date;
        @BindView(R.id.visitLaterBtnId)
        Button visitLtrBtn;
        @BindView(R.id.checkinBtnId)
        Button checkinBtn;

        @BindView(R.id.cardView)
        CardView cardView;



        public VH(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }


        public void bind(final onButtonClicked onButtonClicked, final int position){
            checkinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClicked.onCheckInBtnClicked(position);
                }
            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClicked.onItemClicked(position);
                }
            });
            visitLtrBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClicked.onVisitLaterBtnClicked(position);
                }
            });
        }
    }
}
