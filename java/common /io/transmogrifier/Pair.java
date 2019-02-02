package io.transmogrifier;

import java.util.Objects;

public final class Pair<F, S>
{
    private final F first;
    private final S second;

    public Pair(final F f, final S s)
    {
        first  = f;
        second = s;
    }

    public F getFirst()
    {
        return first;
    }

    public S getSecond()
    {
        return second;
    }

    @Override
    public boolean equals(final Object obj)
    {
        final Pair<?, ?> pair;

        if(this == obj)
        {
            return true;
        }

        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }

        pair = (Pair<?, ?>) obj;

        return Objects.equals(first, pair.first) &&
               Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(first, second);
    }

    public String toString()
    {
        return String.format("(%s, %s)", first, second);
    }
}
