package dev.oleksa.product.constants;

public enum SortingOrder {
    ASC("asc"),
    DESC("desc");
    public final String label;
    SortingOrder(String label) {
        this.label = label;
    }
}
