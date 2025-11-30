package main.java.com.airtribe.studentmanagement.interfaces;
public interface Searchable {

    String searchableText();

    default boolean matches(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return true;
        }
        String source = searchableText();
        return source != null && source.toLowerCase().contains(keyword.toLowerCase());
    }
}
