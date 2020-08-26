package com.votive.mycatvista.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.votive.mycatvista.R;
import com.votive.mycatvista.adapter.CustomAdapter;
import com.votive.mycatvista.apputils.Utility;
import com.votive.mycatvista.model.ImageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String TAG = this.getClass().getSimpleName();
    private GridView Grid_View;
    private Context mContext;
    private String ImageURL = "https://api.imgur.com/3/gallery/search/1?q=vanilla";
    private SearchView searchView;
    ArrayList<ImageModel>ImageList;
    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        Grid_View = findViewById(R.id.Grid_View);

        if (Utility.isNetworkConnected(mContext)) {
            GetImages();
        }else {
            Utility.ShowToastMessage(mContext,getResources().getString(R.string.internet_connection));
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MIN_VALUE);


        searchView.setQueryHint("Search...");


        EditText searchEditText = searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        ImageView searchMagIcon = searchView.findViewById(R.id.search_button);
        searchMagIcon.setImageResource(R.drawable.ic_search_black_24dp);
        searchMagIcon.setColorFilter(getResources().getColor(R.color.white));
        ImageView searchClose = searchView.findViewById(R.id.search_close_btn);
        searchClose.setImageResource(R.drawable.ic_close_black_24dp);
        searchClose.setColorFilter(getResources().getColor(R.color.white));



       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                if(ImageList!=null && ImageList.size()>0)
                {
                    adapter.getFilter().filter(query);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                if(ImageList!=null && ImageList.size()>0)
                {
                    adapter.getFilter().filter(query);

                }

                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // dashboard is xml file name
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void GetImages() {
        Utility.ShowPregressDialogue(mContext);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ImageURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utility.HidePregressDialogue(mContext);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e(TAG, "response====>" + response);


                            //  Log.e("image_list", String.valueOf(image_list.size()));

                            if (String.valueOf(jsonObject.getInt("status")).equals("200")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");


                                ImageList = new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    if (jsonObject1.has("images")) {
                                        JSONArray jsonArray1 = jsonObject1.getJSONArray("images");
                                        String link = null;
                                        for (int j = 0; j < jsonArray1.length(); j++) {
                                            JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                            link = jsonObject2.getString("link");
                                        }
                                        ImageModel imageModel = new ImageModel();
                                        imageModel.setId(jsonObject1.getString("id"));
                                        imageModel.setName(jsonObject1.getString("title"));
                                        imageModel.setImage(link);
                                        ImageList.add(imageModel);

                                        // Log.e("image_list", String.valueOf(image_list.size()));
                                        Log.e("image_list", String.valueOf(jsonObject1.getString("title")+"======>"+link));
                                    }

                                    Log.e("image_list_size", String.valueOf(ImageList.size()));

                                     adapter = new CustomAdapter(MainActivity.this, ImageList);
                                    Grid_View.setAdapter(adapter);

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utility.HidePregressDialogue(mContext);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Client-ID 137cda6b5008a7c");
                return headers;
            }
        };
        queue.add(stringRequest);

    }
}
