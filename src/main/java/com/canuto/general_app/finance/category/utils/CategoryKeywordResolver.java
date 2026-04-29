package com.canuto.general_app.finance.category.utils;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CategoryKeywordResolver {
    
    private static final Map<CategoryType, List<String>> KEYWORDS = Map.of(
        CategoryType.FOOD, List.of("food", "lunch", "dinner", "breakfast", "meal",
                    "restaurant", "eat", "eating", "burger", "pizza",
                    "coffee", "snack", "brunch", "sushi", "bbq"),
        CategoryType.TRANSPORT, List.of("transport", "uber", "bus", "taxi", "metro",
                    "train", "ride", "trip", "ticket", "gas",
                    "fuel", "toll", "parking"),
        CategoryType.ENTERTAINMENT, List.of("movie", "cinema", "netflix", "spotify",
                    "game", "gaming", "concert", "party",
                    "streaming", "subscription")
    );

    public CategoryType resolve(String text) {
        String lowerText = text.toLowerCase();

        for (Map.Entry<CategoryType, List<String>> entry : KEYWORDS.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (lowerText.contains(keyword)) {
                    return entry.getKey();
                }
            }
        }

        return CategoryType.OTHER;
    }
}
