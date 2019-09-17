package net.lantrack.project.test.controller;

import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController  extends BaseController {

    @Autowired
    private  RedisUtils redisUtils;

    @RequestMapping("/{name}")
    public ReturnEntity getName(@PathVariable("name") String name){
        redisUtils.set("testName",name);
        System.out.println("接收到name:"+name);
        System.out.println(redisUtils.get("testName"));
        return getR().result(name);
    }


}
