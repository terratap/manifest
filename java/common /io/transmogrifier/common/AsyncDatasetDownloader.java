package io.transmogrifier.common;

import io.transmogrifier.common.Manifest.Dataset;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class AsyncDatasetDownloader
        extends AbstractDatasetDownloader<String>
{
    private final Executor executor;

    public AsyncDatasetDownloader(final Executor ex)
    {
        executor = ex;
    }

    @Override
    public void download(final String id,
                         final Dataset dataset,
                         final DatasetDownloadHandler handler)
    {
        CompletableFuture.supplyAsync(() ->
                                      {
                                          System.out.println("***********");
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

                                          return null;
                                      },
                                      executor);
    }
}
