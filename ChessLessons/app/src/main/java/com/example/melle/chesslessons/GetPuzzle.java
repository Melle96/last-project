package com.example.melle.chesslessons;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class GetPuzzle extends JSONObject implements
        Response.Listener<JSONObject>, Response.ErrorListener{

    Context contextt;
    Callback callbackk;

    public GetPuzzle(Context context) {

        contextt = context;
    }



    public interface Callback {
        void gotQuestions(JSONObject questions);
        void gotQuestionsError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        callbackk.gotQuestionsError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONObject json;
//        try {
        json = response;
        Log.d("tag", String.valueOf(json));
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//            return;
//        }
        callbackk.gotQuestions(json);
    }


    public void GetPuzzle(Callback activity){

        callbackk = activity;
        RequestQueue q = Volley.newRequestQueue(contextt);
        String url = "https://api.chess.com/pub/puzzle/random";
        JsonObjectRequest jar = new JsonObjectRequest(0, url, this, this, this);
        q.add(jar);

    }
}