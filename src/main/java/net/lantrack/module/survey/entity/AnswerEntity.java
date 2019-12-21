package net.lantrack.module.survey.entity;

import lombok.Data;
import java.io.Serializable;
/**
 *@Description 答卷
 *@Author dahuzi
 *@Date 2019/12/7  18:10
 */
@Data
public class AnswerEntity implements Serializable {
    /**
     * 问题id
     */
    private String qId;
    /**
     * 选项id
     */
    private String optionId;
    /**
     * 其他_______值
     */
    private String otherValue;
    /**
     * 选项内容（男，女），填空内容（姓名，年龄等）
     */
    private String content;
}
