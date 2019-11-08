package com.example.airloca.ui.compte;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.airloca.Entities.Personne;
import com.example.airloca.R;
import com.example.airloca.ServiceWebAsync;
import com.example.airloca.Utils.Functions;
import com.example.airloca.ui.Session;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements ServiceWebAsync.OnFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText txtLogin;
    EditText txtPassword;
    CheckBox checkBox;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        txtLogin = view.findViewById(R.id.champLogin);
        txtPassword = view.findViewById(R.id.champPassword);
        checkBox = view.findViewById(R.id.checkbox_connected);

        Button btnConnexion = view.findViewById(R.id.btnConnexion);

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = txtLogin.getText().toString();
                String password = txtPassword.getText().toString();

                if(!login.isEmpty() && !password.isEmpty()){
                    String url = "http://172.16.64.12/airloca/loginpersonne.php";
                    HashMap<String, String> params = new HashMap<>();
                    params.put("login", login);
                    params.put("password", password);

                    ServiceWebAsync serviceWebAsync = new ServiceWebAsync(url, params, getContext(), LoginFragment.this);
                    serviceWebAsync.execute();
                } else
                {
                    Toast.makeText(getContext(), "Veuillez saisir vos informations", Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }


    //Récupère les informations post
    @Override
    public void returnSw(String value) {
        Toast.makeText(getContext(), "Value=" + value, Toast.LENGTH_LONG).show();

        Gson gson = new Gson();
        Personne personne = null;

        try {
             personne = gson.fromJson(value, Personne.class);
        }
        catch (Exception ex)
        {

        }

        if(personne != null && !personne.getNom().isEmpty())
        {
            Session.setPersonneConnected(personne);


            if(checkBox.isChecked())
            {
                Functions.SaveSharedPreferences(getActivity(),"PermanentPersonne", value);
            }

            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.nav_compte_personne);

        }else
        {
            Toast.makeText(getContext(), "Désolé mais les gens comme toi ne peuvent pas accéder à cette application.", Toast.LENGTH_LONG).show();
            txtLogin.setText("");
            txtPassword.setText("");
        }


    }
}
