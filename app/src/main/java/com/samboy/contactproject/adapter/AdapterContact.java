package com.samboy.contactproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samboy.contactproject.R;
import com.samboy.contactproject.model.ContactModel;

import java.util.List;

/**
 * Created by Hari Group Unity on 02-01-2018.
 */

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder> {

    Context mContext;
    List<ContactModel> mList;

    public AdapterContact(Context context, List<ContactModel> mList){
        this.mContext=context;
        this.mList=mList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ada_contact,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        ContactModel mCm=mList.get(position);
        holder.mView.setTag(position);
        holder.txtName.setText(mCm.getName());
        holder.txtEmail.setText(mCm.getEmail());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

     class ContactViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView txtName;
        TextView txtEmail;

        public ContactViewHolder(View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
            txtEmail=itemView.findViewById(R.id.txtEmail);
            mView=itemView;
        }
    }
}
