package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.InvalidClassificationException;
import ee.taltech.varadehaldamine.model.Classification;
import ee.taltech.varadehaldamine.modelDTO.ClassificationInfo;
import ee.taltech.varadehaldamine.repository.ClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificationService {
    @Autowired
    private ClassificationRepository classificationRepository;

    /**
     * Method to get all classifications from db.
     *
     * @return list of classifications
     */
    public List<Classification> findAll() {
        return classificationRepository.findAll();
    }

    public Classification addClassification(ClassificationInfo classificationInfo) {
        try {
            if (classificationInfo != null
                    && !classificationInfo.getSubClass().isBlank()
                    && !classificationInfo.getMainClass().isBlank()) {
                Classification classification =
                        new Classification(classificationInfo.getSubClass(), classificationInfo.getMainClass());
                return classificationRepository.save(classification);
            } else {
                throw new InvalidClassificationException("Error when saving Classification");
            }
        } catch (InvalidClassificationException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Method to change main class of the subclass
     *
     * @param classification main class holder
     * @param subClass       subclass to find right classification
     * @return changed classification
     */
    public Classification update(Classification classification, String subClass) {
        Classification dbClassification = classificationRepository.findClassificationBySubClass(subClass);
        dbClassification.setMainClass(classification.getMainClass());
        return classificationRepository.save(dbClassification);
    }

    /**
     * Method to check if classification exists with given main class and subclass.
     *
     * @param mainClass main class
     * @param subClass  subclass
     * @return exists or not
     */
    public boolean doesClassificationExist(String mainClass, String subClass) {
        List<Classification> all = findAll();
        for (Classification classification : all) {
            if (classification.getSubClass().equals(subClass) && classification.getMainClass().equals(mainClass)) {
                return true;
            }
        }
        return false;
    }


}
