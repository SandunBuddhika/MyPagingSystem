package com.sandun.app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sandun.app.api.RestClient;
import com.sandun.app.dto.ItemDTO;
import com.sandun.app.model.PagingSource;
import com.sandun.app.service.ItemService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

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

        PagingSource<ItemService, ItemDTO> source = new PagingSource<>(new RestClient<ItemService>(ItemService.class, "").createService(), 10, findViewById(R.id.scrollView), findViewById(R.id.item_container), this);

    }
}