package io.transmogrifier.jse;

import io.transmogrifier.common.AbstractManifestDownloader;
import io.transmogrifier.common.ManifestDownloadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LocalManifestDownloader
        extends AbstractManifestDownloader<String>
{
    private final Class<?> clazz;

    public LocalManifestDownloader(final Class<?> clazz)
    {
        this.clazz = clazz;
    }

    @Override
    public String downloadJSON(final String fileName)
            throws
            ManifestDownloadException
    {
        try(InputStreamReader in = new InputStreamReader(clazz.getResourceAsStream(fileName)))
        {
            Scanner scanner;
            String  jsonString;

            scanner = new Scanner(in).useDelimiter("\\A");
            jsonString = scanner.hasNext() ? scanner.next() : "";

            return jsonString;
        }
        catch(final IOException ex)
        {
            throw new ManifestDownloadException(fileName,
                                                ex);
        }
    }
}
