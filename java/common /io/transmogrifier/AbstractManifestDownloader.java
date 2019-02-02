package io.transmogrifier;


public abstract class AbstractManifestDownloader<T>
    implements ManifestDownloader<T>
{
    public Manifest downloadManifest(final T id)
        throws ManifestDownloadException
    {
        final String   json;
        final Manifest manifest;

        json     = downloadJSON(id);
        manifest = Manifest.parse(json);

        return  manifest;
    }

    public abstract String downloadJSON(T id)
        throws ManifestDownloadException;
}
