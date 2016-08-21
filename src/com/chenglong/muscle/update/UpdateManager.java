package com.chenglong.muscle.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.chenglong.muscle.R;
import com.chenglong.muscle.util.MyPackageUtil;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateManager {

	private Context mContext;
	private final int CHECK_FINISH = 1;
	private final int DOWNLOAD = 2;
	private final int DOWNLOAD_FINISH = 3;
	private String PATH;
	private HashMap<String, String> map;
	private ProgressBar progress;
	private int progressValue;
	private boolean updateCancel = false;
	private boolean checkCancel = false;
	private AlertDialog downloadDialog;
	private AlertDialog checkDialog;
	private int lengh = 0;
	private TextView tv;
	private String UPDATE_XML_FILE;
	private final String UPDATE_XML_URL = "https://raw.githubusercontent.com/abrums21/muscleCaptain/master/publish/version.xml";
	private boolean backUpdate = false;
	private Notification notification;
	private NotificationManager mNotificationManager;
	private int count = 0;
	private final int FRESH_INTERVAL = 10;
	private final int NOTIFY_UPDATE_ID = 1;
	
	private Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) 
		{
			switch (msg.what) {
			case CHECK_FINISH:
				/* 安装软件包  */
				 checkVersion();
				break;
			case DOWNLOAD:
				/* 更新进度条 */
				updateProgress(progressValue);
				break;
             case DOWNLOAD_FINISH:
				/* 安装软件包  */
            	 updateProgress(100);
				break;
			default:
				break;
			}
		};
	};
	
	public UpdateManager(Context context)
	{
		this.mContext = context;
		try
		{
		    PATH = context.getExternalCacheDir().getAbsolutePath() + "/update/";
		    UPDATE_XML_FILE = PATH + "version.xml";
		}
		catch(Exception e)
		{
			PATH = "";
		}
		
		backUpdate = false;
		checkCancel = false;
		updateCancel = false;
	}
	
	private void installApk()
	{
		File apk = new File(PATH + map.get("name") + ".apk");
		if (!apk.exists())
		{
			return;
		}
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apk.toString()), "application/vnd.android.package-archive");
		
		if (true == backUpdate)
		{
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent pi = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT); 
			notification.contentIntent = pi;
			mNotificationManager.notify(NOTIFY_UPDATE_ID, notification);
			Toast.makeText(mContext, "更新下载完完毕，去状态栏点击安装", Toast.LENGTH_SHORT).show();
		}
		else
		{
			mContext.startActivity(intent);
		}
	}
	
	
	private void updateProgress(int progressValue)
	{
		if (true == backUpdate)
		{		
			    if (++count == FRESH_INTERVAL || 100 == progressValue)
			    {
			    	count = 0;
			        RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.notification_update);
			        view.setProgressBar(R.id.notify_progress, 100, progressValue, false);
			        if (100 == progressValue)
			        {
			            view.setTextViewText(R.id.notify_value, "下载完毕，点击安装");
			        }
			        else
			        {
			        	view.setTextViewText(R.id.notify_value, progressValue * lengh / 100 /1000 + "/" +lengh / 1000 + "KB");
			        }
			        notification.contentView = view;
			        mNotificationManager.notify(NOTIFY_UPDATE_ID, notification);
			    }
		}
		else
		{
		    progress.setProgress(progressValue);
		    tv.setText(progressValue * lengh / 100 /1000 + "/" +lengh / 1000 + "KB");  
		}
		
	    if (100 == progressValue)
	    {
       	     installApk();
	    }
	}
	
	public void checkUpdate()
	{		
		showCheckDialog();
		
		/* 下载更新配置文件 */
		downloadVesionFile();
	}
	
	public void checkVersion()
	{		
		if (true == checkCancel)
		{
			return;
		}
		
		if (isUpdate())
		{
			checkDialog.dismiss();
			showUpdateDialog();
		}
		else
		{
			//Toast.makeText(mContext, "已是最新版本", Toast.LENGTH_LONG).show();
			checkDialog.setMessage("已是最新版本");
		}
		
		File apk = new File(PATH + map.get("name") + ".apk");
		if (apk.exists())
		{
			apk.delete();
		}
	}
	
	
	private boolean isUpdate() {
		// TODO Auto-generated method stub
		int versionCode = MyPackageUtil.getAppVersionCode(mContext);
		
		//downloadVesionFile();
		//InputStream is = ParseXmlService.class.getClassLoader().getResourceAsStream("version.xml");
		ParseXmlService service = new ParseXmlService();
		
		try {
			map = service.parseXml(UPDATE_XML_FILE);
		} catch (Exception e) {
			return false;
		}
		
		if (versionCode < Integer.parseInt(map.get("version")))
		{
			return true;
		}
		
		return false;
	}

	
	private void showUpdateDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		AlertDialog dialog = builder.setTitle("应用版本有更新").setIcon(R.drawable.menu_main_1).setMessage(map.get("info"))
				.setNegativeButton("以后再说", null)
				.setPositiveButton("下载", new AlertDialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						showDownloadDialog();
					}
				}).create();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.alpha = 0.8f;
		dialog.getWindow().setAttributes(lp);
		dialog.show();
	}
	
	private void showDownloadDialog()
	{
		if (PATH.isEmpty())
		{
			Toast.makeText(mContext, "无存储路径，下载失败，请确认", Toast.LENGTH_LONG).show();
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("软件版本有更新").setIcon(R.drawable.menu_main_1)
				.setNegativeButton("取消", new AlertDialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						updateCancel = true;
						dialog.dismiss();		
						
					}
				}).setPositiveButton("后台运行", new AlertDialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(mContext, "后台运行，可在通知栏查看", Toast.LENGTH_SHORT).show();
						dialog.dismiss();
						backUpdate = true;
						setUpdateNotification();
					}

				});
		View view = LayoutInflater.from(mContext).inflate(R.layout.update_download, null);
		progress = (ProgressBar) view.findViewById(R.id.update_progress);
		tv = (TextView) view.findViewById(R.id.update_tv);
		builder.setView(view);

		downloadDialog = builder.create();
		WindowManager.LayoutParams lp = downloadDialog.getWindow().getAttributes();
		lp.alpha = 0.8f;
		downloadDialog.getWindow().setAttributes(lp);
		downloadDialog.show();
		
		downloadApk();
	}
	
	private void showCheckDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		checkDialog = builder.setTitle("正在检查更新").setIcon(R.drawable.menu_main_1).setMessage("请耐心等待")
				.setNegativeButton("取消", new AlertDialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						checkCancel = true;
					}
				}).create();
		WindowManager.LayoutParams lp = checkDialog.getWindow().getAttributes();
		lp.alpha = 0.8f;
		checkDialog.getWindow().setAttributes(lp);
		checkDialog.show();
	}
	
	void downloadApk()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					URL url = new URL(map.get("url"));
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					
					lengh = conn.getContentLength();
					InputStream is = conn.getInputStream();
					
					File file = new File(PATH);
					if (!file.exists())
					{
						file.mkdir();
					}
					
					File apk = new File(PATH + map.get("name") + ".apk");
					FileOutputStream os = new FileOutputStream(apk);
					byte buffer[] = new byte[1024];
					int count = 0;
					
					do{
						
						int num = is.read(buffer);
						count += num;
						progressValue = count * 100 / lengh;
						mHandler.sendEmptyMessage(DOWNLOAD);
						
						if (num <= 0)
						{
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						
						os.write(buffer, 0 ,num);
						
					}while(!updateCancel);
					is.close();
					os.close();
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				downloadDialog.dismiss();
		
			}
		}).start();
	}
	
	
	private void downloadVesionFile()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					URL url = new URL(UPDATE_XML_URL);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					
					lengh = conn.getContentLength();
					InputStream is = conn.getInputStream();
					
					File file = new File(PATH);
					if (!file.exists())
					{
						file.mkdir();
					}
					
					File xmlFile = new File(UPDATE_XML_FILE);
					FileOutputStream os = new FileOutputStream(xmlFile);
					byte buffer[] = new byte[1024];
					
					int num = 0;
					while((num = is.read(buffer)) > 0)
					{											
						os.write(buffer, 0 ,num);
						
					}
					is.close();
					os.close();
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}	
				finally {
					mHandler.sendEmptyMessage(CHECK_FINISH);
				}
			}
		}).start();
	}
	
	
	private void setUpdateNotification() {
		// TODO Auto-generated method stub		
		mNotificationManager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);  
		
        RemoteViews mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.notification_update);
        
		Notification.Builder builder = new Notification.Builder(mContext);
		builder.setSmallIcon(R.drawable.icon_small);
		builder.setContentTitle("美队健身更新中");
		builder.setSubText("新版本下载中");
		builder.setWhen(System.currentTimeMillis());
		builder.setTicker("美队健身新版本下载");
		builder.setContent(mRemoteViews);
		
		
		notification = builder.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
//		notification.contentView = mRemoteViews;
		mNotificationManager.notify(NOTIFY_UPDATE_ID, notification);
	}
}
