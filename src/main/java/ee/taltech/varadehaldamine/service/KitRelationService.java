package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.model.KitRelation;
import ee.taltech.varadehaldamine.modelDTO.AssetInfo;
import ee.taltech.varadehaldamine.repository.KitRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitRelationService {

    @Autowired
    private KitRelationRepository kitRelationRepository;

    public List<String> findAllMajorAssets(){
        return kitRelationRepository.findAllMajorAssets();
    }
}
