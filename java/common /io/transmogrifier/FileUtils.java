package io.transmogrifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public final class FileUtils
{
    private FileUtils()
    {
    }

    public static void copyFile(final File inputFile,
                                final File destinationFile)
        throws FileNotFoundException,
               IOException
    {
        try(final InputStream inputStream = new FileInputStream(inputFile))
        {
            copyFile(inputStream,
                     destinationFile);
        }
    }

    public static void copyFile(final InputStream inputStream,
                                final File destinationFile)
        throws IOException
    {
        try(final OutputStream outputStream = new FileOutputStream(destinationFile))
        {
            final byte[] buffer;
            int length;

            buffer = new byte[1024];

            while((length = inputStream.read(buffer)) > 0)
            {
                outputStream.write(buffer,
                                   0,
                                   length);
            }
        }
    }

    public static String readTextFile(final File inputFile)
        throws IOException
    {
        final String retVal;

        final StringBuilder builder;

        builder = new StringBuilder();

        try(final BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(inputFile))))
        {
            String line;

            while((line = reader.readLine()) != null)
            {
                builder.append(line);
                builder.append("\r\n");
            }
        }

        retVal = builder.toString();

        return (retVal);
    }

    public static void writeTextFile(final String text,
                                     final File outputFile)
        throws IOException
    {
        try(final FileWriter writer = new FileWriter(outputFile))
        {
            writer.write(text);
        }
    }

    public static void deleteFolder(final File folder)
        throws IOException
    {
        if(folder.exists())
        {
            if(!(folder.isDirectory()))
            {
                final String path;

                path = folder.getAbsolutePath();
                throw new IOException(path + " is not a directory");
            }

            deleteFolderRecursively(folder);
        }
    }

    private static void deleteFolderRecursively(final File folder)
        throws IOException
    {
        final File[] entries;
        boolean deleted;

        entries = folder.listFiles();

        if(entries != null)
        {
            for(final File entry : entries)
            {
                if(entry.isDirectory())
                {
                    deleteFolderRecursively(entry);
                }
                else
                {
                    deleted = entry.delete();

                    if(!(deleted))
                    {
                        final String path;

                        path = folder.getAbsolutePath();
                        throw new IOException("Could not delete: " + path);
                    }
                }
            }
        }

        deleted = folder.delete();

        if(!(deleted))
        {
            final String path;

            path = folder.getAbsolutePath();
            throw new IOException("Could not delete: " + path);
        }
    }

    public static void createFolder(final File folder)
        throws
        IOException
    {
        if(!(folder.exists()))
        {
            final boolean created;

            created = folder.mkdirs();

            if(!(created))
            {
                if(!(folder.exists()))
                {
                    final String path;

                    path = folder.getAbsolutePath();
                    throw new IOException("Could not create: " + path);
                }
            }
        }
    }
}
