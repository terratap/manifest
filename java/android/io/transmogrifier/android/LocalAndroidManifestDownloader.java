package io.transmogrifier.android;

import android.content.Context;
import io.transmogrifier.common.AbstractManifestDownloader;
import io.transmogrifier.common.ManifestDownloadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class LocalAndroidManifestDownloader
        extends AbstractManifestDownloader<Integer>
{
    private final Context context;

    public LocalAndroidManifestDownloader(final Context ctx)
    {
        context = ctx;
    }

    public String downloadJSON(final Integer resource)
            throws
            ManifestDownloadException
    {
        try(InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resource)))
        {
            Scanner scanner;
            String  jsonString;

            scanner = new Scanner(in).useDelimiter("\\A");
            jsonString = scanner.hasNext() ? scanner.next() : "";

            return jsonString;
        }
        catch(final IOException ex)
        {
            throw new ManifestDownloadException("" + resource,
                                                ex);
        }
    }
}
