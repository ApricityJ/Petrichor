package sophie.naivehash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sophie.Constant;

public class NaiveHashFunc {

    private static final Logger logger = LoggerFactory.getLogger(NaiveHashFunc.class);

    public static long naiveFNV(String key)
    {
        long p = 1099511628211L;
        long hash = 0L;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash ^ key.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 31;
        hash ^= hash >> 17;
        hash += hash << 5;
        if (hash>0) {
            return hash % Constant.ARRAY_BITS;
        } else {
            return -hash % Constant.ARRAY_BITS;
        }
    }
}
