package com.example.myapp.activity;

import android.os.Bundle;

import com.example.myapp.R;
import com.example.myapp.adapter.PixabayImageAdapter;
import com.example.myapp.model.PixabayImage;
import com.example.myapp.model.PixabayResponse;
import com.example.myapp.rest.PixabayService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://pixabay.com/";
    private static final String API_KEY = "12175339-7048b7105116d7fa1da74220c";

    //const

    //fields
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    private ArrayList<PixabayImage> cur_image_list = new ArrayList<>();

    private EditText inputText;
    private String query = "dogs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init fields
        inputText = findViewById(R.id.inputQueryText);
        inputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_ENTER){
                    query=inputText.getText().toString();
                    connectAndGetApiData();
                    return true;
                }
                return false;
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    // This method create an instance of Retrofit
// set the base url
    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        PixabayService pixabayService = retrofit.create(PixabayService.class);
        Call<PixabayResponse> call = pixabayService.getImages(API_KEY,query,"photo");
        call.enqueue(new Callback<PixabayResponse>() {
            @Override
            public void onResponse(Call<PixabayResponse> call, Response<PixabayResponse> response) {
                List<PixabayImage> images = response.body().getHits();
                saveData(images);
                recyclerView.setAdapter(new PixabayImageAdapter(images,R.layout.image_item,getApplicationContext()));
            }

            @Override
            public void onFailure(Call<PixabayResponse> call, Throwable throwable) {
                Log.println(Log.ERROR,"connectAndGetApiData",throwable.toString());
            }
        });
    }

    private void saveData(List<PixabayImage> images) {
        this.cur_image_list.clear();
        this.cur_image_list.addAll(images);
    }

}