package org.vansoft.karmanyak.customElement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.vansoft.karmanyak.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReedemBottomSheet extends BottomSheetDialogFragment {
    public static final String TAG = "ReedemTaf";
    @BindView(R.id.yes_btn_id)
    Button yesBtn;
    @BindView(R.id.no_btn_id)
    Button noBtn;
    @BindView(R.id.progress_bar_reedem_id)
    ProgressBar progressBar;
    @BindView(R.id.reward_message_text_id)
    TextView messageText;


    RewardClickEvents rewardClickEvents;

    public interface RewardClickEvents{
        void onYesBtnClicked();
        void onNoBtnClicked();
    }

    public void setRewardClickEvents(RewardClickEvents rewardClickEvents) {
        this.rewardClickEvents = rewardClickEvents;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View view = inflater.inflate(R.layout.reedem_tokens_transcations_sheet,container,false);
        ButterKnife.bind(this,view);
        setOnClickListners(view);
        return view;
    }

    private void setOnClickListners(View view) {
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewardClickEvents.onYesBtnClicked();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewardClickEvents.onNoBtnClicked();
                dismiss();

            }
        });
    }

    public void setBtnVisibility(int i){
        yesBtn.setVisibility(i);
        noBtn.setVisibility(i);
    }
    public void setProgressBarVisibility(int i){
        progressBar.setVisibility(i);
    }
    public void changeMessageText(String s){
        messageText.setText(s);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
