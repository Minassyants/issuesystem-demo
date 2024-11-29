package mb.pso.issuesystem.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mb.pso.issuesystem.dto.FileInfo;
import mb.pso.issuesystem.service.s3.MinioService;
//[ ] REFACTOR
@Controller
@RequestMapping("/api/file")
@CrossOrigin(origins = "*")
public class AttachedFileController {

    private final MinioService minioService;

    public AttachedFileController(MinioService minioService) {
        this.minioService = minioService;
    }

    @GetMapping
    public ResponseEntity<FileInfo> getFileInfo(@RequestParam String filePath) {
        FileInfo fileInfo = minioService.getFileInfo(filePath);
        return ResponseEntity.ok(fileInfo);
    }
}
