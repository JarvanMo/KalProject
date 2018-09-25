package com.jarvanmo.kal.chooser;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public interface OnChooseListener {
     void onChoose(@NonNull DialogFragment dialogFragment,@NonNull Object content);

}
