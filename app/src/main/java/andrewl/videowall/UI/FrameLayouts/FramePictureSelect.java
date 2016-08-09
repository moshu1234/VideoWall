package andrewl.videowall.UI.FrameLayouts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andrewl.videowall.R;

/**
 * Created by liut1 on 8/9/16.
 */
public class FramePictureSelect extends Fragment {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.frame_picture_select, container, false);
        return mView;
    }
}
