package io.transmogrifier.common;

public interface Converter
{
    String convert(String converterJS,
                   String data)
            throws
            ConversionException;
}
