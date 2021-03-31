package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.model.Classification;
import ee.taltech.varadehaldamine.modelDTO.ClassificationInfo;
import ee.taltech.varadehaldamine.repository.ClassificationRepository;
import ee.taltech.varadehaldamine.exception.InvalidClassificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificationService {
    @Autowired
    private ClassificationRepository classificationRepository;

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

}
