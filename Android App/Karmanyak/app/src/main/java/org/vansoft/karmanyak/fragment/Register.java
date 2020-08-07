package org.vansoft.karmanyak.fragment;

import android.content.Context;
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
import org.vansoft.karmanyak.database.DatabaseHelper;
import org.vansoft.karmanyak.model.User;
import org.vansoft.karmanyak.model.UserRegisterResponse;
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
 * {@link Register.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Register extends Fragment {

    private OnFragmentInteractionListener mListener;

    //Ui
    @BindView(R.id.registerButtonId)
    Button registerButton;

    @BindView(R.id.firstNameId)
    EditText firstName;
    @BindView(R.id.lastNameId)
    EditText lastName;
    @BindView(R.id.emailId)
    EditText emal;
    @BindView(R.id.passwordOneId)
    EditText passwordOne;
    @BindView(R.id.passwordTwoId)
    EditText passwordTwo;

    @BindView(R.id.nameReqId)
    TextView nameReq;
    @BindView(R.id.enterEmailId)
    TextView enterEmail;
    @BindView(R.id.sixCharPasswordId)
    TextView sixCharPassword;
    @BindView(R.id.passwordMismatchId)
    TextView passwordMismatch;


    @BindView(R.id.layout)
    View view;

    DatabaseHelper databaseHelper;

    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this,view);
        databaseHelper = DatabaseHelper.getINSTANCE(getContext());
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onRegisterFragmentInteraction(uri);
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

    @OnClick(R.id.registerButtonId)
    public void onRegisterClicked(){
        String fname = firstName.getText().toString();
        String sname = lastName.getText().toString();
        String email = emal.getText().toString();
        String pass = passwordOne.getText().toString();
        String pass2 = passwordTwo.getText().toString();

        boolean canLogin = false;

        nameReq.setVisibility(View.INVISIBLE);
        enterEmail.setVisibility(View.INVISIBLE);
        sixCharPassword.setVisibility(View.INVISIBLE);
        passwordMismatch.setVisibility(View.INVISIBLE);


        if(fname.equals(" ")){
            nameReq.setVisibility(View.VISIBLE);
        }else if(sname.equals(" ")){
            nameReq.setVisibility(View.VISIBLE);
        }else if(email.equals(" ")||!email.contains(".com")||!email.contains("@")){
            enterEmail.setVisibility(View.VISIBLE);
        }else if(pass.equals("")||pass.length()<6){
            sixCharPassword.setVisibility(View.VISIBLE);
        }else if(!pass2.equals(pass)){
            passwordMismatch.setVisibility(View.VISIBLE);
        }else {
            canLogin = true;
        }
        if(canLogin){
            User user = new User(fname+" "+sname,pass,email);
            Call<UserRegisterResponse> registratrion = Const.getApiService().registerUser(user);
            registratrion.enqueue(new Callback<UserRegisterResponse>() {
                @Override
                public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                    Log.d("code", String.valueOf(response.message()));
                    if(response.code()!=200){
//                        Log.e("error ",response.body().getEmail());
                        Snackbar.make(view,"Cannot Register Now : Error "+response.body(), BaseTransientBottomBar.LENGTH_INDEFINITE).show();

                    }else {
                        Log.e("error ",response.body().getEmail());
                        Snackbar.make(view,"Registration Successful Login Now!", BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                    Log.d("data",t.getMessage());
                    Snackbar.make(view,"Error "+t.getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();

                }
            });
        }else {
            Snackbar.make(view,"Please Correct Errors and Try Again :-)", BaseTransientBottomBar.LENGTH_SHORT).show();
        }

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
        void onRegisterFragmentInteraction(Uri uri);
    }
}
