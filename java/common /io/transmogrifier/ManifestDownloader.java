package io.transmogrifier;

public interface ManifestDownloader<T>
{
    Manifest downloadManifest(T id)
        throws ManifestDownloadException;
}
