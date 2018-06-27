package com.example.melle.chesslessons;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

// in deze class wordt een puzzel verkregen via de api
public class GetPuzzle extends JSONObject implements
        Response.Listener<JSONObject>, Response.ErrorListener{

    Context contextt;
    Callback callbackk;

    public GetPuzzle(Context context) {
        contextt = context;
    }



    public interface Callback {

        // als geen fout
        void gotChessPuzzle(JSONObject questions);

        // als wel fout
        void gotChessPuzzleError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        callbackk.gotChessPuzzleError(error.getMessage());
    }

    // response geven van inladen jsonObject
    @Override
    public void onResponse(JSONObject response) {

        JSONObject json;
        json = response;
        callbackk.gotChessPuzzle(json);
    }


    // puzzel wordt verkegen
    public void GetPuzzle(Callback activity){

        callbackk = activity;
        RequestQueue q = Volley.newRequestQueue(contextt);

        // link naar api maken
        String url = "https://api.chess.com/pub/puzzle/random";

        // json van website naar jsonobject
        JsonObjectRequest jar = new JsonObjectRequest(0, url, this, this, this);
        q.add(jar);

    }
}