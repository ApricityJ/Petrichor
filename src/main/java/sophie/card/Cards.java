package sophie.card;

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sophie.Constant;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cards {

    private static final Logger logger = LoggerFactory.getLogger(Cards.class);
    private static final File FILE_CARD_NUMS = new File(Constant.PATH_CARD_NUMS);
    private static final List<String> cardNums;

    static {
        cardNums = read(FILE_CARD_NUMS);
    }

    public static List<String> getCardNums() {
        return cardNums;
    }

    public static List<List<Integer>> getComSplitCardNums() {
        return getCardNums().stream().map(Cards::toComSplitCardNum).collect(Collectors.toList());
    }

    public static List<Integer> toComSplitCardNum(String cardNum) {
        List<Integer> splitCardNum = new ArrayList<>();
        splitCardNum.add(Integer.valueOf(toBIN(cardNum)));
        splitCardNum.add(Integer.valueOf(toRegion(cardNum)));
        splitCardNum.addAll(toSplitSeqNum(cardNum).stream().map(Integer::valueOf).collect(Collectors.toList()));
        return splitCardNum;
    }

    public static List<String> read(File file) {
        List<String> cardNums = new ArrayList<>();
        try {
            cardNums = Files.readLines(file, StandardCharsets.UTF_8)
                    .stream()
                    .map(s -> s.split("\\|!")[0])
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("READ FILE ERROR : {}", e.toString());
        }
        return cardNums;
    }

    private static String toBIN(String cardNum) { return cardNum.substring(0, 6); }

    private static String toRegion(String cardNum) {
        return cardNum.substring(6, 9);
    }

    private static String toSeqNum(String cardNum) {
        return cardNum.substring(9, 18);
    }

    private static List<String> toSplitSeqNum(String cardNum) {
        List<String> seqNum = new ArrayList<>();
        // skip check bit
        for (int i = 9; i < 18; i++) {
            seqNum.add(cardNum.substring(i, i + 1));
        }
        return seqNum;
    }

    private static void analyze() {
        Map<String, Long> eachPart;

        eachPart = getCardNums().stream().map(Cards::toBIN)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        logger.info(eachPart.toString());

        eachPart = getCardNums().stream().map(Cards::toRegion)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        logger.info(eachPart.toString());

        for (int i = 0; i < 9; i++) {
            int index = i;
            eachPart = getCardNums().stream().map(Cards::toSplitSeqNum)
                    .collect(Collectors.groupingBy(e -> e.get(index), Collectors.counting()));
            logger.info(eachPart.toString());
        }
    }


    public static void main(String[] args) {
        analyze();
    }
}
