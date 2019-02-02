package io.transmogrifier.jse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import io.transmogrifier.AbstractManifestDownloader;
import io.transmogrifier.ManifestDownloadException;

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
            throws ManifestDownloadException
    {
        try(InputStreamReader in = new InputStreamReader(clazz.getResourceAsStream(fileName)))
        {
            Scanner scanner;
            String  jsonString;

            scanner    = new Scanner(in).useDelimiter("\\A");
            jsonString = scanner.hasNext() ? scanner.next() : "";

            return jsonString;
        }
        catch(final IOException ex)
        {
            throw new ManifestDownloadException(fileName, ex);
        }
    }
}
