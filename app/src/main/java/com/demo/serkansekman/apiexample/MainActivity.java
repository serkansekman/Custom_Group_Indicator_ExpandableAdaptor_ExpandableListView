package com.demo.serkansekman.apiexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ExpandableListView lvNav;
    List<PopularPersonItem> listNavItems;
    ExpandableAdaptor adaptor;

    ListView lvMovie;
    List<PopularPersonItem> listMovieItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        lvNav =  (ExpandableListView) findViewById(R.id.popularperson_list);

       // lvMovie = (ListView) findViewById(R.id.personmovie_list);

       // popular = new ArrayList<PopularPerson>();
       // adapter = new ArrayAdapter<PopularPerson>(this,R.layout.single_list_person,R.id.picperson,popular);
       //lvPerson= (ListView) findViewById(R.id.lvPerson);
       // lvPerson.setAdapter(adapter);

        listNavItems = new ArrayList<>();
        adaptor = new ExpandableAdaptor(this, listNavItems);
        lvNav.setAdapter(adaptor);





        //lvNav.setIndicatorBounds(400-GetDipsFromPixel(100), 600-GetDipsFromPixel(50));

        //GetDipsFromPixel(300);

        MovieApi.getPopularPerson(null,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response ) {
                try {
                    JSONArray arr = response.getJSONArray("results");

                    PopularPersonItem popularPersonItem;
                    MovieDetail movieDetail;

                    for(int p=0;p<arr.length();p++){

                        JSONObject obj2 = arr.getJSONObject(p);
                        popularPersonItem = new PopularPersonItem(obj2.getString("name"), obj2.getString("profile_path"));

                           JSONArray arr2 = obj2.optJSONArray("known_for");
                           for (int i =0;i<arr2.length();i++){

                            JSONObject obj3 = arr2.getJSONObject(i);

                            movieDetail = new MovieDetail();
                            if (obj3.has("title") && obj3.has("poster_path")) {
                               movieDetail.setName(obj3.getString("title"));
                               movieDetail.setImage(obj3.getString("poster_path"));
                               popularPersonItem.getMovies().add(movieDetail);
                            }
                       }
                        listNavItems.add(popularPersonItem);
                   }
                  adaptor.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }



}
