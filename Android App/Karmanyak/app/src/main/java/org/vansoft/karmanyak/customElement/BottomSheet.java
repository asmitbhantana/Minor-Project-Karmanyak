package org.vansoft.karmanyak.customElement;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.vansoft.karmanyak.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheet extends BottomSheetDialogFragment {
    public static final String TAG = "BottomsheetTags";
    @BindView(R.id.provide_key_file_id)
    Button provideKeyFileBtn;

    @BindView(R.id.key_file_password_id)
    EditText keyFilePassword;

    @BindView(R.id.create_new_wallet_id)
    Button createNewWalletBtn;

    @BindView(R.id.progress_bar_id)
    ProgressBar walletProgressBar;

     onClickedListner onClikedListners;
    public interface onClickedListner{
        void onProvideButtonClicked();
        void onPasswordSubmitted(String s);
        void onNewWalletCreatedClicked();
    }

    public void setOnClikedListners(onClickedListner onClikedListners) {
        this.onClikedListners = onClikedListners;
    }

    public BottomSheet(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.bottom_sheet_for_key,container,false);
        ButterKnife.bind(this,view);
        walletProgressBar.setVisibility(View.INVISIBLE);
        addClickListeners(view);

        return view;
    }

    private void addClickListeners(View view) {
        provideKeyFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClikedListners.onProvideButtonClicked();
                walletProgressBar.setVisibility(View.VISIBLE);
            }
        });

        keyFilePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onClikedListners.onPasswordSubmitted(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        createNewWalletBtn.setOnClickListener(v -> {
            onClikedListners.onNewWalletCreatedClicked();
            walletProgressBar.setVisibility(View.VISIBLE);
        });

    }


}
