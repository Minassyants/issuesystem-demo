package mb.pso.issuesystem.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.constraints.NotBlank;
import mb.pso.issuesystem.dto.FileInfo;
import mb.pso.issuesystem.service.s3.MinioService;

//[x] REFACTOR
//[ ] SWAGGER VEZDE?
@RestController
@RequestMapping("/api/file")
@CrossOrigin(origins = "*")
public class AttachedFileController {

    private final MinioService minioService;

    public AttachedFileController(MinioService minioService) {
        this.minioService = minioService;
    }

    @GetMapping
    public FileInfo getFileInfo(@RequestParam @NotBlank String filePath) {
        FileInfo fileInfo = minioService.getFileInfo(filePath);
        if (fileInfo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }

        return fileInfo;
    }
}
