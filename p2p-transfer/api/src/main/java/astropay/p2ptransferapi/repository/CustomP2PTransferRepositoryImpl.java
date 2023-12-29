package astropay.p2ptransferapi.repository;

import java.time.LocalDateTime;
//import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import astropay.p2ptransferapi.model.P2PTransfer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CustomP2PTransferRepositoryImpl implements CustomP2PTransferRepository {

	@PersistenceContext
    private EntityManager entityManager;

	@Override
	public Page<P2PTransfer> findBySenderIdAndStatusAndFromAndTo(Long senderId, String status, LocalDateTime from,
			LocalDateTime to, Pageable pageable, Sort sort) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<P2PTransfer> query = cb.createQuery(P2PTransfer.class);
	    Root<P2PTransfer> root = query.from(P2PTransfer.class);

	    // Filters
	    Predicate predicate = buildPredicate(cb, root, senderId, status, from, to);
	    query.select(root).where(predicate);

	    // Count total elements (filtered)
	    CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
	    Root<P2PTransfer> countRoot = countQuery.from(P2PTransfer.class);
	    Predicate countPredicate = buildPredicate(cb, countRoot, senderId, status, from, to);
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
	    TypedQuery<P2PTransfer> typedQuery = entityManager.createQuery(query);
	    typedQuery.setFirstResult((int) pageable.getOffset());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    // Return paginated results
	    List<P2PTransfer> resultList = typedQuery.getResultList();
	    return new PageImpl<>(resultList, pageable, count);
	}


	private Predicate buildPredicate(CriteriaBuilder builder, Root<P2PTransfer> root,
			Long senderId, String status, LocalDateTime from, LocalDateTime to) {

		Predicate predicate = builder.conjunction();

		if (senderId != null) {
			predicate = builder.and(predicate, builder.equal(root.get("senderId"), senderId));
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
