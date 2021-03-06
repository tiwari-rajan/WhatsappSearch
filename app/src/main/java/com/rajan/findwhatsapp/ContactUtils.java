package com.rajan.findwhatsapp;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * @author Rajan
 */
public class ContactUtils {

    /**
     * @param activity
     * @param name
     * @param contact
     */
    public static void performContactAction(Activity activity, String name, String contact) {
        if (!contactExists(activity, contact)) {
            ArrayList<ContentProviderOperation> ops = new ArrayList<>();
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

          /*  if (name != null) {
                ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name).build());
            }
*/
            if (contact != null) {
                ops.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());
            }
            try {
                activity.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
//        AppUtils.openWhatsAppChat(activity, mCountry.getdCode(), contact);
    }

    /**
     * @param _activity
     * @param number
     * @return
     */
    private static boolean contactExists(Activity _activity, String number) {
        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = _activity.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            assert cur != null;
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }

    /**
     * @param phoneNumber
     * @param context
     * @return
     */
    public static String getContactName(final String phoneNumber, Context context) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
        String contactName = "";
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                contactName = cursor.getString(0);
            }
            cursor.close();
        }
        return contactName;
    }

    /**
     * @param activity
     * @param name
     * @return Contact id / 0
     */
    public static int getContactIdForWhatsAppCall(Activity activity, String name) {
        Cursor cursor = activity.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.Data._ID}, ContactsContract.Data.DISPLAY_NAME + "=? and " + ContactsContract.Data.MIMETYPE + "=?", new String[]{name, "vnd.android.cursor.item/vnd.com.whatsapp.voip.call"}, ContactsContract.Contacts.DISPLAY_NAME);
        assert cursor != null;
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data._ID));
            cursor.close();
            return phoneContactID;
        } else {
            cursor.close();
            return 0;
        }
    }

    /**
     * @param activity
     * @param name
     * @return whatsapp contact is for videocall  / 0
     */
    public static int getContactIdForWhatsAppVideoCall(Activity activity, String name) {
        Cursor cursor = activity.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.Data._ID}, ContactsContract.Data.DISPLAY_NAME + "=? and " + ContactsContract.Data.MIMETYPE + "=?", new String[]{name, "vnd.android.cursor.item/vnd.com.whatsapp.video.call"}, ContactsContract.Contacts.DISPLAY_NAME);
        assert cursor != null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data._ID));
            cursor.close();
            return phoneContactID;
        } else {
            cursor.close();
            return 0;
        }
    }

    /**
     * @param ctx
     * @param phone
     * @param name
     */
    public static void deleteContact(Context ctx, String phone, String name) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
        Cursor cur = ctx.getContentResolver().query(contactUri, null, null, null, null);
        try {
            assert cur != null;
            if (cur.moveToFirst()) {
                do {
                    if (cur.getString(cur.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME)).equalsIgnoreCase(name)) {
                        String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                        ctx.getContentResolver().delete(uri, null, null);
                    }

                } while (cur.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        cur.close();
    }
}

