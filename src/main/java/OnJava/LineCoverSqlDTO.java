package OnJava;

import lombok.Data;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/6/12 15:42
 */
@Data
public class LineCoverSqlDTO {

    public LineCoverSqlDTO(String bureauId, String parentBureauId, Double totalLength) {
        this.bureauId = bureauId;
        this.parentBureauId = parentBureauId;
        this.totalLength = totalLength;
    }

    String bureauId;
    String parentBureauId;
    String bureauName;
    Double totalLength;
    Double unCoverLength;

    Double allSubTotalLength;
    String remark;


}
