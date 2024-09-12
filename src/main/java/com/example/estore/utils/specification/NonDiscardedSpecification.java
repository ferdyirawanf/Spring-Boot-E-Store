package com.example.estore.utils.specification;
import com.example.estore.model.Auditable;
import org.springframework.data.jpa.domain.Specification;

public class NonDiscardedSpecification {
    public static <T extends Auditable> Specification<T> notDiscarded() {
        return (root, query, cb) -> cb.isNull(root.get("discardedAt"));
    }
}
