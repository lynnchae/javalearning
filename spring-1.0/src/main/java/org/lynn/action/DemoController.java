package org.lynn.action;

import org.lynn.annotation.AutoWired;
import org.lynn.annotation.Controller;
import org.lynn.annotation.RequestMapping;
import org.lynn.annotation.RequestParam;
import org.lynn.service.DemoService;

/**

 * Class Name : org.lynn
 * Description :
 * Author : cailinfeng
 * Date : 2018/6/26 16:21
 */
@Controller
public class DemoController {

    @AutoWired
    private DemoService demoService;

    @RequestMapping
    public void say(@RequestParam String name){
        demoService.sayHello(name);
    }

}
