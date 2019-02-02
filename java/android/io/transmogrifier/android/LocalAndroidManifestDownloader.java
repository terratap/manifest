package io.transmogrifier.android;

import android.content.Context;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import io.transmogrifier.AbstractManifestDownloader;
import io.transmogrifier.ManifestDownloadException;

public class LocalAndroidManifestDownloader
        extends AbstractManifestDownloader<Integer>
{
    private final Context context;

    public LocalAndroidManifestDownloader(final Context ctx)
    {
        context = ctx;
    }

    public String downloadJSON(final Integer resource)
            throws ManifestDownloadException
    {
        try(InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resource)))
        {
            Scanner scanner;
            String  jsonString;

            scanner    = new Scanner(in).useDelimiter("\\A");
            jsonString = scanner.hasNext() ? scanner.next() : "";

            return jsonString;
        }
        catch(final IOException ex)
        {
            throw new ManifestDownloadException("" + resource, ex);
        }
    }
}
