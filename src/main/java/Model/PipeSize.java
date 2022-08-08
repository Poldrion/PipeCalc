package Model;

public enum PipeSize {
    PIPE_33_40_3_38MM("33.40x3.38"),
    PIPE_33_40_3_50MM("33.40x3.50"),
    PIPE_33_40_4_55MM("33.40x4.55"),
    PIPE_42_16_3_56MM("42.16x3.56"),
    PIPE_42_16_4_85MM("42.16x4.85"),
    PIPE_46_26_3_68MM("46.26x3.68"),
    PIPE_46_26_4_00MM("46.26x4.00"),
    PIPE_60_32_4_24MM("60.32x4.24"),
    PIPE_60_32_4_83MM("60.32x4.83"),
    PIPE_60_32_5_00MM("60.32x5.00"),
    PIPE_60_32_6_45MM("60.32x6.45"),
    PIPE_73_02_5_51MM("73.02x5.51"),
    PIPE_73_02_7_01MM("73.02x7.01"),
    PIPE_88_90_6_45MM("88.90x6.45"),
    PIPE_88_90_7_34MM("88.90x7.34"),
    PIPE_88_90_8_00MM("88.90x8.00"),
    PIPE_88_90_9_52MM("88.90x9.52"),
    PIPE_101_60_6_50MM("101.60x6.50"),
    PIPE_101_60_6_65MM("101.60x6.65"),
    PIPE_114_30_6_88MM("114.30x6.88"),
    PIPE_114_30_7_00MM("114.30x7.00");

    private final String size;
    PipeSize(String size) {
        this.size = size;
    }
    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return size;
    }
}
