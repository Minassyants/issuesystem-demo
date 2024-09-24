package mb.pso.issuesystem.listeners;

import org.springframework.stereotype.Component;

import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import jakarta.persistence.PostRemove;
import mb.pso.issuesystem.entity.AttachedFile;

@Component
public class AttachedFileEntityListener {

    private final MinioClient minioClient;
    // TODO bucketName в env
    private final String bucketName = "issuesystem";

    public AttachedFileEntityListener() {
        minioClient = MinioClient.builder().endpoint("kz-alm-bsk-ws01.ukravto.loc", 7012, false)
                .credentials("XyZg4T3MWqxCeQ6XL2UY", "aKtOTSyLXJezqPwllmLlJQSzBNtx4nVYXajPV38K").build();
    }

    @PostRemove
    public void handlePostRemove(AttachedFile attachedFile) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucketName).object(attachedFile.getFilePath()).build());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
