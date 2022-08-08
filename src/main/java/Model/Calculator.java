package Model;

import Controller.MainController;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Calculator {

    private static final MainController mainController = new MainController();

    private static PipeSize getPipeSize() {
        return mainController.getPipeParameter();
    }

    private static boolean isUpset() {
        return mainController.getUpset();
    }

    private static double getWeightPipe(PipeSize size) {
        double result;

        switch (size) {
            case PIPE_33_40_3_38MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_33_40_3_38MM : Constants.WEIGHT_METER_PIPE_33_40_3_38MM;
            case PIPE_33_40_3_50MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_33_40_3_50MM : Constants.WEIGHT_METER_PIPE_33_40_3_50MM;
            case PIPE_33_40_4_55MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_33_40_4_55MM : Constants.WEIGHT_METER_PIPE_33_40_4_55MM;
            case PIPE_42_16_3_56MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_42_16_3_56MM : Constants.WEIGHT_METER_PIPE_42_16_3_56MM;
            case PIPE_42_16_4_85MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_42_16_4_85MM : Constants.WEIGHT_METER_PIPE_42_16_4_85MM;
            case PIPE_46_26_3_68MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_46_26_3_68MM : Constants.WEIGHT_METER_PIPE_46_26_3_68MM;
            case PIPE_46_26_4_00MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_46_26_4_00MM : Constants.WEIGHT_METER_PIPE_46_26_4_00MM;
            case PIPE_60_32_4_24MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_60_32_4_24MM : Constants.WEIGHT_METER_PIPE_60_32_4_24MM;
            case PIPE_60_32_4_83MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_60_32_4_83MM : Constants.WEIGHT_METER_PIPE_60_32_4_83MM;
            case PIPE_60_32_5_00MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_60_32_5_00MM : Constants.WEIGHT_METER_PIPE_60_32_5_00MM;
            case PIPE_60_32_6_45MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_60_32_6_45MM : Constants.WEIGHT_METER_PIPE_60_32_6_45MM;
            case PIPE_73_02_5_51MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_73_02_5_51MM : Constants.WEIGHT_METER_PIPE_73_02_5_51MM;
            case PIPE_73_02_7_01MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_73_02_7_01MM : Constants.WEIGHT_METER_PIPE_73_02_7_01MM;
            case PIPE_88_90_6_45MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_88_90_6_45MM : Constants.WEIGHT_METER_PIPE_88_90_6_45MM;
            case PIPE_88_90_7_34MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_88_90_7_34MM : Constants.WEIGHT_METER_PIPE_88_90_7_34MM;
            case PIPE_88_90_8_00MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_88_90_8_00MM : Constants.WEIGHT_METER_PIPE_88_90_8_00MM;
            case PIPE_88_90_9_52MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_88_90_9_52MM : Constants.WEIGHT_METER_PIPE_88_90_9_52MM;
            case PIPE_101_60_6_50MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_101_60_6_50MM : Constants.WEIGHT_METER_PIPE_101_60_6_50MM;
            case PIPE_101_60_6_65MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_101_60_6_65MM : Constants.WEIGHT_METER_PIPE_101_60_6_65MM;
            case PIPE_114_30_6_88MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_114_30_6_88MM : Constants.WEIGHT_METER_PIPE_114_30_6_88MM;
            case PIPE_114_30_7_00MM ->
                    result = (isUpset()) ? Constants.WEIGHT_METER_UPSET_PIPE_114_30_7_00MM : Constants.WEIGHT_METER_PIPE_114_30_7_00MM;
            default -> throw new IllegalStateException("Unexpected value: " + size);
        }
        return result;
    }

    /**
     * Преобразование веса НКТ в длину
     *
     * @param weight вес труб в тоннах
     * @return длина труб в метрах
     */

    public static BigDecimal convertWeightToLength(String weight) {
        BigDecimal value = new BigDecimal(weight);
        BigDecimal length = value.multiply(BigDecimal.valueOf(1000.00)).divide(BigDecimal.valueOf(getWeightPipe(getPipeSize())), RoundingMode.HALF_UP);
        length = length.setScale(2, RoundingMode.HALF_UP);
        return length;
    }

    /**
     * Преобразование веса НКТ в кол-во
     *
     * @param weight вес труб в тоннах
     * @return кол-во труб в штуках
     */

    public static BigDecimal convertWeightToCount(String weight) {
        BigDecimal value = new BigDecimal(weight);
        BigDecimal count = value.multiply(BigDecimal.valueOf(1000.00))
                .divide(BigDecimal.valueOf(getWeightPipe(getPipeSize())).multiply(BigDecimal.valueOf(10.00)), RoundingMode.HALF_UP);
        count = count.setScale(0, RoundingMode.HALF_UP);
        return count;
    }

    /**
     * Преобразование длины НКТ в кол-во
     *
     * @param length длина труб в метрах
     * @return кол-во труб в штуках
     */

    public static BigDecimal convertLengthToCount(String length) {
        BigDecimal value = new BigDecimal(length);
        BigDecimal count = value.divide(BigDecimal.valueOf(10.00), RoundingMode.HALF_UP);
        count = count.setScale(0, RoundingMode.HALF_UP);
        return count;
    }

    /**
     * Преобразование длины НКТ в вес
     *
     * @param length длина труб в метрах
     * @return вес труб в тоннах
     */

    public static BigDecimal convertLengthToWeight(String length) {
        BigDecimal value = new BigDecimal(length);
        BigDecimal weight = value.multiply(BigDecimal.valueOf(getWeightPipe(getPipeSize())))
                .divide(BigDecimal.valueOf(1000.00), RoundingMode.HALF_UP);
        weight = weight.setScale(2, RoundingMode.HALF_UP);
        return weight;
    }

    /**
     * Преобразование кол-ва НКТ в длину
     *
     * @param count кол-во труб в штуках
     * @return длина труб в метрах
     */

    public static BigDecimal convertCountToLength(String count) {
        BigDecimal value = new BigDecimal(count);
        BigDecimal length = value.multiply(BigDecimal.valueOf(10.00));
        length = length.setScale(2, RoundingMode.HALF_UP);
        return length;
    }

    /**
     * Преобразование кол-ва НКТ в вес
     *
     * @param count кол-во труб в штуках
     * @return вес труб в тоннах
     */

    public static BigDecimal convertCountToWeight(String count) {
        BigDecimal weight = convertCountToLength(count).multiply(BigDecimal.valueOf(getWeightPipe(getPipeSize())))
                .divide(BigDecimal.valueOf(1000.00),RoundingMode.HALF_UP);
        weight = weight.setScale(2, RoundingMode.HALF_UP);
        return weight;
    }

}
