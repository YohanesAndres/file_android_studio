package proyek.akhir.contactapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactViewAdapter extends CursorAdapter {
    public ContactViewAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(cursor != null)
        {
            CircleImageView civUser = view.findViewById(R.id.civ_user);
            TextView tvContactName = view.findViewById(R.id.tv_name);

            tvContactName.setText(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)));
            if(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI)) != null)
            {
                civUser.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI))));
            }
            else
            {
                civUser.setImageResource(R.drawable.ic_baseline_person_24);
            }
        }
    }
}
