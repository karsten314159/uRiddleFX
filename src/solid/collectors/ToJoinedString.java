package solid.collectors;


import solid.functions.Func1;

/**
 * This class is a syntax enhancement around {@code TextUtils#join(CharSequence, Iterable)} method
 * to chain it with {@link solid.stream.Stream<String>} streams.
 */
public class ToJoinedString {

    private static Func1<Iterable<String>, String> TO_JOINED_STRING = toJoinedString("");

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link String} type into a joined string.
     *
     * @return a method that converts an iterable stream of {@link String} type into a joined string.
     */
    public static Func1<Iterable<String>, String> toJoinedString() {
        return TO_JOINED_STRING;
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link String} type into a joined string.
     *
     * @param delimiter a delimiter
     * @return a method that converts an iterable stream of {@link String} type into a joined string.
     */
    public static Func1<Iterable<String>, String> toJoinedString(final String delimiter) {
        return new Func1<Iterable<String>, String>() {
            @Override
            public String call(Iterable<String> iterable) {
                StringBuilder builder = new StringBuilder();
                boolean first = true;
                for (String string : iterable) {
                    if (first)
                        first = false;
                    else
                        builder.append(delimiter);
                    builder.append(string);
                }
                return builder.toString();
            }
        };
    }
}
