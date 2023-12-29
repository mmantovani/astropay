package astropay.depositapi.repository;

import java.time.LocalDateTime;
//import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import astropay.depositapi.model.Deposit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CustomDepositRepositoryImpl implements CustomDepositRepository {

	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public Page<Deposit> findByUserIdAndStatusAndFromAndTo(Long userId, String status, LocalDateTime from,
			LocalDateTime to, Pageable pageable, Sort sort) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Deposit> query = cb.createQuery(Deposit.class);
	    Root<Deposit> root = query.from(Deposit.class);

	    // Filters
	    Predicate predicate = buildPredicate(cb, root, userId, status, from, to);
	    query.select(root).where(predicate);

	    // Count total elements (filtered)
	    CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
	    Root<Deposit> countRoot = countQuery.from(Deposit.class);
	    Predicate countPredicate = buildPredicate(cb, countRoot, userId, status, from, to);
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
	    TypedQuery<Deposit> typedQuery = entityManager.createQuery(query);
	    typedQuery.setFirstResult((int) pageable.getOffset());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    // Return paginated results
	    List<Deposit> resultList = typedQuery.getResultList();
	    return new PageImpl<>(resultList, pageable, count);
	}


	private Predicate buildPredicate(CriteriaBuilder builder, Root<Deposit> root,
			Long userId, String status, LocalDateTime from, LocalDateTime to) {

		Predicate predicate = builder.conjunction();

		if (userId != null) {
			predicate = builder.and(predicate, builder.equal(root.get("userId"), userId));
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
