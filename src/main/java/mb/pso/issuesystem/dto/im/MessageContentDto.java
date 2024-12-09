package mb.pso.issuesystem.dto.im;

import java.util.List;

import mb.pso.issuesystem.dto.core.AttachedFileDto;

public record MessageContentDto(Integer id, String textMessage, List<AttachedFileDto> attachedFiles, Boolean isAnswer) {

}
