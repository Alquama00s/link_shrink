package com.linkshrink.redirector.redis;

import com.linkshrink.redirector.entity.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClientTest {


    @Test
    public void test(){
        var c = Client.build();
        var u = new Url();
        u.setLongUrl("some th");
//        c.put("get",u);
        var gu = c.get("get", Url.class);

        Assertions.assertEquals(u,gu);

        c.destroy();
    }

}
