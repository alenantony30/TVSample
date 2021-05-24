package com.example.tvsample;

import androidx.cardview.widget.CardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.List;



public class MainActivity extends Activity implements View.OnFocusChangeListener,View.OnClickListener,View.OnKeyListener{






    public static String BASE_URL="https://api.themoviedb.org";
    public static  String API_KEY="f847336cfad8c531603d08281a375f21";
    public static String LANGUAGE="en-US";
    public static int PAGE=1;
    public static String CATEGORY="now_playing";





    public  static Boolean fetching=true;


    CardView tv,liveTv,movies,shows,apps,account,search,wifi,settings;

    ImageView imageView;


    LinearLayout nowPlayingMovies;
    LinearLayout linearLayout;
    LayoutInflater inflater;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nowPlayingMovies=findViewById(R.id.now_playing_movies_linear_layout);
        inflater=LayoutInflater.from(this);
        imageView=findViewById(R.id.nowPlayingMovieImage);
        linearLayout=findViewById(R.id.linearLayout);

        Log.i("error",linearLayout.getChildCount()+"");


        nowPlayingMovies.setVisibility(View.GONE);

        getNowPlayingMovies();



        tv=findViewById(R.id.tv);
        liveTv=findViewById(R.id.liveTv);
        movies=findViewById(R.id.movies);
        shows=findViewById(R.id.shows);
        apps=findViewById(R.id.apps);
        account=findViewById(R.id.account);
        search=findViewById(R.id.search);
        wifi=findViewById(R.id.wifi);
        settings=findViewById(R.id.settings);

        for (int in = 1; in < linearLayout.getChildCount(); in++) {

            View v = linearLayout.getChildAt(in);
            setAlpha(v,0.5f);
            v.setOnClickListener(this);
            v.setOnFocusChangeListener(this);
            v.setOnKeyListener(this);

        }





    }






    public void setAlpha(View view,float value){

        view.setAlpha(value);
    }

    @Override
    public void onFocusChange(View view, boolean b) {


        if(!b) {
            view.setBackgroundColor(getResources().getColor(R.color.background));
            setAlpha(view,0.5f);
        }
        if(b) {
            view.setBackgroundColor(getResources().getColor(R.color.onfocus));
            setAlpha(view,1);
        }
        switch (view.getId()){

            case R.id.tv:

                Log.i("cursts"," tv focusChangeListeneer");

                Toast.makeText(this, "tv focusChangeListeneer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.liveTv:
                Log.i("cursts"," live tv focusChangeListeneer");
                Toast.makeText(this, "live tv focusChangeListeneer", Toast.LENGTH_SHORT).show();

                break;
            case R.id.movies:
                Log.i("cursts"," movies focusChangeListeneer");
                Toast.makeText(this, "movies focusChangeListeneer", Toast.LENGTH_SHORT).show();

                break;
            case R.id.shows:
                Log.i("cursts"," shows focusChangeListeneer");

                Toast.makeText(this, "shows focusChangeListeneer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.apps:
                Log.i("cursts"," apps focusChangeListeneer");
                Toast.makeText(this, "apps focusChangeListeneer", Toast.LENGTH_SHORT).show();

                break;


        }

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.nowPlayingMovieImage){

            Log.i("video","playing");

        }

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {



        Log.i("error",keyEvent+"");

        switch (view.getId()){

            case R.id.tv:

                nowPlayingMovies.setVisibility(View.GONE);

                Log.i("cursts"," tv onKeyListener");

                Toast.makeText(this, "tv onKeyListener", Toast.LENGTH_SHORT).show();
                break;
            case R.id.liveTv:
                nowPlayingMovies.setVisibility(View.GONE);
                Log.i("cursts"," live tv onKeyListener");
                Toast.makeText(this, "live tv onKeyListener", Toast.LENGTH_SHORT).show();

                break;
            case R.id.movies:
                nowPlayingMovies.setVisibility(View.VISIBLE);



                Log.i("cursts"," movies onKeyListener");



                final int childcount = nowPlayingMovies.getChildCount();
                for (int in = 0; in < childcount; in++) {


                    View v = nowPlayingMovies.getChildAt(in);
                    setAlpha(v,0.5f);

                    v.setOnFocusChangeListener(this);
                    v.setOnKeyListener(this);
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("video","play");

                            Intent toPlayer=new Intent(MainActivity.this,Player.class);
                            startActivity(toPlayer);


                        }
                    });

//                    v.setBackgroundColor(Color.BLUE);
//                    Log.i("error",childcount+"");

                }

                Toast.makeText(this, "movies onKeyListener", Toast.LENGTH_SHORT).show();


                break;
            case R.id.shows:
                nowPlayingMovies.setVisibility(View.GONE);
                Log.i("cursts"," shows onKeyListener");

                Toast.makeText(this, "shows onKeyListener", Toast.LENGTH_SHORT).show();
                break;
            case R.id.apps:
                nowPlayingMovies.setVisibility(View.GONE);
                Log.i("cursts"," apps onKeyListener");
                Toast.makeText(this, "apps onKeyListener", Toast.LENGTH_SHORT).show();

                break;


        }
        return false;
    }


    public void getNowPlayingMovies(){

        fetching=false;

        Log.i("error","getting movies");


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiCaller myInterface=retrofit.create(ApiCaller.class);
        Call<MovieResults> call=myInterface.listOfMovies(CATEGORY,API_KEY,LANGUAGE,PAGE);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {

                MovieResults results=response.body();
                List<MovieResults.ResultsBean>listOfMovies=results.getResults();
                for(int i=0;i<listOfMovies.size();i++){
                    String content="";
                    content+=listOfMovies.get(i).getTitle()+"\n\n\n";

                    Log.i("error",content);


                    View view=inflater.inflate(R.layout.now_playing_item,nowPlayingMovies,false);
                    ImageView imageView=view.findViewById(R.id.nowPlayingMovieImage);
                    Glide.with(MainActivity.this).load("https://image.tmdb.org/t/p/w500/"+listOfMovies.get(i)
                            .getPoster_path()).into(imageView);
                    nowPlayingMovies.addView(view);



                }

                fetching=true;


            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

                Log.i("error",t+"");
                fetching=true;

            }
        });
    }
}
