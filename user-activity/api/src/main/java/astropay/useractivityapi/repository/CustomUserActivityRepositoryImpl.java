package astropay.useractivityapi.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import astropay.useractivityapi.model.UserActivity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CustomUserActivityRepositoryImpl implements CustomUserActivityRepository {

	@PersistenceContext
    private EntityManager entityManager;

	@Override
    public Page<UserActivity> findByUserIdAndActivityTypeAndStatusAndFromAndTo(Long userId, String activityType, String status,
            LocalDateTime from, LocalDateTime to, Pageable pageable, Sort sort) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<UserActivity> query = cb.createQuery(UserActivity.class);
	    Root<UserActivity> root = query.from(UserActivity.class);

	    // Filters
	    Predicate predicate = buildPredicate(cb, root, userId, activityType, status, from, to);
	    query.select(root).where(predicate);

	    // Count total elements (filtered)
	    CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
	    Root<UserActivity> countRoot = countQuery.from(UserActivity.class);
	    Predicate countPredicate = buildPredicate(cb, countRoot, userId, activityType, status, from, to);
	    countQuery.select(cb.count(countRoot)).where(countPredicate);
	    long count = entityManager.createQuery(countQuery).getSingleResult();

	    // Apply sorting
	    if (sort != null) {
	        List<Order> orders = new ArrayList<>();
	        for (Sort.Order order : sort) {
	            if (order.isAscending()) {
	                orders.add(cb.asc(root.get(order.getProperty())));
	            } else {
	                orders.add(cb.desc(root.get(order.getProperty())));
	            }
	        }
	        query.orderBy(orders);
	    }

	    // Pagination
	    TypedQuery<UserActivity> typedQuery = entityManager.createQuery(query);
	    typedQuery.setFirstResult((int) pageable.getOffset());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    // Return paginated results
	    List<UserActivity> resultList = typedQuery.getResultList();
	    return new PageImpl<>(resultList, pageable, count);
	}


	private Predicate buildPredicate(CriteriaBuilder builder, Root<UserActivity> root,
			Long userId, String activityType, String status, LocalDateTime from, LocalDateTime to) {

		Predicate predicate = builder.conjunction();

		if (userId != null) {
			predicate = builder.and(predicate, builder.equal(root.get("userId"), userId));
		}
        if (activityType != null) {
            predicate = builder.and(predicate, builder.equal(root.get("activityType"), activityType));
        }
		if (status != null) {
			predicate = builder.and(predicate, builder.equal(root.get("status"), status));
		}
		if (from != null) {
			predicate = builder.and(predicate, builder.greaterThan(root.get("createdAt"), from));
		}
		if (to != null) {
			predicate = builder.and(predicate, builder.lessThan(root.get("createdAt"), to));
		}

		return predicate;
	}
}
