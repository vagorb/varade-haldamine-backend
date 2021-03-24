package ee.taltech.varadehaldamine.Varadehaldamine.Service;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Classification;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.ClassificationInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.ClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificationService {
    @Autowired
    private ClassificationRepository classificationRepository;

    public List<Classification> findAll(){
        return classificationRepository.findAll();
    }

    public Classification addClassification(ClassificationInfo classificationInfo){
        try {
            if (classificationInfo != null && !classificationInfo.getSubclass().isBlank() && !classificationInfo.getMain_class().isBlank()){
                Classification classification = new Classification(classificationInfo.getSubclass(), classificationInfo.getMain_class());
                return classificationRepository.save(classification);
            }
        } catch (Exception ignored) {}
        return null;
    }

}
