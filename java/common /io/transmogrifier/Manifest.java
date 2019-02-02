package io.transmogrifier;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Reader;
import java.util.Arrays;
import java.util.Objects;


public class Manifest
{
    public static class Dataset
    {
        public static class Download
        {
            @SerializedName("src")
            private String srcURL;
            private String encoding;
            @SerializedName("extract")
            private Extract[] extracts;

            public String getSrcURL() {
                return srcURL;
            }

            public String getEncoding() {
                return encoding;
            }

            public Extract[] getExtracts()
            {
                final Extract[] retVal;

                if(extracts == null)
                {
                    retVal = new Extract[0];
                }
                else
                {
                    retVal = Arrays.copyOf(extracts, extracts.length);
                }

                return retVal;
            }

            public static class Extract
            {
                private String src;
                private String encoding;
                private String dst;

                public String getSrc()
                {
                    return src;
                }

                public String getEncoding()
                {
                    return encoding;
                }

                public String getDst()
                {
                    return dst;
                }
            }

            @Override
            public String toString()
            {
                return String.format("%s (%s)", srcURL, encoding);
            }
        }

        @Override
        public boolean equals(final Object obj)
        {
            final Dataset dataset;

            if(this == obj)
            {
                return true;
            }

            if(obj == null || getClass() != obj.getClass())
            {
                return false;
            }

            dataset = (Dataset)obj;

            return Objects.equals(id, dataset.id);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(id);
        }

        @Override
        public String toString()
        {
            return String.format("%s/%s/%s/%s/%s/%s",
                    countryCode,
                    subdivision,
                    region,
                    city,
                    provider,
                    name);
        }

        private String name;
        @SerializedName("country")
        private String countryCode;
        private String subdivision;
        private String region;
        private String city;
        private String provider;
        private String schema;
        private String converter;
        private String id;
        private Download[] downloads;

        public String getName()
        {
            return name;
        }

        public String getCountryCode()
        {
            return countryCode;
        }

        public String getSubdivision()
        {
            return subdivision;
        }

        public String getRegion()
        {
            return region;
        }

        public String getCity()
        {
            return city;
        }

        public String getProvider()
        {
            return provider;
        }

        public String getSchema()
        {
            return schema;
        }

        public String getConverter()
        {
            return converter;
        }

        public String getId()
        {
            return id;
        }

        public Download[] getDownloads()
        {
            return Arrays.copyOf(downloads, downloads.length);
        }
    }

    private int version;
    private Dataset[] datasets;

    public int getVersion()
    {
        return version;
    }

    public Dataset[] getDatasets()
    {
        return Arrays.copyOf(datasets, datasets.length);
    }

    public static Manifest parse(final Reader reader)
    {
        final Gson     gson;
        final Manifest manifest;

        if(reader == null)
        {
            throw new IllegalArgumentException("reader cannot be null");
        }

        gson     = new Gson();
        manifest = gson.fromJson(reader, Manifest.class);

        return manifest;
    }

    public static Manifest parse(final String jsonString)
    {
        final Gson     gson;
        final Manifest manifest;

        if(jsonString == null)
        {
            throw new IllegalArgumentException("jsonString cannot be null");
        }

        gson     = new Gson();
        manifest = gson.fromJson(jsonString, Manifest.class);

        return manifest;
    }
}
