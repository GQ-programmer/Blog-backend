package zgq.cool.blogbackend.controller;

import com.tencent.cloud.Response;
import org.springframework.web.bind.annotation.*;
import zgq.cool.blogbackend.utils.CosStsClientUtils;

/**
 * @Author 孑然
 * @Date 2022 12/08 20:14
 */
@CrossOrigin(origins = {"http://localhost:5175","http://zgq.cool","http://www.zgq.cool"},allowCredentials = "true")
@RestController
public class CosStsController {

    @GetMapping("/cos/getCredential")
    public Response getCredential(){
        Response response = CosStsClientUtils.getCredential();
        return response;
    }
}
