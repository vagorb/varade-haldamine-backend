package ee.taltech.varadehaldamine.Varadehaldamine.Service;

import ee.taltech.varadehaldamine.Varadehaldamine.Model.Possessor;
import ee.taltech.varadehaldamine.Varadehaldamine.ModelDTO.PossessorInfo;
import ee.taltech.varadehaldamine.Varadehaldamine.Repository.PossessorRepository;
import ee.taltech.varadehaldamine.Varadehaldamine.Service.exception.InvalidPossessorException;
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

    public Possessor getPossesorById(Long possessorId) {
        return possessorRepository.findPossessorById(possessorId);
    }

    public Possessor addPossessor(PossessorInfo possessorInfo){
        try {
            if (possessorInfo != null) {
                Possessor possessor = new Possessor();
                if (possessorInfo.getDivision() == null && possessorInfo.getInstitute() != null) {
                    possessor.setInstitute(possessorInfo.getInstitute());
                } else if (possessorInfo.getDivision() != null && possessorInfo.getInstitute() == null) {
                    possessor.setDivision(possessorInfo.getDivision());
                }
                possessor.setSubdivision(possessorInfo.getSubdivision());
                return possessorRepository.save(possessor);
            } else {
                throw new InvalidPossessorException("Error when saving Possessor");
            }
        } catch (InvalidPossessorException e) {
            System.out.println(e);
        }
        return null;
    }
}
