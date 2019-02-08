package io.transmogrifier.common;

import io.transmogrifier.common.Manifest.Dataset;
import io.transmogrifier.common.Manifest.Dataset.Download;
import io.transmogrifier.common.Manifest.Dataset.Download.Extract;

import java.io.File;

public abstract class AbstractTransmogrifierDelegate<T>
        implements TransmogrifierDelegate<T>
{
    @Override
    public void didStartManifestDownload(final T id)
    {

    }

    @Override
    public void didFinishManifestDownload(final T id)
    {

    }

    @Override
    public void didFailManifestDownload(final T id,
                                        final Throwable ex)
    {

    }

    @Override
    public void didStartManifestProcessing(final T id)
    {

    }

    @Override
    public void didFinishManifestProcessing(final T id)
    {

    }

    @Override
    public void didFailManifestProcessing(final T id,
                                          final Throwable ex)
    {

    }

    @Override
    public void didStartDatasetsDownload(final T id)
    {

    }

    @Override
    public void didFinishDatasetsDownload(final T id)
    {

    }

    @Override
    public void didStartDatasetDownloads(final T id,
                                         final Dataset dataset)
    {

    }

    @Override
    public void didFinishDatasetDownloads(final T id,
                                          final Dataset dataset)
    {

    }

    @Override
    public void didStartDatasetDownload(final T id,
                                        final Dataset dataset,
                                        final Download download)
    {

    }

    @Override
    public void didFinishDatasetDownload(final T id,
                                         final Dataset dataset,
                                         final Download download)
    {

    }

    @Override
    public void didFailDatasetDownload(final T id,
                                       final Dataset dataset,
                                       final Download download,
                                       final Throwable ex)
    {

    }

    @Override
    public void didStartExtractions(final T id,
                                    final Dataset dataset,
                                    final Download download)
    {

    }

    @Override
    public void didFinishExtractions(final T id,
                                     final Dataset dataset,
                                     final Download download)
    {

    }

    @Override
    public void didFailExtractions(final T id,
                                   final Dataset dataset,
                                   final Download download,
                                   final Throwable ex)
    {

    }

    @Override
    public void didStartExtraction(final T id,
                                   final Dataset dataset,
                                   final Download download,
                                   final Extract extract)
    {

    }

    @Override
    public void didFinishExtraction(final T id,
                                    final Dataset dataset,
                                    final Download download,
                                    final File location,
                                    final Extract extract)
    {

    }

    @Override
    public void didFailExtraction(final T id,
                                  final Dataset dataset,
                                  final Download download,
                                  final Extract extract,
                                  final Throwable ex)
    {

    }

    @Override
    public void didStartDatasetsConversion(final T id)
    {

    }

    @Override
    public void didFinishDatasetsConversion(final T id)
    {

    }

    @Override
    public void didStartDatasetConversion(final T id,
                                          final Dataset dataset)
    {

    }

    @Override
    public void didFinishDatasetConversion(final T id,
                                           final Dataset dataset)
    {

    }

    @Override
    public void didFailDatasetConversion(final T id,
                                         final Dataset dataset,
                                         final Throwable ex)
    {

    }
}
