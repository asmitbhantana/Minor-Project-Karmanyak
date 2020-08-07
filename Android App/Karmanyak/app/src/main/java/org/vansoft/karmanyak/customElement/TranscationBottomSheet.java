package org.vansoft.karmanyak.customElement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.vansoft.karmanyak.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TranscationBottomSheet extends BottomSheetDialogFragment {

    public static final String TAG = "BottomSheetTag";
    @BindView(R.id.transaction_processing_progress_bar_id)
    ProgressBar progressBar;
    @BindView(R.id.transcation_message_id)
    TextView transactionText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.point_rewarded_sheet,container,false);
        ButterKnife.bind(this,view);
        return view;
    }


    public void setVisibilityForProgressBar(int visibility){
        progressBar.setVisibility(visibility);
    }

    public void setMessageForTranscation(String text){
        transactionText.setText(text);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
