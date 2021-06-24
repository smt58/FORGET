package com.byye.forget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Bilgilendirme")
                .setMessage("Konum bilgisi yüklendi mesajını bekleyiniz. Aksi takdirde konumunuz algılanmayabilir." +
                        "Anlık konumunuzu veya seçtiğiniz bir konumu kaydedebilirsiniz." +
                        "Anlık konum seçiminde 80 metre ile 150 metre arası size bildirim yollar." +
                        "Seçtiğiniz konumda ise o noktaya varmadan 150 metre önce sizi bilgilendirir. " +
                        "Konum alması için konumunuzun açık olması gerekir. "+
                        "Konum yenilemeden kaynaklı gecikmeden dolayı bazen çalışmayabilir." )
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return builder.create();
    }
}