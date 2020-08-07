package org.vansoft.karmanyak.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.activitie.MainActivity;
import org.vansoft.karmanyak.database.DatabaseHelper;
import org.vansoft.karmanyak.model.LoginResponse;
import org.vansoft.karmanyak.model.User;
import org.vansoft.karmanyak.utils.AllData;
import org.vansoft.karmanyak.utils.Const;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Login extends Fragment {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.log_in_btn_id)
    Button loginButton;
    @BindView(R.id.emailId)
    EditText email;
    @BindView(R.id.passwordId)
    EditText password;
    @BindView(R.id.invalidEmailId)
    TextView invalidEmail;
    @BindView(R.id.layout)
    View view;

    AllData allData;
    DatabaseHelper databaseHelper;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        allData = AllData.getInstance();
        databaseHelper = DatabaseHelper.getINSTANCE(getContext());
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onLoginFragmentInteraction("email","password");
        }
    }

    @OnClick(R.id.log_in_btn_id)
    public void onLoginClicked(final View view){
        Log.d("Click","login");
       // mListener.onLoginFragmentInteraction();
        String eml = email.getText().toString();
        String pas = password.getText().toString();

        invalidEmail.setVisibility(View.INVISIBLE);
        boolean canLogin = false;
        if(eml.equals(" ")||!eml.contains("@")||!eml.contains(".com")){
            invalidEmail.setVisibility(View.VISIBLE);
        }else if(pas.length()<6){

        }else{
            canLogin = true;
        }
        if(canLogin){
            final User user = new User(pas,eml);
            Call<LoginResponse> login = Const.getApiService().loginUser(user);
            login.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.code()!=200){
//                        Log.e("error ",response.body().getEmail());
                        Snackbar.make(view,"Cannot Login Now : Error "+response.body(), BaseTransientBottomBar.LENGTH_INDEFINITE).show();

                    }else {
                        User eveUser = new User(response.body().getName()," ",eml);
                        allData.setUser(eveUser);
                        databaseHelper.userDao().addUser(eveUser);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("data",t.getMessage());
                    Snackbar.make(view,"Error "+t.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Snackbar.make(view,"Invalid Email Or Password", BaseTransientBottomBar.LENGTH_SHORT).show();
        }




    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLoginFragmentInteraction(String... values);
    }
}
