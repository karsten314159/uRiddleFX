package solid.stream;

import solid.functions.Func1;

public class Primitives {

    /**
     * Creates a new stream of {@link Integer} type that contains all items of a given array.
     *
     * @param integers an array to get items from.
     * @return a new stream of {@link Integer} type that contains all items of a given array.
     */
    public static Stream<Integer> box(final int[] integers) {
        return new FixedSizeStream<Integer>(integers.length, index -> integers[index]);
    }

    /**
     * Creates a new stream of {@link Long} type that contains all items of a given array.
     *
     * @param longs an array to get items from.
     * @return a new stream of {@link Long} type that contains all items of a given array.
     */
    public static Stream<Long> box(final long[] longs) {
        return new FixedSizeStream<Long>(longs.length, new Func1<Integer, Long>() {
            @Override
            public Long call(Integer index) {
                return longs[index];
            }
        });
    }

    /**
     * Creates a new stream of {@link Float} type that contains all items of a given array.
     *
     * @param floats an array to get items from.
     * @return a new stream of {@link Float} type that contains all items of a given array.
     */
    public static Stream<Float> box(final float[] floats) {
        return new FixedSizeStream<>(floats.length, new Func1<Integer, Float>() {
            @Override
            public Float call(Integer index) {
                return floats[index];
            }
        });
    }

    /**
     * Creates a new stream of {@link Byte} type that contains all items of a given array.
     *
     * @param bytes an array to get items from.
     * @return a new stream of {@link Byte} type that contains all items of a given array.
     */
    public static Stream<Byte> box(final byte[] bytes) {
        return new FixedSizeStream<>(bytes.length, new Func1<Integer, Byte>() {
            @Override
            public Byte call(Integer index) {
                return bytes[index];
            }
        });
    }

    /**
     * Creates a new stream of {@link Double} type that contains all items of a given array.
     *
     * @param doubles an array to get items from.
     * @return a new stream of {@link Double} type that contains all items of a given array.
     */
    public static Stream<Double> box(final double[] doubles) {
        return new FixedSizeStream<>(doubles.length, new Func1<Integer, Double>() {
            @Override
            public Double call(Integer index) {
                return doubles[index];
            }
        });
    }
}
