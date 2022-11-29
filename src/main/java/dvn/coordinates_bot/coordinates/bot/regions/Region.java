package dvn.coordinates_bot.coordinates.bot.regions;

public enum Region {

    BALASH("Балашихинский", 55.791202, 37.956857, 0.19, 0.45),
    VOSKR("Воскресенский", 55.380718, 38.645685, 0.43, 0.75),
    EGOR("Егорьевский", 55.304252, 39.225503, 0.7, 1.05),
    ZHUK("Жуковский", 54.999428, 36.893638, 0.53, 0.68),
    ZARAI("Зарайский", 54.734357, 38.898103, 0.38, 0.83),
    KOLOM("Коломенский", 55.036597, 38.637034, 0.63, 1.48),
    LUHOV("Луховицкий", 54.948649, 39.194250, 0.51, 0.92),
    NOGIN("Ногинский", 55.890325, 38.438776, 0.46, 0.74),
    OZERSK("Озерский", 54.892726, 38.546835, 0.33, 0.58),
    OREH_ZU("Орехово-Зуевский", 55.666426, 39.073894, 0.61, 1.08),
    PAV_POS("Павлово-Посадский", 55.829669, 38.696467, 0.46, 0.6),
    RAMEN("Раменский", 55.478118, 38.208511, 0.56, 0.87),
    SHATUR("Шатурский", 55.470882, 39.788486, 1.09, 1.33),
    SHELK("Щелковский", 56.002017, 38.134184, 0.49, 0.96);

    private String name;
    private double centerLong;
    private double centerLati;
    private double height;
    private double width;

    Region(String name, double centerLong, double centerLati, double height, double width) {
        this.name = name;
        this.centerLong = centerLong;
        this.centerLati = centerLati;
        this.height = height;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public double getCenterLong() {
        return centerLong;
    }

    public double getCenterLati() {
        return centerLati;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
