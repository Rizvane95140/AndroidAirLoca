package com.example.airloca.ui.compte;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.airloca.Entities.Personne;
import com.example.airloca.R;
import com.example.airloca.ui.Session;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   // private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";


    ProgressBar progressBar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CreationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreationFragment newInstance(String param1, String param2) {
        CreationFragment fragment = new CreationFragment();
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
        View view = inflater.inflate(R.layout.fragment_creation, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        final EditText txtNom = view.findViewById(R.id.txtNom);
        final EditText txtPrenom = view.findViewById(R.id.txtPrenom);
        final EditText txtLogin = view.findViewById(R.id.champLogin);
        final EditText txtPassword = view.findViewById(R.id.champPassword);
        final EditText txtEmail = view.findViewById(R.id.txtEmail);
        final EditText txtMobile = view.findViewById(R.id.txtMobile);

        Button btnValider = view.findViewById(R.id.btnValider);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = "";
                String prenom = "";
                String password = "";
                String login = "";
                String email = "";
                String mobile = "";

                if(!nom.isEmpty() && !prenom.isEmpty() && !login.isEmpty() && !password.isEmpty() && !email.isEmpty() && !mobile.isEmpty()){
                    String url = "http://172.16.64.12/airloca/insertpersonne.php";

                    OkHttpAsync okHttpAsync = new OkHttpAsync();

                    okHttpAsync.execute(url, nom, prenom, login, password,email, mobile);
                } else
                {
                    Toast.makeText(getContext(), "Veuillez saisir vos informations", Toast.LENGTH_LONG).show();
                }
            }





        });


        return view;
    }

    public class OkHttpAsync extends AsyncTask<String, Void, String> {

        String nom = "";
        String prenom = "";
        String login = "";
        String password = "";
        String email = "";
        String mobile = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            nom = params[1];
            prenom = params[2];
            login = params[3];
            password = params[4];
            email = params[5];
            mobile = params[6];

            RequestBody body = new FormEncodingBuilder()
                    .add("nom", nom)
                    .add("prenom", prenom)
                    .add("login", login)
                    .add("mobile", mobile)
                    .add("password", password)
                    .add("email", email)
                    .build();



            Request request = new Request.Builder()
                    .post(body)
                    .url(params[0])
                    .build();
            try {
                Response response = client.newCall(request ).execute();
                return response.body().string();
            } catch (IOException e) {
                String s = e.getMessage();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s ) {
            //Handle result here
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            //TextView txtWeb = findViewById(R.id.retourhttp);
            //txtWeb.setText(s);
            if(s == "0"){
                Personne personne = new Personne();
                personne.setNom(nom);
                personne.setPrenom(prenom);
                personne.setLogin(login);
                personne.setEmail(email);
                personne.setMobile(mobile);
                personne.setPassword(password);

                Session.setPersonneConnected(personne);

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_compte_personne);
            }
        }
    }

}
