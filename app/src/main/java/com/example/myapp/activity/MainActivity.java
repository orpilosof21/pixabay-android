package com.example.myapp.activity;

import android.os.Bundle;

import com.example.myapp.R;
import com.example.myapp.adapter.PixabayImageAdapter;
import com.example.myapp.model.PixabayImage;
import com.example.myapp.model.PixabayResponse;
import com.example.myapp.rest.PixabayService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class     MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://pixabay.com/";
    private static final String API_KEY = "12175339-7048b7105116d7fa1da74220c";
    public static final int INIT_PAGE = 0;
    public static final int PER_PAGE = 3;

    //const

    //fields
    private static Retrofit retrofit = null;
    private RecyclerView recycler_view = null;
    private ArrayList<PixabayImage> cur_image_list = new ArrayList<>();

    private EditText input_text;
    private String query = "";
    private int cur_page = INIT_PAGE;
    private int visibleImageCount, totalImageCount, lastImageCount=0;
    private boolean recyclerViewLoading = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init fields
        input_text = findViewById(R.id.inputQueryText);
        input_text.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode==KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                query= input_text.getText().toString();
                this.cur_page=INIT_PAGE;
                this.cur_image_list.clear();
                connectAndGetApiData();
                return true;
            }
            return false;
        });

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleImageCount = recyclerView.getChildCount();
                totalImageCount = recyclerView.getLayoutManager().getItemCount();
                lastImageCount = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (recyclerViewLoading){
                    if (lastImageCount + visibleImageCount >= totalImageCount){
                        connectAndGetApiData();
                        recyclerViewLoading=false;
                    }
                }
                else{
                    recyclerViewLoading=true;
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = getRetfofit();
        }
        PixabayService pixabayService = retrofit.create(PixabayService.class);
        this.cur_page++;
        Call<PixabayResponse> call = pixabayService.getImages(API_KEY,query,"photo",this.cur_page,PER_PAGE);
        call.enqueue(new Callback<PixabayResponse>() {
            @Override
            public void onResponse(Call<PixabayResponse> call, Response<PixabayResponse> response) {
                List<PixabayImage> images = response.body().getHits();
                addAndRenderNewData(images);
            }

            @Override
            public void onFailure(Call<PixabayResponse> call, Throwable throwable) {
                Log.println(Log.ERROR,"connectAndGetApiData",throwable.toString());
            }
        });
    }

    private void renderImages(List<PixabayImage> images) {
        recycler_view.setAdapter(new PixabayImageAdapter(images,R.layout.image_item,getApplicationContext()));
    }

    private Retrofit getRetfofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void addAndRenderNewData(List<PixabayImage> images){
        this.cur_image_list.addAll(images);
        renderImages(this.cur_image_list);
    }

}