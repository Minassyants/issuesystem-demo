package mb.pso.issuesystem.repository.im;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.im.MessageContent;
//[ ] REFACTOR
@Repository
public interface MessageContentRepository extends JpaRepository<MessageContent, Integer> {

}
