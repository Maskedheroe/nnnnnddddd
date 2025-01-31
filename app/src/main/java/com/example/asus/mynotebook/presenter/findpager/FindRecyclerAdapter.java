package com.example.asus.mynotebook.presenter.findpager;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.mynotebook.R;
import com.example.asus.mynotebook.flags.Flags;
import com.example.asus.mynotebook.model.CollectionBean;
import com.example.asus.mynotebook.model.NoteBean;
import com.example.asus.mynotebook.presenter.mainpager.BlankFragment;
import com.example.asus.mynotebook.utils.GlideImageLoader;
import com.example.asus.mynotebook.utils.GlideLoadUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2018/2/24.
 */

public class FindRecyclerAdapter extends RecyclerView.Adapter<FindRecyclerAdapter.ViewHolder> {


    private final int mSearchOnnote;
    private ArrayList noteList;
    private Context context;


    public FindRecyclerAdapter(List noteBeans, Activity mactivity, int searchOnnote) {
        this.context = mactivity;

        mSearchOnnote = searchOnnote;
        if (searchOnnote == Flags.SEARCH_ONNOTE){
            this.noteList = (ArrayList<NoteBean>) noteBeans;
        }else {
            this.noteList = (ArrayList<CollectionBean>) noteBeans;
        }


    }

    class ViewHolder extends RecyclerView.ViewHolder{


        private ImageView iv_content;
        private TextView course;
        private TextView title;
        private TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            course = itemView.findViewById(R.id.tv_course);
            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
            iv_content = itemView.findViewById(R.id.iv_content);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.findpager_item,null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FindRecyclerAdapter.ViewHolder holder, int position) {
        if (mSearchOnnote == Flags.SEARCH_ONNOTE){
            NoteBean noteBean = (NoteBean) noteList.get(position);
            holder.title.setText(noteBean.getTitle());
            holder.course.setText(noteBean.getCourse());
            if (noteBean.getContent()!=null)
              holder.content.setText(noteBean.getContent());
        }else {
            CollectionBean noteBean = (CollectionBean) noteList.get(position);
//           ArrayList<CollectionBean>noteLists = BlankFragment.getData("语文");
//            CollectionBean noteBean = noteLists.get(position);
            holder.title.setText(noteBean.getTitle());
            holder.course.setText(noteBean.getCourse());
//            GlideImageLoader.glideLoaderByURL(,noteBean.getContentMap(),holder.iv_content);
//           new GlideLoadUtils().glideLoad(context,noteBean.getContentMap(),holder.iv_content,0);

            if (noteBean.getContentMap()!=null) {
                GlideImageLoader.glideLoaderByURL(context, noteBean.getContentMap(),holder.iv_content);
            }
        }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
