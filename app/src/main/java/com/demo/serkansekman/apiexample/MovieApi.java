package com.demo.serkansekman.apiexample;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by serkan.sekman on 9/16/2016.
 */
public class MovieApi  {

    private  static String BASE_URL = "http://api.themoviedb.org/3/";
    private  static String api_key = "37f2d7e031c209e7e33dca6bfe0cb835";

   private static String popular_person =BASE_URL+"person/popular?page=1&api_key="+api_key;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getPopularPerson(RequestParams params, AsyncHttpResponseHandler asyncHttpResponseHandler){
        client.get(popular_person,params,asyncHttpResponseHandler);

    }

}
