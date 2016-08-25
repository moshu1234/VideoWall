package andrewl.videowall.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import andrewl.videowall.DataBase.UserInfo.MgrDisplayData;
import andrewl.videowall.R;
import andrewl.videowall.UI.FrameLayouts.FrameConnectManageDisplay;
import andrewl.videowall.UI.FrameLayouts.FrameConnectManageEdit;
import andrewl.videowall.UI.MyAdapters.ConnectMgrDisplayAdapter;

public class ConnectManageActivity extends AppCompatActivity {

    private List<Fragment> mFragments;
    private Fragment fragment1, fragment2, fragment3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_manage);
        ActionBar actionBar = getSupportActionBar();//高版本可以换成 ActionBar actionBar = getActionBar();
        actionBar.hide();
        addFrame();
    }
    public void addFrame(){
        mFragments = new ArrayList<>();
        fragment1 = new FrameConnectManageDisplay();
        fragment2 = new FrameConnectManageEdit();
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
        transaction.add(R.id.frameLayout,mFragments.get(0));

        transaction.commit();
    }
}
