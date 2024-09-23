package mb.pso.issuesystem.service.s3;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import mb.pso.issuesystem.entity.AttachedFile;
import mb.pso.issuesystem.repository.AttachedFileRepository;

@Service
public class MinioService {
    // TODO эти все штуки надо выводить в env переменные
    private final MinioClient minioClient;
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public AttachedFile uploadFile(MultipartFile file) {
        try {
            minioClient
                    .putObject(PutObjectArgs.builder().bucket(bucketName).object("test1").stream(file.getInputStream(),
                            file.getSize(), -1).build());
            AttachedFile attachedFile = new AttachedFile();
            attachedFile.setUrl(
                    minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object("test1")
                                    .build()));
            attachedFile = attachedFileRepository.save(attachedFile);
            return attachedFile;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
