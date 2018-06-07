package fr.esgi.textprediction.data;

/**
 * Created by Jean-Christophe Melikian on 07/06/2018.
 */

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.esgi.textprediction.App;


public class DatabaseManager {
    private static final String TAG = "RoomAsset";
    private final String DATABASE_NAME = "output.db";

    private PredictionDatabase database;

    private DatabaseManager() {
        //call method that check if database not exists and copy prepopulated file from assets
        copyAttachedDatabase(App.getAppContext(), DATABASE_NAME);
        database = Room.databaseBuilder(App.getAppContext(),
                PredictionDatabase.class, DATABASE_NAME)
                .addMigrations(new Migration(1, 2) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        Log.w(TAG, "migrate from version 1 to 2 ");
                    }
                }).build();
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    public PredictionDatabase getDatabase() {
        return database;
    }

    private void copyAttachedDatabase(Context context, String databaseName) {
        final File dbPath = context.getDatabasePath(databaseName);

        // If the database already exists, return
        if (dbPath.exists()) {
            return;
        }

        // Make sure we have a path to the file
        dbPath.getParentFile().mkdirs();

        // Try to copy database file
        try {
            final InputStream inputStream = context.getAssets().open(databaseName);
            final OutputStream output = new FileOutputStream(dbPath);

            byte[] buffer = new byte[8192];
            int length;

            while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            inputStream.close();
        } catch (IOException e) {
            Log.d(TAG, "Failed to open file", e);
            e.printStackTrace();
        }
    }

    private static class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

}
