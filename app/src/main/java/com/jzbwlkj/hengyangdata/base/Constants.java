package com.jzbwlkj.hengyangdata.base;

import com.jzbwlkj.hengyangdata.AppConfig;

/**
 * Created by gaoyuan on 2018/1/7.
 */

public interface Constants {
    interface AliPay {
        /**
         * 支付宝AppID
         */
        String APPID = "2017122901357856";

        /**
         * 支付宝回调后台的NotifyUrl
         */
        String NOTIFY_URL = AppConfig.BASE_URL + "/api/Notify/alipay";

        /**
         * 商户私钥，pkcs8格式
         */

        String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCuDTcFgPw8bjwTIg6w3slC6z7BSKDZCMhfIzEi95XIVqvUCuRPcEpmGJDI9JdXf2xxTBLBjG51FoE/2hBlwnhH1nCGelapLxioDDWzZIsL1ls9+trKH3ezIgJBIQiFGn+qrK/VVheo6MPVPEHAkAydNWgYm+YD6vaGNY/tNHl1mWs2OqGjhh8dUMDNGuzJEQefltwZqN0QWFo2rtm+QNj1MtqqZ9R/9aGKCKlpBFlyTrhicAPIiQxayzxj5dZ8+ztcOLNRNjSCbGNDabkSN8uQqzjSG/YPRqblFg6pfgk8hUvEBktn/Ke3WdKDp9g/ndSte7hEYrax1kQalLMfwIhPAgMBAAECggEBAKTrHKF4cIiKIx41/RmWX7mq0dGvEcnUSpfq7polYqVrinDmySKEUdqfmEDxkZ10vbo0zOjcOaV0Z4+q/jKNrA7Vn9WsggTY/VHuD8wljqLuv5Hg6gLvAiqYJO61cC8+xo1gB1PruYtw0m5wJBVj6K/ldPcscSxxLJpKkk++kv/bhpifUY7WrujkiV0O4AkXN/Xh9TT4Zyz0IOh6kkNqcoU4S5QCIIhA+w+qr/7OT4Q3e4hSkLEkJSwF3yk+NUm4BJKAeKnmcDL2HbbQppwe/sLt3oErjuD8CR2s0wpBbedEYCMQndnMaIdSB1rT1313QYJ1MxUqwvMMVtlT/08Ta1ECgYEA61UBbmrgKTjBOozorZDlUDv+iu31/kRx62Cf+CPn1d/evKUWLsrQVj7lJv0/FJ8Om0JU/i1loCCTJ775F26pQLUAarnwqasIucd171E2vWwlP+Zrw6sBwzB8HVHy0RK6LTygIqx6XSdWWlFf15QtEZmjyUswLH3BKqtS1GDZdhUCgYEAvVZvZvVNRyp0wHIkyVtY3rzwQQw+2pX/OfEYgiZ1+iOKJuQkY1maZJQPDg+ZHYkS8tIqNH8o1qU15imSzqwd0/13gokT0O9Sxf/6fmIF4hkX1mkxoql7fNd2KiM17p95P6Bz3eg2YtqBT4dEIErQWdSzOn8UtXdGLJjw0Z19odMCgYB4fX6NHVWGZZOxucHMVDrE2OLcGThxJnQ5crCVKTUjcUsmxVPVQ2xmLC/BxbpNgZ7F6bGArpew49zUZdg9oiAwjDwZaPiKtpJyJGkCzk87rypnTD/y4BS37pQtou6zE2aVkMvg1PSVy3VQKwfqZeipcUPF5aRVs1F62ls/eBL/fQKBgEU5SeexvfxL+H23/d0kqbxeBdUyffR7LOJpVJWZLD81KTiI5wjv5LPrMgnpCAk1aZkf39BrwWmg47Dv+D3ZzdoI7N3r8jPMnE3KBjKY8h+1HvMEaOJY5s5BFHHZh8ljwvfRWSuD0Aaabva9tNm9oKmHS7QSbGbgYebCUCHDXACDAoGAAmAAUOpjYH5q5ar1J9ZJXHX4Tc1L6/rtTuopZwfLR7oin6jkNLnx7+AV6qydDZ0xh9joaBIw01ObSoEL3f86JCRIWiieAiUnAFXILw1lQ7CJ8SfVFZMXMwqMKIKcTEbw8AruieUK39jE1v46sW6f7zLkFtGNoMdSRe9kQw9IEOM=";



    }

    interface WxPay {
        /**
         * 微信 AppID，在微信开放平台创建应用，并开通支付能力
         */
        String APP_ID = "wx94d744ec7531161d";

        /**
         * 商户号
         */
        String WX_SHOP_NUM = "1497464602";
        /**
         * 微信应用密钥
         */
        String WX_API_KEUSTORE = "dhe12u3921dg1HSD9D19UYsu0u1198h2";

        String NOTIFY_URL = AppConfig.BASE_URL + "/api/Notify/wechat";
    }
}
