package android.betwarendpoint.net.cateraapplication.downloader;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class Downloader  implements DownloadReceiver.Listener {
        private final Listener listener;
        private final DownloadManager downloadManager;

        private DownloadReceiver receiver = null;

        private long downloadId = -1;

        public static Downloader newInstance(Listener listener) {
            Context context = listener.getContext();
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            return new Downloader(downloadManager, listener);
        }

        private Downloader(DownloadManager downloadManager, Listener listener) {
            this.downloadManager = downloadManager;
            this.listener = listener;
        }
    String str =   Environment.DIRECTORY_DOWNLOADS + "/app-release.apk";
       public void download(Uri uri) {
            if (!isDownloading()) {
                register();
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle("Abbas Downlaoding " + "app-release" + ".apk");
                request.setDescription("Downloading " + "app-release" + ".apk");
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/app-release.apk");


                downloadId = downloadManager.enqueue(request);
                runThis();
            }
        }



    private final int UPDATE_PROGRESS = 5020;
        private void runThis(){

            new Thread( ()->{
                boolean downloading = true;
                Handler   handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message message) {
                    Log.v("Message" , message.toString());
                        listener.getProcess(message.arg2,message.arg1);
                  }
                };

                while (downloading) {
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadId);
                    Cursor cursor = downloadManager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                    }
                    //Post message to UI Thread
                    Message msg = handler.obtainMessage();
                    msg.what = UPDATE_PROGRESS;
                    //msg.obj = statusMessage(cursor);
                    msg.arg1 = bytes_downloaded;
                    msg.arg2 = bytes_total;
                    handler.sendMessage(msg);
                    cursor.close();
                }



            }).start();


    }


        public boolean isDownloading() {
            return downloadId >= 0;
        }

        private void register() {
            if (receiver == null) {
                receiver = new DownloadReceiver(this);
                receiver.register(listener.getContext());
            }
        }
        @Override
        public void downloadComplete(long completedDownloadId) {
            if (downloadId == completedDownloadId) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                downloadId = -1;
                unregister();
                Cursor cursor = downloadManager.query(query);
                while (cursor.moveToNext()) {
                    getFileInfo(cursor);
                }
                cursor.close();
            }
        }

        private void unregister() {
            if (receiver != null) {
                receiver.unregister(listener.getContext());
            }
            receiver = null;
        }

        private void getFileInfo(Cursor cursor) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                Long id = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                Uri uri = downloadManager.getUriForDownloadedFile(id);
             //   Uri uri =     Uri.parse(str);
                String mimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
                listener.fileDownloaded(uri, mimeType);
            }
        }

        public void cancel() {
            if (isDownloading()) {
                downloadManager.remove(downloadId);
                downloadId = -1;
                unregister();
            }
        }

        public interface Listener {
            void fileDownloaded(Uri uri, String mimeType);
            void getProcess(int total , int byNow);
            Context getContext();
        }
    }
