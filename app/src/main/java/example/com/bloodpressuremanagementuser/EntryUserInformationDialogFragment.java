package example.com.bloodpressuremanagementuser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class EntryUserInformationDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_msg);
        builder.setPositiveButton(R.string.dialog_btn_ok, new DialogButtonClickListener());
        builder.setNegativeButton(R.string.dialog_btn_ng, new DialogButtonClickListener());

        AlertDialog dialog = builder.create();
        return dialog;
    }
    private class DialogButtonClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
//            String msg = "";
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
//                    msg = getString(R.string.dialog_ok_toast);
                    Intent intent = new Intent(getContext(), UserFinishActivity.class);
                    startActivity(intent);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
//                    msg = getString(R.string.dialog_ng_toast);
                    break;
            }
//            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
    }
}
