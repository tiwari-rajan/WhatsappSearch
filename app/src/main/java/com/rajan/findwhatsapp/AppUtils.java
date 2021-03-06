package com.rajan.findwhatsapp;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * @author Rajan
 */
public class AppUtils {

    /**
     * @param context
     * @param dp
     * @return int (dp into px values)
     */
    public static int getDp2Px(Context context, int dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    /**
     * @param activity hides the soft keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputMethodManager != null)
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void makeCall(Activity activity, String mobile) {
        if (Validator.isEmptyString(mobile) || ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            return;
        try {
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:" + mobile));
            activity.startActivity(phoneIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void openWhatsAppChat(Activity activity, int countryCode, String contact, String message) {
        String toNumber = contact;
        try {
            toNumber = toNumber.replace("+", "").replace(" ", "");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("whatsapp://send?phone=+" + countryCode + toNumber);
            intent.setData(uri);
            intent.setPackage("com.whatsapp");
            activity.startActivity(intent);
        } catch (Exception ex) {
            if (ex instanceof ActivityNotFoundException)
                Toast.makeText(activity, "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void makeWhatsAppCall(Activity activity, String contact) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String toNumber = contact;
            toNumber = toNumber.replace("+", "").replace(" ", "");
            toNumber = "91" + toNumber;
            String name = ContactUtils.getContactName(toNumber, activity);
            int whatsAppCall = ContactUtils.getContactIdForWhatsAppCall(activity, name);
            if (whatsAppCall != 0) {
                intent.setDataAndType(Uri.parse("content://com.android.contacts/data/" + whatsAppCall), "vnd.android.cursor.item/vnd.com.whatsapp.voip.call");
                intent.setPackage("com.whatsapp");
                activity.startActivityForResult(intent, whatsAppCall);
            } else {
                ContactUtils.deleteContact(activity, toNumber, name);
            }
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void makeWhatsAppVideoCall(Activity activity, String contact) {
        try {
            Intent intentVideo = new Intent();
            intentVideo.setAction(Intent.ACTION_VIEW);
            String toNumberVideo = contact;
            toNumberVideo = toNumberVideo.replace("+", "").replace(" ", "");
            toNumberVideo = "91" + toNumberVideo;
            String nameVideo = ContactUtils.getContactName(toNumberVideo, activity);
            int videoCall = ContactUtils.getContactIdForWhatsAppVideoCall(activity, nameVideo);
            if (videoCall != 0) {
                intentVideo.setDataAndType(Uri.parse("content://com.android.contacts/data/" + videoCall), "vnd.android.cursor.item/vnd.com.whatsapp.video.call");
                intentVideo.setPackage("com.whatsapp");
                activity.startActivity(intentVideo);
            } else {
                Toast.makeText(activity, "Contact not available on WhatsApp", Toast.LENGTH_SHORT).show();
            }
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String toCamelCase(String inputString) {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        try {
            char firstChar = inputString.charAt(0);
            char firstCharToUpperCase = Character.toUpperCase(firstChar);
            result = result + firstCharToUpperCase;
            for (int i = 1; i < inputString.length(); i++) {
                char currentChar = inputString.charAt(i);
                char previousChar = inputString.charAt(i - 1);
                if (previousChar == ' ') {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result = result + currentCharToUpperCase;
                } else {
                    char currentCharToLowerCase = Character.toLowerCase(currentChar);
                    result = result + currentCharToLowerCase;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

