package ee.taltech.varadehaldamine.service;

import ee.taltech.varadehaldamine.exception.InvalidPossessorException;
import ee.taltech.varadehaldamine.model.Possessor;
import ee.taltech.varadehaldamine.modelDTO.PossessorInfo;
import ee.taltech.varadehaldamine.repository.PossessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PossessorService {

    @Autowired
    private PossessorRepository possessorRepository;

    /**
     * Method to get all possessors from db.
     *
     * @return all possessors
     */
    public List<Possessor> findAll() {
        return possessorRepository.findAll();
    }

    /**
     * Method to get possessor by id.
     *
     * @param possessorId possessor id
     * @return possessor
     */
    public Possessor getPossessorById(Long possessorId) {
        return possessorRepository.findPossessorById(possessorId);
    }

    /**
     * Method to add new possessor.
     *
     * @param possessorInfo new possessor info holder
     * @return added possessor
     */
    public Possessor addPossessor(PossessorInfo possessorInfo) {
        try {
            if (possessorInfo != null && possessorInfo.getStructuralUnit() != null) {
                Possessor possessor = new Possessor();
                possessor.setStructuralUnit(possessorInfo.getStructuralUnit());
                possessor.setSubdivision(possessorInfo.getSubdivision());
                return possessorRepository.save(possessor);
            } else {
                throw new InvalidPossessorException("Error when saving Possessor, insufficient data");
            }
        } catch (InvalidPossessorException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Method to update possessors' structural unit.
     *
     * @param possessor new possessor unit holder
     * @param id        possessor id
     * @return changes possessor
     */
    public Possessor update(Possessor possessor, Long id) {
        Possessor dbPossessor = possessorRepository.findPossessorById(id);
        dbPossessor.setStructuralUnit(possessor.getStructuralUnit());
        return possessorRepository.save(dbPossessor);
    }

    /**
     * Method to check if possessor with given structural unit and subdivision exists.
     *
     * @param structuralUnit structural unit
     * @param subDivision    subdivision
     * @return exists or not
     */
    public Possessor findPossessor(Integer structuralUnit, Integer subDivision) {
        List<Possessor> all = findAll();
        for (Possessor possessor : all) {
            if (subDivision == null
                    && possessor.getStructuralUnit().equals(structuralUnit)
                    || possessor.getStructuralUnit().equals(structuralUnit)
                    && possessor.getSubdivision().equals(subDivision)
                    || possessor.getStructuralUnit().equals(structuralUnit)) {
                return possessor;
            }
        }
        return null;
    }

}
