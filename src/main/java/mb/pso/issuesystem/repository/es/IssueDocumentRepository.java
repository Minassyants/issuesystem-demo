package mb.pso.issuesystem.repository.es;

import org.springframework.data.repository.CrudRepository;

import mb.pso.issuesystem.entity.es.IssueDocument;

public interface IssueDocumentRepository extends CrudRepository<IssueDocument,Integer>{
    
}
