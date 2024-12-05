package mb.pso.issuesystem.dto;

import java.util.List;

public record MessageContentDto(Integer id, String textMessage, List<AttachedFileDto> attachedFiles, Boolean isAnswer) {

}
