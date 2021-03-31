package ee.taltech.varadehaldamine.Varadehaldamine.Service;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Classification;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.ClassificationInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.ClassificationRepository;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.exception.InvalidClassificationException;
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
            System.out.println(e);
        }
        return null;
    }

}
