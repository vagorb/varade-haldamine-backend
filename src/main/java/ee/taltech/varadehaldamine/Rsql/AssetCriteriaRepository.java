package ee.taltech.varadehaldamine.Rsql;

import ee.taltech.varadehaldamine.model.Address;
import ee.taltech.varadehaldamine.model.Asset;
import ee.taltech.varadehaldamine.model.Classification;
import ee.taltech.varadehaldamine.model.Possessor;
import ee.taltech.varadehaldamine.modelDTO.AssetInfoShort;
import org.springframework.data.domain.*;
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


    public Page<AssetInfoShort> findAllWithFilters(PageImpl<AssetInfoShort> assetPage,
                                          AssetSearchCriteria assetSearchCriteria, String order, String sortBy) {
        CriteriaQuery<Asset> criteriaQuery = criteriaBuilder.createQuery(Asset.class);
        CriteriaQuery<Address> addressCriteriaQuery = criteriaBuilder.createQuery(Address.class);
        CriteriaQuery<Possessor> possessorCriteriaQuery = criteriaBuilder.createQuery(Possessor.class);
        CriteriaQuery<Classification> classificationCriteriaQuery = criteriaBuilder.createQuery(Classification.class);
        Root<Asset> assetRoot = criteriaQuery.from(Asset.class);
        Root<Possessor> possessorRoot = possessorCriteriaQuery.from(Possessor.class);
        Root<Address> addressRoot = addressCriteriaQuery.from(Address.class);
        Root<Classification> classificationRoot = classificationCriteriaQuery.from(Classification.class);
        Predicate predicate = getPredicate(assetSearchCriteria, assetRoot, possessorRoot, addressRoot, classificationRoot);


        CriteriaQuery<AssetInfoShort> assetInfoCriteriaQuery = criteriaBuilder.createQuery(AssetInfoShort.class);
        assetInfoCriteriaQuery.where(predicate);


        //setOrder(criteriaQuery, assetRoot, order, sortBy);

        TypedQuery<AssetInfoShort> typedQuery = entityManager.createQuery(assetInfoCriteriaQuery);
        typedQuery.setFirstResult(assetPage.getNumber() * assetPage.getSize());
        typedQuery.setMaxResults(assetPage.getSize());

        Pageable pageable = getPageable(assetPage, order, sortBy);

        long assetsCount = getAssetsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, assetsCount);
    }

    public void setOrder(CriteriaQuery<Asset> criteriaQuery,
                         Root<Asset> assetRoot, String order, String sortBy) {
        if (order.equals("DESC")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(assetRoot.get(sortBy)));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(assetRoot.get(sortBy)));
        }
    }

    private Predicate getPredicate(AssetSearchCriteria assetSearchCriteria,
                                   Root<Asset> assetRoot,
                                   Root<Possessor> possessorRoot,
                                   Root<Address> addressRoot,
                                   Root<Classification> classificationRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(assetSearchCriteria.getId())) {
            predicates.add(
                    criteriaBuilder.like(assetRoot.get("id"),
                            "%" + assetSearchCriteria.getId() + "%"));
        }
        if (Objects.nonNull(assetSearchCriteria.getName())) {
            predicates.add(
                    criteriaBuilder.like(assetRoot.get("name"),
                            "%" + assetSearchCriteria.getName() + "%"));
        }
        if (Objects.nonNull(assetSearchCriteria.getStructuralUnitPlusSubdivision())) {
            predicates.add(
                    criteriaBuilder.equal(possessorRoot.get("structuralUnit"),
                            assetSearchCriteria.getStructuralUnitPlusSubdivision()));
        }
        if (Objects.nonNull(assetSearchCriteria.getStructuralUnitPlusSubdivision())) {
            predicates.add(
                    criteriaBuilder.equal(possessorRoot.get("subdivision"),
                            assetSearchCriteria.getStructuralUnitPlusSubdivision()));
        }
        if (Objects.nonNull(assetSearchCriteria.getMainClassPlusSubclass())) {
            predicates.add(
                    criteriaBuilder.like(classificationRoot.get("mainClass"),
                            "%" + assetSearchCriteria.getMainClassPlusSubclass() + "%"));
        }
        if (Objects.nonNull(assetSearchCriteria.getMainClassPlusSubclass())) {
            predicates.add(
                    criteriaBuilder.like(classificationRoot.get("subClass"),
                            "%" + assetSearchCriteria.getMainClassPlusSubclass() + "%"));
        }
        if (Objects.nonNull(assetSearchCriteria.getBuildingAbbreviationPlusRoom())) {
            predicates.add(
                    criteriaBuilder.like(addressRoot.get("buildingAbbreviature"),
                            "%" + assetSearchCriteria.getBuildingAbbreviationPlusRoom() + "%"));
        }
        if (Objects.nonNull(assetSearchCriteria.getBuildingAbbreviationPlusRoom())) {
            predicates.add(
                    criteriaBuilder.like(addressRoot.get("room"),
                            "%" + assetSearchCriteria.getBuildingAbbreviationPlusRoom() + "%"));
        }
        if (Objects.nonNull(assetSearchCriteria.getActive())) {
            predicates.add(
                    criteriaBuilder.equal(assetRoot.get("active"),
                            assetSearchCriteria.getActive()));
        }

        //add it later, seichas zabivajem her
//        private Integer lifeMonthsLeft;


//        if (Objects.nonNull(assetSearchCriteria.getSubClass())) {
//            predicates.add(
//                    criteriaBuilder.like(assetRoot.get("subClass"),
//                            "%" + assetSearchCriteria.getSubClass() + "%"));
//        }

//        if (Objects.nonNull(assetSearchCriteria.getUserId())) {
//            predicates.add(
//                    criteriaBuilder.equal(assetRoot.get("userId"),
//                            assetSearchCriteria.getUserId()));
//        }
//        if (Objects.nonNull(assetSearchCriteria.getPossessorId())) {
//            predicates.add(
//                    criteriaBuilder.equal(assetRoot.get("possessorId"),
//                            assetSearchCriteria.getPossessorId()));
//        }
//        if (Objects.nonNull(assetSearchCriteria.getExpirationDate())) {
//            predicates.add(
//                    criteriaBuilder.like(assetRoot.get("expirationDate"),
//                            "%" + assetSearchCriteria.getExpirationDate() + "%"));
//        }
//        if (Objects.nonNull(assetSearchCriteria.getDelicateCondition())) {
//            predicates.add(
//                    criteriaBuilder.equal(assetRoot.get("delicateCondition"),
//                            assetSearchCriteria.getDelicateCondition()));
//        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


    public Pageable getPageable(PageImpl<AssetInfoShort> assetPage, String order, String sortBy) {

        Sort sort;
        if (order.equals("DESC")) {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        } else {
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        }
        return PageRequest.of(assetPage.getNumber(), assetPage.getSize(), sort);
    }

    private long getAssetsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Asset> countRoot = countQuery.from(Asset.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }



}
