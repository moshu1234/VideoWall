package andrewl.videowall.UI.MyWidget;

import android.app.Dialog;
import android.content.Context;

import andrewl.videowall.R;

/**
 * Created by liut1 on 8/25/16.
 */
public class WidgetDialog extends Dialog {
    public WidgetDialog(Context context) {
        super(context);
    }

    public void  init(){
        Dialog dialog = new Dialog(getContext());
//        dialog.setContentView(R.layout.dialog_remind);
//        gifView gif = (gifView)dialog.findViewById(R.id.remind_gif);
//        gif.setMovieResource(R.drawable.remind);
//		gif_run.setPaused(true);
//        int days = getDayInterval(getLastDate(-20),getLastDate(0));
        dialog.show();
    }
}
