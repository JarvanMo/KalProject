package com.jarvanmo.kalproject;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jarvanmo.kal.recyclerview.QuickAdapter;
import com.jarvanmo.kalproject.databinding.ActivityMainBinding;
import com.jarvanmo.kalproject.databinding.EmptyViewBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        EmptyViewBinding empty = EmptyViewBinding.inflate(getLayoutInflater());
        final QuickAdapter<String> quickAdapter = new QuickAdapter<>(BR.viewModel,R.layout.item_text_view);
        binding.rc.setEmptyView(empty.getRoot());
        binding.rc.setAdapter(quickAdapter);
        quickAdapter.notifyDataSetChanged();


        empty.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < 10; i++) {
                    String s = i+"";
                    quickAdapter.add(s);
                }
                quickAdapter.notifyDataSetChanged();
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(quickAdapter.getItemCount() > 50){
                    quickAdapter.clear();
                    return;
                }

                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < 10; i++) {

                    strings.add(++x+"");

                }
                quickAdapter.addAll(strings);
                quickAdapter.notifyDataSetChanged();

            }
        });
    }
}
