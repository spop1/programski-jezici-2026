package rs.ac.singidunum.pj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ac.singidunum.pj.entity.Competition;

@Repository
public interface CompetitionRepo extends JpaRepository<Competition, Integer>{
    
    List<Competition> findAllByDeletedAtIsNull();
}
