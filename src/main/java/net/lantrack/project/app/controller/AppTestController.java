
package net.lantrack.project.app.controller;


import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.project.app.annotation.Login;
import net.lantrack.project.app.annotation.LoginUser;
import net.lantrack.project.app.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@Description APP测试接口
 *@Author lantrack
 *@Date 2019/8/22  18:06
 */
@RestController
@RequestMapping("/app")
@Api("APP测试接口")
public class AppTestController extends BaseController {

    @Login
    @GetMapping("userInfo")
    @ApiOperation("获取用户信息")
    public ReturnEntity userInfo(@LoginUser UserEntity user){
        return getR().put("user", user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public ReturnEntity userInfo(@RequestAttribute("userId") Integer userId){
        return getR().put("userId", userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public ReturnEntity notToken(){
        return getR().put("msg", "无需token也能访问。。。");
    }

}
