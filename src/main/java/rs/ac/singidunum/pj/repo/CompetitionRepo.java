package rs.ac.singidunum.pj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.pj.entity.Competition;

public interface CompetitionRepo extends JpaRepository<Competition, Integer>{
    
    List<Competition> findAllByDeletedAtIsNull();

    List<Competition> findOneBycompetitionSchedulesIdAndDeletedAtIsNull(Integer id);
}
