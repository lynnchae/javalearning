package org.lynn.demo.action;

import org.lynn.duplicate.annotation.AutoWired;
import org.lynn.duplicate.annotation.Controller;
import org.lynn.duplicate.annotation.RequestMapping;
import org.lynn.duplicate.annotation.RequestParam;
import org.lynn.demo.service.DemoService;

/**

 * Class Name : org.lynn
 * Description :
 * @author : cailinfeng
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
