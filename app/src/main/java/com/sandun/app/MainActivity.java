package com.sandun.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sandun.app.api.RestClient;
import com.sandun.app.dto.ItemDTO;
import com.sandun.app.service.ItemService;
import com.sandun.pagingSysyem.adapter.AdapterPagingItem;
import com.sandun.pagingSysyem.model.PagingSource;
import com.sandun.pagingSysyem.model.callback.PagingCallBack;

import java.util.List;

import okhttp3.HttpUrl;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private List<ItemDTO> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        HttpUrl.Builder getData = HttpUrl.parse(RestClient.BASE_URL + "test.php").newBuilder();
        PagingSource<ItemDTO> source = new PagingSource<>(R.layout.item_view_layout, getData, 10, findViewById(R.id.scrollView), findViewById(R.id.item_container), this, new PagingCallBack<ItemDTO>() {
            @Override
            public void handleViewCreate(ItemDTO data, AdapterPagingItem.ViewHolder holder) {
                System.out.println("data");
            }

            @Override
            public void handleRequestData(String data, PagingSource<ItemDTO> source, RecyclerView.Adapter adapter, List<ItemDTO> list) {
                if (data != null && !data.isEmpty()) {
                    try {
                        JsonObject object = JsonParser.parseString(data).getAsJsonObject();
                        System.out.println("parsed data!!!!!!!!!!!!");
                        JsonArray array = object.getAsJsonArray("response");
                        for (JsonElement element : array) {
                            list.add(new ItemDTO());
                            runOnUiThread(() -> {
                                adapter.notifyItemChanged(list.size() - 1);
                            });
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Couldn't passe to json");
                    }
                } else {
                    Log.e(TAG, "Couldn't passe to json (empty data set)");
                }
            }

            @Override
            public void handleRequestError(Throwable throwable) {
                System.out.println("error");
                throwable.printStackTrace();
                System.out.println(throwable.getMessage());
            }
        });

    }
}