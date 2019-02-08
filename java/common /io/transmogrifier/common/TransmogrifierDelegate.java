package io.transmogrifier.common;

import io.transmogrifier.common.Manifest.Dataset;
import io.transmogrifier.common.Manifest.Dataset.Download;
import io.transmogrifier.common.Manifest.Dataset.Download.Extract;

import java.io.File;
import java.util.Map;

public interface TransmogrifierDelegate<T>
{
    void didStartManifestDownload(T id);

    void didFinishManifestDownload(T id);

    void didFailManifestDownload(T id,
                                 Throwable ex);

    void didStartManifestProcessing(T id);

    void didFinishManifestProcessing(T id);

    void didFailManifestProcessing(T id,
                                   Throwable ex);

    void didStartDatasetsDownload(T id);

    void didFinishDatasetsDownload(T id);

    boolean shouldDownloadDataset(T id,
                                  Dataset dataset);

    void didStartDatasetDownloads(T id,
                                  Dataset dataset);

    void didFinishDatasetDownloads(T id,
                                   Dataset dataset);

    void didStartDatasetDownload(T id,
                                 Dataset dataset,
                                 Download download);

    void didFinishDatasetDownload(T id,
                                  Dataset dataset,
                                  Download download);

    void didFailDatasetDownload(T id,
                                Dataset dataset,
                                Download download,
                                Throwable ex);

    void didStartExtractions(T id,
                             Dataset dataset,
                             Download download);

    void didFinishExtractions(T id,
                              Dataset dataset,
                              Download download);

    void didFailExtractions(T id,
                            Dataset dataset,
                            Download download,
                            Throwable ex);

    void didStartExtraction(T id,
                            Dataset dataset,
                            Download download,
                            Extract extract);

    void didFinishExtraction(T id,
                             Dataset dataset,
                             Download download,
                             File location,
                             Extract extract);

    void didFailExtraction(T id,
                           Dataset dataset,
                           Download download,
                           Extract extract,
                           Throwable ex);

    void didStartDatasetsConversion(T id);

    void didFinishDatasetsConversion(T id);

    void didStartDatasetConversion(T id,
                                   Dataset dataset);

    void didFinishDatasetConversion(T id,
                                    Dataset dataset);

    void didFailDatasetConversion(T id,
                                  Dataset dataset,
                                  Throwable ex);

    void didComplete(T id,
                     Map<Dataset, Pair<File[], Boolean>> files);
}
