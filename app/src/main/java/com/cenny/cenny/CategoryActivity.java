package com.cenny.cenny;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static com.cenny.cenny.DBQueries.lists;
import static com.cenny.cenny.DBQueries.loadFragmentData;
import static com.cenny.cenny.DBQueries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView categoryActivityRecyclerView;
    private HomePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String Title=getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(Title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        categoryActivityRecyclerView=findViewById(R.id.category_activity_recyclerview);

        LinearLayoutManager testingLayoutManager =new LinearLayoutManager(CategoryActivity.this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryActivityRecyclerView.setLayoutManager(testingLayoutManager);
        int listPosition=0;
        for(int i=0;i<loadedCategoriesNames.size();i++){
            if(loadedCategoriesNames.get(i).equals(Title.toUpperCase())){
                listPosition=i;
            }
        }

        if(listPosition==0){
            loadedCategoriesNames.add(Title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            adapter = new HomePageAdapter(lists.get(loadedCategoriesNames.size()-1));
            loadFragmentData(CategoryActivity.this, adapter,loadedCategoriesNames.size()-1,Title);
        }else{
            adapter = new HomePageAdapter(lists.get(listPosition));
            adapter.notifyDataSetChanged();
        }


        categoryActivityRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_search) {
            //todo:search
            return true;
        }else if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
