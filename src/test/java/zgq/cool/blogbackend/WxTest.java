package zgq.cool.blogbackend;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author GQ
 * @home <href="http://www.zgq.cool"/>
 * @date 2023 09/06 21:29
 * @description
 */
@SpringBootTest
public class WxTest {

    @Autowired
    WxMpService wxMpService;

    @Test
    public void test() throws WxErrorException {
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(1, 100);
        String url = wxMpQrCodeTicket.getUrl();
        System.out.println(url);
    }
}
