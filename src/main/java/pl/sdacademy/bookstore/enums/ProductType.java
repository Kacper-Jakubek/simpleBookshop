package pl.sdacademy.bookstore.enums;

public enum ProductType {
    SOFT_COVER("Soft cover"),
    HARD_COVER("Hard cover"),
    EBOOK("E-book"),
    AUDIOBOOK("Audio book");

    private String label;

    ProductType(String label){
        this.label = label;
    }

    public static ProductType valueOfLabel(String label) {
        for (ProductType option : ProductType.values()) {
            if (option.label.equals(label)) {
                return option;
            }
        }
        return null;
    }
}
