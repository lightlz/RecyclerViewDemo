package com.light.recyclerviewdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyelerView;

    private SampleAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private GridLayoutManager gridLayoutManager;

    private Boolean isLoadMore = false;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //adapter.notifyItemInserted(2);
                adapter.remove(3);
            }
        });

        initView();
    }

    private void initView(){

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        recyelerView = (RecyclerView)findViewById(R.id.recycler);
        //recyelerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setHasFixedSize(true);
        //gridLayoutManager = new GridLayoutManager(this,2);
        //recyelerView.setLayoutManager(gridLayoutManager);

        recyelerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        adapter = new SampleAdapter(this);

        recyelerView.setAdapter(adapter);

        adapter.setmOnItemClickListener(new SampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Snackbar.make(view, "position :" + position, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.v("refresh : ", "refrshing ");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        recyelerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final int itemCount = adapter.getItemCount();
                int lastItemPosition = gridLayoutManager.findLastVisibleItemPosition()+1;

                if (dy > 0 && lastItemPosition >= itemCount-5 && !isLoadMore) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.insertItems(itemCount - 1);
                        }
                    },2000);

                }
            }
        });

//        adapter.setOnLoadingMoreListener(new SampleAdapter.OnLoadingMoreListener() {
//            @Override
//            public void onLoadingMore(final int position) {
//
//                Log.v("Loading More : ",position+"");
//                mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapter.insertItems(position);
//                        }
//                    },1500);
//            }
//        });
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
}
