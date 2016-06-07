package com.chenglong.muscle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyTipDB {

	private SQLiteDatabase db = null;
	private static String MY_DATABASE_PATH = "/data/data/com.chenglong.muscle";
	private static String MY_DATABASE_FILENAME = "my.db";

	public MyTipDB() {
		openAndCreateDb();
		// createTable();
		return;
	}

	private void openAndCreateDb() {
		// db =
		// SQLiteDatabase.openOrCreateDatabase("/data/data/com.chenglong.muscle/tmp3.db",
		// null);
		db = SQLiteDatabase.openOrCreateDatabase(MY_DATABASE_PATH +"/" + MY_DATABASE_FILENAME, null);
	}

	private void createTable() {
		String sql = "create table tipsTbl(id integer primary key, info text)";
		db.execSQL(sql);
	}

	public void insert(int id, String info) {
		String sql = "insert into tipsTbl(id, info) values(" + id + ", '" + info + "')";
		db.execSQL(sql);
	}

	public void delete(int id) {
		String sql = "delete from tipsTbl where id=" + id;
		db.execSQL(sql);
	}

	public int getNum() {
		Cursor cursor = db.query("tipsTbl", null, null, null, null, null, null);
		return cursor.getCount();
	}

	public String query(int id) {
		String tips = "";
		Cursor cursor = db.query("tipsTbl", new String[] { "info" }, "id=?", new String[] { "" + id }, null, null,
				null);
		while (cursor.moveToNext()) {
			tips = cursor.getString(cursor.getColumnIndex("info"));
		}
		return tips;
	}

	public void close() {
		db.close();
	}

	public static void openDatabase(Context context) {
		try {
			// 获得dictionary.db文件的绝对路径
			String databaseFilename = MY_DATABASE_PATH + "/" + MY_DATABASE_FILENAME;
			File dir = new File(MY_DATABASE_PATH);
			// 如果/sdcard/dictionary目录中存在，创建这个目录
			if (!dir.exists())
				dir.mkdir();
			
			if ((new File(databaseFilename)).exists()) 
			{
				(new File(databaseFilename)).delete();
			}
			// 如果在/sdcard/dictionary目录中不存在
			// dictionary.db文件，则从res\raw目录中复制这个文件到
			// SD卡的目录（/sdcard/dictionary）
			// if (!(new File(databaseFilename)).exists()) {
				// 获得封装dictionary.db文件的InputStream对象
				InputStream is = context.getResources().openRawResource(R.raw.my);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				// 开始复制dictionary.db文件
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}

				fos.close();
				is.close();
			//}
			// 打开/sdcard/dictionary目录中的dictionary.db文件
			//SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stub_tipsDbStore() {
		MyTipDB db = new MyTipDB();
		db.createTable();
		db.insert(1, "为了能在银幕上展示最好的肌肉状态，克里斯·埃文斯每拍一个镜头前都必须进行大量运动，比如说连续400个俯卧撑");
		db.insert(2, "克里斯·埃文斯遵循少食多餐原则，以低碳水化合物、高蛋白为主，在正餐间辅以水果、坚果、运动营养补剂作为加餐");
		db.insert(3, "每部戏之前，克里斯·埃文斯都要根据角色需要，调整自己的体重，当然对于美国队长这样的英雄人物，增加肌肉和瘦体重是主要训练内容。克里斯·埃文斯采用的是分离式力量训练，即每天只训练一个部位的肌肉群");
		db.insert(4,
				"6盎司(170克)的鱼提供34克蛋白质，4克欧米伽-3脂肪酸，一种降低肿痛帮助肌肉修复的健康脂肪，并且它可以帮助抑制皮质醇。(皮质醇水平下降，睾丸酮素的水平就会逐渐升高，帮助肌肉增长。)富含欧米伽-3脂肪酸的饮食还可以让大多数吸收的葡萄糖进入肌肉而不是转化为脂肪");
		db.insert(5,
				"克里斯·埃文斯每天都必须锻炼两小时，强度令人难以想象。“我非常喜欢运动，也经常健身，但这次真是令人崩溃，我练到都快吐了，而我的新陈代谢本来就很快，这是更令人烦躁的，有时候我已经明明吃饱了，连看着一块肌肉都想吐，但你必须汲取蛋白质，所以你必须吃。”");
		db.insert(6, "想增加肌肉，牛肉是不可或缺的。牛肉中天然的蛋白质结构更能满足人体的合成需要。对于健身的人来说，一周要保证2次的牛肉摄入。阿诺德·施瓦辛格在训练期，每天的食物摄入就必须有牛肉的");
		db.insert(7, "鸡蛋中的蛋白质是最易被人体吸收的，是健身后迅速补充蛋白质的首选，同时又能为肌肉合成提供必要的维生素和矿物质");
		db.insert(8, "如果你的并不胖，那就喝全脂奶吧。全脂奶中的短链脂肪对肌肉的合成非常重要，同时能帮助身体吸收更多的维生素，又能预防一些疾病的发生");
		db.insert(9, "酸奶两大作用，一个是含有益菌，帮助身体消化吸收食物。另一个则是含大量的钙元素，能够有效的控制肌肉的收缩过程");
		db.insert(10, "大蒜的作用想不到吧！不含有蛋白质和脂肪的食物如何帮助增长肌肉？最重要的一点是能够提升体内荷尔蒙的含量，来形成一个肌肉合成的环境");
		db.close();
	}
}
