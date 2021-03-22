package ee.taltech.varadehaldamine.Varadehaldamine.Service;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Possessor;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.PossessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PossessorService {

    @Autowired
    private PossessorRepository possessorRepository;

    public List<Possessor> findAll(){
        return possessorRepository.findAll();
    }
}
