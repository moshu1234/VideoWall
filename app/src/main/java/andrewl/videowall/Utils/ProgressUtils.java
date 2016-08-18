package andrewl.videowall.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

/**
 * Created by liut1 on 8/18/16.
 */
public class ProgressUtils {
    private ProgressDialog progressDialog;
    public ProgressUtils(Context context) {
        progressDialog=ProgressDialog.show(context,"正在上传...","0%",true,false);
    }
    public void setProgressMessage(String msg){
        Log.e("setProgressMessage",msg);
        progressDialog.setMessage(msg);
    }
    public void dismissProgressBar(){
        Log.e("dismissProgressBar","dismiss");
        progressDialog.dismiss();
    }
}
