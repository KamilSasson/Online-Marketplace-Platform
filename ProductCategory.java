package AmitGarfel_KamilSason_DanSwirsky;

public enum ProductCategory {
    CHILDREN(1, "Children"),
    ELECTRICITY(2, "Electricity"),
    OFFICE(3, "Office"),
    CLOTHING(4, "Clothing");

    private final int index;
    private final String name;

    ProductCategory(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public static ProductCategory getByIndex(int index) {
        ProductCategory[] categories = values();
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].getIndex() == index) {
                return categories[i];
            }
        }
        return null;
    }
}
