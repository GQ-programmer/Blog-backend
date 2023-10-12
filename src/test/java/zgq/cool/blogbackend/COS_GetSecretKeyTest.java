package zgq.cool.blogbackend;

import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.TreeMap;

/**
 * @Author 孑然
 * @Date 2022 12/08 18:43
 */

public class COS_GetSecretKeyTest {

    /**
     * 基本的临时密钥申请示例，适合对一个桶内的一批对象路径，统一授予一批操作权限
     */
    @Test
    void testGetCredential() {
        TreeMap<String, Object> config = new TreeMap<String, Object>();
        try {
            Properties properties = new Properties();
            File configFile = new File("src/main/resources/local.properties");
            properties.load(new FileInputStream(configFile));

            // 云 api 密钥 SecretId
            config.put("secretId", properties.getProperty("SecretId"));
            // 云 api 密钥 SecretKey
            config.put("secretKey", properties.getProperty("SecretKey"));

            if (properties.containsKey("https.proxyHost")) {
                System.setProperty("https.proxyHost", properties.getProperty("https.proxyHost"));
                System.setProperty("https.proxyPort", properties.getProperty("https.proxyPort"));
            }

            // 设置域名
            //config.put("host", "sts.internal.tencentcloudapi.com");

            // 临时密钥有效时长，单位是秒
            config.put("durationSeconds", 1800);

            // 换成你的 bucket
            config.put("bucket", "zgq-icu-2002-1313043931");
            // 换成 bucket 所在地区
            config.put("region", "ap-shanghai");

            // 可以通过 allowPrefixes 指定前缀数组, 例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
            config.put("allowPrefixes", new String[] {
                    "*"
            });

            // 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[] {
                    // 简单上传
                    "name/cos:PutObject",
                    "name/cos:PostObject",

            };
            config.put("allowActions", allowActions);

            Response response = CosStsClient.getCredential(config);
            System.out.println(response.credentials.tmpSecretId + ":id");
            System.out.println(response.credentials.tmpSecretKey + ":key");
            System.out.println(response.credentials.sessionToken + ":token");
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }

}
