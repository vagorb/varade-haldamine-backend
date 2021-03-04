package ee.taltech.varadehaldamine.Varadehaldamine.Service;
import ee.taltech.varadehaldamine.Varadehaldamine.Model.Vara;
import ee.taltech.varadehaldamine.Varadehaldamine.repository.VaradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VaradeService {

    @Autowired
    private VaradeRepository varadeRepository;


//    public List<Vara> findAll() {
//        return varadeRepository.findAll();
//    }

    public void save(Vara vara) {
        varadeRepository.save(vara);
    }
}
