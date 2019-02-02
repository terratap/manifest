package io.transmogrifier;

public class ManifestDownloadException
    extends Exception
{
    private final String id;

    public ManifestDownloadException(final String id, final Throwable ex)
    {
        super(String.format("Unable to downloadDownloads %s", id), ex);

        this.id = id;
    }
}
