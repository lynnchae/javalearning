package org.lynn.test.action;

import org.lynn.annotation.AutoWired;
import org.lynn.annotation.Controller;
import org.lynn.annotation.RequestMapping;
import org.lynn.annotation.RequestParam;
import org.lynn.mvc.ModelAndView;
import org.lynn.test.service.HelloService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**

 * Class Name : org.lynn
 * Description :
 * @author : cailinfeng
 * Date : 2018/6/26 16:21
 */
@Controller
@RequestMapping("/")
public class DemoController {

    @AutoWired
    private HelloService helloService;

    @RequestMapping("/first.html")
    public ModelAndView query(@RequestParam("teacher") String teacher){
        helloService.sayHello(teacher);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("teacher", teacher);
        model.put("data", "{name:\"" + teacher + "\",time:\"" + time + "\"}");
        model.put("token", UUID.randomUUID().toString());
        return new ModelAndView("first.html",model);
    }

}
