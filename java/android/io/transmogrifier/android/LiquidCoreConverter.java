package io.transmogrifier.android;

import org.liquidplayer.javascript.JSContext;
import org.liquidplayer.javascript.JSValue;

import io.transmogrifier.Converter;


public class LiquidCoreConverter
    implements Converter
{
    @Override
    public String convert(final String converterJS,
                          final String data)
    {
        final JSContext context;
        final JSValue   result;

        context = new JSContext();
        context.evaluateScript("var module = {}");
        context.evaluateScript(converterJS);
        context.evaluateScript("var result = convert(" + data + ");");
        result = context.property("result");

        return result.toString();
    }
}

