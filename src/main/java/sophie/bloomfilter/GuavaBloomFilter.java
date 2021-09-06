package sophie.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class GuavaBloomFilter {

    private static final Logger logger = LoggerFactory.getLogger(GuavaBloomFilter.class);
    private final BloomFilter<String> bloomFilter;

    public GuavaBloomFilter(int bloomFilterCapacity, double fpp) {
        this.bloomFilter = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8), bloomFilterCapacity, fpp);
        ;
    }

    public boolean insert(String cardNum) {
        return bloomFilter.put(cardNum);
    }

    public boolean search(String cardNum) {
        return bloomFilter.mightContain(cardNum);
    }
}
