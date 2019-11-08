package com.example.airloca;


import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServiceWebAsync extends AsyncTask<String,Void,String> {

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void returnSw(String value);
    }

    OnFragmentInteractionListener onFragmentInteractionListener;
    private HashMap<String,String> _params;
    private String _url;

    public ServiceWebAsync(String url, HashMap<String,String> params, Context context,OnFragmentInteractionListener callServiceWebInterface) {
        this._params = params;
        this.onFragmentInteractionListener = callServiceWebInterface;
        this._url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {

        OkHttpClient client = new OkHttpClient();
        String retour="";

        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        if(_params != null && _params.size() >0)
        {
            for (Map.Entry value: _params.entrySet()) {
                formEncodingBuilder.add(value.getKey().toString(),value.getValue().toString());
            }
        }

        RequestBody body = formEncodingBuilder.build();

        Request request = new Request.Builder()
                .post(body)
                .url(_url)
                .build();
        try {
            Response response = client.newCall(request ).execute();
            retour =  response.body().string();
        } catch (IOException e) {
            retour = e.getMessage();
        }

        return retour;
    }

    @Override
    protected void onPostExecute(String s ) {
        //Handle result here
        super.onPostExecute(s);

        onFragmentInteractionListener.returnSw(s);
    }

}