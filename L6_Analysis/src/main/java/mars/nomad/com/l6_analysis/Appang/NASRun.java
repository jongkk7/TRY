package mars.nomad.com.l6_analysis.Appang;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.concurrent.LinkedBlockingQueue;

public class NASRun {

    private static final String NASRUN_SHARED_PREF_KEY = "NASRun#Call#Complete#v2";
    private static final String NASRUN_MEMBER_KEY = "e4ed69f9f1c0b68e3fb00eb32caf6770";

    private static SharedPreferences _settings = null;
    private static Context __context = null;

    public static void run(Context context, String adKey) {
        try {
            __context = context;
            String userKey = getUserKey(context);
            _settings = context.getSharedPreferences("NASRun" + userKey + "_" + adKey, Context.MODE_PRIVATE);
            if (!_settings.getBoolean(NASRUN_SHARED_PREF_KEY, false)) {
                NetworkTask networkTask = new NetworkTask();
                networkTask.execute(adKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint({"DefaultLocale", "MissingPermission", "HardwareIds"})
    private static String getUserKey(Context context) {
        String userKey = null;
        TelephonyManager telephone = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        assert telephone != null;
        userKey = telephone.getDeviceId();

        if (userKey == null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Service.WIFI_SERVICE);
            if (null != wifiManager) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (null != wifiInfo) {
                    userKey = wifiInfo.getMacAddress();
                    if (userKey != null) userKey = userKey.toUpperCase();
                }
            }
        }

        if (userKey != null) {
            try {
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.update(userKey.getBytes());
                byte data[] = m.digest();
                userKey = "";
                for (int i = 0; i < data.length; i++) {
                    String h = Integer.toHexString(0xFF & data[i]);
                    while (h.length() < 2)
                        h = "0" + h;
                    userKey += h;
                }
            } catch (Exception e) {
                userKey = null;
            }
        }
        return userKey;
    }

    private static class NetworkTask extends AsyncTask<String, Boolean, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String adKey = params[0];
                String userKey = getUserKey(__context);

                NASRunAdvertisingIdClient.AdInfo info = NASRunAdvertisingIdClient.getAdvertisingIdInfo(__context);
                info.isLimitAdTrackingEnabled();
                String advUserId = info.getId();

                URL url = new URL("http://www.appang.kr/nas/api/complete.json.asp");
                String post = "os=a&m=" + NASRUN_MEMBER_KEY + "&a=" + adKey + "&u=" + userKey + "&ua=" + advUserId;

                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setDoInput(true);
                urlConn.setDoOutput(true);
                urlConn.setUseCaches(false);
                urlConn.setRequestMethod("POST");
                urlConn.setAllowUserInteraction(true);
                urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                OutputStream os = urlConn.getOutputStream();
                os.write(post.getBytes("utf-8"));
                os.flush();
                os.close();

                urlConn.connect();
                BufferedInputStream bis = new BufferedInputStream(urlConn.getInputStream());
                ByteArrayOutputStream baf = new ByteArrayOutputStream();
                int read = 0;
                int bufSize = 512;
                byte[] buffer = new byte[bufSize];
                while (true) {
                    read = bis.read(buffer);
                    if (read == -1) {
                        break;
                    }
                    baf.write(buffer, 0, read);
                }

                String html = new String(baf.toByteArray(), "UTF-8");
                JSONObject json = new JSONObject(html);
                if (json.has("result")) {
                    if (json.getInt("result") == -70001)
                        publishProgress(false);
                    else
                        publishProgress(true);
                } else
                    publishProgress(false);
            } catch (Exception e) {
                e.printStackTrace();
                publishProgress(false);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Boolean... progress) {
            if (progress[0]) {
                try {
                    Editor editor = _settings.edit();
                    editor.putBoolean(NASRUN_SHARED_PREF_KEY, true);
                    editor.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            _settings = null;
        }
    }

    private static final class NASRunAdvertisingIdClient {

        public static final class AdInfo {
            private final String advertisingId;
            private final boolean limitAdTrackingEnabled;

            AdInfo(String advertisingId, boolean limitAdTrackingEnabled) {
                this.advertisingId = advertisingId;
                this.limitAdTrackingEnabled = limitAdTrackingEnabled;
            }

            public String getId() {
                return this.advertisingId;
            }

            public boolean isLimitAdTrackingEnabled() {
                return this.limitAdTrackingEnabled;
            }
        }

        public static AdInfo getAdvertisingIdInfo(Context context) throws Exception {
            if (Looper.myLooper() == Looper.getMainLooper())
                throw new IllegalStateException("Cannot be called from the main thread");

            try {
                PackageManager pm = context.getPackageManager();
                pm.getPackageInfo("com.android.vending", 0);
            } catch (Exception e) {
                throw e;
            }

            AdvertisingConnection connection = new AdvertisingConnection();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            if (context.bindService(intent, connection, Context.BIND_AUTO_CREATE)) {
                try {
                    AdvertisingInterface adInterface = new AdvertisingInterface(connection.getBinder());
                    AdInfo adInfo = new AdInfo(adInterface.getId(), adInterface.isLimitAdTrackingEnabled(true));
                    return adInfo;
                } catch (Exception exception) {
                    throw exception;
                } finally {
                    context.unbindService(connection);
                }
            }
            throw new IOException("Google Play connection failed");
        }

        private static final class AdvertisingConnection implements ServiceConnection {
            boolean retrieved = false;
            private final LinkedBlockingQueue<IBinder> queue = new LinkedBlockingQueue<IBinder>(1);

            public void onServiceConnected(ComponentName name, IBinder service) {
                try {
                    this.queue.put(service);
                } catch (InterruptedException localInterruptedException) {
                }
            }

            public void onServiceDisconnected(ComponentName name) {
            }

            public IBinder getBinder() throws InterruptedException {
                if (this.retrieved) throw new IllegalStateException();
                this.retrieved = true;
                return (IBinder) this.queue.take();
            }
        }

        private static final class AdvertisingInterface implements IInterface {
            private IBinder binder;

            public AdvertisingInterface(IBinder pBinder) {
                binder = pBinder;
            }

            public IBinder asBinder() {
                return binder;
            }

            public String getId() throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                String id;
                try {
                    data.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    binder.transact(1, data, reply, 0);
                    reply.readException();
                    id = reply.readString();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
                return id;
            }

            public boolean isLimitAdTrackingEnabled(boolean paramBoolean) throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                boolean limitAdTracking;
                try {
                    data.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    data.writeInt(paramBoolean ? 1 : 0);
                    binder.transact(2, data, reply, 0);
                    reply.readException();
                    limitAdTracking = 0 != reply.readInt();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
                return limitAdTracking;
            }
        }
    }
}
