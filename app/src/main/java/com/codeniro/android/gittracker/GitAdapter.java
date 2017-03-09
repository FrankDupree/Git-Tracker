package com.codeniro.android.gittracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Dupree on 05/03/2017.
 */
public class GitAdapter extends BaseAdapter {
    private List<User.ItemsBean> gitUsers;
    private Context mContext;
    private LayoutInflater inflater;

public GitAdapter(Context mContext, List<User.ItemsBean> gitUsers){
    this.mContext = mContext;
    this.gitUsers=gitUsers;

}
    @Override
    public int getCount() {
        return gitUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return gitUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.git_card, viewGroup, false);

        User.ItemsBean item = (User.ItemsBean) getItem(i);
        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.thumbnail);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        String imgUrl = item.getAvatar_url();
        Picasso.with(mContext).load(imgUrl).into(thumbnail);
        title.setText(item.getLogin());

        return rowView;
    }


}
