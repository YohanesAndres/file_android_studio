package proyek.akhir.asynctaskexample;

public interface MyAsyncTaskCallback {
    void onPreExecute();
    void onUpgradeProgress(long value);
    void onPostExecute(String text);
}
