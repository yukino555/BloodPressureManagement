package example.com.bloodpressuremanagementuser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class ResetDialogFragment extends DialogFragment {
    DatabaseHelper helper = null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_msg);
        builder.setPositiveButton(R.string.dialog_btn_ok, new DialogButtonClickListener());
        builder.setNegativeButton(R.string.dialog_btn_ng, new DialogButtonClickListener());

        AlertDialog dialog = builder.create();
        return dialog;
    }
    private class DialogButtonClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            String msg = "";
            switch (which){
                     /*
                     データベース削除処理
                     */
                case DialogInterface.BUTTON_POSITIVE:
                    if(helper == null){
                        helper = new DatabaseHelper(getContext());
                    }
                    SQLiteDatabase db = helper.getReadableDatabase();
                    try{
                        db.execSQL("DELETE FROM _BPtable");
                    } finally {
                        db.close();
                    }
//                    new HomeActivity().onAvg(null);
                    msg = getString(R.string.dialog_ok_toast);
                    Toast ts = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
                    ts.setGravity(Gravity.CENTER, 0, 0);
                    ts.show();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
//                    msg = getString(R.string.dialog_ng_toast);
                    break;
            }

        }
    }
}
