package com.sandun.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sandun.pagingSysyem.adapter.AdapterPagingItem;
import com.sandun.pagingSysyem.model.PagingSource;
import com.sandun.pagingSysyem.model.callback.PagingCustomViewCallBack;

import java.util.List;

import okhttp3.HttpUrl;

public class MainActivity extends AppCompatActivity {
    private Gson gson = new Gson();

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
        NestedScrollView scrollView = findViewById(R.id.scrollView);
        RecyclerView itemContainer = findViewById(R.id.item_container);

        HttpUrl.Builder getUrlBuilder = HttpUrl.parse("http://192.168.1.27:80/babyhub/test.php").newBuilder();

        new PagingSource<>(getUrlBuilder, 3, scrollView, itemContainer, this, true, new PagingCustomViewCallBack<ItemDTO>() {
            @Override
            public void handleViewCreate(ItemDTO data, AdapterPagingItem.ViewHolder viewHolder) {
                if (data.getType() == 0) {
                    TextView textView = viewHolder.view.findViewById(R.id.name_text);
                    textView.setText("Name: " + data.getName());
                }
            }

            @Override
            public void handleRequestData(String data, PagingSource<ItemDTO> source, RecyclerView.Adapter adapter, List<ItemDTO> list) {
                System.out.println(data);
                if (data != null && !data.isEmpty()) {
                    JsonObject res = JsonParser.parseString(data).getAsJsonObject();
                    JsonArray array = res.getAsJsonArray("list");
                    if (array != null && !array.isEmpty()) {

                        for (JsonElement element : array) {
                            if (element != null) {
                                ItemDTO dto = gson.fromJson(element, ItemDTO.class);
                                list.add(dto);
                            } else {
                                source.setCurrentPage(0);
                            }
                        }
                        MainActivity.this.runOnUiThread(() -> {
                            adapter.notifyItemChanged(list.size() - 1);
                        });
                    } else {
                        source.setPagingStatus(false);
//                        source.setCurrentPage(0);
                    }
                }
            }

            @Override
            public void handleRequestError(Throwable throwable) {
                System.out.println("Got a error while loading item data");
                if (throwable != null) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public int handleViewType(int position, List<ItemDTO> list) {
                return list.get(position).getType();
            }

            @Override
            public AdapterPagingItem.ViewHolder createViewHolder(ViewGroup parent, int viewType) {
                View view;
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                if (viewType == 0) {
                    view = inflater.inflate(R.layout.item_view_layout, parent, false);
                } else {
                    view = inflater.inflate(R.layout.item_view_layout2, parent, false);
                }
                return new AdapterPagingItem.ViewHolder(view);
            }
        });
    }
}