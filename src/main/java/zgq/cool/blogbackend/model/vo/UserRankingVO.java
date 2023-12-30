package zgq.cool.blogbackend.model.vo;

import lombok.Data;

/**
 * @author GQ
 * @home <href="http://www.zgq.cool"/>
 * @date 2023 12/18 15:58
 * @description
 */
@Data
public class UserRankingVO {

    private Long userId;

    private Integer articleTotal;

    private String username;

    private String avatarUrl;
}
