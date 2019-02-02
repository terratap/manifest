package io.transmogrifier;

import java.io.File;
import java.util.Map;

public abstract class AbstractTransmogrifierDelegate<T>
    implements TransmogrifierDelegate<T>
{
    @Override
    public void didStartManifestDownload(T id)
    {

    }

    @Override
    public void didFinishManifestDownload(T id)
    {

    }

    @Override
    public void didFailManifestDownload(T id, Throwable ex)
    {

    }

    @Override
    public void didStartManifestProcessing(T id)
    {

    }

    @Override
    public void didFinishManifestProcessing(T id)
    {

    }

    @Override
    public void didFailManifestProcessing(T id, Throwable ex)
    {

    }

    @Override
    public void didStartDatasetsDownload(T id)
    {

    }

    @Override
    public void didFinishDatasetsDownload(T id)
    {

    }

    @Override
    public void didStartDatasetDownloads(T id, Manifest.Dataset dataset)
    {

    }

    @Override
    public void didFinishDatasetDownloads(T id, Manifest.Dataset dataset)
    {

    }

    @Override
    public void didStartDatasetDownload(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download)
    {

    }

    @Override
    public void didFinishDatasetDownload(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download)
    {

    }

    @Override
    public void didFailDatasetDownload(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download, Throwable ex)
    {

    }

    @Override
    public void didStartExtractions(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download)
    {

    }

    @Override
    public void didFinishExtractions(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download)
    {

    }

    @Override
    public void didFailExtractions(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download, Throwable ex)
    {

    }

    @Override
    public void didStartExtraction(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download, Manifest.Dataset.Download.Extract extract)
    {

    }

    @Override
    public void didFinishExtraction(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download, File location, Manifest.Dataset.Download.Extract extract)
    {

    }

    @Override
    public void didFailExtraction(T id, Manifest.Dataset dataset, Manifest.Dataset.Download download, Manifest.Dataset.Download.Extract extract, Throwable ex)
    {

    }

    @Override
    public void didStartDatasetsConversion(T id)
    {

    }

    @Override
    public void didFinishDatasetsConversion(T id)
    {

    }

    @Override
    public void didStartDatasetConversion(T id, Manifest.Dataset dataset)
    {

    }

    @Override
    public void didFinishDatasetConversion(T id, Manifest.Dataset dataset)
    {

    }

    @Override
    public void didFailDatasetConversion(T id, Manifest.Dataset dataset, Throwable ex)
    {

    }
}
