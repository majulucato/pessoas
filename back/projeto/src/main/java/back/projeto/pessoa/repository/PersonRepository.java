package back.projeto.pessoa.repository;

import back.projeto.pessoa.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 " +
                    "FROM person " +
                    "WHERE cpf = :cpf")
    boolean existsByCPF(@Param("cpf") String cpf);
}
