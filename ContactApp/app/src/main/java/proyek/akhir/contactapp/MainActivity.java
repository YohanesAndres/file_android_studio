package proyek.akhir.contactapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CONTACT_LOAD = 110;
    private static final int CONTACT_SELECT = 120;
    private static final int CONTACT_REQUEST_CODE = 101;
    private static final int CALL_REQUEST_CODE = 102;

    private ListView lvContact;
    private ProgressBar progressBar;
    private ContactViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = findViewById(R.id.lv_contact);
        progressBar = findViewById(R.id.progress_bar);

        lvContact.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);

        mAdapter = new ContactViewAdapter(MainActivity.this, null, true);
        lvContact.setAdapter(mAdapter);
        lvContact.setOnItemClickListener(this);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
        {
            LoaderManager.getInstance(this).initLoader(CONTACT_LOAD, null, this);
        }
        else
        {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, CONTACT_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
        long mContactId = cursor.getLong(0);
        Log.d(TAG, "Position : " + position + " "+ mContactId);
        getPhoneNumber(String.valueOf(mContactId));
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        CursorLoader mCursorLoader = null;
        if(id == CONTACT_LOAD)
        {
            progressBar.setVisibility(View.VISIBLE);

            String[] projecttionFields = new String[]
                    {
                            ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.Contacts.PHOTO_URI
                    };

            mCursorLoader = new CursorLoader(MainActivity.this,
                    ContactsContract.Contacts.CONTENT_URI,
                    projecttionFields,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1",
                    null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        }
        else if(id == CONTACT_SELECT)
        {
            String[] phoneProjectionFields = new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER};

            mCursorLoader = new CursorLoader(MainActivity.this,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    phoneProjectionFields,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ? AND " +
                    ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE + " AND " +
                    ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + "=1",
                    new String[]{args.getString("id")},
                    null);
        }


        return mCursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished");

        if(loader.getId() == CONTACT_LOAD)
        {
            if(data.getCount() > 0)
            {
                lvContact.setVisibility(View.VISIBLE);
                mAdapter.swapCursor(data);
            }
            progressBar.setVisibility(View.GONE);
        }
        else if(loader.getId() == CONTACT_SELECT)
        {
            String contactNumber = null;
            if(data.moveToFirst())
            {
                contactNumber = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
            {
                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactNumber));
                startActivity(dialIntent);
            }
            else
            {
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
                }
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if(loader.getId() == CONTACT_LOAD)
        {
            progressBar.setVisibility(View.GONE);
            mAdapter.swapCursor(null);
            Log.d(TAG, "onLoaderReset");
        }
    }

    private void getPhoneNumber(String contactID)
    {
        Bundle bundle = new Bundle();
        bundle.putString("id", contactID);

        LoaderManager.getInstance(this).restartLoader(CONTACT_SELECT, bundle, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CONTACT_REQUEST_CODE)
        {
            if(grantResults.length > 0)
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    LoaderManager.getInstance(this).initLoader(CONTACT_LOAD, null, this);
                    Toast.makeText(this, "Contact Permission Accepted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Contact Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if(requestCode == CALL_REQUEST_CODE)
        {
            if(grantResults.length > 0)
            {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Call Permission Accepted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Call Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}