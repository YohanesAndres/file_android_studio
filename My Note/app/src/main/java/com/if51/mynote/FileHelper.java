package com.if51.mynote;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {

    private static final String TAG = FileHelper.class.getName();

    //method ini digunakan untuk data berupa string menjadi file
    static void writeToFile(FileModel fileModel, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileModel.getFilename(), Context.MODE_PRIVATE));
            outputStreamWriter.write(fileModel.getData());
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e(TAG, "File write Failed :", e);
        }
    }

    //method yang digunakan untuk membaca data dari file
    static FileModel readFromFile(Context context, String filename) {
        FileModel fileModel = new FileModel();

        try {
            InputStream inputStream = context.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String recieveString;
                StringBuilder stringBuilder = new StringBuilder();
                while ((recieveString = bufferedReader.readLine()) != null){
                    stringBuilder.append(recieveString);
                }

                inputStream.close();
                fileModel.setFilename(filename);
                fileModel.setData(stringBuilder.toString());
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found :", e);
        } catch (IOException e) {
            Log.e(TAG, "Can not read file :", e);
        }
        return fileModel;
    }

}
