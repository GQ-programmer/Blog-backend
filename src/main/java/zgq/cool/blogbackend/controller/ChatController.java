package zgq.cool.blogbackend.controller;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import zgq.cool.blogbackend.common.ErrorCode;
import zgq.cool.blogbackend.exception.BusinessException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author 孑然
 * @Date 2022 12/04 12:31
 */
@RestController
@CrossOrigin(origins = {"http://localhost:5175","http://zgq.cool","http://www.zgq.cool"},allowCredentials = "true")
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private YuCongMingClient client;

    @GetMapping("/getMes")
    public BaseResponse<DevChatResponse> getMes(String message) {
        if (StringUtils.isEmpty(message)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "发送消息为空");
        }
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(1654785040361893889L);
        devChatRequest.setMessage(message);
        return client.doChat(devChatRequest);
    }
}