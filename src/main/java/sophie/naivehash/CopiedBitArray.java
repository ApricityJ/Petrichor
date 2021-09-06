package sophie.naivehash;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sophie.util.CheckUtil;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLongArray;

// From com.google.common.hash.BloomFilterStrategies.LockFreeBitArray
public class CopiedBitArray {

    private static final Logger logger = LoggerFactory.getLogger(CopiedBitArray.class);
    private final AtomicLongArray data;

    public CopiedBitArray(long bits) {
        CheckUtil.checkArgument(bits > 0L, "data length is zero!");
        this.data = new AtomicLongArray(CheckUtil.checkedCast((long)Math.ceil((double)bits / 64L)));
    }

    public boolean set(long bitIndex) {
        if (this.get(bitIndex)) {
            return false;
        } else {
            int longIndex = (int) (bitIndex >>> 6);
            long mask = 1L << (int) bitIndex;

            long oldValue;
            long newValue;
            do {
                oldValue = this.data.get(longIndex);
                newValue = oldValue | mask;
                if (oldValue == newValue) {
                    return false;
                }
            } while (!this.data.compareAndSet(longIndex, oldValue, newValue));
            return true;
        }
    }

    public boolean get(long bitIndex) {
        return (this.data.get((int) (bitIndex >>> 6)) & 1L << (int) bitIndex) != 0L;
    }

    public static long[] toPlainArray(AtomicLongArray atomicLongArray) {
        long[] array = new long[atomicLongArray.length()];

        for (int i = 0; i < array.length; ++i) {
            array[i] = atomicLongArray.get(i);
        }

        return array;
    }

    public long getBitSize() {
        return (long) this.data.length() * 64L;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o instanceof CopiedBitArray) {
            CopiedBitArray copiedBitArray = (CopiedBitArray) o;
            return Arrays.equals(toPlainArray(this.data), toPlainArray(copiedBitArray.data));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(toPlainArray(this.data));
    }
}
