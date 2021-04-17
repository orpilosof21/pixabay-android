package com.example.myapp.activity;

import android.os.Bundle;

import com.example.myapp.R;
import com.example.myapp.adapter.PixabayImageAdapter;
import com.example.myapp.model.PixabayImage;
import com.example.myapp.model.PixabayResponse;
import com.example.myapp.rest.PixabayService;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class     MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://pixabay.com/";
    private static final String API_KEY = "12175339-7048b7105116d7fa1da74220c";
    public static final int INIT_PAGE = 0;
    public static final int PER_PAGE = 6;

    //const

    //fields
    private static Retrofit retrofit = null;
    private RecyclerView recycler_view = null;
    private ArrayList<PixabayImage> cur_image_list = new ArrayList<>();
    private ArrayList<PixabayImage> fav_image_list = new ArrayList<>();
    private Set<Integer> fav_ids = new HashSet<Integer>();

    private EditText input_text;
    private String query = "";
    private int cur_page = INIT_PAGE;
    private int visibleImageCount, totalImageCount, lastImageCount=0;
    private boolean recyclerViewLoading = true;
    private TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init fields
        initInputText();

        initRecyclerView();

        initTabsView();
    }

    private void initInputText() {
        input_text = findViewById(R.id.inputQueryText);
        input_text.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode== KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                query= input_text.getText().toString();
                this.cur_page=INIT_PAGE;
                this.cur_image_list.clear();
                connectAndGetApiData();
                return true;
            }
            return false;
        });
    }

    private void initTabsView() {
        tabs = findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        renderSearchTab();
                        break;
                    case 1:
                        renderFavTab();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initRecyclerView() {
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new GridLayoutManager(this,2));
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleImageCount = recyclerView.getChildCount();
                totalImageCount = recyclerView.getLayoutManager().getItemCount();
                lastImageCount = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if (tabs.getSelectedTabPosition()==0 && recyclerViewLoading){
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
        if (tabs.getSelectedTabPosition()==1){
            return;
        }
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
                addAndRenderDataCurList(images);
            }

            @Override
            public void onFailure(Call<PixabayResponse> call, Throwable throwable) {
                Log.println(Log.ERROR,"connectAndGetApiData",throwable.toString());
            }
        });
    }

    private void renderImages(List<PixabayImage> images, boolean is_fav_tab) {
        PixabayImageAdapter pixabayImageAdapter = new PixabayImageAdapter(images, R.layout.image_item, getApplicationContext());
        pixabayImageAdapter.setmOnItemClickListener(new PixabayImageAdapter.OnItemClickListener() {
            @Override
            public void onImageClicked(View v, int position, PixabayImage pixabayImage) {
                addToFavList(pixabayImage);
            }
        });
        recycler_view.setAdapter(pixabayImageAdapter);
    }

    private void addToFavList(PixabayImage pixabayImage) {
        if (fav_ids.contains(pixabayImage.getId())){
            fav_image_list.remove(pixabayImage);
            fav_ids.remove(pixabayImage.getId());
        }
        else{
            fav_image_list.add(pixabayImage);
            fav_ids.add(pixabayImage.getId());
        }
    }


    private Retrofit getRetfofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void addAndRenderDataCurList(List<PixabayImage> images){
        this.cur_image_list.addAll(images);
        renderSearchTab();
    }

    public void renderSearchTab() {
        renderImages(this.cur_image_list,false);
    }

    public void renderFavTab() {
        renderImages(this.fav_image_list,true);
    }
}