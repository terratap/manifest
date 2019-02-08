package io.transmogrifier.jse;


import io.transmogrifier.common.ConversionException;
import io.transmogrifier.common.Converter;
import jdk.nashorn.api.scripting.NashornException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class NashornConverter
        implements Converter
{
    @Override
    public String convert(final String converterJS,
                          final String data)
            throws
            ConversionException
    {
        final ScriptEngine engine;

        engine = new ScriptEngineManager().getEngineByName("nashorn");

        try
        {
            final Invocable invocable;
            final Object    result;

            invocable = (Invocable)engine;
            engine.eval("var module = {}");
            engine.eval(converterJS);
            result = invocable.invokeFunction("convert",
                                              data);

            return result.toString();
        }
        catch(final ScriptException | NoSuchMethodException | NashornException ex)
        {
            throw new ConversionException(ex);
        }
    }
}

