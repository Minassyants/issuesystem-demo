package mb.pso.issuesystem.service.impl.external;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.http.Method;
import mb.pso.issuesystem.config.properties.MinioProperties;
import mb.pso.issuesystem.dto.FileInfo;
import mb.pso.issuesystem.entity.AttachedFile;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.repository.AttachedFileRepository;

//[x] REFACTOR
@Service
public class MinioService {

    private static final Logger logger = LoggerFactory.getLogger(MinioService.class);

    private final MinioClient minioClient;

    private final String bucketName;

    private final AttachedFileRepository attachedFileRepository;

    public MinioService(AttachedFileRepository attachedFileRepository, MinioProperties minioProperties) {
        minioClient = MinioClient.builder()
                .endpoint(minioProperties.getHost(), minioProperties.getPort(), minioProperties.isSecure())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
        this.bucketName = minioProperties.getBucketName();
        this.attachedFileRepository = attachedFileRepository;
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            logger.error("Exception occurred while creating minio bucket", e);
        }
    }

    public AttachedFile uploadFileToIssue(Issue issue, MultipartFile file) {
        try {
            Date curDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
            String fileName = issue.getId().toString() + "/"
                    + dateFormat.format(curDate) + " "
                    + file.getOriginalFilename();
            minioClient
                    .putObject(
                            PutObjectArgs.builder().bucket(bucketName)
                                    .object(fileName)
                                    .userMetadata(Map.of("issue_id", issue.getId().toString()))
                                    .contentType(file.getContentType())
                                    .stream(file.getInputStream(),
                                            file.getSize(), -1)
                                    .build());
            AttachedFile attachedFile = new AttachedFile();
            attachedFile.setFilePath(fileName);
            attachedFile = attachedFileRepository.save(attachedFile);
            return attachedFile;

        } catch (Exception e) {
            logger.error("Exception occurred while uploading a file", e);
        }
        return null;
    }

    public void deleteFile(AttachedFile file) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(file.getFilePath()).build());
        } catch (Exception e) {
            logger.error("Exception occurred while deleting a file", e);
        }
    }

    public FileInfo getFileInfo(String filePath) {
        try {
            String url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName)
                            .object(filePath).expiry(30, TimeUnit.MINUTES)
                            .build());
            StatObjectResponse statObjectResponse = minioClient
                    .statObject(StatObjectArgs.builder().bucket(bucketName).object(filePath).build());
            FileInfo fileInfo = new FileInfo(url, statObjectResponse.contentType());
            return fileInfo;

        } catch (Exception e) {
            logger.error("Exception occurred while retrieving a file info", e);
            return null;
        }
    }

}
