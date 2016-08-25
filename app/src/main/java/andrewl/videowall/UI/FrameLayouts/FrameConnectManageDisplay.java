package andrewl.videowall.UI.FrameLayouts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import andrewl.videowall.DataBase.UserInfo.MgrDisplayData;
import andrewl.videowall.R;
import andrewl.videowall.UI.MyAdapters.ConnectMgrDisplayAdapter;
import andrewl.videowall.UI.MyAdapters.ConnectMgrDisplayContentAdapter;
import andrewl.videowall.UI.MyAdapters.MyLinearLayout;

/**
 * Created by liut1 on 8/20/16.
 */
public class FrameConnectManageDisplay extends Fragment {
    private View mView;
    private ConnectMgrDisplayAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ConnectMgrDisplayContentAdapter mAdapterContent;
    private RecyclerView mRecyclerViewContent;
    private List<MgrDisplayData> mData = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.frame_connect_manage_display, container, false);

        MgrDisplayData mgrDisplayData = new MgrDisplayData();
        mgrDisplayData.setLocalPicAddr("/aa/bb/cc/cc");
        mgrDisplayData.setLocalVideoAddr("http://jad/ajdkf/dksjf/com.mp4");
        mgrDisplayData.setGroup("default");
        mgrDisplayData.setType(1);
        mData.add(mgrDisplayData);
        MgrDisplayData mgrDisplayData2 = new MgrDisplayData();
        mgrDisplayData2.setLocalPicAddr("/aa/bb/cc/cc");
        mgrDisplayData2.setLocalVideoAddr("http://jad/ajdkf/dksjf/com.mp4");
        mgrDisplayData2.setGroup("default");
        mgrDisplayData2.setType(2);
        mData.add(mgrDisplayData2);
        initMgrHeadList();
//        mAdapter.notifyDataSetChanged();
        return mView;
    }
    private void initMgrHeadList(){
        mRecyclerView = (RecyclerView)mView.findViewById(R.id.manage_recycler);
        mAdapter = new ConnectMgrDisplayAdapter(mData,getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.setLayoutManager(new MyLinearLayout(getContext()));
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ConnectMgrDisplayAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, MgrDisplayData data) {
                Log.e("ItemClickListener","short click");
            }
        });
        mAdapter.setOnItemLongClickListener(new ConnectMgrDisplayAdapter.MyItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, MgrDisplayData data) {
                Log.e("ItemLongClickListener","long click");
            }
        });
    }
}
