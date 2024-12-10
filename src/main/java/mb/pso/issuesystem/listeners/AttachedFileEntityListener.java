package mb.pso.issuesystem.listeners;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jakarta.persistence.PostRemove;
import mb.pso.issuesystem.entity.core.AttachedFile;
import mb.pso.issuesystem.service.impl.external.MinioService;


@Component
public class AttachedFileEntityListener {

    private final MinioService minioService;

    public AttachedFileEntityListener(@Lazy MinioService minioService) {
        this.minioService = minioService;
    }

    @PostRemove
    public void handlePostRemove(AttachedFile attachedFile) {
        minioService.deleteFile(attachedFile);
    }
}
