package mb.pso.issuesystem.service.s3;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
import mb.pso.issuesystem.dto.FileInfo;
import mb.pso.issuesystem.entity.AttachedFile;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.repository.AttachedFileRepository;

@Service
public class MinioService {
    // [ ] эти все штуки надо выводить в env переменные
    private final MinioClient minioClient;
    // [ ] bucketName в env
    private final String bucketName = "issuesystem";
    private final AttachedFileRepository attachedFileRepository;

    public MinioService(AttachedFileRepository attachedFileRepository) {
        minioClient = MinioClient.builder().endpoint("kz-alm-bsk-ws01.ukravto.loc", 7012, false)
                .credentials("XyZg4T3MWqxCeQ6XL2UY", "aKtOTSyLXJezqPwllmLlJQSzBNtx4nVYXajPV38K").build();
        this.attachedFileRepository = attachedFileRepository;
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            // [ ] Auto-generated catch block
            e.printStackTrace();
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
            // [ ] Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void deleteFile(AttachedFile file) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(file.getFilePath()).build());
        } catch (Exception e) {
            // [ ] Auto-generated catch block
            e.printStackTrace();
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
            // [ ] Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
