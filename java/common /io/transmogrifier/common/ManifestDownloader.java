package io.transmogrifier.common;

public interface ManifestDownloader<T>
{
    Manifest downloadManifest(T id)
            throws
            ManifestDownloadException;
}
