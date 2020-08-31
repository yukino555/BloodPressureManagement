package example.com.bloodpressuremanagementuser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class BPEntryDialogFragment extends DialogFragment {
//    public Dialog onCreateDialog(Bundle savedInstanceState){
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage(R.string.dialog_entryMsg);
//        builder.setPositiveButton(R.string.dialog_bt_ok, new DialogButtonClickListener());
//        AlertDialog dialog = builder.create();
//        return dialog;
//    }
    private class DialogButtonClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which){
            switch(which){
                case DialogInterface.BUTTON_POSITIVE:
                    break;
            }

        }
    }
}
