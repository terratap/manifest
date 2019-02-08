package io.transmogrifier.common;

import io.transmogrifier.common.Manifest.Dataset;
import io.transmogrifier.common.Manifest.Dataset.Download;

import java.io.File;

public interface DatasetDownloader<T>
{
    void download(T id,
                  Dataset dataset,
                  DatasetDownloadHandler handler);

    interface DatasetDownloadHandler
    {
        void converterDownloadStarted(Dataset dataset);

        void converterDownloadSuccess(Dataset dataset);

        void converterDownloadFailed(Dataset dataset,
                                     Throwable ex);

        void datasetDownloadStarted(Dataset dataset,
                                    Download download);

        void datasetDownloadSuccess(Dataset dataset,
                                    Download download);

        void datasetDownloadFailed(Dataset dataset,
                                   Download download,
                                   Throwable ex);

        void datasetDownloadSuccess(Dataset dataset,
                                    File converterFile,
                                    File[] files);

        void datasetDownloadFailed(Dataset dataset,
                                   Throwable ex);
    }
}
