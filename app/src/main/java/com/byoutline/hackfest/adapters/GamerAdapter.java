package com.byoutline.hackfest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.byoutline.secretsauce.utils.ViewUtils;
import com.byoutline.secretsauce.views.CustomFontTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import com.byoutline.hackfest.App;
import com.byoutline.hackfest.R;
import com.byoutline.hackfest.model.Gamer;

/**
 * @author Sebastian Kacprzak <nait at naitbit.com>
 */
public class GamerAdapter extends ArrayAdapter<Gamer> {

    private LayoutInflater inflater;

    public GamerAdapter(Context ctx) {
        super(ctx, android.R.layout.simple_spinner_item);
        inflater = LayoutInflater.from(ctx);

        ArrayList<Gamer> tmp = new ArrayList<>();
        for(int i=1; i < 32; i++) {
            tmp.add(new Gamer("http://lorempixel.com/200/200/people/" + Integer.toString(i%10) +"/", "Nick " + i,"Other games x" + i));
        }

        addAll(tmp);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (position >= getCount()) {
            return convertView;
        }
        int type = getItemViewType(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gamer_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Gamer gamer = getItem(position);
        if(gamer!=null){
            Picasso.with(App.getInstance()).load(gamer.photoUrl).fit().into(holder.mGamerItemPhotoIv);
            ViewUtils.setTextOrClear(holder.mGamerItemNameTv, gamer.nick);
            ViewUtils.setTextOrClear(holder.mGamerItemGamesTv,gamer.games);
        }


        return convertView;

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'gamer_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
     */


    class ViewHolder {
        @InjectView(R.id.gamer_item_photo_iv)
        ImageView mGamerItemPhotoIv;
        @InjectView(R.id.gamer_item_name_tv)
        CustomFontTextView mGamerItemNameTv;
        @InjectView(R.id.gamer_item_games_tv)
        CustomFontTextView mGamerItemGamesTv;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
