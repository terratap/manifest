package io.transmogrifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDatasetDownloader<T>
    implements DatasetDownloader<T>
{
    protected File[][] download(final Manifest.Dataset dataset,
                                 final DatasetDownloadHandler handler)
        throws IOException
    {
        final String                      converterURLString;
        File                              converterFile;
        final Manifest.Dataset.Download[] downloads;
        final List<File>                  files;
        final File[][]                    retVal;

        converterURLString = dataset.getConverter();
        converterFile      = null;

        if(converterURLString != null)
        {
            handler.converterDownloadStarted(dataset);

            try
            {
                converterFile = download(converterURLString);
                handler.converterDownloadSuccess(dataset);
            }
            catch(IOException ex)
            {
                handler.converterDownloadFailed(dataset, ex);

                throw ex;
            }
        }

        downloads = dataset.getDownloads();
        files     = new ArrayList<>(downloads.length);

        for(final Manifest.Dataset.Download download : downloads)
        {
            final File file;

            handler.datasetDownloadStarted(dataset, download);

            try
            {
                final String urlString;

                urlString = download.getSrcURL();
                file = download(urlString);
                files.add(file);
                handler.datasetDownloadSuccess(dataset, download);
            }
            catch(IOException ex)
            {
                handler.datasetDownloadFailed(dataset, download, ex);

                throw ex;
            }
        }

        retVal    = new File[2][];
        retVal[0] = new File[] { converterFile };
        retVal[1] = files.toArray(new File[0]);

        return retVal;
    }

    private File download(final String urlString)
        throws IOException
    {
        HttpURLConnection connection;

        connection = null;

        try
        {
            final URL    url;
            final File   downloadFile;

            url = new URL(urlString);

            connection  = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            downloadFile = File.createTempFile("tmg", null);

            try (final InputStream in = connection.getInputStream();
                 final OutputStream out = new FileOutputStream(downloadFile))
            {
                final byte[] buffer;

                buffer = new byte[10 * 1024];

                for(int length; (length = in.read(buffer)) != -1;)
                {
                    out.write(buffer, 0, length);
                }
            }

            return downloadFile;
        }
        finally
        {
            if (connection != null)
            {
                connection.disconnect();
            }
        }
    }
}
