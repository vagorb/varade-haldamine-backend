package ee.taltech.varadehaldamine.Varadehaldamine.Rsql;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class AssetCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public AssetCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public Page<Asset> findAllWithFilters(AssetPage assetPage,
                                          AssetSearchCriteria assetSearchCriteria) {
        CriteriaQuery<Asset> criteriaQuery = criteriaBuilder.createQuery(Asset.class);
        Root<Asset> assetRoot = criteriaQuery.from(Asset.class);
        Predicate predicate = getPredicate(assetSearchCriteria, assetRoot);
        criteriaQuery.where(predicate);
        setOrder(assetPage, criteriaQuery, assetRoot);

        TypedQuery<Asset> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(assetPage.getPageNumber() * assetPage.getPageSize());
        typedQuery.setMaxResults(assetPage.getPageSize());

        Pageable pageable = getPageable(assetPage);

        long assetsCount = getAssetsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, assetsCount);
    }

    public void setOrder(AssetPage assetPage,
                         CriteriaQuery<Asset> criteriaQuery,
                         Root<Asset> assetRoot) {
        if (assetPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(assetRoot.get(assetPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(assetRoot.get(assetPage.getSortBy())));
        }
    }

    private Predicate getPredicate(AssetSearchCriteria assetSearchCriteria,
                                   Root<Asset> assetRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(assetSearchCriteria.getName())) {
            predicates.add(
                    criteriaBuilder.like(assetRoot.get("name"),
                            "%" + assetSearchCriteria.getName() + "%"));
        }

        if (Objects.nonNull(assetSearchCriteria.getSubClass())) {
            predicates.add(
                    criteriaBuilder.like(assetRoot.get("subClass"),
                            "%" + assetSearchCriteria.getSubClass() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


    public Pageable getPageable(AssetPage assetPage) {
        Sort sort = Sort.by(assetPage.getSortDirection(), assetPage.getSortBy());
        return PageRequest.of(assetPage.getPageNumber(), assetPage.getPageSize(), sort);
    }

    private long getAssetsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Asset> countRoot = countQuery.from(Asset.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

//    public Page<Employee> findAllWithFilters(EmployeePage employeePage,
//                                             EmployeeSearchCriteria employeeSearchCriteria){
//        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
//        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
//        Predicate predicate = getPredicate(employeeSearchCriteria, employeeRoot);
//        criteriaQuery.where(predicate);
//        setOrder(employeePage, criteriaQuery, employeeRoot);
//
//        TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
//        typedQuery.setFirstResult(employeePage.getPageNumber() * employeePage.getPageSize());
//        typedQuery.setMaxResults(employeePage.getPageSize());
//
//        Pageable pageable = getPageable(employeePage);
//
//        long employeesCount = getEmployeesCount(predicate);
//
//        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
//    }


//    private long getEmployeesCount(Predicate predicate) {
//        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
//        Root<Employee> countRoot = countQuery.from(Employee.class);
//        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
//        return entityManager.createQuery(countQuery).getSingleResult();
//    }



}
