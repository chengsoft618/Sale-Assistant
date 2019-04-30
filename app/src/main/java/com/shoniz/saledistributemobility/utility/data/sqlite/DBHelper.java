package com.shoniz.saledistributemobility.utility.data.sqlite;


import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.shoniz.saledistributemobility.framework.exception.DatabaseException;
import com.shoniz.saledistributemobility.utility.Enums.DBName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DBHelper  {

	public static void deleteDatabase(Context context, DBName dbName) {
		context.deleteDatabase(dbName + ".db");
	}


	public void deleteDbFolder() {
		String name = getPath();
		db.close();

		File f = new File(name);
		if (f.exists())
			f.delete();
	}

	public static String getDatabasePath(Context context) {

		return Environment.getDataDirectory() + "/data/"
				+ context.getPackageName() + "/databases";
	}

	public String getPath() {
		return db.getPath();
	}

	public void attachDataBase(Context context, DBName dbname) throws IOException {
		String path = DBHelper.getDatabasePath(context);
		String sql = "ATTACH DATABASE '" + path + "/" + dbname.toString()
				+ ".db' AS \"" + dbname.toString() + "\";";
		String fileName=path +"/"+ dbname.toString() + ".db";
		File destFile = new File(fileName);
		if (!destFile.exists()) {
			copyDatabaseFromAsset(context, fileName,destFile, "db/" + dbname.toString() + ".db");
		}
		db.execSQL(sql);
	}

	public void detachDataBase(Context context, DBName dbname) {
		String sql = "DETACH DATABASE \"" + dbname.toString() + "\";";
		db.execSQL(sql);
	}

	public SQLiteDatabase getSQLiteDatabase() {
		return db;
	}

	public static DBHelper createFromAssets(final Context context,
											final String destFileName, int version, List<String> assetFiles) throws IOException {

		File destFile = new File(destFileName);
		if (!destFile.exists()) {
			copyDatabaseFromAsset(context, destFileName,  destFile,assetFiles);
		}

		SQLiteDatabase db = new SQLiteOpenHelper(context, destFileName, null,
				version) {

			@Override
			public void onCreate(SQLiteDatabase db) {

			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion,
								  int newVersion) {
			}

		}.getWritableDatabase();

		return new DBHelper(db);
	}

	private static void copyDatabaseFromAsset(Context context, String destFileName,  File destFile,String... assetFiles) throws IOException {
		copyDatabaseFromAsset(context, destFileName,destFile,Arrays.asList(assetFiles));

	}

	private static void copyDatabaseFromAsset(Context context, String destFileName,  File destFile,List<String> assetFiles) throws IOException {
		File pathFile = new File(destFile.getParent());
		if (!pathFile.exists())
			pathFile.mkdirs();

		AssetManager assMan = context.getAssets();
		List<InputStream> inStreams = new LinkedList<>();
		for (String name : assetFiles)
			inStreams.add(assMan.open(name));

		DBHelper.joinFiles(context, destFileName, inStreams);
	}


	public static DBHelper openDatabase(final Context context,
										final String destFileName, int version) {

		File destFile = new File(destFileName);
		if (!destFile.exists()) {
			throw new SQLException("Database not exist!");
		}

		SQLiteDatabase db = new SQLiteOpenHelper(context, destFileName, null,
				version) {

			@Override
			public void onCreate(SQLiteDatabase db) {

			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion,
								  int newVersion) {
			}

		}.getWritableDatabase();

		return new DBHelper(db);
	}



	public static DBHelper createFromAssets(Context context,
											String destFileName, int version, String... assetFiles) throws IOException {

		return DBHelper.createFromAssets(context, destFileName, version,
				Arrays.asList(assetFiles));
	}

	public static String createSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	private final SQLiteDatabase db;

	public static DBHelper openFromFile(String path) {
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, 0);
		return new DBHelper(db);
	}

	private DBHelper(SQLiteDatabase db) {
		this.db = db;
	}

	public void batchExecSQL(List<String> sqls) {
		try {

			db.beginTransaction();

			for (String sql : sqls)
				db.execSQL(sql);

			db.setTransactionSuccessful();

		} catch (Exception e) {
			throw e;
		} finally {
			db.endTransaction();
		}
	}

	public void batchExecSQL(String... sqls) {
		batchExecSQL(Arrays.asList(sqls));
	}

	public void beginTransaction() {
		db.beginTransaction();
	}

	public void close() {
		db.close();
	}

	public void endTransaction() {
		db.endTransaction();
	}

	public void execSQL(String sql) {
		db.execSQL(sql);
	}

	public void setTransactionSuccessful() {
		db.setTransactionSuccessful();
	}

	public static void joinFiles(Context context, String fileName,
								 List<InputStream> inStreams) throws IOException {

		File ofile = new File(fileName);
		FileOutputStream fos = new FileOutputStream(ofile, true);

		int bytesRead = 0;

		for (InputStream fis : inStreams) {
			int len = fis.available();

			byte[] fileBytes = new byte[len];
			bytesRead = fis.read(fileBytes, 0, len);
			assert (bytesRead == len);
			fos.write(fileBytes);

			fos.flush();
			fileBytes = null;

			fis.close();
			fis = null;
		}

		fos.close();
		fos = null;
	}

	public String executeScalar(String sql) {

		Cursor c = null;

		String result = null;

		try {
			c = db.rawQuery(sql, null);
			if (c.moveToFirst())
				result = c.getString(0);
		} catch (Exception e) {
			e.getMessage();
		} finally {
			if (c != null)
				c.close();

		}

		return result;
	}

	public Cursor select(String sql) {

		Cursor c = db.rawQuery(sql, null);

		return c;
	}

	public List<String> getStringList(String sql) {
		return Arrays.asList(getStringArray(sql));
	}

	public String[] getStringArray(String sql) {
		Cursor c = null;
		int i = 0;
		String[] items = null;

		try {
			c = select(sql);
			items = new String[c.getCount()];

			if (c.moveToFirst()) {
				do {
					items[i] = c.getString(0);
					i++;
				} while (c.moveToNext());
			}

		} catch (Exception e) {
			return new String[] {};
		} finally {
			if (c != null)
				c.close();
		}

		return items;
	}

	public String[] getOnRecord(String sql) {
		String[] items = null;
		Cursor c = null;
		try {
			c = select(sql);
			if (c.moveToFirst()) {
				items = new String[c.getColumnCount()];
				for (int i = 0; i < c.getColumnCount(); i++)
					items[i] = c.getString(i);

			}
		} catch (Exception e) {
			return new String[] {};
		} finally {
			if (c != null)
				c.close();
		}
		return items;
	}

}
