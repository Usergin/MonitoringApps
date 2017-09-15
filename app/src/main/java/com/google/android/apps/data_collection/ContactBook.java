package com.google.android.apps.data_collection;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.repositories.models.BaseEvent;
import com.google.android.apps.repositories.models.Contact;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by OldMan on 19.07.2017.
 */

public class ContactBook {
    private String TAG = ContactBook.class.getSimpleName();
    private Context context;

    public ContactBook() {
        this.context = App.getAppComponent().getContext();
    }

    public Single<List<BaseEvent>> getContactList() {
        List<BaseEvent> contactList = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);

        if ((cursor != null ? cursor.getCount() : 0) > 0) {
            while (cursor.moveToNext()) {
                Contact contact = new Contact();
                StringBuilder infoBuilder = new StringBuilder();
                // get contact id from device db
                int id = cursor.getInt(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
                contact.setDb_id(id);

                contact.setName(cursor
                        .getString(cursor
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

                if (Integer
                        .parseInt(cursor.getString(cursor
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    // -----------------------------------------------------------------------------
                    // get the phone number
                    // -----------------------------------------------------------------------------
                    Cursor numberCursor = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[]{String.valueOf(id)}, null);

                    while (numberCursor != null && numberCursor.moveToNext()) {
                        int type = numberCursor.getInt(numberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        String number = numberCursor
                                .getString(numberCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        switch (type) {
                            case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
                                contact.setMain_number(number);
                                break;
                            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                contact.setHome_number(number);
                                break;
                            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                contact.setNumber(number);
                                break;
                            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                contact.setWork_number(number);
                                break;
                            default:
                                infoBuilder.append("Доп.номер: ").append(' ').append(number);
                                break;
                        }
                    }
                    numberCursor.close();
                }
                // -----------------------------------------------------------------------------
                // get email and type
                // -----------------------------------------------------------------------------
                Cursor emailCur = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID
                                + " = ?", new String[]{String.valueOf(id)}, null);
                StringBuilder eMails = new StringBuilder();
                while (emailCur != null && emailCur.moveToNext()) {
                    // This would allow you get several email addresses
                    // if the email addresses were stored in an array
                    String sEmail = emailCur
                            .getString(emailCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    int type = emailCur
                            .getInt(emailCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                    String label = emailCur
                            .getString(emailCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.LABEL));
                    CharSequence emailType = ContactsContract.CommonDataKinds.Email
                            .getTypeLabel(context.getResources(), type,
                                    label);
                    if (sEmail != null)
                        eMails.append(sEmail)
                                .append(' ')
                                .append(emailType)
                                .append(' ')
                                .append(label)
                                .append('\n');
                }
                emailCur.close();
                contact.setE_mail(eMails.toString());

                // -----------------------------------------------------------------------------
                // get note
                // -----------------------------------------------------------------------------
                String noteWhere = ContactsContract.Data.CONTACT_ID
                        + " = ? AND " + ContactsContract.Data.MIMETYPE
                        + " = ?";
                String[] noteWhereParams = new String[]{
                        String.valueOf(id),
                        ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};

                Cursor noteCursor = cr.query(
                        ContactsContract.Data.CONTENT_URI, null, noteWhere,
                        noteWhereParams, null);
                if (noteCursor != null) {
                    infoBuilder.append("Заметка: ").append('\n');
                    if (noteCursor.moveToFirst()) {
                        String note = noteCursor
                                .getString(noteCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                        if (note != null)
                            infoBuilder.append(note).append('\n');
                    }
                    noteCursor.close();
                }

                // -----------------------------------------------------------------------------
                // Get Instant Messenger.........
                // -----------------------------------------------------------------------------
                String imWhere = ContactsContract.Data.CONTACT_ID
                        + " = ? AND " + ContactsContract.Data.MIMETYPE
                        + " = ?";
                String[] imWhereParams = new String[]{
                        String.valueOf(id),
                        ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
                        null, imWhere, imWhereParams, null);
                if (imCur != null) {
                    infoBuilder.append("Messenger:").append('\n');
                    if (imCur.moveToFirst()) {
                        String imName = imCur
                                .getString(imCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                        String imType = getMessage(imCur
                                .getInt(imCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE)));
                        infoBuilder.append(imType).append(' ').append(imName).append('\n');
                    }
                    imCur.close();
                }
                // -----------------------------------------------------------------------------
                // Get Organizations.........
                // -----------------------------------------------------------------------------

                String orgWhere = ContactsContract.Data.CONTACT_ID
                        + " = ? AND " + ContactsContract.Data.MIMETYPE
                        + " = ?";
                String[] orgWhereParams = new String[]{
                        String.valueOf(id),
                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                        null, orgWhere, orgWhereParams, null);
                if (orgCur != null) {
                    infoBuilder.append("Организация:").append('\n');
                    if (orgCur.moveToFirst()) {
                        String orgName = orgCur
                                .getString(orgCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                        String title = orgCur
                                .getString(orgCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                        if (orgName != null || title != null)
                            infoBuilder.append(orgName).append(' ').append(title).append('\n');
                    }
                    orgCur.close();
                }
                contact.setInfo(infoBuilder.toString());
                contactList.add(contact);
            }
            cursor.close();

        }
        Log.d(TAG, "contactList : " + contactList.size());

        return Single.just(contactList);
    }

    private String getMessage(int imType) {
        String type;
        switch (imType) {
            case ContactsContract.CommonDataKinds.Im.PROTOCOL_AIM:
                type = "AIM";
                break;
            case ContactsContract.CommonDataKinds.Im.PROTOCOL_GOOGLE_TALK:
                type = "Google Talk";
                break;
            case ContactsContract.CommonDataKinds.Im.PROTOCOL_ICQ:
                type = "ICQ";
                break;
            case ContactsContract.CommonDataKinds.Im.PROTOCOL_JABBER:
                type = "Jabber";
                break;
            case ContactsContract.CommonDataKinds.Im.PROTOCOL_MSN:
                type = "MSN";
                break;
            case ContactsContract.CommonDataKinds.Im.PROTOCOL_NETMEETING:
                type = "NetMeeting";
                break;
            case ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ:
                type = "QQ";
                break;
            case ContactsContract.CommonDataKinds.Im.PROTOCOL_SKYPE:
                type = "Skype";
                break;
            case ContactsContract.CommonDataKinds.Im.PROTOCOL_YAHOO:
                type = "Yahoo";
                break;
            default:
                type = "custom";
                break;
        }
        return type;

    }
}
