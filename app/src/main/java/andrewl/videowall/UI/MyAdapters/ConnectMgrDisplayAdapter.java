package andrewl.videowall.UI.MyAdapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import andrewl.videowall.DataBase.UserInfo.MgrDisplayData;
import andrewl.videowall.R;

/**
 * Created by liut1 on 8/22/16.
 */
public class ConnectMgrDisplayAdapter extends RecyclerView.Adapter<ConnectMgrDisplayAdapter.MyHeadViewHolder> {
    private MyItemClickListener mItemClickListener;
    private MyItemLongClickListener mItemLongClickListener;
    public interface MyItemClickListener {
        public void onItemClick(View view, MgrDisplayData data);
    }
    public interface MyItemLongClickListener {
        public void onItemLongClick(View view, MgrDisplayData data);
    }
    private Context mContext;
    private List<MgrDisplayData> mData;
    private List<MgrDisplayData> mData1 = new ArrayList<>();
    public ConnectMgrDisplayAdapter(List<MgrDisplayData>data,Context context){
        mData = data;
        mContext = context;
    }
    @Override
    public MyHeadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHeadViewHolder holder = null;
        View view;
//        if(viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_connect_manage_display_item_group, parent, false);
            holder = new MyHeadViewHolder(view);
//        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHeadViewHolder holder, int position) {
        Log.e("ajkdjfa","deng yu 0");
        if(mData.get(position).getType() == 1){
            holder.group.setText(mData.get(position).getGroup());
            Log.e("ajkdjfa","deng yu 1");
        }else {
            Log.e("ajkdjfa","deng yu 2");
//            MgrDisplayData mgrDisplayData = new MgrDisplayData();
//            mgrDisplayData.setLocalPicAddr("/aa/bb/cc/cc");
//            mgrDisplayData.setLocalVideoAddr("http://jad/ajdkf/dksjf/com.mp4");
//            mgrDisplayData.setGroup("default");
//            mgrDisplayData.setType(1);
//            mData1.add(mgrDisplayData);
//            MgrDisplayData mgrDisplayData2 = new MgrDisplayData();
//            mgrDisplayData2.setLocalPicAddr("/aa/bb/cc/cc");
//            mgrDisplayData2.setLocalVideoAddr("http://jad/ajdkf/dksjf/com.mp4");
//            mgrDisplayData2.setGroup("default");
//            mgrDisplayData2.setType(2);
//            mData1.add(mgrDisplayData2);
            mData1.addAll(mData);
            holder.mAdapterContent = new ConnectMgrDisplayContentAdapter(mData1);
            holder.mRecyclerViewContent.setAdapter(holder.mAdapterContent);
            holder.mAdapterContent.notifyDataSetChanged();
//            holder.mAdapterContent.setOnItemClickListener(new ConnectMgrDisplayContentAdapter.ContentClickListener() {
//                @Override
//                public void onItemClick(View view, MgrDisplayData data) {
//                    Log.e("ItemClickListener","content short click");
//                }
//            });
//            holder.mAdapterContent.setOnItemLongClickListener(new ConnectMgrDisplayContentAdapter.ContentLongClickListener() {
//                @Override
//                public void onItemLongClick(View view, MgrDisplayData data) {
//                    Log.e("ItemLongClickListener","content long click");
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for(int i=0;i<mData.size();i++){
            if(mData.get(i).getType() == 1){
                count += 1;
            }
        }
//        return count;
        return mData.size();
    }
    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return mData.get(position).getType();
    }

    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }

    public class MyHeadViewHolder extends RecyclerView.ViewHolder   implements View.OnClickListener,View.OnLongClickListener{
        RecyclerView con;
        TextView group;
        MyItemClickListener mlistener;
        MyItemLongClickListener mLonglistener;
        ConnectMgrDisplayContentAdapter mAdapterContent;
        RecyclerView mRecyclerViewContent;
        public MyHeadViewHolder(View view) {
            super(view);
            mlistener = mItemClickListener;
            mLonglistener = mItemLongClickListener;

            group = (TextView)view.findViewById(R.id.group);
            group.setOnClickListener(this);
            group.setOnLongClickListener(this);

            con = (RecyclerView)view.findViewById(R.id.content_recycler);
            mRecyclerViewContent = (RecyclerView)view.findViewById(R.id.content_recycler);
//            mRecyclerViewContent.setHasFixedSize(true);
//            mAdapterContent = new ConnectMgrDisplayContentAdapter(mData);
            mRecyclerViewContent.setLayoutManager(new LinearLayoutManager(view.getContext()));
//            mRecyclerViewContent.setAdapter(mAdapterContent);
//            mAdapterContent.setOnItemClickListener(new ConnectMgrDisplayContentAdapter.ContentClickListener() {
//                @Override
//                public void onItemClick(View view, MgrDisplayData data) {
//                    Log.e("ItemClickListener","content short click");
//                }
//            });
//            mAdapterContent.setOnItemLongClickListener(new ConnectMgrDisplayContentAdapter.ContentLongClickListener() {
//                @Override
//                public void onItemLongClick(View view, MgrDisplayData data) {
//                    Log.e("ItemLongClickListener","content long click");
//                }
//            });
        }

        @Override
        public void onClick(View v) {
            if(v == group){
//                if(con.getVisibility() == View.VISIBLE){
//                    con.setVisibility(View.GONE);
//                }
//                else {
//                    con.setVisibility(View.VISIBLE);
//                }
//                if(con.getVisibility() == View.VISIBLE){
//                    con.setVisibility(View.GONE);
//                }
//                else {
//                    con.setVisibility(View.VISIBLE);
//                }
            }else {

            }
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
