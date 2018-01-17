package com.samboy.contactproject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.samboy.contactproject.adapter.AdapterAndroid;
import com.samboy.contactproject.adapter.AdapterContact;
import com.samboy.contactproject.model.AndroidModel;
import com.samboy.contactproject.rectroresponce.AndroidList;
import com.samboy.contactproject.rectroresponce.ContactList;
import com.samboy.contactproject.model.ContactModel;
import com.samboy.contactproject.retrofitclient.ApiServices;
import com.samboy.contactproject.retrofitclient.RetroClient;
import com.samboy.contactproject.webservices.NetworUrls;

import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvContact;
    private AdapterContact mAdapter;
    private AdapterAndroid mAdapterAndroid;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intViews();



    }

    private void intViews() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Please wait..");

        rvContact = findViewById(R.id.rvContact);

    }

    private void setupAdapter(List<ContactModel> mList) {
        mAdapter = new AdapterContact(this, mList);
        StaggeredGridLayoutManager lManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvContact.setLayoutManager(lManager);
        rvContact.setAdapter(mAdapter);
    }
    private void setupAdapterAndroid(List<AndroidModel> mList) {
        mAdapterAndroid = new AdapterAndroid(this, mList);
        StaggeredGridLayoutManager lManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvContact.setLayoutManager(lManager);
        rvContact.setAdapter(mAdapter);
    }

    private Call<ContactList> getContactList(ApiServices api) {
        return api.getContactList();
    }

    private Call<AndroidList> getAndroidList(ApiServices api) {
        return api.getAndroidList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAndroid:
                getAndroidList(RetroClient.getApiService(NetworUrls.URL_ANDROID)).enqueue(new Callback<AndroidList>() {
                    @Override
                    public void onResponse(Call<AndroidList> call, Response<AndroidList> response) {
                        AndroidModel am=response.body().getAndroid().get(1);
                        Log.e("RESA",""+am.getName());
                        setupAdapterAndroid(response.body().getAndroid());
                    }

                    @Override
                    public void onFailure(Call<AndroidList> call, Throwable t) {

                    }
                });
                break;
            case R.id.menuConntact:
                getContactList(RetroClient.getApiService(NetworUrls.URL_CONTACT)).enqueue(new Callback<ContactList>() {
                    @Override
                    public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                        setupAdapter(response.body().getContacts());
                    }

                    @Override
                    public void onFailure(Call<ContactList> call, Throwable t) {

                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
