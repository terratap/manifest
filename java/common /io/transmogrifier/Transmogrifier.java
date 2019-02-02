package io.transmogrifier;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Transmogrifier<T>
{
    private final File datasetRoot;
    private final File converterRoot;
    private TransmogrifierDelegate<T> delegate;

    {
        delegate = new NullTransmogrifierDelegate();
    }

    public Transmogrifier(final File dir)
        throws IOException
    {
        boolean result;

        if(dir == null)
        {
            throw new IllegalArgumentException("dir cannot be null");
        }

        if(!(dir.exists()))
        {
            throw new IllegalArgumentException(String.format("\"%s\" must exist", dir.getAbsolutePath()));
        }

        if(!(dir.isDirectory()))
        {
            throw new IllegalArgumentException(String.format("\"%s\" must be a directory", dir.getAbsolutePath()));
        }

        if(!(dir.canWrite()))
        {
            throw new IllegalArgumentException(String.format("\"%s\" must be writable", dir.getAbsolutePath()));
        }

        datasetRoot = new File(dir, "datasets");
        result = datasetRoot.mkdirs();

        if(!(result))
        {
            if(!(datasetRoot.exists()))
            {
                throw new IOException(String.format("cannot create \"%s\"", datasetRoot.getAbsolutePath()));
            }
        }

        converterRoot = new File(dir, "converters");
        result = converterRoot.mkdirs();

        if(!(result))
        {
            if(!(converterRoot.exists()))
            {
                throw new IOException(String.format("cannot create \"%s\"", converterRoot.getAbsolutePath()));
            }
        }
    }

    public void processManifest(final T                     id,
                                final ManifestDownloader<T> manifestDownloader,
                                final DatasetDownloader<T>  downloader,
                                final Converter             converter)
    {
        final Manifest manifest;

        delegate.didStartManifestDownload(id);

        try
        {
            manifest = manifestDownloader.downloadManifest(id);
            delegate.didFinishManifestDownload(id);
            processManifest(id, manifest, downloader, converter);
        }
        catch(final ManifestDownloadException ex)
        {
            delegate.didFailManifestDownload(id, ex);
        }
    }

    public void processManifest(final T                    id,
                                final Manifest             manifest,
                                final DatasetDownloader<T> downloader,
                                final Converter            converter)
    {
        final List<Manifest.Dataset>                   datasetsToProcess;
        final List<Manifest.Dataset>                   datasetsProcessed;
        final Map<Manifest.Dataset, File>              downloadedConverters;
        final Map<Manifest.Dataset, File[]>            downloadedDatasets;
        final List<Manifest.Dataset>                   failedDatasets;
        final DatasetDownloader.DatasetDownloadHandler handler;
        final Manifest.Dataset[]                       datasets;

        if(id == null)
        {
            throw new IllegalArgumentException("id cannot be null");
        }

        if(manifest == null)
        {
            throw new IllegalArgumentException("manifest cannot be null");
        }

        datasetsToProcess    = Collections.synchronizedList(new ArrayList<>());
        datasetsProcessed    = Collections.synchronizedList(new ArrayList<>());
        downloadedConverters = Collections.synchronizedMap(new HashMap<>());
        downloadedDatasets   = Collections.synchronizedMap(new HashMap<>());
        failedDatasets       = Collections.synchronizedList(new ArrayList<>());
        handler = new DatasetDownloader.DatasetDownloadHandler()
        {
            @Override
            public void converterDownloadStarted(Manifest.Dataset dataset)
            {
            }

            @Override
            public void converterDownloadSuccess(Manifest.Dataset dataset)
            {
            }

            @Override
            public void converterDownloadFailed(Manifest.Dataset dataset, Throwable ex)
            {
            }

            @Override
            public void datasetDownloadStarted(final Manifest.Dataset dataset,
                                               final Manifest.Dataset.Download download)
            {
                delegate.didStartDatasetDownload(id, dataset, download);
            }

            @Override
            public void datasetDownloadSuccess(final Manifest.Dataset dataset,
                                               final Manifest.Dataset.Download download)
            {
                delegate.didFinishDatasetDownload(id, dataset, download);
            }

            @Override
            public void datasetDownloadFailed(final Manifest.Dataset dataset,
                                              final Manifest.Dataset.Download download,
                                              final Throwable ex)
            {
                delegate.didFailDatasetDownload(id, dataset, download, ex);
            }

            @Override
            public void datasetDownloadSuccess(final Manifest.Dataset dataset,
                                               final File             converterFile,
                                               final File[]           files)
            {
                delegate.didFinishDatasetDownloads(id, dataset);

                synchronized(datasetsToProcess)
                {
                    datasetsProcessed.add(dataset);

                    if(converterFile != null)
                    {
                        downloadedConverters.put(dataset, converterFile);
                    }

                    downloadedDatasets.put(dataset, files);
                }

                if(datasetsToProcess.size() == datasetsProcessed.size())
                {
                    delegate.didFinishDatasetsDownload(id);
                    processDatasets(id, downloadedConverters, downloadedDatasets, converter);
                }
            }

            @Override
            public void datasetDownloadFailed(final Manifest.Dataset dataset,
                                              final Throwable        ex)
            {
                synchronized(datasetsToProcess)
                {
                    datasetsProcessed.add(dataset);
                    failedDatasets.add(dataset);
                }

                if(datasetsToProcess.size() == datasetsProcessed.size())
                {
                    delegate.didFinishDatasetsDownload(id);
                    processDatasets(id, downloadedConverters, downloadedDatasets, converter);
                }
            }
        };

        datasets = manifest.getDatasets();
        delegate.didStartManifestProcessing(id);
        delegate.didStartDatasetsDownload(id);

        // Yes, have to do both loops.
        for(final Manifest.Dataset dataset : datasets)
        {
            if(delegate.shouldDownloadDataset(id, dataset))
            {
                datasetsToProcess.add(dataset);
            }
        }

        for(final Manifest.Dataset dataset : datasetsToProcess)
        {
            downloadDatasets(id,
                             dataset,
                             downloader,
                             handler);
        }
    }

    private void downloadDatasets(final T                                  id,
                                  final Manifest.Dataset                   dataset,
                                  final DatasetDownloader<T>               downloader,
                                  DatasetDownloader.DatasetDownloadHandler handler)
    {
        if(dataset == null)
        {
            throw new IllegalArgumentException("dataset cannot be null");
        }

        delegate.didStartDatasetDownloads(id, dataset);
        downloader.download(id,
                            dataset,
                            handler);
    }

    private void processDatasets(final T                             id,
                                 final Map<Manifest.Dataset, File>   converters,
                                 final Map<Manifest.Dataset, File[]> datasets,
                                 final Converter                     converter)
    {
        final Map<Manifest.Dataset, Pair<File[], Boolean>> results;

        results = new HashMap<>();
        delegate.didStartDatasetsConversion(id);

        for(final Map.Entry<Manifest.Dataset, File[]> entry : datasets.entrySet())
        {
            final Manifest.Dataset dataset;
            final File[]           downloadedFiles;
            final File             downloadedConverterFile;
            final File[]           newFiles;

            dataset                 = entry.getKey();
            downloadedFiles         = entry.getValue();
            downloadedConverterFile = converters.get(dataset);

            try
            {
                newFiles = moveDownloadedDataset(id,
                                                 dataset,
                                                 downloadedFiles);
            }
            catch(final IOException ex)
            {
                ex.printStackTrace();
                continue;
            }

            if (downloadedConverterFile == null)
            {
                final Pair<File[], Boolean> unconvertedData;
                final boolean               conformsToSchema;

                conformsToSchema = dataset.getSchema() != null;

                unconvertedData = renameUnconvertedFiles(dataset,
                                                         newFiles,
                                                         conformsToSchema);
                results.put(dataset, unconvertedData);
            }
            else
            {
                try
                {
                    final File newConverterFile;
                    final Pair<File[], Boolean> convertedData;

                    newConverterFile = moveDownloadedConverter(id,
                            dataset,
                            downloadedConverterFile);
                    convertedData = convert(id, dataset, newConverterFile, newFiles, converter);
                    results.put(dataset, convertedData);
                }
                catch (final IOException ex)
                {
                    final Pair<File[], Boolean> unconvertedData;

                    unconvertedData = renameUnconvertedFiles(dataset, newFiles, false);
                    results.put(dataset, unconvertedData);
                    // TODO: need callback in delegate for fail
                    ex.printStackTrace();
                }
            }
        }

        delegate.didFinishDatasetsConversion(id);
        delegate.didComplete(id, results);
    }

    private Pair<File[], Boolean> convert(final T                id,
                                          final Manifest.Dataset dataset,
                                          final File             converterFile,
                                          final File[]           filesToConvert,
                                          final Converter        converter)
        throws IOException
    {
        final String                converterJS;
        final StringBuilder         builder;
        final String                datasetName;
        final File                  destinationDir;
        final File                  convertedFile;
        final Pair<File[], Boolean> retVal;
        final String                result;

        delegate.didStartDatasetConversion(id, dataset);

        converterJS = FileUtils.readTextFile(converterFile);
        builder = new StringBuilder();

        for (final File file : filesToConvert)
        {
            final String content;

            content = FileUtils.readTextFile(file);
            builder.append(content);
            builder.append(",");
        }

        builder.setLength(builder.length() - 1);

        result        = converter.convert(converterJS, builder.toString());
        destinationDir = mk(dataset);
        datasetName   = dataset.getName();
        convertedFile = new File(destinationDir, datasetName + ".json");

        FileUtils.writeTextFile(result.toString(),
                convertedFile);

        delegate.didFinishDatasetConversion(id, dataset);

        retVal = new Pair<>(new File[] {convertedFile }, Boolean.TRUE);

        return retVal;
    }

    private File mk(final Manifest.Dataset dataset)
    {
        final File countryDir;
        final File subdivisionDir;
        final File regionDir;
        final File cityDir;
        final File providerDir;

        countryDir     = new File(datasetRoot, dataset.getCountryCode());
        subdivisionDir = new File(countryDir, dataset.getSubdivision());
        regionDir      = new File(subdivisionDir, dataset.getRegion());
        cityDir        = new File(regionDir, dataset.getCity());
        providerDir    = new File(cityDir, dataset.getProvider());

        return providerDir;
    }

    private Pair<File[], Boolean> renameUnconvertedFiles(final Manifest.Dataset dataset,
                                                         final File[] downloadedFiles,
                                                         final Boolean conformsToSchema)
    {
        final Pair<File[], Boolean> results;

        results = new Pair<>(downloadedFiles, conformsToSchema);

        return results;
    }


    private File[] moveDownloadedDataset(final T                id,
                                         final Manifest.Dataset dataset,
                                         final File[]           downloadedFiles)
        throws IOException
    {
        final Manifest.Dataset.Download[] downloads;
        final List<File>                  newFiles;

        downloads = dataset.getDownloads();
        newFiles  = new ArrayList<>();

        for(int i = 0; i < downloads.length; i++)
        {
            final Manifest.Dataset.Download download;
            final File                      downloadedFile;
            final String                    downloadURL;
            final String                    name;
            final File                      file;

            download       = downloads[i];
            downloadedFile = downloadedFiles[i];
            downloadURL    = download.getSrcURL();

            name  = nm(downloadURL);
            file = move(downloadedFile, dataset, name, datasetRoot);
            newFiles.add(file);
        }

        delegate.didFinishDatasetConversion(id, dataset);

        return newFiles.toArray(new File[0]);
    }

    private File moveDownloadedConverter(final T                id,
                                         final Manifest.Dataset dataset,
                                         final File             downloadedConverterFile)
        throws IOException
    {
        final String downloadURL;
        final String name;
        final File   file;

        downloadURL = dataset.getConverter();
        name = nm(downloadURL);
        file = move(downloadedConverterFile, dataset, name, converterRoot);

        return file;
    }

    private String nm(final String urlString)
        throws MalformedURLException
    {
        final URL                       url;
        final String                    path;
        final int                       index;
        final String                    name;
        final File                      file;

        url   = new URL(urlString);
        path  = url.getPath();
        index = path.lastIndexOf('/');

        if(index == -1)
        {
            name = path;
        }
        else
        {
            name = path.substring(index + 1);
        }

        return name;
    }

    private File move(final File             src,
                      final Manifest.Dataset dataset,
                      final String           name,
                      final File             root)
            throws IOException
    {
        final File destinationDir;
        final File datasetFile;
        boolean    result;

        destinationDir = mk(dataset);
        result         = destinationDir.mkdirs();

        if(!(result))
        {
            if(!(destinationDir.exists()))
            {
                throw new IOException(String.format("cannot create \"%s\"", root.getAbsolutePath()));
            }
        }

        datasetFile = new File(destinationDir, dataset.getName() + "-" + name);
        result      = src.renameTo(datasetFile);

        if(!(result))
        {
            throw new IOException(String.format("cannot move \"%s\" to \"%s\"",
                    src.getAbsolutePath(),
                    datasetFile.getAbsolutePath()));
        }

        return datasetFile;
    }

    public void setTransmogrifierDelegate(final TransmogrifierDelegate<T> delegate)
    {
        if(delegate == null)
        {
            this.delegate = new NullTransmogrifierDelegate();
        }
        else
        {
            this.delegate = delegate;
        }
    }

    private class NullTransmogrifierDelegate
        extends AbstractTransmogrifierDelegate<T>
    {
        @Override
        public boolean shouldDownloadDataset(T id, Manifest.Dataset dataset)
        {
            return true;
        }

        @Override
        public void didComplete(T id, Map<Manifest.Dataset, Pair<File[], Boolean>> files)
        {
        }
    }
}
