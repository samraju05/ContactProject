package com.samboy.contactproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samboy.contactproject.R;
import com.samboy.contactproject.model.AndroidModel;
import com.samboy.contactproject.model.ContactModel;

import java.util.List;

/**
 * Created by Hari Group Unity on 05-01-2018.
 */

public class AdapterAndroid extends RecyclerView.Adapter<AdapterAndroid.AndroidViewHolder>{

    Context mContext;
    List<AndroidModel> mList;

    public AdapterAndroid(Context context, List<AndroidModel> mList){
        this.mContext=context;
        this.mList=mList;
    }

    @Override
    public AndroidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ada_contact,parent,false);
        return new AndroidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AndroidViewHolder holder, int position) {
        AndroidModel am=mList.get(position);
        holder.mView.setTag(position);
        holder.txtName.setText(am.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class AndroidViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView txtName;
        TextView txtEmail;

        public AndroidViewHolder(View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
            txtEmail=itemView.findViewById(R.id.txtEmail);
            mView=itemView;
        }
    }
}
