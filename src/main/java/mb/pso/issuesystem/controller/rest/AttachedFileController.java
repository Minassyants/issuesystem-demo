package mb.pso.issuesystem.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import mb.pso.issuesystem.dto.core.FileInfo;
import mb.pso.issuesystem.service.impl.external.MinioService;


@Tag(name = "Attached files", description = "Operations related to attached files")
@RestController
@RequestMapping("/attached-files")
public class AttachedFileController {

    private final MinioService minioService;

    public AttachedFileController(MinioService minioService) {
        this.minioService = minioService;
    }

    @Operation(summary = "Get file information by file path")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "File information retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @GetMapping
    public FileInfo getFileInfo(
            @Parameter(description = "Path to the file", required = true) @RequestParam @NotBlank String filePath) {
        FileInfo fileInfo = minioService.getFileInfo(filePath);
        if (fileInfo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }

        return fileInfo;
    }
}
