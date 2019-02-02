package io.transmogrifier;

import java.io.File;
import java.util.Map;

public interface TransmogrifierDelegate<T>
{
    void didStartManifestDownload(T id);
    void didFinishManifestDownload(T id);
    void didFailManifestDownload(T id, Throwable ex);

    void didStartManifestProcessing(T id);
    void didFinishManifestProcessing(T id);
    void didFailManifestProcessing(T id, Throwable ex);

    void didStartDatasetsDownload(T id);
    void didFinishDatasetsDownload(T id);

    boolean shouldDownloadDataset(T id, Manifest.Dataset dataset);
    void didStartDatasetDownloads(T id, Manifest.Dataset dataset);
    void didFinishDatasetDownloads(T id, Manifest.Dataset dataset);

    void didStartDatasetDownload(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download);
    void didFinishDatasetDownload(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download);
    void didFailDatasetDownload(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download, Throwable ex);

    void didStartExtractions(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download);
    void didFinishExtractions(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download);
    void didFailExtractions(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download, Throwable ex);

    void didStartExtraction(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download, Manifest.Dataset.Download.Extract extract);
    void didFinishExtraction(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download, File location, Manifest.Dataset.Download.Extract extract);
    void didFailExtraction(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download, Manifest.Dataset.Download.Extract extract, Throwable ex);

    void didStartDatasetsConversion(T id);
    void didFinishDatasetsConversion(T id);

    void didStartDatasetConversion(T id, Manifest.Dataset dataset);
    void didFinishDatasetConversion(T id, Manifest.Dataset dataset);
    void didFailDatasetConversion(T id, Manifest.Dataset dataset, Throwable ex);

    void  didComplete(T id, Map<Manifest.Dataset, Pair<File[], Boolean>> files);
}
