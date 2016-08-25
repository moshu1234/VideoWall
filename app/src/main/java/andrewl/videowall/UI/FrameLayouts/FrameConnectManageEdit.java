package andrewl.videowall.UI.FrameLayouts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andrewl.videowall.R;

/**
 * Created by liut1 on 8/20/16.
 */
public class FrameConnectManageEdit extends Fragment{
    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.frame_connect_manage_edit, container, false);

        return mView;
    }
}
