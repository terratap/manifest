package io.transmogrifier;

import java.io.File;

public interface DatasetDownloader<T>
{
    interface DatasetDownloadHandler
    {
        void converterDownloadStarted(Manifest.Dataset dataset);
        void converterDownloadSuccess(Manifest.Dataset dataset);
        void converterDownloadFailed(Manifest.Dataset dataset, Throwable ex);
        void datasetDownloadStarted(Manifest.Dataset dataset, Manifest.Dataset.Download download);
        void datasetDownloadSuccess(Manifest.Dataset dataset, Manifest.Dataset.Download download);
        void datasetDownloadFailed(Manifest.Dataset dataset, Manifest.Dataset.Download download, Throwable ex);
        void datasetDownloadSuccess(Manifest.Dataset dataset, File converterFile, File[] files);
        void datasetDownloadFailed(Manifest.Dataset dataset, Throwable ex);
    }

    void download(T id,
                  Manifest.Dataset dataset,
                  DatasetDownloadHandler handler);
}
