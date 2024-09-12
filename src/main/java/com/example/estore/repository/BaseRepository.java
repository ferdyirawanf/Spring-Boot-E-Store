package com.example.estore.repository;
import com.example.estore.model.Auditable;
import com.example.estore.utils.specification.NonDiscardedSpecification;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends Auditable, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    @Override
    @NonNull
    default List<T> findAll() {
        System.out.println("sini");
        return findAll(NonDiscardedSpecification.notDiscarded()); }

    @Override
    @NonNull
    default Page<T> findAll(@NonNull Pageable pageable) {
        System.out.println("siniiii");
        return findAll(NonDiscardedSpecification.notDiscarded(), pageable);
    }

    @Override
    @NonNull
    default Optional<T> findById(@NonNull ID id) {
        return findOne((Specification<T>) NonDiscardedSpecification.<T>notDiscarded().and(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id)
        ));
    }

    @Modifying
    @Query("UPDATE #{#entityName} e SET e.discardedAt = :discardedAt WHERE e.id = :id")
    void softDelete(@Param("id") ID id, @Param("discardedAt") LocalDateTime discardedAt);

    @Override
    default void deleteById(@NonNull ID id) {
        System.out.println("Halooo");
        softDelete(id, LocalDateTime.now());
    }
}
