package io.transmogrifier.jse;

import io.transmogrifier.common.AbstractDatasetDownloader;
import io.transmogrifier.common.Manifest.Dataset;

import java.io.File;
import java.io.IOException;

public class SerialDatasetDownloader
        extends AbstractDatasetDownloader<String>
{
    @Override
    public void download(final String id,
                         final Dataset dataset,
                         final DatasetDownloadHandler handler)
    {
        try
        {
            final File[][] files;

            files = download(dataset,
                             handler);
            handler.datasetDownloadSuccess(dataset,
                                           files[0][0],
                                           files[1]);
        }
        catch(final IOException ex)
        {
            handler.datasetDownloadFailed(dataset,
                                          ex);
        }
    }
}
