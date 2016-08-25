package andrewl.videowall.UI.MyAdapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import andrewl.videowall.DataBase.UserInfo.MgrDisplayData;
import andrewl.videowall.R;

/**
 * Created by liut1 on 8/23/16.
 */
public class ConnectMgrDisplayContentAdapter extends RecyclerView.Adapter<ConnectMgrDisplayContentAdapter.MyHeadViewHolder> {
    private ContentClickListener mItemClickListener;
    private ContentLongClickListener mItemLongClickListener;
    public interface ContentClickListener {
        public void onItemClick(View view, MgrDisplayData data);
    }
    public interface ContentLongClickListener {
        public void onItemLongClick(View view, MgrDisplayData data);
    }
    private List<MgrDisplayData> mData;
    public ConnectMgrDisplayContentAdapter(List<MgrDisplayData> data ){
        mData = data;
        Log.e("mdate ","size="+mData.size());
    }
    @Override
    public ConnectMgrDisplayContentAdapter.MyHeadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHeadViewHolder holder = null;
        Log.e("aaaaaaaa","ccccccc");
        View view;
//        if(viewType == 2) {
            Log.e("aaaaaaaa","bbbbbbbbbbb");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_connect_manage_display_item_content, parent, false);
            holder = new MyHeadViewHolder(view);
//        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ConnectMgrDisplayContentAdapter.MyHeadViewHolder holder, int position) {
        Log.e("aaaaaaaaaa","ddddddddd");
        if(TextUtils.isEmpty(mData.get(position).getLocalPicAddr())) {
            holder.picTxt.setText(mData.get(position).getRemotePicUrl());
        }else {
            holder.picTxt.setText(mData.get(position).getLocalPicAddr());
        }

        if(TextUtils.isEmpty(mData.get(position).getLocalVideoAddr())) {
            holder.vidTxt.setText(mData.get(position).getRemoteVideoUrl());
        }else {
            holder.vidTxt.setText(mData.get(position).getLocalVideoAddr());
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for(int i=0;i<mData.size();i++){
            if(mData.get(i).getType() == 2){
                count += 1;
            }
        }
//        return count;
        return mData.size();
    }

    public void setOnItemClickListener(ContentClickListener listener){
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(ContentLongClickListener listener){
        this.mItemLongClickListener = listener;
    }
    public class MyHeadViewHolder extends RecyclerView.ViewHolder   implements View.OnClickListener,View.OnLongClickListener {
        ContentClickListener mlistener;
        ContentLongClickListener mLonglistener;
        TextView picTxt, vidTxt;
        ImageView picImg, vidImg;
        LinearLayout contentPic, contentVid;

        public MyHeadViewHolder(View view) {
            super(view);
            Log.e("aaaaaaaaaa","eeeeeeeeeee");
            mlistener = mItemClickListener;
            mLonglistener = mItemLongClickListener;
            picTxt = (TextView)view.findViewById(R.id.pic_txt);
            picImg = (ImageView)view.findViewById(R.id.pic_img);
            vidTxt = (TextView)view.findViewById(R.id.vid_txt);
            vidImg = (ImageView)view.findViewById(R.id.vid_img);
            contentPic = (LinearLayout)view.findViewById(R.id.content_pic);
            contentPic.setOnClickListener(this);
            contentPic.setOnLongClickListener(this);
            contentVid = (LinearLayout)view.findViewById(R.id.content_vid);
            contentVid.setOnClickListener(this);
            contentVid.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mlistener != null){
                mlistener.onItemClick(v,mData.get(getAdapterPosition()));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(mLonglistener != null){
                mLonglistener.onItemLongClick(v,mData.get(getAdapterPosition()));
            }
            return true;
        }
    }

}
