package net.lantrack.project.test.controller;

import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController  extends BaseController {

    @RequestMapping("/{name}")
    public ReturnEntity getName(@PathVariable("name") String name){
        System.out.println("接收到name:"+name);
        return getR().result(name);
    }


}
