package io.transmogrifier.jse;

import java.io.*;

import io.transmogrifier.AbstractDatasetDownloader;
import io.transmogrifier.Manifest;

public class SerialDatasetDownloader
    extends AbstractDatasetDownloader<String>
{
    @Override
    public void download(final String                 id,
                         final Manifest.Dataset       dataset,
                         final DatasetDownloadHandler handler)
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
    }
}
