package com.eyesmart.testapplication.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import com.eyesmart.testapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lwh on 2017/11/21.
 * 权限申请fragment
 */

public class PermissionsUtil extends Fragment {
    private final static String TAG = PermissionsUtil.class.getSimpleName();

    public final static int PERMISSIONS_REQUEST_CODE = 55;

    private PermissionsCallback listener;

    private String[] permissions;

    private boolean isMust = false;

    public static PermissionsUtil getInstance(Activity activity) {
        PermissionsUtil permissionsFragment =
                (PermissionsUtil) activity.getFragmentManager().findFragmentByTag(TAG);
        boolean isNewInstance = permissionsFragment == null;
        if (isNewInstance) {
            permissionsFragment = new PermissionsUtil();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(permissionsFragment, TAG)
                    .commit();
            fragmentManager.executePendingTransactions();
        }
        return permissionsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * 权限不强制调用
     */
    public void requestPermissions(PermissionsCallback listener, @NonNull String... permissions) {
        requestPermissions(listener, false, permissions);
    }

    public void requestPermissions(PermissionsCallback listener, boolean isMust,
            @NonNull String... permissions) {
        this.isMust = isMust;
        this.listener = listener;
        this.permissions = permissions;
        if (!isM()) {
            if (listener != null) {
                this.listener.requestSuccess();
            }
        } else {
            if (!checkPermissions(permissions)) {
                requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
            } else {
                if (listener != null) {
                    this.listener.requestSuccess();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != PERMISSIONS_REQUEST_CODE) return;

        onRequestPermissionsResult(permissions, grantResults);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(String permissions[], int[] grantResults) {
        String[] fails = getFails(permissions, grantResults);
        if (fails.length == 0) {
            if (listener != null) {
                listener.requestSuccess();
            }
        } else {
            //被禁止提示
            if (!getDenied(fails).isEmpty()) {
                startSetting(fails);
            } else {
                if (!isMust) {
                    //权限不是必须的
                    if (listener != null) {
                        listener.requestFail();
                    }
                } else {
                    //权限是必须的
                    requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
                }
            }
        }
    }

    private String[] getFails(String[] permissions, int grantResults[]) {
        List<String> fails = new ArrayList<>();
        for (int i = 0, size = grantResults.length; i < size; i++) {
            boolean granted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            if (!granted) {
                fails.add(permissions[i]);
            }
        }
        return fails.toArray(new String[fails.size()]);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private List<String> getDenied(String[] permissions) {
        List<String> list = new ArrayList<>();
        for (String permission : permissions) {
            if (!shouldShowRequestPermissionRationale(permission)) {
                list.add(permission);
            }
        }
        return list;
    }

    private boolean checkPermissions(String... permissions) {
        for (String permission : permissions) {
            if (!isGranted(permission)) {
                return false;
            }
        }
        return true;
    }

    private boolean isM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isGranted(String permission) {
        return getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean isRevoked(String permission) {
        return getActivity().getPackageManager()
                .isPermissionRevokedByPolicy(permission, getActivity().getPackageName());
    }

    private void startSetting(String[] permissions) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.permission_request)
                .setMessage(getPermissionMessage(permissions))
                .setCancelable(!isMust)
                .setPositiveButton(R.string.permission_go_setting,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent =
                                        new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getActivity().getPackageName(),
                                        null);
                                intent.setData(uri);
                                startActivityForResult(intent, PERMISSIONS_REQUEST_CODE);
                            }
                        }).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private String getPermissionMessage(String[] permissions) {
        StringBuilder builder = new StringBuilder();
        String permissionsStr = Arrays.toString(permissions);

        if (permissionsStr.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || permissionsStr.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            builder.append(
                    String.format("%s、", getString(R.string.permission_READ_EXTERNAL_STORAGE)));
        }
        if (permissionsStr.contains(Manifest.permission.CAMERA)) {
            builder.append(String.format("%s、", getString(R.string.permission_CAMERA)));
        }
        if (permissionsStr.contains(Manifest.permission.READ_SMS)
                || permissionsStr.contains(Manifest.permission.RECEIVE_WAP_PUSH)
                || permissionsStr.contains(Manifest.permission.RECEIVE_MMS)
                || permissionsStr.contains(Manifest.permission.RECEIVE_SMS)
                || permissionsStr.contains(Manifest.permission.SEND_SMS)) {
            builder.append(String.format("%s、", getString(R.string.permission_SMS)));
        }
        if (permissionsStr.contains(Manifest.permission.READ_CALENDAR)
                || permissionsStr.contains(Manifest.permission.WRITE_CALENDAR)) {
            builder.append(String.format("%s、", getString(R.string.permission_CALENDAR)));
        }
        if (permissionsStr.contains(Manifest.permission.READ_CONTACTS)
                || permissionsStr.contains(Manifest.permission.WRITE_CONTACTS)
                || permissionsStr.contains(Manifest.permission.GET_ACCOUNTS)) {
            builder.append(String.format("%s、", getString(R.string.permission_READ_CONTACTS)));
        }
        if (permissionsStr.contains(Manifest.permission.RECORD_AUDIO)) {
            builder.append(String.format("%s、", getString(R.string.permission_RECORD_AUDIO)));
        }
        if (permissionsStr.contains(Manifest.permission.BODY_SENSORS)) {
            builder.append(String.format("%s、", getString(R.string.permission_BODY_SENSORS)));
        }
        if (permissionsStr.contains(Manifest.permission.ACCESS_FINE_LOCATION)
                || permissionsStr.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            builder.append(String.format("%s、", getString(R.string.permission_LOCATION)));
        }
        if (permissionsStr.contains(Manifest.permission.READ_PHONE_STATE)
                || permissionsStr.contains(Manifest.permission.CALL_PHONE)
                || permissionsStr.contains(Manifest.permission.READ_CALL_LOG)
                || permissionsStr.contains(Manifest.permission.WRITE_CALL_LOG)
                || permissionsStr.contains(Manifest.permission.USE_SIP)
                || permissionsStr.contains(Manifest.permission.PROCESS_OUTGOING_CALLS)) {
            builder.append(String.format("%s、", getString(R.string.permission_PHONE)));
        }
        String str = builder.substring(0, builder.length() - 1);
        return String.format("%s%s", str,
                getString(R.string.permission_the_function_will_not_be_able_to_normal_use));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityResult(requestCode);
    }

    /**
     * 检测从设置界面返回到APP时权限申请情况
     * 权限被禁止后不再询问时 在onActivityResult中调用
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode) {
        if (PERMISSIONS_REQUEST_CODE != requestCode) return;
        if (checkPermissions(permissions)) {
            if (listener != null) {
                listener.requestSuccess();
            }
        } else {
            if (isMust) {
                requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
            } else {
                if (listener != null) {
                    listener.requestFail();
                }
            }
        }
    }
}
