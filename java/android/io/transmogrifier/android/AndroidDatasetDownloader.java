package io.transmogrifier.android;


import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;

import io.transmogrifier.AbstractDatasetDownloader;
import io.transmogrifier.Manifest;

public class AndroidDatasetDownloader
        extends AbstractDatasetDownloader<Integer>
{
    @Override
    public void download(final Integer                id,
                         final Manifest.Dataset       dataset,
                         final DatasetDownloadHandler handler)
    {
        class DownloadTask
                extends AsyncTask<Void, Integer, Void>
        {
            @Override
            protected Void doInBackground(final Void... ignore)
            {
                try
                {
                    final File[][] files;

                    files = download(dataset, handler);
                    handler.datasetDownloadSuccess(dataset, files[0][0], files[1]);
                }
                catch(final IOException ex)
                {
                    handler.datasetDownloadFailed(dataset, ex);
                }

                return null;
            }
        }

        final DownloadTask task;

        task = new DownloadTask();
        task.execute((Void)null);
    }
}
